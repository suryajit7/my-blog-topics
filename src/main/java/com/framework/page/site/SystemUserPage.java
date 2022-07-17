package com.framework.page.site;

import com.framework.page.BasePage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

@Getter
public class SystemUserPage extends BasePage {

    private static final String SEARCH_SYSTEM_USER_BY_USERNAME = "searchSystemUser_userName";
    private static final String SEARCH_SYSTEM_USER_BY_USERTYPE = "searchSystemUser_userType";
    private static final String SEARCH_SYSTEM_USER_BY_EMPLOYEE_NAME = "searchSystemUser_employeeName_empName";
    private static final String SEARCH_BUTTON = "searchBtn";

    @FindBy(id = SEARCH_SYSTEM_USER_BY_USERNAME)
    private WebElement searchSystemUserByUsername;

    @FindBy(id = SEARCH_SYSTEM_USER_BY_USERTYPE)
    private WebElement searchSystemUserByUserType;

    @FindBy(id = SEARCH_SYSTEM_USER_BY_EMPLOYEE_NAME)
    private WebElement searchSystemUserByEmployeeName;

    @FindBy(id = SEARCH_BUTTON)
    private WebElement searchButton;

    public SystemUserPage(WebDriver driver) {
        super(driver);
    }

    public SystemUserPage searchSystemUserByUsername(String username){
        wait.until(visibilityOf(searchSystemUserByUsername));
        enterText(searchSystemUserByUsername, username);
        return this;
    }

    public SystemUserPage searchSystemUserByUserRole(UserRole userRole){
        wait.until(visibilityOf(searchSystemUserByUserType)).click();
        selectByVisibleTextInDropdown(searchSystemUserByUserType, userRole.getStrValue());
        return this;
    }

    public SystemUserPage searchSystemUserByEmployeeName(String employeeName){
        wait.until(visibilityOf(searchSystemUserByEmployeeName));
        enterText(searchSystemUserByEmployeeName, employeeName);
        return this;
    }


    public SystemUserPage clickSearchButton(){
        wait.until(visibilityOf(searchButton)).click();
        return this;
    }


    @Override
    public SystemUserPage isPageLoaded() {
        waitForPageToLoad();
        wait.until(visibilityOf(searchSystemUserByUsername));
        wait.until(visibilityOf(searchSystemUserByUserType));
        wait.until(visibilityOf(searchSystemUserByEmployeeName));
        return this;
    }
}
