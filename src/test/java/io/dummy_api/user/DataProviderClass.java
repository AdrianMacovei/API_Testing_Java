package io.dummy_api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dummy_api.enums.Gender;
import io.dummy_api.enums.Title;
import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.Location;
import io.dummy_api.models.UserModel;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.ErrorManager;

public class DataProviderClass {

    private static final String ERROR_DATA_MESSAGE_EMPTY_FIRST_NAME = "Path `firstName` is required.";
    private static final String ERROR_DATA_MESSAGE_EMPTY_LAST_NAME = "Path `lastName` is required.";
    private static final String ERROR_DATA_MESSAGE_EMPTY_EMAIL_NAME = "Path `email` is required.";
    private static final String ERROR_DATA_MESSAGE_TOO_SHORT_FIRST_NAME =
            "Path `firstName` (`%s`) is shorter than the minimum allowed length (2).";
    private static final String ERROR_DATA_MESSAGE_TOO_SHORT_LAST_NAME =
            "Path `lastName` (`%s`) is shorter than the minimum allowed length (2).";
    private static final String ERROR_DATA_MESSAGE_TOO_LONG_LAST_NAME =
            "Path `lastName` (`%s`) is longer than the maximum allowed length (30).";
    private static final String ERROR_DATA_MESSAGE_TOO_LONG_FIRST_NAME =
            "Path `firstName` (`%s`) is longer than the maximum allowed length (30).";
    private static final String ERROR_DATA_MESSAGE_WRONG_FORMAT_EMAIL = "Path `email` is invalid (%s).";

    private static final String DOMAIN1 = "@gmail.com";
    private static final String DOMAIN2 = String.format("@%s.com",
            RandomStringUtils.random(5, true, false).toLowerCase());

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
                {RandomStringUtils.random(24, false, true)},
                {"!@#$%%*%*$#&#&#"},
                {"%20"}

        };
    }

    @DataProvider(name = "valid_page_values")
    public static Object[][] createValidDataPageParam() {
        return new Integer[][]{
                {0}, {999}, {43}, {44}, {4}, {5}
        };
    }

    @DataProvider(name = "invalid_page_values")
    public static Object[][] createInvalidDataPageParam() {
        return new Object[][]{
                {-1}, {1000}, {"something"}, {45.564}, {false}
        };
    }

    @DataProvider(name = "valid_limit_values")
    public static Object[][] createValidDataLimitParam() {
        return new Integer[][]{
                {5}, {50}, {25}
        };
    }

    @DataProvider(name = "invalid_limit_values")
    public static Object[][] createInvalidDataLimitParam() {
        return new Object[][]{
                {4}, {51}, {"something"}, {45.564}, {false}
        };
    }

