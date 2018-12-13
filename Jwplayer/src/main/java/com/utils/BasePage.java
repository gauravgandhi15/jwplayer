/*
  Class to initialize all page methods for the actions available
  under that page. All scripts must call the respective methods from the respective
  pages to achieve any action.

  @author 360Logica
 * @since 1.0
 *
 * @version 1.0
 */
package com.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.relevantcodes.extentreports.ExtentTest;

public abstract class BasePage{

	protected static final int DEFAULT_WAIT_4_ELEMENT = 30;
	protected static final int DEFAULT_WAIT_4_PAGE = 30;
	protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
	protected static WebDriverWait ajaxWait;
	protected WebDriver driver;
	protected String title;
	protected long timeout = 30;
	static String resultPath;
	public static ExtentTest test;
	public static BaseTest baseTest;
	protected String locator;

	/** @Inject @Named("framework.implicitTimeout") protected long timeout; */

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	

	public void clickOn(WebElement element) {
		waitForElement(element);	
		element.click();
	}

	/** Wait for element to be present */
	public void waitForElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	


	/** Click on WebElement by using java script */
	public void javascriptButtonClick(WebElement webElement) {
		waitForElement(webElement);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", webElement);
	}


	public String returnTitle() {
		return title;
	}
	

	/** Input text as string */
	public void inputText(WebElement element, String text) {
		waitForElement(element);
		element.clear();
		waitForElement(element);
		element.sendKeys(text);
	}
	
	
	/** Return driver instance */
	public WebDriver getDriver() {
		return driver;
	}

	

}

