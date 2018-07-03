package com.epam.maxxxwell.test;

import com.epam.maxxxwell.test.pages.RiaBrandPage;
import com.epam.maxxxwell.test.pages.RiaHomePage;
import com.epam.maxxxwell.test.pages.RiaProductPage;
import com.epam.maxxxwell.test.pages.RiaResultPage;
import io.qameta.allure.Flaky;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MainTest extends ConfigTest {

    private static final int CATEGORY_NUMBER = 4;
    private static final String REGION_NAME = "Винница";
    private static final String BRAND = "Honda";
    private static final int PRODUCT_LINK_NUMBER = 3;
    private static final int SMALLER_PRICE = 5000;
    private static final int BIGGER_PRICE = 15000;
    private static final int EARLIER_YEAR = 2012;
    private static final int LATER_YEAR = 2015;

    @Test
    public void categorySelection(){
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaResultPage riaResultPage = riaHomePage.getResultPageByCategory(CATEGORY_NUMBER);

        Assert.assertEquals(riaResultPage.getCategoryNumber(), CATEGORY_NUMBER,
                "Result page does not match selected category: " + CATEGORY_NUMBER);
    }

    @Test
    public void returnToMainPageUsingLogo(){
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaResultPage riaResultPage = riaHomePage.getDefaultResultPage();

        riaHomePage = riaResultPage.mainLogoClick();

        Assert.assertEquals(riaHomePage.getTitle(),
                "AUTO.RIA™ — Автобазар №1. Купить и продать авто легко как никогда",
                "We are not at the main page!");
    }

    @Test
    public void productOfChosenCategory() {
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaResultPage riaResultPage = riaHomePage.getResultPageByCategory(CATEGORY_NUMBER);

        String resultPageCategoryName = riaResultPage.getCategoryName().trim();

        RiaProductPage riaProductPage = riaResultPage.openFirstResultLink();

        Assert.assertTrue(riaProductPage.getProductDescriptionText()
                        .contains(resultPageCategoryName),
                "Product is not from chosen category!");
    }

    @Test
    public void regionSelection(){
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaResultPage riaResultPage = riaHomePage.getResultPageByRegion(REGION_NAME);

        Assert.assertTrue(riaResultPage.regionMatching(REGION_NAME),
                "There is no selected region on the result page");
    }

    @Test
    public void checkingBrandCategorySelection(){
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaBrandPage riaBrandPage = riaHomePage.getResultByBrand(BRAND);
        RiaProductPage riaProductPage = riaBrandPage.getProductByLinkNumber(PRODUCT_LINK_NUMBER);

        Assert.assertTrue(riaProductPage.getProductName().contains(BRAND),
                "Product has wrong category");
    }

    @Test
    public void findProductByPrice() {
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaResultPage riaResultPage = riaHomePage.getResultPageByPrice(SMALLER_PRICE, BIGGER_PRICE);
        RiaProductPage riaProductPage = riaResultPage.openFirstResultLink();

        Assert.assertTrue(riaProductPage.getProductPrice() >= SMALLER_PRICE
                        && riaProductPage.getProductPrice() <= BIGGER_PRICE,
                "Product price is out of range!");
    }

    @Test
    public void wrongRangeOfPriceCheck(){
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaResultPage riaResultPage = riaHomePage.getResultPageByPrice(BIGGER_PRICE, SMALLER_PRICE);

        Assert.assertEquals(riaResultPage.getResultsCount(), 0);
    }

    @Test
    public void findProductByYear() {
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaResultPage riaResultPage = riaHomePage.getResultPageByYear(EARLIER_YEAR, LATER_YEAR);
        RiaProductPage riaProductPage = riaResultPage.openFirstResultLink();

        Assert.assertTrue(riaProductPage.getProductYear() >= EARLIER_YEAR
                        && riaProductPage.getProductYear() <= LATER_YEAR,
                "Product year is out of range!");
    }

    @Flaky
    @Test
    public void wrongRangeOfYearCheck(){
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaResultPage riaResultPage = riaHomePage.getResultPageByYear(LATER_YEAR, EARLIER_YEAR);

        Assert.assertEquals(riaResultPage.getResultsCount(), 0);
    }
}