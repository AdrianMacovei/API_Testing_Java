import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

import java.util.HashMap;

public class DataProviderClass {

    @DataProvider(name = "invalid_ids")
    public static Object[][] createDataIds() {
        return new Object[][]{
                {RandomStringUtils.random(23, true, true).toLowerCase()},
                {RandomStringUtils.random(24, true, true).toLowerCase()},
                {RandomStringUtils.random(24, false, true)},
                {RandomStringUtils.random(24, true, false).toLowerCase()},
                {RandomStringUtils.random(25, true, true).toLowerCase()},
                {"63d233c888cdfd33faa635a4"},
                {"63d23" + RandomStringUtils.random(14, true, true).toLowerCase() + "635a4"},
                {RandomStringUtils.random(24, false, true)}
        };
    }

    @DataProvider(name = "page_values")
    public static Object[][] createDataPageParam() {
        return new Integer[][]{
                {-1}, {0}, {999}, {1000}
        };
    }

    @DataProvider(name = "limit_values")
    public static Object[][] createDataLimitParam() {
        return new Integer[][]{
                {4}, {5}, {50}, {51}
        };
    }

    @DataProvider(name = "invalid_user_data")
    public static Object[][] createInvalidUser() {
        HashMap<String, String> userWithoutFistName = new HashMap<>();
        userWithoutFistName.put("lastName", "Macovei");
        userWithoutFistName.put("email", "adrianmacovei1998@gmail.com");

        HashMap <String, String>  userWithoutLastName = new HashMap<>();
        userWithoutLastName.put("firstName", "Adrian");
        userWithoutLastName.put("email", "adrianmacovei1998@gmail.com");

        HashMap <String, String>  userWithoutEmail = new HashMap<>();
        userWithoutEmail.put("firstName", "Adrian");
        userWithoutEmail.put("lastName", "Macovei");

        HashMap <String, String>  userWithoutEmailAndFirstName = new HashMap<>();
        userWithoutEmailAndFirstName.put("lastName", "Macovei");

        HashMap <String, String>  userWithoutEmailAndLastName = new HashMap<>();
        userWithoutEmailAndLastName.put("firstName", "Adrian");

        HashMap <String, String>  userWithoutFirstAndLastName = new HashMap<>();
        userWithoutFirstAndLastName.put("email", "adrianmacovei1999@gmail.com");

        HashMap <String, String>  userWithEmptyFields = new HashMap<>();

        HashMap <String, String>  userWithInvalidEmail = new HashMap<>();
        userWithInvalidEmail.put("firstName", "Adrian");
        userWithInvalidEmail.put("lastName", "Macovei");
        userWithInvalidEmail.put("email", RandomStringUtils.random(10, true, true).toLowerCase());

        HashMap <String, String>  userWithOneCharFirstName = new HashMap<>();
        userWithOneCharFirstName.put("firstName", "A");
        userWithOneCharFirstName.put("lastName", "Macovei");
        userWithOneCharFirstName.put("email", RandomStringUtils.random(6, true, true).
                toLowerCase() + "@gmail.com");

        HashMap <String, String>  userWithFiftyOneCharsFirstName = new HashMap<>();
        userWithFiftyOneCharsFirstName.put("firstName", RandomStringUtils.random(51, true, false));
        userWithFiftyOneCharsFirstName.put("lastName", "Macovei");
        userWithFiftyOneCharsFirstName.put("email", RandomStringUtils.random(6, true, true).
                toLowerCase() + "@gmail.com");

        HashMap <String, String>  userWithOneCharLastName = new HashMap<>();
        userWithOneCharLastName.put("firstName", "Adrian");
        userWithOneCharLastName.put("lastName", "M");
        userWithOneCharLastName.put("email", RandomStringUtils.random(6, true, true).
                toLowerCase() + "@gmail.com");

        HashMap <String, String>  userWithFiftyOneCharsLastName = new HashMap<>();
        userWithFiftyOneCharsLastName.put("firstName", "Adrian");
        userWithFiftyOneCharsLastName.put("lastName", RandomStringUtils.random(51, true, false));
        userWithFiftyOneCharsLastName.put("email", RandomStringUtils.random(6, true, true).
                toLowerCase() + "@gmail.com");


        return new HashMap[][]{
                {userWithoutFistName},
                {userWithoutLastName},
                {userWithoutEmail},
                {userWithoutEmailAndFirstName},
                {userWithoutEmailAndLastName},
                {userWithoutFirstAndLastName},
                {userWithEmptyFields},
                {userWithInvalidEmail},
                {userWithOneCharFirstName},
                {userWithFiftyOneCharsFirstName},
                {userWithOneCharLastName},
                {userWithFiftyOneCharsLastName}
        };
    }