//    @DataProvider(name = "invalid_user_data")
//    public static Object[][] createInvalidUser() {
//        HashMap<String, String> userWithoutFistName = new HashMap<>();
//        userWithoutFistName.put("lastName", "Macovei");
//        userWithoutFistName.put("email", "adrianmacovei1998@gmail.com");
//
//        HashMap<String, String> userWithoutLastName = new HashMap<>();
//        userWithoutLastName.put("firstName", "Adrian");
//        userWithoutLastName.put("email", "adrianmacovei1998@gmail.com");
//
//        HashMap<String, String> userWithoutEmail = new HashMap<>();
//        userWithoutEmail.put("firstName", "Adrian");
//        userWithoutEmail.put("lastName", "Macovei");
//
//        HashMap<String, String> userWithoutEmailAndFirstName = new HashMap<>();
//        userWithoutEmailAndFirstName.put("lastName", "Macovei");
//
//        HashMap<String, String> userWithoutEmailAndLastName = new HashMap<>();
//        userWithoutEmailAndLastName.put("firstName", "Adrian");
//
//        HashMap<String, String> userWithoutFirstAndLastName = new HashMap<>();
//        userWithoutFirstAndLastName.put("email", "adrianmacovei1999@gmail.com");
//
//        HashMap<String, String> userWithEmptyFields = new HashMap<>();
//
//        HashMap<String, String> userWithInvalidEmail = new HashMap<>();
//        userWithInvalidEmail.put("firstName", "Adrian");
//        userWithInvalidEmail.put("lastName", "Macovei");
//        userWithInvalidEmail.put("email", RandomStringUtils.random(10, true, true).toLowerCase());
//
//        HashMap<String, String> userWithOneCharFirstName = new HashMap<>();
//        userWithOneCharFirstName.put("firstName", "A");
//        userWithOneCharFirstName.put("lastName", "Macovei");
//        userWithOneCharFirstName.put("email", RandomStringUtils.random(6, true, true).
//                toLowerCase() + "@gmail.com");
//
//        HashMap<String, String> userWithFiftyOneCharsFirstName = new HashMap<>();
//        userWithFiftyOneCharsFirstName.put("firstName", RandomStringUtils.random(51, true, false));
//        userWithFiftyOneCharsFirstName.put("lastName", "Macovei");
//        userWithFiftyOneCharsFirstName.put("email", RandomStringUtils.random(6, true, true).
//                toLowerCase() + "@gmail.com");
//
//        HashMap<String, String> userWithOneCharLastName = new HashMap<>();
//        userWithOneCharLastName.put("firstName", "Adrian");
//        userWithOneCharLastName.put("lastName", "M");
//        userWithOneCharLastName.put("email", RandomStringUtils.random(6, true, true).
//                toLowerCase() + "@gmail.com");
//
//        HashMap<String, String> userWithFiftyOneCharsLastName = new HashMap<>();
//        userWithFiftyOneCharsLastName.put("firstName", "Adrian");
//        userWithFiftyOneCharsLastName.put("lastName", RandomStringUtils.random(51, true, false));
//        userWithFiftyOneCharsLastName.put("email", RandomStringUtils.random(6, true, true).
//                toLowerCase() + "@gmail.com");
//
//
//        return new HashMap[][]{
//                {userWithoutFistName},
//                {userWithoutLastName},
//                {userWithoutEmail},
//                {userWithoutEmailAndFirstName},
//                {userWithoutEmailAndLastName},
//                {userWithoutFirstAndLastName},
//                {userWithEmptyFields},
//                {userWithInvalidEmail},
//                {userWithOneCharFirstName},
//                {userWithFiftyOneCharsFirstName},
//                {userWithOneCharLastName},
//                {userWithFiftyOneCharsLastName}
//        };
//    }
    @DataProvider(name = "invalid_firstName_data")
    public static Object[][] createInvalidFirstName() {
        UserModel userModelEmptyFirstName = new UserModel(null,
                RandomStringUtils.random(7, true, false),
                RandomStringUtils.random(7, true, true) + DOMAIN1,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelOneStringFirstName = new UserModel("A",
                RandomStringUtils.random(7, true, false),
                RandomStringUtils.random(7, true, true) + DOMAIN2,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());
        String errorMessageOneCharFirstName =
                String.format(ERROR_DATA_MESSAGE_TOO_SHORT_FIRST_NAME,
                userModelOneStringFirstName.getFirstName());

        UserModel userModelMaxStringFirstName = new UserModel(RandomStringUtils.random(31, true, false),
                RandomStringUtils.random(7, true, false),
                RandomStringUtils.random(7, true, true) + DOMAIN2,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());
        String errorMessageMaxStringFirstName =
                String.format(DataProviderClass.ERROR_DATA_MESSAGE_TOO_LONG_FIRST_NAME,
                        userModelMaxStringFirstName.getFirstName());


        return new Object [][]{
                {userModelEmptyFirstName, ERROR_DATA_MESSAGE_EMPTY_FIRST_NAME},
                {userModelOneStringFirstName, errorMessageOneCharFirstName},
                {userModelMaxStringFirstName, errorMessageMaxStringFirstName},
        };
    }

    @DataProvider(name = "invalid_lastName_data")
    public static Object[][] createInvalidLastName() {
        UserModel userModelEmptyLastName = new UserModel(RandomStringUtils.random(2, true, false),
                null,
                RandomStringUtils.random(7, true, true) + DOMAIN2,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelOneStringLastName = new UserModel(RandomStringUtils.random(30, true, false),
                RandomStringUtils.random(1, true, false),
                RandomStringUtils.random(7, true, true) + DOMAIN2,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());
        String errorMessageOneCharLastName =
                String.format(ERROR_DATA_MESSAGE_TOO_SHORT_LAST_NAME,
                        userModelOneStringLastName.getLastName());

        UserModel userModelMaxStringLastName = new UserModel(RandomStringUtils.random(2, true, false),
                RandomStringUtils.random(31, true, false),
                RandomStringUtils.random(7, true, true) + DOMAIN1,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());
        String errorMessageMaxStringLastName =
                String.format(ERROR_DATA_MESSAGE_TOO_LONG_LAST_NAME,
                        userModelMaxStringLastName.getLastName());


        return new Object [][]{
                {userModelEmptyLastName, ERROR_DATA_MESSAGE_EMPTY_LAST_NAME},
                {userModelOneStringLastName, errorMessageOneCharLastName},
                {userModelMaxStringLastName, errorMessageMaxStringLastName},
        };
    }

    @DataProvider(name = "invalid_email_data")
    public static Object[][] createInvalidEmail() {
        UserModel userModelEmptyEmail = new UserModel(RandomStringUtils.random(2, true, false),
                RandomStringUtils.random(29, true, false),
                null,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatEmail = new UserModel(RandomStringUtils.random(2, true, false),
                RandomStringUtils.random(29, true, false),
                RandomStringUtils.random(10, true, true).toLowerCase(),
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());
        String errorMessageWrongFormatEmail= String.format(ERROR_DATA_MESSAGE_WRONG_FORMAT_EMAIL,
                userModelWrongFormatEmail.getEmail());

        return new Object [][]{
                {userModelEmptyEmail, ERROR_DATA_MESSAGE_EMPTY_EMAIL_NAME},
                {userModelWrongFormatEmail, errorMessageWrongFormatEmail}
        };
    }

    @DataProvider(name = "invalid_first_and_last_name_data")
    public static Object[][] createInvalidFirstAndLastName() {
        UserModel userModelEmptyFirstNameLastName = new UserModel(null,
                null,
                RandomStringUtils.random(7, true, true) + DOMAIN1,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatMinFirstNameAndLastName = new UserModel(
                RandomStringUtils.random(1, true, false),
                RandomStringUtils.random(1, true, false),
                RandomStringUtils.random(7, true, true) + DOMAIN1,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatMaxFirstNameAndLastName = new UserModel(
                RandomStringUtils.random(31, true, false),
                RandomStringUtils.random(31, true, false),
                RandomStringUtils.random(7, true, true) + DOMAIN1,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        return new Object [][]
                {
                        {
                                userModelEmptyFirstNameLastName,
                                ERROR_DATA_MESSAGE_EMPTY_FIRST_NAME,
                                ERROR_DATA_MESSAGE_EMPTY_LAST_NAME
                        },
                        {
                                userModelWrongFormatMinFirstNameAndLastName,
                                String.format(ERROR_DATA_MESSAGE_TOO_SHORT_FIRST_NAME,
                                        userModelWrongFormatMinFirstNameAndLastName.getFirstName()),
                                String.format(ERROR_DATA_MESSAGE_TOO_SHORT_LAST_NAME,
                                        userModelWrongFormatMinFirstNameAndLastName.getLastName())
                        },
                        {
                                userModelWrongFormatMaxFirstNameAndLastName,
                                String.format(ERROR_DATA_MESSAGE_TOO_LONG_FIRST_NAME,
                                        userModelWrongFormatMaxFirstNameAndLastName.getFirstName()),
                                String.format(ERROR_DATA_MESSAGE_TOO_LONG_LAST_NAME,
                                        userModelWrongFormatMaxFirstNameAndLastName.getLastName())
                        },
                };
    }

    @DataProvider(name = "invalid_first_name_and_email_data")
    public static Object[][] createInvalidFirstNameAndEmail() {
        UserModel userModelEmptyFirstNameAndEmail = new UserModel(
                null,
                RandomStringUtils.random(15, true, true),
                null,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatMinFirstEmptyEmail = new UserModel(
                RandomStringUtils.random(1, true, false),
                RandomStringUtils.random(20, true, false),
                null,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatMaxFirstWrongFormatEmail = new UserModel(
                RandomStringUtils.random(31, true, false),
                RandomStringUtils.random(20, true, false),
                RandomStringUtils.random(7, true, true).toLowerCase(),
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        return new Object [][]
                {
                        {
                                userModelEmptyFirstNameAndEmail,
                                ERROR_DATA_MESSAGE_EMPTY_FIRST_NAME,
                                ERROR_DATA_MESSAGE_EMPTY_EMAIL_NAME
                        },
                        {
                                userModelWrongFormatMinFirstEmptyEmail,
                                String.format(ERROR_DATA_MESSAGE_TOO_SHORT_FIRST_NAME,
                                        userModelWrongFormatMinFirstEmptyEmail.getFirstName()),
                                ERROR_DATA_MESSAGE_EMPTY_EMAIL_NAME
                        },
                        {
                                userModelWrongFormatMaxFirstWrongFormatEmail,
                                String.format(ERROR_DATA_MESSAGE_TOO_LONG_FIRST_NAME,
                                        userModelWrongFormatMaxFirstWrongFormatEmail.getFirstName()),
                                String.format(ERROR_DATA_MESSAGE_WRONG_FORMAT_EMAIL,
                                        userModelWrongFormatMaxFirstWrongFormatEmail.getEmail()),
                        }
                };
    }

    @DataProvider(name = "invalid_last_name_and_email_data")
    public static Object[][] createInvalidLastNameAndEmail() {
        UserModel userModelEmptyLastNameAndEmail = new UserModel(
                RandomStringUtils.random(15, true, true),
                null,
                null,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatMinLastEmptyEmail = new UserModel(
                RandomStringUtils.random(15, true, false),
                RandomStringUtils.random(1, true, false),
                null,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatMaxLastWrongFormatEmail = new UserModel(
                RandomStringUtils.random(10, true, false),
                RandomStringUtils.random(31, true, false),
                RandomStringUtils.random(7, true, true).toLowerCase(),
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        return new Object [][]
                {
                        {
                                userModelEmptyLastNameAndEmail,
                                ERROR_DATA_MESSAGE_EMPTY_LAST_NAME,
                                ERROR_DATA_MESSAGE_EMPTY_EMAIL_NAME
                        },
                        {
                                userModelWrongFormatMinLastEmptyEmail,
                                String.format(ERROR_DATA_MESSAGE_TOO_SHORT_LAST_NAME,
                                        userModelWrongFormatMinLastEmptyEmail.getLastName()),
                                ERROR_DATA_MESSAGE_EMPTY_EMAIL_NAME
                        },
                        {
                                userModelWrongFormatMaxLastWrongFormatEmail,
                                String.format(ERROR_DATA_MESSAGE_TOO_LONG_LAST_NAME,
                                        userModelWrongFormatMaxLastWrongFormatEmail.getLastName()),
                                String.format(ERROR_DATA_MESSAGE_WRONG_FORMAT_EMAIL,
                                        userModelWrongFormatMaxLastWrongFormatEmail.getEmail()),
                        }
                };
    }

    @DataProvider(name = "invalid_first_last_and_email_data")
    public static Object[][] createInvalidFirstLastNameAndEmail() {
        UserModel userModelEmptyFirstNameLastNameAndEmail = new UserModel(
                null,
                null,
                null,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatMinFirstNameLastNameEmptyEmail = new UserModel(
                RandomStringUtils.random(1, true, false),
                RandomStringUtils.random(1, true, false),
                null,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatMaxFirstNameLasNameWrongFormatEmail = new UserModel(
                RandomStringUtils.random(31, true, false),
                RandomStringUtils.random(31, true, false),
                RandomStringUtils.random(7, true, true).toLowerCase(),
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        return new Object [][]
                {
                        {
                                userModelEmptyFirstNameLastNameAndEmail,
                                ERROR_DATA_MESSAGE_EMPTY_FIRST_NAME,
                                ERROR_DATA_MESSAGE_EMPTY_LAST_NAME,
                                ERROR_DATA_MESSAGE_EMPTY_EMAIL_NAME,
                        },
                        {
                                userModelWrongFormatMinFirstNameLastNameEmptyEmail,
                                String.format(ERROR_DATA_MESSAGE_TOO_SHORT_FIRST_NAME,
                                        userModelWrongFormatMinFirstNameLastNameEmptyEmail.getFirstName()),
                                String.format(ERROR_DATA_MESSAGE_TOO_SHORT_LAST_NAME,
                                        userModelWrongFormatMinFirstNameLastNameEmptyEmail.getLastName()),
                                ERROR_DATA_MESSAGE_EMPTY_EMAIL_NAME
                        },
                        {
                                userModelWrongFormatMaxFirstNameLasNameWrongFormatEmail,
                                String.format(ERROR_DATA_MESSAGE_TOO_LONG_FIRST_NAME,
                                        userModelWrongFormatMaxFirstNameLasNameWrongFormatEmail.getFirstName()),
                                String.format(ERROR_DATA_MESSAGE_TOO_LONG_LAST_NAME,
                                        userModelWrongFormatMaxFirstNameLasNameWrongFormatEmail.getLastName()),
                                String.format(ERROR_DATA_MESSAGE_WRONG_FORMAT_EMAIL,
                                        userModelWrongFormatMaxFirstNameLasNameWrongFormatEmail.getEmail()),
                        }
                };
    }
    @DataProvider(name = "user_valid_data")
    public static Object[][] createValidDataForUser() {
        UserModel userWithTwoCharactersFirstAndLastName = new UserModel(
                RandomStringUtils.random(2, true, false),
                RandomStringUtils.random(2, true, false),
                RandomStringUtils.random(6, true, true).toLowerCase() + DOMAIN1,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userWithThirtyCharactersInFirstAndLastName = new UserModel(
                RandomStringUtils.random(30, true, false),
                RandomStringUtils.random(30, true, false),
                RandomStringUtils.random(31, true, true).toLowerCase() + DOMAIN2,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userWithValidDataInField = new UserModel(
                RandomStringUtils.random(15, true, false),
                RandomStringUtils.random(15, true, false),
                RandomStringUtils.random(1, true, true).toLowerCase() + DOMAIN2,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        return new UserModel[][]{
                {userWithThirtyCharactersInFirstAndLastName},
                {userWithValidDataInField},
                {userWithTwoCharactersFirstAndLastName}
        };
    }

//    @DataProvider(name = "update_valid_data")
//    public static Object[][] createValidDataForUpdate() {
//
//        HashMap<String, String> twoCharactersFirstAndLastName = new HashMap<>();
//        twoCharactersFirstAndLastName.put("firstName",
//                RandomStringUtils.random(2, true, false));
//        twoCharactersFirstAndLastName.put("lastName",
//                RandomStringUtils.random(2, true, false));
//
//        HashMap<String, String> thirtyCharactersFirstAndLastName = new HashMap<>();
//        thirtyCharactersFirstAndLastName.put("firstName",
//                RandomStringUtils.random(30, true, false));
//        thirtyCharactersFirstAndLastName.put("lastName",
//                RandomStringUtils.random(30, true, false));
//
//        return new HashMap[][]{
//                {twoCharactersFirstAndLastName},
//                {thirtyCharactersFirstAndLastName},
//        };
//    }
//
//    @DataProvider(name = "update_invalid_data")
//    public static Object[][] createInvalidDataForUpdate() {
//
//        HashMap<String, String> oneCharactersFirstAndLastName = new HashMap<>();
//        oneCharactersFirstAndLastName.put("firstName",
//                RandomStringUtils.random(1, true, false));
//        oneCharactersFirstAndLastName.put("lastName",
//                RandomStringUtils.random(1, true, false));
//
//        HashMap<String, String> thirtyOneCharactersFirstAndLastName = new HashMap<>();
//        thirtyOneCharactersFirstAndLastName.put("firstName",
//                RandomStringUtils.random(51, true, false));
//        thirtyOneCharactersFirstAndLastName.put("lastName",
//                RandomStringUtils.random(51, true, false));
//
//        return new HashMap[][]{
//                {thirtyOneCharactersFirstAndLastName},
//                {oneCharactersFirstAndLastName},
//        };
//    }
//
    @DataProvider(name = "user_all_fields_valid_data")
    public static Object[][] createValidDataForAllFields() {
//        HashMap<String, Object> validDataAllFieldsMinAcceptedTitleMsGenderMale = new HashMap<>();
//        validDataAllFieldsMinRange.put("title", Title.MS.getTitleType());
//        validDataAllFieldsMinRange.put("firstName", "Ma");
//        validDataAllFieldsMinRange.put("lastName", "Ad");
//        validDataAllFieldsMinRange.put("gender", Gender.MALE.getGenderType());
//        validDataAllFieldsMinRange.put("email",
//                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
//        validDataAllFieldsMinRange.put("dateOfBirth", "1/1/1900");
//        validDataAllFieldsMinRange.put("phone", RandomStringUtils.random(5, false, true));
//        validDataAllFieldsMinRange.put("picture", "https://unsplash.com/photos/rDEOVtE7vOs");
//        HashMap<String, String> location1 = new HashMap<>();
//        location1.put("street", RandomStringUtils.random(5, true, true));
//        location1.put("city", RandomStringUtils.random(2, true, false));
//        location1.put("state", RandomStringUtils.random(2, true, false));
//        location1.put("country", RandomStringUtils.random(2, true, false));
//        location1.put("timezone", "-9:00");
//        validDataAllFieldsMinRange.put("location", location1);
        Location location1 = new Location(
                RandomStringUtils.random(5, true, true),
                RandomStringUtils.random(2, true, false),
                RandomStringUtils.random(2, true, false),
                RandomStringUtils.random(2, true, false),
                "-9:00"
        );
        UserModel validDataAllFieldsMinAcceptedTitleMsGenderMale = new UserModel(
                RandomStringUtils.random(2, true, false),
                RandomStringUtils.random(2, true, false),
                RandomStringUtils.random(6, true, false).toLowerCase() + DOMAIN1,
                Gender.MALE.getGenderType(),
                Title.MS.getTitleType(),
                RandomStringUtils.random(5, true, false),
                "https://unsplash.com/photos/rDEOVtE7vOs",
                "1900-01-01T00:00:00.000Z",
                location1
        );

//        HashMap<String, Object> validDataAllFieldsMaxRange = new HashMap<>();
//        validDataAllFieldsMaxRange.put("title", Title.DR.getTitleType());
//        validDataAllFieldsMaxRange.put("firstName", RandomStringUtils.random(30, true, false));
//        validDataAllFieldsMaxRange.put("lastName", RandomStringUtils.random(30, true, false));
//        validDataAllFieldsMaxRange.put("gender", Gender.OTHER.getGenderType());
//        validDataAllFieldsMaxRange.put("email",
//                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
//        String date_for = LocalDateTime.now().minusHours(10).toString();
//        validDataAllFieldsMaxRange.put("dateOfBirth", date_for);
//        validDataAllFieldsMaxRange.put("phone", RandomStringUtils.random(20, false, true));
//        validDataAllFieldsMaxRange.put("picture", "https://unsplash.com/photos/rDEOVtE7vOs");
//        HashMap<String, String> location2 = new HashMap<>();
//        location2.put("street", RandomStringUtils.random(100, true, true));
//        location2.put("city", RandomStringUtils.random(30, true, false));
//        location2.put("state", RandomStringUtils.random(30, true, false));
//        location1.put("country", RandomStringUtils.random(30, true, false));
//        location2.put("timezone", "+9:00");
//        validDataAllFieldsMaxRange.put("location", location2);
//
//        HashMap<String, Object> validDataTitleMrGenderFemale = new HashMap<>();
//        validDataTitleMrGenderFemale.put("title", Title.MR.getTitleType());
//        validDataTitleMrGenderFemale.put("firstName", RandomStringUtils.random(30, true, false));
//        validDataTitleMrGenderFemale.put("lastName", RandomStringUtils.random(30, true, false));
//        validDataTitleMrGenderFemale.put("gender", Gender.FEMALE.getGenderType());
//        validDataTitleMrGenderFemale.put("email",
//                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
//        validDataTitleMrGenderFemale.put("dateOfBirth", date_for);
//        validDataTitleMrGenderFemale.put("phone", RandomStringUtils.random(20, false, true));
//        validDataTitleMrGenderFemale.put("picture", "https://unsplash.com/photos/rDEOVtE7vOs");
//        validDataTitleMrGenderFemale.put("location", location2);
//
//        HashMap<String, Object> validDataTitleMrsGenderEmpty = new HashMap<>();
//        validDataTitleMrsGenderEmpty.put("title", Title.MRS.getTitleType());
//        validDataTitleMrsGenderEmpty.put("firstName", RandomStringUtils.random(30, true, false));
//        validDataTitleMrsGenderEmpty.put("lastName", RandomStringUtils.random(30, true, false));
//        validDataTitleMrsGenderEmpty.put("gender", Gender.EMPTY.getGenderType());
//        validDataTitleMrsGenderEmpty.put("email",
//                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
//        validDataTitleMrsGenderEmpty.put("dateOfBirth", date_for);
//        validDataTitleMrsGenderEmpty.put("phone", RandomStringUtils.random(20, false, true));
//        validDataTitleMrsGenderEmpty.put("picture", "https://unsplash.com/photos/rDEOVtE7vOs");
//        validDataTitleMrsGenderEmpty.put("location", location1);
//
//        HashMap<String, Object> validDataTitleMissGenderEmpty = new HashMap<>();
//        validDataTitleMissGenderEmpty.put("title", Title.MISS.getTitleType());
//        validDataTitleMissGenderEmpty.put("firstName", RandomStringUtils.random(30, true, false));
//        validDataTitleMissGenderEmpty.put("lastName", RandomStringUtils.random(30, true, false));
//        validDataTitleMissGenderEmpty.put("gender", Gender.MALE.getGenderType());
//        validDataTitleMissGenderEmpty.put("email",
//                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
//        validDataTitleMissGenderEmpty.put("dateOfBirth", date_for);
//        validDataTitleMissGenderEmpty.put("phone", RandomStringUtils.random(20, false, true));
//        validDataTitleMissGenderEmpty.put("picture", "https://unsplash.com/photos/rDEOVtE7vOs");
//        validDataTitleMissGenderEmpty.put("location", location2);
//
//        HashMap<String, Object> validDataTitleEmptyGenderEmpty = new HashMap<>();
//        validDataTitleEmptyGenderEmpty.put("title", Title.EMPTY.getTitleType());
//        validDataTitleEmptyGenderEmpty.put("firstName", RandomStringUtils.random(30, true, false));
//        validDataTitleEmptyGenderEmpty.put("lastName", RandomStringUtils.random(30, true, false));
//        validDataTitleEmptyGenderEmpty.put("gender", Gender.MALE.getGenderType());
//        validDataTitleEmptyGenderEmpty.put("email",
//                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
//        validDataTitleEmptyGenderEmpty.put("dateOfBirth", date_for);
//        validDataTitleEmptyGenderEmpty.put("phone", RandomStringUtils.random(20, false, true));
//        validDataTitleEmptyGenderEmpty.put("picture", "https://unsplash.com/photos/rDEOVtE7vOs");
//        validDataTitleEmptyGenderEmpty.put("location", location1);

        return new UserModel[][]{
                {validDataAllFieldsMinAcceptedTitleMsGenderMale}
        };
    }
//
//    @DataProvider(name = "user_create_all_fields_invalid_data")
//    public static Object[][] createInvalidDataForAllFields() {
//        HashMap<String, Object> invalidDataInTitle = new HashMap<>();
//        invalidDataInTitle.put("title", "human");
//        invalidDataInTitle.put("firstName", RandomStringUtils.random(15, true, false));
//        invalidDataInTitle.put("lastName", RandomStringUtils.random(15, true, false));
//        invalidDataInTitle.put("email",
//                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
//
//        HashMap<String, Object> invalidDataInGender = new HashMap<>();
//        invalidDataInGender.put("firstName", RandomStringUtils.random(15, true, false));
//        invalidDataInGender.put("lastName", RandomStringUtils.random(15, true, false));
//        invalidDataInGender.put("email",
//                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
//        invalidDataInGender.put("gender", "human");
//
//        HashMap<String, Object> invalidDataInDateOfBirthLessThanMin = new HashMap<>();
//        invalidDataInDateOfBirthLessThanMin.put("firstName", RandomStringUtils.random(15, true, false));
//        invalidDataInDateOfBirthLessThanMin.put("lastName", RandomStringUtils.random(15, true, false));
//        invalidDataInDateOfBirthLessThanMin.put("email",
//                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
//        invalidDataInDateOfBirthLessThanMin.put("dateOfBirth", "12/31/1899");
//
//        HashMap<String, Object> invalidDataInDateOfBirthBiggerThanMax = new HashMap<>();
//        invalidDataInDateOfBirthBiggerThanMax.put("firstName", RandomStringUtils.random(15, true, false));
//        invalidDataInDateOfBirthBiggerThanMax.put("lastName", RandomStringUtils.random(15, true, false));
//        invalidDataInDateOfBirthBiggerThanMax.put("email",
//                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
//        LocalDateTime todayDate = LocalDateTime.now();
//        LocalDateTime date = todayDate.plusDays(1);
//        String date_for = date.toString();
//        invalidDataInDateOfBirthBiggerThanMax.put("dateOfBirth", date_for);
//
//        HashMap<String, Object> invalidDataInPhoneBiggerThanMax = new HashMap<>();
//        invalidDataInPhoneBiggerThanMax.put("firstName", RandomStringUtils.random(15, true, false));
//        invalidDataInPhoneBiggerThanMax.put("lastName", RandomStringUtils.random(15, true, false));
//        invalidDataInPhoneBiggerThanMax.put("email",
//                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
//        invalidDataInPhoneBiggerThanMax.put("phone", RandomStringUtils.random(21, false, true));
//
//        HashMap<String, Object> invalidDataInPhoneMinValue = new HashMap<>();
//        invalidDataInPhoneMinValue.put("firstName", RandomStringUtils.random(15, true, false));
//        invalidDataInPhoneMinValue.put("lastName", RandomStringUtils.random(15, true, false));
//        invalidDataInPhoneMinValue.put("email",
//                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
//        invalidDataInPhoneMinValue.put("phone", RandomStringUtils.random(4, false, true));
//
//        HashMap<String, Object> invalidDataInLocationFieldsMinRange= new HashMap<>();
//        invalidDataInLocationFieldsMinRange.put("firstName", RandomStringUtils.random(15, true, false));
//        invalidDataInLocationFieldsMinRange.put("lastName", RandomStringUtils.random(15, true, false));
//        invalidDataInLocationFieldsMinRange.put("email",
//                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
//        HashMap<String, Object> locationFieldsMin= new HashMap<>();
//        locationFieldsMin.put("street", RandomStringUtils.random(4, true, false));
//        locationFieldsMin.put("city", RandomStringUtils.random(1, true, false));
//        locationFieldsMin.put("state", RandomStringUtils.random(1, true, false));
//        locationFieldsMin.put("country", RandomStringUtils.random(1, true, false));
//        locationFieldsMin.put("timezone","-13:00");
//        invalidDataInLocationFieldsMinRange.put("location", locationFieldsMin);
//
//        HashMap<String, Object> invalidDataInLocationFieldsMaxRange= new HashMap<>();
//        invalidDataInLocationFieldsMaxRange.put("firstName", RandomStringUtils.random(15, true, false));
//        invalidDataInLocationFieldsMaxRange.put("lastName", RandomStringUtils.random(15, true, false));
//        invalidDataInLocationFieldsMaxRange.put("email",
//                RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
//        HashMap<String, Object> locationFieldsMax= new HashMap<>();
//        locationFieldsMax.put("street", RandomStringUtils.random(101, true, false));
//        locationFieldsMax.put("city", RandomStringUtils.random(31, true, false));
//        locationFieldsMax.put("state", RandomStringUtils.random(31, true, false));
//        locationFieldsMax.put("country", RandomStringUtils.random(31, true, false));
//        locationFieldsMax.put("timezone","+15:00");
//        invalidDataInLocationFieldsMaxRange.put("location", locationFieldsMax);
//
//        return new HashMap[][]{
//                {invalidDataInTitle},
//                {invalidDataInGender},
//                {invalidDataInDateOfBirthLessThanMin},
//                {invalidDataInDateOfBirthBiggerThanMax},
//                {invalidDataInPhoneBiggerThanMax},
//                {invalidDataInPhoneMinValue},
//                {invalidDataInLocationFieldsMinRange},
//                {invalidDataInLocationFieldsMaxRange},
//        };
//    }
//
//    @DataProvider(name = "update_only_email")
//    public static Object[][] createDataUpdateEmail() {
//        HashMap<String, String> user_data = new HashMap<>();
//        user_data.put("firstName", "Adrian");
//        user_data.put("lastName", "Macovei");
//        user_data.put("email", RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");
//        return new HashMap[][]{
//                {user_data},
//        };
//    }
//
//    @DataProvider(name = "create_user_xss_inj")
//    public static Object[][] createDataXssInjOnCreateUser() {
//        HashMap<String, String> user_data = new HashMap<>();
//        user_data.put("firstName", "<script>alert(\"XSS\")</script>");
//        user_data.put("lastName", "<script>alert(\"XSS\")</script>");
//        user_data.put("email", "adrianmacovei342@gmail.com");
//        return new HashMap[][]{
//                {user_data},
//        };
//    }
//
//    @DataProvider(name = "update_user_xss_inj")
//    public static Object[][] createDataXssInjOnUpdateUser() {
//        HashMap<String, String> user_data = new HashMap<>();
//        user_data.put("firstName", "<script>alert(\"XSS\")</script>");
//        user_data.put("lastName", "<script>alert(\"XSS\")</script>");
//        return new HashMap[][]{
//                {user_data},
//        };
//    }

}
