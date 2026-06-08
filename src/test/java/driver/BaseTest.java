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
        options.setPlatformVersion("16");
        options.setAutomationName("UiAutomator2");
        options.setAppPackage("com.splendapps.splendo");
        options.setAppActivity("com.splendapps.splendo.activity.MainActivity");
        options.setDeviceName("IRLJ6HV87P9X4TUK");
        options.setNoReset(true);

        MyApp = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
        MyApp.activateApp("com.splendapps.splendo");
        MyApp.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public void HandleAdIfPresent()
    {
        try {
            By closeBtn = By.id("close");
            if (MyApp.findElements(closeBtn).size() > 0) {
                MyApp.findElement(closeBtn).click();
            }
        } catch (Exception e) {
            // ignore if no ad
        }
    }
}
