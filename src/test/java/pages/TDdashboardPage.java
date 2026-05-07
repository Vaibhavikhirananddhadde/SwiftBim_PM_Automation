package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BaseClass;

public class TDdashboardPage extends BaseClass{
	
	public TDdashboardPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//span[text()='Log Out']")
	WebElement btn_Logout;
	
	@FindBy(xpath="//button[text()='Logout']")
	WebElement confirm_logout;

	@FindBy(xpath = "//span[text()='Consultant']")
	WebElement nav_consultant;
	
	@FindBy(xpath="//button[text()='Cancel']")
	WebElement btn_cancel;
	
	public WebElement getBtn_Logout() {
		return btn_Logout;
	}

	public void setBtn_Logout(WebElement btn_Logout) {
		this.btn_Logout = btn_Logout;
	}
	
	public void logout() {
		btn_Logout.click();
		confirm_logout.click();
	}

	public WebElement getNav_consultant() {
		return nav_consultant;
	}

	public void setNav_consultant(WebElement nav_consultant) {
		this.nav_consultant = nav_consultant;
	}

	public WebElement getBtn_cancel() {
		return btn_cancel;
	}

	public void setBtn_cancel(WebElement btn_cancel) {
		this.btn_cancel = btn_cancel;
	}
	
	
	

}
