package framework.tests;

import framework.pageobjects.*;
import framework.testcomponents.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class FinalRunnerTest extends BaseTest {

    String productName = "IPHONE 13 PRO";

    @Test
    public void finalRunner() throws IOException, InterruptedException {

        ProductCatalogue productCatalogue = landingPage.loginApp("lkmlkm@abc.com", "cA2z4J!7bNm3uwd");
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
        CartPage cartPage = productCatalogue.goToCartPage();

        Boolean match = cartPage.VerifyProdDisplay(productName);
        Assert.assertTrue(match);
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("India");
        ResultPage resultPage = checkoutPage.submitOrder();

        String confirmMessage = resultPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
    }

    @Test(dependsOnMethods = {"finalRunner"})
    public void OrderHistoryTest() {
        // checking if IPHONE 13 PRO is listed in the order history page
        ProductCatalogue productCatalogue = landingPage.loginApp("lkmlkm@abc.com", "cA2z4J!7bNm3uwd");
        OrderPage ordersPage = productCatalogue.goToOrdersPage();
        Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));

    }
}


