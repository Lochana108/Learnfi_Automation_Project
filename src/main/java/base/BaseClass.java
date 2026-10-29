package base;

import actionDriver.ActionDriver;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utilities.PropertyFileReader;
import utilities.extentReport.ExtentReportUtility;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BaseClass {

    private static final Logger logger = LogManager.getLogger(BaseClass.class);

    private static ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();

    public static WebDriver getDriver(){
        if (webDriverThreadLocal.get() == null){
            System.out.println("WebDriver is not initialized");
            throw new IllegalStateException("WebDriver is not initialized");
        }
        return webDriverThreadLocal.get();
    }

    public static ActionDriver getActionDriver(){
        if (actionDriver.get() == null){
            System.out.println("ActionDriver is not initialized");
            throw new IllegalStateException("ActionDriver is not initialized.");
        }
        return actionDriver.get();
    }

    @BeforeSuite
    public void beforeSuite(){
        ExtentReportUtility.startReporter();

        try{
            String logFilePath = "logs/automation.log";
            File logFile = new File(logFilePath);
            if(logFile.exists()){
                FileUtils.write(logFile,"",false);
                logger.info("Log file is cleaned: "+logFilePath);
            }else {
                FileUtils.touch(logFile);
                logger.info("Log file created hence not exist: "+logFilePath);
            }
        } catch (Exception e) {
            logger.error("Error cleaning or creating the file: "+e.getMessage());
        }
    }
    @BeforeMethod
    public void openPage() throws IOException {

        String browser = PropertyFileReader.getInstance().getProperty("config","browser");
        String implicity_wait = PropertyFileReader.getInstance().getProperty("config", "implicit_wait");
        String App_url = PropertyFileReader.getInstance().getProperty("config","App_url");

        switch (browser.toLowerCase()){
            case "chrome" :
                webDriverThreadLocal.set(new ChromeDriver());
                break;

            case "edge" :
                webDriverThreadLocal.set(new EdgeDriver());
                break;

            case "firefox" :
                webDriverThreadLocal.set(new FirefoxDriver());
                break;
            default:
                System.out.println("Browser not supported");
        }
        logger.info("Test case automating with : "+browser);

        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(implicity_wait)));
        getDriver().get(App_url);

        actionDriver.set(new ActionDriver(getDriver()));
    }

    @AfterMethod
    public void closeBrowser(){
        getDriver().quit();
    }

    @AfterSuite
    public void afterSuite(){
        ExtentReportUtility.endReport();
    }
}
