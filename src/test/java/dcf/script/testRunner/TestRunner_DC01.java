package dcf.script.testRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;

@CucumberOptions(snippets = SnippetType.CAMELCASE, 
plugin = 
{ 
	"json:target/JSON/CaseSystemTests_Report.json" 
},
glue = {"dcf.script.stepdef"},
monochrome = true, 
dryRun = false,
features = 
	{
		"resources/Features",
	}
)

public class TestRunner_DC01
{
	
}
