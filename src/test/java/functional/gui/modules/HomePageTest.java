package functional.gui.modules;

import functional.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @BeforeClass
    public void setupTestData(){

        loginPage.goToAppLoginPage()
                .enterUsernameAndPassword(HRM_USERNAME, HRM_PASSWORD);
    }

    @Test(priority = 0)
    public void verifyNavigationToHome(){

        dashboardPage.getAllPieChartLabels();
        homePage.clickOrangeHRMLogo();
    }
}
