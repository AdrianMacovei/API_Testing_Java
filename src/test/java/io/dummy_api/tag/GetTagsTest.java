//package io.dummy_api.tag;
//
//import io.dummy_api.models.CreateBodyPostModel;
//import io.dummy_api.models.ErrorModel;
//import io.dummy_api.models.PostModel;
//import io.dummy_api.post.PostBaseClass;
//import io.restassured.response.Response;
//import org.springframework.http.HttpMethod;
//import org.testng.annotations.Test;
//
//import static org.apache.http.HttpStatus.SC_FORBIDDEN;
//import static org.apache.http.HttpStatus.SC_OK;
//
//public class GetTagsTest extends PostBaseClass {
//
//    @Test
//    void testGetTags() {
//        Response responseNewPost = createNewPost(CreateBodyPostModel.generateRandomPostBody(createRandomUserInDb()));
//        PostModel postModel = restWrapper.convertResponseToModel(responseNewPost, PostModel.class);
//        String myTag = postModel.getTags().get(0);
//
//        Response response = restWrapper.sendRequest(HttpMethod.GET, "tag", "", "");
//        getInfo(response);
//        boolean isMyTagIn = false;
//        for (int i = 0; i < response.jsonPath().getList("data").size(); i++)
//        {
//            if (myTag.equals(response.jsonPath().getList("data").get(i)))
//            {
//                isMyTagIn = true;
//                break;
//            }
//        }
//        softAssert.assertEquals(response.statusCode(), SC_OK);
//        softAssert.assertTrue(isMyTagIn);
//        softAssert.assertAll();
//    }
//
//    @Test
//    void testGetTagsNoAppId() {
//        Response response = restWrapperNoId.sendRequest(HttpMethod.GET, "tag", "", "");
//        ErrorModel errorRsp = restWrapper.convertResponseToModel(response, ErrorModel.class);
//        getInfo(response);
//
//        softAssert.assertEquals(response.statusCode(), SC_FORBIDDEN);
//        softAssert.assertEquals(errorRsp.getError(), "APP_ID_MISSING");
//        softAssert.assertAll();
//    }
//
//}
