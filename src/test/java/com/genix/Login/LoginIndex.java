package com.genix.Login;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.genix.init.Common;
import com.genix.init.SeleniumInit;
import com.genix.utility.TestData;
import static com.genix.init.ITestStatus.FAILED;
import static com.genix.init.ITestStatus.PASSED;


public class LoginIndex extends SeleniumInit{

	
	@Test(description = "Do Login")
	public void login() throws IOException, AWTException{

		int numOfFailure=0;
		
		Assert.fail();
		log("Open URL : "+testUrl);	
		
		//Parameters
		String LoginEmail, Password;

		log("Do login with valid email and password.");
		
		//For Data Driven
		File f = new File("UploadData/Sigin.xlsx");
		String SiginExcel = f.getAbsolutePath();
				
		ArrayList<String> EmailExcel = TestData.getColumnData(SiginExcel, "Sigin", "Email");
		ArrayList<String> PasswordExcel = TestData.getColumnData(SiginExcel, "Sigin", "Password");
		
		
		LoginEmail = EmailExcel.get(0);
		Password = PasswordExcel.get(0);
		
		log("Do login with valid username and password.");
		loginVerification = loginIndexPage.doLogin(LoginEmail, Password);
		

		if (numOfFailure > 0) {
			Assert.assertTrue(false);
		} 

	}
	
	@Test
	public void selectProduct() throws IOException, AWTException{
		
		int numOfFailure=0;
				 
		log("Click on product.");
		loginVerification=loginIndexPage.product();
		
		
		if(loginVerification.verifyAddedProductToCart())
		{
			Common.pause(3);
			logStatus(PASSED,"Product loaded in cart.");
		}
		else{
			logStatus(FAILED,"Product not loaded in cart.");
			numOfFailure++;
		}

		if (numOfFailure > 0) {
			Assert.assertTrue(false);
		} 
		
	}
	
	@Test
	public void productPriceVerification() throws IOException, AWTException{
		
		int numOfFailure=0;
		
		log("Filter set to Low to High");
		loginVerification=loginIndexPage.filterLowToHigh("Price (low to high)");
		
		if(loginVerification.priceAll())
		{
			logStatus(PASSED,"Filter applied.");
		}
		else{
			logStatus(FAILED,"Filter not applied.");
			numOfFailure++;
		}

		if (numOfFailure > 0) {
			Assert.assertTrue(false);
		} 
	}
	
	
	@Test
	public void removeProductFromCart() throws IOException, AWTException{
		
		int numOfFailure=0;
		
		log("Checking if product added succesfully");
		boolean countCheck =loginIndexPage.checkCartCount();
		
		if(countCheck==true)
		{
			logStatus(PASSED,"Product added in cart.");
		}
		else{
			logStatus(FAILED,"Product not added in cart.");
			numOfFailure++;
		}
		
		if (numOfFailure > 0) {
			Assert.assertTrue(false);
		} 
		
	}
	
	@Test
	public void logout() throws IOException, AWTException{
		
		Assert.fail();
		int numOfFailure=0;
		
		log("Logging out.");
		loginVerification=loginIndexPage.logout();

		if (numOfFailure > 0) {
			Assert.assertTrue(false);
		} 
		
	}

}
