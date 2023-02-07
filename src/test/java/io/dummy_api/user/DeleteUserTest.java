package io.dummy_api.user;

import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.UserModel;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.*;


public class DeleteUserTest extends UserBaseClass {
    @Test(groups = {"user_test"})
    void testDeleteUser() {
        String id = createUser(UserModel.generateRandomUser());
        Response response = restWrapper.sendRequest(HttpMethod.DELETE, "user/{id}", "", id);
        getInfo(response);

        softAssert.assertEquals(response.statusCode(), SC_OK);
        softAssert.assertEquals(response.jsonPath().getString("id"), id);
        softAssert.assertAll();
    }

    @Test(groups = {"user_test"})
    void testDeleteAnAlreadyDeletedUser() {
        String id = createUser(UserModel.generateRandomUser());
        deleteUser(id);
        Response response = restWrapper.sendRequest(HttpMethod.DELETE, "user/{id}", "", id);
        ErrorModel errorRsp = restWrapper.convertResponseToModel(response, ErrorModel.class);
        getInfo(response);

        softAssert.assertEquals(response.statusCode(), SC_NOT_FOUND);
        softAssert.assertEquals(errorRsp.getError(), "RESOURCE_NOT_FOUND");
        softAssert.assertAll();
    }

    @Test(groups = {"user_test"})
    void testDeleteUserWithoutAppId() {
        String id = createUser(UserModel.generateRandomUser());
        Response response = restWrapperNoId.sendRequest(HttpMethod.DELETE, "user/{id}", "", id);
        ErrorModel errorRsp = restWrapper.convertResponseToModel(response, ErrorModel.class);
        getInfo(response);

        softAssert.assertEquals(response.statusCode(), SC_FORBIDDEN);
        softAssert.assertEquals(errorRsp.getError(), "APP_ID_MISSING");
        softAssert.assertAll();
    }

    @Test(groups = {"user_test"})
    void testDeleteInvalidUserId() {
        String id = RandomStringUtils.random(24, true, true);
        Response response = restWrapper.sendRequest(HttpMethod.DELETE, "user/{id}", "", id);
        ErrorModel errorRsp = restWrapper.convertResponseToModel(response, ErrorModel.class);
        getInfo(response);

        softAssert.assertEquals(response.statusCode(), SC_BAD_REQUEST);
        softAssert.assertEquals(errorRsp.getError(), "PARAMS_NOT_VALID");
        softAssert.assertAll();
    }

}
