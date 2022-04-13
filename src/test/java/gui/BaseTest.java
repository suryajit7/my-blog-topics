package gui;


import com.framework.page.site.DashboardPage;
import com.framework.page.site.LoginPanelPage;
import com.framework.page.site.MenuNavigationPage;
import com.framework.page.site.SystemUserPage;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.remote.CapabilityType.*;


public class BaseTest {

    protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(BaseTest.class);

    protected static final int TIMEOUT = 50;
    protected String host;
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected static final LocalDate localDate = LocalDate.now();
    protected static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddLLLLyyyy");
    protected static final String USERNAME = System.getenv("HRM_USERNAME");
    protected static final String PASSWORD = System.getenv("HRM_PASSWORD");

    protected LoginPanelPage loginPage;
    protected MenuNavigationPage menuNavigation;
    protected DashboardPage dashboardPage;
    protected SystemUserPage systemUserPage;

    @BeforeSuite
    public void beforeSuiteTestSetup(){
        sleepUninterruptibly(5, SECONDS); //intentional, as chrome containers take few ms to register to hub
    }


    @BeforeTest
    public void beforeClassTestSetup(ITestContext context) throws MalformedURLException {

        MutableCapabilities dc = new ChromeOptions();

        dc.merge(configureChromeOptions());
        dc.setCapability("name", context.getCurrentXmlTest().getName());

        host = System.getenv("HUB_HOST") != null ? System.getenv("HUB_HOST") : "hub";

        driver.set(new RemoteWebDriver(new URL("http://" + host + ":4444/wd/hub"), dc));

        logger.info("Remote Chrome Driver Started...");

        driver.get().manage().deleteAllCookies();
        driver.get().manage().window().maximize();

        context.setAttribute("WebDriver", driver.get());

        logger.info("Window Size: " + driver.get().manage().window().getSize().getHeight() + "x" + driver.get().manage().window().getSize().getWidth());
   }


    @BeforeClass
    public void beforeClassSetup(){
        loginPage = new LoginPanelPage(driver.get());
        menuNavigation = new MenuNavigationPage(driver.get());
        dashboardPage = new DashboardPage(driver.get());
        systemUserPage = new SystemUserPage(driver.get());
   }


    @AfterSuite
    public void tearDownDriver(ITestContext context) {
        this.driver.get().close();
        if (null != this.driver) {
            this.driver.get().quit();
        }
    }

    private ChromeOptions configureChromeOptions() {

        Proxy proxy = new Proxy();
        proxy.setAutodetect(true);

        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.setCapability(ELEMENT_SCROLL_BEHAVIOR, true);
        chromeOptions.setCapability(SUPPORTS_ALERTS, true);
        chromeOptions.setCapability(SUPPORTS_JAVASCRIPT, true);
        chromeOptions.setCapability(SUPPORTS_APPLICATION_CACHE, true);
        chromeOptions.setCapability(ACCEPT_SSL_CERTS, true);
        chromeOptions.setCapability(ELEMENT_SCROLL_BEHAVIOR, true);
        chromeOptions.setCapability(PROXY, proxy);
        chromeOptions.setCapability(BROWSER_NAME, "chrome");

        chromeOptions.setCapability("chrome.switches", asList("--disable-extensions"));
        chromeOptions.setExperimentalOption("excludeSwitches", singletonList("enable-automation"));
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--allow-insecure-localhost");
        chromeOptions.addArguments("--no-default-browser-check");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-gpu");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);
        chromeOptions.setExperimentalOption("prefs", prefs);

        return chromeOptions;
    }

}
