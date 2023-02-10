package io.dummy_api.requests;

import io.dummy_api.core.RestRequest;
import io.dummy_api.core.RestWrapper;
import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.UserModel;
import io.dummy_api.models.UsersCollection;
import org.springframework.http.HttpMethod;

public class UsersRequests extends ModelRequest<UsersRequests>
{
    public UsersRequests(RestWrapper restWrapper) {
        super(restWrapper);
    }

    public UserModel getUser(String userId)
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, "user/{id}", userId);
        return restWrapper.executeRequestAndProcessModel(UserModel.class, request);
    }

    public ErrorModel getInvalidUser(String userId)
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, "user/{id}", userId);
        return restWrapper.executeRequestAndProcessModel(ErrorModel.class, request);
    }

    public UsersCollection getUsers()
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, "user?{parameters}", restWrapper.getParameters());
        return restWrapper.executeRequestAndProcessModel(UsersCollection.class, request);
    }

    public ErrorModel getUsersWithError()
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, "user?{parameters}", restWrapper.getParameters());
        return restWrapper.executeRequestAndProcessModel(ErrorModel.class, request);
    }

    public UserModel createUser(UserModel userModel)
    {
        RestRequest postRequest = RestRequest.requestWithBody(HttpMethod.POST,
                userModel,
                "user/create");
        return restWrapper.executeRequestAndProcessModel(UserModel.class, postRequest);
    }

    public ErrorModel createInvalidUser(UserModel userModel)
    {
        RestRequest postRequest = RestRequest.requestWithBody(HttpMethod.POST,
                userModel,
                "user/create");
        return restWrapper.executeRequestAndProcessModel(ErrorModel.class, postRequest);
    }

    public UsersCollection getCreatedUsers()
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, "user?{parameters}", "created=1");
        return restWrapper.executeRequestAndProcessModel(UsersCollection.class, request);
    }

    public UserModel deleteUser(String userId)
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.DELETE, "user/{parameters}", userId);
        return restWrapper.executeRequestAndProcessModel(UserModel.class, request);
    }

    public ErrorModel deleteUserWithError(String userId)
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.DELETE, "user/{parameters}", userId);
        return restWrapper.executeRequestAndProcessModel(ErrorModel.class, request);
    }

    public UserModel updateUser(String userId, UserModel body){
        RestRequest updateRequest = RestRequest.requestWithBody(HttpMethod.PUT,
                body, "user/" + userId);
        return restWrapper.executeRequestAndProcessModel(UserModel.class, updateRequest);
    }

    public ErrorModel updateUserError(String userId, UserModel body){
        RestRequest updateRequest = RestRequest.requestWithBody(HttpMethod.PUT,
                body, "user/" + userId);
        return restWrapper.executeRequestAndProcessModel(ErrorModel.class, updateRequest);
    }


}
