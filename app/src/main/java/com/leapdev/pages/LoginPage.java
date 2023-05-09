package com.leapdev.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private WebDriver driver;
	private WebDriverWait wait;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	// Locators for the login page elements
	private By usernameInput = By.id("userName");
	private By passwordInput = By.id("password");
	private By loginButton = By.xpath("//button[contains(text(),'Login')]");
	private By successMsg = By.xpath("//*[@id=\"userName-value\"]");
	private By errorMsg = By.xpath("//*[@id=\"name\"]");

	// Methods to interact with the login page elements
	public void enterUsername(String username) {
		driver.findElement(usernameInput).sendKeys(username);
	}

	public void enterPassword(String password) {
		driver.findElement(passwordInput).sendKeys(password);
	}

	public void clickLoginButton() {
		driver.findElement(loginButton).click();
	}

	public String getSuccessMessage() {
		return driver.findElement(successMsg).getText();
	}

	public String getErrorMessage() {
		return driver.findElement(errorMsg).getText();
	}
	
	public WebElement waitVisibility(By by) {
		wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}