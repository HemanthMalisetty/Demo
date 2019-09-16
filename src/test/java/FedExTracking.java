import com.applitools.eyes.*;
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

    private static final String BATCH_NAME = "FedEx";
    private static final String BATCH_ID = params.BATCH_ID;



    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @Test(priority = 1, alwaysRun = true, enabled = true)
    public void Golden_1(String platformName ,String platformVersion,
                         String browserName, String browserVersion) {

        try {
            eyes.open(driver, "FedEx Golden_1", "Golden_1", new RectangleSize(1200, 800));

            driver.get("https://www.fedex.com/en-us/home.html");
            utils.page.suspend(4000);
            eyes.check("Home Page", Target.window().fully());

            TestResults testResult = eyes.close(false);
            System.out.println("Applitools Test Results");
            System.out.println(testResult.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @Test(priority = 1, alwaysRun = true, enabled = true)
    public void Golden_2(String platformName ,String platformVersion,
                         String browserName, String browserVersion) {


        try {
            eyes.open(driver, "FedEx Golden_2", "Golden_2", new RectangleSize(1200, 800));

            driver.get("https://www.fedex.com/en-us/home.html");
            utils.page.suspend(4000);
            eyes.check("Home Page", Target.window().fully());

            driver.findElement(By.cssSelector("div:nth-child(1) > div.fxg-cube__content > svg")).click();
            utils.page.suspend(4000);
            eyes.check("Rate & Ship", Target.window().fully());

            TestResults testResult = eyes.close(false);
            System.out.println("Applitools Test Results");
            System.out.println(testResult.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @Test(priority = 1, alwaysRun = true, enabled = true)
    public void Golden_3(String platformName ,String platformVersion,
                         String browserName, String browserVersion) {

        try {
            eyes.open(driver,"FedEx Golden_3", "Golden_3", new RectangleSize(1200, 800));

            driver.get("https://www.fedex.com/en-us/home.html");
            utils.page.suspend(4000);
            eyes.check("Home Page", Target.window().fully());

            driver.findElement(By.name("trackingnumber")).click();
            driver.findElement(By.name("trackingnumber")).sendKeys("772803780072");
            utils.page.suspend(2000);
            driver.findElement(By.id("btnSingleTrack")).click();
            utils.page.suspend(4000);
            eyes.check("Tracking 772803780072", Target.window().fully());

            /*
            driver.findElement(By.name("trackingnumber")).click();
            driver.findElement(By.name("trackingnumber")).clear();
            driver.findElement(By.name("trackingnumber")).sendKeys("787658126565");
            utils.page.suspend(2000);

            driver.findElement(By.id("btnSingleTrack")).click();   //switch between these two to go to the wrong page

            utils.page.suspend(4000);
            eyes.check("Tracking 787658126565", Target.window().fully());
            */

            TestResults testResult = eyes.close(false);
            System.out.println("Applitools Test Results");
            System.out.println(testResult.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @Test(priority = 1, alwaysRun = true, enabled = true)
    public void Brand_1(String platformName ,String platformVersion,
                        String browserName, String browserVersion) {

        try {
            eyes.open(driver,"FedEx Brand_1", "Brand_1", new RectangleSize(1200, 800));

            driver.get("https://brand.fedex.com/login.html");
            eyes.check("Home Page", Target.window().fully());

            driver.get("https://brand.fedex.com/login.html");
            driver.findElement(By.id("ou")).click();
            driver.findElement(By.id("input-username")).click();
            driver.findElement(By.id("input-password")).sendKeys("Wipro123");
            driver.findElement(By.id("input-username")).sendKeys("PRODTESTBRAND1");
            eyes.check("Login", Target.window().fully());

            TestResults testResult = eyes.close(false);
            System.out.println("Applitools Test Results");
            System.out.println(testResult.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @Test(priority = 1, alwaysRun = true, enabled = true)
    public void Brand_2(String platformName ,String platformVersion,
                         String browserName, String browserVersion) {

        try {
            eyes.open(driver,"FedEx Brand_2", "Brand_2", new RectangleSize(1200, 800));

            driver.get("https://brand.fedex.com/login.html");
            eyes.check("Home Page", Target.window().fully());

            driver.get("https://brand.fedex.com/login.html");
            driver.findElement(By.id("ou")).click();
            driver.findElement(By.id("input-username")).click();
            driver.findElement(By.id("input-password")).sendKeys("Wipro123");
            driver.findElement(By.id("input-username")).sendKeys("PRODTESTBRAND1");
            eyes.check("Login", Target.window().fully());

            driver.findElement(By.id("input-submit1")).click();
            driver.findElement(By.name("search")).click();
            driver.findElement(By.name("search")).sendKeys("Truck");
            driver.findElement(By.cssSelector(".searchmagnifiericon")).click();
            utils.page.suspend(10000);
            eyes.check("Search", Target.window().fully());

            driver.findElement(By.cssSelector(".clearfix:nth-child(7) .fx-asset-thumbnail > img")).click();
            eyes.check("Select", Target.window().fully());

            driver.findElement(By.cssSelector(".text-center > .btn-default")).click();

            driver.findElement(By.cssSelector(".text-center > .btn-default")).click();
            eyes.check("Cart", Target.window().fully());

            TestResults testResult = eyes.close(false);
            System.out.println("Applitools Test Results");
            System.out.println(testResult.toString());
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
        //eyes.setLogHandler(new StdoutLogHandler(false));

        BatchInfo batchInfo = new BatchInfo(BATCH_NAME);
        if(BATCH_ID!=null) batchInfo.setId(BATCH_ID);
        eyes.setBatch(batchInfo);

        driver = utils.drivers.getLocalChrome(threadId);
        driver.manage().timeouts().setScriptTimeout(90, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);

        //Used to compare browsers against each other
        //eyes.setBaselineEnvName(<tag string here>);

        eyes.setMatchLevel(params.MATCH_MODE);
        eyes.setStitchMode(StitchMode.CSS);
        eyes.setForceFullPageScreenshot(false);
        if(params.FULL_SCREEN) eyes.setForceFullPageScreenshot(true);
        eyes.setSendDom(true);

        //Allows for filtering dashboard view
        eyes.addProperty("Sample Group", "YES");

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
