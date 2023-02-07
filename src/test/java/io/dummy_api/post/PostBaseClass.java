package io.dummy_api.post;

import io.dummy_api.ApiBaseClass;
import io.dummy_api.models.*;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.testng.annotations.AfterMethod;

import java.util.Random;

public class PostBaseClass extends ApiBaseClass {

    protected UserModel getRandomUserId()
    {
        Response response = restWrapper.sendRequest(HttpMethod.GET,
                "user", "", "");
        UsersCollection users = restWrapper.convertResponseToModel(response, UsersCollection.class);
        Random random = new Random();
        return users.getData().get(random.nextInt(19));
    }

    protected Response createNewPost(CreateBodyPostModel postData)
    {
        return restWrapper.sendRequest(HttpMethod.POST, "post/create", postData, "");
    }

    protected PostModel getRandomPost()
    {
        Response response = restWrapper.sendRequest(HttpMethod.GET, "post", "", "");
        PostsCollection postModel = restWrapper.convertResponseToModel(response, PostsCollection.class);
        Random random = new Random();
        return postModel.getData().get(random.nextInt(19));
    }

    protected UserModel createRandomUserInDb(){
        UserModel newUser = UserModel.generateRandomUser();
        Response response = restWrapper.sendRequest(HttpMethod.POST,
                "user/create", newUser, "");
        return restWrapper.convertResponseToModel(response, UserModel.class);
    }

    protected Response deletePost(String postId)
    {
       return restWrapper.sendRequest(HttpMethod.DELETE, "post/{params}", "", postId);
    }

    protected PostsCollection getCreatedPosts()
    {
        Response response = restWrapper.sendRequest(HttpMethod.GET, "post?{params}", "", "created=15");
        return restWrapper.convertResponseToModel(response, PostsCollection.class);
    }
    @AfterMethod
    public void tearDownPost()
    {
        // delete created posts from DB
        PostsCollection newPostCollection = getCreatedPosts();
        int total = newPostCollection.getTotal();
        for (int i=0; i<total; i++)
        {
            PostModel post = newPostCollection.getData().get(i);
            deletePost(post.getId());
        }
    }
}
