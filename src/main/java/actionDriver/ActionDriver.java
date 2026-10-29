package actionDriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.PropertyFileReader;

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

    public void switchToFrameByElement(WebElement element){
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
        } catch (Exception e) {
            logger.error("Unale to switch to iframe ",e);
        }
    }

}
