package com.epam.maxxxwell.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.epam.maxxxwell.test.WebDriverContainer.getDriver;

public class RiaResultPage {

    @FindBy(xpath = "//div[@class = 'container-header']/a[@class = 'logo']")
    private WebElement mainLogo;

    @FindBy(id = "category")
    private WebElement categoriesDropDownList;

    @FindBy(xpath = "//div[@id = 'searchResults']/section//div[@class = 'item ticket-title']/a")
    private WebElement firstResultLink;

    @FindBy(xpath = "//div[@id = 'searchResults']/section")
    private List<WebElement> results;

    @FindBy(id = "staticResultsCount")
    private WebElement resultsCount;

    public RiaResultPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public int getCategoryNumber() {
        Select categories = new Select(categoriesDropDownList);
        return Integer.parseInt(categories.getFirstSelectedOption().getAttribute("value"));
    }

    public String getCategoryName(){
        Select categories = new Select(categoriesDropDownList);
        return categories.getFirstSelectedOption().getText();
    }

    public RiaHomePage mainLogoClick() {
        mainLogo.click();
        return new RiaHomePage();
    }

    public RiaProductPage openFirstResultLink(){
        firstResultLink.click();

        return new RiaProductPage();
    }

    public boolean regionMatching(String region){
        String str;
        for (WebElement item : results){
            str = item.findElement(By.xpath("//li[@class = 'item-char view-location']")).getText();
            if (str.equals(region))
                return true;
        }
        return false;
    }

    public int getResultsCount(){
        return Integer.parseInt(resultsCount.getAttribute("innerHTML"));
    }
}
