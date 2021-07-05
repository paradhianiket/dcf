package dcf.script.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

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
		List <WebElement> labels=Init.driver.findElements(By.xpath(properties.getProperty("defandantlabels")));
		labels.size();
		for(int i=0; i<reader.getColumnCount("Person"); i++)
		{
			persondata=ExcelReader.datavalue[0][i];
			for(int j=0; j<labels.size(); j++)
			{
				String labelvalue=labels.get(j).getText();
				WebElement Surname=labels.get(j).findElement(By.xpath(properties.getProperty("Surname")));
				WebElement Forename=labels.get(j).findElement(By.xpath(properties.getProperty("Forename")));
				WebElement Gender=labels.get(j).findElement(By.xpath(properties.getProperty("Gender")));
				WebElement Birthdate=labels.get(j).findElement(By.xpath(properties.getProperty("Birthdate")));
				if(labelvalue.equalsIgnoreCase(persondata.toString()))
				{
					if(labelvalue.equalsIgnoreCase("Surname"))
					{
						Surname.sendKeys(ExcelReader.datavalue[1][i].toString());
					}
					else if(labelvalue.equalsIgnoreCase("Forename 1"))
					{
						Forename.sendKeys(ExcelReader.datavalue[1][i].toString());
					}
					else if(labelvalue.equalsIgnoreCase("Gender"))
					{
						Gender.sendKeys(ExcelReader.datavalue[1][i].toString());
					}
					else if(labelvalue.equalsIgnoreCase("Date of Birth"))
					{
						Birthdate.sendKeys(ExcelReader.datavalue[1][i].toString());
					}
						
				}
			}
		}
		List <WebElement> nxtbtn= Init.driver.findElements(By.xpath(properties.getProperty("nextbutton")));
 		for(int i=0; i<nxtbtn.size(); i++)
 		{
 			nxtbtn.get(1).click();
 		}
		waitForElementToBe(By.xpath(properties.getProperty("Noneofthese")), "VISIBLE", Init.driver, 25);
		Init.driver.findElement(By.xpath(properties.getProperty("Noneofthese")));
	}
	
	public static void mandatepersondetails()
	{
		List <WebElement> tabs=Init.driver.findElements(By.xpath(properties.getProperty("tabname")));
		for(int k=0; k<tabs.size(); k++)
		{
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
