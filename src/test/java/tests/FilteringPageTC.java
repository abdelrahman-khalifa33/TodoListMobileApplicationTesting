package tests;

import driver.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddTaskPage;
import pages.FilteringPage;
import pages.MarkATaskPage;

public class FilteringPageTC extends BaseTest{

    AddTaskPage MyAddTaskPage;
    FilteringPage MyFilteringPage;

    @BeforeMethod
    public void CreateTask()
    {
        HandleAdIfPresent();
        MyAddTaskPage = new AddTaskPage(MyApp);
        MyAddTaskPage.ClickOnAddTaskButton();
        MyAddTaskPage.EnterTaskTitle("Automation Tasks");
        MyAddTaskPage.ClickOnSelectTaskDescription();
        MyAddTaskPage.ClickOnNameOfTaskDescription();
        MyAddTaskPage.ClickOnSaveButton();
    }
    @Test
    public void VerifyThatUserCanMarkATaskAsCompleted()
    {
        HandleAdIfPresent();
        MyFilteringPage = new FilteringPage(MyApp);

        String taskName = "Automation Tasks";

        MyFilteringPage.ClickTaskCheckbox(taskName);
        MyFilteringPage.ClickOnBurgerButton();
        MyFilteringPage.OpenFinishedTasks();
        Assert.assertTrue(MyFilteringPage.IsTaskPresentInFinished(taskName),
                "Scenario 5 FAILED: Completed task not visible in Finished filter.");
    }
}
