package utilities;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.google.common.collect.ImmutableMap;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UtilClass {
	
public static WebDriver driver;
	
	public static String readProperty(String key, String path) throws Exception {
		String projectPath = System.getProperty("user.dir");
		File file = new File(projectPath + path);
		FileInputStream fileInput = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(fileInput);
		return prop.get(key).toString();
	}
	
	public static void launchBrowser(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			// Remove headless mode for better stability
			// options.addArguments("--headless");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--disable-gpu");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-web-security");
			options.addArguments("--allow-running-insecure-content");
			options.addArguments("--disable-features=VizDisplayCompositor");
			options.setCapability("pageLoadStrategy", "normal");
			options.setCapability("timeouts", ImmutableMap.of("pageLoad", 120000));

			driver = new ChromeDriver(options);
		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			// Remove headless mode for better stability
			// options.addArguments("--headless");
			driver = new FirefoxDriver(options);
		} else if (browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else {
			System.out.println("Opening Chrome browser as Default browser");
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			driver = new ChromeDriver(options);
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
	}
	
	public String termsValidationMessage(WebElement checkbox) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    // reportValidity() forces the bubble to show (useful for visual confirmation)
	    js.executeScript("arguments[0].reportValidity();", checkbox);
	    return (String) js.executeScript("return arguments[0].validationMessage;", checkbox);
	}
	
	public String getValidationMessage(WebElement el) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    // Forces the native bubble to show (not needed for assertion, but helpful)
	    js.executeScript("arguments[0].reportValidity();", el);
	    return (String) js.executeScript("return arguments[0].validationMessage;", el);
	}
	
	public static Object[][] dataReader(String sheetName, String path) throws Exception {
		String excelPath = System.getProperty("user.dir");
		XSSFWorkbook workBook = new XSSFWorkbook(excelPath + path);
		XSSFSheet sheet = workBook.getSheet(sheetName);
		int row = sheet.getPhysicalNumberOfRows();
		int column = sheet.getRow(0).getPhysicalNumberOfCells();
		Object[][] data = new Object[row - 1][column];

		for (int i = 1; i < row; i++) {
			for (int j = 0; j < column; j++) {
				XSSFCell cell = sheet.getRow(i).getCell(j);
				if (cell != null) {
					if (cell.getCellType() == CellType.NUMERIC) {
						// Convert numeric value to string
						data[i - 1][j] = String.valueOf((int) cell.getNumericCellValue());
					} else {
						// For other cell types, get the string value
						data[i - 1][j] = cell.getStringCellValue();
					}
				} else {
					data[i - 1][j] = "";
				}
			}
		}
		workBook.close();
		return data;
	}
	
	public static void getApplication(String url) {
		driver.get(url);
	}
	
	public static void checkAlertpresent() {
		if (isAlertPresent()) {
			// Switch to the alert
			Alert alert = driver.switchTo().alert();

			// Perform actions with the alert if needed
			System.out.println("Alert text: " + alert.getText());

			// Accept the alert
			alert.accept();
		} else {
			System.out.println("No alert present on the webpage.");
		}
	}
	
	public static boolean isAlertPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}
	
	public static void scrolltoview(WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);

	}

	public static void waitExplicit(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(160));
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));

	}

	public static void waitUntillVisiblity(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(150));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void waitExplicitUntillTitle(String titleToWait) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs(titleToWait));
	}

	public static void waitImplicit() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
	}


	public static void type(WebElement element, String text) {
		element.sendKeys(text);
	}
	
	public static void safeClear(WebElement element) {
		safeClearWithRetry(element, 3);
	}
	
	public static void safeClearWithRetry(WebElement element, int maxRetries) {
		int attempts = 0;
		while (attempts < maxRetries) {
			try {
				// Wait for element to be visible and interactable
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				wait.until(ExpectedConditions.visibilityOf(element));
				wait.until(ExpectedConditions.elementToBeClickable(element));
				
				element.clear();
				return; // Success, exit the method
			} catch (Exception e) {
				attempts++;
				if (attempts >= maxRetries) {
					throw e;
				}
				try {
					// Try alternative clear method using JavaScript
					((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
					element.sendKeys(Keys.CONTROL + "a");
					element.sendKeys(Keys.DELETE);
					return;
				} catch (Exception jsException) {
					try {
						Thread.sleep(2000); // Wait longer before retry
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}
	}

	public static void clickOn(WebElement element) {
		clickOnWithRetry(element, 3);
	}
	
	public static void clickOnWithRetry(WebElement element, int maxRetries) {
		int attempts = 0;
		while (attempts < maxRetries) {
			try {
				waitExplicit(element);
				element.click();
				return; // Success, exit the method
			} catch (ElementClickInterceptedException e) {
				attempts++;
				if (attempts >= maxRetries) {
					throw e;
				}
				// Handle the exception, for example, scrolling into view and trying again
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
				try {
					Thread.sleep(1000); // Wait before retry
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
				}
			} catch (Exception e) {
				attempts++;
				if (attempts >= maxRetries) {
					throw e;
				}
				try {
					Thread.sleep(1000); // Wait before retry
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	public static String getPageTitle() {
		waitExplicitUntillTitle(driver.getTitle());
		return driver.getTitle();
	}

	public static String extractText(WebElement element) {
		waitExplicit(element);
		return element.getText();
	}


	public static void selectFromDropDown(WebElement element, String visibleText) {
		waitExplicit(element);
		Select select = new Select(element);
		select.selectByVisibleText(visibleText);
	}

	public static void titleAssertion(String expTitle) {
		Assert.assertEquals(getPageTitle(), expTitle);
	}

	public static void textAssertion(WebElement element, String expectedText) {
		Assert.assertEquals(extractText(element), expectedText);

	}
	public static void smartScrollTo(WebElement el) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // 1) Bring it to the center of the viewport (more reliable than default)
	    js.executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", el);

	    // 2) If a fixed header exists, nudge up by its height
	    Long headerH = (Long) js.executeScript(
	        "var h=document.querySelector('header, .navbar, .sticky-top, [class*=\"header\" i]');" +
	        "return (h && getComputedStyle(h).position==='fixed') ? h.offsetHeight : 0;"
	    );
	    if (headerH != null && headerH > 0) {
	        js.executeScript("window.scrollBy(0, -arguments[0]-12);", headerH); // extra 12px padding
	    }

	    // 3) As a last nudge, move mouse to it (helps some sites with lazy load/hover)
	    try { new org.openqa.selenium.interactions.Actions(driver).moveToElement(el).perform(); } catch (Exception ignore) {}
	}

	public static boolean isInViewport(WebElement el) {
	    return (Boolean) ((JavascriptExecutor) driver).executeScript(
	        "var r=arguments[0].getBoundingClientRect();" +
	        "return r.top>=0 && r.left>=0 && r.bottom<=(window.innerHeight||document.documentElement.clientHeight)" +
	        " && r.right<=(window.innerWidth||document.documentElement.clientWidth);", el);
	}
	
	public static void jsScrollUntillElement(WebElement element) {
		waitExplicit(element);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static void scrollDown() throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,8000)", "");
		Thread.sleep(3000);
	}

	public static void jsClickOn(WebElement element) {
		waitExplicit(element);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public static void softAssert(String actResult, String expResult) {
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(actResult, expResult);
	}

	public static String getScreenshot(String testCaseName) throws Exception, IOException {
		String time = getTime();
		String path = System.getProperty("user.dir") + "/screenshot/" + testCaseName + time + ".png";
		FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(path));
		return path;
	}

	public static String getParentWindowId() {
		return driver.getWindowHandle();
	}

	public static void switchToChildWindow(String parentWindowId) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String windows : allWindows) {
			if (windows != parentWindowId) {
				driver.switchTo().window(windows);
			}
		}
	}

	public static void windowhandle(WebElement element) {

		String mainWindowHandle = driver.getWindowHandle();

		// Click on an element that opens the new window
		element.click();

		// Switch to the new window
		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String windowHandle : allWindowHandles) {
			if (!windowHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(windowHandle);
				// Perform operations in the new window
				// ...
				// Close or switch back to the main window when done
				// driver.close(); // Close the current window
				// driver.switchTo().window(mainWindowHandle); // Switch back to the main window
			}
		}
	}

	public static String getTime() {
		DateFormat dateFormat = null;
		Date date = null;
		try {
			dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			date = new Date();
		} catch (Exception e) {
			System.out.println("Error in Getdateandtime : " + e.getMessage());
		}

		return dateFormat.format(date);
	}

	public static void switchToParentWindow(String parentWindowId) {
		driver.switchTo().window(parentWindowId);
	}

	public static void handleAlert() {
		driver.switchTo().alert().accept();
	}

	public void switchToFrame(WebElement elemnt) {
		driver.switchTo().frame(elemnt);
	}

	public void toDefaultContent() {
		driver.switchTo().defaultContent();
	}

	public boolean isElementDisplayed(WebElement element) {
		return element.isDisplayed();
	}

//	public void flWait() {
//		FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(10))
//				.pollingEvery(Duration.ofMillis(200)).ignoring(Exception.class);
//	}
//
//	public void checkIfPagesReachesTop() {
//		JavascriptExecutor executor = (JavascriptExecutor) driver;
//		Long value = (Long) executor.executeScript("return window.pageYOffset;");
//	}

	public static void jsScrollDownUntilElementVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		while (true) {
			try {
				if (wait.until(ExpectedConditions.elementToBeClickable(element)).isDisplayed()) {
					element.click();
					break;
				} else {
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				}
			} catch (StaleElementReferenceException | TimeoutException e) {

			}
		}

	}

	public void clickButtonAndWaitForDownload(String buttonxpath, int timeoutInSeconds) {
		WebElement downloadButton = driver.findElement(By.xpath(buttonxpath));
		downloadButton.click();
		waitForDownload(timeoutInSeconds);
	}

	public boolean isPdfDownloaded(String fileName, String downloadPath) {
		String filePath = downloadPath + "/" + fileName;
		return isFileDownloaded(filePath);
	}

	private void waitForDownload(int timeoutInSeconds) {
		// You can use WebDriverWait here for a more robust solution
		try {
			Thread.sleep(timeoutInSeconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean isFileDownloaded(String filePath) {
		// Implement logic to check if the file exists in the specified path
		// You can use java.nio.file.Path and java.nio.file.Files to check file
		// existence
		// Return true if the file is found, false otherwise
		// For example:
		return Files.exists(Paths.get(filePath));

	}

	public boolean isFooterSticky() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		// Check if footer is sticky
		WebElement footer = driver.findElement(By.id("footer"));
		return (boolean) js.executeScript("var rect = arguments[0].getBoundingClientRect();"
				+ "return (rect.bottom <= window.innerHeight && rect.bottom > 0);", footer);
	}

	public List<WebElement> handleDropdown(WebElement container, String path) throws Exception {
		List<WebElement> DDelements = new ArrayList<>();
		List<WebElement> containerlements = new ArrayList<>();
		containerlements = container.findElements(By.cssSelector(path));
		for (int i = 0; i < containerlements.size(); i++) {
			DDelements.add(containerlements.get(i));

		}
		return DDelements;
	}

	public void childBrowser(WebDriver driver) {
		Set<String> child = driver.getWindowHandles();
		for (String b : child) {
			driver.switchTo().window(b);
		}
	}

	public void mouseOvering(WebDriver driver, WebElement ele) {
		Actions a = new Actions(driver);
		a.moveToElement(ele).perform();
	}

	/*
	public Void ddWithoutSelect(WebElement optionsContainer) {
		for(int i=0; i<optionsContainer.size(); i++)
		{
			if(optionsContainer.get(i).getText().contains("Jiffy Digital Office"))
			{
				optionsContainer.get(i).click();
				break;
			}
		}
	}
	*/
	
	 public static int getRowCount(String path, String sheetName) {
		 String filePath = System.getProperty("user.dir")+path;
	        try {
	        	FileInputStream fis = new FileInputStream(filePath);
	        	XSSFWorkbook workbook = new XSSFWorkbook(fis);
	        	XSSFSheet excelSheet = workbook.getSheet(sheetName);
	            int ttlRows = excelSheet.getLastRowNum() + 1;
	            workbook.close();
	            fis.close();
	            return ttlRows;
	        } catch (Exception e) {
	            return 0;
	        }
	    }
	 
	 public static String getCellValue(String path, String sheetName, int rowNo, int cellNo) {
		 String filePath = System.getProperty("user.dir")+path;
	        try {
	        	FileInputStream fis = new FileInputStream(filePath);
	        	XSSFWorkbook workbook = new XSSFWorkbook(fis);
	        	XSSFSheet excelSheet = workbook.getSheet(sheetName);
	            XSSFCell excelCell = excelSheet.getRow(rowNo).getCell(cellNo);
	            String cellValue = "";
	            

	            if (excelCell != null) {
	                if (excelCell.getCellType() == CellType.NUMERIC) {
	                    cellValue = String.valueOf((int) excelCell.getNumericCellValue());
	                } else {
	                    cellValue = excelCell.toString();
	                }
	            }

	            workbook.close();
	            fis.close();

	            return cellValue;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "";
	        }

	    }
	 
	  public static int getColCount(String fileName, String sheetName) {
	        try {
	        	FileInputStream fis = new FileInputStream(fileName);
	        	XSSFWorkbook workbook = new XSSFWorkbook(fis);
	        	XSSFSheet excelSheet = workbook.getSheet(sheetName);
	            int ttlCells = excelSheet.getRow(0).getLastCellNum();
	            workbook.close();
	            fis.close();
	            return ttlCells;
	        } catch (Exception e) {
	            return 0;
	        }
	    }
	  
	  public static String[] getDataFromSingleCol(String path, String sheetname, int cellno)
	  {
		  String filePath = System.getProperty("user.dir")+path;
		  int rownum = getRowCount(filePath, sheetname);
		  String data[] = new String[rownum];
		  
		  for(int i=1; i<=rownum; i++)
		  {
			  data[i-1] = getCellValue(path, sheetname, i, cellno);
		  }
		return data;
	  }
	  
	  public static void fileuploadRobot(String filePath) throws AWTException
	  {
		  Robot robot = new Robot();
		  StringSelection fileSelection = new StringSelection(filePath);
          Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);
          // Paste the file path
          robot.keyPress(KeyEvent.VK_CONTROL);
          robot.keyPress(KeyEvent.VK_V);
          robot.keyRelease(KeyEvent.VK_V);
          robot.keyRelease(KeyEvent.VK_CONTROL);

          // Press "Enter" to confirm the upload
          robot.keyPress(KeyEvent.VK_ENTER);
          robot.keyRelease(KeyEvent.VK_ENTER);
           
          
	  }
	  
	  public static void handleDropdownWithoutSelect(List<WebElement> options, String value)
	  {
		  for(WebElement option: options)
		  {
			  if(option.getText().equals(value))
			  {
				  option.click();
				  break;
			  }
		  }
	  }
	  
	  public void captureScreenShot(WebDriver driver, String testName) throws IOException
		{
			//step1: convert webdriver object to TakesScreenshot interface
			TakesScreenshot screenshot = (TakesScreenshot)driver;
			
			//step2: call getScreenshotAs method to create image file
			
			File src = screenshot.getScreenshotAs(OutputType.FILE);
			
			File dest = new File(System.getProperty("user.dir"+"//Screenshots//"+ testName+".png"));
			
			//step3 : copy image from src file to dest file
			FileUtils.copyFile(src,dest);
		}
	  
	  public static void closeChildAndReturn(String parentHandle) {
		    try {
		        if (!driver.getWindowHandle().equals(parentHandle)) {
		            driver.close();
		            driver.switchTo().window(parentHandle);
		        }
		    } catch (org.openqa.selenium.NoSuchWindowException e) {
		        // If current is already closed, just try to switch to parent if present
		        for (String h : driver.getWindowHandles()) {
		            if (h.equals(parentHandle)) {
		                driver.switchTo().window(parentHandle);
		                break;
		            }
		        }
		    }
	  
	  }
	  
	  public void selectFromReactDropdown(By optionsLocator, String valueToSelect) {
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    int attempts = 0;

		    while (attempts < 3) {
		        try {
		            // Wait until all options are visible
		            List<WebElement> options = wait.until(
		                    ExpectedConditions.visibilityOfAllElementsLocatedBy(optionsLocator)
		            );

		            for (WebElement option : options) {
		                String optionText = option.getText().trim();
		                System.out.println("Option: " + optionText);

		                if (optionText.equalsIgnoreCase(valueToSelect)) {
		                    option.click();
		                    return; // ✅ done, exit method
		                }
		            }

		            // If we reached here, option not found
		            throw new RuntimeException("Value '" + valueToSelect + "' not found in dropdown");

		        } catch (StaleElementReferenceException e) {
		            System.out.println("StaleElementReferenceException caught. Retrying... Attempt: " + (attempts + 1));
		            attempts++;
		        }
		    }

		    throw new RuntimeException("Failed to select '" + valueToSelect + "' due to repeated stale elements.");
		}

}
