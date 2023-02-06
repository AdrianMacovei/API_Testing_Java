package io.dummy_api.post;

import io.dummy_api.models.CreateBodyPostModel;
import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.PostsCollection;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;
import static org.apache.http.HttpStatus.*;

public class GetAllPostsTest extends PostBaseClass {

    @Test
    void testGetAllPostsValidAppId() {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "post", "", "");
        PostsCollection postRsp = getRestWrapper().convertResponseToModel(response, PostsCollection.class);
        getInfo(response);

        softAssert.assertTrue(response.statusCode() == SC_OK);
        softAssert.assertEquals(postRsp.getData().size(), postRsp.getLimit());
        softAssert.assertAll();
    }

    @Test
    void testGetAllPostsInvalidAppId() {
        Response response = getRestWrapperNoId().sendRequest(HttpMethod.GET, "post", "", "");
        ErrorModel postRsp = getRestWrapper().convertResponseToModel(response, ErrorModel.class);
        getInfo(response);

        softAssert.assertTrue(response.statusCode() == SC_FORBIDDEN);
        softAssert.assertEquals(postRsp.getError(), "APP_ID_MISSING");
        softAssert.assertAll();
    }

    @Test
    void getAllCreatedPosts() {
        // create a new user in DB and create a new post using that user id
        createNewPost(CreateBodyPostModel.generateRandomPostBody(createRandomUserInDb()));
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "post?{params}", "", "created=50");
        PostsCollection postCollectionRsp = getRestWrapper().convertResponseToModel(response, PostsCollection.class);
        getInfo(response);

        softAssert.assertTrue(response.statusCode() == SC_OK);
        softAssert.assertEquals(postCollectionRsp.getTotal(), 1);
        softAssert.assertAll();
    }
}

