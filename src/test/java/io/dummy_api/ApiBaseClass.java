package io.dummy_api;

import io.dummy_api.user.UserApiMethods;
import io.dummy_api.util.TestContext;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import io.dummy_api.util.Properties;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

@ContextConfiguration(classes = TestContext.class)
public class ApiBaseClass extends AbstractTestNGSpringContextTests {

    @Autowired
    protected Properties properties;

    @Autowired
    protected UserApiMethods apiUserMethods;

    @BeforeMethod
    protected void setUp() {
        RestAssured.baseURI = properties.getApiUri();
    }

}
