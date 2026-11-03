package actionDriver;

import base.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.PropertyFileReader;
import utilities.extentReport.ExtentReportUtility;

import java.time.Duration;
import java.util.Set;

public class ActionDriver {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(ActionDriver.class);

    public ActionDriver(WebDriver driver){
        this.driver = driver;
        long explicitWait = Long.parseLong(PropertyFileReader.getInstance().getProperty("config","explicit_wait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
    }

    private void waitForElementToBeClickable(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            logger.error("element is not clickable : "+e.getMessage());
        }
    }

    private void waitForElementToBeVisible(WebElement element){
       try{
           wait.until(ExpectedConditions.visibilityOf(element));
       } catch (Exception e) {
           logger.error("element is not visible : "+e.getMessage());
       }
    }

    public boolean waitForURL(String expectedURL){
        try{
            return wait.until(ExpectedConditions.urlToBe(expectedURL));
        } catch (Exception e) {
            logger.error("Expected URL was not reached: " + expectedURL, e);
            return false;
        }
    }

    public boolean isDisplayed(WebElement element){
        try{
            waitForElementToBeVisible(element);
            return element.isDisplayed();
        } catch (Exception e) {
            logger.error("element is not Displayed : "+e.getMessage());
            return false;
        }
    }

    public void click(WebElement element) {
        try {
            waitForElementToBeClickable(element);
            element.click();
        } catch (Exception e) {
            logger.error("Unable to click Element");
        }
    }

    public String getText(WebElement element){
        try{
            waitForElementToBeVisible(element);
            return element.getText();
        } catch (Exception e) {
            logger.error("Unable to get the text : "+e.getMessage());
            return "";
        }
    }

    public void switchToWindow(WebElement element){
        String mainWindow = driver.getWindowHandle();
        click(element);
        try{
            wait.until(w -> w.getWindowHandles().size()>1);

            Set<String> allWindows = driver.getWindowHandles();

            for (String windowHandle : allWindows){
                if(!windowHandle.equals(mainWindow)){
                    driver.switchTo().window(windowHandle);
                    logger.info("Switched to window");
                    return;
                }
            }
            logger.warn("Window is not found.");
        }catch(Exception e){
            logger.error("Unable to switch window ",e);
        }
    }
    public String getCurrentURL() {
        try {
            String url = driver.getCurrentUrl();
            ExtentReportUtility.logStep("Current URL fetched: " + url);
            logger.info("Current URL fetched: " + url);
            return url;
        } catch (Exception e) {
            ExtentReportUtility.logFailure(BaseClass.getDriver(), "Unable to fetch current URL", "get_current_url_failed");
            logger.error("Unable to fetch current URL: " + e.getMessage());
            return null;
        }
    }

    public void switchToFrameByElement(WebElement element){
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
        } catch (Exception e) {
            logger.error("Unale to switch to iframe ",e);
        }
    }

    public void setInputField(WebElement element,String value){
        try{
            waitForElementToBeVisible(element);
            element.clear();
            element.sendKeys(value);
        } catch (Exception e) {
            logger.error("Unable to enter text", e);
        }
    }


}
