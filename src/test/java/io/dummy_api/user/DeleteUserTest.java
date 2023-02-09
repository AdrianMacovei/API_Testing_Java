package io.dummy_api.user;

import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.UserModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.*;


public class DeleteUserTest extends UserBaseClass {
    @Test(groups = {"user_test"})
    void testDeleteUser() {
        String id = createUser(UserModel.generateRandomUser());
        UserModel response = restWrapper.usingUsers().deleteUser(id);

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_OK);
        softAssert.assertEquals(response.getId(), id);
        softAssert.assertAll();
    }

    @Test(groups = {"user_test"})
    void testDeleteAnAlreadyDeletedUser() {
        String id = createUser(UserModel.generateRandomUser());
        deleteUser(id);
        ErrorModel response = restWrapper.usingUsers().deleteUserWithError(id);

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_NOT_FOUND);
        softAssert.assertEquals(response.getError(), "RESOURCE_NOT_FOUND");
        softAssert.assertAll();
    }

    @Test(groups = {"user_test"})
    void testDeleteUserWithoutAppId() {
        String id = createUser(UserModel.generateRandomUser());
        ErrorModel response = restWrapperNoId.usingUsers().deleteUserWithError(id);

        softAssert.assertEquals(restWrapperNoId.getStatusCode(), SC_FORBIDDEN);
        softAssert.assertEquals(response.getError(), "APP_ID_MISSING");
        softAssert.assertAll();
    }

    @Test(groups = {"user_test"})
    void testDeleteInvalidUserId() {
        ErrorModel response = restWrapper.usingUsers().deleteUserWithError(
                RandomStringUtils.random(24, true, true));

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
        softAssert.assertEquals(response.getError(), "PARAMS_NOT_VALID");
        softAssert.assertAll();
    }

}
