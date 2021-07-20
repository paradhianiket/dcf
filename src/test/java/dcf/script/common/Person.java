package dcf.script.common;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import dcf.script.init.Init;
import dcf.script.utility.CommonUtil;
import dcf.script.utility.ExcelReader;
import org.openqa.selenium.JavascriptExecutor;

public class Person extends CommonUtil
{
	public static Object persondata;
	public static ExcelReader reader= new ExcelReader();
	public static void persondetails()
	{
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
		String tabname;
		List<WebElement> tabbuttons=Init.driver.findElements(By.xpath(properties.getProperty("tabbutton")));
		for(int i=0; i<tabbuttons.size(); i++)
		{
			String buttonname=tabbuttons.get(i).getAttribute("aria-label");
			Matcher match= Pattern.compile("^\s*[a-zA-Z0-9]+\s*[a-zA-Z0-9]+").matcher(buttonname);
			match.find();
			tabname= match.group();
			for(int j=0; j<reader.getsheetnumber();j++)
			{
				if(tabname.equalsIgnoreCase(reader.sheetname()[j].toString()))
				{
					if(tabname.equalsIgnoreCase("Basic Details"))
					{
						System.out.println("Basic card is filled.");
					}
				else
					if(tabname.equalsIgnoreCase("Contact Details"))
					{
						tabbuttons.get(i).click();
						waitForElementToBe(By.xpath(properties.getProperty("addressbtn")), "PRESENCE", Init.driver, 25);
						Init.driver.findElement(By.xpath(properties.getProperty("addressbtn"))).click();
						reader.getCellData(tabname,"Link type");
						List<WebElement> contactdetails=Init.driver.findElements(By.xpath(properties.getProperty("contactdetails")));
						for(int l=0; l<contactdetails.size(); l++)
						{
							String persondetailstxtbxvalues=contactdetails.get(l).getAttribute("name");
							Matcher match1= Pattern.compile("[a-zA-Z0-9]+$").matcher(persondetailstxtbxvalues);
							match1.find();
							String persondetailstxtbxvalue=match1.group();
							for(int k=0; k<reader.getColumnCount(tabname); k++)
							{
								persondata=ExcelReader.datavalue[0][k];
								if(persondetailstxtbxvalue.trim().equalsIgnoreCase(persondata.toString().trim()))
								{
									contactdetails.get(l).sendKeys(ExcelReader.datavalue[1][k].toString());
								}	
							}
						}
						waitForElementToBe(By.xpath(properties.getProperty("findbtn")), "VISIBLE", Init.driver, 80);
						WebElement findbtn=Init.driver.findElement(By.xpath(properties.getProperty("findbtn")));
						findbtn.click();
						waitForElementToBe(By.xpath(properties.getProperty("Addresslist")), "VISIBLE", Init.driver, 80);
						WebElement addlist=Init.driver.findElement(By.xpath(properties.getProperty("Addresslist")));
						addlist.click();
						waitForElementToBe(By.xpath(properties.getProperty("Addresssavebtn")), "CLICKABLE", Init.driver, 80);
						List <WebElement> addsavebtn=Init.driver.findElements(By.xpath(properties.getProperty("Addresssavebtn")));

							addsavebtn.get(1).click();

						List <WebElement> addsavebtn1=Init.driver.findElements(By.xpath(properties.getProperty("Addresssavebtn")));
							addsavebtn1.get(1).click();	
					}
					else if(tabname.equalsIgnoreCase("Descriptive Details"))
					{
						try 
						{
						tabbuttons.get(i).click();
						reader.getCellData(tabname,"Link type");
						List <WebElement> descplabels=Init.driver.findElements(By.xpath(properties.getProperty("descplabels")));
						for(int k=0; k<descplabels.size(); k++)
						{
							String labelvalue=descplabels.get(k).getAttribute("name");
							Matcher match1= Pattern.compile("[a-zA-Z0-9]+$").matcher(labelvalue);
							match1.find();
							String persondetailstxtbxvalue=match1.group();
							for(int m=0; m<reader.getColumnCount(tabname); m++)
							{
								persondata=ExcelReader.datavalue[0][m];
								if(persondetailstxtbxvalue.trim().equalsIgnoreCase(persondata.toString().trim()))
								{
									descplabels.get(k).sendKeys(ExcelReader.datavalue[1][m].toString());
								}	
							}	
						}
						}
						catch(Exception e)
						{
							e.getStackTrace();
						}
					}
					
				}
			}
		}
		Init.driver.findElement(By.xpath(properties.getProperty("savebtn"))).click();
}
	
}
