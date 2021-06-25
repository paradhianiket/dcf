package dcf.script.utility;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import dcf.script.init.Init;

public class CommonUtil 
{
	public static Properties properties;
	public static Logger Log = LogManager.getLogger();
	public static String CONFIGS_FOLDER = "src/main/resources/Configs/";
	public static final String TESTDATA_FOLDER = "src/main/resources/Testdatafiles/";
	public static String configFileName = "Stgamo";
	public static String testEnvironment = "";
	
//to get properties	
		public static Properties getProperties() throws Exception 
		{
			try 
			{
				if (properties == null) 
				{
					properties = new Properties();
					InputStream input = null;
					input = new FileInputStream(CONFIGS_FOLDER + "Config" + configFileName + ".properties");
					properties.load(input);
				}
			} 
			catch (Exception e) 
			{
				Log.error("Unable to load Properties" + e);
				throw (e);
			}
			return properties;
		}
		
//to load properties
		public static Properties loadProperties() throws Exception 
		{
			properties = getProperties();
			return properties;
		}

//to add properties
		public static void addProperties(String eventType) throws Exception 
		{
			Properties task = new Properties();
			task.load(new FileInputStream(CONFIGS_FOLDER + properties.getProperty("Customer").trim() + ".properties"));
			properties.putAll(task);
		}
		
		public static void setTestEnvironment(String strTestEnvironment)
		{
			testEnvironment = strTestEnvironment;
		}
		
		public static void setConfigFileName(String Config) throws Exception
		{
			String jenkinsConfig = System.getProperty("jenkinsConfig");
			configFileName = jenkinsConfig;
		}
}
