package framework.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features="src/test/java/framework/cucumber", glue="framework.stepDefinition", monochrome = true, tags = "@Regression", plugin = {"pretty", "html:target/cucumber.html", "json:target/cucumber.json"})

public class TestNGTestRunner extends AbstractTestNGCucumberTests {

}
