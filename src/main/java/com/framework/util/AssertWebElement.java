package com.framework.util;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.WebElement;

import static com.framework.util.Await.*;


/**
 * Custom assertions suitable for WebElements.
 */
@Slf4j
public class AssertWebElement extends AbstractAssert<AssertWebElement, WebElement> {

    public AssertWebElement(WebElement element) {
        super(element, AssertWebElement.class);
    }

    public static AssertWebElement assertThat(WebElement element) {
        return new AssertWebElement(element);
    }

    public AssertWebElement isDisplayed() {
        isNotNull();

        awaitUntil(elementIsDisplayed, actual);

        if (actual.isDisplayed()) {
            log.info("Expected element is Displayed");
        } else {
            failWithMessage("Expected element is NOT displayed.");
            log.error("Expected element is NOT displayed.");
        } return this;
    }

    public AssertWebElement isEnabled() {
        isNotNull();

        awaitUntil(elementIsEnabled, actual);

        if (actual.isEnabled()) {
            log.info("Expected element is enabled.");
        } else {
            failWithMessage("Expected element is NOT enabled.");
            log.error("Expected element is NOT enabled.");
        } return this;
    }

    public AssertWebElement isSelected() {
        isNotNull();
        if (actual.isSelected()) {
            log.info("Expected element is selected.");
        } else {
            failWithMessage("Expected element is NOT selected.");
            log.error("Expected element is NOT selected.");
        } return this;
    }

    public AssertWebElement isButton() {
        isNotNull();
        boolean isButton = actual.getTagName().equalsIgnoreCase("button") || actual.getAttribute("type").equalsIgnoreCase("button");

        if (isButton) {
            log.info("Expected element is button.");
        } else {
            failWithMessage("Expected element is NOT a button.");
            log.error("Expected element is NOT a button.");
        } return this;
    }

    public AssertWebElement isLink() {
        isNotNull();

        if(!actual.getTagName().equalsIgnoreCase("a")){
            failWithMessage("Expected element is Not a Link.");
            log.error("Expected element is Not a Link.");
        } return this;
    }

    public AssertWebElement hasAttributeValue(String attribute, String value) {
        isNotNull();
        if (actual.getAttribute(attribute).equals(value)) {
            log.info("Expected element have attribute <%s> value as <%s>.", attribute, value);
        } else {
            log.error("Expected element doesn't have attribute <%s> value as <%s>.", attribute, value);
            failWithMessage("Expected element doesn't have attribute <%s> value as <%s>.");
        } return this;
    }


}