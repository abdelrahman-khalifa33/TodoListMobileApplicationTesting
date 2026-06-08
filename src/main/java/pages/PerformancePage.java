package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PerformancePage {

    AndroidDriver MyApp;
    WebDriverWait Wait;

    public final By AddTaskLocator = By.id("com.splendapps.splendo:id/fab_add_task");
    public final By TaskTitleLocator = By.id("com.splendapps.splendo:id/edtTaskName");
    public final By TaskListLocator = By.id("com.splendapps.splendo:id/spinnerLists");
    public final By NameOfTaskLocator = By.xpath("//android.widget.TextView[@text=\"العمل\"]");
    public final By SaveButtonLocator = By.id("com.splendapps.splendo:id/fab_save");

    public PerformancePage(AndroidDriver MyAPP)
    {
        this.MyApp = MyAPP;
        Wait = new WebDriverWait(MyAPP , Duration.ofSeconds(10));
    }

    public void CreateTaskFast(String taskName)
    {
        Wait.until(ExpectedConditions.elementToBeClickable(AddTaskLocator)).click();
        Wait.until(ExpectedConditions.visibilityOfElementLocated(TaskTitleLocator)).sendKeys(taskName);
        Wait.until(ExpectedConditions.elementToBeClickable(TaskListLocator)).click();
        Wait.until(ExpectedConditions.elementToBeClickable(NameOfTaskLocator)).click();
        Wait.until(ExpectedConditions.elementToBeClickable(SaveButtonLocator)).click();

//        Wait.until(ExpectedConditions.invisibilityOf(SaveButtonLocator));
    }

    public boolean IsTaskDisplayed(String taskName) {
        By taskLocator = By.xpath(
                "//android.widget.TextView[@resource-id='com.splendapps.splendo:id/task_name' and @text='" + taskName + "']");
        return Wait.until(ExpectedConditions.visibilityOfElementLocated(taskLocator)).isDisplayed();
    }
    public boolean IsAppStable()
    {
        return !MyApp.getPageSource().toLowerCase().contains("error");
    }
}
