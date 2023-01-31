package io.dummy_api.user;

import io.dummy_api.ApiBaseClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;


public class DeleteUserTest extends ApiBaseClass
{
    @Test()
    void testDeleteUser()
    {
        String id = apiUserMethods.createUser();
        System.out.println(properties.getApiUri());
        System.out.println(properties.getAppId());
        Response response = RestAssured.given().header("app-id", properties.getAppId())
                .delete(id);
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(response.jsonPath().getString("id")).isEqualTo(id);
    }

    @Test()
    void testDeleteAnAlreadyDeletedUser()
    {
        String id = apiUserMethods.createUser();
        apiUserMethods.deleteUser(id);
        Response response = RestAssured.given().header("app-id", properties.getAppId())
                .contentType(ContentType.JSON).delete(RestAssured.baseURI + id);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_NOT_FOUND);
        Assertions.assertThat(response.jsonPath().getString("error")).isEqualTo("RESOURCE_NOT_FOUND");
    }
}
