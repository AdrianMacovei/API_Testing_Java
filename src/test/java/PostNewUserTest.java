import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.HashMap;
import static org.apache.http.HttpStatus.*;


public class PostNewUserTest {

    @BeforeClass
    public static void setUp()
    {
        RestAssured.baseURI = "https://dummyapi.io/data/v1";
    }

    @Test
    void testCreateUserWithValidFistNameLastNameAndEmail()
    {
        HashMap<String, String> user1 = new HashMap<>();
        user1.put("firstName", "Apostu");
        user1.put("lastName", "Costel");
        user1.put("email", "apostucostel@gmail.com");


        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .contentType(ContentType.JSON).body(user1).post(RestAssured.baseURI + "/user/create");
        response.prettyPrint();
        System.out.println(response.statusCode());
        SoftAssert softAssert = new SoftAssert();
        UserApiMethods.deleteUser(response.jsonPath().getString("id"));

        softAssert.assertEquals(response.statusCode(),SC_CREATED);
        softAssert.assertEquals(response.jsonPath().getString("firstName"),user1.get("firstName"));
        softAssert.assertEquals(response.jsonPath().getString("lastName"),user1.get("lastName"));
        softAssert.assertEquals(response.jsonPath().getString("email"),user1.get("email"));
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema_create_user_successfully.json"));
        softAssert.assertAll();
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "invalid_user_data")
    void testCreateAUserWithInvalidData(HashMap<String, String> user)
    {
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .contentType(ContentType.JSON).body(user).post(RestAssured.baseURI + "/user/create");
        response.prettyPrint();
        System.out.println(response.statusCode());
        System.out.println(user);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
        Assertions.assertThat(response.jsonPath().getString("error")).isEqualTo("BODY_NOT_VALID");
    }
}
