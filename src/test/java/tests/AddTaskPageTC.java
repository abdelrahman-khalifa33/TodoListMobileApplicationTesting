package tests;

import driver.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AddTaskPage;

public class AddTaskPageTC extends BaseTest {

    AddTaskPage MyAddTaskPage;

    @Test
    public void VerifyThatUserCanAddANewTask()
    {
        HandleAdIfPresent();
        MyAddTaskPage = new AddTaskPage(MyApp);
        MyAddTaskPage.ClickOnAddTaskButton();
        MyAddTaskPage.EnterTaskTitle("Hello My World");
        MyAddTaskPage.ClickOnSelectTaskDescription();
        MyAddTaskPage.ClickOnNameOfTaskDescription();
        MyAddTaskPage.ClickOnSaveButton();
        Assert.assertTrue(MyAddTaskPage.IsTaskDisPlayed());
    }
}
