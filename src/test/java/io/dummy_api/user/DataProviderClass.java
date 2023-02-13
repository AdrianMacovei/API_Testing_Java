package io.dummy_api.user;

import io.dummy_api.enums.Gender;
import io.dummy_api.enums.Title;
import io.dummy_api.models.Location;
import io.dummy_api.models.UserModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

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
    private static final String ERROR_DATA_MESSAGE_TOO_LOW_DATE_OF_BIRTH =
            "Path `dateOfBirth` (Sun Dec 31 1899 00:00:00 GMT+0000 (Coordinated Universal Time)) is " +
                    "before minimum allowed value (Mon Jan 01 1900 00:00:00 GMT+0000 (Coordinated Universal Time)).";
    private static final String ERROR_DATA_MESSAGE_AFTER_MAX_DATE_OF_BIRTH =
            "Path `dateOfBirth` (%s GMT+0000 (Coordinated Universal Time))" +
                    " is after maximum allowed value";

    private static final String ERROR_DATA_MESSAGE_WRONG_DATE_OF_BIRTH =
            "Cast to date failed for value \"%s\" (type string) at path \"dateOfBirth\"";

    private static final String ERROR_DATA_MESSAGE_TOO_SHORT_PHONE =
            "Path `phone` (`%s`) is shorter than the minimum allowed length (5).";

    private static final String ERROR_DATA_MESSAGE_TOO_LONG_PHONE =
            "Path `phone` (`%s`) is longer than the maximum allowed length (20).";

    private static final String DOMAIN1 = "@gmail.com";
    private static final String DOMAIN2 = java.lang.String.format("@%s.com",
            RandomStringUtils.random(5, true, false).toLowerCase());

    protected static Random rand = new Random();

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
                {0}, {999}, {43}, {44}, {4}, {rand.nextInt(10)}, {rand.nextInt(898) + 100}
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

    @DataProvider(name = "valid_limit_and_page_values")
    public static Object[][] createValidDataLimitAndPageParam() {
        return new Integer[][]{
                {5, 0}, {50, 999}, {rand.nextInt(5) + 5, rand.nextInt(10)},
                {rand.nextInt(49)+10, rand.nextInt(899) + 100}
        };
    }


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


        return new Object[][]{
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


        return new Object[][]{
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
        String errorMessageWrongFormatEmail = String.format(ERROR_DATA_MESSAGE_WRONG_FORMAT_EMAIL,
                userModelWrongFormatEmail.getEmail());

        return new Object[][]{
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

        return new Object[][]
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

        return new Object[][]
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

        return new Object[][]
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

        return new Object[][]
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

    @DataProvider(name = "user_all_fields_valid_data")
    public static Object[][] createValidDataForAllFields() {

        Location location1 = new Location(
                RandomStringUtils.random(5, true, true),
                RandomStringUtils.random(2, true, false),
                RandomStringUtils.random(2, true, false),
                RandomStringUtils.random(2, true, false),
                "-12:00"
        );
        UserModel validDataAllFieldsMinAcceptedTitleMsGenderMale = new UserModel(
                RandomStringUtils.random(2, true, false),
                RandomStringUtils.random(2, true, false),
                RandomStringUtils.random(6, true, false).toLowerCase() + DOMAIN1,
                Gender.MALE.getGenderType(),
                Title.MS.getTitleType(),
                RandomStringUtils.random(5, false, true),
                "https://unsplash.com/photos/rDEOVtE7vOs",
                "1900-01-01T00:00:00.000Z",
                location1
        );


        Location location2 = new Location(
                RandomStringUtils.random(100, true, true),
                RandomStringUtils.random(30, true, false),
                RandomStringUtils.random(30, true, false),
                RandomStringUtils.random(30, true, false),
                "+14:00"
        );
        UserModel validDataAllFieldsMaxAcceptedTitleDrGenderOther = new UserModel(
                RandomStringUtils.random(30, true, false),
                RandomStringUtils.random(30, true, false),
                RandomStringUtils.random(6, true, false).toLowerCase() + DOMAIN2,
                Gender.OTHER.getGenderType(),
                Title.DR.getTitleType(),
                RandomStringUtils.random(20, false, true),
                "https://unsplash.com/photos/rDEOVtE7vOs",
                formatDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", -2, 0),
                location2
        );


        UserModel validDataTitleMrGenderFemale = new UserModel(
                RandomStringUtils.random(15, true, false),
                RandomStringUtils.random(15, true, false),
                RandomStringUtils.random(8, true, false).toLowerCase() + DOMAIN2,
                Gender.FEMALE.getGenderType(),
                Title.MR.getTitleType(),
                RandomStringUtils.random(10, false, true),
                "https://unsplash.com/photos/rDEOVtE7vOs",
                formatDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", 0, (rand.nextInt(10) - 10)),
                location2
        );

        UserModel validDataTitleMrsGenderEmpty = new UserModel(
                RandomStringUtils.random(15, true, false),
                RandomStringUtils.random(15, true, false),
                RandomStringUtils.random(8, true, false).toLowerCase() + DOMAIN2,
                Gender.EMPTY.getGenderType(),
                Title.MRS.getTitleType(),
                RandomStringUtils.random(10, false, true),
                "https://unsplash.com/photos/rDEOVtE7vOs",
                formatDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", rand.nextInt(10)-10, rand.nextInt(20)-20),
                location2
        );

        UserModel validDataTitleMissGenderMale = new UserModel(
                RandomStringUtils.random(15, true, false),
                RandomStringUtils.random(15, true, false),
                RandomStringUtils.random(8, true, false).toLowerCase() + DOMAIN2,
                Gender.MALE.getGenderType(),
                Title.MISS.getTitleType(),
                RandomStringUtils.random(10, false, true),
                "https://unsplash.com/photos/rDEOVtE7vOs",
                formatDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", rand.nextInt(10)-10, rand.nextInt(20)-20),
                location1
        );

        UserModel validDataTitleEmptyGenderFemale = new UserModel(
                RandomStringUtils.random(15, true, false),
                RandomStringUtils.random(15, true, false),
                RandomStringUtils.random(8, true, false).toLowerCase() + DOMAIN2,
                Gender.FEMALE.getGenderType(),
                Title.EMPTY.getTitleType(),
                RandomStringUtils.random(10, false, true),
                "https://unsplash.com/photos/rDEOVtE7vOs",
                formatDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", rand.nextInt(10)-10, rand.nextInt(20)-20),
                location1
        );

        return new UserModel[][]{
                {validDataAllFieldsMinAcceptedTitleMsGenderMale},
                {validDataAllFieldsMaxAcceptedTitleDrGenderOther},
                {validDataTitleMrGenderFemale},
                {validDataTitleMrsGenderEmpty},
                {validDataTitleMissGenderMale},
                {validDataTitleEmptyGenderFemale},
        };
    }

    @DataProvider(name = "user_invalid_title_data")
    public static Object[][] createInvalidTitleData() {
        UserModel invalidTitle = new UserModel();
        invalidTitle.setFirstName(RandomStringUtils.random(3, false, true));
        invalidTitle.setLastName(RandomStringUtils.random(3, false, true));
        invalidTitle.setEmail(RandomStringUtils.random(6, true, true).toLowerCase() + DOMAIN2);
        invalidTitle.setTitle(RandomStringUtils.random(3, true, false));
        invalidTitle.setGender(Gender.MALE.getGenderType());
        return new UserModel[][]{
                {invalidTitle}
        };
    }

    @DataProvider(name = "user_invalid_dateOfBirth_data")
    public static Object[][] createInvalidDateOfBirth() {
        UserModel invalidDataInDateOfBirthLessThanMin = new UserModel();
        invalidDataInDateOfBirthLessThanMin.setFirstName(RandomStringUtils.random(21, true, true));
        invalidDataInDateOfBirthLessThanMin.setLastName(RandomStringUtils.random(21, true, true));
        invalidDataInDateOfBirthLessThanMin.setEmail(RandomStringUtils.random(6, true, true).toLowerCase() + DOMAIN2);
        invalidDataInDateOfBirthLessThanMin.setTitle(Title.MISS.getTitleType());
        invalidDataInDateOfBirthLessThanMin.setGender(Gender.MALE.getGenderType());
        invalidDataInDateOfBirthLessThanMin.setDateOfBirth("1899/12/31");

        UserModel invalidDataInDateOfBirthMoreThanMax = new UserModel();
        invalidDataInDateOfBirthMoreThanMax.setFirstName(RandomStringUtils.random(21, true, true));
        invalidDataInDateOfBirthMoreThanMax.setLastName(RandomStringUtils.random(21, true, true));
        invalidDataInDateOfBirthMoreThanMax.setEmail(RandomStringUtils.random(6, true, true).toLowerCase() + DOMAIN2);
        invalidDataInDateOfBirthMoreThanMax.setTitle(Title.MISS.getTitleType());
        invalidDataInDateOfBirthMoreThanMax.setGender(Gender.MALE.getGenderType());
        String dateNowPlus = formatDate("E MMM dd yyyy HH:mm:ss", rand.nextInt(20), rand.nextInt(20));
        invalidDataInDateOfBirthMoreThanMax.setDateOfBirth(dateNowPlus);

        UserModel wrongFormatDateOfBirth = new UserModel();
        wrongFormatDateOfBirth.setFirstName(RandomStringUtils.random(21, true, true));
        wrongFormatDateOfBirth.setLastName(RandomStringUtils.random(21, true, true));
        wrongFormatDateOfBirth.setEmail(RandomStringUtils.random(6, true, true).toLowerCase() + DOMAIN2);
        wrongFormatDateOfBirth.setTitle(Title.MISS.getTitleType());
        wrongFormatDateOfBirth.setGender(Gender.MALE.getGenderType());
        wrongFormatDateOfBirth.setDateOfBirth("15/05/2020");

        UserModel randomStringNumericAndAlphabeticalDateOfBirth = new UserModel();
        randomStringNumericAndAlphabeticalDateOfBirth.setFirstName(RandomStringUtils.random(21, true, true));
        randomStringNumericAndAlphabeticalDateOfBirth.setLastName(RandomStringUtils.random(21, true, true));
        randomStringNumericAndAlphabeticalDateOfBirth.setEmail(RandomStringUtils.random(6, true, true).toLowerCase() + DOMAIN2);
        randomStringNumericAndAlphabeticalDateOfBirth.setTitle(Title.MISS.getTitleType());
        randomStringNumericAndAlphabeticalDateOfBirth.setGender(Gender.MALE.getGenderType());
        String randomBirthDateNumAndAlphabetic = RandomStringUtils.random(6, true, true);
        randomStringNumericAndAlphabeticalDateOfBirth.setDateOfBirth(randomBirthDateNumAndAlphabetic);

        UserModel randomStringOnlyNumericalDateOfBirth = new UserModel();
        randomStringOnlyNumericalDateOfBirth.setFirstName(RandomStringUtils.random(21, true, true));
        randomStringOnlyNumericalDateOfBirth.setLastName(RandomStringUtils.random(21, true, true));
        randomStringOnlyNumericalDateOfBirth.setEmail(RandomStringUtils.random(6, true, true).toLowerCase() + DOMAIN2);
        randomStringOnlyNumericalDateOfBirth.setTitle(Title.MISS.getTitleType());
        randomStringOnlyNumericalDateOfBirth.setGender(Gender.MALE.getGenderType());
        String randomBirthDateOnlyNumeric = RandomStringUtils.random(8, false, true);
        randomStringOnlyNumericalDateOfBirth.setDateOfBirth(randomBirthDateOnlyNumeric);

        UserModel emptyStringDateOfBirth = new UserModel();
        emptyStringDateOfBirth.setFirstName(RandomStringUtils.random(21, true, true));
        emptyStringDateOfBirth.setLastName(RandomStringUtils.random(21, true, true));
        emptyStringDateOfBirth.setEmail(RandomStringUtils.random(6, true, true).toLowerCase() + DOMAIN2);
        emptyStringDateOfBirth.setTitle(Title.MISS.getTitleType());
        emptyStringDateOfBirth.setGender(Gender.MALE.getGenderType());
        emptyStringDateOfBirth.setDateOfBirth("");

        return new Object[][]{
                {invalidDataInDateOfBirthLessThanMin, ERROR_DATA_MESSAGE_TOO_LOW_DATE_OF_BIRTH},
                {invalidDataInDateOfBirthMoreThanMax, String.format(ERROR_DATA_MESSAGE_AFTER_MAX_DATE_OF_BIRTH, dateNowPlus)},
                {wrongFormatDateOfBirth, String.format(ERROR_DATA_MESSAGE_WRONG_DATE_OF_BIRTH, "15/05/2020")},
                {randomStringNumericAndAlphabeticalDateOfBirth, String.format(ERROR_DATA_MESSAGE_WRONG_DATE_OF_BIRTH, randomBirthDateNumAndAlphabetic)},
                {emptyStringDateOfBirth, String.format(ERROR_DATA_MESSAGE_WRONG_DATE_OF_BIRTH, "")},
                {randomStringOnlyNumericalDateOfBirth, String.format(ERROR_DATA_MESSAGE_WRONG_DATE_OF_BIRTH, randomBirthDateOnlyNumeric)}
        };
    }

    protected static String formatDate(String pattern, int hoursToAdd, int daysToAdd) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        calendar.add(Calendar.HOUR_OF_DAY, hoursToAdd);
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
        return dateFormat.format(calendar.getTime());
    }

    @DataProvider(name = "user_invalid_gender_data")
    public static Object[][] createInvalidGenderData() {
        UserModel invalidGender = new UserModel();
        invalidGender.setFirstName(RandomStringUtils.random(3, false, true));
        invalidGender.setLastName(RandomStringUtils.random(3, false, true));
        invalidGender.setEmail(RandomStringUtils.random(6, true, true).toLowerCase() + DOMAIN2);
        invalidGender.setTitle(Title.MISS.getTitleType());
        invalidGender.setGender(RandomStringUtils.random(4, true, false));

        return new UserModel[][]{
                {invalidGender}
        };
    }

    @DataProvider(name = "user_invalid_phone_data")
    public static Object[][] createInvalidPhoneData() {
        UserModel invalidDataInPhoneLessThanMin = new UserModel();
        invalidDataInPhoneLessThanMin.setFirstName(RandomStringUtils.random(21, true, true));
        invalidDataInPhoneLessThanMin.setLastName(RandomStringUtils.random(21, true, true));
        invalidDataInPhoneLessThanMin.setEmail(RandomStringUtils.random(6, true, true).toLowerCase() + DOMAIN2);
        invalidDataInPhoneLessThanMin.setTitle(Title.MISS.getTitleType());
        invalidDataInPhoneLessThanMin.setGender(Gender.MALE.getGenderType());
        String randomPhone1 = RandomStringUtils.random(4, false, true);
        invalidDataInPhoneLessThanMin.setPhone(randomPhone1);

        UserModel invalidDataInPhoneMoreThanMax = new UserModel();
        invalidDataInPhoneMoreThanMax.setFirstName(RandomStringUtils.random(21, true, true));
        invalidDataInPhoneMoreThanMax.setLastName(RandomStringUtils.random(21, true, true));
        invalidDataInPhoneMoreThanMax.setEmail(RandomStringUtils.random(6, true, true).toLowerCase() + DOMAIN2);
        invalidDataInPhoneMoreThanMax.setTitle(Title.MISS.getTitleType());
        invalidDataInPhoneMoreThanMax.setGender(Gender.MALE.getGenderType());
        String randomPhone2 = RandomStringUtils.random(21, false, true);
        invalidDataInPhoneMoreThanMax.setPhone(randomPhone2);

        UserModel invalidDataInPhoneOnlyAlphabetical = new UserModel();
        invalidDataInPhoneOnlyAlphabetical.setFirstName(RandomStringUtils.random(21, true, true));
        invalidDataInPhoneOnlyAlphabetical.setLastName(RandomStringUtils.random(21, true, true));
        invalidDataInPhoneOnlyAlphabetical.setEmail(RandomStringUtils.random(6, true, true).toLowerCase() + DOMAIN2);
        invalidDataInPhoneOnlyAlphabetical.setTitle(Title.MISS.getTitleType());
        invalidDataInPhoneOnlyAlphabetical.setGender(Gender.MALE.getGenderType());
        String randomPhone3 = RandomStringUtils.random(10, true, false);
        invalidDataInPhoneOnlyAlphabetical.setPhone(randomPhone3);

        return new Object[][]{
                {invalidDataInPhoneLessThanMin, String.format(ERROR_DATA_MESSAGE_TOO_SHORT_PHONE, randomPhone1)},
                {invalidDataInPhoneMoreThanMax, String.format(ERROR_DATA_MESSAGE_TOO_LONG_PHONE, randomPhone2)},
                {invalidDataInPhoneOnlyAlphabetical, "Invalid phone number"},
        };
    }

    @DataProvider(name = "user_invalid_location_data")
    public static Object[][] createInvalidLocationData() {
        UserModel invalidDataInLocationFieldsLessThanMinAccepted = new UserModel();
        invalidDataInLocationFieldsLessThanMinAccepted.setFirstName(
                RandomStringUtils.random(21, true, true));
        invalidDataInLocationFieldsLessThanMinAccepted.setLastName(
                RandomStringUtils.random(21, true, true));
        invalidDataInLocationFieldsLessThanMinAccepted.setEmail(
                RandomStringUtils.random(6, true, true).toLowerCase() + DOMAIN2);
        invalidDataInLocationFieldsLessThanMinAccepted.setTitle(Title.MISS.getTitleType());
        invalidDataInLocationFieldsLessThanMinAccepted.setGender(Gender.MALE.getGenderType());
        Location locationInvalidLessThanMinAccepted = new Location(
                RandomStringUtils.random(4, true, true),
                RandomStringUtils.random(1, true, true),
                RandomStringUtils.random(1, true, true),
                RandomStringUtils.random(1, true, true),
                "-13:00"
        );
        invalidDataInLocationFieldsLessThanMinAccepted.setLocation(locationInvalidLessThanMinAccepted);

        UserModel invalidDataInLocationFieldsMoreThanMaxAccepted = new UserModel();
        invalidDataInLocationFieldsMoreThanMaxAccepted.setFirstName(
                RandomStringUtils.random(10, true, true));
        invalidDataInLocationFieldsMoreThanMaxAccepted.setLastName(
                RandomStringUtils.random(10, true, true));
        invalidDataInLocationFieldsMoreThanMaxAccepted.setEmail(
                RandomStringUtils.random(10, true, true).toLowerCase() + DOMAIN2);
        invalidDataInLocationFieldsMoreThanMaxAccepted.setTitle(Title.MISS.getTitleType());
        invalidDataInLocationFieldsMoreThanMaxAccepted.setGender(Gender.MALE.getGenderType());
        Location locationInvalidMoreThanMaxAccepted = new Location(
                RandomStringUtils.random(101, true, true),
                RandomStringUtils.random(31, true, true),
                RandomStringUtils.random(31, true, true),
                RandomStringUtils.random(31, true, true),
                "+15:00"
        );
        invalidDataInLocationFieldsLessThanMinAccepted.setLocation(locationInvalidMoreThanMaxAccepted);

        return new Object[][]{
                {invalidDataInLocationFieldsLessThanMinAccepted},
                {invalidDataInLocationFieldsMoreThanMaxAccepted}
        };
    }

    @DataProvider(name = "user_invalid_all_fields_data")
    public static Object[][] createInvalidDataInAllFields() {
        UserModel invalidAllFields = new UserModel();
        invalidAllFields.setFirstName(
                RandomStringUtils.random(1, true, true));
        invalidAllFields.setLastName(
                RandomStringUtils.random(1, true, true));
        invalidAllFields.setEmail(
                RandomStringUtils.random(10, true, true).toLowerCase());
        invalidAllFields.setTitle("Something");
        invalidAllFields.setGender("Something");
        Location locationInvalidAllFields = new Location(
                RandomStringUtils.random(101, true, true),
                RandomStringUtils.random(31, true, true),
                RandomStringUtils.random(31, true, true),
                RandomStringUtils.random(31, true, true),
                RandomStringUtils.random(6, true, true)
        );
        invalidAllFields.setLocation(locationInvalidAllFields);
        invalidAllFields.setPhone(RandomStringUtils.random(21, false, true));
        String randomBirth = RandomStringUtils.random(8, true, true);
        invalidAllFields.setDateOfBirth(randomBirth);

        return new Object[][]{
                {invalidAllFields,
                        String.format(ERROR_DATA_MESSAGE_TOO_SHORT_FIRST_NAME, invalidAllFields.getFirstName()),
                        String.format(ERROR_DATA_MESSAGE_TOO_SHORT_LAST_NAME, invalidAllFields.getLastName()),
                        String.format(ERROR_DATA_MESSAGE_WRONG_FORMAT_EMAIL, invalidAllFields.getEmail()),
                        String.format(ERROR_DATA_MESSAGE_WRONG_DATE_OF_BIRTH, invalidAllFields.getDateOfBirth()),
                        String.format(ERROR_DATA_MESSAGE_TOO_LONG_PHONE, invalidAllFields.getPhone())
                }
        };
    }

    @DataProvider(name = "update_valid_first_and_last_name_data")
    public static Object[][] updateValidFirstAndLastName()
    {
        UserModel updateFirstAndLastNameValidMinValues = new UserModel();
        updateFirstAndLastNameValidMinValues.setFirstName(RandomStringUtils.random(2, true, false));
        updateFirstAndLastNameValidMinValues.setLastName(RandomStringUtils.random(2, true, false));

        UserModel updateFirstAndLastNameValidMaxValues = new UserModel();
        updateFirstAndLastNameValidMaxValues.setFirstName(RandomStringUtils.random(30, true, false));
        updateFirstAndLastNameValidMaxValues.setLastName(RandomStringUtils.random(30, true, false));

        return new Object[][]{
                {updateFirstAndLastNameValidMinValues},
                {updateFirstAndLastNameValidMaxValues},
        };
    }

    @DataProvider(name = "update_invalid_first_and_last_name_data")
    public static Object[][] updateInvalidFirstAndLastName()
    {
        UserModel updateFirstAndLastNameValidMinValues = new UserModel();
        updateFirstAndLastNameValidMinValues.setFirstName(RandomStringUtils.random(1, true, false));
        updateFirstAndLastNameValidMinValues.setLastName(RandomStringUtils.random(1, true, false));

        UserModel updateFirstAndLastNameValidMaxValues = new UserModel();
        updateFirstAndLastNameValidMaxValues.setFirstName(RandomStringUtils.random(31, true, false));
        updateFirstAndLastNameValidMaxValues.setLastName(RandomStringUtils.random(31, true, false));

        UserModel updateFirstAndLastNameValidEmpty = new UserModel();
        updateFirstAndLastNameValidEmpty.setFirstName("");
        updateFirstAndLastNameValidEmpty.setLastName("");

        UserModel updateFirstAndLastNameValidNull = new UserModel();
        updateFirstAndLastNameValidNull.setFirstName(null);
        updateFirstAndLastNameValidNull.setLastName(null);

        return new Object[][]{
                {updateFirstAndLastNameValidMinValues},
                {updateFirstAndLastNameValidMaxValues},
                {updateFirstAndLastNameValidEmpty},
                {updateFirstAndLastNameValidNull}
        };
    }

    @DataProvider(name = "update_all_fields_valid_data")
    public static Object[][] updateAllFieldsValidData()
    {
        UserModel updateValidDataAllFields = new UserModel();
        updateValidDataAllFields.setFirstName(RandomStringUtils.random(15, true, false));
        updateValidDataAllFields.setLastName(RandomStringUtils.random(15, true, false));
        updateValidDataAllFields.setPhone(RandomStringUtils.random(10, false, true));
        updateValidDataAllFields.setPicture(RandomStringUtils.random(10, true, true));
        updateValidDataAllFields.setTitle(Title.MR.getTitleType());
        updateValidDataAllFields.setGender(Gender.MALE.getGenderType());
        Location location = new Location(
                RandomStringUtils.random(15, true, false),
                RandomStringUtils.random(15, true, false),
                RandomStringUtils.random(15, true, false),
                RandomStringUtils.random(15, true, false),
                "+5:00"
        );
        updateValidDataAllFields.setLocation(location);

        return new UserModel[][]{
                {updateValidDataAllFields},
        };
    }

    @DataProvider(name = "update_all_fields_invalid_data")
    public static Object[][] updateAllFieldsInvalidData()
    {
        UserModel updateInvalidDataAllFieldsLessThanMin = new UserModel();
        updateInvalidDataAllFieldsLessThanMin.setFirstName(RandomStringUtils.random(1, true, false));
        updateInvalidDataAllFieldsLessThanMin.setLastName(RandomStringUtils.random(1, true, false));
        updateInvalidDataAllFieldsLessThanMin.setPhone(RandomStringUtils.random(4, false, true));
        updateInvalidDataAllFieldsLessThanMin.setPicture(RandomStringUtils.random(10, true, true));
        updateInvalidDataAllFieldsLessThanMin.setTitle("human");
        updateInvalidDataAllFieldsLessThanMin.setGender("human");
        updateInvalidDataAllFieldsLessThanMin.setDateOfBirth("1899/12/31");
        Location locationLessThanMin = new Location(
                RandomStringUtils.random(4, true, false),
                RandomStringUtils.random(1, true, false),
                RandomStringUtils.random(1, true, false),
                RandomStringUtils.random(1, true, false),
                "-13:00"
        );
        updateInvalidDataAllFieldsLessThanMin.setLocation(locationLessThanMin);

        UserModel updateInvalidDataAllFieldsMoreThanMax = new UserModel();
        updateInvalidDataAllFieldsMoreThanMax.setFirstName(RandomStringUtils.random(31, true, false));
        updateInvalidDataAllFieldsMoreThanMax.setLastName(RandomStringUtils.random(31, true, false));
        updateInvalidDataAllFieldsMoreThanMax.setEmail(RandomStringUtils.random(7, true, false));
        updateInvalidDataAllFieldsMoreThanMax.setPhone(RandomStringUtils.random(21, false, true));
        updateInvalidDataAllFieldsMoreThanMax.setPicture(RandomStringUtils.random(10, true, true));
        updateInvalidDataAllFieldsMoreThanMax.setDateOfBirth(formatDate("E MMM dd yyyy HH:mm:ss",
                rand.nextInt(20), rand.nextInt(20)));
        updateInvalidDataAllFieldsMoreThanMax.setTitle("human");
        updateInvalidDataAllFieldsMoreThanMax.setGender("human");
        Location locationMoreThanMax = new Location(
                RandomStringUtils.random(101, true, false),
                RandomStringUtils.random(31, true, false),
                RandomStringUtils.random(31, true, false),
                RandomStringUtils.random(31, true, false),
                "+15:00"
        );
        updateInvalidDataAllFieldsMoreThanMax.setLocation(locationMoreThanMax);

        return new UserModel[][]{
                {updateInvalidDataAllFieldsLessThanMin},
                {updateInvalidDataAllFieldsMoreThanMax},
        };
    }
    @DataProvider(name = "update_only_email")
    public static Object[][] createDataUpdateEmail() {
        UserModel userUpdateEmail = new UserModel();
        userUpdateEmail.setEmail(RandomStringUtils.random(6, true, true).toLowerCase() + DOMAIN1);

        UserModel userUpdateWrongFormatEmail = new UserModel();
        userUpdateEmail.setEmail(RandomStringUtils.random(6, true, true).toLowerCase());

        return new UserModel[][]{
                {userUpdateEmail},
                {userUpdateWrongFormatEmail},
        };
    }

    @DataProvider(name = "update_id_of_user")
    public static Object[][] createDataUpdateId() {
        UserModel userUpdateIdWrongLength = new UserModel();
        userUpdateIdWrongLength.setId(RandomStringUtils.random(6, true, true));

        UserModel userUpdateWrongId = new UserModel();
        userUpdateWrongId.setId(RandomStringUtils.random(24, false, true));

        return new UserModel[][]{
                {userUpdateIdWrongLength},
                {userUpdateWrongId},
        };
    }

    @DataProvider(name = "create_user_xss_inj")
    public static Object[][] createDataXssInjOnCreateUser() {
        UserModel userWithXss = new UserModel(
                "<script>alert(\"XSS\")</script>",
                "<script>alert(\"XSS\")</script>",
                RandomStringUtils.random(7, true, true).toLowerCase() + DOMAIN2,
                Gender.MALE.getGenderType(),
                Title.DR.getTitleType()
        );
        return new UserModel[][]{
                {userWithXss},
        };
    }

    @DataProvider(name = "update_user_first_and_last_name_xss_injection")
    public static Object[][] createDataXssInjOnUpdateUser() {
        UserModel userWithXss = new UserModel();
        userWithXss.setFirstName("<script>alert(\"XSS\")</script>");
        userWithXss.setLastName("<script>alert(\"XSS\")</script>");
        return new UserModel[][]{
                {userWithXss},
        };
    }

}
