package io.dummy_api.user;

import io.dummy_api.models.ErrorUserModel;
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
        restWrapper.usingUsers().deleteUser(id);
        ErrorUserModel response = restWrapper.usingUsers().deleteUserWithError(id);

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_NOT_FOUND);
        softAssert.assertEquals(response.getError(), ERROR_MSG_RSC_NOT_FOUND);
        softAssert.assertAll();
    }

    @Test(groups = {"user_test"})
    void testDeleteUserWithoutAppId() {
        String id = createUser(UserModel.generateRandomUser());
        ErrorUserModel response = restWrapperNoId.usingUsers().deleteUserWithError(id);

        softAssert.assertEquals(restWrapperNoId.getStatusCode(), SC_FORBIDDEN);
        softAssert.assertEquals(response.getError(), ERROR_MSG_MISSING_APP_ID);
        softAssert.assertAll();
    }

    @Test(groups = {"user_test"})
    void testDeleteInvalidUserId() {
        ErrorUserModel response = restWrapper.usingUsers().deleteUserWithError(
                RandomStringUtils.random(24, true, true));

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
        softAssert.assertEquals(response.getError(), ERROR_MSG_PARAMS_NOT_VALID);
        softAssert.assertAll();
    }

}
