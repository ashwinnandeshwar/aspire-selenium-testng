package org.aspire.utilities;

import com.google.common.base.Strings;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/** Browser utils. all methods required  */
public class BrowserUtils {

	private JavascriptExecutor javascriptExecutor;
	private WebDriver webDriver;
	private WebDriverWait wait;


	public BrowserUtils(WebDriver webDriver) {
		this.webDriver = webDriver;
		this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		webDriver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
		if (webDriver instanceof JavascriptExecutor) {
			javascriptExecutor = (JavascriptExecutor) webDriver;
		} else {
			throw new IllegalArgumentException("provided webdriver can't execute " + "js commands");
		}
	}

	public static boolean isUiExpectedCondition(
			WebDriver webDriver, Function isTrue, int timeOutInSeconds) {
		return isUiExpectedCondition(webDriver, isTrue, timeOutInSeconds, "");
	}

	public static boolean isUiExpectedCondition(
			WebDriver webDriver, Function isTrue, int timeOutInSeconds, String description) {
		if (!Strings.isNullOrEmpty(description))
			System.out.println(description);
		try {
			new WebDriverWait(webDriver, Duration.ofSeconds(timeOutInSeconds))
					.ignoring(WebDriverException.class)
					.until(isTrue);
		} catch (WebDriverException wde) {
			return false;
		}
		return true;
	}

	public static void selectOptionFromSelectList(WebElement selectAsWebElement, String option)
	{
		Select select = new Select(selectAsWebElement);
		select.selectByVisibleText(option);
	}

	public static void keyPress(WebDriver driver, Keys keys) throws Exception {
		Actions ob = new Actions(driver);
		try {
			ob.sendKeys(keys).build().perform();
			System.out.println(keys.toString() + "pressed");
		}

		catch (Exception e) {
			System.out.println(keys.toString() + "pressed");
		}
	}


	public static void waitAndClickElementUsingJS(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			new WebDriverWait(driver,Duration.ofSeconds(15)).until(ExpectedConditions.elementToBeClickable(element));
			js.executeScript("arguments[0].click();", element);
//			System.out.println("Successfully JS clicked on the following WebElement: " + "<" + element.toString() + ">");
		} catch (StaleElementReferenceException elementUpdated) {
			WebElement staleElement = element;
			Boolean elementPresent = new WebDriverWait(driver,Duration.ofSeconds(15)).until(ExpectedConditions.elementToBeClickable(staleElement)).isEnabled();
			if (elementPresent == true) {
				js.executeScript("arguments[0].click();", elementPresent);
				System.out.println("(Stale Exception) Successfully JS clicked on the following WebElement: " + "<" + element.toString() + ">");
			}
		} catch (NoSuchElementException e) {
			System.out.println("Unable to JS click on the following WebElement: " + "<" + element.toString() + ">");
		}
	}

	public static void jsClick(WebDriver driver,WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	public static void clickElement(WebDriver driver,WebElement element) throws InterruptedException, IOException {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			System.out.println("Successfully clicked on the WebElement: " + "<" + element.toString() + ">");
		}
		catch (StaleElementReferenceException e) {
			new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(element));
			element.click();
			System.out.println("Successfully clicked on the WebElement: " + "<" + element.toString() + ">");
		}
		catch (NoSuchElementException e) {
			new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			System.out.println("Successfully clicked on the WebElement: " + "<" + element.toString() + ">");
		}
		catch (Exception e) {
			System.out.println("Unable to click on WebElement, Exception: " + e.getMessage());
			Assert.fail("Unable to click on the WebElement, using locator: " + "<" + element.toString() + ">");
		}

	}

	public static void sendKeysToWebElement(WebDriver driver,WebElement element, String textToSend) throws Exception {

		try {
			WaitUntilWebElementIsVisible(driver,element);
			element.clear();
			element.sendKeys(textToSend);
			System.out.println("Successfully Sent the following keys: '" + textToSend + "' to element: " + "<"+ element.toString() + ">");
		} catch (Exception e) {
			System.out.println("Unable to locate WebElement: " + "<" + element.toString() + "> and send the following keys: " + textToSend);
		}
	}

	/**********************************************************************************
	 **WAIT METHODS
	 **********************************************************************************/
	public static boolean WaitUntilWebElementIsVisible(WebDriver driver,WebElement element) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(element));
			System.out.println("WebElement is visible using locator: " + "<" + element.toString() + ">");
			return true;
		} catch (Exception e) {
			System.out.println("WebElement is NOT visible, using locator: " + "<" + element.toString() + ">");
			return false;
		}
	}

	public static String getAttributeValue(WebDriver driver,WebElement element,String attributeName) {
		String strText = null;
		try {
			new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(element));
			strText = element.getAttribute(attributeName).trim();
		//	System.out.println("value with in the Element: " + "<" + strText + ">");
			return strText;
		} catch (NullPointerException e) {
			System.out.println("unable to get the value from the WebElement using locator: " + "<" + element.toString() + ">");
		}
		return strText;
	}

	public static void waitForPageLoad(WebDriver driver,int timeout)  {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		ExpectedCondition<Boolean> pageLoadCondition = new
				ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						return ((JavascriptExecutor)driver).executeScript("return document.readyState").toString().equals("complete");
					}
				};
		wait.until(pageLoadCondition);
	}

	public static void poll(long milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
