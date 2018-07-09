package com.epam.maxxxwell.test;

import org.testng.annotations.DataProvider;

public class DataClass {

    @DataProvider(name = "categorySelectionTest")
    public Object[] categories() {
        return new Object[]{3, 5, 7};
    }

    @DataProvider(name = "regionSelectionTest")
    public Object[] regions() {
        return new Object[]{"Винница", "Киев"};
    }

    @DataProvider(name = "brandCheckingTest")
    public Object[][] brands() {
        return new Object[][]{
                {"Honda", 3},
                {"Opel", 2},
        };
    }

    @DataProvider(name = "priceCheckingTest")
    public Object[][] prices() {
        return new Object[][]{
                {2500, 5000},
                {3700, 4100},
        };
    }

    @DataProvider(name = "yearCheckingTest")
    public Object[][] years() {
        return new Object[][]{
                {2015, 2017},
                {2014, 2016},
        };
    }
}
