package framework.tests;

import framework.pageobjects.CartPage;
import framework.pageobjects.ProductCatalogue;
import framework.testcomponents.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class ErrorValidationsTest extends BaseTest {

    @Test(groups = {"ErrorHandling"})
    public void LoginErrorValidation() throws IOException, InterruptedException {

        String productName = "IPHONE 13 PRO";
        ProductCatalogue productCatalogue = landingPage.loginApp("lkmlkm@abc.com", "cA2z4J!7bNmwd");
        Assert.assertEquals(landingPage.getErrorMessage(), "Incorrect email or password.");

    }

    @Test
    public void ProductErrorValidation() throws IOException, InterruptedException {

        String productName = "IPHONE 13 PRO";
        ProductCatalogue productCatalogue = landingPage.loginApp("nnn@abc.com", "Matrix@303");
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.VerifyProdDisplay("IPHONE 15");
        Assert.assertFalse(match);
    }

}

