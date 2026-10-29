package utilities.extentReport;

import base.BaseClass;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListnerManager extends BaseClass implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getTestClass().getName() + " - " + result.getMethod().getMethodName();
        ExtentReportUtility.createTest(testName);
        ExtentReportUtility.getTest().info("Started Test Execution : "+result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportUtility.getTest().pass("Test Case Passed : "+result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportUtility.getTest().fail(result.getName()+" - Test Case Failed");
        ExtentReportUtility.getTest().fail(result.getThrowable());
        ExtentReportUtility.captureScreenshotAsBase64_FailCase(getDriver(), result.getName()+"_failed_Point_Screenshot");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportUtility.getTest().skip(result.getName()+"_Test Case skipped");
        ExtentReportUtility.captureScreenshotAsBase64_SkipCase(getDriver(), result.getName()+"_skipped_Point_Screenshot");
    }
}
