package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class BasePage {

    static protected AndroidDriver driver;
    protected WebDriverWait wait;

    public BasePage(AndroidDriver driver) {
        BasePage.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    protected void click(By element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        driver.findElement(element).click();
    }

    protected void type(By element, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        driver.findElement(element).clear();
        driver.findElement(element).sendKeys(text);
    }

    protected String getText(By locator) {
        return find(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void visibilityWait(By element) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    protected void clickElement(By element) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        driver.findElement(element).click();
    }

    protected void sendText(By element, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        driver.findElement(element).sendKeys(text);
    }

    protected void clickElementNoWait(By element) {
        driver.findElement(element).click();
    }

    public void clearAndType(By locator, String value) {
        var el = driver.findElement(locator);
        el.clear();
        el.sendKeys(value);
    }

    protected void enterOTP(String otp) {
        if (!(driver instanceof AndroidDriver)) {
            throw new IllegalStateException("Driver is not AndroidDriver, cannot enter OTP");
        }
        AndroidDriver androidDriver = (AndroidDriver) driver;
        for (char digit : otp.toCharArray()) {
            switch (digit) {
                case '0': androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_0)); break;
                case '1': androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_1)); break;
                case '2': androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_2)); break;
                case '3': androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_3)); break;
                case '4': androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_4)); break;
                case '5': androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_5)); break;
                case '6': androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_6)); break;
                case '7': androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_7)); break;
                case '8': androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_8)); break;
                case '9': androidDriver.pressKey(new KeyEvent(AndroidKey.DIGIT_9)); break;
                default: throw new IllegalArgumentException("Invalid OTP digit: " + digit);
            }
        }
    }

    @Step("-Go back")
    public BasePage goBack(int iterations) {
        for (int i = 1; i <= iterations; i++) {
            driver.navigate().back();
        }
        return this;
    }

    public void scrollDownToEnd() {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollToEnd(3)"
        ));
    }

    public void scrollToText(String visibleText) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().textContains(\"" + visibleText + "\"))"
        ));
    }

    public void scrollToElementByDescFast(String desc) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".flingForward()" +
                        ".scrollIntoView(new UiSelector().description(\"" + desc + "\"))"
        ));
    }

    public static String generateRandomMobileNumber() {
        Random random = new Random();
        int remainingDigits = 1000000 + random.nextInt(9000000);
        return "55" + remainingDigits;
    }

    public static String generateRandomNumber() {
        Random random = new Random();
        int firstDigit = 1 + random.nextInt(9);
        int remainingDigits = 100000000 + random.nextInt(900000000);
        return firstDigit + String.valueOf(remainingDigits);
    }
}
