package io.dummy_api.user;

import io.dummy_api.ApiBaseClass;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import java.util.ArrayList;
import static org.apache.http.HttpStatus.*;

public class GetUsersTest extends ApiBaseClass {

    @Test
    void getUsersListWithValidAppId()
    {
        Response response = RestAssured.given().header("app-id", getAppId())
                .get("user");
        response.body().prettyPrint();
        System.out.println(response.getStatusCode());

        ArrayList<String> usersList = new ArrayList<>(response.body().jsonPath().getList("data"));

        Assertions.assertThat(usersList.size()).isEqualTo(20);
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema_list.json"));
    }

    @Test
    void getUsersWithInvalidAppId()
    {
        Response response = RestAssured.get("user");
        response.body().prettyPrint();
        System.out.println(response.getStatusCode());

        JsonPath jsonPath = response.body().jsonPath();
        String errorValue = jsonPath.getString("error");


        Assertions.assertThat(errorValue).isEqualTo("APP_ID_MISSING");
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_FORBIDDEN);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "limit_values")
    void getUsersWithLimitParam(int limit_value)
    {
        Response response = RestAssured.given().header("app-id", getAppId())
                .param("limit", limit_value)
                .get("user");
        response.body().prettyPrint();
        System.out.println(response.getStatusCode());
        if (limit_value >= 5 && limit_value <= 50)
        {
            JsonPath jsonPath = response.body().jsonPath();
            ArrayList<String> usersList = new ArrayList<>(jsonPath.getList("data"));
            int limitValue = jsonPath.getInt("limit");

            Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
            Assertions.assertThat(usersList.size()).isEqualTo(limitValue);
            Assertions.assertThat((double) usersList.size()).isEqualTo(limitValue);
        } else
        {
            Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
        }

    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "page_values")
    void getUsersWithPageParam(int page_value)
    {
        Response response = RestAssured.given().header("app-id", getAppId())
                .param("page", page_value)
                .get("user");
        response.body().prettyPrint();
        System.out.println(response.getStatusCode());

        if (page_value >= 0 && page_value <= 999)
        {
            JsonPath jsonPath = response.body().jsonPath();
            int pageValue = jsonPath.getInt("page");
            ArrayList<String> usersList = new ArrayList<>(jsonPath.getList("data"));

            Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
            Assertions.assertThat(page_value).isEqualTo(pageValue);
            Assertions.assertThat(usersList.size()).isEqualTo(20);
        } else
        {
            Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
        }
    }

    @Test
    void getUsersWithValidPageAndLimitParameters()
    {
        Response response = RestAssured.given().header("app-id", getAppId())
                .params("limit", 6).params("page","1")
                .get("user");
        response.body().prettyPrint();
        System.out.println(response.getStatusCode());
        ArrayList<String> usersList = new ArrayList<>(response.body().jsonPath().getList("data"));

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(usersList.size()).isEqualTo(6);

    }

    @Test
    void getUserWithValidId()
    {
        Response response = RestAssured.given().header("app-id", getAppId())
                .get("user");

        JsonPath path = response.body().jsonPath();
        String userId = path.getString("data[0].id");
        String userFirstName = path.getString("data.firstName[0]");

        response = UserApiMethods.getUser(userId);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(response.body().jsonPath().getString("id")).isEqualTo(userId);
        Assertions.assertThat(response.body().jsonPath().getString("firstName")).isEqualTo(userFirstName);
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema_full_user.json"));

    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_ids")
    void getUserWithInvalidId(String id)
    {
        Response response = RestAssured.given().header("app-id", getAppId())
                .get("user/" + id);

        response.prettyPrint();
        System.out.println(response.statusCode());

        if (id.length() == 24)
        {
            Assertions.assertThat(response.statusCode()).isEqualTo(SC_NOT_FOUND);
            Assertions.assertThat(response.jsonPath().getString("error")).isEqualTo("RESOURCE_NOT_FOUND");
        } else
        {
            Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
            Assertions.assertThat(response.jsonPath().getString("error")).isEqualTo("PARAMS_NOT_VALID");
        }

        /*
            I tried to test get user with invalid id but the answer that I received in different that what I expected.
            Base on my tests seems that only id with length 24 and who contains only numbers give an SC_NOT_FOUND with error
            RESOURCES_NOT_FOUND, tried to find a pattern for creating id, but I didn't find one.
         */

    }

    @Test
    void getCreatedUsers()
    {
        String id1 =  UserApiMethods.createUser();
        String id2 =  UserApiMethods.createUser();

        Response response = RestAssured.given().header("app-id", getAppId())
                .params("created", 3).get("user");

        response.prettyPrint();
        UserApiMethods.deleteUser(id1);
        UserApiMethods.deleteUser(id2);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(response.jsonPath().getInt("total")).isEqualTo(2);
        Assertions.assertThat(response.jsonPath().getList("data").size()).isEqualTo(2);
        Assertions.assertThat(response.jsonPath().getString("data[0].id")).isEqualTo(id1);
        Assertions.assertThat(response.jsonPath().getString("data[1].id")).isEqualTo(id2);
    }
}