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
        Response response = getRestWrapper().sendRequest(HttpMethod.GET,
                "user", "", "");
        UsersCollection users = getRestWrapper().convertResponseToModel(response, UsersCollection.class);
        Random random = new Random();
        return users.getData().get(random.nextInt(19));
    }

    protected Response createNewPost(CreateBodyPostModel postData)
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

    protected UserModel createRandomUserInDb(){
        UserModel newUser = UserModel.generateRandomUser();
        Response response = getRestWrapper().sendRequest(HttpMethod.POST,
                "user/create", newUser, "");
        return getRestWrapper().convertResponseToModel(response, UserModel.class);
    }

    protected Response deletePost(String postId)
    {
       return getRestWrapper().sendRequest(HttpMethod.DELETE, "post/{params}", "", postId);
    }

    protected PostsCollection getCreatedPosts()
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "post?{params}", "", "created=50");
        return getRestWrapper().convertResponseToModel(response, PostsCollection.class);
    }
    @AfterMethod
    public void tearDown()
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
