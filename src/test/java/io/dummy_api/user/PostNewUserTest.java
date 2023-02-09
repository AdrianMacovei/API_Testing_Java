package io.dummy_api.user;

import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.UserModel;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

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

        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_OK);
        softAssert.assertEquals(userRsp.getTitle(), userData.getTitle());
        softAssert.assertEquals(userRsp.getPhone(), userData.getPhone());
        softAssert.assertEquals(userRsp.getGender(), userData.getGender());
        softAssert.assertEquals(userRsp.getEmail(), userData.getEmail());
        softAssert.assertEquals(userRsp.getFirstName(), userData.getFirstName());
        softAssert.assertEquals(userRsp.getLastName(), userData.getLastName());
        softAssert.assertEquals(userRsp.getDateOfBirth(), userData.getDateOfBirth());
        softAssert.assertEquals(userRsp.getPicture(), userData.getPicture());
        softAssert.assertTrue(userRsp.getRegisterDate().contains(
                DataProviderClass.formatDate("yyyy-MM-dd'T'HH:mm", 0, 0)));
        softAssert.assertEquals(userRsp.getLocation().getCity(), userData.getLocation().getCity());
        softAssert.assertEquals(userRsp.getLocation().getCountry(), userData.getLocation().getCountry());
        softAssert.assertEquals(userRsp.getLocation().getStreet(), userData.getLocation().getStreet());
        softAssert.assertEquals(userRsp.getLocation().getState(), userData.getLocation().getState());
        softAssert.assertEquals(userRsp.getLocation().getTimezone(), userData.getLocation().getTimezone());
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_invalid_title_data", groups = {"user_test"})
    void testCreateUserWithInvalidTitle(UserModel userData) {
        ErrorModel errorRsp = restWrapper.usingUsers().createInvalidUser(userData);

        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
        softAssert.assertEquals(errorRsp.getError(), ERROR_MSG_BODY);
        softAssert.assertEquals(errorRsp.getData().getTitle(),
                String.format(ERROR_DATA_MESSAGE_WRONG_TITLE, userData.getTitle()));
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_invalid_gender_data", groups = {"user_test"})
    void testCreateUserWithInvalidGender(UserModel userData) {
        ErrorModel errorRsp = restWrapper.usingUsers().createInvalidUser(userData);

        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
        softAssert.assertEquals(errorRsp.getError(), ERROR_MSG_BODY);
        softAssert.assertEquals(errorRsp.getData().getGender(),
                String.format(ERROR_DATA_MESSAGE_WRONG_GENDER, userData.getGender()));
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_invalid_dateOfBirth_data", groups = {"user_test"})
    void testCreateUserWithInvalidDateOfBirthField(Object[] userDataProvider) {
        UserModel userData =(UserModel) userDataProvider[0];
        String birthErrorMsg = (String) userDataProvider[1];
        ErrorModel errorRsp = restWrapper.usingUsers().createInvalidUser(userData);

        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
        softAssert.assertEquals(errorRsp.getError(), ERROR_MSG_BODY);
        softAssert.assertTrue(errorRsp.getData().getDateOfBirth().contains(
                birthErrorMsg));
        System.out.println(errorRsp.getData().getDateOfBirth());
        System.out.println(birthErrorMsg);
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_invalid_phone_data", groups = {"user_test"})
    void testCreateUserWithInvalidPhoneField(Object[] userDataProvider) {
        UserModel userData =(UserModel) userDataProvider[0];
        String birthErrorMsg = (String) userDataProvider[1];
        ErrorModel errorRsp = restWrapper.usingUsers().createInvalidUser(userData);

        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
        softAssert.assertEquals(errorRsp.getError(), ERROR_MSG_BODY);
        softAssert.assertEquals(errorRsp.getData().getPhone(),
                birthErrorMsg);
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_invalid_location_data", groups = {"user_test"})
    void testCreateUserWithInvalidLocationField(UserModel userData) {
       restWrapper.usingUsers().createUser(userData);

        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_invalid_all_fields_data",
            groups = {"user_test"})
    void testCreateUserWithInvalidDataInAllAvailableFields(Object[] userDataProvider) {
        UserModel userBody = (UserModel) userDataProvider[0];
        ErrorModel errorRsp = restWrapper.usingUsers().createInvalidUser(userBody);
        UserModel data = errorRsp.getData();

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
        softAssert.assertEquals(data.getFirstName(), (String) userDataProvider[1]);
        softAssert.assertEquals(data.getLastName(), (String) userDataProvider[2]);
        softAssert.assertEquals(data.getEmail(), (String) userDataProvider[3]);
        softAssert.assertEquals(data.getDateOfBirth(), (String) userDataProvider[4]);
        softAssert.assertEquals(data.getPhone(), (String) userDataProvider[5]);
        softAssert.assertEquals(data.getTitle(), String.format(ERROR_DATA_MESSAGE_WRONG_TITLE, userBody.getTitle()));
        softAssert.assertEquals(data.getGender(), String.format(ERROR_DATA_MESSAGE_WRONG_GENDER, userBody.getGender()));
        softAssert.assertAll();
    }



    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "create_user_xss_inj",
            groups = {"user_test"})
    void testCreateUserXssInjection(UserModel userData) {
        restWrapper.usingUsers().createUser(userData);

        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_valid_data", groups = {"user_test"})
    void testCreateUserWithNoAppId(UserModel userData) {
        ErrorModel userRsp = restWrapperNoId.usingUsers().createInvalidUser(userData);

        softAssert.assertEquals(restWrapperNoId.getStatusCode(), SC_FORBIDDEN);
        softAssert.assertEquals(userRsp.getError(), "APP_ID_MISSING");
        softAssert.assertAll();
    }

}
