package com.epam.maxxxwell.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RiaLoginPage {
    private WebDriver driver;

    @FindBy(xpath = "//iframe[@id = 'login_frame']")
    private WebElement loginFrame;

    @FindBy(id = "emailloginform-email")
    private WebElement loginInput;

    @FindBy(id = "emailloginform-password")
    private WebElement passwordInput;

    @FindBy(xpath = "//form[@id = 'login-form']/div[@class='login-link']/button")
    private WebElement submitButton;

    public RiaLoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public RiaHomePage rightValidation(){
        driver.switchTo().frame(loginFrame);

        loginInput.sendKeys("0687532848");
        passwordInput.sendKeys("testtest");
        submitButton.submit();

        return PageFactory.initElements(driver, RiaHomePage.class);
    }
}
