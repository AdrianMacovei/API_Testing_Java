package io.dummy_api.post;

import io.dummy_api.models.CreateBodyPostModel;
import io.dummy_api.models.PostModel;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;

public class UpdatePostTest extends PostBaseClass {

//    @Test(dataProviderClass = DataProviderForPost.class, dataProvider = "valid_update_data")
//    void testUpdatePost(PostModel modelData) {
//        Response responseNewPost = createNewPost(CreateBodyPostModel.generateRandomPostBody(createRandomUserInDb()));
//        PostModel postModelInitial = restWrapper.convertResponseToModel(responseNewPost, PostModel.class);
//        PostModel postModel = restWrapper.convertResponseToModel(responseNewPost, PostModel.class);
//        getInfo(responseNewPost);
//        postModel.setText("Hello World!");
//        postModel.setLink("someLink");
//        ArrayList<String> updateTags = new ArrayList<String>();
//        updateTags.add("new"); updateTags.add("tags");
//        postModel.setTags(updateTags);
//        postModel.setLikes(20);
//        postModel.setImage("some image");
//        Response response = restWrapper.sendRequest(HttpMethod.PUT,
//            "post/{params}", postModel
//            , postModel.getId());
//        PostModel postModelRsp = restWrapper.convertResponseToModel(response, PostModel.class);
//        getInfo(response);

//        softAssert.assertEquals(response.statusCode(), SC_OK);
//        softAssert.assertNotEquals(postModelRsp.getText(), postModelInitial.getText());
//        softAssert.assertNotEquals(postModelRsp.getLink(), postModelInitial.getLink());
//        softAssert.assertEquals(postModelRsp.getImage(), postModel.getImage());
//        softAssert.assertEquals(postModelRsp.getLikes(), postModel.getLikes());
//        softAssert.assertEquals(postModelRsp.getTags(), postModel.getTags());
//        softAssert.assertAll();
//    }
}
