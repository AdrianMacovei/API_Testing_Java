package io.dummy_api.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

@Getter
@Setter
public class RestRequest {

    private Object body = "";
    private HttpMethod httpMethod;
    private String path;
    private String[] pathParams;
//    private String contentType;


    private RestRequest(HttpMethod httpMethod, String path, String... pathParams)
    {
        setHttpMethod(httpMethod);
        setPath(path);
        setPathParams(pathParams);
    }

    private RestRequest(HttpMethod httpMethod, Object body, String path, String... pathParams) {
        setHttpMethod(httpMethod);
        setPath(path);
        setPathParams(pathParams);
        setBody(body);
    }

    /**
     * This method create a request without body
     * @param httpMethod
     * @param path
     * @param pathParams
     * @return Object of type RestRequest only with fields httpMethod, path and pathParams
     */
    public static RestRequest simpleRequest(HttpMethod httpMethod, String path, String... pathParams)
    {
        return new RestRequest(httpMethod, path, pathParams);
    }

    /**
     * This method create a request with body
     * @param httpMethod
     * @param body
     * @param path
     * @param pathParams
     * @return Object of type RestRequest with body and rest of the fields
     */
    public static RestRequest requestWithBody(HttpMethod httpMethod, Object body, String path, String... pathParams)
    {
        return new RestRequest(httpMethod, body, path, pathParams);
    }
}
