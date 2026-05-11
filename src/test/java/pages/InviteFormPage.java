package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BaseClass;

public class InviteFormPage extends BaseClass{
	
	public InviteFormPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//textarea[@placeholder='Enter Multiple Email addresses separated by commas,']")
	WebElement txt_email;
	
	@FindBy(xpath="//textarea[@placeholder='Enter your Invitation Message.,']")
	WebElement txt_message;
	
	@FindBy(xpath="//button[normalize-space()='Invite']")
	WebElement btn_invite;

	public WebElement getTxt_email() {
		return txt_email;
	}

	public void setTxt_email(WebElement txt_email) {
		this.txt_email = txt_email;
	}

	public WebElement getTxt_message() {
		return txt_message;
	}

	public void setTxt_message(WebElement txt_message) {
		this.txt_message = txt_message;
	}

	public WebElement getBtn_invite() {
		return btn_invite;
	}

	public void setBtn_invite(WebElement btn_invite) {
		this.btn_invite = btn_invite;
	}
	
	

}
