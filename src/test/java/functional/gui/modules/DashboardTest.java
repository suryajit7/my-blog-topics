package functional.gui.modules;

import functional.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.framework.page.site.MenuOption.USERS;
import static com.framework.page.site.UserRole.ESS;
import static com.framework.util.AssertWebElement.assertThat;
import static com.framework.util.Await.awaitUntil;
import static com.framework.util.Await.visibilityOf;

public class DashboardTest extends BaseTest {


    @BeforeClass
    public void setupTestData(){

        loginPage.goToAppLoginPage()
                .enterUsernameAndPassword(HRM_USERNAME, HRM_PASSWORD);
    }


    @Test(priority = 0)
    public void verifySearchUserFunctionality(){

        dashboardPage.getAllPieChartLabels();

        menuNavigation.navigateToMenu(USERS);

        systemUserPage.isPageLoaded()
                .searchSystemUserByUsername("Maggie.Manning")
                .searchSystemUserByUserRole(ESS)
                .searchSystemUserByEmployeeName("Maggie Manning")
                .clickSearchButton();

        awaitUntil(visibilityOf, systemUserPage.getSearchButton());
        assertThat(systemUserPage.getSearchButton()).isDisplayed();
    }


}
