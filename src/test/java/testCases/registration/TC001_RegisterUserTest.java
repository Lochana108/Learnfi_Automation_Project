package testCases.registration;

import base.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegisterPage;

public class TC001_RegisterUserTest extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC001_RegisterUserTest.class);

    @Test
    public void verifyRegistrationPageUIElements() {
        logger.info("--------Starting TC001_RegisterUserTest--------");
        HomePage home = new HomePage(getDriver());
        Assert.assertTrue(home.signUpMenu(), "Sign in Element is not displayed");
        home.signIn();

        logger.info("Verifying Register Menu");
        RegisterPage registerPage = new RegisterPage(getDriver());
        Assert.assertTrue(registerPage.registerMenu().contains("Create Your Free Account"), "Navigation to register Menu failed");

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

}
