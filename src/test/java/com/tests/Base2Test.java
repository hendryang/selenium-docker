package com.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Base2Test {
    protected WebDriver driver;

    @BeforeTest
    public void setupDriver() {
        /* required details
            [1] Browser to run on
            [2] Hub details (eq. HUB_HOST)
         */
        String host = "localhost";
        DesiredCapabilities dc ;

        // get browser
        if (System.getProperty("BROWSER") != null && System.getProperty("BROWSER").equalsIgnoreCase("firefox")){
            dc = DesiredCapabilities.firefox();
        } else {
            dc = DesiredCapabilities.chrome();
        }

        // get hub url
        if (System.getProperty("HUB_HOST") != null) {
            host = System.getProperty("HUB_HOST");
        }

        // connect to remote webdriver
        String hubURL = "http://" + host + ":4444/wd/hub";
        try {
            if ( host.equalsIgnoreCase("local") ) {
                System.setProperty("webdriver.chrome.driver" , System.getProperty("user.dir") + File.separator + "src/test/resources/chromedriver" );
                this.driver = new ChromeDriver();
            } else {
                this.driver = new RemoteWebDriver( new URL(hubURL) , dc );
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Local driver
        // System.setProperty("webdriver.chrome.driver" , System.getProperty("user.dir") + File.separator + "src/test/resources/chromedriver" );
        // driver = new ChromeDriver();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    public void fileupload() {
        //assuming driver is the name of the variable for WebDriver instance.
        if(driver instanceof RemoteWebDriver){
            ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        }
    }
}
