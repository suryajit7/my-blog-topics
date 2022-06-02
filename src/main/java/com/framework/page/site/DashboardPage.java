package com.framework.page.site;

import com.framework.page.BasePage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class DashboardPage extends BasePage {

    private static final String PIE_CHART = "//span[contains(@id,'pieLabel')]";
    private static final String ORANGE_HRM_LOGO = "//img[@alt='OrangeHRM']";

    @FindBy(xpath = PIE_CHART)
    private List<WebElement> pieChartLabels;

    @FindBy(xpath = ORANGE_HRM_LOGO)
    private WebElement orangeHRMLogo;


    public DashboardPage(WebDriver driver) {
        super(driver);
    }


    public List<String> getAllPieChartLabels() {
        return this.pieChartLabels.stream()
                .map(WebElement::getText)
                .collect(toList());
    }


    @Override
    public BasePage isPageLoaded() {
        waitForPageToLoad();
        return this;
    }
}
