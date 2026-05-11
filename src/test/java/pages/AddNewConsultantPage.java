package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BaseClass;

public class AddNewConsultantPage extends BaseClass{
	
	public AddNewConsultantPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//input[@placeholder='Enter Employee Name']")
	WebElement txt_FullName;
	
	@FindBy(xpath="//input[@placeholder='Enter Phone Number']")
	WebElement txt_PhoneNumber;
	
	@FindBy(xpath="//input[@placeholder='Enter Password']")
	WebElement txt_password;
	
	@FindBy(xpath="(//div[@class='relative w-full']/button)[1]")
	WebElement dd_Role;
	
	@FindBy(xpath="//button[contains(text(),'Bim Consultant')]")
	WebElement opt_BC;
	
	@FindBy(xpath="//button[normalize-space()='Browse File']")
	WebElement file_uploadProfile;
	
	@FindBy(xpath="//textarea[@placeholder='Type your Address...']")
	WebElement txt_Address;
	
	@FindBy(xpath="((//div[@class='space-y-5'])[2]//input)[1]")
	WebElement date_DOB;
	
	@FindBy(xpath="//input[@placeholder='Enter Email']")
	WebElement txt_email;
	
	@FindBy(xpath="(//button[contains(@class,'w-full flex items-center justify-between px-4 py-2 bg-[#F2F3F4]')])[2]")
	WebElement dd_type;
	
	@FindBy(xpath="(//input[@type='date'])[2]")
	WebElement date_DOJ;
	
	@FindBy(xpath="//button[normalize-space()='Submit']")
	WebElement btn_Submit;
	
	@FindBy(xpath="(//div[@class='min-w-0']/h3)[1]")
	WebElement newConsultant_name;
	
	@FindBy(xpath="//p[text()='Email already exists']")
	WebElement error_emailExists;

	public WebElement getTxt_FullName() {
		return txt_FullName;
	}

	public void setTxt_FullName(WebElement txt_FullName) {
		this.txt_FullName = txt_FullName;
	}

	public WebElement getTxt_PhoneNumber() {
		return txt_PhoneNumber;
	}

	public void setTxt_PhoneNumber(WebElement txt_PhoneNumber) {
		this.txt_PhoneNumber = txt_PhoneNumber;
	}

	public WebElement getTxt_password() {
		return txt_password;
	}

	public void setTxt_password(WebElement txt_password) {
		this.txt_password = txt_password;
	}

	public WebElement getOpt_BC() {
		return opt_BC;
	}

	public void setOpt_BC(WebElement opt_BC) {
		this.opt_BC = opt_BC;
	}

	public WebElement getFile_uploadProfile() {
		return file_uploadProfile;
	}

	public void setFile_uploadProfile(WebElement file_uploadProfile) {
		this.file_uploadProfile = file_uploadProfile;
	}

	public WebElement getTxt_Address() {
		return txt_Address;
	}

	public void setTxt_Address(WebElement txt_Address) {
		this.txt_Address = txt_Address;
	}

	public WebElement getDate_DOB() {
		return date_DOB;
	}

	public void setDate_DOB(WebElement date_DOB) {
		this.date_DOB = date_DOB;
	}

	public WebElement getTxt_email() {
		return txt_email;
	}

	public void setTxt_email(WebElement txt_email) {
		this.txt_email = txt_email;
	}

	public WebElement getDd_type() {
		return dd_type;
	}

	public void setDd_type(WebElement dd_type) {
		this.dd_type = dd_type;
	}

	public WebElement getDate_DOJ() {
		return date_DOJ;
	}

	public void setDate_DOJ(WebElement date_DOJ) {
		this.date_DOJ = date_DOJ;
	}

	public WebElement getBtn_Submit() {
		return btn_Submit;
	}

	public void setBtn_Submit(WebElement btn_Submit) {
		this.btn_Submit = btn_Submit;
	}
	
	public WebElement getNewConsultant_name() {
		return newConsultant_name;
	}

	public void setNewConsultant_name(WebElement newConsultant_name) {
		this.newConsultant_name = newConsultant_name;
	}

	
	public WebElement getError_emailExists() {
		return error_emailExists;
	}

	public void setError_emailExists(WebElement error_emailExists) {
		this.error_emailExists = error_emailExists;
	}

	public void selectRole(String role) {

	    dd_Role.click();

	    WebElement option = driver.findElement(
	        By.xpath("//button[normalize-space()='" + role + "']")
	    );

	    option.click();
	}
	
	public void selectType(String Type) {
		dd_type.click();
		
		List<WebElement> typeOptions = driver.findElements(By.xpath("//div[@class='max-h-[220px] overflow-y-auto custom-scrollbar']/button"));
		for (int i = 0; i < typeOptions.size(); i++) {

	        // Re-fetch elements every iteration
	        typeOptions = driver.findElements(
	            By.xpath("//div[@class='max-h-[220px] overflow-y-auto custom-scrollbar']/button")
	        );

	        WebElement option = typeOptions.get(i);

	        if (option.getText().trim().equalsIgnoreCase(Type)) {

	            option.click();
	            break;
	        }
	    }
	}
	
	
	
	
	

}
