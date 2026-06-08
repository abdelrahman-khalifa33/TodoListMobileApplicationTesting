package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MarkATaskPage {

    AndroidDriver MyApp;
    WebDriverWait Wait;

    public final By BurgerButtonLocator = By.xpath("//android.widget.ImageButton");
    public final By FinishedTasksLocator = By.xpath("//android.widget.TextView[@resource-id=\"com.splendapps.splendo:id/navItemLabel\" and @text=\"Finished\"]");

    public MarkATaskPage(AndroidDriver MyAPP) {
        this.MyApp = MyAPP;
        Wait = new WebDriverWait(MyAPP, Duration.ofSeconds(10));
    }

    public void ClickTaskCheckbox(String taskName) {

        By checkBox = By.xpath(
                "//android.widget.TextView[@text='" + taskName
                        + "']/ancestor::android.view.ViewGroup//android.widget.CheckBox");
        Wait.until(ExpectedConditions.elementToBeClickable(checkBox)).click();
    }
    public void ClickOnBurgerButton()
    {
        Wait.until(ExpectedConditions.elementToBeClickable(BurgerButtonLocator)).click();
    }
    public void OpenFinishedTasks() {
        Wait.until(ExpectedConditions.elementToBeClickable(FinishedTasksLocator)).click();
    }
    public boolean IsTaskPresentInFinished(String taskTitle)
    {
        By TaskLocator = By.xpath("//android.widget.TextView[@text='" + taskTitle + "']");
        return Wait.until(ExpectedConditions.visibilityOfElementLocated(TaskLocator)).isDisplayed();
    }
}
