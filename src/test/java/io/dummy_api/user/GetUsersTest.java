package io.dummy_api.user;

import io.dummy_api.models.ErrorUserModel;
import io.dummy_api.models.UserModel;
import io.dummy_api.models.UsersCollection;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.*;

public class GetUsersTest extends UserBaseClass {

    @Test(groups = {"user_test"})
    void testGetUsersListWithValidAppId() {
        UsersCollection response = restWrapper.usingUsers().getUsers();

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_OK);
        softAssert.assertEquals(response.getData().size(), 20);
        softAssert.assertEquals(response.getLimit(), 20);
        softAssert.assertEquals(response.getPage(), 0);
        softAssert.assertAll();
    }

    @Test(groups = {"user_test"})
    void testGetUsersWithInvalidAppId() {
        ErrorUserModel response = restWrapperNoId.usingUsers().getUsersWithError();

        softAssert.assertEquals(restWrapperNoId.getStatusCode(), SC_FORBIDDEN);
        softAssert.assertEquals(response.getError(), "APP_ID_MISSING");
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "valid_limit_values", groups = {"user_test"})
    void testGetUsersWithValidLimitParam(int limitValue) {
        UsersCollection response = restWrapper.usingUsers().usingParams("limit=" + limitValue).getUsers();

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_OK);
        softAssert.assertEquals(response.getData().size(), limitValue);
        softAssert.assertEquals(response.getLimit(), limitValue);
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_limit_values", groups = {"user_test"})
    void testGetUsersWithInvalidLimitParam(Object limitValue) {
        restWrapper.usingUsers().usingParams(
                "limit=" + limitValue).getUsersWithError();

        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "valid_page_values", groups = {"user_test"})
    void testGetUsersWithValidPageParam(int pageValue) {
        UsersCollection response = restWrapper.usingUsers().usingParams(
                "page=" + pageValue).getUsers();

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_OK);
        softAssert.assertEquals(response.getPage(), pageValue);
        if (response.getTotal() >= response.getPage() * response.getLimit()) {
            softAssert.assertEquals(response.getData().size(), 20);
        } else {
            softAssert.assertEquals(response.getData().size(), 0);
        }
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_page_values", groups = {"user_test"})
    void testGetUsersWithInvalidPageParam(Object pageValue) {
        restWrapper.usingUsers().usingParams(
                "page=" + pageValue).getUsersWithError();

        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "valid_limit_and_page_values", groups = {"user_test"})
    void testGetUsersWithValidPageAndLimitParameters(Integer[] dataProvider) {
        UsersCollection response = restWrapper.usingUsers().usingParams("limit="+dataProvider[0],
                "page="+dataProvider[1]).getUsers();

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_OK);
        softAssert.assertEquals(response.getLimit(), (int) dataProvider[0]);
        softAssert.assertEquals(response.getPage(), (int) dataProvider[1]);
        if (response.getTotal() >= response.getPage() * response.getLimit()) {
            softAssert.assertEquals(response.getData().size(), (int) dataProvider[0]);
        } else {
            softAssert.assertEquals(response.getData().size(), 0);
        }
        softAssert.assertAll();
    }

    @Test(groups = {"user_test"})
    void testGetUserWithValidId() {
        UserModel newUser = UserModel.generateRandomUser();
        String newUserId = createUser(newUser);
        UserModel response = getUser(newUserId);

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_OK);
        softAssert.assertEquals(response.getId(), newUserId);
        softAssert.assertEquals(response.getFirstName(), newUser.getFirstName());
        softAssert.assertEquals(response.getLastName(), newUser.getLastName());
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_ids", groups = {"user_test"})
    void testGetUserWithInvalidId(String id) {
        ErrorUserModel response = restWrapper.usingUsers().getInvalidUser(id);

        if (id.length() == 24 && id.matches("\\d+")) {
            softAssert.assertEquals(restWrapper.getStatusCode(), SC_NOT_FOUND);
            softAssert.assertEquals(response.getError(), "RESOURCE_NOT_FOUND");
        } else {
            softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
            softAssert.assertEquals(response.getError(), "PARAMS_NOT_VALID");
        }
        softAssert.assertAll();

        /*
            I tried to test get user with invalid id but the answer that I received in different that what I expected.
            Base on my tests seems that only id with length 24 and who contains only numbers give an SC_NOT_FOUND with error
            RESOURCES_NOT_FOUND, tried to find a pattern for creating id, but I didn't find one.
         */

    }

    @Test(groups = {"user_test"})
    void testGetCreatedUsers() {
        String id1 = createUser(UserModel.generateRandomUser());
        String id2 = createUser(UserModel.generateRandomUser());
        UsersCollection response = restWrapper.usingUsers().getCreatedUsers();

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_OK);
        softAssert.assertEquals(response.getTotal(), 2);
        softAssert.assertEquals(response.getData().size(), 2);
        softAssert.assertEquals(response.getData().get(0).getId(), id1);
        softAssert.assertEquals(response.getData().get(1).getId(), id2);
        softAssert.assertAll();
    }
}
