package utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class SetupExtentReport {
	
public static ExtentReports setupExtentReport() {
		

	
	String base = System.getProperty("user.dir") + File.separator + "Reports";
    new File(base).mkdirs();
    String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
    String reportPath = base + File.separator + "ExtentReport_" + timestamp + ".html";

    ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
    ExtentReports extent = new ExtentReports();
    extent.attachReporter(spark);

    spark.config().setDocumentTitle("Automation Report");
    spark.config().setReportName("SwiftBim_Client UI Tests");
    spark.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);

    extent.setSystemInfo("OS", System.getProperty("os.name"));
    extent.setSystemInfo("User", System.getProperty("user.name"));
    extent.setSystemInfo("Browser", "Chrome");

    return extent;
	}

}
