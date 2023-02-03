package io.dummy_api.user;

import io.dummy_api.ApiBaseClass;
import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.UserModel;
import io.dummy_api.models.UsersCollection;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.*;

public class GetUsersTest extends ApiBaseClass {

    @Test(groups = {"user_test"})
    void getUsersListWithValidAppId() {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "user", "", "");
        UsersCollection user = getRestWrapper().convertResponseToModel(response, UsersCollection.class);
        getInfo(response);

        softAssert.assertEquals(user.getData().size(), 20);
        softAssert.assertEquals(response.statusCode(), SC_OK);
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema_list.json"));
        softAssert.assertAll();
    }

    @Test(groups = {"user_test"})
    void getUsersWithInvalidAppId() {
        Response response = getRestWrapperNoId().sendRequest(HttpMethod.GET, "user", "", "");
        getInfo(response);
        ErrorModel error = getRestWrapper().convertResponseToModel(response, ErrorModel.class);

        softAssert.assertEquals(error.getError(), "APP_ID_MISSING");
        softAssert.assertEquals(response.statusCode(), SC_FORBIDDEN);
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "valid_limit_values", groups = {"user_test"})
    void getUsersWithValidLimitParam(int limitValue) {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "user?limit={parameters}", "",
                limitValue);
        getInfo(response);
        UsersCollection user = getRestWrapper().convertResponseToModel(response, UsersCollection.class);

        softAssert.assertEquals(response.statusCode(), SC_OK);
        softAssert.assertEquals(user.getData().size(), user.getLimit());
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_limit_values", groups = {"user_test"})
    void getUsersWithInvalidLimitParam(Object limitValue) {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "user?limit={parameters}", "",
                limitValue);
        getInfo(response);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "valid_page_values", groups = {"user_test"})
    void getUsersWithValidPageParam(int pageValue) {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "user?page={parameters}", "",
                pageValue);
        getInfo(response);
        UsersCollection user = getRestWrapper().convertResponseToModel(response, UsersCollection.class);

        softAssert.assertEquals(response.statusCode(), SC_OK);
        softAssert.assertEquals(user.getPage(), pageValue);
        softAssert.assertEquals(user.getData().size(), 20);
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_page_values", groups = {"user_test"})
    void getUsersWithInvalidPageParam(Object pageValue) {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "user?page={parameters}", "",
                pageValue);
        getInfo(response);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test(groups = {"user_test"})
    void getUsersWithValidPageAndLimitParameters() {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "user?limit={parameters}&page=1", "",
                6);
        getInfo(response);
        UsersCollection user = getRestWrapper().convertResponseToModel(response, UsersCollection.class);

        softAssert.assertEquals(response.statusCode(), SC_OK);
        softAssert.assertEquals(user.getLimit(), 6);
        softAssert.assertEquals(user.getPage(), 1);
        softAssert.assertEquals(user.getData().size(), 6);
        softAssert.assertAll();

    }

    @Test(groups = {"user_test"})
    void getUserWithValidId() {
        UserModel newUser = UserModel.generateRandomUser();
        String newUserId = UserApiMethods.createUser(newUser);
        Response response = UserApiMethods.getUser(newUserId);
        UserModel userRsp = getRestWrapper().convertResponseToModel(response, UserModel.class);
        getInfo(response);

        softAssert.assertEquals(response.statusCode(), SC_OK);
        softAssert.assertEquals(userRsp.getId(), newUserId);
        softAssert.assertEquals(userRsp.getFirstName(), newUser.getFirstName());
        softAssert.assertEquals(userRsp.getLastName(), newUser.getLastName());
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath
                ("schema_full_user_null_nonrequired.json"));
        softAssert.assertAll();

    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_ids", groups = {"user_test"})
    void getUserWithInvalidId(String id) {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "user/{parameters}", "",
                id);
        ErrorModel userError = getRestWrapper().convertResponseToModel(response, ErrorModel.class);
        getInfo(response);

        if (id.length() == 24) {
            softAssert.assertEquals(response.statusCode(), SC_NOT_FOUND);
            softAssert.assertEquals(userError.getError(), "RESOURCE_NOT_FOUND");
        } else {
            softAssert.assertEquals(response.statusCode(), SC_BAD_REQUEST);
            softAssert.assertEquals(userError.getError(), "PARAMS_NOT_VALID");
        }
        softAssert.assertAll();

        /*
            I tried to test get user with invalid id but the answer that I received in different that what I expected.
            Base on my tests seems that only id with length 24 and who contains only numbers give an SC_NOT_FOUND with error
            RESOURCES_NOT_FOUND, tried to find a pattern for creating id, but I didn't find one.
         */

    }

    @Test(groups = {"user_test"})
    void getCreatedUsers() {
        String id1 = UserApiMethods.createUser(UserModel.generateRandomUser());
        String id2 = UserApiMethods.createUser(UserModel.generateRandomUser());
        Response response = getRestWrapper().sendRequest(HttpMethod.GET,
                "user?created=2", "", "");
        UsersCollection users = getRestWrapper().convertResponseToModel(response, UsersCollection.class);
        getInfo(response);

        softAssert.assertEquals(response.statusCode(), SC_OK);
        softAssert.assertEquals(users.getTotal(), 2);
        softAssert.assertEquals(users.getData().size(), 2);
        softAssert.assertEquals(users.getData().get(0).getId(), id1);
        softAssert.assertEquals(users.getData().get(1).getId(), id2);
        softAssert.assertAll();
    }
}
