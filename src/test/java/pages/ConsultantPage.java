package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.BaseClass;

public class ConsultantPage extends BaseClass{
	
	@FindBy(xpath="(//button[text()='Add Consultant'])[2]")
	WebElement btn_addConsultant;
	
	

}
