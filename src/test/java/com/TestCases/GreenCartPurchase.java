package com.TestCases;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.BaseTest.BaseTest;
import com.GreenKart.PageObjects.GreenKartCheckOutPage;
import com.GreenKart.PageObjects.GreenkartHomePage;
import com.Reporting.ExtentLogger;
import com.Utilities.ExcelUtils;

import com.Reporting.RetryFlakyTests;

public class GreenCartPurchase extends BaseTest {
	
	@Test(dataProvider = "Greenkartdata", retryAnalyzer=RetryFlakyTests.class)
	public void purchaseOrder(List<String> veggies, String country) {
        GreenkartHomePage gp= new GreenkartHomePage(getDriver());        
		gp.goTo("https://rahulshettyacademy.com/seleniumPractise/#/");
		Assert.assertEquals(true, gp.VerifyLogo());
		ExtentLogger.logScreenshot("Logo Verification");
		GreenKartCheckOutPage gc=gp.addveggietoCart(veggies);
		ExtentLogger.logScreenshot("Add to Cart Verification");
		gc.checkoutCartverification(veggies);		
		gc.TermsandConditionsagreement(country);
		ExtentLogger.logScreenshot("End Page Verification");
		
		
	}
	
	
	
	
	
	@DataProvider(name = "Greenkartdata")
	public Object[][] getVeggiesAndCountryData() throws IOException {
	    Map<String, List<String>> excelData = ExcelUtils.getAllColumnsData(System.getProperty("user.dir")+"//src//test//java//com//TestDataResources//VeggiesData.xlsx", "TestData");

	    List<String> veggies = excelData.get("Veggies"); // Extract the list of veggies
	    List<String> countries = excelData.get("Country"); // Extract the list of countries

	    int rowCount = countries.size(); // Number of test cases (one country per test)

	    Object[][] dataProvider = new Object[rowCount][2];

	    for (int i = 0; i < rowCount; i++) {
	        dataProvider[i][0] = veggies; // Pass entire veggie list (same for all tests)
	        dataProvider[i][1] = countries.get(i); // One country per test
	    }

	    return dataProvider;
	}


}
