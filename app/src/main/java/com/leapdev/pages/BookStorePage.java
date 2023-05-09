package com.leapdev.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookStorePage {

	private WebDriver driver;

	public BookStorePage(WebDriver driver) {
		this.driver = driver;
	}

	private By bookStoreMenuLink = By.xpath("//span[@class='text' and text()='Book Store']");

	private By bookStoreHeader = By.xpath("//*[@class='main-header']");

	private By searchBox = By.id("searchBox");

	private By table = By.cssSelector(".ReactTable.-striped.-highlight");
	
	private By addNewRecordButton = By.xpath("//button[@id='addNewRecordButton' and text()='Add To Your Collection']");
	
	private By rows = By.className("rt-tr-group");
	
	private By actionButton = By.className("action-buttons");

	public void clickBookStoreMenu() {
		driver.findElement(bookStoreMenuLink).click();
	}

	public By getBookStoreMenuLink() {
		return bookStoreMenuLink;
	}

	public String getBookStoreHeader() {
		return driver.findElement(bookStoreHeader).getText();
	}

	public void enterSearch(String search) {
		driver.findElement(searchBox).sendKeys(search);
	}
	
	public void addNewRecordButton() {
		driver.findElement(addNewRecordButton).click();
	}
	
	public String acceptConfirmation()
	{
		driver.findElement(addNewRecordButton).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		String message = alert.getText();
		alert.accept();
		driver.switchTo().defaultContent();
		return message;
	}

	public void waitVisibilityBookStoreMenu() {
		// Locate the target element
		WebElement element = driver.findElement(bookStoreMenuLink);
		// Scroll down until the element is visible
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void waitVisibilityAddNewButton() {
		// Locate the target element
		WebElement element = driver.findElement(addNewRecordButton);
		// Scroll down until the element is visible
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void waitTableToLoad() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(table));
	}

	public List<String> getSearchResults() {
		List<String> lst = new ArrayList<>();
		WebElement searchResultsTable = driver.findElement(table);
		List<WebElement> searchResultRows = searchResultsTable.findElements(rows);
		for (WebElement row : searchResultRows) {
			List<WebElement> cells = row.findElements(actionButton);
			for (WebElement cell : cells) {
				lst.add(cell.getText());
			}
		}
		return lst;
	}
	
	//Get Search Result
	public boolean getSearchResults(String search) {
		WebElement searchResultsTable = driver.findElement(table);
		List<WebElement> searchResultRows = searchResultsTable.findElements(rows);
		for (WebElement row : searchResultRows) {
			List<WebElement> cells = row.findElements(actionButton);
			for (WebElement cell : cells) {
				if (cell.getText().contains(search)) {
					return true;
				}
			}
		}
		return false;
	}
	
	//Navigate to Add Collection Page
	public boolean navigateToAddCollecton(String search) {
		WebElement searchResultsTable = driver.findElement(table);
		List<WebElement> searchResultRows = searchResultsTable.findElements(rows);
		for (WebElement row : searchResultRows) {
			List<WebElement> cells = row.findElements(actionButton);
			for (WebElement cell : cells) {
				if (cell.getText().equals(search)) {
					cell.click();
					return true;
				}
			}
		}
		return false;
	}
}