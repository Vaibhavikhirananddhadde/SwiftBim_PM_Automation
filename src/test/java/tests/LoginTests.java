package tests;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseClass;
import pages.LoginPage;
import pages.TDdashboardPage;

public class LoginTests extends BaseClass{
	
	LoginPage login;
	TDdashboardPage dashboard;
	
	
	//Verify that user can login successfully with valid credentials.
	@Test
	public void loginSuccessful() throws Exception {
		LogManager.getLogger("----------------Starting Login tests------------------");
		login = new LoginPage();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LogManager.getLogger("Entering valid mail id");
		WebElement Email=login.getTxt_email();
		wait.until(ExpectedConditions.visibilityOf(Email));
		Email.sendKeys("nagendra@swifterz.co");
		
		LogManager.getLogger("Entering valid password");
		WebElement Password=login.getTxt_password();
		wait.until(ExpectedConditions.visibilityOf(Password));
		Password.sendKeys("swift@123");
		
		LogManager.getLogger("Clicking on login button");
		wait.until(ExpectedConditions.visibilityOf(login.getBtn_Login()));
		login.getBtn_Login().click();
		Thread.sleep(2000);
		
		String ExpectedURL = "http://localhost:5173/td/dashboard";
		
		Assert.assertEquals(driver.getCurrentUrl(), ExpectedURL, "Dashboard URL does not match!");
		LogManager.getLogger("-------------Login Tests ends----------------------");
	}
	
	//Verify that eye toggle is displayed for password textfield.
		@Test
		public void login() throws Exception {
			LogManager.getLogger("----------------Starting Login tests------------------");
			login = new LoginPage();
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			LogManager.getLogger("Entering valid mail id");
			WebElement Email=login.getTxt_email();
			wait.until(ExpectedConditions.visibilityOf(Email));
			Email.sendKeys("nagendra@swifterz.co");
			
			LogManager.getLogger("Entering valid password");
			WebElement Password=login.getTxt_password();
			wait.until(ExpectedConditions.visibilityOf(Password));
			Password.sendKeys("swift@123");
			
			Assert.assertTrue(login.getIcon_eye().isDisplayed(), "Eye toggle is not displayed for password textfield!");
			LogManager.getLogger("-------------Login Tests ends----------------------");
		}
		
		//Verify error message when valid mail id and invalid password is entered.
		@Test
		public void ValidMailInvalidPassword() throws Exception {
			LogManager.getLogger("----------------Starting Login tests------------------");
			login = new LoginPage();
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			LogManager.getLogger("Entering valid mail id");
			WebElement Email=login.getTxt_email();
			wait.until(ExpectedConditions.visibilityOf(Email));
			Email.sendKeys("nagendra@swifterz.co");
			
			LogManager.getLogger("Entering invalid password");
			WebElement Password=login.getTxt_password();
			wait.until(ExpectedConditions.visibilityOf(Password));
			Password.sendKeys("123456");
			
			LogManager.getLogger("Clicking on login button");
			wait.until(ExpectedConditions.visibilityOf(login.getBtn_Login()));
			login.getBtn_Login().click();
			Thread.sleep(2000);
			
	        Assert.assertTrue(login.getErrorMsg().isDisplayed(), "Error message is not displayed!");
			LogManager.getLogger("-------------Login Tests ends----------------------");
		}
	
		//Verify error message when invalid mail id and valid password is entered.
		@Test
		public void InvalidMailValidPassword() throws Exception {
			LogManager.getLogger("----------------Starting Login tests------------------");
			login = new LoginPage();
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			LogManager.getLogger("Entering invalid mail id");
			WebElement Email=login.getTxt_email();
			wait.until(ExpectedConditions.visibilityOf(Email));
			Email.sendKeys("test@swifterz.co");
			
			LogManager.getLogger("Entering valid password");
			WebElement Password=login.getTxt_password();
			wait.until(ExpectedConditions.visibilityOf(Password));
			Password.sendKeys("swift@123");
			
			LogManager.getLogger("Clicking on login button");
			wait.until(ExpectedConditions.visibilityOf(login.getBtn_Login()));
			login.getBtn_Login().click();
			Thread.sleep(2000);
			
	        Assert.assertTrue(login.getErrorMsg().isDisplayed(), "Error message is not displayed!");
			LogManager.getLogger("-------------Login Tests ends----------------------");
		}
		
		//Verify error message when invalid mail id and invalid password is entered.
		@Test
		public void InvalidMailInvalidPassword() throws Exception {
			LogManager.getLogger("----------------Starting Login tests------------------");
			login = new LoginPage();
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			LogManager.getLogger("Entering invalid mail id");
			WebElement Email=login.getTxt_email();
			wait.until(ExpectedConditions.visibilityOf(Email));
			Email.sendKeys("test@swifterz.co");
			
			LogManager.getLogger("Entering invalid password");
			WebElement Password=login.getTxt_password();
			wait.until(ExpectedConditions.visibilityOf(Password));
			Password.sendKeys("123456");
			
			LogManager.getLogger("Clicking on login button");
			wait.until(ExpectedConditions.visibilityOf(login.getBtn_Login()));
			login.getBtn_Login().click();
			Thread.sleep(2000);
			
	        Assert.assertTrue(login.getErrorMsg().isDisplayed(), "Error message is not displayed!");
			LogManager.getLogger("-------------Login Tests ends----------------------");
		}
		
		//Verify that proper error message is displayed when fields are left blank.
		@Test
		public void emptyFields() throws Exception {
			LogManager.getLogger("----------------Starting Login tests------------------");
			login = new LoginPage();
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			LogManager.getLogger("Left the Email field blank");
			WebElement Email=login.getTxt_email();
			wait.until(ExpectedConditions.visibilityOf(Email));
			Email.clear();
			
			LogManager.getLogger("Left the password field empty");
			WebElement Password=login.getTxt_password();
			wait.until(ExpectedConditions.visibilityOf(Password));
			Password.clear();
			
			LogManager.getLogger("Clicking on login button");
			wait.until(ExpectedConditions.visibilityOf(login.getBtn_Login()));
			login.getBtn_Login().click();
			Thread.sleep(2000);
			
	        String ExpectedURL = "http://localhost:5173/login";
	        Assert.assertEquals(driver.getCurrentUrl(), ExpectedURL,"URL does not match");
			LogManager.getLogger("-------------Login Tests ended----------------------");
		}
		
		//Verify that user can logout successfully.
		@Test
		public void logout() {
			LogManager.getLogger("----------------Starting Logout tests------------------");
			login = new LoginPage();
			dashboard = new TDdashboardPage();
			
			login.login("nagendra@swifterz.co", "swift@123");
			dashboard.logout();
			
			String expectedURL = "http://localhost:5173/td/dashboard";
			Assert.assertEquals(driver.getCurrentUrl(), expectedURL, "URL does not match");
			LogManager.getLogger("---------------- Logout tests Ended------------------");
		}

		
}
