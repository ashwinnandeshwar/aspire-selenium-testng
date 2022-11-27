package org.aspire.testCases;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.aspire.constants.DriverType;
import org.aspire.utilities.ConfigFileReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static WebDriver webDriver;
    ConfigFileReader configFileReader=new ConfigFileReader();

    @Parameters({"browser"})
    @BeforeMethod
    public void setup(String browser) throws InterruptedException {
//        public void setup(@Optional("browser") String browser, @Optional("environment") String environment){

        System.out.println("Parameter is: "+browser);
        DriverType driverType = configFileReader.getBrowser(browser);

        switch (driverType) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver();
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                webDriver = new FirefoxDriver(firefoxOptions);
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                webDriver = new EdgeDriver();
                break;
            case SAFARI:
                WebDriverManager.safaridriver().setup();
                webDriver = new SafariDriver();
                break;
        }

        long time = configFileReader.getTime();

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
        webDriver.manage().timeouts().setScriptTimeout(time, TimeUnit.SECONDS);
        webDriver.manage().deleteAllCookies();
        webDriver.get(configFileReader.getApplicationUrl());
    }

    @AfterMethod
    public void teardown(){
        webDriver.quit();
    }
}
