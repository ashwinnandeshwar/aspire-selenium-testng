package org.aspire.pagObjects;

import org.aspire.utilities.BrowserUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

public class LeftFilterSectionPage {

    private WebDriver webDriver;

    public LeftFilterSectionPage(WebDriver driver){
        this.webDriver=driver;
        PageFactory.initElements(driver,this);
    }


    @FindBy(xpath = "//*[@id='searchDropdownBox']")
    private WebElement selectBrandCheckBox;

    @FindBy(xpath = "//div[@id='priceRefinements']//input[@id='low-price']")
    private WebElement minPriceTextBox;

    @FindBy(xpath = "//div[@id='priceRefinements']//input[@id='high-price']")
    private WebElement maxPriceTextBox;

    @FindBy(xpath = "//div[@id='priceRefinements']//span[@class='a-button-text' and contains(text(),'Go')]/preceding-sibling::input")
    private WebElement goPriceApplyButton;



    public void selectBrand(String brandName) throws IOException, InterruptedException {
        BrowserUtils.clickElement(webDriver, webDriver.findElement(By.xpath("//div[@id='brandsRefinements']//li[@aria-label='"+brandName+"']//a")));
        BrowserUtils.waitForPageLoad(webDriver,5);
    }

    public void enterPrice(int min, int max) throws Exception {
        String mi = String.valueOf(min);
        String ma= String.valueOf(max);

        BrowserUtils.sendKeysToWebElement(webDriver,minPriceTextBox,mi);
        BrowserUtils.sendKeysToWebElement(webDriver,maxPriceTextBox,ma);
        BrowserUtils.waitForPageLoad(webDriver,5);

        BrowserUtils.waitAndClickElementUsingJS(webDriver,goPriceApplyButton);
//        goPriceApplyButton.click();

    }
}
