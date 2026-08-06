import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartPage extends AbstractComponents{

    WebDriver driver;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css=".totalRow button")
    WebElement checkoutElement;

    @FindBy(css=".cartSection h3")
    private List<WebElement> productTitles;

    public Boolean VerifyProdDisplay(String prodName){
        Boolean match = productTitles.stream().anyMatch(productTitle -> productTitle.getText().equalsIgnoreCase(prodName));
        System.out.println("Cart list size: " + productTitles.size());
        System.out.println("Product Name: " + prodName);
        return match;
    }

    public CheckoutPage goToCheckout()
    {
        checkoutElement.click();
        return new CheckoutPage(driver);
    }
}
