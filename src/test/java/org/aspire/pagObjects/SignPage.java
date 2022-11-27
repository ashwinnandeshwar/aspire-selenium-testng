package org.aspire.pagObjects;

import org.aspire.utilities.BrowserUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SignPage {

    private WebDriver webDriver;

    public SignPage(WebDriver driver){
        this.webDriver=driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//h1[contains(text(),'Sign in')]")
    private WebElement signInText;


    public boolean isSignIn(){
        return BrowserUtils.isUiExpectedCondition(webDriver, ExpectedConditions.visibilityOf(signInText),2);
    }

}
