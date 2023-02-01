package io.dummy_api.core;

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

import static io.restassured.RestAssured.given;

@Service
@Scope("prototype")
public class RestWrapper
{

    @Autowired
    private Properties properties;

    private RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();

    @PostConstruct
    public void initializeRequestSpecBuilder()
    {
        String url = properties.getApiUri();
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
                returnedResponse = onRequest().get(path, params).andReturn();
                break;
            case DELETE:
                returnedResponse = onRequest().delete(path, params).andReturn();
                break;
            case POST:
                returnedResponse = onRequest().body(body).post(path, params).andReturn();
                break;
            case PUT:
                returnedResponse = onRequest().body(body).put(path, params).andReturn();
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


}
