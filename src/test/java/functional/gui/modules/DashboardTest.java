package functional.gui.modules;

import functional.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.framework.page.site.MenuOption.USERS;
import static com.framework.page.site.UserRole.ESS;

public class DashboardTest extends BaseTest {


    @BeforeClass
    public void setupTestData(){

        loginPage.goToAppLoginPage()
                .enterUsernameAndPassword(USERNAME, PASSWORD);
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

    }


}
