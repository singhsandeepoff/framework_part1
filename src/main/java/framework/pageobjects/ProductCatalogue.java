package framework.pageobjects;

import framework.abstractcomponents.AbstractComponents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCatalogue extends AbstractComponents {

    WebDriver driver;

    public ProductCatalogue(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".mb-3")
    List<WebElement> products;

    @FindBy(css = ".ng-animating")
    WebElement spinner;

    By productsBy = By.cssSelector(".mb-3");
    By addToCart = By.cssSelector(".card-body button:last-of-type");
    By toastMessage = By.cssSelector("#toast-container");
    By spinnerBy = By.cssSelector(".ng-animating");

    public List<WebElement> getProductList() {
        waitForElementToAppear(productsBy);
        return products;
    }

    public WebElement getProductByName(String productName) {
        WebElement prod = getProductList().stream().filter(product -> product.findElement(By.cssSelector("b")).getText().trim().equalsIgnoreCase(productName)).findFirst().orElse(null);
        return prod;
    }

    public void addProductToCart(String productName) throws InterruptedException {
        WebElement prod = getProductByName(productName);
        if(prod ==null)
        {
            throw new RuntimeException("Product '" + productName + "' was not found on the page!");
        }
        prod.findElement(addToCart).click();
        waitForElementToAppear(toastMessage);
        try {
            waitForElementToAppear(spinnerBy);
        } catch (Exception e) {
            // If the spinner was too fast and didn't trigger, catch exception & proceed
        }
        waitForElementToDisappear(spinner);
    }
}
