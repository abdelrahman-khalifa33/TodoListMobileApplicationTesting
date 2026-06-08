package tests;

import driver.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddTaskPage;
import pages.MarkATaskPage;

public class MarkATaskPageTC extends BaseTest{

    AddTaskPage MyAddTaskPage;
    MarkATaskPage MyMarkATaskPage;

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
        MyMarkATaskPage = new MarkATaskPage(MyApp);

        String TaskName = "Automation Tasks";

        MyMarkATaskPage.ClickTaskCheckbox(TaskName);
        MyMarkATaskPage.ClickOnBurgerButton();
        MyMarkATaskPage.OpenFinishedTasks();
        Assert.assertTrue(MyMarkATaskPage.IsTaskPresentInFinished(TaskName));
    }
}
