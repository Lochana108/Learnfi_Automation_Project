package pages;

import actionDriver.ActionDriver;
import base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver;
    ActionDriver actionDriver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        this.actionDriver = BaseClass.getActionDriver();
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//input[@id='username']")
    public WebElement emailEle;

    @FindBy(xpath = "//input[@id='password']")
    public WebElement passwordEle;

    @FindBy(xpath = "//button[normalize-space()='Continue']")
    public WebElement continueBtn;

    public void enterEmail(String emailAddress){
        actionDriver.setInputField(emailEle,emailAddress);
    }
    public void enterPassword(String password){
        actionDriver.setInputField(passwordEle,password);
    }
    public void clickContinueBtn(){
        actionDriver.click(continueBtn);
    }

}
