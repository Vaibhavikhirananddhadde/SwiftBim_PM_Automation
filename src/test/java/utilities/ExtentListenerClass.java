package utilities;

import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import base.BaseClass;

public class ExtentListenerClass extends BaseClass implements ITestListener{
	

	
	 // ✅ Own the report here
	  private static ExtentReports extent;
	  private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	
	
	@BeforeSuite
	public void onStart(ITestContext context)
	{
		//setup method call
		//configureReport();
//		extent = SetupExtentReport.setupExtentReport();
//		System.out.println("On Start method invoked..... ");
		
		 // Create Reports dir if not exists
        new File(System.getProperty("user.dir") + File.separator + "Reports").mkdirs();
        new File(System.getProperty("user.dir") + File.separator + "Screenshots").mkdirs();

        // Single source of truth: create extent here only
        extent = SetupExtentReport.setupExtentReport();
        System.out.println("Extent report initialized.");
	}
	
	//When the test get started this method is invoked
	
		public void onTestStart(ITestResult Result)
		{
			//before each test case
//			test = extent.createTest(Result.getName());
//			System.out.println("Name of test method started: "+Result.getName());
			
			 ExtentTest t = extent.createTest(Result.getMethod().getMethodName());
			    test.set(t);   
		}
		
	
	
	//When the test case get failed this method is called
	
	public void onTestFailure(ITestResult Result)
	{
//		System.out.println("Name of the method failed: "+Result.getName());
//		test = extent.createTest(Result.getName());//Create entry in html report
//		test.log(Status.FAIL, MarkupHelper.createLabel("Name of the failed test case is: "+Result.getName(), ExtentColor.RED));
//		test.log(Status.FAIL, "the testcase failed cause is: "+Result.getThrowable());
//		
//		TakesScreenshot ss = (TakesScreenshot) driver;
//		File src = ss.getScreenshotAs(OutputType.FILE);
//		
//		SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy hh-mm-ss");
//		//get current date
//		
//		String Actualdate = format.format(new Date());
//		
//		
//		String screenShotPath =System.getProperty("user.dir")+"\\Screenshots\\"+Result.getName()+Actualdate+".png";
//		
//		File screenshotFile = new File(screenShotPath);
//		
//		try {
//			FileUtils.copyFile(src, screenshotFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		if(screenshotFile.exists())
//		{
//			test.fail("Captured Screenshot is below: "+test.addScreenCaptureFromPath(screenShotPath));
//		}
		
		ExtentTest t = test.get();
	    t.log(Status.FAIL,
	        MarkupHelper.createLabel(Result.getMethod().getMethodName() + " FAILED", ExtentColor.RED));
	    if (Result.getThrowable() != null) t.fail(Result.getThrowable());

	    try {
	      String path = captureScreenshot(Result.getMethod().getMethodName());
	      t.addScreenCaptureFromPath(path);
	    } catch (Exception e) {
	      t.warning("Screenshot capture failed: " + e.getMessage());
	    }
	}
	
	public void onTestSkipped(ITestResult Result)
	{
//		System.out.println("Name of the test method skipped: "+Result.getName());
//		test = extent.createTest(Result.getName());
//		test.log(Status.SKIP, MarkupHelper.createLabel("Name of the skipped test case is: "+Result.getName(), ExtentColor.YELLOW));
		
		 ExtentTest t = test.get();
		    t.log(Status.SKIP,
		        MarkupHelper.createLabel(Result.getMethod().getMethodName() + " SKIPPED", ExtentColor.YELLOW));
		    if (Result.getThrowable() != null) t.skip(Result.getThrowable());
		
	}
	
	
	
	public void onTestSuccess(ITestResult Result)
	{
//		System.out.println("Name of the test method passed is: "+Result.getName());
//		test = extent.createTest(Result.getName());
//		test.log(Status.PASS, MarkupHelper.createLabel("Name Of the passed test case is: "+Result.getName(), ExtentColor.GREEN));
		
		test.get().log(Status.PASS,
		        MarkupHelper.createLabel(Result.getMethod().getMethodName() + " PASSED", ExtentColor.GREEN));
		 
		
	}
	
	
	
	public void onTestFailedButWithinSuccessPercentage(ITestResult Result)
	{
		
	}
	
	@AfterSuite
	public void onFinish(ITestContext Result)
	{
		//close extent
//		System.out.println("On Finish method invoked..........");
//		extent.flush();//It is mandatory to ensure information is written to the started report
		
		 if (extent != null) extent.flush();
	        System.out.println("Extent report flushed.");
	}
	
	private String captureScreenshot(String testName) throws IOException {
	    File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    String ts = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
	    String dest = System.getProperty("user.dir") + File.separator + "Screenshots"
	                + File.separator + testName + "_" + ts + ".png";
	    org.apache.commons.io.FileUtils.copyFile(src, new File(dest));
	    return dest;
	  }

}
