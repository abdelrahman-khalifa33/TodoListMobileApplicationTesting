package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EditTaskPage {

    AndroidDriver MyApp;
    WebDriverWait Wait;

    public final By EditButtonLocator = By.className("android.widget.ImageButton");
    public final By EditListButtonLocator = By.id("com.splendapps.splendo:id/edit_lists");
    public final By TaskLocator = By.xpath("(//android.widget.LinearLayout[@resource-id=\"com.splendapps.splendo:id/lay_task_list\"])[2]/android.widget.LinearLayout[1]");
    public final By EditTaskLocator = By.id("com.splendapps.splendo:id/task_name");
    public final By EditAndNewTitleLocator = By.id("com.splendapps.splendo:id/edtTaskName");
    public final By SaveEditButton = By.id("com.splendapps.splendo:id/fab_save");


    public EditTaskPage(AndroidDriver MyAPP)
    {
        this.MyApp = MyAPP;
        Wait = new WebDriverWait(MyAPP , Duration.ofSeconds(10));
    }

    public void ClickOnEditButton()
    {
        Wait.until(ExpectedConditions.elementToBeClickable(EditButtonLocator)).click();
    }
    public void ClickOnEditListButton()
    {
        Wait.until(ExpectedConditions.elementToBeClickable(EditListButtonLocator)).click();
    }
    public void SelectTaskToEdit()
    {
        Wait.until(ExpectedConditions.elementToBeClickable(TaskLocator)).click();
    }
    public void ClickOnTaskToEdit(){ Wait.until(ExpectedConditions.presenceOfElementLocated(EditTaskLocator)).click();}
    public void UpdateTaskTitle(String newTitle)
    {
        WebElement TitleFiled = Wait.until(ExpectedConditions.visibilityOfElementLocated(EditAndNewTitleLocator));
        TitleFiled.clear();
        TitleFiled.sendKeys(newTitle);
    }
    public void ClickOnSaveEditButton()
    {
        Wait.until(ExpectedConditions.elementToBeClickable(SaveEditButton)).click();
    }
    public boolean IsUpdatedTaskDisplayed(String updatedTitle)
    {
        By specificTask = By.xpath("//android.widget.TextView[@text='" + updatedTitle + "']");
        return Wait.until(ExpectedConditions.visibilityOfElementLocated(specificTask)).isDisplayed();
    }

}
