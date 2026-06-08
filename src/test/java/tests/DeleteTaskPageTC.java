package tests;

import driver.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddTaskPage;
import pages.DeleteTaskPage;

public class DeleteTaskPageTC extends BaseTest{

    AddTaskPage MyAddTaskPage;
    DeleteTaskPage MyDeleteTaskPage;

    @BeforeMethod
    public void CreateTask()
    {
        HandleAdIfPresent();
        MyAddTaskPage = new AddTaskPage(MyApp);
        MyAddTaskPage.ClickOnAddTaskButton();
        MyAddTaskPage.EnterTaskTitle("Hello My World");
        MyAddTaskPage.ClickOnSelectTaskDescription();
        MyAddTaskPage.ClickOnNameOfTaskDescription();
        MyAddTaskPage.ClickOnSaveButton();

    }

    @Test
    public void VerifyThatUserCanDeleteTask() throws InterruptedException {
        HandleAdIfPresent();
        MyDeleteTaskPage = new DeleteTaskPage(MyApp);
        MyDeleteTaskPage.LongPressOnTask();
        MyDeleteTaskPage.ClickOnDeleteButton();
        MyDeleteTaskPage.ClickOnConfirmDeleteButton();
        MyDeleteTaskPage.WaitUntilTaskDeleted("Hello My World");
        Assert.assertFalse(MyDeleteTaskPage.IsDeletedTaskPresent("Hello My World"));
    }
}
