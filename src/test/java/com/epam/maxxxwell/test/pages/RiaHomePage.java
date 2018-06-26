package com.epam.maxxxwell.test.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class RiaHomePage {

    private WebDriver driver;

    @FindBy (xpath = "//form[@id = 'mainSearchForm']/div[@class = 'footer-form']/button[@class = 'button']")
    private WebElement searchButton;

//    @FindBy (xpath = "//div[@id = 'header']/header[1]/div[1]/div/div/a[2]")
//    private WebElement loginLink;

    @FindBy (id = "categories")
    private WebElement categoriesDropDownList;

    @FindBy (id = "regionAutocompleteAutocomplete-1")
    private WebElement regionsDropDownList;

    @FindBy (xpath = "//select[@id = 'categories']/option")
    private List<WebElement> categoriesItems;

    @FindBy (xpath = "//div[@id = 'regionAutocompleteAutocomplete-1']/ul/li/a")
    private List<WebElement> regionsItems;

    @FindBy (className = "item-brands")
    private List<WebElement> brandItems;

    @FindBy (id = "priceFrom")
    private WebElement priceFromInput;

    @FindBy (id = "priceTo")
    private WebElement priceToInput;

    @FindBy (id = "yearFrom")
    private WebElement yearFromDropDownList;

    @FindBy (id = "yearTo")
    private WebElement yearToDropDownList;

    public RiaHomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public RiaResultPage getDefaultResultPage(){
        searchButton.submit();
        return PageFactory.initElements(driver, RiaResultPage.class);
    }

    public RiaResultPage getResultPageByCategory(int categoryNumber){
        // Start actions
        Actions builder = new Actions(driver);
        builder.click(categoriesDropDownList);
        for (WebElement item : categoriesItems) {
            if(categoryNumber == Integer.parseInt(item.getAttribute("value"))) {
                item.click();
                break;
            }
        }
        // Generate the composite action.
        Action compositeAction = builder.build();
        // Perform the composite action.
        compositeAction.perform();

        searchButton.submit();

        return PageFactory.initElements(driver, RiaResultPage.class);
    }

    public RiaResultPage getResultPageByRegion(String region){
        regionsDropDownList.click();
        for (WebElement item : regionsItems) {
            if(region.equals(item.getAttribute("innerHTML"))) {
                Actions builder = new Actions(driver);
                builder.moveToElement(item);
                builder.click(item);
                builder.build().perform();
                break;
            }
        }
        searchButton.submit();

        return PageFactory.initElements(driver, RiaResultPage.class);
    }

    public RiaBrandPage getResultByBrand(String brand){
        for(WebElement item : brandItems){
            if(item.getAttribute("title").equals(brand)){
                item.click();
                break;
            }
        }

        return PageFactory.initElements(driver, RiaBrandPage.class);
    }

    public RiaResultPage getResultPageByPrice(int priceFrom, int priceTo){
        priceFromInput.sendKeys(String.valueOf(priceFrom));
        priceToInput.sendKeys(String.valueOf(priceTo));
        searchButton.submit();

        return PageFactory.initElements(driver, RiaResultPage.class);
    }
    public RiaResultPage getResultPageByPrice(String price){
        //priceFromInput.sendKeys(price);
        priceFromInput.click();
        new Actions(driver).sendKeys(price).perform();
        //priceToInput.sendKeys(price);
        priceToInput.click();
        new Actions(driver).sendKeys(price).perform();
        searchButton.submit();

        return PageFactory.initElements(driver, RiaResultPage.class);
    }

    public RiaResultPage getResultPageByYear(int yearFrom, int yearTo){
        List<WebElement> yearsFrom = yearFromDropDownList.findElements(By.tagName("option"));
        List<WebElement> yearsTo = yearToDropDownList.findElements(By.tagName("option"));

        yearFromDropDownList.click();
        for (WebElement item : yearsFrom) {
            if(String.valueOf(yearFrom).equals(item.getAttribute("innerHTML"))) {
                item.click();
                break;
            }
        }
        yearToDropDownList.click();
        for (WebElement item : yearsTo) {
            if(String.valueOf(yearTo).equals(item.getAttribute("innerHTML"))) {
                item.click();
                break;
            }
        }
        searchButton.submit();

        return PageFactory.initElements(driver, RiaResultPage.class);
    }

//    public RiaLoginPage getToLoginPage(){
//        loginLink.click();
//        return PageFactory.initElements(driver, RiaLoginPage.class);
//    }
}
