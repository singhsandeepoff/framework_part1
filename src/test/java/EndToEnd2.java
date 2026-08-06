import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class EndToEnd2 {
    public static void main(String[] args) throws InterruptedException {

        String prodName = "ADIDAS ORIGINAL";
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        LandingPage landingPage = new LandingPage(driver);

        driver.get("https://rahulshettyacademy.com/client");
        driver.manage().deleteAllCookies();

        driver.findElement(By.id("userEmail")).sendKeys("lkmlkm@abc.com");
        driver.findElement(By.id("userPassword")).sendKeys("cA2z4J!7bNm3uwd");
        driver.findElement(By.id("login")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
        WebElement prod = products.stream().filter(product -> product.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(prodName)).findFirst().orElse(null);
        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#toast-container")));

        driver.findElement(By.cssSelector("ul li button[routerlink*='cart']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cartSection h3")));
        List<WebElement> cartProds = driver.findElements(By.cssSelector(".cartSection h3"));
        Boolean match = cartProds.stream().anyMatch(cartProd -> cartProd.getText().equalsIgnoreCase(prodName));
        Assert.assertTrue(match, "Product " + prodName + " was not found in the cart!");
        driver.findElement(By.cssSelector(".totalRow button")).click();

        Actions a = new Actions(driver);
        a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

        driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
        driver.findElement(By.cssSelector(".action__submit")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));

        String orderId = driver.findElement(By.cssSelector("tbody tr [class='ng-star-inserted'] td label")).getText().trim();
        System.out.println(orderId);

        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));

        driver.close();
    }
}


