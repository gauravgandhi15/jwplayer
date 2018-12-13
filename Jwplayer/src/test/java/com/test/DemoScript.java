package com.test;

import java.util.Random;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.pages.DashboardPage;
import com.pages.LoginPage;
import com.utils.BaseTest;

public class DemoScript extends BaseTest {
	// WebDriver driver;
	Random rg = new Random();
	int randomInt = rg.nextInt(99999);
	String playerName = "Tester" + randomInt;

	@Test
	public void loginTest() throws InterruptedException {
		LoginPage loginPage = PageFactory.initElements(getWebDriver(), LoginPage.class);
		DashboardPage dashboardPage = loginPage.loginInApp(username, password);

		// Click on Player link from left navigation bar
		reportLog("Click on Player link from left navigation bar");
		dashboardPage.clickOnPlayer();

		// Select first player name
		reportLog("Select first player name");
		dashboardPage.selectFirstPlayer();
		
		// Clear the text filed and re enter the new name
		reportLog("Clear the text filed and re enter the new name");
		dashboardPage.changePlayerName(playerName);

		 //change the aspect ratio, and save your changes	
		reportLog("change the aspect ratio, and save your changes");
		dashboardPage.selectAspectRatio("16:10");

		// Click on Save Button
		reportLog("Click on Save Button");
		dashboardPage.clickOnSaveButton();

		// Click on Close Button
		reportLog("Click on Close Button");
		dashboardPage.clickOnCloseButton();

		// Changed name is showing on Players page
		reportLog("Changed name is showing on Players page");
		dashboardPage.verifyChanges(playerName, "16:10");
		
	}


}