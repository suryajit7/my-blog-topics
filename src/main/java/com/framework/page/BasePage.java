package com.framework.page;

import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.support.PageFactory.initElements;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

@Getter
public abstract class BasePage {

  protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(BasePage.class);

  protected WebDriver driver;
  protected final WebDriverWait wait;
  protected final Actions actions;
  protected static final int TIMEOUT = 30;

  protected static final String MENU_LIST = "//ul[@role='listbox']//li[@role='option']";

  protected static final List<Class<? extends WebDriverException>> exceptionList =
          Arrays.asList(NoSuchWindowException.class, NoSuchFrameException.class, NoAlertPresentException.class, InvalidSelectorException.class, TimeoutException.class, NoSuchSessionException.class, StaleElementReferenceException.class);

  public abstract BasePage isPageLoaded();

  public BasePage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(this.driver, ofSeconds(TIMEOUT));
    this.actions = new Actions(this.driver);
    initElements(new AjaxElementLocatorFactory(this.driver, TIMEOUT), this);
  }


  public BasePage goTo(String url) {
    this.driver.get(url);
    waitForPageToLoad();
    logger.info(("URL loaded: ").concat(url));
    return this;
  }


  public BasePage close() {
    waitForPageToLoad();
    this.driver.close();
    logger.info("Quiting browser instance.");
    return this;
  }


  public WebElement scrollToElement(WebElement element){
    JavascriptExecutor js = (JavascriptExecutor) this.driver;
    js.executeScript("arguments[0].scrollIntoView({block: \"center\",inline: \"center\",behavior: \"smooth\"});",element);
    wait.until(visibilityOf(element));
    logger.info(("Browser scrolled for element:").concat(element.getText()));
    return element;
  }

  public BasePage switchToActiveElement() {
    this.driver.switchTo().activeElement();
    logger.info("Switched to active WebElement.");
    return this;
  }

  public BasePage enterText(By locator, String text) {
    wait.until(visibilityOfElementLocated(locator)).click();
    this.driver.findElement(locator).clear();
    this.driver.findElement(locator).sendKeys(text);
    logger.info("Text entered: ".concat(text));
    return this;
  }

  public BasePage enterText(WebElement element, String text) {
    wait.until(visibilityOf(element)).click();
    element.clear();
    element.sendKeys(text);
    return this;
  }


  public BasePage refreshPage() {
    this.driver.navigate().refresh();
    logger.info("Page refreshed");
    return this;
  }

  public BasePage waitForPageToLoad() {
    wait.until((ExpectedCondition) webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").toString().equalsIgnoreCase("complete")
            || ((Boolean) ((JavascriptExecutor) webDriver).executeScript("return jQuery.active == 0")));
    return this;
  }

  public Boolean isElementLoaded(By locator){
    waitForPageToLoad();
    try {
      wait.ignoreAll(exceptionList)
              .withMessage("Elements is Not available.")
              .until(refreshed(visibilityOfAllElements(this.driver.findElements(locator))));
    } catch (WebDriverException exception) {
      logger.info(exception.getMessage());
    } return !this.driver.findElements(locator).isEmpty();
  }

  public BasePage moveToElementAndClick(WebElement webelement) {
    this.actions.click(webelement);
    logger.info("Moved to WebElement and clicked: ".concat(webelement.getText()));
    return this;
  }

  public BasePage moveToElementAndClick(List<WebElement> webelements) {
    for (WebElement element : webelements) {
      wait.until(visibilityOf(element));
      this.actions.click(element);
      logger.info("Moved to WebElement and clicked: ".concat(element.getText()));
    }
    return this;
  }

  public String getDynamicLocator(String locator, String newString1, String... newString2) {
    return String.format(locator, newString1, newString2);
  }

  public WebElement getDynamicWebElement(String locator, String newString1, String... newString2) {
    return this.driver.findElement(By.xpath(String.format(locator, newString1, newString2)));
  }


  public BasePage dragAndDropWebElement(WebElement source, WebElement destination) {
    this.actions.moveToElement(source).dragAndDrop(source, destination);
    logger.info("WebElement dragged and dropped to ".concat(destination.getText()));
    return this;
  }


  public BasePage selectByVisibleTextInDropdown(WebElement webelement, String visibleText) {
    new Select(webelement).selectByVisibleText(visibleText);
    logger.info("WebElement selected by visible text: ".concat(visibleText));
    return this;
  }


  public BasePage selectByValueInDropdown(WebElement webelement, String value) {
    new Select(webelement).selectByValue(value);
    logger.info("WebElement selected by value: ".concat(value));
    return this;
  }


  public BasePage selectManyByValueInDropdown(WebElement webelement, List<String> multiValues) {
    for (String valueToBeSelected : multiValues) {
      new Select(webelement).selectByValue(valueToBeSelected);
    }
    logger.info("All WebElements selected by value: ".concat(multiValues.toString()));
    return this;
  }


  public BasePage selectManyByVisibleTextInDropdown(WebElement webelement, List<String> multiValues) {
    for (String valueToBeSelected : multiValues) {
      new Select(webelement).selectByVisibleText(valueToBeSelected);
    }
    logger.info("All WebElements selected by visible text: ".concat(multiValues.toString()));
    return this;
  }


  public BasePage selectByIndexInDropdown(WebElement webelement, int index) {
    new Select(webelement).selectByIndex(index);
    logger.info("WebElement selected by index: ".concat(String.valueOf(index)));
    return this;
  }


  public WebElement getFirstSelectedOptionInDropdown(WebElement webelement) {
    return new Select(webelement).getFirstSelectedOption();
  }


  public List<WebElement> getAllSelectedOptionsInDropdown(WebElement webelement) {
    return new Select(webelement).getAllSelectedOptions();
  }


  public BasePage deselectByVisibleTextInDropdown(WebElement webelement, String visibleText) {
    new Select(webelement).deselectByVisibleText(visibleText);
    logger.info("WebElement deselected by visible text: ".concat(visibleText));
    return this;
  }


  public BasePage deselectByValueInDropdown(WebElement webelement, String value) {
    new Select(webelement).deselectByValue(value);
    logger.info("WebElement deselected by value: ".concat(value));
    return this;
  }


  public BasePage deselectByIndexInDropdown(WebElement webelement, int index) {
    new Select(webelement).deselectByIndex(index);
    logger.info("WebElement deselected by index: ".concat(String.valueOf(index)));
    return this;
  }


  public BasePage deselectAllInDropdown(WebElement webelement) {
    new Select(webelement).deselectAll();
    logger.info("All WebElements deselected.");
    return this;
  }





}
