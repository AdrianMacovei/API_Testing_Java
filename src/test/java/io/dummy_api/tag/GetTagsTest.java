package io.dummy_api.tag;

import io.dummy_api.ApiBaseClass;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

public class GetTagsTest extends ApiBaseClass {

    @Test
    void testGetTags()
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "tag", "", "");
        getInfo(response);
        System.out.println(response.jsonPath().getList("data").size());
    }

    @Test
    void testGetTag()
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "tag/{params}/post", "", "leave");
        getInfo(response);
    }

}
