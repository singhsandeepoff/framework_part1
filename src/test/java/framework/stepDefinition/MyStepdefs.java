package framework.stepDefinition;

import framework.pageobjects.*;
import framework.testcomponents.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.List;

public class MyStepdefs extends BaseTest {

    public LandingPage landingPage;
    public ProductCatalogue productCatalogue;
    public ResultPage resultPage;
    String confirmMessage;

    @Given("I landed on Ecommerce Page")
    public void iLandedOnEcommercePage() throws IOException {
        landingPage = launchApplication();
    }

    @Given("^Logged in with username (.+) and password (.+)$")
    public void loggedInWithUsernameNameAndPasswordPassword(String username, String password) throws Throwable {
        productCatalogue = landingPage.loginApp(username, password);
    }

    @When("^I add product (.+) to Cart$")
    public void iAddProductProductNameToCart(String productName) throws Throwable {
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
    }

    @And("^Checkout (.+) and submit the order$")
    public void checkoutProductNameAndSubmitTheOrder(String productName) throws Throwable {
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.VerifyProdDisplay(productName);
        Assert.assertTrue(match, "Product was not found in cart!");
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("India");
        resultPage = checkoutPage.submitOrder();
    }

    @Then("{string} message is displayed on ConfirmationPage")
    public void messageIsDisplayedOnConfirmationPage(String string) {
        confirmMessage = resultPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
    }

    @Then("{string} message is displayed")
    public void messageIsDisplayed(String string1) {
        Assert.assertEquals(string1, "Incorrect email or password.");
    }

    @After
    public void tearDownScenario() {
        tearDown();
    }

    @Override
    public void onFinish(ITestResult result) {

    }


}
