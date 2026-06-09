package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class DeleteTaskPage {

    AndroidDriver MyApp;
    WebDriverWait Wait;

    public final By TaskLocator = By.id("com.splendapps.splendo:id/task_name");
    public final By DeleteButtonLocator = By.id("com.splendapps.splendo:id/action_delete");
    public final By ConfirmDeleteLocator = By.id("android:id/button1");

    public DeleteTaskPage(AndroidDriver MyAPP)
    {
        this.MyApp = MyAPP;
        Wait = new WebDriverWait(MyAPP, Duration.ofSeconds(10));
    }

    public void LongPressOnTask() {
        WebElement Task =
                Wait.until(ExpectedConditions.visibilityOfElementLocated(TaskLocator));

        ((JavascriptExecutor) MyApp).executeScript(
                "mobile: longClickGesture",
                Map.of(
                        "elementId",
                        ((RemoteWebElement) Task).getId(),
                        "duration",
                        2000
                ));
    }

    public void ClickOnDeleteButton() {
        Wait.until(ExpectedConditions.elementToBeClickable(DeleteButtonLocator)).click();
    }
    public void ClickOnConfirmDeleteButton()
    {
        Wait.until(ExpectedConditions.visibilityOfElementLocated(ConfirmDeleteLocator)).click();
    }
    public void WaitUntilTaskDeleted(String taskName)
    {
        By locator = By.xpath("//android.widget.TextView[@text='" + taskName + "']");
        Wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    public boolean IsDeletedTaskPresent(String taskName)
    {
        By taskLocator = By.xpath("//android.widget.TextView[@text='" + taskName + "']");
        try
        {
            MyApp.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            return MyApp.findElements(taskLocator).size() > 0;
        }
        finally
        {
            MyApp.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        }
    }
}
