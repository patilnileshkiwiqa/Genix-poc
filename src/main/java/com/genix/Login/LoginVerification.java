package com.genix.Login;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.genix.init.AbstractPage;
import com.genix.init.Common;
import com.google.common.collect.Ordering;


public class LoginVerification extends AbstractPage {

	public LoginVerification(WebDriver driver) {
		super(driver);

	}

//	public Boolean verifyloginurl() 
//	{
//		Common.pause(1);
//		String xpath = "//h4[text()='Applications']";
//
//		if(driver.findElements(By.xpath(xpath)).size()==1)
//			return true;
//		else
//			return false;	
//	}
//	
	
	public Boolean verifyAddedProductToCart() 
	{
		Common.pause(2);
		String xpath = "//span[@class='shopping_cart_badge']";

		if(driver.findElements(By.xpath(xpath)).size()==1)
			return true;
		else
			return false;	
	}
	
	public boolean priceAll() {
		
		ArrayList<Float> s = new ArrayList<>();

		List<WebElement> m = driver.findElements(By.xpath("//div[@class='inventory_item_price']"));
		for(int i = 0; i< m.size(); i++) {
			System.out.println("price"+m.get(i).getText().replace("$",""));
			
			s.add(Float.parseFloat(m.get(i).getText().replace("$","")));
			
			
		}

		return Ordering.natural().isOrdered(s);

	}

}

