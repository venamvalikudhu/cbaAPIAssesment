package frameworkUtils;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class ExtentReportConfig {

	public static ExtentReports SetExtentReport(String reportName) {
		String path = System.getProperty("user.dir") + File.separator 
				+ ConfigReader.getValue("framework_extent_result_folder")
				+ File.separator + reportName;
		
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName(reportName);
		reporter.config().setDocumentTitle("Test Results");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Harish");
		return extent;
	}
}