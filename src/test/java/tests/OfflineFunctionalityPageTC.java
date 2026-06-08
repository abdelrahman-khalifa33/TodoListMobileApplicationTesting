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
        MyOfflineFunctionalityPage = new OfflineFunctionalityPage(MyApp);

        String taskName = "Hello My World";

        MyOfflineFunctionalityPage.ClickAddTask();
        MyOfflineFunctionalityPage.EnterTaskTitle(taskName);
        MyOfflineFunctionalityPage.OpenTaskList();
        MyOfflineFunctionalityPage.SelectTaskCategory();
        MyOfflineFunctionalityPage.ClickOnSaveButton();
        Assert.assertTrue(MyOfflineFunctionalityPage.IsTaskDisplayed(taskName));
        Assert.assertTrue(MyOfflineFunctionalityPage.IsAppStable());
    }
}
