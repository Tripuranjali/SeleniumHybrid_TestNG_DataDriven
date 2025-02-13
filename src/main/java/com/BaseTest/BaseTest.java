package com.BaseTest;



import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


import com.Utilities.PropertiesUtils;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    private String browserName;
    //private String environmentName;

    /**
     * Initializes the WebDriver based on the given browser name.
     */
    private WebDriver initializeDriver(String browser) throws IOException {
        if (browser == null || browser.isEmpty()) {
            browser = PropertiesUtils.readbrowserprop(); // Fallback to properties if not provided
        }
        this.browserName = browser; // Ensure the correct browser name is set

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                WebDriverManager.chromedriver().setup();
                if (PropertiesUtils.readbrowserprop().contains("headless")) {
                    chromeOptions.addArguments("--headless");
                    chromeOptions.setAcceptInsecureCerts(true);
                }
                driverThread.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                WebDriverManager.firefoxdriver().setup();
                if (PropertiesUtils.readbrowserprop().contains("headless")) {
                    firefoxOptions.addArguments("--headless");
                    firefoxOptions.setAcceptInsecureCerts(true);
                }
                driverThread.set(new FirefoxDriver(firefoxOptions));
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                WebDriverManager.edgedriver().setup();
                if (PropertiesUtils.readbrowserprop().contains("headless")) {
                    edgeOptions.addArguments("--headless");
                    edgeOptions.setAcceptInsecureCerts(true);
                }
                driverThread.set(new EdgeDriver(edgeOptions));
                break;

            default:
                throw new IllegalArgumentException("Browser \"" + browser + "\" not supported.");
        }

        WebDriver driver = getDriver();
        driver.manage().window().maximize();
        return driver;
    }

    /**
     * Navigates to the given environment's URL.
     */
   /* private void goTo(String environment) throws IOException {
       /* if (environment == null || environment.isEmpty()) {
            environment = PropertiesUtils.readenvprop(); // Fallback to properties if not provided
        }
        this.environmentName = environment; // Ensure the correct environment name is set        
        getDriver().get(environment);
    }*/

    /**
     * Returns the appropriate URL based on the environment.
     */
   /* private String getUrlForEnvironment(String environment) {
        switch (environment.toLowerCase()) {
            case "tst1": return "";
            case "tst2": return "https://retail-tst.metropolitan.co.za/mmih-cdi-search-client/login";
            case "tst3": return "https://retail-pre.metropolitan.co.za/mmih-cdi-search-client/login";
            case "tst4": return "";
            case "tst5": return "";
            default:
                throw new IllegalArgumentException("Environment \"" + environment + "\" not supported.");
        }
    }*/

    /**
     * Provides a thread-safe WebDriver instance.
     */
    public static WebDriver getDriver() {
        return driverThread.get();
    }

    /**
     * Test setup: Initializes WebDriver and navigates to the application.
     * @return 
     * @return 
     */
    @Parameters("browser")
    @BeforeMethod
    public void  launchApplication(@Optional("") String browser) throws IOException {
        initializeDriver(browser);        

       

    }

    /**
     * Getter method for retrieving the browser name.
     */
    public String getBrowserName() {
        return (browserName != null && !browserName.isEmpty()) ? browserName : "Unknown";
    }

    /**
     * Getter method for retrieving the environment name.
     
    public String getEnvName() {
        return (environmentName != null && !environmentName.isEmpty()) ? environmentName : "Unknown";
    }
    */

    /**
     * Cleans up WebDriver after each test method.
     */
   @AfterMethod
    public void tearDown() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            driverThread.remove(); // Ensure proper cleanup
        }
    }
}





