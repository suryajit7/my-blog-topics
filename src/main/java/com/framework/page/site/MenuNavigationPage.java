package com.framework.page.site;

import com.framework.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

import static com.framework.page.site.MenuOption.*;
import static java.util.Arrays.asList;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class MenuNavigationPage extends BasePage {

    private static final String MENU = "//li[@class='main-menu-first-level-list-item']/child::a/child::b";
    private static final String ORANGE_HRM_LOGO = "//img[@alt='OrangeHRM']";

    @FindBy(how = How.XPATH, using = MENU)
    private WebElement menu;


    public MenuNavigationPage(WebDriver driver) {
        super(driver);
    }


    public void navigateToMenuOption(List<MenuOption> menuOptions) {
        for (MenuOption option : menuOptions) {
            wait.until(visibilityOf(this.driver.findElement(By.id(option.getMenuId())))).click();
        }
    }


    public MenuNavigationPage navigateToMenu(MenuOption menuOption) {

        wait.until(visibilityOfElementLocated(By.xpath(ORANGE_HRM_LOGO)));

        switch (menuOption) {

            case USERS:
                navigateToMenuOption(asList(ADMIN, USER_MANAGEMENT, USERS));
                break;

        }

        return this;
    }

    @Override
    public MenuNavigationPage isPageLoaded() {
        waitForPageToLoad();
        return this;
    }
}
