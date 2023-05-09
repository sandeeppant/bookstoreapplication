package com.leapdev;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.leapdev.pages.LoginPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseUITest {

	private static final Logger logger = LogManager.getLogger(BaseUITest.class);
	
	private WebDriver driver;

	@BeforeMethod
	public void setup() {
		// Set up the driver
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@AfterMethod
	public void teardown() {
		// Quit the driver
		driver.quit();
	}
	
	public WebDriver getDriver()
	{
		return driver;
	}
	
	public void loginToApplication(WebDriver driver, Map<String, String> map) throws IOException
	{
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
		
		// Verify that the user is logged in
		Assert.assertEquals(loginPage.getSuccessMessage(),map.get("username"));
	}
}
