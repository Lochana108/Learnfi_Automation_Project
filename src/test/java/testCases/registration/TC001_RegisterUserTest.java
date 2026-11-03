package testCases.registration;

import base.BaseClass;
import dataProviders.TestDataProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import utilities.ExcelReaderUtility;
import utilities.PropertyFileReader;

public class TC001_RegisterUserTest extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC001_RegisterUserTest.class);

    @Test
    public void verifyRegistrationPageUIElements() {
        logger.info("--------Starting TC001_RegisterUserTest--------");
        HomePage home = new HomePage(getDriver());
        Assert.assertTrue(home.signUpMenu(), "Sign in Element is not displayed");
        home.signUp();

        logger.info("Verifying Register Menu");
        RegisterPage registerPage = new RegisterPage(getDriver());
        String expectedUrl = PropertyFileReader.getInstance().getProperty("pagesurl","RegisterPage_URL");
        String currentUrl = getActionDriver().getCurrentURL();
        Assert.assertEquals(currentUrl,expectedUrl,"Navigation to Register Page failed!");

        logger.info("Verifying continue With Google Button");
        boolean continueWithGoogleDisplayed = registerPage.isContinueWithGoogleDisplayed();
        Assert.assertTrue(continueWithGoogleDisplayed, "Continue with Google button is not displayed");

        logger.info("Verifying continue With Email button");
        boolean continueWithEmailDisplayed = registerPage.isContinueWithEmailDisplayed();
        Assert.assertTrue(continueWithEmailDisplayed, "Continue with Email button is not displayed");

        logger.info("Verifying go to Home button");
        boolean goToHomeDisplayed = registerPage.isGoToHomeDisplayed();
        Assert.assertTrue(goToHomeDisplayed, "Go to Home button is not displayed");

        logger.info("Verifying sign in button");
        boolean signIn = registerPage.isSignInDisplayed();
        Assert.assertTrue(signIn, "Sign in button is not displayed");
        logger.info("--------Finished TC001_RegisterUserTest--------");
    }

    @Test(dataProvider = "loginData",dataProviderClass = TestDataProvider.class)
    public void verifyingSignInFunctionality(String email,String password,String validation){
        getDriver().get(PropertyFileReader.getInstance().getProperty("pagesurl","RegisterPage_URL"));
        RegisterPage registerPage = new RegisterPage(getDriver());
        registerPage.signIn();

        LoginPage loginPage = new LoginPage(getDriver());
        String currenturl = getActionDriver().getCurrentURL();
        String expectedUrl = PropertyFileReader.getInstance().getProperty("pagesurl","LoginPage_URL");
        Assert.assertEquals(currenturl,expectedUrl,"Navigation to Login Page failed!");

        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickContinueBtn();

        String navigationExpUrl = PropertyFileReader.getInstance().getProperty("pagesurl","HomePage_URL");
        boolean urlVerification = getActionDriver().waitForURL(navigationExpUrl);

        if(validation.equals("valid")){
            Assert.assertTrue(urlVerification,"Expecting login success but not navigated to dashboard!");
        }else{
            Assert.assertFalse(urlVerification,"Expecting login failed but navigated to dashboard!");
        }

    }
}
