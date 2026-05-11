package tests;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseClass;
import pages.AddNewConsultantPage;
import pages.ConsultantPage;
import pages.InviteFormPage;
import pages.LoginPage;
import pages.TDdashboardPage;
import utilities.FakeDataGenerator;

public class ConsultantTests extends BaseClass{
	LoginPage login;
	TDdashboardPage dashboard;
	ConsultantPage consultant;
	AddNewConsultantPage addConsultant;
	InviteFormPage invite;
	
	
 //Verify that technical director can add new consultant.
	@Test
	public void addNewConsultant() throws Throwable {
		LogManager.getLogger("---------Starting Add new consultant test---------------");
		login = new LoginPage();
		dashboard = new TDdashboardPage();
		consultant = new ConsultantPage();
		addConsultant = new AddNewConsultantPage();
		
		LogManager.getLogger("Logging in as technical director");
		login.login("nagendra@swifterz.co", "swift@123");
		
		LogManager.getLogger("Clicking on consultant nav link");
		dashboard.getNav_consultant().click();
		
		LogManager.getLogger("Clicking on Add consultant button");
		consultant.getBtn_addConsultant().click();
		
		//FakeData
		String fullName = FakeDataGenerator.getFullName();
		String phoneNumber = FakeDataGenerator.getPhoneNumber();
		String address = FakeDataGenerator.getCity();
		String email = FakeDataGenerator.getEmail();
		
		LogManager.getLogger("Filling Add new consultant form");
		addConsultant.getTxt_FullName().sendKeys(fullName);
		addConsultant.getTxt_PhoneNumber().sendKeys(phoneNumber);
		addConsultant.getTxt_password().sendKeys("Test@123");
		addConsultant.getDate_DOB().sendKeys("14-12-2002");
		addConsultant.getTxt_email().sendKeys(email);
		addConsultant.selectType("Employee");
		addConsultant.getDate_DOJ().sendKeys("08-05-2026");
		
		addConsultant.selectRole("Bim Lead");
		scrolltoview(addConsultant.getTxt_Address());
		addConsultant.getFile_uploadProfile().sendKeys("C:\\Users\\Dell\\OneDrive\\Pictures\\Screenshots\\Girl Profile.png");
		addConsultant.getTxt_Address().sendKeys(address);

		
		
		scrolltoview(addConsultant.getBtn_Submit());
		LogManager.getLogger("Submitting the form");
		addConsultant.getBtn_Submit().click();
		
		//Verify
		String ActualConsultantName =addConsultant.getNewConsultant_name().getText();
		
		Assert.assertEquals(ActualConsultantName, fullName, "New consultant is not getting added!");
		LogManager.getLogger("--------- Add new consultant test Ended---------------");
		
	}
	
	//Check that proper error message is displayed when same mail id is used to create multiple consultants.
	@Test
	public void addConsultantExistingMail() throws Throwable {
		LogManager.getLogger("---------Starting Add new consultant test---------------");
		login = new LoginPage();
		dashboard = new TDdashboardPage();
		consultant = new ConsultantPage();
		addConsultant = new AddNewConsultantPage();
		
		LogManager.getLogger("Logging in as technical director");
		login.login("nagendra@swifterz.co", "swift@123");
		
		LogManager.getLogger("Clicking on consultant nav link");
		dashboard.getNav_consultant().click();
		
		LogManager.getLogger("Clicking on Add consultant button");
		consultant.getBtn_addConsultant().click();
		
		//FakeData
		String fullName = FakeDataGenerator.getFullName();
		String phoneNumber = FakeDataGenerator.getPhoneNumber();
		
		LogManager.getLogger("Filling Add new consultant form");
		addConsultant.getTxt_FullName().sendKeys(fullName);
		addConsultant.getTxt_PhoneNumber().sendKeys(phoneNumber);
		addConsultant.getTxt_password().sendKeys("Test@123");
		addConsultant.getDate_DOB().sendKeys("14-12-2002");
		addConsultant.getTxt_email().sendKeys("ed.goyette@gmail.com");

	
		scrolltoview(addConsultant.getBtn_Submit());
		LogManager.getLogger("Submitting the form");
		addConsultant.getBtn_Submit().click();
		
	    Assert.assertTrue(addConsultant.getError_emailExists().isDisplayed());
	
	}
	
	//Verify that user can invite consultants through mail.
	@Test
	public void invite() throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LogManager.getLogger("---------Starting Invite consultant test---------------");
		login = new LoginPage();
		dashboard = new TDdashboardPage();
		consultant = new ConsultantPage();
		invite = new InviteFormPage();
		
		LogManager.getLogger("Logging in as technical director");
		login.login("nagendra@swifterz.co", "swift@123");
		
		LogManager.getLogger("Clicking on consultant nav link");
		dashboard.getNav_consultant().click();
		consultant.getBtn_invite().click();
		
		//Fake Data
		String email = FakeDataGenerator.getEmail();
		
		LogManager.getLogger("Entering mail id and welcome message clicked on invite button");
		invite.getTxt_email().sendKeys(email);
		invite.getTxt_message().sendKeys("Welcome To SwiftBIM!");
		invite.getBtn_invite().click();
		

		boolean isSendingDisplayed = wait.until(
		        ExpectedConditions.textToBe(
		                By.xpath("//button[@type='submit']"),
		                "Sending..."
		        )
		);
		
		// Assertion
		Assert.assertTrue(isSendingDisplayed, "Button text did not change to Sending...");	
		LogManager.getLogger("---------Ending Invite consultant test---------------");
	}
	
	@Test
	public void manageInactive() throws InterruptedException {
		LogManager.getLogger("---------Starting Manage Inactive test---------------");
		login = new LoginPage();
		dashboard = new TDdashboardPage();
		consultant = new ConsultantPage();
		
		LogManager.getLogger("Logging in as technical director");
		login.login("nagendra@swifterz.co", "swift@123");
		
		LogManager.getLogger("Clicking on Manage Inactive button.");
		dashboard.getNav_consultant().click();
		
		consultant.getBtn_ManageInactive().click();
		
		consultant.selectCheckbox("vaibhavi dhadde","EMP-0359");
		consultant.getBtn_updateStatus().click();
		consultant.checkInactiveStatus("vaibhavi dhadde");
		
	}

}
