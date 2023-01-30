import io.dummy_api.PojoNewUser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.HashMap;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;


public class PostNewUserTest {

    @BeforeClass
    public static void setUp()
    {
        RestAssured.baseURI = "https://dummyapi.io/data/v1";
    }

    @Test
    void testCreateUserWithValidFistNameLastNameAndEmail()
    {
//        PojoNewUser user1 = new PojoNewUser();
//        user1.setFirstName("Apostu");
//        user1.setLastName("Costel");
//        user1.setEmail("apostucostel@gmail.com");

        HashMap<String, String> user1 = new HashMap<>();
        user1.put("firstName", "Apostu");
        user1.put("lastName", "Costel");
        user1.put("email", "apostucostel@gmail.com");


        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .contentType(ContentType.JSON).body(user1).post(RestAssured.baseURI + "/user/create");
        response.prettyPrint();
        System.out.println(response.statusCode());
        SoftAssert softassert = new SoftAssert();

        softassert.assertEquals(response.statusCode(),SC_CREATED);
        softassert.assertEquals(response.jsonPath().getString("firstName"),user1.get("firstName"));
        softassert.assertEquals(response.jsonPath().getString("lastName"),user1.get("lastName"));
        softassert.assertEquals(response.jsonPath().getString("email"),user1.get("email"));
        softassert.assertAll();

        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema_create_user_successfully.json"));
    }


    @DataProvider(name = "invalid_user_data")
    public Object[][] createInvalidUser() {
        HashMap <String, String>  userWithoutFistName = new HashMap<>();
        userWithoutFistName.put("lastName", "Macovei");
        userWithoutFistName.put("email", "adrianmacovei1998@gmail.com");

        HashMap <String, String>  userWithoutLastName = new HashMap<>();
        userWithoutLastName.put("firstName", "Adrian");
        userWithoutLastName.put("email", "adrianmacovei1998@gmail.com");

        HashMap <String, String>  userWithoutEmail = new HashMap<>();
        userWithoutEmail.put("firstName", "Adrian");
        userWithoutEmail.put("lastName", "Macovei");

        HashMap <String, String>  userWithoutEmailAndFirstName = new HashMap<>();
        userWithoutEmailAndFirstName.put("lastName", "Macovei");

        HashMap <String, String>  userWithoutEmailAndLastName = new HashMap<>();
        userWithoutEmailAndLastName.put("firstName", "Adrian");

        HashMap <String, String>  userWithoutFirstAndLastName = new HashMap<>();
        userWithoutFirstAndLastName.put("email", "adrianmacovei1999@gmail.com");

        HashMap <String, String>  userWithEmptyFields = new HashMap<>();

        HashMap <String, String>  userWithInvalidEmail = new HashMap<>();
        userWithInvalidEmail.put("firstName", "Adrian");
        userWithInvalidEmail.put("lastName", "Macovei");
        userWithInvalidEmail.put("email", RandomStringUtils.random(10, true, true).toLowerCase());

        HashMap <String, String>  userWithOneCharFirstName = new HashMap<>();
        userWithOneCharFirstName.put("firstName", "A");
        userWithOneCharFirstName.put("lastName", "Macovei");
        userWithOneCharFirstName.put("email", RandomStringUtils.random(6, true, true).
                        toLowerCase() + "@gmail.com");

        HashMap <String, String>  userWithFiftyOneCharsFirstName = new HashMap<>();
        userWithFiftyOneCharsFirstName.put("firstName", RandomStringUtils.random(51, true, false));
        userWithFiftyOneCharsFirstName.put("lastName", "Macovei");
        userWithFiftyOneCharsFirstName.put("email", RandomStringUtils.random(6, true, true).
                toLowerCase() + "@gmail.com");

        return new HashMap[][]{
                {userWithoutFistName},
                {userWithoutLastName},
                {userWithoutEmail},
                {userWithoutEmailAndFirstName},
                {userWithoutEmailAndLastName},
                {userWithoutFirstAndLastName},
                {userWithEmptyFields},
                {userWithInvalidEmail},
                {userWithOneCharFirstName},
                {userWithFiftyOneCharsFirstName}
        };
    }

    @Test(dataProvider = "invalid_user_data")
    void testCreateAUserWithInvalidData(HashMap<String, String> user)
    {
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .contentType(ContentType.JSON).body(user).post(RestAssured.baseURI + "/user/create");
        response.prettyPrint();
        System.out.println(response.statusCode());
        System.out.println(user);
    }


    @Test()
    void testDeleteAnAlreadyCreatedUser()
    {
        String id = createUser();
        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .contentType(ContentType.JSON).delete(RestAssured.baseURI + "/user/" + id);

        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);
        Assertions.assertThat(response.jsonPath().getString("id")).isEqualTo(id);
    }




    private String createUser()
    {
        HashMap<String, String> user1 = new HashMap<>();
        user1.put("firstName", "Popescu");
        user1.put("lastName", "Marian");
        user1.put("email", "popescumarian@gmail.com");

        Response response = RestAssured.given().header("app-id", "63d233c888cdfd33faa635a4")
                .contentType(ContentType.JSON).body(user1).post(RestAssured.baseURI + "/user/create");

        return response.jsonPath().getString("id");
    }



}
