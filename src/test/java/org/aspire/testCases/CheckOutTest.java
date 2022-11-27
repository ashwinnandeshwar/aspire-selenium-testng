package org.aspire.testCases;

import org.aspire.pagObjects.*;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class CheckOutTest extends BaseTest{

    HomePage homePage;
    LeftFilterSectionPage leftFilterSectionPage;
    SearchResultPage searchResultPage;
    ProductDetailPage productDetailPage;
    ShoppingCartPage shoppingCartPage;
    SignPage signPage;


    //Test Case 3
    @Test (priority = 1)
    public void checkOut() throws Exception {
        homePage=new HomePage(webDriver);
        leftFilterSectionPage=new LeftFilterSectionPage(webDriver);
        searchResultPage=new SearchResultPage(webDriver);
        productDetailPage=new ProductDetailPage(webDriver);
        shoppingCartPage=new ShoppingCartPage(webDriver);
        signPage=new SignPage(webDriver);

        homePage.selectCategory("Electronics");
        homePage.searchItem("iPhone 14");
        leftFilterSectionPage.enterPrice(25,50);
        searchResultPage.selectAnyRandomProduct();
        productDetailPage.addToCartButton();
        Assert.assertTrue(shoppingCartPage.isProductAddedToCart());
        shoppingCartPage.clickOnGoToCartButton();
        shoppingCartPage.clickOnProceedToCheckout();
        Assert.assertTrue(signPage.isSignIn(),"Sign in page is not displayed");

    }
}
