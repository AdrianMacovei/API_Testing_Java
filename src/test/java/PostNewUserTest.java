package io.dummy_api.user;

import io.dummy_api.ApiBaseClass;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;
import java.util.HashMap;
import static org.apache.http.HttpStatus.*;


public class PostNewUserTest extends ApiBaseClass {

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_valid_data", groups = {"user_test"})
    void testCreateUserWithValidFistNameLastNameAndEmail(HashMap<String, String> user_data)
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.POST,
                "user/create", user_data, "");
        getInfo(response);

        softAssert.assertEquals(response.statusCode(),SC_CREATED);
        softAssert.assertEquals(response.jsonPath().getString("firstName"),user_data.get("firstName"));
        softAssert.assertEquals(response.jsonPath().getString("lastName"),user_data.get("lastName"));
        softAssert.assertEquals(response.jsonPath().getString("email"),user_data.get("email"));
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema_create_user_successfully.json"));
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_user_data", groups = {"user_test"})
    void testCreateAUserWithInvalidData(HashMap<String, String> user)
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.POST,
                "user/create", user, "");
        getInfo(response);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
        Assertions.assertThat(response.jsonPath().getString("error")).isEqualTo("BODY_NOT_VALID");
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_all_fields_valid_data")
    void testCreateUserWithValidDataInAllAvailableFields(HashMap<String, Object> user)
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.POST,
                "user/create", user, "");
        getInfo(response);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_create_all_fields_invalid_data", groups = {"user_test"})
    void testCreateUserWithInvalidDataInAllAvailableFields(HashMap<String, Object> user_data)
    {
        Response response = getRestWrapper().sendRequest(HttpMethod.POST,
                "user/create", user_data, "");
        getInfo(response);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
    }

}
