package com.leapdev.uitests;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.leapdev.BaseUITest;
import com.leapdev.pages.ProfilePage;
import com.leapdev.utils.ReadCSV;

public class ProfilePageTest extends BaseUITest{
	@Test
	public void deleteBookFromCollection() throws IOException {
		WebDriver driver = getDriver();
		Map<String, String> map = ReadCSV.readDataByRow("login.csv", "TC01");
		loginToApplication(driver, map);
		
		ProfilePage profilePage = new ProfilePage(driver);
		profilePage.waitVisibilityProfileMenu();
		profilePage.clickProfileMenu();
		
		Assert.assertEquals(profilePage.getProfileHeader(),"Profile");
		
		//Enter the search criteria
		profilePage.enterSearch("Git Pocket Guide");
		
		//Wait for table to load the results
		profilePage.waitTableToLoad();
		
		boolean deleted = profilePage.removeBookFromCollection("Git Pocket Guide");
		Assert.assertTrue(deleted);
	}
}
