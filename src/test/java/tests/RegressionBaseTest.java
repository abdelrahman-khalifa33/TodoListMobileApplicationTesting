package tests;

import driver.Driver;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import pages.BasePage;

import java.io.IOException;

public class RegressionBaseTest {

    protected static AppiumDriver driver;
    protected SoftAssert softAssert;

    private static boolean driverReady = false;

    protected int backStepsAfterTest() { return 1; }

    protected int backStepsOverride = -1;

    @BeforeClass
    public synchronized void initDriver() {
        if (!driverReady) {
            try {
                driver = new Driver().getDriver();
            } catch (IOException e) {
                throw new RuntimeException("Failed to initialize Appium driver", e);
            }
            driverReady = true;
        }
    }

    @AfterSuite
    public synchronized void tearDownSuite() {
        if (!driverReady) return;
        Driver.quitDriver();
        driverReady = false;
    }

    @BeforeMethod
    public void beforeMethod() {
        softAssert = new SoftAssert();
        backStepsOverride = -1;
    }

    public void returnToHome() {
        int steps = backStepsOverride >= 0 ? backStepsOverride : backStepsAfterTest();
        new BasePage(driver).goBack(steps);
    }
}
