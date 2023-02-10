package io.dummy_api.post;

import io.dummy_api.ApiBaseClass;
import io.dummy_api.models.*;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.testng.annotations.AfterMethod;

import java.util.Random;

public class PostBaseClass extends ApiBaseClass {

    protected UserModel getRandomUser()
    {
        UsersCollection response = restWrapper.usingUsers().getUsers();
        Random random = new Random();
        return response.getData().get(random.nextInt(19));
    }

    protected PostModel createNewPost()
    {
        return restWrapper.usingPosts().createPost(CreateBodyPostModel.generateRandomPostBody(createRandomUserInDb()));
    }

    protected PostModel getRandomPost()
    {
        PostsCollection response = restWrapper.usingPosts().getAllPosts();
        Random random = new Random();
        return response.getData().get(random.nextInt(19));
    }

    protected UserModel createRandomUserInDb(){
        UserModel newUser = UserModel.generateRandomUser();
        return restWrapper.usingUsers().createUser(newUser);
    }

    protected PostModel deletePost(String postId)
    {
       return restWrapper.usingPosts().deletePost(postId);
    }
//
//    protected PostsCollection getCreatedPosts()
//    {
//        Response response = restWrapper.sendRequest(HttpMethod.GET, "post?{params}", "", "created=15");
//        return restWrapper.convertResponseToModel(response, PostsCollection.class);
//    }
    @AfterMethod
    public void tearDownPost()
    {
        // delete created posts from DB
        PostsCollection newPostCollection = restWrapper.usingPosts().usingParams("created=1").getAllPosts();
        int dataSize =  newPostCollection.getData().size();
        if (dataSize > 0) {
            for (int i = 0; i < dataSize; i++) {
                PostModel post = newPostCollection.getData().get(i);
                deletePost(post.getId());
            }
        }
    }
}
