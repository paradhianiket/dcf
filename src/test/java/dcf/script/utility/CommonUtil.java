package dcf.script.utility;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import dcf.script.init.Init;

public class CommonUtil 
{
	public static Properties properties;
	public static Properties properties1;
	public static Logger Log = LogManager.getLogger();
	public static String CONFIGS_FOLDER = "src/main/resources/Configs/";
		public static String OBJ_FOLDER= "src/main/resources/ObjectRepository/";
	public static final String TESTDATA_FOLDER = "src/main/resources/Testdatafiles/";
	public static String configFileName = "Stgamo";
	public static String testEnvironment = "";
	public static WebDriverWait wait;
	
//to get properties	
		public static Properties getProperties() throws Exception 
		{
			try 
			{
				if (properties == null) 
				{
					properties = new Properties();
					InputStream input = null;
					InputStream input1 = null;
					input = new FileInputStream(CONFIGS_FOLDER + "Config" + configFileName + ".properties");
					input1 = new FileInputStream(OBJ_FOLDER + "CaseOR" + ".properties");
					properties.load(input);
					properties.load(input1);
				}
			} 
			catch (Exception e) 
			{
				Log.error("Unable to load Properties" + e);
				throw (e);
			}
			return properties;
		}
		
//to load properties
		public static Properties loadProperties() throws Exception 
		{
			properties = getProperties();
			return properties;	
		}
//to add properties
		public static void addProperties(String eventType) throws Exception 
		{
			Properties task = new Properties();
			task.load(new FileInputStream(CONFIGS_FOLDER + properties.getProperty("Customer").trim() + ".properties"));
			properties.putAll(task);
		}
		
		public static void setTestEnvironment(String strTestEnvironment)
		{
			testEnvironment = strTestEnvironment;
		}
		
		public static void setConfigFileName(String Config) throws Exception
		{
			String jenkinsConfig = System.getProperty("jenkinsConfig");
			configFileName = jenkinsConfig;
		}
		
//explicit wait
		public static List<WebElement> waitForElementToBe(By locator, String expectedCondition, WebDriver driver,
				int valueOfDuration) {
			try {
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(valueOfDuration))
						.pollingEvery(Duration.ofSeconds(1));

				switch (expectedCondition) {
				case ("PRESENCE"):
					wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
					return driver.findElements(locator);
				case ("CLICKABLE"):
					wait.until(ExpectedConditions.elementToBeClickable(locator));
					return driver.findElements(locator);
				case ("VISIBLE"):
					wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
					return driver.findElements(locator);
				case ("SELECTED"):
					wait.until(ExpectedConditions.elementToBeSelected(locator));
					return driver.findElements(locator);
				case ("INVISIBLE"):
					wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
					return null;
				}
			} catch (NoSuchElementException e) {
				return null;
			} catch (ElementNotVisibleException e) {
				List<WebElement> els = driver.findElements(locator);
				if(els.isEmpty())
					return null;	
				else
					return getDisplayedElements(els).isEmpty() ? null :getDisplayedElements(els);
			} catch (TimeoutException e) {
				List<WebElement> els = driver.findElements(locator);
				if(els.isEmpty())
					return null;	
				else
					return getDisplayedElements(els).isEmpty() ? null :getDisplayedElements(els);
				} 
			catch (Exception e) {
				Log.error("Error while processing request in waitForElementToBe " + e.getCause().toString());
				throw (e);
			}
			return null;
		}

		public static List<WebElement> getDisplayedElements(List<WebElement> elements) {
			List<WebElement> displayedWebElements = new ArrayList<WebElement>();
			try {
				for (WebElement element : elements) {
					if (element.isDisplayed()) {
						displayedWebElements.add(element);
					}
				}
			} catch (Exception e) {
				throw (e);
			}
			return displayedWebElements;
		}
		
//pageloading
		public static void waitForPageLoadMsgToBeInvisible(String Message)
		{
			if(waitForElementToBe(By.xpath("//div[@class='x-mask-msg-text']"), "VISIBLE", getDriver(), 25) != null)
				Assert.assertTrue(waitForElementStateToBe(By.xpath("//div[@class='x-mask-msg-text'][text()='" + Message + "']"),
						"INVISIBLE", getDriver()), "Requested page is not loaded");
		}
		public static RemoteWebDriver getDriver() 
		{
			return Init.driver;
		}
		@SuppressWarnings("deprecation")
		public static boolean waitForElementStateToBe(By locator, String expectedCondition, WebDriver driver) {
			try {
				By element = locator;
				WebElement webElement = null;
				boolean flag = false;
				getWait(driver);
				wait.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
				switch (expectedCondition) {
				case ("PRESENCE"):
					webElement = wait.until(ExpectedConditions.presenceOfElementLocated(element));
					if (webElement != null)
						flag = true;
					break;
				case ("CLICKABLE"):
					webElement = wait.until(ExpectedConditions.elementToBeClickable(element));
					if (webElement != null)
						flag = true;
					break;
				case ("VISIBLE"):
					webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(element));
					if (webElement != null)
						flag = true;
					break;
				case ("SELECTED"):
					flag = wait.until(ExpectedConditions.elementToBeSelected(element));
					break;
				case ("INVISIBLE"):
					flag = wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
					break;
				}
				if (flag) {
//					Utility.printInLogFile(Log,"Element is :" + expectedCondition);
					return true;
				} else {
//					Utility.printInLogFile(Log,"Element is NOT :" + expectedCondition);
					return false;
				}
			} catch (Exception e) {
				Log.error("Error while processing request in waitForElementToBe " + e);
				return false;
			}
		}
		public static WebDriverWait getWait(WebDriver driver) {
			wait = new WebDriverWait(driver, 180);
			return wait;

		}
		
//waitForConnectCardLoadMsgToBeInvisible
		public void waitForConnectCardLoadMsgToBeInvisible() throws InterruptedException {
			RemoteWebDriver driver = getDriver();
			if(waitForElementToBe(By.xpath("//div[@class='x-loading-spinner-outer']"), "VISIBLE", driver, 30)!=null) {
				Assert.assertNull(waitForElementToBe(By.xpath("//div[@class='x-loading-spinner-outer']"), "INVISIBLE", 
						driver, 240), "Requested card screen is not loaded");
			}
		}

//gotitibutton
		public static void gotitbtn()
		{ 
			waitForElementToBe(By.xpath(properties.getProperty("gotitbtn")), "CLICKABLE", Init.driver, 90);
		}
}
