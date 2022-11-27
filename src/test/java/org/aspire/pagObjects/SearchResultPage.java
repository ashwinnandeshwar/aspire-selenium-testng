package org.aspire.pagObjects;

import org.aspire.utilities.BrowserUtils;
import org.aspire.utilities.CommonUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SearchResultPage {

    private WebDriver webDriver;

    By lastPageBy = By.xpath("//span[@data-component-type='s-search-results']//span[@class='s-pagination-strip']//span[contains(text(),'Next') and @aria-disabled='true']");

    @FindBy(xpath = "//span[@data-component-type='s-search-results']//span[@class='s-pagination-strip']//a[contains(text(),'Next')]")
    private WebElement nextPage;

    @FindBy(xpath = "//span[@aria-label='Sort by:']")
    private WebElement sortByOptionDropdown;

    @FindBy(xpath = "//span[@data-component-type='s-search-results']//span[contains(text(),'RESULT')]")
    private WebElement resultText;


    public SearchResultPage(WebDriver driver) {
        this.webDriver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectSortByOption(String sortOption) throws Exception {
        BrowserUtils.clickElement(webDriver, sortByOptionDropdown);
        BrowserUtils.clickElement(webDriver, webDriver.findElement(By.xpath("//li[@role='option']/following::a[contains(text(),'" + sortOption + "')]")));
        BrowserUtils.isUiExpectedCondition(webDriver, ExpectedConditions.visibilityOf(resultText),2);
    }



    // function to get items per page
    public  Map<String,Double> perPageItem(){
        Map<String,Double> productMapPerPage=new LinkedHashMap<>();
        String productId;
        String price;
        List<WebElement> elements = webDriver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));

        for (int i = 0; i < elements.size(); i++) {
            productId = BrowserUtils.getAttributeValue(webDriver, elements.get(i), "data-asin");
            List<WebElement> priceEle = webDriver.findElements(By.xpath("//div[@data-asin='" + productId + "']//span[@class='a-price']/span[@class='a-offscreen']"));

            if (priceEle != null && !priceEle.isEmpty()) {
                price = priceEle.get(0).getAttribute("innerHTML");
                // System.out.println(productId + " price is " + price);
                String priceWithoutCurrencySymbol = CommonUtils.removeSpecialCharFromString(price, "$");
                Double priceInDoubleType = CommonUtils.convertStringToDecimal(priceWithoutCurrencySymbol);
                productMapPerPage.put(productId, priceInDoubleType);
            }
        }
        return productMapPerPage;
    }
    public Map<String, Double> getSearchedProducts() throws IOException, InterruptedException {
        Map<String, Double> productMap = new LinkedHashMap<String, Double>();
        int totalPages = 1;
        int page = 1;

        List<WebElement> totalPagesEle = webDriver.findElements(By.xpath("//span[@data-component-type='s-search-results']//span[@class='s-pagination-strip']//a[contains(text(),'Next')]/preceding-sibling::*[1]"));

        if (totalPagesEle != null && !totalPagesEle.isEmpty()) {
            totalPages = Integer.valueOf(totalPagesEle.get(0).getAttribute("innerHTML"));
        }
        System.out.println("Total pages: " + totalPages);
        while (page <= totalPages) {
            productMap=perPageItem();
            System.out.println("Page "+page+" has " + productMap.size() +" products");
            if (page==totalPages) {
                break;
            } else {
                BrowserUtils.clickElement(webDriver, nextPage);
            }
            page++;
        }
        return productMap;
    }

    public boolean verifyProductsWithinPriceRange(int min,int max) throws IOException, InterruptedException {
        Map<String, Double> productMap = getSearchedProducts();
//                Map<String,Double>productMap=getProductsByRec(1);
        for (Map.Entry<String, Double> entry : productMap.entrySet()) {
            if (entry.getValue() <= min && entry.getValue() >= max) {
                System.out.println("Product = " + entry.getKey() + ", Price = " + entry.getValue());
                return false;
            }
        }
        return true;
    }


    public boolean verifyProductsSorted(Map<String, Double> prodBeforeSort) throws Exception {
        boolean isSorted=false;
        Map<String, Double> productsFromWeb = perPageItem();

//        Map<String,Double>productsFromWeb=perPageItem();//getProductsByRec(1);
        Map<String, Double> sortedProducts;
        sortedProducts = prodBeforeSort.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

//        System.out.println("+++++++++++++++++before sort+++++++++++++++++++++++");
//        for (Map.Entry<String, Double> entry : productsFromWeb.entrySet()) {
//            System.out.println("Product = " + entry.getKey() + ", Price = " + entry.getValue());
//        }
//
//        System.out.println("+++++++++++++++++After sort+++++++++++++++++++++++");
//        for (Map.Entry<String, Double> entry : sortedProducts.entrySet()) {
//            System.out.println("Product = " + entry.getKey() + ", Price = " + entry.getValue());
//        }

        List<String> productsFromWebKeyList = new LinkedList<>(productsFromWeb.keySet());
        List<String> sortedProductsKeyList = new LinkedList<>(sortedProducts.keySet());

        for (int i = 0; i < productsFromWebKeyList.size()-1; i++) {
            if (sortedProductsKeyList.get(i).equals(productsFromWebKeyList.get(i))) {
                isSorted= true;
            } else {
                isSorted= false;
            }
        }
        return isSorted;
    }



    public void selectAnyRandomProduct() throws IOException, InterruptedException {
        int randomNum = CommonUtils.getRandomNumber();

        WebElement prodImage=webDriver.findElement(By.xpath("//div[@cel_widget_id='MAIN-SEARCH_RESULTS-"+randomNum+"']//span[@data-component-type='s-product-image']//a"));
        BrowserUtils.clickElement(webDriver,prodImage);
    }


    // method is not in use, trying to find products by using recursion
    public  Map<String, Double> getProductsByRec(int page) throws IOException, InterruptedException {
        Map<String,Double> productMap=new LinkedHashMap<>();
        int totalPages=1;

        List<WebElement>  totalPagesEle = webDriver.findElements(By.xpath("//span[@data-component-type='s-search-results']//span[@class='s-pagination-strip']//a[contains(text(),'Next')]/preceding-sibling::*[1]"));

        if(totalPagesEle!=null&&!totalPagesEle.isEmpty()){
            totalPages= Integer.valueOf(totalPagesEle.get(0).getAttribute("innerHTML"));
            System.out.println("Total pages: "+totalPages);
        }

        productMap=perPageItem();

        if(page==totalPages){
            return productMap;
        }else{
            BrowserUtils.clickElement(webDriver, nextPage);
            return getProductsByRec(page+1);
        }
    }

}
