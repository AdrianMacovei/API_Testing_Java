package io.dummy_api.post;

import io.dummy_api.models.CreateBodyPostModel;
import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.PostModel;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;

public class DeletePostTest extends PostBaseClass {

    @Test
    void testDeletePost() {
        Response responseCreateNewPost = createNewPost(CreateBodyPostModel.generateRandomPostBody(createRandomUserInDb()));
        PostModel newPost = restWrapper.convertResponseToModel(responseCreateNewPost, PostModel.class);

        Response response = deletePost(newPost.getId());
        getInfo(response);
        softAssert.assertEquals(response.statusCode(), SC_OK);
        softAssert.assertEquals(response.jsonPath().getString("id"), newPost.getId());
        softAssert.assertAll();
    }

    @Test
    void testAnAlreadyDeletedPost()
    {
        Response responseCreateNewPost = createNewPost(CreateBodyPostModel.generateRandomPostBody(createRandomUserInDb()));
        PostModel newPost = restWrapper.convertResponseToModel(responseCreateNewPost, PostModel.class);
        deletePost(newPost.getId());
        Response response = deletePost(newPost.getId());
        ErrorModel errorRsp = restWrapper.convertResponseToModel(response, ErrorModel.class);
        getInfo(response);

        softAssert.assertEquals(errorRsp.getError(), "RESOURCE_NOT_FOUND");
        softAssert.assertAll();
    }

    @Test
    void testDeletePostNoAppId() {
        Response responseCreateNewPost = createNewPost(CreateBodyPostModel.generateRandomPostBody(createRandomUserInDb()));
        PostModel newPost = restWrapper.convertResponseToModel(responseCreateNewPost, PostModel.class);

        Response response = restWrapperNoId.sendRequest(HttpMethod.DELETE, "post/{params}", "", newPost.getId());
        ErrorModel errorRsp = restWrapperNoId.convertResponseToModel(response, ErrorModel.class);
        getInfo(response);

        softAssert.assertEquals(response.statusCode(), SC_FORBIDDEN);
        softAssert.assertEquals(errorRsp.getError(), "APP_ID_MISSING");
        softAssert.assertAll();
    }
}
