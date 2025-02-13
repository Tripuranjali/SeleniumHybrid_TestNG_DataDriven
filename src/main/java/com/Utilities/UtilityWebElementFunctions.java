package com.Utilities;



import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class UtilityWebElementFunctions {

	public WebDriver driver;

	public UtilityWebElementFunctions(WebDriver driver) {
		this.driver = driver;

	}

	public void waitfunc(WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(ele));

	}

	public void scrollto(WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", ele);

	}

	public void jsclick(WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", ele);
	}
	
	

	public void pagezoomfunction(Double percentage) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("document.body.style.zoom=" + percentage + "");
	}

	public String getWebEleText(WebElement ele) {
		return ele.getText();

	}
	
    public void WaitFuncMultiple(List<WebElement> element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfAllElements(element));
        } catch (Exception e) {
            System.out.println("No Elements Present");
        }
    }
    
    public void scrolltilltop() {
    	try {
            JavascriptExecutor je = (JavascriptExecutor) driver;
            je.executeScript("window.scrollTo({ top: 0, behavior: 'smooth' });");
        } catch (Exception e) {
            System.out.println("No Element Found");
        }
    }
    
    public void scrolltillbottom() {
    	try {
            JavascriptExecutor je = (JavascriptExecutor) driver;
            je.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        } catch (Exception e) {
            System.out.println("No Element Found");
        }
    }
    
   /*
    public void onTestFailure(ITestResult result) {
        WebDriver driver = ((BaseTest) result.getInstance()).getDriver(); // Get WebDriver instance from BaseTest
        String screenshotPath = takeScreenshot(driver, result.getMethod().getMethodName());
        testThread.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());
        testThread.get().addScreenCaptureFromPath(screenshotPath);
    }*/

 

	








}
