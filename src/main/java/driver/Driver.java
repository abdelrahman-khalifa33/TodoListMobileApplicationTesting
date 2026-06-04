package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Driver {

    static AndroidDriver driver;

    public Driver() throws IOException {
        appiumDriverSetup();
    }

    public void appiumDriverSetup() throws MalformedURLException {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setPlatformVersion("13");
        options.setAutomationName("UiAutomator2");
        options.setApp("Downloads/ApiDemos-release.apk");
        options.setDeviceName("emulator-5554");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);

        driver.activateApp("io.appium.android.apis");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public AndroidDriver getDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
