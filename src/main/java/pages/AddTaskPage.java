package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddTaskPage {

    AndroidDriver MyApp;
    WebDriverWait Wait;

    public final By AddTaskLocator = By.id("com.splendapps.splendo:id/fab_add_task");
    public final By TaskTitleLocator = By.id("com.splendapps.splendo:id/edtTaskName");
    public final By TaskListLocator = By.id("com.splendapps.splendo:id/spinnerLists");
    public final By NameOfTaskLocator = By.xpath("//android.widget.TextView[@text=\"Work\"]");
    public final By SaveButtonLocator = By.id("com.splendapps.splendo:id/fab_save");
    public final By TaskLocator = By.xpath("//android.widget.TextView[@resource-id=\"com.splendapps.splendo:id/task_name\" and @text=\"Hello My World\"]");

    public AddTaskPage(AndroidDriver MyAPP)
    {
        this.MyApp = MyAPP;
        Wait = new WebDriverWait(MyAPP , Duration.ofSeconds(10));
    }

    public void ClickOnAddTaskButton()
    {
        Wait.until(ExpectedConditions.elementToBeClickable(AddTaskLocator)).click();
    }
    public void EnterTaskTitle(String title)
    {
        Wait.until(ExpectedConditions.visibilityOfElementLocated(TaskTitleLocator)).sendKeys(title);
    }
    public void ClickOnSelectTaskDescription()
    {
        Wait.until(ExpectedConditions.elementToBeClickable(TaskListLocator)).click();
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }
    public void ClickOnNameOfTaskDescription() {
        Wait.until(ExpectedConditions.visibilityOfElementLocated(NameOfTaskLocator)).click();
    }
    public void ClickOnSaveButton()
    {
        Wait.until(ExpectedConditions.elementToBeClickable(SaveButtonLocator)).click();
    }
    public boolean IsTaskDisPlayed()
    {
        return Wait.until(ExpectedConditions.visibilityOfElementLocated(TaskLocator)).isDisplayed();
    }
}
