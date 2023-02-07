package io.dummy_api.post;

import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.PostsCollection;
import io.dummy_api.models.UserModel;
import io.dummy_api.user.DataProviderClass;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.*;

public class GetPostsByUserTest extends PostBaseClass{

    @Test
    void testGetUserPosts()
    {
        UserModel user = getRandomUserId();
        Response response = restWrapper.sendRequest(HttpMethod.GET,
                "user/{params}/post",
                "", user.getId());
        getInfo(response);
        PostsCollection postRsp = restWrapper.convertResponseToModel(response, PostsCollection.class);

        softAssert.assertEquals(response.statusCode(), SC_OK);
        for (int index = 0; index < postRsp.getData().size(); index++) {
            softAssert.assertEquals(postRsp.getData().get(index).getOwner().getId(), user.getId());
            softAssert.assertEquals(postRsp.getData().get(index).getOwner().getFirstName(), user.getFirstName());
            softAssert.assertEquals(postRsp.getData().get(index).getOwner().getLastName(), user.getLastName());
        }
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_ids")
    void testGetUserPostsInvalidUserId(String userId)
    {
        Response response = restWrapper.sendRequest(HttpMethod.GET,
                "user/{params}/post",
                "", userId);
        ErrorModel postError = restWrapper.convertResponseToModel(response, ErrorModel.class);
        getInfo(response);

        if (userId.length() == 24 && userId.matches("\\d+")) {
            softAssert.assertEquals(response.statusCode(), SC_NOT_FOUND);
            softAssert.assertEquals(postError.getError(), "RESOURCE_NOT_FOUND");
        } else {
            softAssert.assertEquals(response.statusCode(), SC_BAD_REQUEST);
            softAssert.assertEquals(postError.getError(), "PARAMS_NOT_VALID");
        }
        softAssert.assertAll();

    }


}
