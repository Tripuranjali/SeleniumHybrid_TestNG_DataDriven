package com.Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
	static Properties prop = new Properties();
	
	public static void readglobalpropfile() throws FileNotFoundException {    	
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "/src/test/java/com/TestDataResources/GlobalProperties.properties");
		try {
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
    public static String readbrowserprop() throws IOException {
    	readglobalpropfile();
    	return System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("DefaultBrowser");		
    }
    
    public static String readFlightBookingWebsiteprop() throws FileNotFoundException {
    	readglobalpropfile();
		return System.getProperty("env") != null ? System.getProperty("env")
				: prop.getProperty("FlightBookingWebsite");
    }
    public static String readEcommerceShopWebsiteprop() throws FileNotFoundException {
    	readglobalpropfile();
		return System.getProperty("env") != null ? System.getProperty("env")
				: prop.getProperty("EcommerceShopWebsite");
    }

    public static String readSeleniumConceptualWebsiteprop() throws FileNotFoundException {
    	readglobalpropfile();
		return System.getProperty("env") != null ? System.getProperty("env")
				: prop.getProperty("SeleniumConceptualWebsite");
    }

    public static String readSeleniumIdeasWebsiteprop() throws FileNotFoundException {
    	readglobalpropfile();
		return System.getProperty("env") != null ? System.getProperty("env")
				: prop.getProperty("SeleniumIdeasWebsite");
    }


}
