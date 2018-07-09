package com.epam.maxxxwell.test;

import com.epam.maxxxwell.test.pages.RiaBrandPage;
import com.epam.maxxxwell.test.pages.RiaHomePage;
import com.epam.maxxxwell.test.pages.RiaProductPage;
import com.epam.maxxxwell.test.pages.RiaResultPage;
import io.qameta.allure.Flaky;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MainTest extends ConfigTest {

    @Test(dataProvider = "categorySelectionTest", dataProviderClass = DataClass.class)
    public void categorySelection(int category) {
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaResultPage riaResultPage = riaHomePage.getResultPageByCategory(category);
        System.out.println("Hi");
        Assert.assertEquals(riaResultPage.getCategoryNumber(), category,
                "We are not at selected category page");
    }

    @Test
    public void returnToMainPageUsingLogo() {
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaResultPage riaResultPage = riaHomePage.getDefaultResultPage();

        riaHomePage = riaResultPage.mainLogoClick();

        Assert.assertEquals(riaHomePage.getTitle(),
                "AUTO.RIA™ — Автобазар №1. Купить и продать авто легко как никогда",
                "We are not at the main page!");
    }

    @Test(dataProvider = "categorySelectionTest", dataProviderClass = DataClass.class)
    public void productOfChosenCategory(int category) {
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaResultPage riaResultPage = riaHomePage.getResultPageByCategory(category);

        String resultPageCategoryName = riaResultPage.getCategoryName().trim();

        RiaProductPage riaProductPage = riaResultPage.openFirstResultLink();

        Assert.assertTrue(riaProductPage.getProductDescriptionText()
                        .contains(resultPageCategoryName),
                "Product is not from chosen category!");
    }

    @Test(dataProvider = "regionSelectionTest", dataProviderClass = DataClass.class)
    public void regionSelection(String region) {
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaResultPage riaResultPage = riaHomePage.getResultPageByRegion(region);

        Assert.assertTrue(riaResultPage.regionMatching(region),
                "There is no selected region on the result page");
    }

    @Test(dataProvider = "brandCheckingTest", dataProviderClass = DataClass.class)
    public void checkingBrandCategorySelection(String brand, int linkNumber) {
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaBrandPage riaBrandPage = riaHomePage.getResultByBrand(brand);
        RiaProductPage riaProductPage = riaBrandPage.getProductByLinkNumber(linkNumber);

        Assert.assertTrue(riaProductPage.getProductName().contains(brand),
                "Product has wrong category");
    }

    @Test(dataProvider = "priceCheckingTest", dataProviderClass = DataClass.class)
    public void findProductByPrice(int smallPrice, int bigPrice) {
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaResultPage riaResultPage = riaHomePage.getResultPageByPrice(smallPrice, bigPrice);
        RiaProductPage riaProductPage = riaResultPage.openFirstResultLink();

        Assert.assertTrue(riaProductPage.getProductPrice() >= smallPrice
                        && riaProductPage.getProductPrice() <= bigPrice,
                "Product price is out of range!");
    }

    @Test(dataProvider = "priceCheckingTest", dataProviderClass = DataClass.class)
    public void wrongRangeOfPriceCheck(int smallPrice, int bigPrice) {
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaResultPage riaResultPage = riaHomePage.getResultPageByPrice(bigPrice, smallPrice);

        Assert.assertEquals(riaResultPage.getResultsCount(), 0);
    }

    @Test(dataProvider = "yearCheckingTest", dataProviderClass = DataClass.class)
    public void findProductByYear(int earlierYear, int laterYear) {
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaResultPage riaResultPage = riaHomePage.getResultPageByYear(earlierYear, laterYear);
        RiaProductPage riaProductPage = riaResultPage.openFirstResultLink();

        Assert.assertTrue(riaProductPage.getProductYear() >= earlierYear
                        && riaProductPage.getProductYear() <= laterYear,
                "Product year is out of range!");
    }

    @Flaky
    @Test(dataProvider = "yearCheckingTest", dataProviderClass = DataClass.class)
    public void wrongRangeOfYearCheck(int earlierYear, int laterYear) {
        RiaHomePage riaHomePage = new RiaHomePage();
        RiaResultPage riaResultPage = riaHomePage.getResultPageByYear(laterYear, earlierYear);

        Assert.assertEquals(riaResultPage.getResultsCount(), 0);
    }
}