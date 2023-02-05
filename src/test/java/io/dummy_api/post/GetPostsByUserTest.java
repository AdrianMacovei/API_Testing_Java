package io.dummy_api.post;

import io.dummy_api.models.PostsCollection;
import io.dummy_api.models.UserModel;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class GetPostsByUserTest extends PostBaseClass{

    @Test
    void testGetUserPosts()
    {
        UserModel user = getRandomUserId();
        Response response = getRestWrapper().sendRequest(HttpMethod.GET,
                "user/{params}/post",
                "", user.getId());
        getInfo(response);
        PostsCollection postRsp = getRestWrapper().convertResponseToModel(response, PostsCollection.class);

        softAssert.assertEquals(response.statusCode(), SC_OK);
        for (int index = 0; index < postRsp.getData().size(); index++) {
            softAssert.assertEquals(postRsp.getData().get(index).getOwner().getId(), user.getId());
            softAssert.assertEquals(postRsp.getData().get(index).getOwner().getFirstName(), user.getFirstName());
            softAssert.assertEquals(postRsp.getData().get(index).getOwner().getLastName(), user.getLastName());
        }
        softAssert.assertAll();
    }

}
