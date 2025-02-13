package com.GreenKart.PageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.Utilities.UtilityWebElementFunctions;

public class GreenKartCheckOutPage extends UtilityWebElementFunctions {
	
	public WebDriver driver;
	
	public GreenKartCheckOutPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}
	
	
	@FindBy(xpath="//table[@id='productCartTables']/tbody/tr/td[2]/p")
	List <WebElement> tableitems;
	
	@FindBy(xpath="//button[text()='Place Order']")
	WebElement placeorder;
	
	@FindBy(css="select[style='width: 200px;']")
	WebElement selec;
	
	@FindBy(css=".chkAgree")
	WebElement agreecheckbox;
	
	@FindBy(xpath="//button[text()='Proceed']")
	WebElement finalprocced;
	
	public void checkoutCartverification(List <String> Vegetable) {
		int j=0;
		WaitFuncMultiple(tableitems);
		for(int k=0;k<tableitems.size();k++) {
			String titem=tableitems.get(k).getText().split(" ")[0].trim();
			if(Vegetable.contains(titem)) {
				j++;
				System.out.println(titem+"-"+"Item Verified/Present in the Cart");
				if(j==Vegetable.size()) {					
					break;
				}
				
			}else {
				System.out.println(titem+"-"+"Item is NOT Verified/Present in the Cart");
			}
			
			
			
		}
		
		
		waitfunc(placeorder);
		jsclick(placeorder);
		
	}
	
	public void TermsandConditionsagreement(String Country) {
		Select drpcountry= new Select(selec);
		drpcountry.selectByValue(Country);
		waitfunc(agreecheckbox);
		agreecheckbox.click();
		finalprocced.click();
		
		
	}
	


	

}
