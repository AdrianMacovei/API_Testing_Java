package io.dummy_api.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dummy_api.exception.JsonToModelConversionException;
import io.dummy_api.util.Properties;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

@Service
@Scope("prototype")
public class RestWrapper
{

    @Autowired
    private Properties properties;

    private final RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();

    @PostConstruct
    public void initializeRequestSpecBuilder()
    {
//        String url = properties.getApiUri();
        String url = properties.getURI();
        RestAssured.baseURI = url;

        configureRequestSpec().setBaseUri(url);
    }

    public RequestSpecBuilder configureRequestSpec()
    {
        return this.requestSpecBuilder;
    }

    public RestWrapper addRequestHeader(String headerKey, String headerValue)
    {
        configureRequestSpec().addHeader(headerKey, headerValue);
        return this;
    }

    public Response sendRequest(HttpMethod httpMethod, String path, Object body, Object params)
    {
        Response returnedResponse;

        switch(httpMethod)
        {
            case GET:
                if (params.toString().equals(""))
                {
                    returnedResponse = onRequest().body(body).get(path).andReturn();
                } else
                {
                    returnedResponse = onRequest().body(body).get(path, params).andReturn();
                }
                break;
            case DELETE:
                if (params.toString().equals(""))
                {
                    returnedResponse = onRequest().body(body).delete(path).andReturn();
                } else
                {
                    returnedResponse = onRequest().body(body).delete(path, params).andReturn();
                }
                break;
            case POST:
                if (params.toString().equals(""))
                {
                    returnedResponse = onRequest().body(body).post(path).andReturn();
                } else
                {
                    returnedResponse = onRequest().body(body).post(path, params).andReturn();
                }
                break;
            case PUT:
                if (params.toString().equals(""))
                {
                    returnedResponse = onRequest().body(body).put(path).andReturn();
                } else
                {
                    returnedResponse = onRequest().body(body).put(path, params).andReturn();
                }
                break;
            default:
                throw new RuntimeException("Please provide a valid request method");
        }
        return returnedResponse;
    }

    protected RequestSpecification onRequest()
    {
        return given().spec(configureRequestSpec().setContentType(ContentType.JSON).build());
    }


    public <T> T convertResponseToModel(Response response, Class<T> modelClass)
    {
        T model;

        try
        {
            model = response.getBody().as(modelClass);
        } catch (Exception processError)
        {
            processError.printStackTrace();
            throw new JsonToModelConversionException(modelClass, processError);
        }
        return model;
    }

    public static <T> T convertHashMapToModel (HashMap<T, T> hashMap, Class<T> modelClass)
    {
        T model;

        try
        {
            ObjectMapper mapper = new ObjectMapper();
            model = mapper.convertValue(hashMap, modelClass);
        } catch (Exception processError)
        {
            processError.printStackTrace();
            throw new JsonToModelConversionException(modelClass, processError);
        }
        return model;
    }
}
