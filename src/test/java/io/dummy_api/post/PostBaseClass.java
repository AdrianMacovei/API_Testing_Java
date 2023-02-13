package io.dummy_api.post;

import io.dummy_api.ApiBaseClass;
import io.dummy_api.models.*;
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

    @AfterMethod
    public void tearDownPost()
    {
        System.out.println("*************************Tear Down Start************************");
        // delete created posts from DB
        PostsCollection newPostCollection = restWrapper.usingPosts().usingParams("created=1").getAllPosts();
        int dataSize =  newPostCollection.getData().size();
        if (dataSize > 0) {
            for (int i = 0; i < dataSize; i++) {
                PostModel post = newPostCollection.getData().get(i);
                deletePost(post.getId());
            }
        }
        // delete created users from DB
        UsersCollection usersCollectionRsp = restWrapper.usingUsers().usingParams("created=1").getUsers();
        if (usersCollectionRsp.getData().size() > 0)
        {
            for (int i = 0; i < usersCollectionRsp.getData().size(); i++) {
                restWrapper.usingUsers().deleteUser(usersCollectionRsp.getData().get(i).getId());
            }
        }
        System.out.println("*************************Tear Down Finish************************");
    }
}
