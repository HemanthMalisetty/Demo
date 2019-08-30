import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import org.openqa.selenium.By;


import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.params;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action;

import java.util.concurrent.TimeUnit;

public class FedExTracking {

    protected RemoteWebDriver driver;
    protected Eyes eyes;

    private static final String BATCH_NAME = "FedEx Brand";
    private static final String BATCH_ID = params.BATCH_ID;

    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @Test(priority = 1, alwaysRun = true, enabled = true)
    public void BrandURL(String platformName ,String platformVersion,
                         String browserName, String browserVersion) {

        Integer i=0;
        long before;

        //Force to check against specific baseline branch
        //eyes.setBaselineBranchName("Firefox");
        //Force; to check with the forced baselines corresponding environment
        //eyes.setBaselineEnvName("FF1200x900");

        //Set the environment name in the test batch results
        //eyes.setEnvName(driver.getCapabilities().getBrowserName() + " " + driver.getCapabilities().getVersion());

        driver.manage().timeouts().implicitlyWait(8,TimeUnit.SECONDS) ;


        eyes.setMatchLevel(params.MATCH_MODE);
        eyes.setStitchMode(StitchMode.CSS);
        eyes.setForceFullPageScreenshot(false);  //if(params.FULL_SCREEN) eyes.setForceFullPageScreenshot(true);
        eyes.setSendDom(true);
        eyes.open(driver,"FedEx Brand", "FedEx Brand", new RectangleSize(1200, 800));

        driver.get("https://brand.fedex.com/login.html");
        eyes.check("Home Page", Target.window().fully());

        driver.get("https://brand.fedex.com/login.html");
        driver.findElement(By.id("ou")).click();
        driver.findElement(By.id("input-username")).click();
        driver.findElement(By.id("input-password")).sendKeys("Patanjali1-4");
        driver.findElement(By.id("input-username")).sendKeys("cemerson123");
        eyes.check("Login", Target.window().fully());

        driver.findElement(By.id("input-submit1")).click();
        driver.findElement(By.name("search")).click();
        driver.findElement(By.name("search")).sendKeys("Delivery");
        driver.findElement(By.cssSelector(".searchmagnifiericon")).click();
        utils.page.suspend(8000);
        utils.page.changePageSingle(driver,")", "]");
        eyes.check("Search", Target.window().fully());

        driver.findElement(By.cssSelector("#facet-materialType2 .ng-scope:nth-child(2) .border-checkbox")).click();
        utils.page.suspend(1000);
        driver.findElement(By.cssSelector(".resetsearch-edit")).click();
        utils.page.suspend(1000);


        driver.findElement(By.cssSelector(".clearfix:nth-child(7) .fx-asset-thumbnail > img")).click();
        utils.page.changePageSingle(driver,"o", "0");
        eyes.check("Select", Target.window().fully());

        driver.findElement(By.cssSelector(".text-center > .btn-default")).click();

        driver.findElement(By.cssSelector(".text-center > .btn-default")).click();
        eyes.check("Cart", Target.window().fully());

        TestResults testResult = eyes.close(false);
        System.out.println("Applitools Test Results");
        System.out.println(testResult.toString());

    }




    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @Test(priority = 1, alwaysRun = true, enabled = false)
    public void CheckURL(String platformName ,String platformVersion,
                         String browserName, String browserVersion) {

        Integer i=0;
        long before;

        //Force to check against specific baseline branch
        //eyes.setBaselineBranchName("Firefox");
        //Force; to check with the forced baselines corresponding environment
        //eyes.setBaselineEnvName("FF1200x900");

        //Set the environment name in the test batch results
        //eyes.setEnvName(driver.getCapabilities().getBrowserName() + " " + driver.getCapabilities().getVersion());

        eyes.setMatchLevel(params.MATCH_MODE);
        eyes.setStitchMode(StitchMode.CSS);
        eyes.setForceFullPageScreenshot(false);  //if(params.FULL_SCREEN) eyes.setForceFullPageScreenshot(true);
        eyes.setSendDom(true);
        eyes.open(driver,"FedEx Tracking", "FedEx Tracking", new RectangleSize(1200, 800));

        driver.get("https://www.fedex.com/en-us/home.html");
        utils.page.suspend(4000);
        eyes.check("Home Page", Target.window().fully());

        driver.findElement(By.name("trackingnumber")).click();
        driver.findElement(By.name("trackingnumber")).sendKeys("772803780072");
        utils.page.suspend(2000);
        driver.findElement(By.id("btnSingleTrack")).click();
        utils.page.suspend(4000);
        eyes.check("Tracking 772803780072", Target.window().fully());

       // driver.findElement(By.cssSelector(".fxg-app__single-tracking")).click();
        driver.findElement(By.name("trackingnumber")).click();
        driver.findElement(By.name("trackingnumber")).clear();
        driver.findElement(By.name("trackingnumber")).sendKeys("787658126565");
        utils.page.suspend(2000);

        driver.findElement(By.id("btnSingleTrack")).click();   //switch between these two to go to the wrong page
        //driver.get("https://www.fedex.com/en-us/tracking.html");

        utils.page.suspend(4000);
        eyes.check("Tracking 787658126565", Target.window().fully());


        TestResults testResult = eyes.close(false);
        System.out.println("Applitools Test Results");
        System.out.println(testResult.toString());

    }


    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @BeforeClass(alwaysRun = true)
    public void baseBeforeClass(String platformName ,String platformVersion,
                                String browserName, String browserVersion) {

        String threadId = Long.toString(Thread.currentThread().getId());
        long before = System.currentTimeMillis();

        eyes = utils.myeyes.getEyes(threadId);
        //eyes.setLogHandler(new FileLogger("log/file.log",true,true));
        eyes.setLogHandler(new StdoutLogHandler(false));

        BatchInfo batchInfo = new BatchInfo(BATCH_NAME);
        if(BATCH_ID!=null) batchInfo.setId(BATCH_ID);
        eyes.setBatch(batchInfo);

        driver = utils.drivers.getLocalChrome(threadId);
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
