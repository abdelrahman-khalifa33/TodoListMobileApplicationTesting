package tests;

import driver.Driver;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

public class BaseTest {

    protected AppiumDriver driver;
    protected SoftAssert softAssert;

    @BeforeClass
    public void setUp() throws Exception {
        driver = new Driver().getDriver();
    }

    @BeforeMethod
    public void beforeMethod() {
        softAssert = new SoftAssert();
    }

    @AfterClass
    public void tearDown() {
        Driver.quitDriver();
    }
}
