package com.framework.page.site;

import com.framework.page.BasePage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.framework.data.Constants.APP_URL;
import static com.framework.page.site.HomePage.ORANGE_HRM_LOGO;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@Getter
public class LoginPanelPage extends BasePage {

    private static final String USERNAME_FIELD = "txtUsername";
    private static final String PASSWORD_FIELD = "txtPassword";
    private static final String LOGIN_BUTTON = "btnLogin";

    @FindBy(id = USERNAME_FIELD)
    private WebElement usernameField;

    @FindBy(id = PASSWORD_FIELD)
    private WebElement passwordField;

    @FindBy(id = LOGIN_BUTTON)
    private WebElement loginButton;

    public LoginPanelPage(WebDriver driver) {
        super(driver);
    }


    public LoginPanelPage goToAppLoginPage() {
        if (isUserLoggedIn()) {
            new HomePage(driver).clickOrangeHRMLogo();
            logger.info("Skipping navigating to Login screen.");
        } else {
            goTo(APP_URL);
        }
        return this;
    }

    public LoginPanelPage enterUsername(String username) {
        enterText(this.usernameField, username);
        return this;
    }

    public LoginPanelPage enterPassword(String password) {
        enterText(this.passwordField, password);
        return this;
    }

    public LoginPanelPage clickLoginButton() {
        wait.until(visibilityOfElementLocated(By.id(LOGIN_BUTTON))).click();
        return this;
    }


    public LoginPanelPage enterUsernameAndPassword(String username, String password) {
        if (!isUserLoggedIn()){
            enterUsername(username);
            enterPassword(password);
            clickLoginButton();
        } return this;
    }

    public Boolean isUserLoggedIn(){
        waitForPageToLoad();
        return isElementLoaded(By.xpath(ORANGE_HRM_LOGO));
    }



    @Override
    public LoginPanelPage isPageLoaded() {
        waitForPageToLoad();
        wait.until(visibilityOf(loginButton));
        return this;
    }
}
