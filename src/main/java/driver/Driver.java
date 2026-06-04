package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class Driver {

    static AppiumDriver driver;

    public Driver() throws IOException {
        appiumDriverSetup();
    }

    public void appiumDriverSetup() throws MalformedURLException {

        String appPackage = utils.Constants.ENVIRONMENT_NAME.equalsIgnoreCase("Staging")
                ? "com.todolist.app"
                : "com.todolist.app.dev";

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("platformName", "Android");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("deviceName", "YOUR_DEVICE_ID");
        caps.setCapability("appium:appPackage", appPackage);
        caps.setCapability("appium:appActivity", "com.todolist.app.MainActivity");
        caps.setCapability("newCommandTimeout", 3600);
        caps.setCapability("autoGrantPermissions", true);
        caps.setCapability("noReset", true);
        caps.setCapability("appium:autoLaunch", false);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);

        driver.executeScript("mobile: clearApp", Map.of("appId", appPackage));

        String[] runtimePermissions = {
                "android.permission.POST_NOTIFICATIONS"
        };
        for (String permission : runtimePermissions) {
            try {
                driver.executeScript("mobile: shell", Map.of(
                        "command", "pm",
                        "args", List.of("grant", appPackage, permission)
                ));
            } catch (Exception ignored) {}
        }

        String[] appOps = {
                "android:post_notifications"
        };
        for (String op : appOps) {
            try {
                driver.executeScript("mobile: shell", Map.of(
                        "command", "appops",
                        "args", List.of("set", appPackage, op, "allow")
                ));
            } catch (Exception ignored) {}
        }

        ((AndroidDriver) driver).activateApp(appPackage);

        dismissPermissionDialogs();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    private void dismissPermissionDialogs() {
        long deadline = System.currentTimeMillis() + 15_000;
        int misses = 0;
        while (System.currentTimeMillis() < deadline) {
            if (tryClickPermissionAllow()) {
                misses = 0;
                try { Thread.sleep(800); } catch (InterruptedException ignored) {}
            } else {
                if (++misses >= 6) break;
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            }
        }
    }

    private boolean tryClickPermissionAllow() {
        String[] ids = {
            "com.android.permissioncontroller:id/permission_allow_button",
            "com.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.android.permissioncontroller:id/permission_allow_always_button"
        };
        for (String id : ids) {
            try { driver.findElement(By.id(id)).click(); return true; }
            catch (Exception ignored) {}
        }
        String[] uaQueries = {
            "new UiSelector().textMatches(\"(?i)allow.*\")",
            "new UiSelector().textMatches(\"(?i).*while using.*\")",
            "new UiSelector().textMatches(\"(?i).*using the app.*\")"
        };
        for (String q : uaQueries) {
            try {
                driver.findElement(AppiumBy.androidUIAutomator(q)).click();
                return true;
            } catch (Exception ignored) {}
        }
        return false;
    }

    public AppiumDriver getDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
