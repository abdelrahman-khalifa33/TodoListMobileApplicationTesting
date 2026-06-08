package driver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    protected AndroidDriver MyApp;

    @BeforeMethod
    public void Setup() throws MalformedURLException
    {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setAppPackage("com.splendapps.splendo");
        options.setAppActivity("com.splendapps.splendo.activity.MainActivity");
        options.setDeviceName("IRLJ6HV87P9X4TUK");
        options.setNoReset(true);

        MyApp = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
        MyApp.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        MyApp.terminateApp("com.splendapps.splendo");
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        MyApp.activateApp("com.splendapps.splendo");
        HandleAdIfPresent();
    }

    public void HandleAdIfPresent()
    {
        By[] adCloseLocators = {
            By.id("com.google.android.gms:id/close_button"),
            By.xpath("//*[@content-desc='Close ad']"),
            By.xpath("//*[@content-desc='close_button']"),
            By.id("com.android.vending:id/close_button")
        };
        try {
            MyApp.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            for (By locator : adCloseLocators) {
                if (MyApp.findElements(locator).size() > 0) {
                    MyApp.findElement(locator).click();
                    return;
                }
            }
        } catch (Exception e) {
            // No ad present, continue
        } finally {
            MyApp.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        }
    }
}
