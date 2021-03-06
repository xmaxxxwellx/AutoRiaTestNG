package com.epam.maxxxwell.test;

import com.epam.maxxxwell.test.utils.ConfigProperties;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.concurrent.TimeUnit;

public class WebDriverContainer {

    private static WebDriver driver = null;

    private static Logger Log = Logger.getLogger(WebDriverContainer.class.getName());

    private WebDriverContainer(){
        // block constructor
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            switch (ConfigProperties.getParameter("browser")) {
                case "chrome":{
                    System.setProperty("webdriver.chrome.driver", ConfigProperties.getDriverPath());
                    driver = new ChromeDriver();
                    break;
                }

                case "firefox": {
                    System.setProperty("webdriver.gecko.driver", ConfigProperties.getDriverPath());
                    driver = new FirefoxDriver();
                    break;
                }
                case "ie": {
                    System.setProperty("webdriver.ie.driver", ConfigProperties.getDriverPath());
                    driver = new InternetExplorerDriver();
                    break;
                }
                default: {
                    System.setProperty("webdriver.chrome.driver", ConfigProperties.getDriverPath());
                    driver = new ChromeDriver();
                }
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(ConfigProperties.getImplicitlyWait(), TimeUnit.SECONDS);
            Log.info("WebDriver created and managed.");
        }
        return driver;
    }

    public static void quitDriver(){
        if(driver != null){
            driver.quit();
            driver = null;
            Log.info("WebDriver closed.");
        }
    }
}
