package io.dummy_api.user;

import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.Location;
import io.dummy_api.models.UserModel;
import io.dummy_api.models.UsersCollection;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.apache.http.HttpStatus.*;


public class PostNewUserTest extends UserBaseClass {

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_valid_data", groups = {"user_test"})
    void testCreateUserWithValidFistNameLastNameAndEmail(UserModel userData) {
        UserModel userRsp = restWrapper.usingUsers().createUser(userData);

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_CREATED);
        softAssert.assertEquals(userRsp.getFirstName(), userData.getFirstName());
        softAssert.assertEquals(userRsp.getLastName(), userData.getLastName());
        softAssert.assertEquals(userRsp.getEmail(), userData.getEmail());
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_firstName_data", groups = {"user_test"})
    void testCreateUserWithInvalidFirstName(Object[] userDataProvider)
    {
        UserModel userDataBody = (UserModel) userDataProvider[0];
        String errorMessage = (String) userDataProvider[1];
        ErrorModel userErrorRsp = restWrapper.usingUsers().createInvalidUser(userDataBody);

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
        softAssert.assertEquals(userErrorRsp.getError(), ERROR_MSG_BODY);
        softAssert.assertEquals(userErrorRsp.getData().getFirstName(), errorMessage);
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_lastName_data", groups = {"user_test"})
    void testCreateUserWithInvalidLastName(Object[] userDataProvider)
    {
        UserModel userDataBody = (UserModel) userDataProvider[0];
        String errorMessage = (String) userDataProvider[1];
        ErrorModel userErrorRsp = restWrapper.usingUsers().createInvalidUser(userDataBody);

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
        softAssert.assertEquals(userErrorRsp.getError(), ERROR_MSG_BODY);
        softAssert.assertEquals(userErrorRsp.getData().getLastName(), errorMessage);
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_email_data", groups = {"user_test"})
    void testCreateUserWithInvalidEmail(Object[] userDataProvider)
    {
        UserModel userDataBody = (UserModel) userDataProvider[0];
        String errorMessage = (String) userDataProvider[1];
        ErrorModel userErrorRsp = restWrapper.usingUsers().createInvalidUser(userDataBody);

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
        softAssert.assertEquals(userErrorRsp.getError(), ERROR_MSG_BODY);
        softAssert.assertEquals(userErrorRsp.getData().getEmail(), errorMessage);
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_first_and_last_name_data",
            groups = {"user_test"})
    void testCreateUserWithInvalidFirstNameAndLastName(Object[] userDataProvider)
    {
        UserModel userDataBody = (UserModel) userDataProvider[0];
        String errorMessageFirstName = (String) userDataProvider[1];
        String errorMessageLastName = (String) userDataProvider[2];
        ErrorModel userErrorRsp = restWrapper.usingUsers().createInvalidUser(userDataBody);

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
        softAssert.assertEquals(userErrorRsp.getError(), ERROR_MSG_BODY);
        softAssert.assertEquals(userErrorRsp.getData().getFirstName(), errorMessageFirstName);
        softAssert.assertEquals(userErrorRsp.getData().getLastName(), errorMessageLastName);
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_first_name_and_email_data",
            groups = {"user_test"})
    void testCreateUserWithInvalidFirstNameAndEmail(Object[] userDataProvider)
    {
        UserModel userDataBody = (UserModel) userDataProvider[0];
        String errorMessageFirstName = (String) userDataProvider[1];
        String errorMessageEmail = (String) userDataProvider[2];
        ErrorModel userErrorRsp = restWrapper.usingUsers().createInvalidUser(userDataBody);

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
        softAssert.assertEquals(userErrorRsp.getError(), ERROR_MSG_BODY);
        softAssert.assertEquals(userErrorRsp.getData().getFirstName(), errorMessageFirstName);
        softAssert.assertEquals(userErrorRsp.getData().getEmail(), errorMessageEmail);
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_last_name_and_email_data",
            groups = {"user_test"})
    void testCreateUserWithInvalidLastNameAndEmail(Object[] userDataProvider)
    {
        UserModel userDataBody = (UserModel) userDataProvider[0];
        String errorMessageLastName = (String) userDataProvider[1];
        String errorMessageEmail = (String) userDataProvider[2];
        ErrorModel userErrorRsp = restWrapper.usingUsers().createInvalidUser(userDataBody);

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
        softAssert.assertEquals(userErrorRsp.getError(), ERROR_MSG_BODY);
        softAssert.assertEquals(userErrorRsp.getData().getLastName(), errorMessageLastName);
        softAssert.assertEquals(userErrorRsp.getData().getEmail(), errorMessageEmail);
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_first_last_and_email_data",
            groups = {"user_test"})
    void testCreateUserWithInvalidFirstNameLastNameAndEmail(Object[] userDataProvider)
    {
        UserModel userDataBody = (UserModel) userDataProvider[0];
        String errorMessageFirstName = (String) userDataProvider[1];
        String errorMessageLastName = (String) userDataProvider[2];
        String errorMessageEmail = (String) userDataProvider[3];
        ErrorModel userErrorRsp = restWrapper.usingUsers().createInvalidUser(userDataBody);

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
        softAssert.assertEquals(userErrorRsp.getError(), ERROR_MSG_BODY);
        softAssert.assertEquals(userErrorRsp.getData().getFirstName(), errorMessageFirstName);
        softAssert.assertEquals(userErrorRsp.getData().getLastName(), errorMessageLastName);
        softAssert.assertEquals(userErrorRsp.getData().getEmail(), errorMessageEmail);
        softAssert.assertAll();
    }


    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_all_fields_valid_data", groups = {"user_test"})
    void testCreateUserWithValidDataInAllAvailableFields(UserModel userData) {
        UserModel userRsp = restWrapper.usingUsers().createUser(userData);

        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_OK);;
        softAssert.assertEquals(userRsp.getTitle(), userData.getTitle());
        softAssert.assertEquals(userRsp.getPhone(), userData.getPhone());
        softAssert.assertEquals(userRsp.getGender(), userData.getGender());
        softAssert.assertEquals(userRsp.getEmail(), userData.getEmail());
        softAssert.assertEquals(userRsp.getFirstName(), userData.getFirstName());
        softAssert.assertEquals(userRsp.getLastName(), userData.getLastName());
        softAssert.assertEquals(userRsp.getDateOfBirth(), userData.getDateOfBirth());
        softAssert.assertEquals(userRsp.getPicture(), userData.getPicture());
        softAssert.assertEquals(userRsp.getRegisterDate(), LocalDateTime.now().toString());
        softAssert.assertEquals(userRsp.getLocation().getCity(), userData.getLocation().getCity());
        softAssert.assertEquals(userRsp.getLocation().getCountry(), userData.getLocation().getCountry());
        softAssert.assertEquals(userRsp.getLocation().getStreet(), userData.getLocation().getStreet());
        softAssert.assertEquals(userRsp.getLocation().getState(), userData.getLocation().getState());
        softAssert.assertEquals(userRsp.getLocation().getTimezone(), userData.getLocation().getTimezone());
        softAssert.assertAll();
    }
//
//    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_create_all_fields_invalid_data", groups = {"user_test"})
//    void testCreateUserWithInvalidDataInAllAvailableFields(HashMap<String, Object> user_data) {
//        Response response = restWrapper.sendRequest(HttpMethod.POST,
//                "user/create", user_data, "");
//        getInfo(response);
//
//        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
//        ErrorModel errorRsp = restWrapper.convertResponseToModel(response, ErrorModel.class);
//        Assertions.assertThat(errorRsp.getError()).isEqualTo("BODY_NOT_VALID");
//        verifyErrorDataNonRequiredFieldsUserCreate(user_data, errorRsp);
//    }
//
//    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "create_user_xss_inj",
//            groups = {"user_test"})
//    void testCreateUserXssInjection(HashMap<String, String> user_data) {
//        Response response = restWrapper.sendRequest(HttpMethod.POST,
//                "user/create", user_data, "");
//        getInfo(response);
//
//        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
//    }
//
//    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_valid_data", groups = {"user_test"})
//    void testCreateUserWithNoAppId(HashMap<String, String> user_data) {
//        Response response = restWrapperNoId.sendRequest(HttpMethod.POST,
//                "user/create", user_data, "");
//        ErrorModel userRsp = restWrapperNoId.convertResponseToModel(response, ErrorModel.class);
//        getInfo(response);
//
//        softAssert.assertEquals(response.statusCode(), SC_FORBIDDEN);
//        softAssert.assertEquals(userRsp.getError(), "APP_ID_MISSING");
//        softAssert.assertAll();
//    }
}
