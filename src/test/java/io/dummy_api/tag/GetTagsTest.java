package io.dummy_api.tag;

import io.dummy_api.models.ErrorUserModel;
import io.dummy_api.models.PostModel;
import io.dummy_api.models.TagsModel;
import io.dummy_api.post.PostBaseClass;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;

public class GetTagsTest extends PostBaseClass {

    @Test
    void testGetTags() {
        PostModel responseNewPost = createNewPost();
        String myTag = responseNewPost.getTags().get(0);

        TagsModel response = restWrapper.usingTags().getTags();
        boolean isMyTagIn = false;
        if (response.getData().contains(myTag))
            {
                isMyTagIn = true;
            }
        softAssert.assertEquals(restWrapper.getStatusCode(), SC_OK);
        softAssert.assertTrue(isMyTagIn);
        softAssert.assertAll();
    }

    @Test
    void testGetTagsNoAppId() {
        ErrorUserModel response = restWrapperNoId.usingTags().getTagsError();

        softAssert.assertEquals(restWrapperNoId.getStatusCode(), SC_FORBIDDEN);
        softAssert.assertEquals(response.getError(), ERROR_MSG_MISSING_APP_ID);
        softAssert.assertAll();
    }

    @Test
    void testGetRandomTag() {
        String myTag = RandomStringUtils.random(30, true, true);

        TagsModel response = restWrapper.usingTags().getTags();
        boolean isMyTagIn = false;
        if (response.getData().contains(myTag))
        {
            isMyTagIn = true;
        }
        softAssert.assertEquals(restWrapper.getStatusCode(), SC_OK);
        softAssert.assertFalse(isMyTagIn);
        softAssert.assertAll();
    }

}
