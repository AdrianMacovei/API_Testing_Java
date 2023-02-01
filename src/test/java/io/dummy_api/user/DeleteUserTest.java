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
        String id = UserApiMethods.createUser();
        Response response = RestAssured.given().header("app-id", getAppId())
                .delete("user/" + id);
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(response.jsonPath().getString("id")).isEqualTo(id);
    }

    @Test()
    void testDeleteAnAlreadyDeletedUser()
    {
        String id = UserApiMethods.createUser();
        UserApiMethods.deleteUser(id);
        Response response = RestAssured.given().header("app-id", getAppId())
                .contentType(ContentType.JSON).delete("user/" + id);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_NOT_FOUND);
        Assertions.assertThat(response.jsonPath().getString("error")).isEqualTo("RESOURCE_NOT_FOUND");
    }
}
