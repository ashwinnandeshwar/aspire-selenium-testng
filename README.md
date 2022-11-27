# aspire-selenium-testng
  Code Challenge

# Instructions:

1. Problem statement: Acceptance criteria(search,sort and checkout ) for https://www.amazon.com
2. Solution is implemented using java 11,selenium, WebDriverManager,tesng, maven using Intellj IDE on window System.
3. By default scripts will run on chrome browser. Browser can be parameterized in testng.xml file. Supported browsers: chrome, edge, firefox and safari
4. BaseTest.java contains setup() to initialise browser and teardown to close the browser
5. SearchProductTest.java implemented Test case 1(searchProduct) and Test case 2(sortProductByPrice)
6. CheckoutTest.Java implemented Test case 3(checkout).
7.  Report is available on path ".\target\surefire-reports\emailable-report.html"

Steps to execute:
1. Open command prompt
2. Navigate to project path
3. Run "mvn clean test"

Above maven command will trigger the automation script.Once the execution finish, report will generate on path mentioned in point 7.</br>
Note: 
1. Old Report will be replaced by new.</br>
2. Test case 2(sorted by price) will fail as sorting filter is not working on amazon website.</br> 
