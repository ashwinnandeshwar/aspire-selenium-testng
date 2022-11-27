package org.aspire.pagObjects;

import org.aspire.utilities.BrowserUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;

public class ShoppingCartPage {

    private WebDriver webDriver;

    public ShoppingCartPage(WebDriver driver){
        this.webDriver=driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//div[@id='sw-atc-confirmation']//span[contains(text(),'Added to Cart')]")
    private WebElement addedToCartConfirmationMessage;

    @FindBy(xpath = "//div[@id='sw-atc-confirmation']//a[contains(text(),'Go to Cart')]")
    private WebElement goToCartButton;

    @FindBy(xpath = "//input[@value='Proceed to checkout']")
    private WebElement proceedToCheckoutButton;

    public boolean isProductAddedToCart(){
        return  BrowserUtils.isUiExpectedCondition(webDriver, ExpectedConditions.visibilityOf(addedToCartConfirmationMessage),2);
    }

    public void clickOnGoToCartButton() throws IOException, InterruptedException {
        BrowserUtils.clickElement(webDriver,goToCartButton);
    }

    public void clickOnProceedToCheckout() throws IOException, InterruptedException {
        BrowserUtils.clickElement(webDriver,proceedToCheckoutButton);

    }
}
