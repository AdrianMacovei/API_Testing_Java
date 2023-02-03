package io.dummy_api.user;

import io.dummy_api.ApiBaseClass;
import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.UserModel;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.*;


public class DeleteUserTest extends ApiBaseClass {
    @Test(groups = {"user_test"})
    void testDeleteUser() {
        String id = UserApiMethods.createUser(UserModel.generateRandomUser());
        Response response = getRestWrapper().sendRequest(HttpMethod.DELETE, "user/{id}", "", id);
        getInfo(response);


        softAssert.assertEquals(response.statusCode(), SC_OK);
        softAssert.assertEquals(response.jsonPath().getString("id"), id);
        softAssert.assertAll();
    }

    @Test(groups = {"user_test"})
    void testDeleteAnAlreadyDeletedUser() {
        String id = UserApiMethods.createUser(UserModel.generateRandomUser());
        UserApiMethods.deleteUser(id);
        Response response = getRestWrapper().sendRequest(HttpMethod.DELETE, "user/{id}", "", id);
        ErrorModel errorRsp = getRestWrapper().convertResponseToModel(response, ErrorModel.class);
        getInfo(response);

        softAssert.assertEquals(response.statusCode(), SC_NOT_FOUND);
        softAssert.assertEquals(errorRsp.getError(), "RESOURCE_NOT_FOUND");
        softAssert.assertAll();
    }

    @Test(groups = {"user_test"})
    void testDeleteUserWithoutAppId() {
        String id = UserApiMethods.createUser(UserModel.generateRandomUser());
        Response response = getRestWrapperNoId().sendRequest(HttpMethod.DELETE, "user/{id}", "", id);
        ErrorModel errorRsp = getRestWrapper().convertResponseToModel(response, ErrorModel.class);
        getInfo(response);

        softAssert.assertEquals(response.statusCode(), SC_FORBIDDEN);
        softAssert.assertEquals(errorRsp.getError(), "APP_ID_MISSING");
        softAssert.assertAll();
    }
}
