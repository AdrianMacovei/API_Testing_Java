package io.dummy_api.user;

import io.dummy_api.models.UserModel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserApiMethods {

    private static final String API_ID = "63d233c888cdfd33faa635a4";
    private static final String BASE_URI = "https://dummyapi.io/data/v1/user";

    public static String createUser(UserModel user) {
        Response response = RestAssured.given().header("app-id", API_ID)
                .contentType(ContentType.JSON).body(user).post(BASE_URI + "/create");

        return response.jsonPath().getString("id");
    }

    public static Response getUser(String userId) {
        return RestAssured.given().header("app-id", API_ID)
                .contentType(ContentType.JSON).get(BASE_URI + "/" + userId);
    }

    public static void deleteUser(String userId) {
        RestAssured.given().header("app-id", API_ID)
                .contentType(ContentType.JSON).delete(BASE_URI + "/" + userId);
    }

    public static Response getCreatedUsers() {
        return RestAssured.given().header("app-id", API_ID)
                .contentType(ContentType.JSON).param("created", 50).get(BASE_URI);
    }
}
