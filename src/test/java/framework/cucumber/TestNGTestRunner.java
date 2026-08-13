package framework.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features="src/test/java/framework/cucumber", glue="framework.stepDefinition", monochrome = true, tags = "@Regression", plugin = {"html:target/cucumber.html"})

public class TestNGTestRunner extends AbstractTestNGCucumberTests {



}
