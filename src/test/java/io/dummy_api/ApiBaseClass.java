package io.dummy_api;

import io.dummy_api.core.RestWrapper;
import io.dummy_api.log.PlainTextReporter;
import io.dummy_api.util.TestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import io.dummy_api.util.Properties;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;

@ContextConfiguration(classes = TestContext.class)
@Listeners(PlainTextReporter.class)
public abstract class ApiBaseClass extends AbstractTestNGSpringContextTests {
    protected static final String ERROR_MSG_BODY = "BODY_NOT_VALID";
    protected static final String ERROR_MSG_MISSING_APP_ID = "APP_ID_MISSING";
    protected static final String ERROR_MSG_PARAMS_NOT_VALID = "PARAMS_NOT_VALID";
    protected static final String ERROR_MSG_RSC_NOT_FOUND = "RESOURCE_NOT_FOUND";

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
}
