import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import common.constants;
import io.appium.java_client.android.AndroidDriver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.PublicKey;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestBase {

    protected AndroidDriver driver;

    protected WebDriverWait wait;

    protected ExtentReports extend;

    protected ExtentTest test;

    @BeforeMethod
    public void setUpDriver() throws IOException, ParseException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("appium:deviceName", "AOSP on IA Emulator");
        desiredCapabilities.setCapability("appium:platformVersion", "9");
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appium:appPackage", "com.iitdh.sonusourav.instigo");
        desiredCapabilities.setCapability("appium:appActivity", ".Account.SplashActivity");
        desiredCapabilities.setCapability("appium:automationName", "Appium");//UIAutomator2
        desiredCapabilities.setCapability("appium:noReset", false);
        desiredCapabilities.setCapability("appium:ensureWebviewsHavePages", true);
        desiredCapabilities.setCapability("appium:nativeWebScreenshot", true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        //desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);





        URL remoteUrl = new URL("http://localhost:4723/wd/hub");

        if (constants.LOCAL_EXECUTION) {
            driver = new AndroidDriver(remoteUrl, desiredCapabilities);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }
        else {

            JSONParser parser = new JSONParser();
            JSONObject config = (JSONObject) parser.parse(new FileReader("src/main/resources/BrowserStack_Config.json"));

            DesiredCapabilities capabilities = new DesiredCapabilities();

            JSONArray envs = (JSONArray) config.get("environments");
            Map<String, String> envCapabilities = (Map<String, String>) envs.get(0);
            Iterator it = envCapabilities.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }

            Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
            it = commonCapabilities.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                if(capabilities.getCapability(pair.getKey().toString()) == null){
                    capabilities.setCapability(pair.getKey().toString(), pair.getValue());
                }
            }

            String username = (String) config.get("username");

            String accessKey = (String) config.get("access_key");

            driver = new AndroidDriver (new URL("http://"+username+":"+accessKey+"@"+config.get("server")+"/wd/hub"), capabilities);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }


        wait = new WebDriverWait(driver,10);
    }


    @AfterMethod
    public  void extendReporting(ITestResult result)
    {
        if (result.getStatus()== ITestResult.FAILURE)
        {
            test.fail(result.getThrowable());
        }
        else if (result.getStatus() == ITestResult.SUCCESS)
        {
            test.pass(result.getThrowable());
        }
        else if (result.getStatus() == ITestResult.SKIP)
        {
            test.skip(result.getThrowable());
        }
        extend.flush();

    }

    @BeforeTest
    public void createReport()
    {
        extend = new ExtentReports();
        extend.attachReporter(new ExtentSparkReporter(("./extendReport.html")));
    }

    @AfterMethod
    public  void afterEachMethod()
    {
        driver.quit();
    }
}
