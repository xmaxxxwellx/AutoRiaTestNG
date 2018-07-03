package com.epam.maxxxwell.test;


import com.epam.maxxxwell.test.utils.ConfigProperties;
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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.maxxxwell.test.WebDriverContainer.*;

public class ConfigTest extends TestListenerAdapter {

    private Logger Log = Logger.getLogger(ConfigTest.class.getName());
    private File logFile = new File("log4j-Allure.log");

    @BeforeMethod
    public void setup(Method method) {

        setDriver(WebDriverContainer.Driver.Chrome);
        //setDriver(WebDriverContainer.Driver.Firefox);
        //setDriver(WebDriverContainer.Driver.IE);

        getDriver().get(ConfigProperties.getUrl());

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
