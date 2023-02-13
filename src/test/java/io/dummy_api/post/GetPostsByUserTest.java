package io.dummy_api.post;

import io.dummy_api.models.ErrorPostModel;
import io.dummy_api.models.PostsCollection;
import io.dummy_api.models.UserModel;
import io.dummy_api.user.DataProviderClass;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.*;

public class GetPostsByUserTest extends PostBaseClass{

    @Test
    void testGetUserPosts()
    {
        UserModel user = getRandomUser();
        PostsCollection response = restWrapper.usingPosts().getUserPosts(user.getId());

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_OK);
        for (int index = 0; index < response.getData().size(); index++) {
            softAssert.assertEquals(response.getData().get(index).getOwner().getId(), user.getId());
            softAssert.assertEquals(response.getData().get(index).getOwner().getFirstName(), user.getFirstName());
            softAssert.assertEquals(response.getData().get(index).getOwner().getLastName(), user.getLastName());
        }
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_ids")
    void testGetUserPostsInvalidUserId(String userId)
    {
        ErrorPostModel response = restWrapper.usingPosts().getUserPostsError(userId);

        if (userId.length() == 24 && userId.matches("\\d+")) {
            softAssert.assertEquals(restWrapper.getStatusCode(), SC_NOT_FOUND);
            softAssert.assertEquals(response.getError(), "RESOURCE_NOT_FOUND");
        } else {
            softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
            softAssert.assertEquals(response.getError(), "PARAMS_NOT_VALID");
        }
        softAssert.assertAll();
    }
}
