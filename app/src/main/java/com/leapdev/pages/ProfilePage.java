package com.leapdev.pages;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage {

	private WebDriver driver;

	public ProfilePage(WebDriver driver) {
		this.driver = driver;
	}

	// Locators for the login page elements
	private By logoutButton = By.xpath("//button[contains(text(),'Log out')]");

	// private By welcomeMessage = By.xpath("//*[@id=\"userForm\"]/div[1]/h5");
	private By welcomeMessage = By.xpath("//h5[contains(text(),'Login in Book Store')]");

	private By profilePageMenuLink = By.xpath("//span[@class='text' and text()='Profile']");

	private By profilePageHeader = By.xpath("//*[@class='main-header']");

	private By searchBox = By.id("searchBox");

	private By table = By.cssSelector(".ReactTable.-striped.-highlight");

	private By rows = By.className("rt-tr-group");

	private By actionButton = By.className("action-buttons");

	private By modelDialogOk = By.id("closeSmallModal-ok");

	private By deleteButton = By.cssSelector("span#delete-record-undefined[title='Delete']");

	public void clickLogoutButton() {
		driver.findElement(logoutButton).click();
	}

	public String getWelcomeMessage() {
		return driver.findElement(welcomeMessage).getText();
	}

	public void clickProfileMenu() {
		driver.findElement(profilePageMenuLink).click();
	}

	public By getProfileMenuLink() {
		return profilePageMenuLink;
	}

	public void enterSearch(String search) {
		driver.findElement(searchBox).sendKeys(search);
	}

	public String getProfileHeader() {
		return driver.findElement(profilePageHeader).getText();
	}

	public void waitTableToLoad() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(table));
	}

	public void waitVisibilityProfileMenu() {
		// Locate the target element
		WebElement element = driver.findElement(profilePageMenuLink);
		// Scroll down until the element is visible
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public boolean removeBookFromCollection(String search) {
		WebElement searchResultsTable = driver.findElement(table);
		List<WebElement> searchResultRows = searchResultsTable.findElements(rows);
		for (WebElement row : searchResultRows) {
			List<WebElement> cells = row.findElements(actionButton);
			for (WebElement cell : cells) {
				if (cell.getText().equals(search)) {
					// Find the span element by id and title
					WebElement spanElement = driver.findElement(deleteButton);
					spanElement.click();
					WebDriverWait wait = new WebDriverWait(driver, 10);
					WebElement modalDialog = wait.until(ExpectedConditions.elementToBeClickable(modelDialogOk));
					// Click on the modal dialog
					modalDialog.click();
					Alert alert = wait.until(ExpectedConditions.alertIsPresent());
					alert.accept();
					driver.switchTo().defaultContent();
					return true;
				}
			}
		}
		return false;
	}
}