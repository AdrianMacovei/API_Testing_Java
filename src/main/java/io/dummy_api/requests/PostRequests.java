package io.dummy_api.requests;

import io.dummy_api.core.RestRequest;
import io.dummy_api.core.RestWrapper;
import io.dummy_api.models.CreateBodyPostModel;
import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.PostModel;
import io.dummy_api.models.PostsCollection;
import org.springframework.http.HttpMethod;

public class PostRequests extends ModelRequest<PostRequests>{

    public PostRequests(RestWrapper restWrapper) {
        super(restWrapper);
    }

    public PostModel getPost(String postId) {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, "post/{id}", postId);
        return restWrapper.executeRequestAndProcessModel(PostModel.class, request);
    }

    public PostsCollection getAllPosts()
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, "post?{params}",
                restWrapper.getParameters());
        return restWrapper.executeRequestAndProcessModel(PostsCollection.class, request);
    }

    public PostModel getPostById(String id)
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, "post/" + id);
        return restWrapper.executeRequestAndProcessModel(PostModel.class, request);
    }

    public ErrorModel getPostByIdError(String id)
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, "post/" + id);
        return restWrapper.executeRequestAndProcessModel(ErrorModel.class, request);
    }

    public ErrorModel getAllPostsError()
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, "post?{params}",
                restWrapper.getParameters());
        return restWrapper.executeRequestAndProcessModel(ErrorModel.class, request);
    }

    public PostModel createPost(CreateBodyPostModel bodyPostModel){
        RestRequest request = RestRequest.requestWithBody(HttpMethod.POST, bodyPostModel, "post/create");
        return restWrapper.executeRequestAndProcessModel(PostModel.class, request);
    }

    public PostModel updatePost(CreateBodyPostModel bodyPostModel, String postId){
        RestRequest request = RestRequest.requestWithBody(HttpMethod.PUT, bodyPostModel, "post/" + postId);
        return restWrapper.executeRequestAndProcessModel(PostModel.class, request);
    }

    public PostModel deletePost(String postId){
        RestRequest request = RestRequest.simpleRequest(HttpMethod.DELETE,"post/" + postId);
        return restWrapper.executeRequestAndProcessModel(PostModel.class, request);
    }
}


