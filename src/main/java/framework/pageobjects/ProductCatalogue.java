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
//
//    @FindBy(css = "#toast-container")
//    WebElement spinner2;

    By productsBy = By.cssSelector(".mb-3");
    By addToCart = By.cssSelector(".card-body button:last-of-type");
    By toastMessage = By.cssSelector("#toast-container");
 //   By spinnerBy = By.cssSelector(".ng-animating");

    public List<WebElement> getProductList() {
        waitForElementToAppear(productsBy);
        return products;
    }

    public WebElement getProductByName(String productName) {
        WebElement prod = getProductList().stream().filter(product -> product.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(productName)).findFirst().orElse(null);
        return prod;
    }

    public void addProductToCart(String productName) throws InterruptedException {
        WebElement prod = getProductByName(productName);
        prod.findElement(addToCart).click();
        waitForElementToAppear(toastMessage);
        waitForElementToDisappear(spinner);
//        waitForElementToDisappear(spinner2);

//    public void addProductToCart(String productName) throws InterruptedException {
//        WebElement prod = getProductByName(productName);
//        if (prod != null) {
//            prod.findElement(addToCart).click();
//            waitForElementToAppear(toastMessage);
//            waitForElementToDisappear(spinner); // Passing By locator
//        } else {
//            System.out.println("Product " + productName + " was not found in the catalogue!");
//        }

    }

}
