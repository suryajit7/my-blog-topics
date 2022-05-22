package com.framework.util;

import com.framework.core.AwaitInterface;
import org.openqa.selenium.WebElement;
import org.testcontainers.shaded.org.awaitility.core.ConditionFactory;

import static java.time.Duration.ofSeconds;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class Await {

    public static AwaitInterface elementIsDisplayed = (element) -> getInitializedAwait()
            .until(element::isDisplayed);

    public static AwaitInterface elementIsEnabled = (element) -> getInitializedAwait()
            .until(element::isEnabled);

    public static AwaitInterface elementIsSelected = (element) -> getInitializedAwait()
            .until(element::isSelected);

    public static AwaitInterface elementIsButton = (element) -> getInitializedAwait()
            .until(() -> element.getTagName().equalsIgnoreCase("button") || element.getAttribute("type").equalsIgnoreCase("button"));

    public static void awaitUntil(AwaitInterface method, WebElement element) {
        method.syncUsingAwait(element);
    }

    public static ConditionFactory getInitializedAwait() {
        return await()
                .ignoreExceptions()
                .pollDelay(ofSeconds(1))
                .atLeast(ofSeconds(1))
                .atMost(ofSeconds(30));
    }








}
