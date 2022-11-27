package org.aspire.pagObjects;

import org.aspire.utilities.BrowserUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

public class ProductDetailPage {

    private WebDriver webDriver;

    public ProductDetailPage(WebDriver driver){
        this.webDriver=driver;
        PageFactory.initElements(driver,this);
    }

//    @FindBy(xpath = "//input[@id='add-to-cart-button']")
    @FindBy(xpath = "//input[@value='Add to Cart']")
    private WebElement addToCartButton;


    public void addToCartButton() throws IOException, InterruptedException {
        BrowserUtils.clickElement(webDriver,addToCartButton);
    }

}
