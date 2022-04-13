package com.framework.util;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@Slf4j
public class AssertWebTable extends AbstractAssert<AssertWebElement, WebElement> {


    protected AssertWebTable(WebElement element) {
        super(element, AssertWebTable.class);
    }

    public static AssertWebTable assertThat(WebElement element) {
        return new AssertWebTable(element);
    }


    public AssertWebTable containsTableHeaders(List<String> headerList) {
        isNotNull();
        if (!headerList.isEmpty()) {

            Assertions.assertThat(actual.findElements(By.xpath("//tr//th")))
                    .isNotEmpty()
                    .extracting(WebElement::getText)
                    .containsAll(headerList);
            log.info("The given WebTable has expected Header List.");
        } else {
            failWithMessage("The given WebTable doesn't have the expected Header List.");
            log.error("The given WebTable doesn't have the expected Header List.");
        } return this;
    }


    public AssertWebTable hasTableHeaders(List<String> headerList) {
        isNotNull();
        if (!headerList.isEmpty()) {

            Assertions.assertThat(actual.findElements(By.xpath("//tr//th")))
                    .isNotEmpty()
                    .extracting(WebElement::getText)
                    .asList()
                    .containsExactlyInAnyOrder(headerList.toArray());
            log.info("The given WebTable has expected Header List.");
        } else {
            failWithMessage("The given WebTable doesn't have the expected Header List.");
            log.error("The given WebTable doesn't have the expected Header List.");
        } return this;
    }


    public AssertWebTable hasTableHeadersInOrder(List<String> headerList) {
        isNotNull();
        if (!headerList.isEmpty()) {

            Assertions.assertThat(actual.findElements(By.xpath("//tr//th")))
                    .isNotEmpty()
                    .extracting(WebElement::getText)
                    .asList()
                    .containsExactly(headerList.toArray());
            log.info("The given WebTable has expected Header List.");
        } else {
            failWithMessage("The given WebTable doesn't have the expected Header List.");
            log.error("The given WebTable doesn't have the expected Header List.");
        } return this;
    }

}
