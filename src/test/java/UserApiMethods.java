import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;

public class UserApiMethods {

    private static final String APP_ID = "63d233c888cdfd33faa635a4";
    private static final String BASE_URI = "https://dummyapi.io/data/v1/";

    public static String createUser()
    {
        HashMap<String, String> user1 = new HashMap<>();
        user1.put("firstName", "Popescu");
        user1.put("lastName", "Marian");
        user1.put("email", "popescumarian" + RandomStringUtils.random(3, false, true) +
                "@gmail.com");

        Response response = RestAssured.given().header("app-id", UserApiMethods.APP_ID)
                .contentType(ContentType.JSON).body(user1).post(BASE_URI + "user/create");

        return response.jsonPath().getString("id");
    }

    public static Response getUser(String userId)
    {
        return RestAssured.given().header("app-id", APP_ID)
                .contentType(ContentType.JSON).get(BASE_URI + "user/" + userId);
    }

    public static void deleteUser(String userId)
    {
        RestAssured.given().header("app-id", UserApiMethods.APP_ID)
                .contentType(ContentType.JSON).delete(BASE_URI + "user/" + userId);
    }
}
