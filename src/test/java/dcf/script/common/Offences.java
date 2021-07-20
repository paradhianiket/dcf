package dcf.script.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import dcf.script.init.Init;
import dcf.script.utility.ExcelReader;

public class Offences extends Person
{
	public void offencedetails()
	{
		reader.getCellData("Offence(s)","Link type");
		persondata=ExcelReader.datavalue;
		WebElement Addoffencebtn=Init.driver.findElement(By.xpath(properties.getProperty("Addoffencebtn")));
		Addoffencebtn.click();
		WebElement dfndtselection=Init.driver.findElement(By.xpath(properties.getProperty("defendantselection")));
		dfndtselection.click();
	}
}
