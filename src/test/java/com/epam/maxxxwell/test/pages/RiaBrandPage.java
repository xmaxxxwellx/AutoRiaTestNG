package com.epam.maxxxwell.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class RiaBrandPage {
    private WebDriver driver;

    @FindBy(xpath = "//div[@id = 'catalogContent']/section//div[@class = 'standart-view m-view result-explore']/section")
    private List<WebElement> results;

    public RiaBrandPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public RiaProductPage getProductByLinkNumber (int linkNumber){
        results.get(linkNumber - 1).findElement(By.xpath("//div[@class = 'item ticket-title']/a"))
                .click();
        return PageFactory.initElements(driver, RiaProductPage.class);
    }
}
