import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
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
        response.body().prettyPrint();
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
        response.body().prettyPrint();
        System.out.println(response.getStatusCode());

        String jsonString = response.getBody().asString();
        Map jsonMap = getJsonToMap(jsonString);


        Assertions.assertThat(jsonMap.get("error")).isEqualTo("APP_ID_MISSING");
        Assertions.assertThat(response.statusCode()).isEqualTo(403);
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
            ArrayList<String> usersList = new ArrayList<>(response.body().jsonPath().getList("data"));
            System.out.println(usersList.size());

            Assertions.assertThat(response.statusCode()).isEqualTo(200);
            Assertions.assertThat(usersList.size()).isEqualTo(limit_value);

            String jsonString = response.getBody().asString();
            Map jsonMap = getJsonToMap(jsonString);

            Assertions.assertThat((double) usersList.size()).isEqualTo(jsonMap.get("limit"));
        } else
        {
            Assertions.assertThat(response.statusCode()).isEqualTo(400);
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
            Assertions.assertThat(response.statusCode()).isEqualTo(200);

            String jsonString = response.getBody().asString();
            Map jsonMap = getJsonToMap(jsonString);

            Assertions.assertThat((double)page_value).isEqualTo(jsonMap.get("page"));
        } else
        {
            Assertions.assertThat(response.statusCode()).isEqualTo(400);
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

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(usersList.size()).isEqualTo(6);

    }


    private Map<Object, Object> getJsonToMap(String jsonString) {;
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Map jsonHashMap = gson.fromJson(jsonString, HashMap.class);
        return jsonHashMap;
    }

}
