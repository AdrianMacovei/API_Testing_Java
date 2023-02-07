package io.dummy_api.requests;

import io.dummy_api.core.RestRequest;
import io.dummy_api.core.RestWrapper;
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

    public UsersCollection getUsers()
    {
        RestRequest request = RestRequest.simpleRequest(HttpMethod.GET, "user?{parameters", restWrapper.getParameters());
        return restWrapper.executeRequestAndProcessModel(UsersCollection.class, request);
    }

    public UserModel createUser(UserModel userModel)
    {
        RestRequest postRequest = RestRequest.requestWithBody(HttpMethod.POST, userModel,"user/create",
                restWrapper.getParameters());
        return restWrapper.executeRequestAndProcessModel(UserModel.class, postRequest);
    }

}
