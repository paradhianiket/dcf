package dcf.script.init;

import static org.testng.Assert.assertNotNull;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;
import dcf.script.utility.CommonUtil;
import dcf.script.utility.ExcelReader;
import io.github.bonigarcia.wdm.OperatingSystem;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Init 
{
	public static RemoteWebDriver driver;
	private static Properties properties;
	public RemoteWebDriver getDriver() 
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
	}
	
	public static void roleselection() throws InterruptedException
	{
		String envroles=properties.getProperty("CaseRoles");
		String[] listRoles = envroles.split(",");
		for (String role : listRoles)
		{
			WebElement ChckElement = driver.findElement(By.xpath("//input[@type='checkbox'][@value='" + role + "']"));
			if (ChckElement.getAttribute("checked") == null)
			ChckElement.click();
			Thread.sleep(500);
		}
		WebElement BtnSubmit = driver.findElement(By.xpath("//input[@type='submit'][@value='Submit']"));
		BtnSubmit.click();
		Thread.sleep(500);
		WebElement BtnAccept= driver.findElement(By.id("splashScreenBtnOk")); 
		BtnAccept.click();
		Thread.sleep(500);
		if(envroles.contains("Admin"))
		{
			driver.findElement(By.xpath(".//*[@id='radioUser']//input")).click();	
			// Clicking on Select Button
			WebElement BtnSelect = driver.findElement(By.id("btnSelectWorkspace"));
			assertNotNull(BtnSelect);
			BtnSelect.click();
		}
	}
	
	public static void iconselection(String iconNameValue)
	{
		List <WebElement> elHomeIconNodes= driver.findElementsByCssSelector("div.lp-icons div.lp-icon-item div.lp-text");
		boolean iconFound = false;
		ExcelReader reader=new ExcelReader();
		reader.getCellData(iconNameValue);
		for (WebElement ChildNode : elHomeIconNodes) 
		{
			if (ChildNode.getText().equalsIgnoreCase(iconNameValue)) 
			{
				WebElement el = ChildNode.findElement(By.xpath(".."));
				assertNotNull(el);
				el.click();
				iconFound = true;
				break;
			}
		}
	}
}
