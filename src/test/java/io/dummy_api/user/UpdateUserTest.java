package io.dummy_api.user;

import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.UserModel;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.*;

public class UpdateUserTest extends UserBaseClass {

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_valid_first_and_last_name_data",
            groups = {"user_test"})
    void testUpdateValidFirstNameAndLastName(UserModel data) {
        UserModel newUserForUpdate = UserModel.generateRandomUser();
        String id = createUser(newUserForUpdate);
        UserModel response = restWrapper.usingUsers().updateUser(id, data);

        softAssert.assertTrue(restWrapper.getStatusCode() == SC_OK);
        softAssert.assertEquals(response.getFirstName(), data.getFirstName());
        softAssert.assertEquals(response.getLastName(), data.getLastName());
        softAssert.assertEquals(response.getId(), id);
        softAssert.assertEquals(response.getEmail(), newUserForUpdate.getEmail());
        softAssert.assertEquals(response.getGender(), null);
        softAssert.assertEquals(response.getTitle(), null);
        softAssert.assertEquals(response.getPhone(), null);
        softAssert.assertEquals(response.getPicture(), null);
        softAssert.assertEquals(response.getDateOfBirth(), null);
        softAssert.assertTrue(response.getUpdatedDate().contains(
                DataProviderClass.formatDate("yyyy-MM-dd'T'HH:mm", 0, 0)));
        softAssert.assertAll();
    }


    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_all_fields_valid_data",
            groups = {"user_test"})
    void testUpdateValidDataInAllFields(UserModel data) {
        UserModel newUserForUpdate = UserModel.generateRandomUser();
        String id = createUser(newUserForUpdate);
        UserModel response = restWrapper.usingUsers().updateUser(id, data);

        softAssert.assertTrue(restWrapper.getStatusCode() == SC_OK);
        softAssert.assertEquals(response.getFirstName(), data.getFirstName());
        softAssert.assertEquals(response.getLastName(), data.getLastName());
        softAssert.assertEquals(response.getId(), id);
        softAssert.assertEquals(response.getEmail(), newUserForUpdate.getEmail());
        softAssert.assertEquals(response.getGender(), data.getGender());
        softAssert.assertEquals(response.getTitle(), data.getTitle());
        softAssert.assertEquals(response.getPhone(), data.getPhone());
        softAssert.assertEquals(response.getPicture(), data.getPicture());
        softAssert.assertEquals(response.getDateOfBirth(), data.getDateOfBirth());
        softAssert.assertTrue(response.getUpdatedDate().contains(
                DataProviderClass.formatDate("yyyy-MM-dd'T'HH:mm", 0, 0)));
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_all_fields_invalid_data",
            groups = {"user_test"})
    void testUpdateInvalidDataInAllFields(UserModel data) {
        UserModel newUserForUpdate = UserModel.generateRandomUser();
        String id = createUser(newUserForUpdate);
        UserModel response = restWrapper.usingUsers().updateUser(id, data);

        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
        softAssert.assertNotEquals(response.getFirstName(), data.getFirstName());
        softAssert.assertNotEquals(response.getLastName(), data.getLastName());
        softAssert.assertEquals(response.getId(), id);
        softAssert.assertNotEquals(response.getGender(), data.getGender());
        softAssert.assertNotEquals(response.getTitle(), data.getTitle());
        softAssert.assertNotEquals(response.getPhone(), data.getPhone());
        softAssert.assertNotEquals(response.getPicture(), data.getPicture());
        softAssert.assertNotEquals(response.getDateOfBirth(), data.getDateOfBirth());
        softAssert.assertTrue(response.getUpdatedDate().contains(
                DataProviderClass.formatDate("yyyy-MM-dd'T'HH:mm", 0, 0)));
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_invalid_first_and_last_name_data",
            groups = {"user_test"})
    void testUpdateInvalidFirstNameAndLastName(UserModel data) {
        String id = createUser(UserModel.generateRandomUser());
        restWrapper.usingUsers().updateUser(id, data);

        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_only_email", groups = {"user_test"})
    void testUpdateEmail(UserModel data) {
        String id = createUser(UserModel.generateRandomUser());
        UserModel response = restWrapper.usingUsers().updateUser(id, data);

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
        softAssert.assertNotEquals(response.getEmail(), data.getEmail());
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_id_of_user", groups = {"user_test"})
    void testUpdateTryUpdateId(UserModel data) {
        String id = createUser(UserModel.generateRandomUser());
        UserModel response = restWrapper.usingUsers().updateUser(id, data);

        softAssert.assertEquals(restWrapper.getStatusCode(), SC_BAD_REQUEST);
        softAssert.assertNotEquals(response.getId(), data.getId());
        softAssert.assertAll();
    }


    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_all_fields_valid_data",
            groups = {"user_test"})
    void testUpdateNoAppId(UserModel data) {
        String id = createUser(UserModel.generateRandomUser());
        ErrorModel response = restWrapperNoId.usingUsers().updateUserError(id, data);

        softAssert.assertEquals(restWrapperNoId.getStatusCode(), SC_FORBIDDEN);
        softAssert.assertEquals(response.getError(), "APP_ID_MISSING");
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_user_first_and_last_name_xss_injection",
            groups = {"user_test"})
    void testUpdateWithXssInjection(UserModel data) {
        String id = createUser(UserModel.generateRandomUser());
        restWrapper.usingUsers().updateUser(id, data);

        Assertions.assertThat(restWrapper.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
    }
}
