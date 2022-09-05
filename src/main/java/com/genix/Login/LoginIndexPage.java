package com.genix.Login;




import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.genix.init.AbstractPage;
import com.genix.init.Common;


public class LoginIndexPage extends AbstractPage{

	public LoginIndexPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	//Login

	@FindBy(id="user-name")
	WebElement emailID;
	@FindBy(id="password")
	WebElement passwordtxt;
	@FindBy(id="login-button")
	WebElement loginBtn;

	public LoginVerification doLogin(String email, String password) throws AWTException
	{
		emailID.sendKeys(email);
		passwordtxt.sendKeys(password);
		Common.clickOn(driver, loginBtn);
		return new LoginVerification(driver);
	}


	@FindBy(xpath="//div[text()='Sauce Labs Backpack']")
	WebElement product1;
	@FindBy(id="add-to-cart-sauce-labs-backpack")
	WebElement addToCart;

	public LoginVerification product() throws AWTException
	{
		Common.clickableElement(product1, driver);
		Common.clickOn(driver, product1);
		Common.clickableElement(addToCart, driver);
		Common.clickOn(driver, addToCart);

		return new LoginVerification(driver);
	}

	@FindBy(id="react-burger-menu-btn")
	WebElement burgerMenu;
	@FindBy(xpath="//a[@id='logout_sidebar_link']")
	WebElement logoutLink;

	public LoginVerification logout() 
	{

		Common.clickableElement(burgerMenu, driver);
		Common.clickOn(driver, burgerMenu);
		Common.clickableElement(logoutLink, driver);
		Common.clickOn(driver, logoutLink);

		return new LoginVerification(driver);
	}

	@FindBy(xpath="//select[@class='product_sort_container']")
	WebElement filterLowToHighDropDown;

	public LoginVerification filterLowToHigh(String value) {

		Common.clickableElement(filterLowToHighDropDown, driver);
		Common.selectFromComboByVisibleElement(filterLowToHighDropDown, value);

		return new LoginVerification(driver);
	}

	@FindBy(xpath="//span[@class='shopping_cart_badge']")
	WebElement cartCount;

	public boolean checkCartCount() {

		String s = cartCount.getText();
		int i=Integer.parseInt(s);  

		System.out.println(">>>> "+i);

		if(i<=0) {
			return false;		
		}else {
			return true;		

		}

	}

}
