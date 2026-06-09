package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OfflineFunctionalityPage {

    AndroidDriver MyApp;
    WebDriverWait Wait;

    public final By AddTaskLocator = By.id("com.splendapps.splendo:id/fab_add_task");
    public final By TaskTitleLocator = By.id("com.splendapps.splendo:id/edtTaskName");
    public final By TaskListLocator = By.id("com.splendapps.splendo:id/spinnerLists");
    public final By NameOfTaskLocator = By.xpath("//android.widget.TextView[@text=\"Work\"]");
    public final By SaveButtonLocator = By.id("com.splendapps.splendo:id/fab_save");

    public OfflineFunctionalityPage(AndroidDriver MyAPP)
    {
        this.MyApp = MyAPP;
        Wait = new WebDriverWait(MyAPP , Duration.ofSeconds(10));
    }

    public void ClickAddTask()
    {
        Wait.until(ExpectedConditions.elementToBeClickable(AddTaskLocator)).click();
    }
    public void EnterTaskTitle(String title) {Wait.until(ExpectedConditions.visibilityOfElementLocated(TaskTitleLocator)).sendKeys(title);}
    public void OpenTaskList()
    {
        Wait.until(ExpectedConditions.elementToBeClickable(TaskListLocator)).click();
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }
    public void SelectTaskCategory()
    {
        Wait.until(ExpectedConditions.visibilityOfElementLocated(NameOfTaskLocator)).click();
    }
    public void ClickOnSaveButton()
    {
        Wait.until(ExpectedConditions.elementToBeClickable(SaveButtonLocator)).click();
    }
    public boolean IsTaskDisplayed(String taskName)
    {
        By taskLocator = By.xpath(
                "//android.widget.TextView[@resource-id='com.splendapps.splendo:id/task_name' and @text='" + taskName + "']");
        return Wait.until(ExpectedConditions.visibilityOfElementLocated(taskLocator)).isDisplayed();
    }
    public boolean IsAppStable()
    {
        return !MyApp.getPageSource().toLowerCase().contains("error");
    }
}
