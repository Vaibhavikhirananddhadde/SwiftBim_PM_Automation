package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.BaseClass;

public class ConsultantPage extends BaseClass{
	
	public ConsultantPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="(//button[text()='Add Consultant'])[2]")
	WebElement btn_addConsultant;
	
	@FindBy(xpath="//button[normalize-space()='Invite']")
	WebElement btn_invite;
	
	@FindBy(xpath = "//button[normalize-space()='Manage Inactive']")
	WebElement btn_ManageInactive;
	
	
	@FindBy(xpath="//button[normalize-space()='Update Status']")
	WebElement btn_updateStatus;

	public WebElement getBtn_addConsultant() {
		return btn_addConsultant;
	}

	public void setBtn_addConsultant(WebElement btn_addConsultant) {
		this.btn_addConsultant = btn_addConsultant;
	}

	public WebElement getBtn_invite() {
		return btn_invite;
	}

	public void setBtn_invite(WebElement btn_invite) {
		this.btn_invite = btn_invite;
	}

	public WebElement getBtn_ManageInactive() {
		return btn_ManageInactive;
	}

	public void setBtn_ManageInactive(WebElement btn_ManageInactive) {
		this.btn_ManageInactive = btn_ManageInactive;
	}
	
	public void selectCheckbox(String empName, String empId) {
		
		By employeeCheckbox = By.xpath(
			    "//span[contains(.,'" + empName + "') and contains(.,'" + empId + "')]"
			  + "/ancestor::div[contains(@class,'flex')][1]"
			  + "//div[contains(@class,'border-2')]"
			);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement checkbox = wait.until(
			    ExpectedConditions.elementToBeClickable(employeeCheckbox)
			);

		checkbox.click();
	}
	
	public void checkInactiveStatus(String empName) {
		By inactiveStatus = By.xpath(
			    "//span[contains(.,'" + empName + "')]"
			  + "/ancestor::div[contains(@class,'justify-between')]"
			  + "//span[contains(text(),'Currently In-Active')]"
			);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement status = wait.until(
			    ExpectedConditions.elementToBeClickable(inactiveStatus)
			);
		
		Assert.assertTrue(status.isDisplayed(), "Status inactive is not displayed.");
	}

	public WebElement getBtn_updateStatus() {
		return btn_updateStatus;
	}

	public void setBtn_updateStatus(WebElement btn_updateStatus) {
		this.btn_updateStatus = btn_updateStatus;
	}
	
	
	
	
	
	
	
	
	

}
