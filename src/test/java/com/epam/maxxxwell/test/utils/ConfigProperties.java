package com.epam.maxxxwell.test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {

    private static final Properties properties;
    private static File file = new File("src/test/resources/config.properties");
    private static FileInputStream fileInputStream = null;

    static {
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        properties = new Properties();

        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDriverPath(){
        String driverPath = null;
        switch (getParameter("browser")){
            case "chrome":
                driverPath = properties.getProperty("chromeDriverPath");
                break;
            case "firefox":
                driverPath = properties.getProperty("firefoxDriverPath");
                break;
            case "ie":
                driverPath = properties.getProperty("IEDriverPath");
                break;
        }

        if(driverPath != null) {
            return driverPath;
        }
        else {
            throw new RuntimeException("driverPath not specified in the Configuration.properties file.");
        }
    }

    public static long getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("implicitlyWait");
        if(implicitlyWait != null) {
            return Long.parseLong(implicitlyWait);
        }
        else {
            throw new RuntimeException("implicitlyWait not specified in the Configuration.properties file.");
        }
    }

    public static String getUrl() {
        String url = properties.getProperty("URL");
        if(url != null) {
            return url;
        }
        else {
            throw new RuntimeException("url not specified in the Configuration.properties file.");
        }
    }

    public static String getParameter(String name) {
        String value = System.getProperty(name);
        if (value == null || value.isEmpty()) {
            value = properties.getProperty(name);
        }
        return value;
    }
}
