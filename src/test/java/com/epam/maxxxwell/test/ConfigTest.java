package com.epam.maxxxwell.test;

import com.epam.maxxxwell.test.utils.Log;
import com.epam.maxxxwell.test.utils.Tools;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import static com.epam.maxxxwell.test.WebDriverContainer.getDriver;
import static com.epam.maxxxwell.test.WebDriverContainer.quitDriver;
import static com.epam.maxxxwell.test.WebDriverContainer.setDriver;

public class ConfigTest {

    @Parameters({"baseURL",
            "driverPass"})
    @BeforeMethod
    public void setup(@Optional("https://auto.ria.com") String baseUrl,
                      @Optional("\\drivers\\chromedriver.exe") String driverPass) {
        System.setProperty("webdriver.chrome.driver", driverPass);

        setDriver(WebDriverContainer.Driver.Chrome);
        getDriver().get(baseUrl);

        Log.info("BeforeTest setup done!");
    }

    @AfterMethod
    public void cleanup(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            Tools.makeScreenshot(getDriver(), result.getName());
            Log.error("Test failure. Screenshot have been taken!");
            saveScreenshot();
        }

        quitDriver();
        Log.info("Driver is closed!");
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] saveScreenshot() {
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
