package dcf.script.stepdef;

import org.testng.annotations.Test;

import dcf.script.init.Init;
import dcf.script.utility.ExcelReader;

public class CA01_Testng extends Init
{
	 public static final String CaseIcon = null;
	ExcelReader reader=new ExcelReader();
  @Test
  public void loginandroles() throws Exception
	{
	  lprop();
	  setChromeDriver();
	  roleselection();
	  iconselection(CaseIcon);
	}

}