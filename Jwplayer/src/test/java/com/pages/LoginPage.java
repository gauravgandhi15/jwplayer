package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utils.BasePage;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);	
	}
	
	@FindBy(id="email")
	private WebElement userInput;
	
	@FindBy(xpath="//input[@name='password']")
	private WebElement passwordField;
	
	@FindBy(id="submit_login")
	private WebElement submitButton;
	
	public DashboardPage loginInApp(String userName, String password){
		inputText(userInput, userName);
		inputText(passwordField, password);
		clickOn(submitButton);
		return PageFactory.initElements(getDriver(), DashboardPage.class);
	}
}
