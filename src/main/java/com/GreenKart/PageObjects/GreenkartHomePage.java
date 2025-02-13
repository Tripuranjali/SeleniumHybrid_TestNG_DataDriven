package com.GreenKart.PageObjects;


import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Utilities.UtilityWebElementFunctions;

public class GreenkartHomePage extends UtilityWebElementFunctions {
	
	public WebDriver driver;
	
	public GreenkartHomePage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}
	
	//div[@class(contains,'greenLogo')]/following::sibling-
	//.greenLogo/redLogo-
	
	@FindBy(xpath="//div[contains(@class,'greenLogo')]")
	WebElement Logo;
	
	@FindBy(css="h4.product-name")
	List <WebElement> getproductslist;
	
	@FindBy(xpath="//div[@class='product-action']/button")
	List<WebElement> AddtoCartbutton;
	
	@FindBy(css="a.cart-icon")
	WebElement carticon;
	
	@FindBy(css="div.cart-preview button")
	WebElement proccedbutton;

	
	public void goTo(String url) {
		driver.get(url);
	}
	
	public  boolean VerifyLogo() {
		 return Logo.isDisplayed();
	}
	
	public GreenKartCheckOutPage addveggietoCart(List<String> Veggies) {
		int j=0;
		for(int i=0;i<getproductslist.size();i++) {
			String productname=getproductslist.get(i).getText().split(" ")[0].trim();
			
			if(Veggies.contains(productname)) {
				j++;
				jsclick(AddtoCartbutton.get(i));				
				if(j==Veggies.size()) {					
					break;
				}
				
			}
		}
		waitfunc(carticon);
		jsclick(carticon);
		if(proccedbutton.isEnabled()) {
			jsclick(proccedbutton);
		}
		
	return new GreenKartCheckOutPage(driver);		
		
	}
	

}