    @DataProvider(name = "user_valid_data")
    public static Object[][] createValidDataForUser() {
        HashMap <String, String>  userWithTwoCharactersFirstAndLastName = new HashMap<>();
        userWithTwoCharactersFirstAndLastName.put("firstName",
                RandomStringUtils.random(2, true, false));
        userWithTwoCharactersFirstAndLastName.put("lastName",
                RandomStringUtils.random(2, true, false));
        userWithTwoCharactersFirstAndLastName.put("email",
                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");


        HashMap <String, String>  userWithThirtyCharactersInFirstAndLastName = new HashMap<>();
        userWithThirtyCharactersInFirstAndLastName.put("firstName",
                RandomStringUtils.random(30, true, false));
        userWithThirtyCharactersInFirstAndLastName.put("lastName",
                RandomStringUtils.random(30, true, false));
        userWithThirtyCharactersInFirstAndLastName.put("email",
                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");

        HashMap <String, String>  userWithValidDataInField = new HashMap<>();
        userWithValidDataInField.put("firstName",
                RandomStringUtils.random(6, true, false));
        userWithValidDataInField.put("lastName",
                RandomStringUtils.random(6, true, false));
        userWithValidDataInField.put("email",
                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");

        return new HashMap[][]{
                {userWithThirtyCharactersInFirstAndLastName},
                {userWithValidDataInField},
                {userWithTwoCharactersFirstAndLastName}
        };
    }

    @DataProvider(name = "update_valid_data")
    public static Object[][] createValidDataForUpdate() {

        HashMap<String, String> twoCharactersFirstAndLastName = new HashMap<>();
        twoCharactersFirstAndLastName.put("firstName",
                RandomStringUtils.random(2, true, false));
        twoCharactersFirstAndLastName.put("lastName",
                RandomStringUtils.random(2, true, false));

        HashMap<String, String> thirtyCharactersFirstAndLastName = new HashMap<>();
        thirtyCharactersFirstAndLastName.put("firstName",
                RandomStringUtils.random(30, true, false));
        thirtyCharactersFirstAndLastName.put("lastName",
                RandomStringUtils.random(30, true, false));

        return new HashMap[][]{
                {twoCharactersFirstAndLastName},
                {thirtyCharactersFirstAndLastName},
        };
    }

    @DataProvider(name = "update_invalid_data")
    public static Object[][] createInvalidDataForUpdate() {

        HashMap<String, String> oneCharactersFirstAndLastName = new HashMap<>();
        oneCharactersFirstAndLastName.put("firstName",
                RandomStringUtils.random(1, true, false));
        oneCharactersFirstAndLastName.put("lastName",
                RandomStringUtils.random(1, true, false));

        HashMap<String, String> thirtyOneCharactersFirstAndLastName = new HashMap<>();
        thirtyOneCharactersFirstAndLastName.put("firstName",
                RandomStringUtils.random(51, true, false));
        thirtyOneCharactersFirstAndLastName.put("lastName",
                RandomStringUtils.random(51, true, false));

        return new HashMap[][]{
                {thirtyOneCharactersFirstAndLastName},
                {oneCharactersFirstAndLastName},
        };
    }

    @DataProvider(name = "user_all_fields_valid_data")
    public static Object[][] createValidDataForAllFields() {
        HashMap <String, Object>  validDataAllFieldsMinRange = new HashMap<>();
        validDataAllFieldsMinRange.put("title", "mr");
        validDataAllFieldsMinRange.put("firstName", "Ma");
        validDataAllFieldsMinRange.put("lastName", "Ad");
        validDataAllFieldsMinRange.put("gender", "male");
        validDataAllFieldsMinRange.put("email",
                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
        validDataAllFieldsMinRange.put("dateOfBirth", "1/1/1900");
        validDataAllFieldsMinRange.put("phone", RandomStringUtils.random(10, false, true));
        validDataAllFieldsMinRange.put("picture", "https://unsplash.com/photos/rDEOVtE7vOs");
        HashMap <String, String>  location1 = new HashMap<>();
        location1.put("street", RandomStringUtils.random(5, true, true));
        location1.put("city", RandomStringUtils.random(2, true, false));
        location1.put("state", RandomStringUtils.random(2, true, false));
        location1.put("country", RandomStringUtils.random(2, true, false));
        location1.put("timezone", "-9:00");
        validDataAllFieldsMinRange.put("location", location1);

        HashMap <String, Object>  validDataAllFieldsMaxRange = new HashMap<>();
        validDataAllFieldsMaxRange.put("title", "ms");
        validDataAllFieldsMaxRange.put("firstName", RandomStringUtils.random(30, true, false));
        validDataAllFieldsMaxRange.put("lastName", RandomStringUtils.random(30, true, false));
        validDataAllFieldsMaxRange.put("gender", "female");
        validDataAllFieldsMaxRange.put("email",
                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
        validDataAllFieldsMaxRange.put("dateOfBirth", "01/30/2023");
        validDataAllFieldsMaxRange.put("phone", RandomStringUtils.random(20, false, true));
        validDataAllFieldsMaxRange.put("picture", "https://unsplash.com/photos/rDEOVtE7vOs");
        HashMap <String, String>  location2 = new HashMap<>();
        location2.put("street", RandomStringUtils.random(100, true, true));
        location2.put("city", RandomStringUtils.random(30, true, false));
        location2.put("state", RandomStringUtils.random(30, true, false));
        location1.put("country", RandomStringUtils.random(30, true, false));
        location2.put("timezone", "+9:00");
        validDataAllFieldsMaxRange.put("location", location2);

        return new HashMap[][]{
                {validDataAllFieldsMinRange},
                {validDataAllFieldsMaxRange},
        };
    }

    @DataProvider(name = "user_create_all_fields_invalid_data")
    public static Object[][] createInvalidDataForAllFields() {
        HashMap<String, Object> invalidDataInTitle = new HashMap<>();
        invalidDataInTitle.put("title", "human");
        invalidDataInTitle.put("firstName", RandomStringUtils.random(15, true, false));
        invalidDataInTitle.put("lastName", RandomStringUtils.random(15, true, false));
        invalidDataInTitle.put("email",
                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");



        return new HashMap[][]{
                {invalidDataInTitle},

        };
    }
}
