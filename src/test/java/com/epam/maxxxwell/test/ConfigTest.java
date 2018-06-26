package com.epam.maxxxwell.test;

import com.epam.maxxxwell.test.utils.Log;
import com.epam.maxxxwell.test.utils.Tools;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

public class ConfigTest {

    WebDriver driver;

    @Parameters({"baseURL",
            "driverPass",
            "pageWaitTimeout"})
    @BeforeMethod
    public void setup(@Optional("https://auto.ria.com") String baseUrl,
                      @Optional("\\drivers\\chromedriver.exe") String driverPass,
                      @Optional("10") String pageWaitTimeout) {
        System.setProperty("webdriver.chrome.driver", driverPass);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(pageWaitTimeout),
                TimeUnit.SECONDS);
        driver.get(baseUrl);
        Log.info("BeforeTest setup done!");
    }

    @AfterMethod
    public void cleanup(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            Tools.makeScreenshot(driver, result.getName());
            Log.error("Test failure. Screenshot have been taken!");
            saveScreenshot();
        }

        if (driver != null) {
            driver.quit();
            Log.info("Driver is closed!");
        }
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] saveScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
