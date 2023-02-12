package io.dummy_api.post;

import io.dummy_api.models.ErrorPostModel;
import io.dummy_api.models.PostsCollection;
import io.dummy_api.user.DataProviderClass;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import static org.apache.http.HttpStatus.*;

public class GetAllPostsTest extends PostBaseClass {

    @Test
    void testGetAllPostsValidAppId() {
        PostsCollection response = restWrapper.usingPosts().getAllPosts();

        softAssert.assertTrue(restWrapper.getStatusCode() == SC_OK);
        softAssert.assertEquals(response.getData().size(), response.getLimit());
        softAssert.assertAll();
    }

    @Test
    void testGetAllPostsInvalidAppId() {
        ErrorPostModel response = restWrapperNoId.usingPosts().getAllPostsError();

        softAssert.assertTrue(restWrapperNoId.getStatusCode() == SC_FORBIDDEN);
        softAssert.assertEquals(response.getError(), "APP_ID_MISSING");
        softAssert.assertAll();
    }

    @Test
    void getAllCreatedPosts() {
        // create a new user in DB and create a new post using that user id
        createNewPost();
        createNewPost();
        PostsCollection response = restWrapper.usingPosts().usingParams("created=1").getAllPosts();

        softAssert.assertTrue(restWrapper.getStatusCode() == SC_OK);
        softAssert.assertEquals(response.getTotal(), 2);
        softAssert.assertEquals(response.getData().size(), 2);
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "valid_limit_values")
    void getPostsWithValidLimitParam(int limitValue) {
        PostsCollection response = restWrapper.usingPosts().usingParams("limit="+limitValue).getAllPosts();

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_OK);
        softAssert.assertEquals(response.getLimit(), limitValue);
        softAssert.assertEquals(response.getData().size(), response.getLimit());
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "valid_page_values")
    void getPostsWithValidPageParam(int pageValue) {
        PostsCollection response = restWrapper.usingPosts().usingParams("page="+pageValue).getAllPosts();

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_OK);
        softAssert.assertEquals(response.getPage(), pageValue);
        if (response.getTotal() >= response.getPage() * response.getLimit()) {
            softAssert.assertEquals(response.getData().size(), response.getLimit());
        } else {
            softAssert.assertEquals(response.getData().size(), 0);
        }
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_page_values")
    void getPostsWithInvalidPageParam(Object pageValue) {
        ErrorPostModel resposne = restWrapper.usingPosts().usingParams(
                "page=" + pageValue).getAllPostsError();

        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_limit_values")
    void getPostsWithInvalidLimitParam(Object limitValue) {
        ErrorPostModel resposne = restWrapper.usingPosts().usingParams(
                "limit=" + limitValue).getAllPostsError();

        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "valid_limit_and_page_values", groups = {"user_test"})
    void testGetPostsWithValidPageAndLimitParameters(Integer[] dataProvider) {
        PostsCollection response = restWrapper.usingPosts().usingParams("limit="+dataProvider[0],
                "page="+dataProvider[1]).getAllPosts();

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_OK);
        softAssert.assertEquals(response.getLimit(), (int) dataProvider[0]);
        softAssert.assertEquals(response.getPage(), (int) dataProvider[1]);
        if (response.getTotal() >= response.getPage() * response.getLimit()) {
            softAssert.assertEquals(response.getData().size(), (int) dataProvider[0]);
        } else {
            softAssert.assertEquals(response.getData().size(), 0);
        }
        softAssert.assertAll();
    }
}

