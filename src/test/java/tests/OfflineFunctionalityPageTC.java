package tests;

import driver.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.OfflineFunctionalityPage;

public class OfflineFunctionalityPageTC extends BaseTest{

    OfflineFunctionalityPage MyOfflineFunctionalityPage;

    @Test
    public void VerifyThatUserCanAddANewTaskWhenNoInternetConnection()
    {
        HandleAdIfPresent();
        MyOfflineFunctionalityPage = new OfflineFunctionalityPage(MyApp);
        String taskName = "Hello My World";
        MyOfflineFunctionalityPage.ClickAddTask();
        MyOfflineFunctionalityPage.EnterTaskTitle(taskName);
        MyOfflineFunctionalityPage.OpenTaskList();
        MyOfflineFunctionalityPage.SelectTaskCategory();
        MyOfflineFunctionalityPage.ClickOnSaveButton();
        Assert.assertTrue(MyOfflineFunctionalityPage.IsTaskDisplayed(taskName) ,
                "Bonus Scenario 6 FAILED: Task not displayed after offline creation.");
        Assert.assertTrue(MyOfflineFunctionalityPage.IsAppStable() ,
                "Bonus Scenario 6 FAILED: App is not stable after offline operation.");
    }
}
