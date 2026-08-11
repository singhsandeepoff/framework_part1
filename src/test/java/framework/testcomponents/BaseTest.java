//package framework.testcomponents;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import framework.pageobjects.LandingPage;
//import org.apache.commons.io.FileUtils;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.safari.SafariDriver;
//import org.testng.ITestResult;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Properties;
//
//public abstract class BaseTest {
//
//    public static WebDriver driver;
//    public static LandingPage landingPage;
//
//    @Test
//    public static WebDriver initializeDriver() throws IOException {
//
//        //properties class
//        Properties prop = new Properties();
//        FileInputStream input = new FileInputStream(System.getProperty("user.dir") + "//src//main//java//framework//resources//GlobalData.properties");
//        prop.load(input);
//        String browserName = prop.getProperty("browser");
//
//        if (browserName.equalsIgnoreCase("chrome")) {
//            driver = new ChromeDriver();
//        } else if (browserName.equalsIgnoreCase("firefox")) {
//            driver = new FirefoxDriver();
//        } else if (browserName.equalsIgnoreCase("edge")) {
//            driver = new EdgeDriver();
//        } else if (browserName.equalsIgnoreCase("safari")) {
//            driver = new SafariDriver();
//        }
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//        driver.manage().window().maximize();
//        //driver.manage().deleteAllCookies();
//        return driver;
//    }
//
//    public List<HashMap<String, String>> getJasonDataToMap(String filePath) throws IOException {
//
//        //read json to String
//        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
//
//        //String to HashMap
//        ObjectMapper mapper = new ObjectMapper();
//        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
//        });
//        return data;
//    }
//
//    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
//        TakesScreenshot ts = (TakesScreenshot) driver;
//        File source = ts.getScreenshotAs(OutputType.FILE);
//        String filePath = System.getProperty("user.dir") + "/reports/" + testCaseName + ".png";
//        File file = new File(filePath);
//        FileUtils.copyFile(source, file);
//        return filePath;
//    }
//
//    @BeforeMethod(alwaysRun = true)
//    public static LandingPage launchApplication() throws IOException {
//        driver = initializeDriver();
//        landingPage = new LandingPage(driver);
//        landingPage.goTo();
//        return landingPage;
//    }
//
//    @AfterMethod(alwaysRun = true)
//    public void tearDown() {
//        driver.close();
//    }
//
//    public abstract void onFinish(ITestResult result);
//}


package framework.testcomponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import framework.pageobjects.LandingPage;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public abstract class BaseTest {

    // Removed 'static' to keep tests independent and parallel-safe
    public WebDriver driver;
    public LandingPage landingPage;

    public WebDriver initializeDriver() throws IOException {
        Properties prop = new Properties();
        String propertiesPath = System.getProperty("user.dir") + "/src/main/java/framework/resources/GlobalData.properties";

        try (FileInputStream input = new FileInputStream(propertiesPath)) {
            prop.load(input);
        }
        String browserName =  System.getProperty("browser")!=null ? System.getProperty("browser") :prop.getProperty("browser");
        // prop.getProperty("browser");

        if (browserName.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else if (browserName.equalsIgnoreCase("safari")) {
            driver = new SafariDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    public List<HashMap<String, String>> getJasonDataToMap(String filePath) throws IOException {
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String filePath = System.getProperty("user.dir") + "/reports/" + testCaseName + ".png";
        File file = new File(filePath);
        FileUtils.copyFile(source, file);
        return filePath;
    }

    @BeforeMethod(alwaysRun = true)
    public LandingPage launchApplication() throws IOException {
        driver = initializeDriver();
        landingPage = new LandingPage(driver);
        landingPage.goTo();
        return landingPage;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Quits browser session cleanly and kills driver process
        }
    }

    public abstract void onFinish(ITestResult result);
}