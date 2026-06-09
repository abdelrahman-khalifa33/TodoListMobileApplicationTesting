package tests;

import driver.BaseTest;
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
        MyAddTaskPage.EnterTaskTitle("Delete Task");
        MyAddTaskPage.ClickOnSelectTaskDescription();
        MyAddTaskPage.ClickOnNameOfTaskDescription();
        MyAddTaskPage.ClickOnSaveButton();
    }

    @Test
    public void VerifyThatUserCanDeleteTask() throws InterruptedException {
        HandleAdIfPresent();
        MyDeleteTaskPage = new DeleteTaskPage(MyApp);
        MyDeleteTaskPage.LongPressOnTask("Delete Task");
        MyDeleteTaskPage.ClickOnDeleteButton();
        MyDeleteTaskPage.ClickOnConfirmDeleteButton();
        MyDeleteTaskPage.WaitUntilTaskDeleted("Delete Task");
        Assert.assertFalse(MyDeleteTaskPage.IsDeletedTaskPresent("Delete Task") ,
                "Scenario 3 FAILED: Task still present after deletion.");
    }
}
