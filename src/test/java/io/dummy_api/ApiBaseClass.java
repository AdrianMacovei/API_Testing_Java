package io.dummy_api;

import io.dummy_api.user.UserApiMethods;
import io.dummy_api.util.TestContext;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import io.dummy_api.util.Properties;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;

@ContextConfiguration(classes = TestContext.class)
public class ApiBaseClass extends AbstractTestNGSpringContextTests {

    @Autowired
    private Properties properties;

    @BeforeMethod
    protected void setUp() {
        RestAssured.baseURI = properties.getApiUri();
    }

    public String getAppId()
    {
        return properties.getAppId();
    }

    public void getInfo(Response response)
    {
        response.prettyPrint();
        System.out.println(response.statusCode());
    }

}
