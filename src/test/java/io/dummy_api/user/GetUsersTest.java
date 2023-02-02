package io.dummy_api.user;

import io.dummy_api.ApiBaseClass;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import static org.apache.http.HttpStatus.*;

public class GetUsersTest extends ApiBaseClass {

    @Test(groups = {"user_test"})
    void getUsersListWithValidAppId()
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "user", "", "");
        response.body().prettyPrint();
        System.out.println(response.getStatusCode());

        ArrayList<String> usersList = new ArrayList<>(response.body().jsonPath().getList("data"));

        Assertions.assertThat(usersList.size()).isEqualTo(20);
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema_list.json"));
    }

    @Test(groups = {"user_test"})
    void getUsersWithInvalidAppId()
    {
        Response response = getRestWrapperNoId().sendRequest(HttpMethod.GET, "user", "", "");
        response.body().prettyPrint();
        System.out.println(response.getStatusCode());

        JsonPath jsonPath = response.body().jsonPath();
        String errorValue = jsonPath.getString("error");


        Assertions.assertThat(errorValue).isEqualTo("APP_ID_MISSING");
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_FORBIDDEN);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "valid_limit_values", groups = {"user_test"})
    void getUsersWithValidLimitParam(int limitValue)
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "user?limit={parameters}", "",
                limitValue);

        getInfo(response);

        JsonPath jsonPath = response.body().jsonPath();
        ArrayList<String> usersList = new ArrayList<>(jsonPath.getList("data"));
        int valueOfLimit = jsonPath.getInt("limit");

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(usersList.size()).isEqualTo(valueOfLimit);
        Assertions.assertThat((double) usersList.size()).isEqualTo(valueOfLimit);

    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_limit_values", groups = {"user_test"})
    void getUsersWithInvalidLimitParam(Object limitValue)
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "user?limit={parameters}", "",
                limitValue);
        getInfo(response);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);

    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "valid_page_values", groups = {"user_test"})
    void getUsersWithValidPageParam(int pageValue)
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "user?page={parameters}", "",
                pageValue);
        getInfo(response);

        JsonPath jsonPath = response.body().jsonPath();
        int valueOfPage = jsonPath.getInt("page");
        ArrayList<String> usersList = new ArrayList<>(jsonPath.getList("data"));

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(pageValue).isEqualTo(valueOfPage);
        Assertions.assertThat(usersList.size()).isEqualTo(20);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_page_values", groups = {"user_test"})
    void getUsersWithInvalidPageParam(Object pageValue)
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "user?page={parameters}", "",
                pageValue);
        getInfo(response);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test(groups = {"user_test"})
    void getUsersWithValidPageAndLimitParameters()
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "user?limit={parameters}&page=1", "",
                6);
        getInfo(response);
        ArrayList<String> usersList = new ArrayList<>(response.body().jsonPath().getList("data"));

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(usersList.size()).isEqualTo(6);

    }

    @Test(groups = {"user_test"})
    void getUserWithValidId()
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "user", "",
                "");

        JsonPath path = response.body().jsonPath();
        String userId = path.getString("data[0].id");
        String userFirstName = path.getString("data.firstName[0]");

        response = UserApiMethods.getUser(userId);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(response.body().jsonPath().getString("id")).isEqualTo(userId);
        Assertions.assertThat(response.body().jsonPath().getString("firstName")).isEqualTo(userFirstName);
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema_full_user.json"));

    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_ids", groups = {"user_test"})
    void getUserWithInvalidId(String id)
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.GET, "user/{parameters}", "",
                id);
        getInfo(response);

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

    @Test(groups = {"user_test"})
    void getCreatedUsers()
    {
        String id1 =  UserApiMethods.createUser();
        String id2 =  UserApiMethods.createUser();

        Response response = getRestWrapper().sendRequest(HttpMethod.GET,
                "user?created=2", "", "");
        getInfo(response);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(response.jsonPath().getInt("total")).isEqualTo(2);
        Assertions.assertThat(response.jsonPath().getList("data").size()).isEqualTo(2);
        Assertions.assertThat(response.jsonPath().getString("data[0].id")).isEqualTo(id1);
        Assertions.assertThat(response.jsonPath().getString("data[1].id")).isEqualTo(id2);
    }
}
