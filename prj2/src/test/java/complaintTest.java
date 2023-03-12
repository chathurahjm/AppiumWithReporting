import com.aventstack.extentreports.Status;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.appHomePage;
import pages.complaints.complaintLandingPage;
import pages.complaints.complaintListPage;
import pages.complaints.complaintRegisterPage;
import pages.loginPage;
import pages.splashPage;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class complaintTest extends TestBase{

    @Test
    public void validateUserCanSuccessfullyCreateAComplaint()
    {
        test = extend.createTest("Validate user can successfully create a complaint ","This is the 1st test");
        SoftAssert softAssert = new SoftAssert();
        Date date = new Date();
        String complaintTitle = "Cha"+date.toString();
        test.log(Status.INFO,"complaintTitle will be: "+complaintTitle);

        splashPage splashPage = new splashPage(driver);
        splashPage.clickOnSkipButton();
        test.log(Status.INFO,"User click on the skip button");


        loginPage loginPage = new loginPage(driver);
        loginPage.loginTotheSystemWithValidUserNameAndPassword();
        test.log(Status.INFO,"User log in to the system with valid user name and password");


        appHomePage appHomePage = new appHomePage(driver);
        appHomePage.tapOnComplaintIcon();
        test.log(Status.INFO,"User user tap on complaint icon");


        complaintLandingPage complaintLandingPage = new complaintLandingPage(driver);
        complaintLandingPage.tapOnRegisterComplaintButton();

        complaintRegisterPage complaintRegisterPage = new complaintRegisterPage(driver);
        complaintRegisterPage.createANewCompalint(complaintTitle);

        complaintLandingPage.tapOnComplaintList();

        complaintListPage complaintListPage = new complaintListPage(driver);
        //Boolean output = complaintListPage.isTitleAvailableAtTheComplaintList();

        softAssert.assertTrue(complaintListPage.isTitleAvailableAtTheComplaintList(complaintTitle));

        softAssert.assertAll();

    }

    @Test
    public void validateUserCanSuccessfullyLoginTotheSystem() {
        test = extend.createTest("Validate user can successfully log in to the system", "This is the 1st test");
        SoftAssert softAssert = new SoftAssert();
        Date date = new Date();
        String complaintTitle = "Cha" + date.toString();
        test.log(Status.INFO, "complaintTitle will be: " + complaintTitle);

        softAssert.assertEquals(1,4);
        softAssert.assertAll();
    }

    }
