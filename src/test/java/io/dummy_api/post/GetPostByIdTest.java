package io.dummy_api.post;
import io.dummy_api.models.ErrorPostModel;
import io.dummy_api.models.PostModel;
import io.dummy_api.user.DataProviderClass;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.*;

public class GetPostByIdTest extends PostBaseClass {

    @Test
    void testGetPostById() {
        PostModel postModel = getRandomPost();
        PostModel response = restWrapper.usingPosts().getPostById(postModel.getId());

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_OK);
        softAssert.assertEquals(response.getId(), postModel.getId());
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_ids")
    void testGetPostWithInvalidId(String postId) {
        ErrorPostModel response = restWrapper.usingPosts().getPostByIdError(postId);

        if (postId.length() == 24 && postId.matches("\\d+")) {
            softAssert.assertEquals(restWrapper.getStatusCode(), SC_NOT_FOUND);
            softAssert.assertEquals(response.getError(), "RESOURCE_NOT_FOUND");
        } else {
            softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
            softAssert.assertEquals(response.getError(), "PARAMS_NOT_VALID");
        }
        softAssert.assertAll();
    }

}
