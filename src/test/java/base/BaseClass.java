package base;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utilities.UtilClass;

public class BaseClass extends UtilClass{
	
	private static final Logger logger = LogManager.getLogger(BaseClass.class);
	 public static ExtentTest test;
		public static ExtentReports extent;
		
		
		 
		 @BeforeClass
		    public void setup() {
		        // Initialize the ExtentReports object
//		        extent =  SetupExtentReport.setupExtentReport();
		    }
		 
	 
	@BeforeMethod
	public void setUp() throws Exception {
        
		launchBrowser(readProperty("browser", "/src/test/resources/configfiles/config.properties"));
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
		
		logger.info("launching browser");
		getApplication(readProperty("url", "/src/test/resources/configfiles/config.properties"));
		logger.info("Entering url in the browser");
	
		}
	
	@AfterMethod
	public void tearDown() {
	    try {
	        if (driver != null) {
	            // Try to close gracefully first
	            try {
	                driver.close();
	            } catch (Exception e) {
	                logger.warn("Error closing browser: " + e.getMessage());
	            }
	            
	            // Then quit to end the session
	            driver.quit();
	            logger.info("Browser quit successfully.");
	        }
	    } catch (Exception e) {
	        logger.warn("Browser already gone: " + e.getMessage());
	    } finally {
	        driver = null; // Ensure driver is nullified
	    }
	}

	 
}