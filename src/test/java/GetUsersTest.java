import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.ArrayList;

import static org.apache.http.HttpStatus.*;

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
        response.body().prettyPrint();
        System.out.println(response.getStatusCode());

        System.out.println(response.body().jsonPath().getList("data"));
        ArrayList<String> usersList = new ArrayList<>(response.body().jsonPath().getList("data"));
        System.out.println(usersList.size());


        Assertions.assertThat(usersList.size()).isEqualTo(20);
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
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

            Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
            Assertions.assertThat(page_value).isEqualTo(pageValue);
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
    void getUserById()
    {
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .get(RestAssured.baseURI + "/user");

        JsonPath path = response.body().jsonPath();
        String userId = path.getString("data.id[0]");
        String userFirstName = path.getString("data.firstName[0]");
        System.out.println(userId);
        System.out.println(userFirstName);

        response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .get(RestAssured.baseURI + "/user" + "/" + userId);

        response.body().prettyPrint();

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(response.body().jsonPath().getString("id")).isEqualTo(userId);
        Assertions.assertThat(response.body().jsonPath().getString("firstName")).isEqualTo(userFirstName);

    }

}
