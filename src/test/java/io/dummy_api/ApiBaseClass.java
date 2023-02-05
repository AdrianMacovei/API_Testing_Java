package io.dummy_api;

import io.dummy_api.core.RestWrapper;
import io.dummy_api.user.UserApiMethods;
import io.dummy_api.util.TestContext;
import io.restassured.response.Response;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import io.dummy_api.util.Properties;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

@ContextConfiguration(classes = TestContext.class)
public class ApiBaseClass extends AbstractTestNGSpringContextTests {

    @Autowired
    private Properties properties;

    @Autowired
    @Getter
    private RestWrapper restWrapper;

    @Autowired
    @Getter
    private RestWrapper restWrapperNoId;

    public SoftAssert softAssert;

    @BeforeClass(alwaysRun = true)
    protected void setUp() {
        restWrapper.addRequestHeader("app-id", properties.getAPP_ID());
    }

    @BeforeMethod()
    public void setUpMethod()
    {
        softAssert = new SoftAssert();
    }

    public void getInfo(Response response) {
        response.prettyPrint();
        System.out.println(response.statusCode());
    }

    @AfterMethod(alwaysRun = true, onlyForGroups = {"user_test"})
    protected void tearDown() {
        Response response = UserApiMethods.getCreatedUsers();

        for (int i = 0; i < response.jsonPath().getList("data").size(); i++) {
            UserApiMethods.deleteUser(response.jsonPath().getString("data[" + i + "].id"));
        }
    }

}
