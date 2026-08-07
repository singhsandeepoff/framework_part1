//package framework.tests;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
//
//import java.time.Duration;
//import java.util.List;
//
//public class EndToEnd {
//    public static void main(String[] args) throws InterruptedException {
//
//        String prodName = "ADIDAS ORIGINAL";
//        WebDriver driver = new ChromeDriver();
//        driver.manage().window().maximize();
//
////        framework.pageobjects.LandingPage landingPage = new framework.pageobjects.LandingPage(driver);
//
//        driver.get("https://rahulshettyacademy.com/client");
//        driver.manage().deleteAllCookies();
//
//        // ---------------- DYNAMIC DATA GENERATION ----------------
//        long timestamp = System.currentTimeMillis();
//
//        // Dynamically generated fields
//        String dynamicFirstName = "User" + (timestamp % 10000);               // e.g., User5821
//        String dynamicLastName = "Test" + (timestamp % 1000);                // e.g., Test482
//        String dynamicEmail = "sandeep" + timestamp + "@gmail.com";          // e.g., sandeep171289123@gmail.com
//
//        // Generates a valid 10-digit mobile number starting with 9
//        String dynamicMobile = "9" + String.valueOf(timestamp).substring(4, 13);
//        driver.findElement(By.cssSelector("[class='text-reset']")).click();
//
//        // Fixed fields as requested
//        String fixedPassword = "Test@123";
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Login here")));
//
//        // Fill Dynamically Generated Fields
//        driver.findElement(By.id("firstName")).sendKeys(dynamicFirstName);
//        driver.findElement(By.id("lastName")).sendKeys(dynamicLastName);
//        WebElement email = driver.findElement(By.id("userEmail"));
//        email.sendKeys(dynamicEmail);
//        driver.findElement(By.id("userMobile")).sendKeys(dynamicMobile);
//
//        WebElement staticDropDown = driver.findElement(By.cssSelector("select[class*='custom-select']"));
//        Select dropdown = new Select(staticDropDown);
//        dropdown.selectByVisibleText("Engineer");
//        System.out.println(dropdown.getFirstSelectedOption().getText());
//
//        driver.findElement(By.cssSelector("input[value='Male']")).click();
//        driver.findElement(By.id("userPassword")).sendKeys(fixedPassword);
//        driver.findElement(By.id("confirmPassword")).sendKeys(fixedPassword);
//        driver.findElement(By.cssSelector("div div input[type='checkbox']")).click();
//        System.out.println(driver.findElement(By.cssSelector("div div input[type='checkbox']")).isSelected());
//
//        driver.findElement(By.id("login")).click();
//
//        Thread.sleep(2000);
//        // Assert.assertEquals(driver.findElement(By.cssSelector("h1[class='headcolor']")).getText(), "Account Created Successfully");
//        driver.findElement(By.cssSelector("section div div button[class*='btn-primary']")).click();
//
//        //   wait.until(ExpectedConditions.elementToBeClickable(By.id("Login"))).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1[class='login-title']")));
//
//        driver.findElement(By.id("userEmail")).sendKeys(dynamicEmail);
//        driver.findElement(By.id("userPassword")).sendKeys(fixedPassword);
//        driver.findElement(By.id("login")).click();
//
//        System.out.println("Registration & Login successful!");
//        System.out.println("User: " + dynamicFirstName + " " + dynamicLastName);
//        System.out.println("Email: " + dynamicEmail);
//        System.out.println("Mobile: " + dynamicMobile);
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
//        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
//        WebElement prod = products.stream().filter(product -> product.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(prodName)).findFirst().orElse(null);
//        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#toast-container")));
//
//        driver.findElement(By.cssSelector("ul li button[routerlink*='cart']")).click();
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cartSection h3")));
//        List<WebElement> cartProds = driver.findElements(By.cssSelector(".cartSection h3"));
//        Boolean match = cartProds.stream().anyMatch(cartProd -> cartProd.getText().equalsIgnoreCase(prodName));
//        Assert.assertTrue(match, "Product " + prodName + " was not found in the cart!");
//        //Assert.assertTrue(match);
//
//        driver.findElement(By.cssSelector(".totalRow button")).click();
//
////        String text = "India";
////        WebElement searchInput = driver.findElement(By.cssSelector(".form-group input[class*='text-validated']"));
////        searchInput.click();
////        searchInput.sendKeys(text);
//
////        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
//
//        Actions a = new Actions(driver);
//        a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build().perform();
//
////        Thread.sleep(2000);
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
//
//        driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
//        driver.findElement(By.cssSelector(".action__submit")).click();
//
////        List<WebElement> countryNames = driver.findElements(By.cssSelector(".ta-results button"));
////        WebElement selectedCountry = countryNames.stream().filter(countryName -> countryName.getText().trim().equalsIgnoreCase("India")).findFirst().orElse(null);
////        assert selectedCountry != null;
////        selectedCountry.click();
////        Thread.sleep(2000);
////        driver.findElement(By.cssSelector(".actions a[class*='action__submit']")).click();
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
//
//        String orderId = driver.findElement(By.cssSelector("tbody tr [class='ng-star-inserted'] td label")).getText().trim();
//        System.out.println(orderId);
//
//        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
//        Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
//
//        driver.close();
////        System.out.println(orderId.getText());
//
//    }
//}
