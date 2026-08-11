package framework.pageobjects;

import framework.abstractcomponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends AbstractComponents {

    WebDriver driver;

    public LandingPage(WebDriver driver) {
        super(driver);
        //initialization
        this.driver = driver;
        PageFactory.initElements(driver, this);  //to pass the driver to PageFactory method
    }

    //WebElement userEmail = driver.findElement(By.id("userEmail"));
    //PageFactory
    @FindBy(id = "userEmail")
    WebElement userEmail;

    @FindBy(id = "userPassword")
    WebElement userPassword;

    @FindBy(id = "login")
    WebElement submit;

    @FindBy(css = "[class*='flyInOut']")
    WebElement errorMessage;

    public ProductCatalogue loginApp(String email, String password) {
        userEmail.sendKeys(email);
        userPassword.sendKeys(password);
        waitForElementToClickable(submit);
        submit.click();
        ProductCatalogue productCatalogue = new ProductCatalogue(driver);
        return productCatalogue;
    }

    public String getErrorMessage() {
        waitForElementToAppear(errorMessage);
        return errorMessage.getText();
    }

    public void goTo() {
        driver.get("https://rahulshettyacademy.com/client");
    }
}
