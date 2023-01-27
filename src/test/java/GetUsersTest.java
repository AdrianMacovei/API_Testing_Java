import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.http.util.Asserts;
import org.assertj.core.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetUsersTest {


    @BeforeClass
    public static void setUp()
    {
        RestAssured.baseURI = "https://dummyapi.io/data/v1/";
    }


    @Test
    void getUsersListWithValidAppId()
    {
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .get(RestAssured.baseURI + "/user");
        System.out.println(response.asString());
        System.out.println(response.getStatusCode());
        System.out.println(response.getHeader("content-type"));
        System.out.println(response.getTime());
        System.out.println(response.body().jsonPath().getList("data"));
        ArrayList<String> usersList = new ArrayList<>(response.body().jsonPath().getList("data"));
        System.out.println(usersList.size());


        Assertions.assertThat(usersList.size()).isEqualTo(20);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    void getUsersWithInvalidAppId()
    {
        Response response = RestAssured.get(RestAssured.baseURI + "/user");
        System.out.println(response.asString());
        System.out.println(response.getStatusCode());

        String jsonString = response.getBody().asString();

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();
        Map error = gson.fromJson(jsonString, HashMap.class);
        System.out.println(error);

        Assertions.assertThat(error.get("error")).isEqualTo("APP_ID_MISSING");
        Assertions.assertThat(response.statusCode()).isEqualTo(403);
    }

    @Test
    void getUsersWithLimitParam()
    {
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .param("limit", 5)
                .get(RestAssured.baseURI + "/user");
        System.out.println(response.asString());
        System.out.println(response.getStatusCode());

        ArrayList<String> usersList = new ArrayList<>(response.body().jsonPath().getList("data"));
        System.out.println(usersList.size());

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(usersList.size()).isEqualTo(5);

        String jsonString = response.getBody().asString();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Map jsonHashMap = gson.fromJson(jsonString, HashMap.class);


        Assertions.assertThat((double)usersList.size()).isEqualTo(jsonHashMap.get("limit"));
    }

}
