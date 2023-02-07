package io.dummy_api.user;

import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.UserModel;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.apache.http.HttpStatus.*;


public class PostNewUserTest extends UserBaseClass {

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_valid_data", groups = {"user_test"})
    void testCreateUserWithValidFistNameLastNameAndEmail(HashMap<String, String> user_data) {
        Response response = restWrapper.sendRequest(HttpMethod.POST,
                "user/create", user_data, "");
        UserModel userRsp = restWrapper.convertResponseToModel(response, UserModel.class);
        getInfo(response);

        softAssert.assertEquals(response.statusCode(), SC_CREATED);
        softAssert.assertEquals(userRsp.getFirstName(), user_data.get("firstName"));
        softAssert.assertEquals(userRsp.getLastName(), user_data.get("lastName"));
        softAssert.assertEquals(userRsp.getEmail(), user_data.get("email"));
        softAssert.assertAll();
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema_create_user_successfully.json"));
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_user_data", groups = {"user_test"})
    void testCreateAUserWithInvalidData(HashMap<String, String> user) {
        Response response = restWrapper.sendRequest(HttpMethod.POST,
                "user/create", user, "");
        getInfo(response);
        ErrorModel errorRsp = restWrapper.convertResponseToModel(response, ErrorModel.class);

        softAssert.assertEquals(response.statusCode(), SC_BAD_REQUEST);
        softAssert.assertEquals(errorRsp.getError(), "BODY_NOT_VALID");
        verifyErrorDataMessageForRequiredFields(user, errorRsp);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_all_fields_valid_data", groups = {"user_test"})
    void testCreateUserWithValidDataInAllAvailableFields(HashMap<String, Object> user_data) {
        Response response = restWrapper.sendRequest(HttpMethod.POST,
                "user/create", user_data, "");
        getInfo(response);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        UserModel userModel = restWrapper.convertResponseToModel(response, UserModel.class);
        softAssert.assertEquals(userModel.getTitle(), user_data.get("title"));
        softAssert.assertEquals(userModel.getPhone(), user_data.get("phone"));
        softAssert.assertEquals(userModel.getGender(), user_data.get("gender"));
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_create_all_fields_invalid_data", groups = {"user_test"})
    void testCreateUserWithInvalidDataInAllAvailableFields(HashMap<String, Object> user_data) {
        Response response = restWrapper.sendRequest(HttpMethod.POST,
                "user/create", user_data, "");
        getInfo(response);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
        ErrorModel errorRsp = restWrapper.convertResponseToModel(response, ErrorModel.class);
        Assertions.assertThat(errorRsp.getError()).isEqualTo("BODY_NOT_VALID");
        verifyErrorDataNonRequiredFieldsUserCreate(user_data, errorRsp);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "create_user_xss_inj",
            groups = {"user_test"})
    void testCreateUserXssInjection(HashMap<String, String> user_data) {
        Response response = restWrapper.sendRequest(HttpMethod.POST,
                "user/create", user_data, "");
        getInfo(response);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_valid_data", groups = {"user_test"})
    void testCreateUserWithNoAppId(HashMap<String, String> user_data) {
        Response response = restWrapperNoId.sendRequest(HttpMethod.POST,
                "user/create", user_data, "");
        ErrorModel userRsp = restWrapperNoId.convertResponseToModel(response, ErrorModel.class);
        getInfo(response);

        softAssert.assertEquals(response.statusCode(), SC_FORBIDDEN);
        softAssert.assertEquals(userRsp.getError(), "APP_ID_MISSING");
        softAssert.assertAll();
    }
}
