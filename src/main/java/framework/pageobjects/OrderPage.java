package framework.pageobjects;

import framework.abstractcomponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OrderPage extends AbstractComponents {

    WebDriver driver;

    public OrderPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css=".totalRow button")
    WebElement checkoutElement;

    @FindBy(css="tr td:nth-child(3)")
    private List<WebElement> productNames;

    public Boolean VerifyOrderDisplay(String prodName){
        Boolean match = productNames.stream().anyMatch(product -> product.getText().equalsIgnoreCase(prodName));
        System.out.println("Cart list size: " + productNames.size());
        System.out.println("Product Name: " + prodName);
        return match;
    }

}
