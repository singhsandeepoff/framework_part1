package framework.tests;

import framework.pageobjects.*;
import framework.testcomponents.BaseTest;
import framework.testcomponents.Listeners;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class FinalRunnerTest extends BaseTest {

    String productName = "ADIDAS ORIGINAL";

    @Test(dataProvider = "getData", groups = {"Purchase"})
    public void finalRunner(HashMap<String, String> input) throws IOException, InterruptedException {

        // Step 1: Login
        Listeners.getTest().info("Logging into application with email: " + input.get("email"));
        ProductCatalogue productCatalogue = landingPage.loginApp(input.get("email"), input.get("password"));

        // Step 2: Fetch Products & Add Selected Product to Cart
        Listeners.getTest().info("Fetching product list and searching for: " + input.get("productName"));
        List<WebElement> products = productCatalogue.getProductList();

        Listeners.getTest().info("Clicking 'Add To Cart' for: " + input.get("productName"));
        productCatalogue.addProductToCart(input.get("productName"));

        // Step 3: Navigate to Cart & Verify Product
        Listeners.getTest().info("Navigating to Cart Page and validating item presence");
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.VerifyProdDisplay(input.get("productName"));
        Assert.assertTrue(match, "Product was not found in the cart!");
        Listeners.getTest().info("Product successfully validated in Cart");

        // Step 4: Proceed to Checkout & Select Country
        Listeners.getTest().info("Proceeding to Checkout page");
        CheckoutPage checkoutPage = cartPage.goToCheckout();

        Listeners.getTest().info("Selecting shipping country: India");
        checkoutPage.selectCountry("India");

        // Step 5: Submit Order
        Listeners.getTest().info("Submitting order");
        ResultPage resultPage = checkoutPage.submitOrder();

        // Step 6: Confirmation Verification
        String confirmMessage = resultPage.getConfirmationMessage();
        Listeners.getTest().info("Order Confirmation Message received: " + confirmMessage);
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
    }

    @Test(dependsOnMethods = {"finalRunner"})
    public void OrderHistoryTest() {
        // checking if IPHONE 13 PRO is listed in the order history page
        Listeners.getTest().info("Logging in to check Order History for user: lkmlkm@abc.com");
        ProductCatalogue productCatalogue = landingPage.loginApp("lkmlkm@abc.com", "cA2z4J!7bNm3uwd");

        Listeners.getTest().info("Navigating to My Orders page");
        OrderPage ordersPage = productCatalogue.goToOrdersPage();

        Listeners.getTest().info("Verifying order history for product: " + productName);
        Boolean orderMatch = ordersPage.VerifyOrderDisplay(productName);
        Assert.assertTrue(orderMatch, "Product " + productName + " was not found in order history!");
        Listeners.getTest().info("Product found in Order History successfully");
    }
    //Extent Reports

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJasonDataToMap(System.getProperty("user.dir") + "//src//test//java//framework//data//PurchaseOrder.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }

    @Override
    public void onFinish(ITestResult result) {

    }
}

