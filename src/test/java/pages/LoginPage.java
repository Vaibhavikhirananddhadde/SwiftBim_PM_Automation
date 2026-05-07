package pages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BaseClass;

public class LoginPage extends BaseClass{
	
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//input[@id='email']")
	WebElement txt_email;
	
	@FindBy(xpath="//input[@id='password']")
	WebElement txt_password;
	
	@FindBy(xpath="//button[normalize-space()='Forgot Password']")
	WebElement lnk_forgotPassword;
	
	@FindBy(xpath="//button[normalize-space()='Login']")
	WebElement btn_Login;
	
	@FindBy(xpath="(//button[@aria-label='Show password']//*[name()='svg']//*[name()='path'])[2]")
	WebElement icon_eye;
	
	@FindBy(xpath="//div[@class='mb-4 p-3 rounded-lg bg-rose-50 text-rose-600 text-sm border border-rose-100']")
	WebElement errorMsg;

	public WebElement getTxt_email() {
		return txt_email;
	}

	public void setTxt_email(WebElement txt_email) {
		this.txt_email = txt_email;
	}

	public WebElement getTxt_password() {
		return txt_password;
	}

	public void setTxt_password(WebElement txt_password) {
		this.txt_password = txt_password;
	}

	public WebElement getLnk_forgotPassword() {
		return lnk_forgotPassword;
	}

	public void setLnk_forgotPassword(WebElement lnk_forgotPassword) {
		this.lnk_forgotPassword = lnk_forgotPassword;
	}

	public WebElement getBtn_Login() {
		return btn_Login;
	}

	public void setBtn_Login(WebElement btn_Login) {
		this.btn_Login = btn_Login;
	}

	public WebElement getIcon_eye() {
		return icon_eye;
	}

	public void setIcon_eye(WebElement icon_eye) {
		this.icon_eye = icon_eye;
	}
	
	
	
	public WebElement getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(WebElement errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void login(String email, String password) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(txt_email));
		txt_email.clear();
		txt_email.sendKeys(email);
		wait.until(ExpectedConditions.visibilityOf(txt_password));
		txt_password.clear();
		txt_password.sendKeys(password);
		wait.until(ExpectedConditions.visibilityOf(btn_Login));
		btn_Login.click();
	}

}
