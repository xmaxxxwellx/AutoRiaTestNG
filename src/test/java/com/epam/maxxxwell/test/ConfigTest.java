package com.epam.maxxxwell.test;


import com.epam.maxxxwell.test.utils.Tools;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.maxxxwell.test.WebDriverContainer.getDriver;
import static com.epam.maxxxwell.test.WebDriverContainer.quitDriver;
import static com.epam.maxxxwell.test.WebDriverContainer.setDriver;

public class ConfigTest extends TestListenerAdapter {

    private Logger Log = Logger.getLogger(ConfigTest.class.getName());
    private File logFile = new File("log4j-Allure.log");

    @Parameters({"baseURL",
            "driverPass"})
    @BeforeMethod
    public void setup(@Optional("https://auto.ria.com") String baseUrl,
                      @Optional("\\drivers\\chromedriver.exe") String driverPass,
                      Method method) {

        System.setProperty("webdriver.chrome.driver", driverPass);

        setDriver(WebDriverContainer.Driver.Chrome);
        getDriver().get(baseUrl);

        Log.info("BeforeTest setup method done!");

        Log.info("-------======= Test \"" + method.getName() + "\" started =======-------");
    }

    @AfterMethod
    public void cleanup(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            Tools.makeScreenshot(getDriver(), result.getName());
            Log.error("Test failure. Screenshot have been taken!");
            saveScreenshot();
        }

        Log.info("-------======= Test \"" + result.getMethod().getMethodName() + "\" finished =======-------");

        quitDriver();
        Log.info("AfterTest cleanup method done!");

        appendLogToAllure(logFile);
        FileUtils.write(new File("log4j-Allure.log"), "");
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] saveScreenshot() {
        Log.info("Screenshot have been added to Allure Report.");
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Test logs", type = "text/html")
    private byte[] appendLogToAllure(File file) {
        try {
            return FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
        return null;
    }
}
