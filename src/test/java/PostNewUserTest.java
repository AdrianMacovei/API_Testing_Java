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

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_valid_data")
    void testCreateUserWithValidFistNameLastNameAndEmail(HashMap<String, String> user_data)
    {
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .contentType(ContentType.JSON).body(user_data).post(RestAssured.baseURI + "/user/create");
        response.prettyPrint();
        System.out.println(user_data);
        System.out.println(response.statusCode());
        SoftAssert softAssert = new SoftAssert();
        UserApiMethods.deleteUser(response.jsonPath().getString("id"));

        softAssert.assertEquals(response.statusCode(),SC_CREATED);
        softAssert.assertEquals(response.jsonPath().getString("firstName"),user_data.get("firstName"));
        softAssert.assertEquals(response.jsonPath().getString("lastName"),user_data.get("lastName"));
        softAssert.assertEquals(response.jsonPath().getString("email"),user_data.get("email"));
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

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "user_all_fields_valid_data")
    void testCreateUserWithValidDataInAllAvailableFields(HashMap<String, Object> user)
    {
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .contentType(ContentType.JSON).body(user).post(RestAssured.baseURI + "/user/create");

        response.prettyPrint();
        System.out.println(response.statusCode());
        UserApiMethods.deleteUser(response.jsonPath().getString("id"));
    }
}
