package io.dummy_api.post;

import io.dummy_api.models.CreateBodyPostModel;
import io.dummy_api.models.PostModel;
import io.dummy_api.models.UserModel;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class DeletePostTest extends PostBaseClass {

    @Test
    void testDeletePost() {
        Response responseCreateNewPost = createNewPost(CreateBodyPostModel.generateRandomPostBody(createRandomUserInDb()));
        PostModel newPost = getRestWrapper().convertResponseToModel(responseCreateNewPost, PostModel.class);

        Response response = deletePost(newPost.getId());
        getInfo(response);
        softAssert.assertEquals(response.statusCode(), SC_OK);
        softAssert.assertEquals(response.jsonPath().getString("id"), newPost.getId());
        softAssert.assertAll();
    }
}
