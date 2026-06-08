package tests;
import driver.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddTaskPage;
import pages.EditTaskPage;

public class EditTaskPageTC extends BaseTest{

    AddTaskPage MyAddTaskPage;
    EditTaskPage MyEditTaskPage;

    @BeforeMethod
    public void VerifyThatUserCanAddANewTask()
    {
        MyAddTaskPage = new AddTaskPage(MyApp);
        MyAddTaskPage.ClickOnAddTaskButton();
        MyAddTaskPage.EnterTaskTitle("Hello My World");
        MyAddTaskPage.ClickOnSelectTaskDescription();
        MyAddTaskPage.ClickOnNameOfTaskDescription();
        MyAddTaskPage.ClickOnSaveButton();
        Assert.assertTrue(MyAddTaskPage.IsTaskDisPlayed());
    }

    @Test
    public void VerifyThatUserCanEditExistingTask()
    {
        MyEditTaskPage = new EditTaskPage(MyApp);
        MyEditTaskPage.ClickOnEditButton();
        MyEditTaskPage.ClickOnEditListButton();
        MyEditTaskPage.SelectTaskToEdit();
        MyEditTaskPage.ClickOnTaskToEdit();

        MyEditTaskPage.UpdateTaskTitle("Tasks Today");
        MyEditTaskPage.ClickOnSaveEditButton();

        String ActualTitle = MyEditTaskPage.GetTaskName();
        Assert.assertEquals(ActualTitle , "Tasks Today");
    }

}
