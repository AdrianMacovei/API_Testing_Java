package io.dummy_api.user;

import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.UserModel;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.apache.http.HttpStatus.*;

public class UpdateUserTest extends UserBaseClass {

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_valid_data", groups = {"user_test"})
    void testUpdateValidFirstNameAndLastName(HashMap<String, String> data) {
        String id = createUser(UserModel.generateRandomUser());
        Response response = restWrapper.sendRequest(HttpMethod.PUT,
                "user/{parameters}", data, id);
        UserModel userRsp = restWrapper.convertResponseToModel(response, UserModel.class);
        getInfo(response);

        softAssert.assertTrue(response.statusCode() == SC_OK);
        softAssert.assertTrue(data.get("firstName").equals(userRsp.getFirstName()));
        softAssert.assertTrue(data.get("lastName").equals(userRsp.getLastName()));
        softAssert.assertEquals(userRsp.getId(), id);
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_invalid_data", groups = {"user_test"})
    void testUpdateInvalidFirstNameAndLastName(HashMap<String, String> data) {
        String id = createUser(UserModel.generateRandomUser());
        Response response = restWrapper.sendRequest(HttpMethod.PUT,
                "user/{parameters}", data, id);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_only_email", groups = {"user_test"})
    void testUpdateEmail(HashMap<String, String> user_data) {
        String id = createUser(UserModel.generateRandomUser());
        Response response = restWrapper.sendRequest(HttpMethod.PUT,
                "user/{parameters}", user_data, id);
        UserModel userRsp = restWrapper.convertResponseToModel(response, UserModel.class);
        getInfo(response);

        softAssert.assertEquals(response.statusCode(), SC_BAD_REQUEST);
        softAssert.assertNotEquals(userRsp.getEmail(), user_data.get("email"));
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_valid_data", groups = {"user_test"})
    void testUpdateNoAppId(HashMap<String, String> data) {
        String id = createUser(UserModel.generateRandomUser());
        Response response = restWrapperNoId.sendRequest(HttpMethod.PUT,
                "user/{parameters}", data, id);
        ErrorModel userRsp = restWrapperNoId.convertResponseToModel(response, ErrorModel.class);
        getInfo(response);

        softAssert.assertEquals(response.statusCode(), SC_FORBIDDEN);
        softAssert.assertEquals(userRsp.getError(), "APP_ID_MISSING");
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_user_xss_inj", groups = {"user_test"})
    void testUpdateWithXssInjection(HashMap<String, String> data) {
        String id = createUser(UserModel.generateRandomUser());
        Response response = restWrapper.sendRequest(HttpMethod.PUT,
                "user/{parameters}", data, id);
        getInfo(response);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
    }
}
