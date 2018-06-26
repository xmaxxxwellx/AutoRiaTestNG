package com.epam.maxxxwell.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RiaProductPage {
    private WebDriver driver;

    @FindBy(xpath = "//div[@id = 'heading-cars']/div[@class = 'heading']/h1")
    WebElement productTitle;

    @FindBy(xpath = "//div[@id = 'description_v3']/dl/dd")
    WebElement productTypeDescription;

    @FindBy(xpath = "//div[@id = 'heading-cars']/div/h1")
    private WebElement productName;

    @FindBy(xpath = "//div[@id = 'showLeftBarView']/div/div[@class = 'price-seller']/span")
    private WebElement productPrice;

    public RiaProductPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getProductDescriptionText(){
        return productTypeDescription.getText();
    }

    public String getProductName(){
        return productName.getAttribute("innerHTML");
    }

    public int getProductPrice(){
        String str = productPrice.getAttribute("innerHTML");
        str = str.replaceAll("\\$|\\s", "");
        return Integer.parseInt(str);
    }

    public int getProductYear(){
        String title = productTitle.getText();
        String year = title.substring(title.length() - 4);
        return Integer.parseInt(year);
    }
}
