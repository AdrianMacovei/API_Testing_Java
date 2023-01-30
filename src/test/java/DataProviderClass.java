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
}