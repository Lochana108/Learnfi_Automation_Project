package pages;

import actionDriver.ActionDriver;
import base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    WebDriver driver;
    ActionDriver actionDriver;

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
        this.actionDriver = BaseClass.getActionDriver();
    }

    @FindBy(xpath = "//a[contains(@class,'nav-link header-signup-btn text-white mx-1 nav-link')]")
    public WebElement signUp;

    public boolean signUpMenu(){
        return actionDriver.isDisplayed(signUp);
    }

    public void signIn(){
        actionDriver.click(signUp);
    }
}
