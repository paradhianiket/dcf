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
import org.testng.asserts.SoftAssert;

import dcf.script.common.Offences;
import dcf.script.common.Person;
import dcf.script.utility.CommonUtil;
import dcf.script.utility.ExcelReader;
import io.github.bonigarcia.wdm.OperatingSystem;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Init 
{
	public static SoftAssert soft;
	public static Object carddata;
	public static RemoteWebDriver driver;
	private static Properties properties;
	public static boolean goCards = true;
	public static ExcelReader reader=new ExcelReader();
	public static Person persondetails= new Person();
	public static Offences off=new Offences();
	
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
		Thread.sleep(5500);
		CommonUtil.waitForElementToBe((By.id("splashScreenBtnOk")), "VISIBLE", driver, 350);
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
		CommonUtil.switchmainframe();
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
			
//togetcard
			public static void cardlist() throws Exception
			{
				CommonUtil.switchmobileframe();
				List <WebElement> cardlist=driver.findElements(By.xpath(properties.getProperty("cardthumbnail")));
				for(int i=0; i<cardlist.size(); i++)
				{
					reader.getCellData("CaseIcon","CardName");
					carddata=reader.datavalue;
					String cardname=cardlist.get(i).getText();
					String[] splitStr = cardname.split("\\s+");
					for(int j=0; j<reader.getRowCount("CaseIcon"); j++)
					{
						carddata=reader.datavalue[j][0];
						if(carddata.toString().equalsIgnoreCase(splitStr[0]))
						{
							if(splitStr[0].equalsIgnoreCase("Defendant"))
							{
								cardlist.get(i).click();
								CommonUtil.cardrequired();
								CommonUtil.yellowpages();
								Person.persondetails();
								Person.mandatepersondetails();
								break;
							}
							else if(splitStr[0].equalsIgnoreCase("offence(s)"))
							{
								cardlist.get(i).click();
								off.offencedetails();
								
							}
						}
					}
				}
			}
}

