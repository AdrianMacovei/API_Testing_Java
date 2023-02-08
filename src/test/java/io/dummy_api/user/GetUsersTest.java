package io.dummy_api.user;

import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.UserModel;
import io.dummy_api.models.UsersCollection;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.*;

public class GetUsersTest extends UserBaseClass {

//    @Test(groups = {"user_test"})
//    void testGetUsersListWithValidAppId() {
//        Response response = restWrapper.sendRequest(HttpMethod.GET, "user", "", "");
//        UsersCollection user = restWrapper.convertResponseToModel(response, UsersCollection.class);
//        getInfo(response);
//
//        softAssert.assertEquals(user.getData().size(), 20);
//        softAssert.assertEquals(response.statusCode(), SC_OK);
//        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema_list.json"));
//        softAssert.assertAll();
//    }
//
//    @Test(groups = {"user_test"})
//    void testGetUsersWithInvalidAppId() {
//        Response response = restWrapperNoId.sendRequest(HttpMethod.GET, "user", "", "");
//        getInfo(response);
//        ErrorModel error = restWrapper.convertResponseToModel(response, ErrorModel.class);
//
//        softAssert.assertEquals(error.getError(), "APP_ID_MISSING");
//        softAssert.assertEquals(response.statusCode(), SC_FORBIDDEN);
//        softAssert.assertAll();
//    }
//
//    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "valid_limit_values", groups = {"user_test"})
//    void testGetUsersWithValidLimitParam(int limitValue) {
//        Response response = restWrapper.sendRequest(HttpMethod.GET, "user?limit={parameters}", "",
//                limitValue);
//        getInfo(response);
//        UsersCollection user = restWrapper.convertResponseToModel(response, UsersCollection.class);
//
//        softAssert.assertEquals(response.statusCode(), SC_OK);
//        softAssert.assertEquals(user.getData().size(), user.getLimit());
//        softAssert.assertAll();
//    }
//
//    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_limit_values", groups = {"user_test"})
//    void testGetUsersWithInvalidLimitParam(Object limitValue) {
//        Response response = restWrapper.sendRequest(HttpMethod.GET, "user?limit={parameters}", "",
//                limitValue);
//        getInfo(response);
//
//        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
//    }
//
//    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "valid_page_values", groups = {"user_test"})
//    void testGetUsersWithValidPageParam(int pageValue) {
//        Response response = restWrapper.sendRequest(HttpMethod.GET, "user?page={parameters}", "",
//                pageValue);
//        getInfo(response);
//        UsersCollection user = restWrapper.convertResponseToModel(response, UsersCollection.class);
//
//        softAssert.assertEquals(response.statusCode(), SC_OK);
//        softAssert.assertEquals(user.getPage(), pageValue);
//        softAssert.assertEquals(user.getData().size(), 20);
//        softAssert.assertAll();
//    }
//
//    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_page_values", groups = {"user_test"})
//    void testGetUsersWithInvalidPageParam(Object pageValue) {
//        Response response = restWrapper.sendRequest(HttpMethod.GET, "user?page={parameters}", "",
//                pageValue);
//        getInfo(response);
//
//        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
//    }
//
//    @Test(groups = {"user_test"})
//    void testGetUsersWithValidPageAndLimitParameters() {
//        Response response = restWrapper.sendRequest(HttpMethod.GET, "user?limit={parameters}&page=1", "",
//                6);
//        getInfo(response);
//        UsersCollection user = restWrapper.convertResponseToModel(response, UsersCollection.class);
//
//        softAssert.assertEquals(response.statusCode(), SC_OK);
//        softAssert.assertEquals(user.getLimit(), 6);
//        softAssert.assertEquals(user.getPage(), 1);
//        softAssert.assertEquals(user.getData().size(), 6);
//        softAssert.assertAll();
//
//    }
//
//    @Test(groups = {"user_test"})
//    void testGetUserWithValidId() {
//        UserModel newUser = UserModel.generateRandomUser();
//        String newUserId = createUser(newUser);
//        Response response = getUser(newUserId);
//        UserModel userRsp = restWrapper.convertResponseToModel(response, UserModel.class);
//        getInfo(response);
//
//        softAssert.assertEquals(response.statusCode(), SC_OK);
//        softAssert.assertEquals(userRsp.getId(), newUserId);
//        softAssert.assertEquals(userRsp.getFirstName(), newUser.getFirstName());
//        softAssert.assertEquals(userRsp.getLastName(), newUser.getLastName());
//        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath
//                ("schema_full_user_null_nonrequired.json"));
//        softAssert.assertAll();
//
//    }
//
//    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_ids", groups = {"user_test"})
//    void testGetUserWithInvalidId(String id) {
//        Response response = restWrapper.sendRequest(HttpMethod.GET, "user/{parameters}", "",
//                id);
//        ErrorModel userError = restWrapper.convertResponseToModel(response, ErrorModel.class);
//        getInfo(response);
//
//        if (id.length() == 24 && id.matches("\\d+")) {
//            softAssert.assertEquals(response.statusCode(), SC_NOT_FOUND);
//            softAssert.assertEquals(userError.getError(), "RESOURCE_NOT_FOUND");
//        } else {
//            softAssert.assertEquals(response.statusCode(), SC_BAD_REQUEST);
//            softAssert.assertEquals(userError.getError(), "PARAMS_NOT_VALID");
//        }
//        softAssert.assertAll();
//
//        /*
//            I tried to test get user with invalid id but the answer that I received in different that what I expected.
//            Base on my tests seems that only id with length 24 and who contains only numbers give an SC_NOT_FOUND with error
//            RESOURCES_NOT_FOUND, tried to find a pattern for creating id, but I didn't find one.
//         */
//
//    }
//
//    @Test(groups = {"user_test"})
//    void testGetCreatedUsers() {
//        String id1 = createUser(UserModel.generateRandomUser());
//        String id2 = createUser(UserModel.generateRandomUser());
//        Response response = restWrapper.sendRequest(HttpMethod.GET,
//                "user?created=2","", "");
//        UsersCollection users = restWrapper.convertResponseToModel(response, UsersCollection.class);
//        getInfo(response);
//
//        softAssert.assertEquals(response.statusCode(), SC_OK);
//        softAssert.assertEquals(users.getTotal(), 2);
//        softAssert.assertEquals(users.getData().size(), 2);
//        softAssert.assertEquals(users.getData().get(0).getId(), id1);
//        softAssert.assertEquals(users.getData().get(1).getId(), id2);
//        softAssert.assertAll();
//    }
}
