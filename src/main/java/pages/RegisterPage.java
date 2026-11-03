package pages;

import actionDriver.ActionDriver;
import base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.PropertyFileReader;

public class RegisterPage {

    WebDriver driver;
    ActionDriver actionDriver;


    public RegisterPage(WebDriver driver){
        this.driver = driver;
        this.actionDriver = BaseClass.getActionDriver();
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//h1[normalize-space()='Create Your Free Account']")
    public WebElement text;

    @FindBy(xpath = "//span[normalize-space()='Continue with Google']")
    public WebElement continueWithGoogle;

    @FindBy(xpath = "//button[normalize-space()='Continue with email']")
    public WebElement continueWithEmail;

    @FindBy(xpath = "//span[normalize-space()='Go to Home']")
    public WebElement goToHome;

    @FindBy(xpath = "//span[normalize-space()='Sign in']")
    public WebElement SignIn;

    @FindBy(xpath = "//span[normalize-space()='Sign in']")
    public WebElement signInText;

    @FindBy(xpath = "//div[@data-identifier='deshanlochana2@gmail.com']")
    public WebElement account;

    @FindBy(css = "iframe[src*='accounts.google.com/gsi/button']")
    public WebElement googleButtonFrame;

    @FindBy(xpath = "//div[@class='_googleLoginOverlay_k9pwi_45']")
    public WebElement actualGoogleButton;

    @FindBy(xpath = "//input[@id='identifierId']")
    public WebElement emailOrPhone;

    @FindBy(xpath = "//button//span[normalize-space()='Next']")
    public WebElement nextBtn;

    public String registerMenu(){
        return actionDriver.getText(text);
    }

    public boolean isContinueWithGoogleDisplayed() {
        return actionDriver.isDisplayed(continueWithGoogle);
    }

    public boolean isContinueWithEmailDisplayed() {
        return actionDriver.isDisplayed(continueWithEmail);
    }

    public boolean isGoToHomeDisplayed() {
        return actionDriver.isDisplayed(goToHome);
    }

    public boolean isSignInDisplayed() {
        return actionDriver.isDisplayed(SignIn);
    }

    public String clickContinueWithGoogle(){
        //actionDriver.switchToFrameByElement(googleButtonFrame);
        actionDriver.switchToWindow(actualGoogleButton);
        return actionDriver.getText(signInText);
    }

    public void selectGoogleAccount(String email) {
        actionDriver.click(account);

    }

    public void setEmailOrPhone(String email){
        actionDriver.setInputField(emailOrPhone,email);
        actionDriver.click(nextBtn);
    }

    public void signIn(){
        actionDriver.click(SignIn);
    }

}
