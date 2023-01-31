package io.dummy_api.user;

import io.dummy_api.ApiBaseClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import java.util.HashMap;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
public class UpdateUserTest extends ApiBaseClass {

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_valid_data")
    void testUpdateValidFirstNameAndLastName(HashMap<String, String> data)
    {
        String id = apiUserMethods.createUser();
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .contentType(ContentType.JSON).body(data).put(RestAssured.baseURI + id);
        apiUserMethods.deleteUser(id);

        response.prettyPrint();
        System.out.println(response.statusCode());

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(response.jsonPath().getString("firstName")).isEqualTo(data.get("firstName"));
        Assertions.assertThat(response.jsonPath().getString("lastName")).isEqualTo(data.get("lastName"));
        Assertions.assertThat(response.jsonPath().getString("id")).isEqualTo(id);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "update_invalid_data")
    void testUpdateInvalidFirstNameAndLastName(HashMap<String, String> data)
    {
        String id = apiUserMethods.createUser();
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .contentType(ContentType.JSON).body(data).put(RestAssured.baseURI + id);
        apiUserMethods.deleteUser(id);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);

    }

    @Test
    void testUpdateEmail()
    {
        HashMap <String, String>  user_data = new HashMap<>();
        user_data.put("firstName", "Adrian");
        user_data.put("lastName", "Macovei");
        user_data.put("email", RandomStringUtils.random(6, true, true).toLowerCase() + "@gmail.com");

        String id = apiUserMethods.createUser();
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .contentType(ContentType.JSON).body(user_data).put(RestAssured.baseURI + id);
        apiUserMethods.deleteUser(id);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
        Assertions.assertThat(response.jsonPath().getString("email")).isNotSameAs(user_data.get("email"));
    }

    @Test
    void testUpdateOtherFields()
    {

    }
}
