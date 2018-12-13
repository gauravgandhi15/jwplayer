package com.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.utils.BasePage;

public class DashboardPage extends BasePage {

	public DashboardPage(WebDriver driver) {
		super(driver);	
	}
	
	@FindBy(css="[href='/#/players/list']")
	private WebElement playerMenu;
	
	@FindBy(xpath="(//a[@class='name videoTruncate ng-binding ng-scope'])[1]")
	private WebElement firstPlayer;
		
	@FindBy(id="player-name")
	private WebElement playerNameInput;
	
	@FindBy(xpath="(//a[@class='button button-sm form-dropdown dropdown-toggle ng-scope'])[1]")
	private WebElement aspectRatioDropdown;
	
	@FindBy(xpath="//button[@class='button button-med button-default']")
	private WebElement saveButton;
	
	@FindBy(xpath="//*[@ng-click='close()']")
	private WebElement closeButton;
	
	public void clickOnPlayer(){		
		clickOn(playerMenu);
	}
	

	public void selectFirstPlayer(){		
		clickOn(firstPlayer);
	}
	
	public void changePlayerName(String playerName){
		inputText(playerNameInput, playerName);
	}
	
	public void selectAspectRatio(String aspectRatio){
		clickOn(aspectRatioDropdown);
		clickOn(getDriver().findElement(By.xpath("//a[contains(text(),'"+aspectRatio+"')]")));
	}
	
	public void clickOnSaveButton(){		
		clickOn(saveButton);
	}
	
	public void clickOnCloseButton(){		
		clickOn(closeButton);
	}
	
	public void verifyChanges(String playerName, String ratio){
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'" + playerName + "')]"))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'" + playerName + "')]"
				+ "/ancestor::tr[@class='ng-scope']//td[text()='"+ratio+"']")).isDisplayed());

	}
}

