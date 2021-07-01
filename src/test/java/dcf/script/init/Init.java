package dcf.script.init;

import static org.testng.Assert.assertNotNull;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.itextpdf.text.log.SysoCounter;

import dcf.script.utility.CommonUtil;
import dcf.script.utility.ExcelReader;
import io.github.bonigarcia.wdm.OperatingSystem;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Init 
{
	public static SoftAssert soft;
	public static RemoteWebDriver driver;
	private static Properties properties;
	public static boolean goCards = true;
	public static ExcelReader reader=new ExcelReader();
	
	public static RemoteWebDriver getDriver() 
	{
		return Init.driver;
	}

	public static void setDriver(RemoteWebDriver driver) 
	{
		Init.driver = driver;
	}
	
	public static void lprop() throws Exception 
	{
		properties = CommonUtil.loadProperties();
	}
	
	public static void setChromeDriver() throws Exception 
	{
		soft= new SoftAssert();
		String rmDriver = System.getProperty("rmDriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-fullscreen");
		options.setAcceptInsecureCerts(true);
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
		options.setCapability( "goog:loggingPrefs", logPrefs );
		WebDriverManager.chromedriver().operatingSystem(OperatingSystem.WIN).setup();
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		setDriver(driver);
		driver.get(properties.getProperty("URL"));
		soft.assertTrue(driver.findElement(By.id("mockIamTitle")).getText().equalsIgnoreCase("Mock IAM"));
	}
	
	public static void roleselection() throws InterruptedException
	{
		String envroles=properties.getProperty("CaseRoles");
		String[] listRoles = envroles.split(",");
		for (String role : listRoles)
		{
			Thread.sleep(500);
			WebElement CheckElement = driver.findElement(By.xpath(properties.getProperty("CheckElement")+"[@value='" + role + "']"));
			if (CheckElement.getAttribute("checked") == null)
				CheckElement.click();
				Thread.sleep(500);
		}
		WebElement BtnSubmit = driver.findElement(By.xpath(properties.getProperty("BtnSubmit")));
		BtnSubmit.click();
		Thread.sleep(500);
		WebElement BtnAccept= driver.findElement(By.id("splashScreenBtnOk")); 
		BtnAccept.click();
		Thread.sleep(500);
		if(envroles.contains("Admin"))
		{
			driver.findElement(By.xpath(properties.getProperty("adminradiobtn"))).click();	
			WebElement BtnSelect = driver.findElement(By.id(properties.getProperty("btnslctid")));
			assertNotNull(BtnSelect);
			BtnSelect.click();
		}
	}
	
	public static void iconselection(String iconNameValue) throws InterruptedException
	{
		CommonUtil.waitForPageLoadMsgToBeInvisible("loading...");
		CommonUtil.gotitbtn();
		driver.switchTo().defaultContent();
		CommonUtil.waitForElementToBe(By.cssSelector(properties.getProperty("iframe")), "CLICKABLE", driver, 30);
		driver.switchTo().frame(driver.findElement(By.cssSelector(properties.getProperty("iframe"))));
		List<WebElement> elHomeIconNodes = driver.findElements(By.xpath(properties.getProperty("icons")));
		System.out.println(elHomeIconNodes.size());
		for(int i=0; i<elHomeIconNodes.size(); i++)
		{
			if(elHomeIconNodes.get(i).getText().equalsIgnoreCase(iconNameValue))
			{
				elHomeIconNodes.get(i).click();
				break;
			}
			
		}
	}
	
//clk to add remove cards
			public static void clickaddremovecards() throws InterruptedException
			{
				CommonUtil.gotitbtn();
			}
			
//togetcard
			public static void cardlist()
			{
				reader.getCellData("CaseIcon","CardName");
				List <WebElement> cardlist=driver.findElements(By.xpath(properties.getProperty("cardthumbnail")));
				for(int i=0; i<cardlist.size(); i++)
				{
					Object cardvalue=reader.datavalue[i][0];
					String cardname=cardlist.get(i).getText();
					String[] splitStr = cardname.split("\\s+");
					if(cardvalue.toString().equalsIgnoreCase(splitStr[0]))
					{
						cardlist.get(i).click();
						CommonUtil.cardrequired();
						
					}
				}
			}
}

