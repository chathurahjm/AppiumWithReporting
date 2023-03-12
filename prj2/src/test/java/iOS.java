package org.demo;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class iOS {
    public WebDriver driver;
    @Test
    public void testOne() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("appium:deviceName", "iPad Pro");
        desiredCapabilities.setCapability("appium:platformVersion", "16.2");
        desiredCapabilities.setCapability("platformName", "iOS");
        desiredCapabilities.setCapability("udid", "15C4907F-1A2A-4839-A683-025C9AE80133");
        desiredCapabilities.setCapability("appium:automationName", "XCUITest");
        desiredCapabilities.setCapability("webkit:WebRTC", false);
        desiredCapabilities.setCapability("relaxed-security",true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        //desiredCapabilities.setCapability("noReset", "true");
        URL remoteUrl = new URL("http://localhost:4723/wd/hub");

        driver = new IOSDriver<>(remoteUrl, desiredCapabilities);
        //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        driver.get("https://www.google.com");
        driver.get("https://www.yahoo.com");
    }
}
