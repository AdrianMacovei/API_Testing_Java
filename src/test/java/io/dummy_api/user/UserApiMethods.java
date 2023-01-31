package io.dummy_api.user;

import io.dummy_api.ApiBaseClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


@Getter
@Configuration
public class UserApiMethods extends ApiBaseClass {
    private final String APP_ID = properties.getAppId();
    private final String BASE_URI = properties.getApiUri();

    public String createUser()
    {
        HashMap<String, String> user1 = new HashMap<>();
        user1.put("firstName", "Popescu");
        user1.put("lastName", "Marian");
        user1.put("email", "popescumarian" + RandomStringUtils.random(3, false, true) +
                "@gmail.com");

        Response response = RestAssured.given().header("app-id", getAPP_ID())
                .contentType(ContentType.JSON).body(user1).post(getBASE_URI() + "create");

        return response.jsonPath().getString("id");
    }

    public Response getUser(String userId)
    {
        return RestAssured.given().header("app-id", getAPP_ID())
                .contentType(ContentType.JSON).get(getBASE_URI() + userId);
    }

    public void deleteUser(String userId)
    {
        RestAssured.given().header("app-id", getAPP_ID())
                .contentType(ContentType.JSON).delete(getBASE_URI() + userId);
    }
}
