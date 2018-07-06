package com.epam.maxxxwell.test.utils;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.sql.Timestamp;

import static com.epam.maxxxwell.test.WebDriverContainer.getDriver;

public class Tools {

    private static Logger Log = Logger.getLogger(Tools.class.getName());

    public static void makeScreenshot(WebDriver driver, String fileName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot,
                    new File(".\\target\\screenshots\\"
                            + fileName + ".png"));
            Log.info("Screenshot taken at " + new Timestamp(System.currentTimeMillis()));
        }
        catch (Exception e)
        {
            Log.error("Exception while taking screenshot "+e.getMessage());
        }
    }

    public static void waitPageToLoad(WebDriver driver, int seconds) {
        try {
            Log.info("Start waiting");
            new WebDriverWait(driver, seconds).until(
                    (ExpectedCondition<Boolean>) d ->
                            ((JavascriptExecutor) d)
                                    .executeScript("return document.readyState")
                                    .equals("complete")
            );
            Log.info("Finish waiting");
        }
        catch (Throwable error) {
            Log.error("Exception in wait for loading page method!" + error.getMessage());
        }
    }

    public static WebElement dynamicElement(WebDriver driver, int seconds, By locator){
        return (new WebDriverWait(driver, seconds))
                .until((ExpectedCondition<WebElement>) d -> d.findElement(locator));
    }

    public static void mayClick(WebElement element){
        WebDriverWait wait = new WebDriverWait(getDriver(), ConfigProperties.getImplicitlyWait());
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForVisibility(By locator){
        WebDriverWait wait = new WebDriverWait(getDriver(), ConfigProperties.getImplicitlyWait());
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}