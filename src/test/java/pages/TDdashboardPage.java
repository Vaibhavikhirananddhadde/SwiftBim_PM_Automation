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

}
