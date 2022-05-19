package com.framework.util;

import org.openqa.selenium.WebElement;

import static java.time.Duration.ofSeconds;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class Await {


    public Await awaitUntilWebElementIsDisplayed(WebElement element) {
        await().ignoreExceptions()
                .atLeast(ofSeconds(1))
                .atMost(ofSeconds(30))
                .until(element::isDisplayed);
        return this;
    }


    public Await awaitUntilWebElementIsButton(WebElement element) {
        await().ignoreExceptions()
                .atLeast(ofSeconds(1))
                .atMost(ofSeconds(30))
                .until(() -> element.getTagName().equalsIgnoreCase("button") || element.getAttribute("type").equalsIgnoreCase("button"));
        return this;
    }


    public Await awaitUntilWebElementHasText(WebElement element, String text) {
        await().ignoreExceptions()
                .atLeast(ofSeconds(1))
                .atMost(ofSeconds(30))
                .until(() -> element.getText().equalsIgnoreCase(text));
        return this;
    }







}
