package org.aspire.pagObjects;

import org.aspire.utilities.BrowserUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private WebDriver webDriver;

    public HomePage(WebDriver driver){
        this.webDriver=driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//*[@id='searchDropdownBox']")
    private WebElement selectCategoryDropdown;

    @FindBy(xpath = "//input[@id='twotabsearchtextbox']")
    private WebElement searchItemTextBox;

    @FindBy(xpath = "//h1[contains(text(),' Create a Submission ')]")
    private WebElement searchButton;


    public void selectCategory(String category) throws Exception {
        BrowserUtils.selectOptionFromSelectList(selectCategoryDropdown,category);
    }

    public void searchItem(String item) throws Exception {
        BrowserUtils.sendKeysToWebElement(webDriver,searchItemTextBox,item);
        BrowserUtils.keyPress(webDriver,Keys.ENTER);
        BrowserUtils.waitForPageLoad(webDriver,5);
    }
}
