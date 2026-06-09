package tests;

import driver.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.PerformancePage;

public class PerformancePageTC extends BaseTest{

    PerformancePage MyPerformancePage;

    @Test
    public void VerifyAppPerformanceUnderHeavyTaskCreation() throws InterruptedException {
        MyPerformancePage = new PerformancePage(MyApp);
        int NumberOfTasks = 10;
        long StartTime = System.currentTimeMillis();

        for (int i=1 ; i<=NumberOfTasks ; i++)
        {
            String TaskName = "Performance Task :" + i;
            MyPerformancePage.CreateTaskFast(TaskName);
            Thread.sleep(800);
            System.out.println("Created: " + TaskName);
        }

        long TotalTime = System.currentTimeMillis() - StartTime;
        System.out.println("Total time for " + NumberOfTasks + " tasks = " + TotalTime + " ms");

        Assert.assertTrue(MyPerformancePage.IsAppStable() ,
                "Bonus Scenario 2 FAILED: App is not stable after load test.");
    }
}
