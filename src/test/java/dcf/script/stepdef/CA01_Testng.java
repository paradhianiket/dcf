package dcf.script.stepdef;

import org.testng.annotations.Test;
import dcf.script.init.Init;

public class CA01_Testng extends Init
{

  @Test
  public void loginandroles() throws Exception
	{
	  lprop();
	  setChromeDriver();
	  roleselection();
	  iconselection("Create Precharge Case");
	  cardlist();
	}
}
