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
import com.leapdev.pages.BookStorePage;
import com.leapdev.utils.ReadCSV;

public class BookStoreTest extends BaseUITest{
	private static final Logger logger = LogManager.getLogger(BookStoreTest.class);
	
	@Test
	public void verifyThatTheUserIsAbleToSearchForBooksUsingKeywords() throws IOException {
		WebDriver driver = getDriver();
		Map<String, String> map = ReadCSV.readDataByRow("login.csv", "TC01");
		loginToApplication(driver, map);
		
		BookStorePage bookStore = new BookStorePage(driver);
		bookStore.waitVisibilityBookStoreMenu();
		bookStore.clickBookStoreMenu();
		
		Assert.assertEquals(bookStore.getBookStoreHeader(),"Book Store");
		
		String search = "et";
		logger.info("User entered following search criteria" + search);
		//Enter the search criteria
		bookStore.enterSearch(search);
		
		//Wait for table to load the results
		bookStore.waitTableToLoad();
		
		boolean found = bookStore.getSearchResults("Git Pocket Guide");
		logger.info("Search found: " + found);
		Assert.assertTrue(found);
	}
	
	@Test
	public void verifyThatUserAddedBookToTheCollection() throws IOException {
		WebDriver driver = getDriver();
		Map<String, String> map = ReadCSV.readDataByRow("login.csv", "TC01");
		loginToApplication(driver, map);
		
		BookStorePage bookStore = new BookStorePage(driver);
		bookStore.waitVisibilityBookStoreMenu();
		bookStore.clickBookStoreMenu();
		
		Assert.assertEquals(bookStore.getBookStoreHeader(),"Book Store");
		
		String search = "Git Pocket Guide";
		logger.info("User entered following search criteria" + search);
		//Enter the search criteria
		bookStore.enterSearch(search);
		
		//Wait for table to load the results
		bookStore.waitTableToLoad();
		
		boolean found = bookStore.navigateToAddCollecton(search);
		logger.info("User able to navigate to add collection" + found);
		if(!found)
		{
			Assert.assertTrue(false,"No Search Found");
		}
		else
		{
			bookStore.waitVisibilityAddNewButton();
			String message = bookStore.acceptConfirmation();
			logger.info("Add to Collection Message" + message);
			Assert.assertEquals(message, Constant.BOOK_ADDED_TO_COLLECTION);
		}
	}
}
