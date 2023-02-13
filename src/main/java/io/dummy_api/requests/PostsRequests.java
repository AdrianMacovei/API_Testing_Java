package io.dummy_api.requests;

import io.dummy_api.core.RestRequest;
import io.dummy_api.core.RestWrapper;
import io.dummy_api.models.*;
import org.springframework.http.HttpMethod;

public class PostsRequests extends ModelRequest<PostsRequests>{

    public PostsRequests(RestWrapper restWrapper) {
        super(restWrapper);
    }

    private final String PATH_POST_WITH_PARAMS = "post?{params}";
    private final String PATH_POST_WITH_ENDPOINT = "post/{params}";
    private final String PATH_POSTS_OF_USER = "user/{params}/post";

    public PostsCollection getAllPosts()
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, PATH_POST_WITH_PARAMS,
                restWrapper.getParameters());
        return restWrapper.executeRequestAndProcessModel(PostsCollection.class, request);
    }

    public PostModel getPostById(String id)
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, PATH_POST_WITH_ENDPOINT, id);
        return restWrapper.executeRequestAndProcessModel(PostModel.class, request);
    }

    public ErrorPostModel getPostByIdError(String id)
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, PATH_POST_WITH_ENDPOINT, id);
        return restWrapper.executeRequestAndProcessModel(ErrorPostModel.class, request);
    }

    public PostsCollection getUserPosts(String userId)
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, PATH_POSTS_OF_USER, userId);
        return restWrapper.executeRequestAndProcessModel(PostsCollection.class, request);
    }
    public ErrorPostModel getUserPostsError(String userId)
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, PATH_POSTS_OF_USER, userId);
        return restWrapper.executeRequestAndProcessModel(ErrorPostModel.class, request);
    }


    public ErrorPostModel getAllPostsError()
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, PATH_POST_WITH_PARAMS,
                restWrapper.getParameters());
        return restWrapper.executeRequestAndProcessModel(ErrorPostModel.class, request);
    }

    public PostModel createPost(CreateBodyPostModel bodyPostModel){
        RestRequest request = RestRequest.requestWithBody(HttpMethod.POST, bodyPostModel, PATH_POST_WITH_ENDPOINT,
                "create");
        return restWrapper.executeRequestAndProcessModel(PostModel.class, request);
    }

    public ErrorPostModel createPostError(CreateBodyPostModel bodyPostModel){
        RestRequest request = RestRequest.requestWithBody(HttpMethod.POST, bodyPostModel, PATH_POST_WITH_ENDPOINT,
                "create");
        return restWrapper.executeRequestAndProcessModel(ErrorPostModel.class, request);
    }

    public PostModel updatePost(CreateBodyPostModel bodyPostModel, String postId){
        RestRequest request = RestRequest.requestWithBody(HttpMethod.PUT, bodyPostModel, PATH_POST_WITH_ENDPOINT,
                postId);
        return restWrapper.executeRequestAndProcessModel(PostModel.class, request);
    }

    public ErrorPostModel updatePostError(CreateBodyPostModel bodyPostModel, String postId){
        RestRequest request = RestRequest.requestWithBody(HttpMethod.PUT, bodyPostModel, PATH_POST_WITH_ENDPOINT,
                postId);
        return restWrapper.executeRequestAndProcessModel(ErrorPostModel.class, request);
    }

    public PostModel deletePost(String postId){
        RestRequest request = RestRequest.simpleRequest(HttpMethod.DELETE,PATH_POST_WITH_ENDPOINT, postId);
        return restWrapper.executeRequestAndProcessModel(PostModel.class, request);
    }

    public ErrorPostModel deletePostError(String postId){
        RestRequest request = RestRequest.simpleRequest(HttpMethod.DELETE,PATH_POST_WITH_ENDPOINT, postId);
        return restWrapper.executeRequestAndProcessModel(ErrorPostModel.class, request);
    }
}


