package utilities.extentReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ExtentReportUtility {
    public static ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    public static void startReporter(){
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"//test-output//extentReports//ExtentReport.html");
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Learnfi - extent Report");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Environment","QA");
        extent.setSystemInfo("ProjectName","Learnfi_Project");
        extent.setSystemInfo("Browser","chrome");
        extent.setSystemInfo("Operating System",System.getProperty("os.name"));
        extent.setSystemInfo("Java Version",System.getProperty("java.version"));
        extent.setSystemInfo("Tester","Lochana");
    }

    public static void createTest(String testName){
        test = extent.createTest(testName);
    }
    public static ExtentTest getTest(){
        return test;
    }

    public static void endReport(){
        extent.flush();
    }

    public static void captureScreenshotAsBase64_StepInfo(WebDriver driver,String message){
        String base64 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.info(message, MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
    }

    public static void captureScreenshotAsBase64_FailCase(WebDriver driver,String message){
        String base64 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.fail(message,MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
    }

    public static void captureScreenshotAsBase64_SkipCase(WebDriver driver,String message){
        String base64 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.skip(message,MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
    }



}
