package io.dummy_api.core;

import io.dummy_api.exception.JsonToModelConversionException;
import io.dummy_api.requests.UsersRequests;
import io.dummy_api.util.Properties;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

@Service
@Scope("prototype")
public class RestWrapper
{

    @Autowired
    private Properties properties;

    private final RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();

    public String parameters;

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

    protected Response sendRequest(RestRequest restRequest)
    {
        Response returnedResponse;

        switch(restRequest.getHttpMethod())
        {
            case GET:
                returnedResponse = onRequest().get(restRequest.getPath(), restRequest.getPathParams()).andReturn();
                break;
            case DELETE:
                returnedResponse = onRequest().delete(restRequest.getPath(), restRequest.getPathParams()).andReturn();
                break;
            case POST:
                returnedResponse = onRequest().body(restRequest.getBody()).post(restRequest.getPath(),
                        restRequest.getPathParams()).andReturn();
                break;
            case PUT:
                returnedResponse = onRequest().body(restRequest.getBody()).put(restRequest.getPath(),
                        restRequest.getPathParams()).andReturn();
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

    public String getParameters()
    {
        String localParams = parameters;
        clearParameters();
        return localParams;
    }
    protected void setParameters(String parameters)
    {
        this.parameters = parameters;
    }

    protected void clearParameters()
    {
        setParameters("");
    }

    public RestWrapper withParams(String... parameters)
    {
        String paramsStr = Arrays.stream(parameters).collect(Collectors.joining("&"));
        setParameters(paramsStr);
        return this;
    }

    public <T> T executeRequestAndProcessModel(Class<T> modelClass, RestRequest restRequest)
    {
        return callAPIAndCreateModel(modelClass, restRequest);
    }

    private <T> T callAPIAndCreateModel(Class<T> modelClass, RestRequest restRequest)
    {
        Response returnedResponse = sendRequest(restRequest);
        setStatusCode(String.valueOf(returnedResponse.getStatusCode()));

        T model;

        try
        {
            model = returnedResponse.getBody().as(modelClass);
        } catch (Exception processError)
        {
            processError.printStackTrace();
            throw new JsonToModelConversionException(modelClass, processError);
        }
        return model;
    }

    public UsersRequests usingUsers()
    {
        return new UsersRequests(this);
    }

}
