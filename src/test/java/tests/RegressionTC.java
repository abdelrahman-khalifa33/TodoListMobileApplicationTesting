package tests;

import driver.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pages.*;
import java.time.Duration;

public class RegressionTC extends BaseTest {

    /** Press back until the main screen FAB is visible — app stays open the whole time */
    @AfterMethod
    public void ReturnToMainScreen()
    {
        try {
            By fabLocator = By.id("com.splendapps.splendo:id/fab_add_task");
            MyApp.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            for (int i = 0; i < 5; i++) {
                if (MyApp.findElements(fabLocator).size() > 0) break;
                MyApp.navigate().back();
                Thread.sleep(500);
            }
            HandleAdIfPresent();
        }
        catch (Exception ignored)
        {
        }
        finally
        {
            MyApp.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        }
    }

    @AfterClass
    public void TearDownSession() {
        if (MyApp != null) MyApp.quit();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scenario 1: Verify That User Can Add a New Task
    // ─────────────────────────────────────────────────────────────────────────
    @Test(priority = 1, description = "Scenario 1: Verify That User Can Add a New Task")
    public void verifyThatUserCanAddANewTask()
    {
        HandleAdIfPresent();
        AddTaskPage MyAddTaskPage = new AddTaskPage(MyApp);
        MyAddTaskPage.ClickOnAddTaskButton();
        MyAddTaskPage.EnterTaskTitle("Hello My World");
        MyAddTaskPage.ClickOnSelectTaskDescription();
        MyAddTaskPage.ClickOnNameOfTaskDescription();
        MyAddTaskPage.ClickOnSaveButton();
        Assert.assertTrue(MyAddTaskPage.IsTaskDisPlayed(),
                "Scenario 1 FAILED: Task was not displayed after adding.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scenario 2: Verify That User Can Edit an Existing Task
    // ─────────────────────────────────────────────────────────────────────────
    @Test(priority = 2, description = "Scenario 2: Verify That User Can Edit an Existing Task")
    public void verifyThatUserCanEditExistingTask()
    {
        HandleAdIfPresent();
        AddTaskPage MyAddTaskPage = new AddTaskPage(MyApp);
        MyAddTaskPage.ClickOnAddTaskButton();
        MyAddTaskPage.EnterTaskTitle("Task_");
        MyAddTaskPage.ClickOnSelectTaskDescription();
        MyAddTaskPage.ClickOnNameOfTaskDescription();
        MyAddTaskPage.ClickOnSaveButton();

        HandleAdIfPresent();
        EditTaskPage MyEditTaskPage = new EditTaskPage(MyApp);
        MyEditTaskPage.ClickOnEditButton();
        MyEditTaskPage.ClickOnEditListButton();
        MyEditTaskPage.SelectTaskToEdit();
        MyEditTaskPage.ClickOnTaskToEdit();
        MyEditTaskPage.UpdateTaskTitle("Tasks Today");
        MyEditTaskPage.ClickOnSaveEditButton();

        Assert.assertTrue(MyEditTaskPage.IsUpdatedTaskDisplayed("Tasks Today"),
                "Scenario 2 FAILED: Task title was not updated correctly.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scenario 3: Verify That User Can Delete a Task
    // ─────────────────────────────────────────────────────────────────────────
    @Test(priority = 3, description = "Scenario 3: Verify That User Can Delete a Task")
    public void verifyThatUserCanDeleteTask() throws InterruptedException
    {
        HandleAdIfPresent();
        AddTaskPage MyAddTaskPage = new AddTaskPage(MyApp);
        MyAddTaskPage.ClickOnAddTaskButton();
        MyAddTaskPage.EnterTaskTitle("Delete Task");
        MyAddTaskPage.ClickOnSelectTaskDescription();
        MyAddTaskPage.ClickOnNameOfTaskDescription();
        MyAddTaskPage.ClickOnSaveButton();

        HandleAdIfPresent();
        DeleteTaskPage MyDeleteTaskPage = new DeleteTaskPage(MyApp);
        MyDeleteTaskPage.LongPressOnTask("Delete Task");
        MyDeleteTaskPage.ClickOnDeleteButton();
        MyDeleteTaskPage.ClickOnConfirmDeleteButton();
        MyDeleteTaskPage.WaitUntilTaskDeleted("Delete Task");
        Assert.assertFalse(MyDeleteTaskPage.IsDeletedTaskPresent("Delete Task"),
                "Scenario 3 FAILED: Task still present after deletion.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scenario 4: Verify That User Can Mark a Task as Completed
    // ─────────────────────────────────────────────────────────────────────────
    @Test(priority = 4, description = "Scenario 4: Verify That User Can Mark a Task as Completed")
    public void verifyThatUserCanMarkATaskAsCompleted()
    {
        HandleAdIfPresent();
        AddTaskPage MyAddTaskPage = new AddTaskPage(MyApp);
        MyAddTaskPage.ClickOnAddTaskButton();
        MyAddTaskPage.EnterTaskTitle("Automation Tasks");
        MyAddTaskPage.ClickOnSelectTaskDescription();
        MyAddTaskPage.ClickOnNameOfTaskDescription();
        MyAddTaskPage.ClickOnSaveButton();

        HandleAdIfPresent();
        MarkATaskPage MyMarkATaskPage = new MarkATaskPage(MyApp);
        String TaskName = "Automation Tasks";
        MyMarkATaskPage.ClickTaskCheckbox(TaskName);
        By Checkbox = By.xpath("//android.widget.TextView[@text='" + TaskName +
                "']/ancestor::android.view.ViewGroup//android.widget.CheckBox");
        Assert.assertEquals(MyApp.findElements(Checkbox).size(), 0);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scenario 5: Automate Filtering Completed Tasks
    // ─────────────────────────────────────────────────────────────────────────
    @Test(priority = 5, description = "Scenario 5: Automate Filtering Completed Tasks")
    public void verifyFilteringCompletedTasks()
    {
        HandleAdIfPresent();
        AddTaskPage MyAddTaskPage = new AddTaskPage(MyApp);
        MyAddTaskPage.ClickOnAddTaskButton();
        MyAddTaskPage.EnterTaskTitle("Filter Task Test");
        MyAddTaskPage.ClickOnSelectTaskDescription();
        MyAddTaskPage.ClickOnNameOfTaskDescription();
        MyAddTaskPage.ClickOnSaveButton();

        HandleAdIfPresent();
        FilteringPage MyFilteringPage = new FilteringPage(MyApp);
        String taskName = "Filter Task Test";
        MyFilteringPage.ClickTaskCheckbox(taskName);
        MyFilteringPage.ClickOnBurgerButton();
        MyFilteringPage.OpenFinishedTasks();
        Assert.assertTrue(MyFilteringPage.IsTaskPresentInFinished(taskName),
                "Scenario 5 FAILED: Completed task not visible in Finished filter.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Bonus Scenario 2: Verify App Performance Under Continuous Task Creation
    // ─────────────────────────────────────────────────────────────────────────
    @Test(priority = 6, description = "Bonus Scenario 2: Verify App Performance Under Continuous Task Creation")
    public void verifyAppPerformanceUnderHeavyTaskCreation() throws InterruptedException
    {
        HandleAdIfPresent();
        PerformancePage MyPerformancePage = new PerformancePage(MyApp);
        int numberOfTasks = 10;
        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= numberOfTasks; i++)
        {
            String taskName = "Performance Task : " + i;
            MyPerformancePage.CreateTaskFast(taskName);
            Thread.sleep(800);
            System.out.println("Created: " + taskName);
        }

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Total time for " + numberOfTasks + " tasks = " + totalTime + " ms");

        Assert.assertTrue(MyPerformancePage.IsAppStable(),
                "Bonus Scenario 2 FAILED: App is not stable after load test.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Bonus Scenario 6: Validate Offline Functionality
    // ─────────────────────────────────────────────────────────────────────────
    @Test(priority = 7, description = "Bonus Scenario 6: Validate Offline Functionality")
    public void verifyThatUserCanAddANewTaskWhenNoInternetConnection()
    {
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
