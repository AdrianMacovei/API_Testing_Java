package io.dummy_api.post;
import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.PostModel;
import io.dummy_api.user.DataProviderClass;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.*;

public class GetPostByIdTest extends PostBaseClass {

    @Test
    void testGetPostById() {
        PostModel postModel = getRandomPost();
        Response response = restWrapper.sendRequest(HttpMethod.GET,
                "post/{params}",
                "", postModel.getId());
        getInfo(response);
        PostModel rspPostModel = restWrapper.convertResponseToModel(response, PostModel.class);


        softAssert.assertEquals(response.statusCode(), SC_OK);
        softAssert.assertEquals(rspPostModel.getId(), postModel.getId());
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_ids")
    void testGetPostWithInvalidId(String postId) {
        Response response = restWrapper.sendRequest(HttpMethod.GET,
                "post/{params}",
                "", postId);
        ErrorModel postError = restWrapper.convertResponseToModel(response, ErrorModel.class);
        getInfo(response);

        if (postId.length() == 24 && postId.matches("\\d+")) {
            softAssert.assertEquals(response.statusCode(), SC_NOT_FOUND);
            softAssert.assertEquals(postError.getError(), "RESOURCE_NOT_FOUND");
        } else {
            softAssert.assertEquals(response.statusCode(), SC_BAD_REQUEST);
            softAssert.assertEquals(postError.getError(), "PARAMS_NOT_VALID");
        }
        softAssert.assertAll();
    }

}
