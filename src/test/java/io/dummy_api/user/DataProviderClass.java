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

    private static final String ERROR_DATA_MESSAGE_EMPTY_REQUIRED_FIELDS = "Path `%s` is required.";

    private static final String ERROR_LESS_THAN_MIN_FIELD =
            "Path `%s` (`%s`) is shorter than the minimum allowed length (%s).";

    private static final String ERROR_MORE_THAN_MAX_FIELD =
            "Path `%s` (`%s`) is longer than the maximum allowed length (%s).";

    private static final String MIN_FIRST_OR_LAST_NAME = "2";
    private static final String MAX_FIRST_OR_LAST_NAME = "30";
    private static final String MIN_PHONE = "5";
    private static final String MAX_PHONE = "20";

    private static final String ERROR_DATA_MESSAGE_WRONG_FORMAT_EMAIL = "Path `email` is invalid (%s).";
    private static final String ERROR_DATA_MESSAGE_TOO_LOW_DATE_OF_BIRTH =
            "Path `dateOfBirth` (Sun Dec 31 1899 00:00:00 GMT+0000 (Coordinated Universal Time)) is " +
                    "before minimum allowed value (Mon Jan 01 1900 00:00:00 GMT+0000 (Coordinated Universal Time)).";
    private static final String ERROR_DATA_MESSAGE_AFTER_MAX_DATE_OF_BIRTH =
            "Path `dateOfBirth` (%s GMT+0000 (Coordinated Universal Time))" +
                    " is after maximum allowed value";

    private static final String ERROR_DATA_MESSAGE_WRONG_DATE_OF_BIRTH =
            "Cast to date failed for value \"%s\" (type string) at path \"dateOfBirth\"";


    private static final String DOMAIN1 = "@gmail.com";
    private static final String DOMAIN2 = java.lang.String.format("@%s.com",
            RandomStringUtils.randomAlphabetic(5).toLowerCase());

    protected static Random rand = new Random();

    @DataProvider(name = "invalid_ids")
    public static Object[][] createDataIds() {
        return new Object[][]{
                {RandomStringUtils.randomAlphanumeric(23).toLowerCase()},
                {RandomStringUtils.randomAlphanumeric(24).toLowerCase()},
                {RandomStringUtils.randomNumeric(24)},
                {RandomStringUtils.randomAlphabetic(24).toLowerCase()},
                {RandomStringUtils.randomAlphanumeric(25).toLowerCase()},
                {"63d233c888cdfd33faa635a4"},
                {"63d23" + RandomStringUtils.randomAlphanumeric(14).toLowerCase() + "635a4"},
                {""},
                {" "}
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
                RandomStringUtils.randomAlphabetic(7),
                RandomStringUtils.randomAlphanumeric(7) + DOMAIN1,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelOneStringFirstName = new UserModel("A",
                RandomStringUtils.randomAlphabetic(7),
                RandomStringUtils.randomAlphanumeric(7) + DOMAIN2,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());
        String errorMessageOneCharFirstName =
                String.format(ERROR_LESS_THAN_MIN_FIELD, "firstName",
                        userModelOneStringFirstName.getFirstName(), MIN_FIRST_OR_LAST_NAME);

        UserModel userModelMaxStringFirstName = new UserModel(RandomStringUtils.randomAlphabetic(31),
                RandomStringUtils.randomAlphabetic(7),
                RandomStringUtils.randomAlphanumeric(7) + DOMAIN2,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());
        String errorMessageMaxStringFirstName =
                String.format(ERROR_MORE_THAN_MAX_FIELD, "firstName",
                        userModelMaxStringFirstName.getFirstName(), MAX_FIRST_OR_LAST_NAME);


        return new Object[][]{
                {userModelEmptyFirstName, String.format(ERROR_DATA_MESSAGE_EMPTY_REQUIRED_FIELDS, "firstName")},
                {userModelOneStringFirstName, errorMessageOneCharFirstName},
                {userModelMaxStringFirstName, errorMessageMaxStringFirstName},
        };
    }

    @DataProvider(name = "invalid_lastName_data")
    public static Object[][] createInvalidLastName() {
        UserModel userModelEmptyLastName = new UserModel(RandomStringUtils.randomAlphabetic(2),
                null,
                RandomStringUtils.randomAlphanumeric(7) + DOMAIN2,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelOneStringLastName = new UserModel(RandomStringUtils.randomAlphabetic(30),
                RandomStringUtils.randomAlphabetic(1),
                RandomStringUtils.randomAlphanumeric(7) + DOMAIN2,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());
        String errorMessageOneCharLastName =
                String.format(ERROR_LESS_THAN_MIN_FIELD, "lastName",
                        userModelOneStringLastName.getLastName(), MIN_FIRST_OR_LAST_NAME);

        UserModel userModelMaxStringLastName = new UserModel(RandomStringUtils.randomAlphabetic(2),
                RandomStringUtils.randomAlphabetic(31),
                RandomStringUtils.randomAlphanumeric(7) + DOMAIN1,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());
        String errorMessageMaxStringLastName =
                String.format(ERROR_MORE_THAN_MAX_FIELD, "lastName",
                        userModelMaxStringLastName.getLastName(), MAX_FIRST_OR_LAST_NAME);


        return new Object[][]{
                {userModelEmptyLastName, String.format(ERROR_DATA_MESSAGE_EMPTY_REQUIRED_FIELDS, "lastName")},
                {userModelOneStringLastName, errorMessageOneCharLastName},
                {userModelMaxStringLastName, errorMessageMaxStringLastName},
        };
    }

    @DataProvider(name = "invalid_email_data")
    public static Object[][] createInvalidEmail() {
        UserModel userModelEmptyEmail = new UserModel(RandomStringUtils.randomAlphabetic(2),
                RandomStringUtils.randomAlphabetic(29),
                null,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatEmail = new UserModel(RandomStringUtils.randomAlphabetic(2),
                RandomStringUtils.randomAlphabetic(29),
                RandomStringUtils.randomAlphanumeric(10).toLowerCase(),
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());
        String errorMessageWrongFormatEmail = String.format(ERROR_DATA_MESSAGE_WRONG_FORMAT_EMAIL,
                userModelWrongFormatEmail.getEmail());

        return new Object[][]{
                {userModelEmptyEmail, String.format(ERROR_DATA_MESSAGE_EMPTY_REQUIRED_FIELDS, "email")},
                {userModelWrongFormatEmail, errorMessageWrongFormatEmail}
        };
    }

    @DataProvider(name = "invalid_first_and_last_name_data")
    public static Object[][] createInvalidFirstAndLastName() {
        UserModel userModelEmptyFirstNameLastName = new UserModel(null,
                null,
                RandomStringUtils.randomAlphanumeric(7) + DOMAIN1,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatMinFirstNameAndLastName = new UserModel(
                RandomStringUtils.randomAlphabetic(1),
                RandomStringUtils.randomAlphabetic(1),
                RandomStringUtils.randomAlphanumeric(7) + DOMAIN1,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatMaxFirstNameAndLastName = new UserModel(
                RandomStringUtils.randomAlphabetic(31),
                RandomStringUtils.randomAlphabetic(31),
                RandomStringUtils.randomAlphanumeric(7) + DOMAIN1,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        return new Object[][]
                {
                        {
                                userModelEmptyFirstNameLastName,
                                String.format(ERROR_DATA_MESSAGE_EMPTY_REQUIRED_FIELDS, "firstName"),
                                String.format(ERROR_DATA_MESSAGE_EMPTY_REQUIRED_FIELDS, "lastName")
                        },
                        {
                                userModelWrongFormatMinFirstNameAndLastName,
                                String.format(ERROR_LESS_THAN_MIN_FIELD, "firstName",
                                        userModelWrongFormatMinFirstNameAndLastName.getFirstName(), MIN_FIRST_OR_LAST_NAME),
                                String.format(ERROR_LESS_THAN_MIN_FIELD, "lastName",
                                        userModelWrongFormatMinFirstNameAndLastName.getLastName(), MIN_FIRST_OR_LAST_NAME)
                        },
                        {
                                userModelWrongFormatMaxFirstNameAndLastName,
                                String.format(ERROR_MORE_THAN_MAX_FIELD, "firstName",
                                        userModelWrongFormatMaxFirstNameAndLastName.getFirstName(), MAX_FIRST_OR_LAST_NAME),
                                String.format(ERROR_MORE_THAN_MAX_FIELD, "lastName",
                                        userModelWrongFormatMaxFirstNameAndLastName.getLastName(), MAX_FIRST_OR_LAST_NAME)
                        },
                };
    }

    @DataProvider(name = "invalid_first_name_and_email_data")
    public static Object[][] createInvalidFirstNameAndEmail() {
        UserModel userModelEmptyFirstNameAndEmail = new UserModel(
                null,
                RandomStringUtils.randomAlphabetic(15),
                null,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatMinFirstEmptyEmail = new UserModel(
                RandomStringUtils.randomAlphabetic(1),
                RandomStringUtils.randomAlphabetic(20),
                null,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatMaxFirstWrongFormatEmail = new UserModel(
                RandomStringUtils.randomAlphabetic(31),
                RandomStringUtils.randomAlphabetic(20),
                RandomStringUtils.randomAlphanumeric(7).toLowerCase(),
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        return new Object[][]
                {
                        {
                                userModelEmptyFirstNameAndEmail,
                                String.format(ERROR_DATA_MESSAGE_EMPTY_REQUIRED_FIELDS, "firstName"),
                                String.format(ERROR_DATA_MESSAGE_EMPTY_REQUIRED_FIELDS, "email")
                        },
                        {
                                userModelWrongFormatMinFirstEmptyEmail,
                                String.format(ERROR_LESS_THAN_MIN_FIELD, "firstName",
                                        userModelWrongFormatMinFirstEmptyEmail.getFirstName(), MIN_FIRST_OR_LAST_NAME),
                                String.format(ERROR_DATA_MESSAGE_EMPTY_REQUIRED_FIELDS, "email")
                        },
                        {
                                userModelWrongFormatMaxFirstWrongFormatEmail,
                                String.format(ERROR_MORE_THAN_MAX_FIELD, "firstName",
                                        userModelWrongFormatMaxFirstWrongFormatEmail.getFirstName(),
                                        MAX_FIRST_OR_LAST_NAME),
                                String.format(ERROR_DATA_MESSAGE_WRONG_FORMAT_EMAIL,
                                        userModelWrongFormatMaxFirstWrongFormatEmail.getEmail()),
                        }
                };
    }

    @DataProvider(name = "invalid_last_name_and_email_data")
    public static Object[][] createInvalidLastNameAndEmail() {
        UserModel userModelEmptyLastNameAndEmail = new UserModel(
                RandomStringUtils.randomAlphabetic(15),
                null,
                null,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatMinLastEmptyEmail = new UserModel(
                RandomStringUtils.randomAlphabetic(15),
                RandomStringUtils.randomAlphabetic(1),
                null,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatMaxLastWrongFormatEmail = new UserModel(
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(31),
                RandomStringUtils.randomAlphanumeric(7).toLowerCase(),
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        return new Object[][]
                {
                        {
                                userModelEmptyLastNameAndEmail,
                                String.format(ERROR_DATA_MESSAGE_EMPTY_REQUIRED_FIELDS, "lastName"),
                                String.format(ERROR_DATA_MESSAGE_EMPTY_REQUIRED_FIELDS, "email")
                        },
                        {
                                userModelWrongFormatMinLastEmptyEmail,
                                String.format(ERROR_LESS_THAN_MIN_FIELD, "lastName",
                                        userModelWrongFormatMinLastEmptyEmail.getLastName(), MIN_FIRST_OR_LAST_NAME),
                                String.format(ERROR_DATA_MESSAGE_EMPTY_REQUIRED_FIELDS, "email")
                        },
                        {
                                userModelWrongFormatMaxLastWrongFormatEmail,
                                String.format(ERROR_MORE_THAN_MAX_FIELD, "lastName",
                                        userModelWrongFormatMaxLastWrongFormatEmail.getLastName(), MAX_FIRST_OR_LAST_NAME),
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
                RandomStringUtils.randomAlphabetic(1),
                RandomStringUtils.randomAlphabetic(1),
                null,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userModelWrongFormatMaxFirstNameLasNameWrongFormatEmail = new UserModel(
                RandomStringUtils.randomAlphabetic(31),
                RandomStringUtils.randomAlphabetic(31),
                RandomStringUtils.randomAlphanumeric(7).toLowerCase(),
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        return new Object[][]
                {
                        {
                                userModelEmptyFirstNameLastNameAndEmail,
                                String.format(ERROR_DATA_MESSAGE_EMPTY_REQUIRED_FIELDS, "firstName"),
                                String.format(ERROR_DATA_MESSAGE_EMPTY_REQUIRED_FIELDS, "lastName"),
                                String.format(ERROR_DATA_MESSAGE_EMPTY_REQUIRED_FIELDS, "email")
                        },
                        {
                                userModelWrongFormatMinFirstNameLastNameEmptyEmail,
                                String.format(ERROR_LESS_THAN_MIN_FIELD, "firstName",
                                        userModelWrongFormatMinFirstNameLastNameEmptyEmail.getFirstName(),
                                        MIN_FIRST_OR_LAST_NAME),
                                String.format(ERROR_LESS_THAN_MIN_FIELD, "lastName",
                                        userModelWrongFormatMinFirstNameLastNameEmptyEmail.getLastName(),
                                        MIN_FIRST_OR_LAST_NAME),
                                String.format(ERROR_DATA_MESSAGE_EMPTY_REQUIRED_FIELDS, "email")
                        },
                        {
                                userModelWrongFormatMaxFirstNameLasNameWrongFormatEmail,
                                String.format(ERROR_MORE_THAN_MAX_FIELD, "firstName",
                                        userModelWrongFormatMaxFirstNameLasNameWrongFormatEmail.getFirstName(),
                                        MAX_FIRST_OR_LAST_NAME),
                                String.format(ERROR_MORE_THAN_MAX_FIELD, "lastName",
                                        userModelWrongFormatMaxFirstNameLasNameWrongFormatEmail.getLastName(),
                                        MAX_FIRST_OR_LAST_NAME),
                                String.format(ERROR_DATA_MESSAGE_WRONG_FORMAT_EMAIL,
                                        userModelWrongFormatMaxFirstNameLasNameWrongFormatEmail.getEmail()),
                        }
                };
    }

    @DataProvider(name = "user_valid_data")
    public static Object[][] createValidDataForUser() {
        UserModel userWithTwoCharactersFirstAndLastName = new UserModel(
                RandomStringUtils.randomAlphabetic(2),
                RandomStringUtils.randomAlphabetic(2),
                RandomStringUtils.randomAlphanumeric(6).toLowerCase() + DOMAIN1,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userWithThirtyCharactersInFirstAndLastName = new UserModel(
                RandomStringUtils.randomAlphabetic(30),
                RandomStringUtils.randomAlphabetic(30),
                RandomStringUtils.randomAlphanumeric(31).toLowerCase() + DOMAIN2,
                Gender.MALE.getGenderType(),
                Title.MR.getTitleType());

        UserModel userWithValidDataInField = new UserModel(
                RandomStringUtils.randomAlphabetic(15),
                RandomStringUtils.randomAlphabetic(15),
                RandomStringUtils.randomAlphanumeric(1).toLowerCase() + DOMAIN2,
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
                RandomStringUtils.randomAlphanumeric(5),
                RandomStringUtils.randomAlphanumeric(2),
                RandomStringUtils.randomAlphanumeric(2),
                RandomStringUtils.randomAlphanumeric(2),
                "-12:00"
        );
        UserModel validDataAllFieldsMinAcceptedTitleMsGenderMale = new UserModel(
                RandomStringUtils.randomAlphabetic(2),
                RandomStringUtils.randomAlphabetic(2),
                RandomStringUtils.randomAlphanumeric(6).toLowerCase() + DOMAIN1,
                Gender.MALE.getGenderType(),
                Title.MS.getTitleType(),
                RandomStringUtils.randomNumeric(5),
                "https://unsplash.com/photos/rDEOVtE7vOs",
                "1900-01-01T00:00:00.000Z",
                location1
        );


        Location location2 = new Location(
                RandomStringUtils.randomAlphanumeric(100),
                RandomStringUtils.randomAlphabetic(30),
                RandomStringUtils.randomAlphabetic(30),
                RandomStringUtils.randomAlphabetic(30),
                "+14:00"
        );
        UserModel validDataAllFieldsMaxAcceptedTitleDrGenderOther = new UserModel(
                RandomStringUtils.randomAlphabetic(30),
                RandomStringUtils.randomAlphabetic(30),
                RandomStringUtils.randomAlphabetic(6).toLowerCase() + DOMAIN2,
                Gender.OTHER.getGenderType(),
                Title.DR.getTitleType(),
                RandomStringUtils.randomNumeric(20),
                "https://unsplash.com/photos/rDEOVtE7vOs",
                formatDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", -5, 0),
                location2
        );


        UserModel validDataTitleMrGenderFemale = new UserModel(
                RandomStringUtils.randomAlphabetic(15),
                RandomStringUtils.randomAlphabetic(15),
                RandomStringUtils.randomAlphabetic(8).toLowerCase() + DOMAIN2,
                Gender.FEMALE.getGenderType(),
                Title.MR.getTitleType(),
                RandomStringUtils.randomNumeric(10),
                "https://unsplash.com/photos/rDEOVtE7vOs",
                formatDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", 0, (rand.nextInt(10) - 10)),
                location2
        );

        UserModel validDataTitleMrsGenderEmpty = new UserModel(
                RandomStringUtils.randomAlphabetic(15),
                RandomStringUtils.randomAlphabetic(15),
                RandomStringUtils.randomAlphabetic(8).toLowerCase() + DOMAIN2,
                Gender.EMPTY.getGenderType(),
                Title.MRS.getTitleType(),
                RandomStringUtils.randomNumeric(10),
                "https://unsplash.com/photos/rDEOVtE7vOs",
                formatDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", rand.nextInt(10)-10, rand.nextInt(20)-20),
                location2
        );

        UserModel validDataTitleMissGenderMale = new UserModel(
                RandomStringUtils.randomAlphabetic(15),
                RandomStringUtils.randomAlphabetic(15),
                RandomStringUtils.randomAlphabetic(8).toLowerCase() + DOMAIN2,
                Gender.MALE.getGenderType(),
                Title.MISS.getTitleType(),
                RandomStringUtils.randomNumeric(10),
                "https://unsplash.com/photos/rDEOVtE7vOs",
                formatDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", rand.nextInt(10)-10, rand.nextInt(20)-20),
                location1
        );

        UserModel validDataTitleEmptyGenderFemale = new UserModel(
                RandomStringUtils.randomAlphabetic(15),
                RandomStringUtils.randomAlphabetic(15),
                RandomStringUtils.randomAlphabetic(8).toLowerCase() + DOMAIN2,
                Gender.FEMALE.getGenderType(),
                Title.EMPTY.getTitleType(),
                RandomStringUtils.randomNumeric(10),
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
        invalidTitle.setFirstName(RandomStringUtils.randomNumeric(3));
        invalidTitle.setLastName(RandomStringUtils.randomNumeric(3));
        invalidTitle.setEmail(RandomStringUtils.randomAlphanumeric(6).toLowerCase() + DOMAIN2);
        invalidTitle.setTitle(RandomStringUtils.randomAlphabetic(3));
        invalidTitle.setGender(Gender.MALE.getGenderType());
        return new UserModel[][]{
                {invalidTitle}
        };
    }

    @DataProvider(name = "user_invalid_dateOfBirth_data")
    public static Object[][] createInvalidDateOfBirth() {
        UserModel invalidDataInDateOfBirthLessThanMin = new UserModel();
        invalidDataInDateOfBirthLessThanMin.setFirstName(RandomStringUtils.randomAlphanumeric(21));
        invalidDataInDateOfBirthLessThanMin.setLastName(RandomStringUtils.randomAlphanumeric(21));
        invalidDataInDateOfBirthLessThanMin.setEmail(RandomStringUtils.randomAlphanumeric(6).toLowerCase() + DOMAIN2);
        invalidDataInDateOfBirthLessThanMin.setTitle(Title.MISS.getTitleType());
        invalidDataInDateOfBirthLessThanMin.setGender(Gender.MALE.getGenderType());
        invalidDataInDateOfBirthLessThanMin.setDateOfBirth("1899/12/31");

        UserModel invalidDataInDateOfBirthMoreThanMax = new UserModel();
        invalidDataInDateOfBirthMoreThanMax.setFirstName(RandomStringUtils.randomAlphanumeric(21));
        invalidDataInDateOfBirthMoreThanMax.setLastName(RandomStringUtils.randomAlphanumeric(21));
        invalidDataInDateOfBirthMoreThanMax.setEmail(RandomStringUtils.randomAlphanumeric(6).toLowerCase() + DOMAIN2);
        invalidDataInDateOfBirthMoreThanMax.setTitle(Title.MISS.getTitleType());
        invalidDataInDateOfBirthMoreThanMax.setGender(Gender.MALE.getGenderType());
        String dateNowPlus = formatDate("E MMM dd yyyy HH:mm:ss", rand.nextInt(20), rand.nextInt(20));
        invalidDataInDateOfBirthMoreThanMax.setDateOfBirth(dateNowPlus);

        UserModel wrongFormatDateOfBirth = new UserModel();
        wrongFormatDateOfBirth.setFirstName(RandomStringUtils.randomAlphanumeric(21));
        wrongFormatDateOfBirth.setLastName(RandomStringUtils.randomAlphanumeric(21));
        wrongFormatDateOfBirth.setEmail(RandomStringUtils.randomAlphanumeric(6).toLowerCase() + DOMAIN2);
        wrongFormatDateOfBirth.setTitle(Title.MISS.getTitleType());
        wrongFormatDateOfBirth.setGender(Gender.MALE.getGenderType());
        wrongFormatDateOfBirth.setDateOfBirth("15/05/2020");

        UserModel randomStringNumericAndAlphabeticalDateOfBirth = new UserModel();
        randomStringNumericAndAlphabeticalDateOfBirth.setFirstName(RandomStringUtils.randomAlphanumeric(21));
        randomStringNumericAndAlphabeticalDateOfBirth.setLastName(RandomStringUtils.randomAlphanumeric(21));
        randomStringNumericAndAlphabeticalDateOfBirth.setEmail(RandomStringUtils.randomAlphanumeric(6).toLowerCase() + DOMAIN2);
        randomStringNumericAndAlphabeticalDateOfBirth.setTitle(Title.MISS.getTitleType());
        randomStringNumericAndAlphabeticalDateOfBirth.setGender(Gender.MALE.getGenderType());
        String randomBirthDateNumAndAlphabetic = RandomStringUtils.randomAlphanumeric(6);
        randomStringNumericAndAlphabeticalDateOfBirth.setDateOfBirth(randomBirthDateNumAndAlphabetic);

        UserModel randomStringOnlyNumericalDateOfBirth = new UserModel();
        randomStringOnlyNumericalDateOfBirth.setFirstName(RandomStringUtils.randomAlphanumeric(21));
        randomStringOnlyNumericalDateOfBirth.setLastName(RandomStringUtils.randomAlphanumeric(21));
        randomStringOnlyNumericalDateOfBirth.setEmail(RandomStringUtils.randomAlphanumeric(6).toLowerCase() + DOMAIN2);
        randomStringOnlyNumericalDateOfBirth.setTitle(Title.MISS.getTitleType());
        randomStringOnlyNumericalDateOfBirth.setGender(Gender.MALE.getGenderType());
        String randomBirthDateOnlyNumeric = RandomStringUtils.randomNumeric(8);
        randomStringOnlyNumericalDateOfBirth.setDateOfBirth(randomBirthDateOnlyNumeric);

        UserModel emptyStringDateOfBirth = new UserModel();
        emptyStringDateOfBirth.setFirstName(RandomStringUtils.randomAlphanumeric(21));
        emptyStringDateOfBirth.setLastName(RandomStringUtils.randomAlphanumeric(21));
        emptyStringDateOfBirth.setEmail(RandomStringUtils.randomAlphanumeric(6).toLowerCase() + DOMAIN2);
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
        invalidGender.setFirstName(RandomStringUtils.randomNumeric(3));
        invalidGender.setLastName(RandomStringUtils.randomNumeric(3));
        invalidGender.setEmail(RandomStringUtils.randomAlphanumeric(6).toLowerCase() + DOMAIN2);
        invalidGender.setTitle(Title.MISS.getTitleType());
        invalidGender.setGender(RandomStringUtils.randomAlphabetic(4));

        return new UserModel[][]{
                {invalidGender}
        };
    }

    @DataProvider(name = "user_invalid_phone_data")
    public static Object[][] createInvalidPhoneData() {
        UserModel invalidDataInPhoneLessThanMin = new UserModel();
        invalidDataInPhoneLessThanMin.setFirstName(RandomStringUtils.randomAlphanumeric(21));
        invalidDataInPhoneLessThanMin.setLastName(RandomStringUtils.randomAlphanumeric(21));
        invalidDataInPhoneLessThanMin.setEmail(RandomStringUtils.randomAlphanumeric(6).toLowerCase() + DOMAIN2);
        invalidDataInPhoneLessThanMin.setTitle(Title.MISS.getTitleType());
        invalidDataInPhoneLessThanMin.setGender(Gender.MALE.getGenderType());
        String randomPhone1 = RandomStringUtils.randomNumeric(4);
        invalidDataInPhoneLessThanMin.setPhone(randomPhone1);

        UserModel invalidDataInPhoneMoreThanMax = new UserModel();
        invalidDataInPhoneMoreThanMax.setFirstName(RandomStringUtils.randomAlphanumeric(21));
        invalidDataInPhoneMoreThanMax.setLastName(RandomStringUtils.randomAlphanumeric(21));
        invalidDataInPhoneMoreThanMax.setEmail(RandomStringUtils.randomAlphanumeric(6).toLowerCase() + DOMAIN2);
        invalidDataInPhoneMoreThanMax.setTitle(Title.MISS.getTitleType());
        invalidDataInPhoneMoreThanMax.setGender(Gender.MALE.getGenderType());
        String randomPhone2 = RandomStringUtils.randomNumeric(21);
        invalidDataInPhoneMoreThanMax.setPhone(randomPhone2);

        UserModel invalidDataInPhoneOnlyAlphabetical = new UserModel();
        invalidDataInPhoneOnlyAlphabetical.setFirstName(RandomStringUtils.randomAlphanumeric(21));
        invalidDataInPhoneOnlyAlphabetical.setLastName(RandomStringUtils.randomAlphanumeric(21));
        invalidDataInPhoneOnlyAlphabetical.setEmail(RandomStringUtils.randomAlphanumeric(6).toLowerCase() + DOMAIN2);
        invalidDataInPhoneOnlyAlphabetical.setTitle(Title.MISS.getTitleType());
        invalidDataInPhoneOnlyAlphabetical.setGender(Gender.MALE.getGenderType());
        String randomPhone3 = RandomStringUtils.randomAlphabetic(10);
        invalidDataInPhoneOnlyAlphabetical.setPhone(randomPhone3);

        return new Object[][]{
                {invalidDataInPhoneLessThanMin, String.format(ERROR_LESS_THAN_MIN_FIELD, "phone", randomPhone1,
                        MIN_PHONE)},
                {invalidDataInPhoneMoreThanMax, String.format(ERROR_MORE_THAN_MAX_FIELD, "phone", randomPhone2,
                        MAX_PHONE)},
                {invalidDataInPhoneOnlyAlphabetical, "Invalid phone number"},
        };
    }

    @DataProvider(name = "user_invalid_location_data")
    public static Object[][] createInvalidLocationData() {
        UserModel invalidDataInLocationFieldsLessThanMinAccepted = new UserModel();
        invalidDataInLocationFieldsLessThanMinAccepted.setFirstName(
                RandomStringUtils.randomAlphanumeric(21));
        invalidDataInLocationFieldsLessThanMinAccepted.setLastName(
                RandomStringUtils.randomAlphanumeric(21));
        invalidDataInLocationFieldsLessThanMinAccepted.setEmail(
                RandomStringUtils.randomAlphanumeric(6).toLowerCase() + DOMAIN2);
        invalidDataInLocationFieldsLessThanMinAccepted.setTitle(Title.MISS.getTitleType());
        invalidDataInLocationFieldsLessThanMinAccepted.setGender(Gender.MALE.getGenderType());
        Location locationInvalidLessThanMinAccepted = new Location(
                RandomStringUtils.randomAlphanumeric(4),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                "-13:00"
        );
        invalidDataInLocationFieldsLessThanMinAccepted.setLocation(locationInvalidLessThanMinAccepted);

        UserModel invalidDataInLocationFieldsMoreThanMaxAccepted = new UserModel();
        invalidDataInLocationFieldsMoreThanMaxAccepted.setFirstName(
                RandomStringUtils.randomAlphanumeric(10));
        invalidDataInLocationFieldsMoreThanMaxAccepted.setLastName(
                RandomStringUtils.randomAlphanumeric(10));
        invalidDataInLocationFieldsMoreThanMaxAccepted.setEmail(
                RandomStringUtils.randomAlphanumeric(10).toLowerCase() + DOMAIN2);
        invalidDataInLocationFieldsMoreThanMaxAccepted.setTitle(Title.MISS.getTitleType());
        invalidDataInLocationFieldsMoreThanMaxAccepted.setGender(Gender.MALE.getGenderType());
        Location locationInvalidMoreThanMaxAccepted = new Location(
                RandomStringUtils.randomAlphanumeric(101),
                RandomStringUtils.randomAlphanumeric(31),
                RandomStringUtils.randomAlphanumeric(31),
                RandomStringUtils.randomAlphanumeric(31),
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
                RandomStringUtils.randomAlphanumeric(1));
        invalidAllFields.setLastName(
                RandomStringUtils.randomAlphanumeric(1));
        invalidAllFields.setEmail(
                RandomStringUtils.randomAlphanumeric(10).toLowerCase());
        invalidAllFields.setTitle("Something");
        invalidAllFields.setGender("Something");
        Location locationInvalidAllFields = new Location(
                RandomStringUtils.randomAlphanumeric(101),
                RandomStringUtils.randomAlphanumeric(31),
                RandomStringUtils.randomAlphanumeric(31),
                RandomStringUtils.randomAlphanumeric(31),
                RandomStringUtils.randomAlphanumeric(6)
        );
        invalidAllFields.setLocation(locationInvalidAllFields);
        invalidAllFields.setPhone(RandomStringUtils.randomNumeric(21));
        String randomBirth = RandomStringUtils.randomAlphanumeric(8);
        invalidAllFields.setDateOfBirth(randomBirth);

        return new Object[][]{
                {invalidAllFields,
                        String.format(ERROR_LESS_THAN_MIN_FIELD, "firstName", invalidAllFields.getFirstName(),
                                MIN_FIRST_OR_LAST_NAME),
                        String.format(ERROR_LESS_THAN_MIN_FIELD, "lastName", invalidAllFields.getLastName(),
                                MIN_FIRST_OR_LAST_NAME),
                        String.format(ERROR_DATA_MESSAGE_WRONG_FORMAT_EMAIL, invalidAllFields.getEmail()),
                        String.format(ERROR_DATA_MESSAGE_WRONG_DATE_OF_BIRTH, invalidAllFields.getDateOfBirth()),
                        String.format(ERROR_MORE_THAN_MAX_FIELD, "phone", invalidAllFields.getPhone(), MAX_PHONE)
                }
        };
    }

    @DataProvider(name = "update_valid_first_and_last_name_data")
    public static Object[][] updateValidFirstAndLastName()
    {
        UserModel updateFirstAndLastNameValidMinValues = new UserModel();
        updateFirstAndLastNameValidMinValues.setFirstName(RandomStringUtils.randomAlphabetic(2));
        updateFirstAndLastNameValidMinValues.setLastName(RandomStringUtils.randomAlphabetic(2));

        UserModel updateFirstAndLastNameValidMaxValues = new UserModel();
        updateFirstAndLastNameValidMaxValues.setFirstName(RandomStringUtils.randomAlphabetic(30));
        updateFirstAndLastNameValidMaxValues.setLastName(RandomStringUtils.randomAlphabetic(30));

        return new Object[][]{
                {updateFirstAndLastNameValidMinValues},
                {updateFirstAndLastNameValidMaxValues},
        };
    }

    @DataProvider(name = "update_invalid_first_and_last_name_data")
    public static Object[][] updateInvalidFirstAndLastName()
    {
        UserModel updateFirstAndLastNameValidMinValues = new UserModel();
        updateFirstAndLastNameValidMinValues.setFirstName(RandomStringUtils.randomAlphabetic(1));
        updateFirstAndLastNameValidMinValues.setLastName(RandomStringUtils.randomAlphabetic(1));

        UserModel updateFirstAndLastNameValidMaxValues = new UserModel();
        updateFirstAndLastNameValidMaxValues.setFirstName(RandomStringUtils.randomAlphabetic(31));
        updateFirstAndLastNameValidMaxValues.setLastName(RandomStringUtils.randomAlphabetic(31));

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
        updateValidDataAllFields.setFirstName(RandomStringUtils.randomAlphabetic(15));
        updateValidDataAllFields.setLastName(RandomStringUtils.randomAlphabetic(15));
        updateValidDataAllFields.setPhone(RandomStringUtils.randomNumeric(10));
        updateValidDataAllFields.setPicture(RandomStringUtils.randomAlphanumeric(10));
        updateValidDataAllFields.setTitle(Title.MR.getTitleType());
        updateValidDataAllFields.setGender(Gender.MALE.getGenderType());
        updateValidDataAllFields.setLocation(Location.generateRandomLocation());

        return new UserModel[][]{
                {updateValidDataAllFields},
        };
    }

    @DataProvider(name = "update_all_fields_invalid_data")
    public static Object[][] updateAllFieldsInvalidData()
    {
        UserModel updateInvalidDataAllFieldsLessThanMin = new UserModel();
        updateInvalidDataAllFieldsLessThanMin.setFirstName(RandomStringUtils.randomAlphabetic(1));
        updateInvalidDataAllFieldsLessThanMin.setLastName(RandomStringUtils.randomAlphabetic(1));
        updateInvalidDataAllFieldsLessThanMin.setPhone(RandomStringUtils.randomNumeric(4));
        updateInvalidDataAllFieldsLessThanMin.setPicture(RandomStringUtils.randomAlphanumeric(10));
        updateInvalidDataAllFieldsLessThanMin.setTitle("human");
        updateInvalidDataAllFieldsLessThanMin.setGender("human");
        updateInvalidDataAllFieldsLessThanMin.setDateOfBirth("1899/12/31");
        Location locationLessThanMin = new Location(
                RandomStringUtils.randomAlphabetic(4),
                RandomStringUtils.randomAlphabetic(1),
                RandomStringUtils.randomAlphabetic(1),
                RandomStringUtils.randomAlphabetic(1),
                "-13:00"
        );
        updateInvalidDataAllFieldsLessThanMin.setLocation(locationLessThanMin);

        UserModel updateInvalidDataAllFieldsMoreThanMax = new UserModel();
        updateInvalidDataAllFieldsMoreThanMax.setFirstName(RandomStringUtils.randomAlphabetic(31));
        updateInvalidDataAllFieldsMoreThanMax.setLastName(RandomStringUtils.randomAlphabetic(31));
        updateInvalidDataAllFieldsMoreThanMax.setEmail(RandomStringUtils.randomAlphabetic(7));
        updateInvalidDataAllFieldsMoreThanMax.setPhone(RandomStringUtils.randomNumeric(21));
        updateInvalidDataAllFieldsMoreThanMax.setPicture(RandomStringUtils.randomAlphanumeric(10));
        updateInvalidDataAllFieldsMoreThanMax.setDateOfBirth(formatDate("E MMM dd yyyy HH:mm:ss",
                rand.nextInt(20), rand.nextInt(20)));
        updateInvalidDataAllFieldsMoreThanMax.setTitle("human");
        updateInvalidDataAllFieldsMoreThanMax.setGender("human");
        Location locationMoreThanMax = new Location(
                RandomStringUtils.randomAlphabetic(101),
                RandomStringUtils.randomAlphabetic(31),
                RandomStringUtils.randomAlphabetic(31),
                RandomStringUtils.randomAlphabetic(31),
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
        userUpdateEmail.setEmail(RandomStringUtils.randomAlphanumeric(6).toLowerCase() + DOMAIN1);

        UserModel userUpdateWrongFormatEmail = new UserModel();
        userUpdateEmail.setEmail(RandomStringUtils.randomAlphanumeric(6).toLowerCase());

        return new UserModel[][]{
                {userUpdateEmail},
                {userUpdateWrongFormatEmail},
        };
    }

    @DataProvider(name = "update_id_of_user")
    public static Object[][] createDataUpdateId() {
        UserModel userUpdateIdWrongLength = new UserModel();
        userUpdateIdWrongLength.setId(RandomStringUtils.randomAlphanumeric(6));

        UserModel userUpdateWrongId = new UserModel();
        userUpdateWrongId.setId(RandomStringUtils.randomNumeric(24));

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
                RandomStringUtils.randomAlphanumeric(7).toLowerCase() + DOMAIN2,
                Gender.MALE.getGenderType(),
                Title.DR.getTitleType()
        );
        return new UserModel[][]{
                {userWithXss},
        };
    }
}
