package io.dummy_api.post;

import io.dummy_api.models.CreateBodyPostModel;
import io.dummy_api.models.ErrorPostModel;
import io.dummy_api.models.PostModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.*;

public class CreatePostTest extends PostBaseClass {

    private final static String ERROR_LIKE_INVALID_FORMAT =
            "Cast to Number failed for value \"%s\" (type string) at path \"likes\"";

    @DataProvider(name = "valid_data_create_post")
    public Object[][] createValidDataCreatePost() {
        CreateBodyPostModel validDataForCreatePostMinAccepted = new CreateBodyPostModel(
                RandomStringUtils.randomAlphanumeric(6),
                "https://loremflickr.com/320/240",
                "0",
                () -> {
                        ArrayList<String> tags = new ArrayList<>();
                        tags.add("");
                        return tags;
                },
                RandomStringUtils.randomAlphanumeric(6),
                createRandomUserInDb().getId()
        );

        CreateBodyPostModel validDataForCreatePostMaxAccepted = new CreateBodyPostModel(
                RandomStringUtils.randomAlphanumeric(1000),
                RandomStringUtils.randomAlphanumeric(100),
                "1000",
                () -> {
                    ArrayList<String> tags = new ArrayList<>();
                    tags.add(RandomStringUtils.randomAlphanumeric(101));
                    tags.add(RandomStringUtils.randomAlphanumeric(1));
                    return tags;
                },
                RandomStringUtils.randomAlphanumeric(200),
                createRandomUserInDb().getId()
        );

        CreateBodyPostModel validDataForCreatePost = new CreateBodyPostModel(
                RandomStringUtils.randomAlphanumeric(500),
                "https://loremflickr.com/320/240",
                "100",
                () -> {
                    ArrayList<String> tags = new ArrayList<>();
                    tags.add(RandomStringUtils.randomAlphanumeric(101));
                    tags.add(RandomStringUtils.randomAlphanumeric(1));
                    return tags;
                },
                RandomStringUtils.randomAlphanumeric(100),
                createRandomUserInDb().getId()
        );

        return new CreateBodyPostModel[][]{
                {validDataForCreatePostMinAccepted},
                {validDataForCreatePostMaxAccepted},
                {validDataForCreatePost}
        };
    }
    @Test(dataProvider = "valid_data_create_post")
    void testCreatePostValidDataInRequiredFields(CreateBodyPostModel newPostBody)
    {
        PostModel response = restWrapper.usingPosts().createPost(newPostBody);

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_CREATED);
        softAssert.assertEquals(response.getOwner().getId(), newPostBody.getOwner());
        softAssert.assertEquals(response.getText(), newPostBody.getText());
        softAssert.assertEquals(response.getTags(), newPostBody.getTags());
        softAssert.assertEquals(response.getImage(), newPostBody.getImage());
        softAssert.assertEquals(response.getLink(), newPostBody.getLink());
        softAssert.assertEquals(response.getLikes(), newPostBody.getLikes());
        softAssert.assertAll();
    }

    @DataProvider(name = "invalid_data_create_post")
    public Object[][] createInvalidDataCreatePost() {
        CreateBodyPostModel validDataForCreatePostLessThanMinAccepted = new CreateBodyPostModel(
                RandomStringUtils.randomAlphanumeric(5),
                "https://loremflickr.com/320/240",
                "-1",
                ArrayList::new,
                RandomStringUtils.randomAlphanumeric(201),
                createRandomUserInDb().getId()
        );

        CreateBodyPostModel validDataForCreatePostMoreThanMaxAccepted = new CreateBodyPostModel(
                RandomStringUtils.randomAlphanumeric(1001),
                RandomStringUtils.randomAlphanumeric(1001),
                "10000",
                () -> {
                    ArrayList<String> tags = new ArrayList<>();
                    tags.add(RandomStringUtils.randomAlphanumeric(101));
                    tags.add(RandomStringUtils.randomAlphanumeric(1));
                    return tags;
                },
                RandomStringUtils.randomAlphanumeric(201),
                createRandomUserInDb().getId()
        );

        return new CreateBodyPostModel[][]{
                {validDataForCreatePostLessThanMinAccepted},
                {validDataForCreatePostMoreThanMaxAccepted},
        };
    }
    @Test(dataProvider = "invalid_data_create_post")
    void testCreatePostInvalidDataInRequiredFields(CreateBodyPostModel newPostBody)
    {
        PostModel response = restWrapper.usingPosts().createPost(newPostBody);
        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @DataProvider(name = "invalid_user_id_create_post")
    public Object[][] createInvalidUserIdCreatePost() {
        CreateBodyPostModel invalidUserIdCreatePost = new CreateBodyPostModel(
                RandomStringUtils.randomAlphanumeric(10),
                "https://loremflickr.com/320/240",
                "0",
                () -> {
                    ArrayList<String> tags = new ArrayList<>();
                    tags.add(RandomStringUtils.randomAlphanumeric(15));
                    return tags;
                },
                RandomStringUtils.randomAlphanumeric(150),
                RandomStringUtils.randomAlphanumeric(24)
        );
        CreateBodyPostModel nullUserIdCreatePost = new CreateBodyPostModel(
                RandomStringUtils.randomAlphanumeric(10),
                "https://loremflickr.com/320/240",
                "0",
                () -> {
                    ArrayList<String> tags = new ArrayList<>();
                    tags.add(RandomStringUtils.randomAlphanumeric(15));
                    return tags;
                },
                RandomStringUtils.randomAlphanumeric(150),
                null
        );

        return new CreateBodyPostModel[][]{
                {invalidUserIdCreatePost},
                {nullUserIdCreatePost}
        };
    }
    @Test(dataProvider = "invalid_user_id_create_post")
    void testCreatePostInvalidUserId(CreateBodyPostModel newPostBody)
    {
        ErrorPostModel response = restWrapper.usingPosts().createPostError(newPostBody);
        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
        Assertions.assertThat(response.getError()).isEqualTo(ERROR_MSG_BODY);
    }

    @DataProvider(name = "create-post_empty_fields")
    public Object[][] emptyFieldsCreatePost() {
        CreateBodyPostModel emptyText = new CreateBodyPostModel(
                null,
                "https://loremflickr.com/320/240",
                "0",
                () -> {
                    ArrayList<String> tags = new ArrayList<>();
                    tags.add(RandomStringUtils.randomAlphanumeric(15));
                    return tags;
                },
                RandomStringUtils.randomAlphanumeric(150),
                createRandomUserInDb().getId()
        );
        CreateBodyPostModel emptyImage = new CreateBodyPostModel(
                RandomStringUtils.randomAlphanumeric(10),
                null,
                "0",
                () -> {
                    ArrayList<String> tags = new ArrayList<>();
                    tags.add(RandomStringUtils.randomAlphanumeric(15));
                    return tags;
                },
                RandomStringUtils.randomAlphanumeric(150),
                createRandomUserInDb().getId()
        );

        CreateBodyPostModel emptyLikes = new CreateBodyPostModel(
                RandomStringUtils.randomAlphanumeric(10),
                "https://loremflickr.com/320/240",
                null,
                () -> {
                    ArrayList<String> tags = new ArrayList<>();
                    tags.add(RandomStringUtils.randomAlphanumeric(15));
                    return tags;
                },
                RandomStringUtils.randomAlphanumeric(150),
                createRandomUserInDb().getId()
        );

        CreateBodyPostModel nullAllFields = new CreateBodyPostModel();

        return new CreateBodyPostModel[][]{
                {emptyText},
                {emptyLikes},
                {emptyImage},
                {nullAllFields},

        };
    }

    @Test(dataProvider = "create-post_empty_fields")
    void testCreatePostEmptyFields(CreateBodyPostModel newPostBody)
    {
        ErrorPostModel response = restWrapper.usingPosts().createPostError(newPostBody);
        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
        Assertions.assertThat(response.getError()).isEqualTo(ERROR_MSG_BODY);
    }

    @DataProvider(name = "string_in_like_field")
    public Object[][] stringLikeFieldData() {
        CreateBodyPostModel randomStringLike = new CreateBodyPostModel(
                RandomStringUtils.randomAlphanumeric(20),
                "https://loremflickr.com/320/240",
                RandomStringUtils.randomAlphanumeric(5),
                () -> {
                    ArrayList<String> tags = new ArrayList<>();
                    tags.add(RandomStringUtils.randomAlphanumeric(15));
                    return tags;
                },
                RandomStringUtils.randomAlphanumeric(150),
                createRandomUserInDb().getId()
        );
        return new CreateBodyPostModel[][]{
                {randomStringLike}
        };
    }
    @Test(dataProvider = "string_in_like_field")
    void testCreatePostStringInLikeField(CreateBodyPostModel newPostBody)
    {
        ErrorPostModel response = restWrapper.usingPosts().createPostError(newPostBody);
        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
        Assertions.assertThat(response.getError()).isEqualTo(ERROR_MSG_BODY);
        Assertions.assertThat(response.getData().getLikes()).isEqualTo(String.format
                (ERROR_LIKE_INVALID_FORMAT, newPostBody.getLikes()));
    }

    @Test(dataProvider = "valid_data_create_post")
    void testCreatePostNoAppId(CreateBodyPostModel newPostBody)
    {
        ErrorPostModel response = restWrapperNoId.usingPosts().createPostError(newPostBody);
        Assertions.assertThat(restWrapperNoId.getStatusCode()).isEqualTo(SC_FORBIDDEN);
    }
}
