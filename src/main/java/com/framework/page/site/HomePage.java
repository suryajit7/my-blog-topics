package com.framework.page.site;

import com.framework.page.BasePage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

@Getter
public class HomePage extends BasePage {

    public static final String ORANGE_HRM_LOGO = "//img[@alt='OrangeHRM']";


    @FindBy(xpath = ORANGE_HRM_LOGO)
    public WebElement orangeHRMLogo;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickOrangeHRMLogo(){
        waitForPageToLoad();
        orangeHRMLogo.click();
        isPageLoaded();
        return this;
    }

    @Override
    public HomePage isPageLoaded() {
        waitForPageToLoad();
        wait.until(visibilityOf(orangeHRMLogo));
        return this;
    }
}
