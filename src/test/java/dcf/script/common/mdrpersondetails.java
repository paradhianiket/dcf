package dcf.script.common;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.itextpdf.text.log.SysoCounter;

import dcf.script.init.Init;
import dcf.script.utility.CommonUtil;
import dcf.script.utility.ExcelReader;

public class mdrpersondetails extends CommonUtil
{
	public static Object persondata;
	public static void persondetails()
	{
		reader= new ExcelReader();
		reader.getCellData("Person","Link type");
		persondata=ExcelReader.datavalue;
		List <WebElement> persondetails=Init.driver.findElements(By.xpath(properties.getProperty("persondetails")));
		List <WebElement> persondetailstxtbx=Init.driver.findElements((By.xpath(properties.getProperty("persondetailstxtbox"))));
			for(int j=0; j<persondetailstxtbx.size(); j++)
			{
				String persondetailstxtbxvalues=persondetailstxtbx.get(j).getAttribute("name");
				Matcher match= Pattern.compile("[a-zA-Z0-9]+$").matcher(persondetailstxtbxvalues);
				match.find();
				String persondetailstxtbxvalue=match.group();
				for(int i=0; i<reader.getColumnCount("Person"); i++)
				{
					persondata=ExcelReader.datavalue[0][i];
					if(persondetailstxtbxvalue.trim().equalsIgnoreCase(persondata.toString().trim()))
					{
						persondetailstxtbx.get(j).sendKeys(ExcelReader.datavalue[1][i].toString());
						break;
					}
				}
			}
			try
			{
				List <WebElement> nxtbtn= Init.driver.findElements(By.xpath(properties.getProperty("nextbutton")));
				CommonUtil.waitForElementToBe(By.xpath(properties.getProperty("nextbutton")), "PRESENCE", Init.driver, 30);
				for(int i=0; i<nxtbtn.size(); i++)
				{
					nxtbtn.get(1).click();
					break;
				}
			}
			catch(Exception e)
			{
				e.getStackTrace();
			}
			
		waitForElementToBe(By.xpath(properties.getProperty("Noneofthese")), "PRESENCE", Init.driver, 25);
		Init.driver.findElement(By.xpath(properties.getProperty("Noneofthese")));
	}
	
	public static void mandatepersondetails()
	{
		List <WebElement> tabs=Init.driver.findElements(By.xpath(properties.getProperty("persontab")));
		for(int k=0; k<tabs.size(); k++)
		{
			String tabtext=tabs.get(k).getAttribute("aria-label");
			System.out.println(tabtext);
			tabs.get(4).click();
				reader= new ExcelReader();
				reader.getCellData("Descriptive_details_Inc_Disabilities","Link type");
				persondata=ExcelReader.datavalue;
				List <WebElement> labels=Init.driver.findElements(By.xpath(properties.getProperty("defandantlabels")));
				labels.size();
				for(int i=0; i<reader.getColumnCount("Descriptive_details_Inc_Disabilities"); i++)
		{
			persondata=ExcelReader.datavalue[0][i];
			for(int j=0; j<labels.size(); j++)
			{
				String labelvalue=labels.get(j).getText();
				WebElement Appereance=labels.get(j).findElement(By.xpath(properties.getProperty("Appereance")));
				if(labelvalue.equalsIgnoreCase(persondata.toString()))
				{
					if(labelvalue.equalsIgnoreCase("Ethnic Appearance"))
					{
						Appereance.sendKeys(ExcelReader.datavalue[1][i].toString());
					}
				}
			}
		}
	}
}
	
}
