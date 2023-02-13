package io.dummy_api.requests;

import io.dummy_api.core.RestRequest;
import io.dummy_api.core.RestWrapper;
import io.dummy_api.models.ErrorUserModel;
import io.dummy_api.models.UserModel;
import io.dummy_api.models.UsersCollection;
import org.springframework.http.HttpMethod;

public class UsersRequests extends ModelRequest<UsersRequests>

{
    private final String PATH_USER_WITH_PARAMS = "user?{params}";
    private final String PATH_USER_WITH_ENDPOINT = "user/{params}";
    public UsersRequests(RestWrapper restWrapper) {
        super(restWrapper);
    }

    public UserModel getUser(String userId)
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, PATH_USER_WITH_ENDPOINT, userId);
        return restWrapper.executeRequestAndProcessModel(UserModel.class, request);
    }

    public ErrorUserModel getInvalidUser(String userId)
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, PATH_USER_WITH_ENDPOINT, userId);
        return restWrapper.executeRequestAndProcessModel(ErrorUserModel.class, request);
    }

    public UsersCollection getUsers()
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, PATH_USER_WITH_PARAMS, restWrapper.getParameters());
        return restWrapper.executeRequestAndProcessModel(UsersCollection.class, request);
    }

    public ErrorUserModel getUsersWithError()
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, PATH_USER_WITH_PARAMS, restWrapper.getParameters());
        return restWrapper.executeRequestAndProcessModel(ErrorUserModel.class, request);
    }

    public UserModel createUser(UserModel userModel)
    {
        RestRequest postRequest = RestRequest.requestWithBody(HttpMethod.POST,
                userModel,
                PATH_USER_WITH_ENDPOINT, "create");
        return restWrapper.executeRequestAndProcessModel(UserModel.class, postRequest);
    }

    public ErrorUserModel createInvalidUser(UserModel userModel)
    {
        RestRequest postRequest = RestRequest.requestWithBody(HttpMethod.POST,
                userModel,
                PATH_USER_WITH_ENDPOINT, "create");
        return restWrapper.executeRequestAndProcessModel(ErrorUserModel.class, postRequest);
    }

//    public UsersCollection getCreatedUsers()
//    {
//        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, PATH_USER_WITH_PARAMS, "created=1");
//        return restWrapper.executeRequestAndProcessModel(UsersCollection.class, request);
//    }

    public UserModel deleteUser(String userId)
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.DELETE, PATH_USER_WITH_ENDPOINT, userId);
        return restWrapper.executeRequestAndProcessModel(UserModel.class, request);
    }

    public ErrorUserModel deleteUserWithError(String userId)
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.DELETE, PATH_USER_WITH_ENDPOINT, userId);
        return restWrapper.executeRequestAndProcessModel(ErrorUserModel.class, request);
    }

    public UserModel updateUser(String userId, UserModel body){
        RestRequest updateRequest = RestRequest.requestWithBody(HttpMethod.PUT,
                body, PATH_USER_WITH_ENDPOINT, userId);
        return restWrapper.executeRequestAndProcessModel(UserModel.class, updateRequest);
    }

    public ErrorUserModel updateUserError(String userId, UserModel body){
        RestRequest updateRequest = RestRequest.requestWithBody(HttpMethod.PUT,
                body, PATH_USER_WITH_PARAMS, userId);
        return restWrapper.executeRequestAndProcessModel(ErrorUserModel.class, updateRequest);
    }


}
