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

public class Listeners extends BaseTest implements ITestListener {
    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReportObject();

    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
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
        extent.flush(); // Generates/updates index.html in /reports
    }

    @Override
    public void onFinish(ITestResult result) {

    }
}
