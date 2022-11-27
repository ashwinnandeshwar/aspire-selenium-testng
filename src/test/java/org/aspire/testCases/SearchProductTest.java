package org.aspire.testCases;

import org.aspire.pagObjects.HomePage;
import org.aspire.pagObjects.LeftFilterSectionPage;
import org.aspire.pagObjects.SearchResultPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class SearchProductTest extends BaseTest{

    HomePage homePage;
    LeftFilterSectionPage leftFilterSectionPage;
    SearchResultPage searchResultPage;

    Map<String, Double> productsBeforeSort = new LinkedHashMap<String, Double>();


    //Test Case 1
    @Test (priority = 2)
    public void searchProduct() throws Exception {
        homePage=new HomePage(webDriver);
        leftFilterSectionPage=new LeftFilterSectionPage(webDriver);
        searchResultPage=new SearchResultPage(webDriver);

        homePage.selectCategory("Electronics");
        homePage.searchItem("iPhone 14");
        leftFilterSectionPage.selectBrand("ZAGG");
        leftFilterSectionPage.enterPrice(10,30);
        Assert.assertTrue(searchResultPage.verifyProductsWithinPriceRange(10,30),"Products are not within price range");
    }

    //Test Case 2
    @Test (priority = 3)
    public void sortProducts() throws Exception {
        homePage=new HomePage(webDriver);
        leftFilterSectionPage=new LeftFilterSectionPage(webDriver);
        searchResultPage=new SearchResultPage(webDriver);

        homePage.selectCategory("Electronics");
        homePage.searchItem("iPhone 14");
        leftFilterSectionPage.enterPrice(25,50);
        productsBeforeSort=searchResultPage.getSearchedProducts();
        searchResultPage.selectSortByOption("Price: High to Low");
        Assert.assertTrue(searchResultPage.verifyProductsSorted(productsBeforeSort),"Products are not sorted");
    }
}
