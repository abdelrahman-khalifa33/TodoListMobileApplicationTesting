//package tests;
//
//import driver.BaseTest;
//import io.appium.java_client.android.AndroidDriver;
//import org.testng.annotations.AfterSuite;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeMethod;
//import org.testng.asserts.SoftAssert;
//import pages.BasePage;
//
//import java.io.IOException;
//
//public class RegressionBaseTest {
//
//    protected static AndroidDriver driver;
//    protected SoftAssert softAssert;
//
//    private static boolean driverReady = false;
//
//    protected int backStepsAfterTest() { return 1; }
//
//    protected int backStepsOverride = -1;
//
//    @BeforeClass
//    public synchronized void initDriver() {
//        if (!driverReady) {
//            try {
//                driver = new BaseTest();
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to initialize Appium driver", e);
//            }
//            driverReady = true;
//        }
//    }
//
//    @AfterSuite
//    public synchronized void tearDownSuite() {
//        if (!driverReady) return;
//        BaseTest.quitDriver();
//        driverReady = false;
//    }
//
//    @BeforeMethod
//    public void beforeMethod() {
//        softAssert = new SoftAssert();
//        backStepsOverride = -1;
//    }
//
//    public void returnToHome() {
//        int steps = backStepsOverride >= 0 ? backStepsOverride : backStepsAfterTest();
//        new BasePage(driver).goBack(steps);
//    }
//}
