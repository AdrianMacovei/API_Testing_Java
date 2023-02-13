package io.dummy_api.post;

import io.dummy_api.models.CreateBodyPostModel;
import io.dummy_api.models.ErrorPostModel;
import io.dummy_api.models.PostModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.*;

public class UpdatePostTest extends PostBaseClass {

    @DataProvider(name = "valid_data_update_post")
    public Object[][] createValidDataUpdatePost() {
        CreateBodyPostModel validDataForUpdatePost = new CreateBodyPostModel(
                RandomStringUtils.randomAlphanumeric(15),
                "https://loremflickr.com/320/240",
                "0",
                () -> {
                    ArrayList<String> tags = new ArrayList<>();
                    tags.add(RandomStringUtils.randomAlphanumeric(5));
                    return tags;
                },
                RandomStringUtils.randomAlphanumeric(15),
                createRandomUserInDb().getId()
        );

        return new CreateBodyPostModel[][]{
                {validDataForUpdatePost},
        };
    }

    @Test(dataProvider = "valid_data_update_post")
    void testUpdatePostValidDataInAllFields(CreateBodyPostModel bodyPost)
    {
        PostModel newPost = createNewPost();
        PostModel responseUpdatePost = restWrapper.usingPosts().updatePost(bodyPost, newPost.getId());

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_OK);
        softAssert.assertEquals(responseUpdatePost.getId(), newPost.getId());
        softAssert.assertEquals(responseUpdatePost.getImage(), bodyPost.getImage());
        softAssert.assertEquals(responseUpdatePost.getLink(), bodyPost.getLink());
        softAssert.assertEquals(responseUpdatePost.getLikes(), bodyPost.getLikes());
        softAssert.assertEquals(responseUpdatePost.getTags(), bodyPost.getTags());
        softAssert.assertEquals(responseUpdatePost.getText(), bodyPost.getText());
        softAssert.assertFalse(responseUpdatePost.getUpdatedDate().equals(responseUpdatePost.getPublishDate()));
        softAssert.assertAll();
    }

    @DataProvider(name = "invalid_text_data_update_post")
    public Object[][] createInvalidDataForUpdatePost() {
        CreateBodyPostModel invalidTextLessThanMinAccepted = new CreateBodyPostModel(
                RandomStringUtils.randomAlphanumeric(5),
                "https://loremflickr.com/320/240",
                "0",
                () -> {
                    ArrayList<String> tags = new ArrayList<>();
                    tags.add(RandomStringUtils.randomAlphanumeric(5));
                    return tags;
                },
                RandomStringUtils.randomAlphanumeric(15),
                createRandomUserInDb().getId()
        );

        CreateBodyPostModel invalidTextMoreThanMaxAccepted = new CreateBodyPostModel(
                RandomStringUtils.randomAlphanumeric(1001),
                "https://loremflickr.com/320/240",
                "0",
                () -> {
                    ArrayList<String> tags = new ArrayList<>();
                    tags.add(RandomStringUtils.randomAlphanumeric(5));
                    return tags;
                },
                RandomStringUtils.randomAlphanumeric(15),
                createRandomUserInDb().getId()
        );

        return new CreateBodyPostModel[][]{
                {invalidTextLessThanMinAccepted},
                {invalidTextMoreThanMaxAccepted},
        };
    }

    @Test(dataProvider = "invalid_text_data_update_post")
    void testUpdateInvalidTextUpdatePost(CreateBodyPostModel bodyPost)
    {
        PostModel newPost = createNewPost();
        PostModel responseUpdatePost = restWrapper.usingPosts().updatePost(bodyPost, newPost.getId());

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
        softAssert.assertAll();
    }

    @Test(dataProvider = "valid_data_update_post")
    void testUpdatePostValidDataInAllFieldsNoAppId(CreateBodyPostModel bodyPost)
    {
        PostModel newPost = createNewPost();
        ErrorPostModel responseUpdatePost = restWrapperNoId.usingPosts().updatePostError(bodyPost, newPost.getId());

        softAssert.assertEquals(restWrapperNoId.getStatusCode(), SC_FORBIDDEN);
        softAssert.assertEquals(responseUpdatePost.getError(), ERROR_MSG_MISSING_APP_ID);
        softAssert.assertAll();
    }

}
