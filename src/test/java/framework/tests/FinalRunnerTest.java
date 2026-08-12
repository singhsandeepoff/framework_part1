package framework.tests;

import com.aventstack.extentreports.ExtentTest;
import framework.pageobjects.*;
import framework.testcomponents.BaseTest;
import framework.testcomponents.Listeners;
import framework.testcomponents.TestCounter;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FinalRunnerTest extends BaseTest {

    String productName = "ADIDAS ORIGINAL";

    @Test(dataProvider = "getData", groups = {"Purchase"})
    public void finalRunner(HashMap<String, String> input) throws IOException, InterruptedException {

        String datasetLabel = " [" + input.get("productName") + "]";

        // Step 1: User Login
        ExtentTest t1 = Listeners.getExtentReports().createTest(TestCounter.getNextId() + " - User Login" + datasetLabel);
        t1.info("Logging into application with email: " + input.get("email"));
        ProductCatalogue productCatalogue = landingPage.loginApp(input.get("email"), input.get("password"));
        t1.pass("Login Successful");

        // Step 2: Search & Add Product
        ExtentTest t2 = Listeners.getExtentReports().createTest(TestCounter.getNextId() + " - Add Product To Cart" + datasetLabel);
        t2.info("Fetching products and adding: " + input.get("productName"));
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(input.get("productName"));
        t2.pass("Product added to cart successfully");

        // Step 3: Cart Display Verification
        ExtentTest t3 = Listeners.getExtentReports().createTest(TestCounter.getNextId() + " - Verify Cart Display" + datasetLabel);
        t3.info("Navigating to Cart Page");
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.VerifyProdDisplay(input.get("productName"));
        Assert.assertTrue(match, "Product was not found in cart!");
        t3.pass("Product verified in Cart");

        // Step 4: Checkout & Country Selection
        ExtentTest t4 = Listeners.getExtentReports().createTest(TestCounter.getNextId() + " - Select Country" + datasetLabel);
        t4.info("Selecting country: India");
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("India");
        t4.pass("Country selected");

        // Step 5: Submit Order & Confirmation
        ExtentTest t5 = Listeners.getExtentReports().createTest(TestCounter.getNextId() + " - Submit Order" + datasetLabel);
        t5.info("Submitting order");

        ResultPage resultPage = checkoutPage.submitOrder();
        String confirmMessage = resultPage.getConfirmationMessage();
        String expectedMessage = "Thankyou for the order.";

        // Compare ignoring case
        if (confirmMessage.equalsIgnoreCase(expectedMessage)) {
            t5.pass("Order submitted successfully: " + confirmMessage);
        } else {
            // Capture screenshot manually on soft assertion failure
            try {
                String screenshotPath = getScreenshot("SubmitOrderFailure", driver);
                t5.fail("Incorrect confirm message: " + confirmMessage)
                        .addScreenCaptureFromPath(screenshotPath, "Submit Order Failure Screenshot");
            } catch (IOException e) {
                t5.fail("Incorrect confirm message: " + confirmMessage + " (Failed to capture screenshot)");
            }

            // Fail the TestNG test explicitly so build tools know the test failed
            Assert.fail("Confirmation message mismatch! Expected: " + expectedMessage + " but got: " + confirmMessage);
        }
    }

    @Test(dependsOnMethods = {"finalRunner"})
    public void OrderHistoryTest() {
        ExtentTest t6 = Listeners.getExtentReports().createTest(TestCounter.getNextId() + " - Order History Verification");
        t6.info("Logging in to check Order History");
        ProductCatalogue productCatalogue = landingPage.loginApp("lkmlkm@abc.com", "cA2z4J!7bNm3uwd");

        OrderPage ordersPage = productCatalogue.goToOrdersPage();
        Boolean orderMatch = ordersPage.VerifyOrderDisplay(productName);
        Assert.assertTrue(orderMatch, "Product " + productName + " was not found in order history!");
        t6.pass("Order verified in Order History");
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJasonDataToMap(System.getProperty("user.dir") + "//src//test//java//framework//data//PurchaseOrder.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }

    @Override
    public void onFinish(ITestResult result) {
    }
}

//package framework.tests;
//
//import framework.pageobjects.*;
//import framework.testcomponents.BaseTest;
//import framework.testcomponents.Listeners;
//import org.openqa.selenium.WebElement;
//import org.testng.Assert;
//import org.testng.ITestResult;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//
//public class FinalRunnerTest extends BaseTest {
//
//    String productName = "ADIDAS ORIGINAL";
//
//    @Test(dataProvider = "getData", groups = {"Purchase"})
//    public void finalRunner(HashMap<String, String> input) throws IOException, InterruptedException {
//
//        // Step 1: User Authentication
//        Listeners.getTest().createNode("1. User Login")
//                .info("Logging into application with email: " + input.get("email"));
//        ProductCatalogue productCatalogue = landingPage.loginApp(input.get("email"), input.get("password"));
//
//        // Node 2: Catalog Search & Add to Cart
//        Listeners.getTest().createNode("2. Add Product To Cart")
//                .info("Fetching product list and selecting: " + input.get("productName"));
//        List<WebElement> products = productCatalogue.getProductList();
//        productCatalogue.addProductToCart(input.get("productName"));
//
//        // Node 3: Cart Display Verification
//        Listeners.getTest().createNode("3. Verify Cart Display")
//                .info("Navigating to Cart Page and validating product presence");
//        CartPage cartPage = productCatalogue.goToCartPage();
//        Boolean match = cartPage.VerifyProdDisplay(input.get("productName"));
//        Assert.assertTrue(match, "Product was not found in the cart!");
//        Listeners.getTest().info("Product successfully validated in Cart");
//
//        // Node 4: Checkout & Country Selection
//        Listeners.getTest().createNode("4. Select Shipping Details")
//                .info("Proceeding to Checkout page and selecting country: India");
//        CheckoutPage checkoutPage = cartPage.goToCheckout();
//        Listeners.getTest().info("Selecting shipping country: India");
//        checkoutPage.selectCountry("India");
//
//        // Node 5: Submit Order & Verify Confirmation
//        Listeners.getTest().createNode("5. Submit Order & Verify Confirmation")
//                .info("Placing order and verifying confirmation banner");
//        ResultPage resultPage = checkoutPage.submitOrder();
//        String confirmMessage = resultPage.getConfirmationMessage();
//        Listeners.getTest().info("Order Confirmation Message received: " + confirmMessage);
//        Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
//    }
//
//    @Test(dependsOnMethods = {"finalRunner"})
//    public void OrderHistoryTest() {
//        // Node 1: Login & Navigate
//        Listeners.getTest().createNode("1. User Login & Orders Navigation")
//                .info("Logging in to check Order History for user: lkmlkm@abc.com");
//        ProductCatalogue productCatalogue = landingPage.loginApp("lkmlkm@abc.com", "cA2z4J!7bNm3uwd");
//
//        // Node 2: Order History Validation
//        Listeners.getTest().createNode("2. Order History Verification")
//                .info("Verifying order history for product: " + productName);OrderPage ordersPage = productCatalogue.goToOrdersPage();
//
//        Listeners.getTest().info("Verifying order history for product: " + productName);
//        Boolean orderMatch = ordersPage.VerifyOrderDisplay(productName);
//        Assert.assertTrue(orderMatch, "Product " + productName + " was not found in order history!");
//        Listeners.getTest().info("Product found in Order History successfully");
//    }
//    //Extent Reports
//
//    @DataProvider
//    public Object[][] getData() throws IOException {
//        List<HashMap<String, String>> data = getJasonDataToMap(System.getProperty("user.dir") + "//src//test//java//framework//data//PurchaseOrder.json");
//        return new Object[][]{{data.get(0)}, {data.get(1)}};
//    }
//
//    @Override
//    public void onFinish(ITestResult result) {
//
//    }
//}
//
