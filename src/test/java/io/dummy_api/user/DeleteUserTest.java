package io.dummy_api.user;

import io.dummy_api.ApiBaseClass;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;


public class DeleteUserTest extends ApiBaseClass
{
    @Test()
    void testDeleteUser()
    {
        String id = UserApiMethods.createUser();
        Response response = getRestWrapper().sendRequest(HttpMethod.DELETE, "user/{id}", "",id);
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(response.jsonPath().getString("id")).isEqualTo(id);
    }

    @Test()
    void testDeleteAnAlreadyDeletedUser()
    {
        String id = UserApiMethods.createUser();
        UserApiMethods.deleteUser(id);
        Response response = getRestWrapper().sendRequest(HttpMethod.DELETE, "user/{id}", "",id);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_NOT_FOUND);
        Assertions.assertThat(response.jsonPath().getString("error")).isEqualTo("RESOURCE_NOT_FOUND");
    }
}
