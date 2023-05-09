package com.leapdev.uitests;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.leapdev.BaseUITest;
import com.leapdev.constant.Constant;
import com.leapdev.pages.LoginPage;
import com.leapdev.pages.ProfilePage;
import com.leapdev.utils.ReadCSV;

public class LoginTest extends BaseUITest {
	private static final Logger logger = LogManager.getLogger(LoginTest.class);

	@Test
	public void testValidLogin() throws IOException {
		WebDriver driver = getDriver();
		Map<String, String> map = ReadCSV.readDataByRow("login.csv", "TC01");
		// Navigate to the login page

		String url = map.get("url");
		logger.info("Login to Application: " + url);
		driver.get(url);

		// Create a new LoginPage object
		LoginPage loginPage = new LoginPage(driver);

		// Enter the username and password
		loginPage.enterUsername(map.get("username"));
		loginPage.enterPassword(map.get("password"));

		// Click the login button
		loginPage.clickLoginButton();
		logger.info("User logged into to Application");

		// Verify that the user is logged in
		Assert.assertEquals(loginPage.getSuccessMessage(), map.get("username"));
	}

	@Test
	public void testInValidLogin() throws IOException {
		WebDriver driver = getDriver();
		Map<String, String> map = ReadCSV.readDataByRow("login.csv", "TC02");
		// Navigate to the login page
		String url = map.get("url");
		logger.info("Login to Application: " + url);
		driver.get(url);

		// Create a new LoginPage object
		LoginPage loginPage = new LoginPage(driver);

		// Enter the username and password
		loginPage.enterUsername(map.get("username"));
		loginPage.enterPassword(map.get("password"));

		// Click the login button
		loginPage.clickLoginButton();

		logger.info("Error in logging into the application");
		// Verify that the user is not logged in
		Assert.assertEquals(loginPage.getErrorMessage(), Constant.LOGIN_ERROR_MESSAGE);
	}

	@Test
	public void testLogout() throws IOException {
		WebDriver driver = getDriver();
		Map<String, String> map = ReadCSV.readDataByRow("login.csv", "TC01");
		// Navigate to the login page
		String url = map.get("url");
		logger.info("Login to Application: " + url);
		driver.get(url);

		// Create a new LoginPage object
		LoginPage loginPage = new LoginPage(driver);

		// Enter the username and password
		loginPage.enterUsername(map.get("username"));
		loginPage.enterPassword(map.get("password"));

		// Click the login button
		loginPage.clickLoginButton();

		// Verify that the user is not logged in
		Assert.assertEquals(loginPage.getSuccessMessage(), map.get("username"));

		ProfilePage profilePage = new ProfilePage(driver);
		// Click the login button
		profilePage.clickLogoutButton();
		logger.info("User Logout of the application");
		Assert.assertEquals(profilePage.getWelcomeMessage(), Constant.WELCOME_MESSAGE);
	}
}
