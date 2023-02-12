package io.dummy_api.post;

import io.dummy_api.models.CreateBodyPostModel;
import io.dummy_api.models.PostModel;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;

public class UpdatePostTest extends PostBaseClass {

    @DataProvider(name = "valid_data_update_post")
    public Object[][] createValidDataUpdatePost() {
        CreateBodyPostModel validDataForUpdatePost = new CreateBodyPostModel(
                RandomStringUtils.random(15, true, true),
                "https://loremflickr.com/320/240",
                "0",
                () -> {
                    ArrayList<String> tags = new ArrayList<>();
                    tags.add(RandomStringUtils.random(5, true, true));
                    return tags;
                },
                RandomStringUtils.random(15, true, true),
                createRandomUserInDb().getId()
        );

        return new CreateBodyPostModel[][]{
                {validDataForUpdatePost},
        };
    }

    @Test
    void testUpdatePostValidDataInAllFields(CreateBodyPostModel bodyPost)
    {
        PostModel newPost = createNewPost();
        PostModel responseUpdatePost = restWrapper.usingPosts().updatePost(bodyPost, newPost.getId());
    }
}
