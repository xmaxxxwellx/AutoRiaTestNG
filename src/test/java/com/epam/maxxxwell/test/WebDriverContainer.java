package com.epam.maxxxwell.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.concurrent.TimeUnit;

public class WebDriverContainer {

    public enum Driver{
        Firefox, Chrome, IE
    }

    private static Driver driverType;
    private static WebDriver driver = null;

    private WebDriverContainer(){
        // block constructor
    }

    public static void setDriver(Driver driverType){
        WebDriverContainer.driverType = driverType;
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            switch (driverType) {
                case Chrome:
                    driver = new ChromeDriver();
                    break;
                case Firefox:
                    driver = new FirefoxDriver();
                    break;
                case IE:
                    driver = new InternetExplorerDriver();
                    break;
                default:
                    driver = new ChromeDriver();
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        return driver;
    }

    public static void quitDriver(){
        if(driver != null){
            driver.quit();
            driver = null;
        }
    }
}
