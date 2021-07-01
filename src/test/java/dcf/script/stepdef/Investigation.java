package dcf.script.stepdef;

import org.testng.annotations.Test;

import dcf.script.init.Init;

public class Investigation extends Init {

	@Test
	public void ClickOnInvestigation() throws Exception
	{
		  lprop();
		  setChromeDriver();
		  roleselection();
		  iconselection("Create Generic Investigation"); 
	}
}

