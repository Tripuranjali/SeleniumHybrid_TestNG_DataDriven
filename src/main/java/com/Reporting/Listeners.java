package com.Reporting;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.Utilities.ExtentReportSetup;
import com.Utilities.IDGenerationUtils;


public class Listeners implements ITestListener {

    private static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName(); 
        Object[] parameters = result.getParameters();
        
        String uniqueIdentifier = IDGenerationUtils.extractUniqueIdentifier(parameters);

        // Final test name
        String uniqueTestName = testName + "_" + uniqueIdentifier;

        // Initialize ExtentTest
        ExtentTest test = ExtentReportSetup.getReportObject().createTest(uniqueTestName).createNode(uniqueTestName);
        ExtentLogger.setTest(test);
        extentTestThreadLocal.set(test);

        System.out.println("Initialized ExtentTest for test: " + uniqueTestName);
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        extentTestThreadLocal.get().log(Status.PASS, result.getMethod().getMethodName() + "TEST PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTestThreadLocal.get().log(Status.FAIL, result.getThrowable());
        ExtentLogger.logScreenshot("Captured Failed Step: " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTestThreadLocal.get().log(Status.SKIP, result.getMethod().getMethodName()+"TEST SKIPPED");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportSetup.getReportObject().flush();
    }
}
