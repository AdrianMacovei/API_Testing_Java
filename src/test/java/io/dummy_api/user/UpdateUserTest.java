package io.dummy_api.user;

import io.dummy_api.ApiBaseClass;
import io.dummy_api.models.UserModel;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;
import java.util.HashMap;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;

public class UpdateUserTest extends ApiBaseClass {

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_valid_data", groups = {"user_test"})
    void testUpdateValidFirstNameAndLastName(HashMap<String, String> data) {
        String id = UserApiMethods.createUser(UserModel.generateRandomUser());
        Response response = getRestWrapper().sendRequest(HttpMethod.PUT,
                "user/{parameters}", data, id);
        UserModel userRsp = getRestWrapper().convertResponseToModel(response, UserModel.class);
        getInfo(response);

        softAssert.assertTrue(response.statusCode() == SC_OK);
        softAssert.assertTrue(data.get("firstName").equals(userRsp.getFirstName()));
        softAssert.assertTrue(data.get("lastName").equals(userRsp.getLastName()));
        softAssert.assertEquals(userRsp.getId(), id);
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_invalid_data", groups = {"user_test"})
    void testUpdateInvalidFirstNameAndLastName(HashMap<String, String> data) {
        String id = UserApiMethods.createUser(UserModel.generateRandomUser());
        Response response = getRestWrapper().sendRequest(HttpMethod.PUT,
                "user/{parameters}", data, id);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test(groups = {"user_test"})
    void testUpdateEmail() {
        HashMap<String, String> user_data = new HashMap<>();
        user_data.put("firstName", "Adrian");
        user_data.put("lastName", "Macovei");
        user_data.put("email", RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");

        String id = UserApiMethods.createUser(UserModel.generateRandomUser());
        Response response = getRestWrapper().sendRequest(HttpMethod.PUT,
                "user/{parameters}", user_data, id);
        UserModel userRsp = getRestWrapper().convertResponseToModel(response, UserModel.class);
        getInfo(response);

        softAssert.assertEquals(response.statusCode(), SC_BAD_REQUEST);
        softAssert.assertNotEquals(userRsp.getEmail(), user_data.get("email"));
        softAssert.assertAll();
    }
}
