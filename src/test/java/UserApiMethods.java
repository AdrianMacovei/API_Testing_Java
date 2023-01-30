import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

public class UserApiMethods {

    private static final String APP_ID = "63d233c888cdfd33faa635a4";

    public static String createUser()
    {
        HashMap<String, String> user1 = new HashMap<>();
        user1.put("firstName", "Popescu");
        user1.put("lastName", "Marian");
        user1.put("email", "popescumarian@gmail.com");

        Response response = RestAssured.given().header("app-id", APP_ID)
                .contentType(ContentType.JSON).body(user1).post(RestAssured.baseURI + "/user/create");

        return response.jsonPath().getString("id");
    }

    public static Response getUser(String userId)
    {
        return RestAssured.given().header("app-id", APP_ID)
                .contentType(ContentType.JSON).get(RestAssured.baseURI + "/user/" + userId);
    }

    public static void deleteUser(String userId)
    {
        RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .contentType(ContentType.JSON).delete(RestAssured.baseURI + "/user/" + userId);
    }
}
