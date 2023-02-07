package io.dummy_api;

import io.dummy_api.core.RestWrapper;
import io.dummy_api.enums.Gender;
import io.dummy_api.enums.Title;
import io.dummy_api.models.ErrorModel;
import io.dummy_api.user.UserBaseClass;
import io.dummy_api.util.TestContext;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import io.dummy_api.util.Properties;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;

@ContextConfiguration(classes = TestContext.class)
public abstract class ApiBaseClass extends AbstractTestNGSpringContextTests {

    @Autowired
    private Properties properties;

    @Autowired
    protected RestWrapper restWrapper;

    @Autowired
    protected RestWrapper restWrapperNoId;

    public SoftAssert softAssert;

    @BeforeClass(alwaysRun = true)
    protected void setUp() {
        restWrapper.addRequestHeader("app-id", properties.getAPP_ID());
    }

    @BeforeMethod()
    public void setUpMethod() {
        softAssert = new SoftAssert();
    }

    public void getInfo(Response response) {
        response.prettyPrint();
        System.out.println(response.statusCode());
    }

}
