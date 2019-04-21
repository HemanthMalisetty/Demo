import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.FileLogger;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.StitchMode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import org.testng.annotations.Test;
import utils.page;
import utils.params;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SmileDirect {

    protected RemoteWebDriver driver;

    protected Eyes eyes;

    private static final String BATCH_NAME = params.BATCH_NAME;
    private static final String BATCH_ID = params.BATCH_ID;
    private static final String APP_NAME = params.APP_NAME;

    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @Test(priority = 1, alwaysRun = true, enabled = true)
    public void RescheduleVisit(String platformName ,String platformVersion,
                         String browserName, String browserVersion) {

        Integer i=0;
        String testName = "SD Reschedule Visit";

        //Force to check against specific baseline branch
        //eyes.setBaselineBranchName("");
        //Force to check with the forced baselines corresponding environment
        //eyes.setBaselineEnvName("FF1200x900");

        //Set the environment name in the test batch results
        //eyes.setEnvName(driver.getCapabilities().getBrowserName() + " " + driver.getCapabilities().getVersion());

        eyes.setMatchLevel(params.MATCH_MODE);
        eyes.setStitchMode(StitchMode.CSS);
        eyes.setForceFullPageScreenshot(true);
        if(params.FULL_SCREEN) eyes.setForceFullPageScreenshot(true);
        eyes.setSendDom(true);

        eyes.open(driver,APP_NAME, testName, new RectangleSize(1200, 900));

        try {
            driver.get("https://smiledirectclub.com");
            eyes.check("Home Page", Target.window().fully());

            driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Accessories'])[3]/following::a[1]")).click();
            utils.page.clickName(driver, "username");
            driver.findElement(By.name("username")).sendKeys("chemerson210@gmail.com");
            driver.findElement(By.name("password")).sendKeys("zaq1@WSX");
            eyes.check("Login Page", Target.window().fully());

            driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Forgot Password?'])[1]/following::button[1]")).click();
            utils.page.suspend(5000);
            eyes.check("Submit Login", Target.window().fully());

            driver.findElementByXPath("//a[@href=\"/reschedule/\"]").click();
            utils.page.suspend(5000);
            eyes.check("Reschedule", Target.window().fully());

            driver.findElement(By.id("date-select")).click();
            new Select(driver.findElement(By.id("date-select"))).selectByVisibleText("Tuesday, Apr 30");
            driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Date'])[2]/following::span[15]")).click();
            driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Apply'])[1]/following::button[1]")).click();
            utils.page.suspend(5000);
            eyes.check("Reschedule Complete Page", Target.window().fully());

            eyes.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @BeforeClass(alwaysRun = true)
    public void baseBeforeClass(String platformName ,String platformVersion,
                                String browserName, String browserVersion) {

        String threadId = Long.toString(Thread.currentThread().getId());
        long before = System.currentTimeMillis();

        eyes = utils.myeyes.getEyes(threadId);
        eyes.setLogHandler(new FileLogger("log/file.log",true,true));

        BatchInfo batchInfo = new BatchInfo(BATCH_NAME);
        if(BATCH_ID!=null) batchInfo.setId(BATCH_ID);
        eyes.setBatch(batchInfo);

        //driver = utils.drivers.getGrid(threadId, browserName);
        driver = utils.drivers.getLocalChrome(threadId);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(90, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);


        //Allows for filtering dashboard view
        eyes.addProperty("SANDBOX", "YES");

        System.out.println("START THREAD ID - " + Thread.currentThread().getId() + " " + browserName + " " + browserVersion);
        System.out.println("baseBeforeClass took " + (System.currentTimeMillis() - before) + "ms");
    }

    @AfterClass(alwaysRun = true)
    public void baseAfterClass() {

        if (driver != null) {
            long before = System.currentTimeMillis();
            eyes.abortIfNotClosed();
            driver.quit();
            System.out.println("Driver quit took " + (System.currentTimeMillis() - before) + "ms");
        }


    }
}
