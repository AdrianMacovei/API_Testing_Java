package io.dummy_api.post;

import io.dummy_api.models.ErrorPostModel;
import io.dummy_api.models.PostModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.*;

public class DeletePostTest extends PostBaseClass {

    @Test
    void testDeletePost() {
        PostModel newPost = createNewPost();
        PostModel response = deletePost(newPost.getId());

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_OK);
        softAssert.assertEquals(response.getId(), newPost.getId());
        softAssert.assertAll();
    }

    @Test
    void testDeleteAlreadyDeletedPost()
    {
        PostModel newPost = createNewPost();
        deletePost(newPost.getId());
        ErrorPostModel responseError = restWrapper.usingPosts().deletePostError(newPost.getId());

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_NOT_FOUND);
        softAssert.assertEquals(responseError.getError(), "RESOURCE_NOT_FOUND");
        softAssert.assertAll();
    }

    @Test
    void testDeleteRandomIdPost()
    {
        String newPostId= RandomStringUtils.random(24, true, true);
        ErrorPostModel responseError = restWrapper.usingPosts().deletePostError(newPostId);

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
        softAssert.assertEquals(responseError.getError(), "PARAMS_NOT_VALID");
        softAssert.assertAll();
    }

    @Test
    void testDeletePostNoAppId() {
        PostModel newPost = createNewPost();
        ErrorPostModel responseError = restWrapperNoId.usingPosts().deletePostError(newPost.getId());

        softAssert.assertEquals(restWrapperNoId.getStatusCode(), SC_FORBIDDEN);
        softAssert.assertEquals(responseError.getError(), "APP_ID_MISSING");
        softAssert.assertAll();
    }
}
