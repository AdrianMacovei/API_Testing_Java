import io.dummy_api.PojoNewUser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;

import static org.apache.http.HttpStatus.*;

public class GetUsersTest {


    @BeforeClass
    public static void setUp()
    {
        RestAssured.baseURI = "https://dummyapi.io/data/v1";
    }


    @Test
    void getUsersListWithValidAppId()
    {
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .get(RestAssured.baseURI + "/user");
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
        Response response = RestAssured.get(RestAssured.baseURI + "/user");
        response.body().prettyPrint();
        System.out.println(response.getStatusCode());

        JsonPath jsonPath = response.body().jsonPath();
        String errorValue = jsonPath.getString("error");


        Assertions.assertThat(errorValue).isEqualTo("APP_ID_MISSING");
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_FORBIDDEN);
    }


    @DataProvider(name = "limit_values")
    public Object[][] createDataLimitParam() {
        return new Integer[][]{
                {4}, {5}, {50}, {51}
        };
    }
    @Test(dataProvider = "limit_values")
    void getUsersWithLimitParam(int limit_value)
    {
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .param("limit", limit_value)
                .get(RestAssured.baseURI + "/user");
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

    @DataProvider(name = "page_values")
    public Object[][] createDataPageParam() {
        return new Integer[][]{
                {-1}, {0}, {999}, {1000}
        };
    }
    @Test(dataProvider = "page_values")
    void getUsersWithPageParam(int page_value)
    {
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .param("page", page_value)
                .get(RestAssured.baseURI + "/user");
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
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .params("limit", 6).params("page","1")
                .get(RestAssured.baseURI + "/user");
        response.body().prettyPrint();
        System.out.println(response.getStatusCode());
        ArrayList<String> usersList = new ArrayList<>(response.body().jsonPath().getList("data"));

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(usersList.size()).isEqualTo(6);

    }

    @Test
    void getUserWithValidId()
    {
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .get(RestAssured.baseURI + "/user");

        JsonPath path = response.body().jsonPath();
        String userId = path.getString("data.id[0]");
        String userFirstName = path.getString("data.firstName[0]");

        response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .get(RestAssured.baseURI + "/user/" + userId);

        response.body().prettyPrint();

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(response.body().jsonPath().getString("id")).isEqualTo(userId);
        Assertions.assertThat(response.body().jsonPath().getString("firstName")).isEqualTo(userFirstName);
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema_full_user.json"));

    }


    @DataProvider(name = "invalid_ids")
    public Object[][] createDataIds() {
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

    @Test(dataProvider = "invalid_ids")
    void getUserWithInvalidId(String id)
    {
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .get(RestAssured.baseURI + "/user/" + id);

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

}
