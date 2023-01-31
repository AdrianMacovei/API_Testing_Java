import io.dummy_api.Properties;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;


@Configuration
@ContextConfiguration("main.io.dummy_api.Properties.class")
public class DeleteUserTest {

    @Autowired
    private Properties properties;

    @Test()
    void testDeleteUser()
    {
        String id = UserApiMethods.createUser();
        System.out.println(properties.getURI());
        System.out.println(properties.getAPP_ID());
        Response response = RestAssured.given().header("app-id", properties.getAPP_ID())
                .delete(properties.getURI() + id);
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(response.jsonPath().getString("id")).isEqualTo(id);
    }

    @Test()
    void testDeleteAnAlreadyDeletedUser()
    {
        String id = UserApiMethods.createUser();
        UserApiMethods.deleteUser(id);
        Response response = RestAssured.given().header("app-id", properties.getAPP_ID())
                .contentType(ContentType.JSON).delete(properties.getURI() + id);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_NOT_FOUND);
        Assertions.assertThat(response.jsonPath().getString("error")).isEqualTo("RESOURCE_NOT_FOUND");
    }
}
