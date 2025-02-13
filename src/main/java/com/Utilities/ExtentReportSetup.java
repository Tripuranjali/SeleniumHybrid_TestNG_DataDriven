package com.Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportSetup {
	private static String reportPath;
	private static ExtentReports extent;

	// 🔹 Ensure a single instance of ExtentReports is created
	public static ExtentReports getReportObject() {
		if (extent == null) { // ✅ Prevent multiple initializations
			reportPath = System.getProperty("user.dir") + "/TestExecutionReports/" +  getReportNameWithTimestamp();

			ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
			reporter.config().setDocumentTitle("Test Execution Report");
			reporter.config().setTheme(Theme.DARK);
			reporter.config().setEncoding("utf-8");

			extent = new ExtentReports();
			extent.attachReporter(reporter);
			extent.setSystemInfo("Project Name", "SeleniumHybridFrameworkProject");
			extent.setSystemInfo("Testers", "Tripuranjali");
		}
		return extent; // ✅ Return the same instance every time
	}

	// Helper method to generate unique report names with timestamps
	private static String getReportNameWithTimestamp() {
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		return "Test-Report-" + timestamp + ".html";
	}
}
