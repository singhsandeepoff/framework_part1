package framework.testcomponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import framework.resources.ExtentReporterNG;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.HashMap;

public class Listeners extends BaseTest implements ITestListener {
    ExtentTest test;
    static ExtentReports extent = ExtentReporterNG.getReportObject();

    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // Public getter so test methods can append step-by-step logs
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    // Inside Listeners.java
    public static ExtentReports getExtentReports() {
        return extent;
    }


    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Object[] params = result.getParameters();

        // If test uses DataProvider, append the dataset identifier (e.g., product name or email)
        if (params.length > 0 && params[0] instanceof HashMap) {
            HashMap<String, String> input = (HashMap<String, String>) params[0];
            testName += " - " + input.get("productName");
        }
        test = extent.createTest(testName);
        extentTest.set(test); //unique thread id
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // 1. Clean Error Message (Only get the exception message, not full stack trace)
        String cleanErrorMessage = result.getThrowable().getMessage();
        extentTest.get().fail("<b>Failure Reason:</b> " + cleanErrorMessage);

        // 2. Get active driver instance safely
        WebDriver driverInstance = null;
        try {
            driverInstance = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. Take Screenshot and Attach
        if (driverInstance != null) {
            try {
                String filePath = getScreenshot(result.getMethod().getMethodName(), driverInstance);

                // Attach using absolute file path directly to avoid broken relative links
                extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        //if skipped
        extentTest.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush(); // REQUIRED to write index.html to disk
        }
    }

    @Override
    public void onFinish(ITestResult result) {

    }
}
