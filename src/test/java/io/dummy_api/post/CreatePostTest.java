package io.dummy_api.post;

import io.dummy_api.models.CreateBodyPostModel;
import io.dummy_api.models.PostModel;
import io.dummy_api.models.UserModel;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.*;

public class CreatePostTest extends PostBaseClass {

    @Test
    void testCreatePost()
    {
        UserModel user = createRandomUserInDb();
        CreateBodyPostModel newPostBody = CreateBodyPostModel.generateRandomPostBody(user);
        Response response = restWrapper.sendRequest(HttpMethod.POST,
                "post/create",
                newPostBody, "");
        PostModel postModel = restWrapper.convertResponseToModel(response, PostModel.class);
        getInfo(response);

        softAssert.assertEquals(response.statusCode(), SC_CREATED);
        softAssert.assertEquals(postModel.getOwner().getId(), user.getId());
        softAssert.assertEquals(postModel.getText(), newPostBody.getText());
        softAssert.assertAll();
    }
}
