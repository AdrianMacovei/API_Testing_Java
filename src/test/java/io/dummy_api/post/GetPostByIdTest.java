package io.dummy_api.post;

import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.PostModel;
import io.dummy_api.models.PostsCollection;
import io.dummy_api.models.UserModel;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import io.dummy_api.ApiBaseClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import static org.apache.http.HttpStatus.*;

public class GetPostByIdTest extends PostBaseClass {

    @Test
    void testGetPostById() {
        PostModel postModel = getRandomPost();
        Response response = getRestWrapper().sendRequest(HttpMethod.GET,
                "post/{params}",
                "", postModel.getId());
        getInfo(response);
        PostModel rspPostModel = getRestWrapper().convertResponseToModel(response, PostModel.class);


        softAssert.assertEquals(response.statusCode(), SC_OK);
        softAssert.assertEquals(rspPostModel.getId(), postModel.getId());;
        softAssert.assertAll();
    }
}
