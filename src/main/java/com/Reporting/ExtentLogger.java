package com.Reporting;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

import com.BaseTest.BaseTest;

public class ExtentLogger {
	
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    // Set the current ExtentTest instance for the current thread
    public static void setTest(ExtentTest test) {
        testThread.set(test);
    }

    // Get the current ExtentTest instance for the current thread
    public static ExtentTest getTest() {
        return testThread.get();
    }

    // Log info message
    public static void logInfo(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.info(message);
        } else {
            throw new IllegalStateException("ExtentTest is not initialized. Ensure setTest() is called before logging.");
        }
    }

    // Log screenshot
    public static void logScreenshot(String message) {
        WebDriver driver = BaseTest.getDriver();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is null. Ensure WebDriver is initialized in BaseTest.");
        }

        String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        getTest().addScreenCaptureFromBase64String(base64Screenshot, message);
    }
    

}
