package tests;

import driver.BaseTest;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class RegressionTest extends BaseTest {

    // ─────────────────────────────────────────────────────────────────────────
    // Single session lifecycle — app opens once, stays open for all 7 tests
    // ─────────────────────────────────────────────────────────────────────────

    @BeforeClass
    public void setupSession() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setAppPackage("com.splendapps.splendo");
        options.setAppActivity("com.splendapps.splendo.activity.MainActivity");
        options.setDeviceName("RK8TC03SMBK");
        options.setNoReset(true);

        MyApp = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
        MyApp.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        MyApp.terminateApp("com.splendapps.splendo");
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        MyApp.activateApp("com.splendapps.splendo");
        HandleAdIfPresent();
    }

    /** Disable parent's per-test driver restart — session is shared */
    @Override
    @BeforeMethod
    public void Setup() {
        // intentionally empty
    }

    /** Press back until the main screen FAB is visible — app stays open the whole time */
    @AfterMethod
    public void returnToMainScreen() {
        try {
            By fabLocator = By.id("com.splendapps.splendo:id/fab_add_task");
            MyApp.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            for (int i = 0; i < 5; i++) {
                if (MyApp.findElements(fabLocator).size() > 0) break;
                MyApp.navigate().back();
                Thread.sleep(500);
            }
            HandleAdIfPresent();
        } catch (Exception ignored) {
        } finally {
            MyApp.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        }
    }

    @AfterClass
    public void tearDownSession() {
        if (MyApp != null) MyApp.quit();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scenario 1: Verify That User Can Add a New Task
    // ─────────────────────────────────────────────────────────────────────────
    @Test(priority = 1, description = "Scenario 1: Verify That User Can Add a New Task")
    public void verifyThatUserCanAddANewTask() {
        HandleAdIfPresent();
        AddTaskPage addTaskPage = new AddTaskPage(MyApp);
        addTaskPage.ClickOnAddTaskButton();
        addTaskPage.EnterTaskTitle("Hello My World");
        addTaskPage.ClickOnSelectTaskDescription();
        addTaskPage.ClickOnNameOfTaskDescription();
        addTaskPage.ClickOnSaveButton();
        Assert.assertTrue(addTaskPage.IsTaskDisPlayed(),
                "Scenario 1 FAILED: Task was not displayed after adding.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scenario 2: Verify That User Can Edit an Existing Task
    // ─────────────────────────────────────────────────────────────────────────
    @Test(priority = 2, description = "Scenario 2: Verify That User Can Edit an Existing Task")
    public void verifyThatUserCanEditExistingTask() {
        HandleAdIfPresent();
        AddTaskPage addTaskPage = new AddTaskPage(MyApp);
        addTaskPage.ClickOnAddTaskButton();
        addTaskPage.EnterTaskTitle("Hello My World");
        addTaskPage.ClickOnSelectTaskDescription();
        addTaskPage.ClickOnNameOfTaskDescription();
        addTaskPage.ClickOnSaveButton();

        HandleAdIfPresent();
        EditTaskPage editTaskPage = new EditTaskPage(MyApp);
        editTaskPage.ClickOnEditButton();
        editTaskPage.ClickOnEditListButton();
        editTaskPage.SelectTaskToEdit();
        editTaskPage.ClickOnTaskToEdit();
        editTaskPage.UpdateTaskTitle("Tasks Today");
        editTaskPage.ClickOnSaveEditButton();

        Assert.assertTrue(editTaskPage.IsUpdatedTaskDisplayed("Tasks Today"),
                "Scenario 2 FAILED: Task title was not updated correctly.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scenario 3: Verify That User Can Delete a Task
    // ─────────────────────────────────────────────────────────────────────────
    @Test(priority = 3, description = "Scenario 3: Verify That User Can Delete a Task")
    public void verifyThatUserCanDeleteTask() throws InterruptedException {
        HandleAdIfPresent();
        AddTaskPage addTaskPage = new AddTaskPage(MyApp);
        addTaskPage.ClickOnAddTaskButton();
        addTaskPage.EnterTaskTitle("Deleted Task");
        addTaskPage.ClickOnSelectTaskDescription();
        addTaskPage.ClickOnNameOfTaskDescription();
        addTaskPage.ClickOnSaveButton();

        HandleAdIfPresent();
        DeleteTaskPage deleteTaskPage = new DeleteTaskPage(MyApp);
        deleteTaskPage.LongPressOnTask();
        deleteTaskPage.ClickOnDeleteButton();
        deleteTaskPage.ClickOnConfirmDeleteButton();
        deleteTaskPage.WaitUntilTaskDeleted("Deleted Task");
        Assert.assertFalse(deleteTaskPage.IsDeletedTaskPresent("Deleted Task"),
                "Scenario 3 FAILED: Task still present after deletion.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scenario 4: Verify That User Can Mark a Task as Completed
    // ─────────────────────────────────────────────────────────────────────────
    @Test(priority = 4, description = "Scenario 4: Verify That User Can Mark a Task as Completed")
    public void verifyThatUserCanMarkATaskAsCompleted() {
        HandleAdIfPresent();
        AddTaskPage addTaskPage = new AddTaskPage(MyApp);
        addTaskPage.ClickOnAddTaskButton();
        addTaskPage.EnterTaskTitle("Automation Tasks");
        addTaskPage.ClickOnSelectTaskDescription();
        addTaskPage.ClickOnNameOfTaskDescription();
        addTaskPage.ClickOnSaveButton();

        HandleAdIfPresent();
        MarkATaskPage markATaskPage = new MarkATaskPage(MyApp);
        String taskName = "Automation Tasks";
        markATaskPage.ClickTaskCheckbox(taskName);
        markATaskPage.ClickOnBurgerButton();
        markATaskPage.OpenFinishedTasks();
        Assert.assertTrue(markATaskPage.IsTaskPresentInFinished(taskName),
                "Scenario 4 FAILED: Task was not found in Finished tasks.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scenario 5: Automate Filtering Completed Tasks
    // ─────────────────────────────────────────────────────────────────────────
    @Test(priority = 5, description = "Scenario 5: Automate Filtering Completed Tasks")
    public void verifyFilteringCompletedTasks() {
        HandleAdIfPresent();
        AddTaskPage addTaskPage = new AddTaskPage(MyApp);
        addTaskPage.ClickOnAddTaskButton();
        addTaskPage.EnterTaskTitle("Filter Task Test");
        addTaskPage.ClickOnSelectTaskDescription();
        addTaskPage.ClickOnNameOfTaskDescription();
        addTaskPage.ClickOnSaveButton();

        HandleAdIfPresent();
        MarkATaskPage markATaskPage = new MarkATaskPage(MyApp);
        String taskName = "Filter Task Test";
        markATaskPage.ClickTaskCheckbox(taskName);
        markATaskPage.ClickOnBurgerButton();
        markATaskPage.OpenFinishedTasks();
        Assert.assertTrue(markATaskPage.IsTaskPresentInFinished(taskName),
                "Scenario 5 FAILED: Completed task not visible in Finished filter.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Bonus Scenario 2: Verify App Performance Under Continuous Task Creation
    // ─────────────────────────────────────────────────────────────────────────
    @Test(priority = 6, description = "Bonus Scenario 2: Verify App Performance Under Continuous Task Creation")
    public void verifyAppPerformanceUnderHeavyTaskCreation() throws InterruptedException {
        HandleAdIfPresent();
        PerformancePage performancePage = new PerformancePage(MyApp);
        int numberOfTasks = 10;
        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= numberOfTasks; i++) {
            String taskName = "Performance Task : " + i;
            performancePage.CreateTaskFast(taskName);
            Thread.sleep(800);
            System.out.println("Created: " + taskName);
        }

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Total time for " + numberOfTasks + " tasks = " + totalTime + " ms");

        Assert.assertTrue(performancePage.IsAppStable(),
                "Bonus Scenario 2 FAILED: App is not stable after load test.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Bonus Scenario 6: Validate Offline Functionality
    // ─────────────────────────────────────────────────────────────────────────
    @Test(priority = 7, description = "Bonus Scenario 6: Validate Offline Functionality")
    public void verifyThatUserCanAddANewTaskWhenNoInternetConnection() {
        HandleAdIfPresent();
        OfflineFunctionalityPage offlinePage = new OfflineFunctionalityPage(MyApp);
        String taskName = "Hello My World";
        offlinePage.ClickAddTask();
        offlinePage.EnterTaskTitle(taskName);
        offlinePage.OpenTaskList();
        offlinePage.SelectTaskCategory();
        offlinePage.ClickOnSaveButton();
        Assert.assertTrue(offlinePage.IsTaskDisplayed(taskName),
                "Bonus Scenario 6 FAILED: Task not displayed after offline creation.");
        Assert.assertTrue(offlinePage.IsAppStable(),
                "Bonus Scenario 6 FAILED: App is not stable after offline operation.");
    }
}
