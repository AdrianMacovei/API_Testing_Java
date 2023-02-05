package io.dummy_api.post;

import io.dummy_api.ApiBaseClass;
import io.dummy_api.models.PostModel;
import io.dummy_api.models.PostsCollection;
import io.dummy_api.models.UserModel;
import io.dummy_api.models.UsersCollection;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpMethod;

import java.util.Random;

public class PostBaseClass extends ApiBaseClass {

    protected UserModel getRandomUserId()
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET,
                "user", "", "");
        UsersCollection users = getRestWrapper().convertResponseToModel(response, UsersCollection.class);
        Random random = new Random();
        return users.getData().get(random.nextInt(19));
    }

    protected Response createNewPost(PostModel postData)
    {
        return getRestWrapper().sendRequest(HttpMethod.POST, "post/create", postData, "");
    }

    protected PostModel getRandomPost()
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "post", "", "");
        PostsCollection postModel = getRestWrapper().convertResponseToModel(response, PostsCollection.class);
        Random random = new Random();
        return postModel.getData().get(random.nextInt(19));
    }
}
