package functional;


import com.framework.page.site.*;
import com.framework.util.AsyncService;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import com.saasquatch.jsonschemainferrer.*;
import io.restassured.module.jsv.JsonSchemaValidatorSettings;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.LoggerFactory;
import org.testng.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.net.MalformedURLException;
import java.net.URL;

import static com.framework.data.Constants.BLANK;
import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static com.google.common.base.Preconditions.checkNotNull;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.settings;
import static java.lang.String.valueOf;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Listeners(BaseTest.class)
public class BaseTest implements ITestListener, IInvokedMethodListener {

    protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(BaseTest.class);

    protected String host;
    protected JsonValidator validator;
    protected AsyncService asyncService;

    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected static final ThreadLocal<ITestNGMethod> currentMethods = new ThreadLocal<>();
    protected static final ThreadLocal<ITestResult> currentResults = new ThreadLocal<>();

    protected static final String HRM_USERNAME = System.getenv("HRM_USERNAME");
    protected static final String HRM_PASSWORD = System.getenv("HRM_PASSWORD");

    protected HomePage homePage;
    protected LoginPanelPage loginPage;
    protected MenuNavigationPage menuNavigation;
    protected DashboardPage dashboardPage;
    protected SystemUserPage systemUserPage;

    protected static final JsonSchemaInferrer jsonSchemaInferrer = JsonSchemaInferrer.newBuilder()
            .setSpecVersion(SpecVersion.DRAFT_04)
            .addFormatInferrers(FormatInferrers.email(), FormatInferrers.ip())
            .setAdditionalPropertiesPolicy(AdditionalPropertiesPolicies.notAllowed())
            .setRequiredPolicy(RequiredPolicies.nonNullCommonFields())
            .addEnumExtractors(EnumExtractors.validEnum(java.time.Month.class), EnumExtractors.validEnum(java.time.DayOfWeek.class))
            .build();

    protected final ValidationConfiguration validationConfig = ValidationConfiguration.newBuilder()
            .setDefaultVersion(DRAFTV4).freeze();

    protected final JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
            .setValidationConfiguration(validationConfig).freeze();

    @BeforeSuite
    public void beforeSuiteTestSetup(){

        settings = JsonSchemaValidatorSettings.settings()
                .with().jsonSchemaFactory(jsonSchemaFactory)
                .and().with().checkedValidation(true);

        validator = jsonSchemaFactory.getValidator();

        asyncService.initialize();
    }


    @BeforeClass
    public void beforeClassSetup(ITestContext context) throws MalformedURLException {

        if (context.getName().equalsIgnoreCase("UI Regression")) {

            await().atLeast(1, SECONDS)
                    .and().atMost(60, SECONDS)
                    .until(() -> getGridAvailability().equalsIgnoreCase("true"));

            getRemoteDriver(context);
            initObjects();
        }
   }

   private String getGridAvailability(){
       return given().contentType(JSON)
               .when().get("http://localhost:4444/wd/hub/status")
               .then().extract().response().path("value.ready");
   }


    @AfterSuite
    public void tearDownDriver(ITestContext context) {

        if (context.getName().equalsIgnoreCase("UI Regression")) {
            this.driver.get().close();
            if (null != this.driver.get()) {
                this.driver.get().quit();
            }
        }
    }

    private void getRemoteDriver(ITestContext context) throws MalformedURLException {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("se:name", context.getName());

        host = System.getenv("HUB_HOST") != null ? System.getenv("HUB_HOST") : "localhost";

        driver.set(new RemoteWebDriver(new URL("http://" + host + ":4444/wd/hub"), chromeOptions));
        logger.info("Remote Chrome Driver Started...");

        driver.get().manage().deleteAllCookies();
        driver.get().manage().window().maximize();

        context.setAttribute("WebDriver", driver.get());

        logger.info("Window Size: " + driver.get().manage().window().getSize().getHeight() + "x" + driver.get().manage().window().getSize().getWidth());
    }

    private ChromeOptions getChromeOptions(ITestContext context) {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("se:name", context.getName());

        return chromeOptions;
    }

    private void initObjects() {
        homePage = new HomePage(driver.get());
        loginPage = new LoginPanelPage(driver.get());
        menuNavigation = new MenuNavigationPage(driver.get());
        dashboardPage = new DashboardPage(driver.get());
        systemUserPage = new SystemUserPage(driver.get());
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        currentMethods.set(method.getTestMethod());
        currentResults.set(testResult);
    }


    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        currentMethods.remove();
        currentResults.remove();
    }


    public static ITestResult getTestResult() {
        return checkNotNull(currentResults.get(),
                "Did you forget to register the %s listener?", BaseTest.class.getName());
    }

    public static ITestNGMethod getTestMethod() {
        return checkNotNull(currentMethods.get(),
                "Did you forget to register the %s listener?", BaseTest.class.getName());
    }

    public static String getTestMethodName(String parameterValue) {
        return getTestMethod().getMethodName()
                .concat(parameterValue.equalsIgnoreCase(BLANK) ? BLANK : ("_").concat(parameterValue))
                .concat(".json");
    }


    @Override
    public void onTestStart(ITestResult result) {

        logger.info("************************");
        logger.info(result.getMethod().getMethodName());
        logger.info("Test Priority:"+result.getMethod().getPriority());

    }


    @Override
    public void onTestSuccess(ITestResult result) {

        logger.info("Test Status: Passed");
        logger.info("************************");
    }


    @Override
    public void onTestFailure(ITestResult result) {

        logger.info("Test Status: Failed");
        logger.error(result.getThrowable().getMessage());
        logger.info("************************");
    }


    @Override
    public void onTestSkipped(ITestResult result) {

        logger.info("Test Status: Skipped");
        logger.trace(valueOf(result.getSkipCausedBy()));
        logger.error(result.getThrowable().getMessage());
        logger.info("************************");
    }


    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("************************");
    }


    @Override
    public void onStart(ITestContext context) {

        logger.info("************************");
        logger.info("Test Suite Started...");
        logger.info(context.getSuite().getName());
        logger.info(valueOf(context.getSuite().getXmlSuite()));
        logger.info("************************");
    }


    @Override
    public void onFinish(ITestContext context) {
        logger.info("************************");
    }


}
