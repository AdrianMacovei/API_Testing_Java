package io.dummy_api.post;

import io.dummy_api.models.CreateBodyPostModel;
import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.PostsCollection;
import io.dummy_api.user.DataProviderClass;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;
import static org.apache.http.HttpStatus.*;

public class GetAllPostsTest extends PostBaseClass {

    @Test
    void testGetAllPostsValidAppId() {
        Response response = restWrapper.sendRequest(HttpMethod.GET, "post", "", "");
        PostsCollection postRsp = restWrapper.convertResponseToModel(response, PostsCollection.class);
        getInfo(response);

        softAssert.assertTrue(response.statusCode() == SC_OK);
        softAssert.assertEquals(postRsp.getData().size(), postRsp.getLimit());
        softAssert.assertAll();
    }

    @Test
    void testGetAllPostsInvalidAppId() {
        Response response = restWrapperNoId.sendRequest(HttpMethod.GET, "post", "", "");
        ErrorModel postRsp = restWrapper.convertResponseToModel(response, ErrorModel.class);
        getInfo(response);

        softAssert.assertTrue(response.statusCode() == SC_FORBIDDEN);
        softAssert.assertEquals(postRsp.getError(), "APP_ID_MISSING");
        softAssert.assertAll();
    }

    @Test
    void getAllCreatedPosts() {
        // create a new user in DB and create a new post using that user id
        createNewPost(CreateBodyPostModel.generateRandomPostBody(createRandomUserInDb()));
        createNewPost(CreateBodyPostModel.generateRandomPostBody(createRandomUserInDb()));
        Response response = restWrapper.sendRequest(HttpMethod.GET, "post?{params}", "", "created=5");
        PostsCollection postCollectionRsp = restWrapper.convertResponseToModel(response, PostsCollection.class);
        getInfo(response);

        softAssert.assertTrue(response.statusCode() == SC_OK);
        softAssert.assertEquals(postCollectionRsp.getTotal(), 2);
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "valid_limit_values")
    void getPostsWithValidLimitParam(int limitValue) {
        Response response = restWrapper.sendRequest(HttpMethod.GET, "post?limit={params}", "", limitValue);
        getInfo(response);
        PostsCollection postsCollection = restWrapper.convertResponseToModel(response, PostsCollection.class);

        softAssert.assertEquals(response.statusCode(), SC_OK);
        softAssert.assertEquals(postsCollection.getData().size(), postsCollection.getLimit());
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "valid_page_values")
    void getPostsWithValidPageParam(int pageValue) {
        Response response = restWrapper.sendRequest(HttpMethod.GET, "post?page={params}", "", pageValue);
        getInfo(response);
        PostsCollection postsCollection = restWrapper.convertResponseToModel(response, PostsCollection.class);

        softAssert.assertEquals(response.statusCode(), SC_OK);
        softAssert.assertEquals(postsCollection.getPage(), pageValue);
        softAssert.assertEquals(postsCollection.getData().size(), 20);
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_page_values")
    void getPostsWithInvalidPageParam(Object pageValue) {
        Response response = restWrapper.sendRequest(HttpMethod.GET, "post?page={params}", "", pageValue);
        getInfo(response);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_limit_values")
    void getPostsWithInvalidLimitParam(Object limitValue) {
        Response response = restWrapper.sendRequest(HttpMethod.GET, "post?limit={params}", "", limitValue);
        getInfo(response);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
    }
}

