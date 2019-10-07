import com.applitools.eyes.*;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.drivers;
import utils.myeyes;
import utils.params;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BBVA {

    protected RemoteWebDriver driver;
    protected Eyes eyes;

    private EyesRunner visualGridRunner;
    private Configuration renderConfig = new Configuration();

    private static final String BATCH_NAME = "BBVA VG";
    private static final String BATCH_ID = params.BATCH_ID;

    private static  final boolean isVG = true;


    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @Test(priority = 1, alwaysRun = true, enabled = true)
    public void BBVA_1(String platformName ,String platformVersion,
                         String browserName, String browserVersion) {

        try {
            openEyes(isVG, "BBVA", "BBVA", 1200, 1000);

            driver.get("https://bbvausa.com");
            utils.page.pageDown(driver);
            //utils.page.arrowUp(driver);
            //utils.page.home(driver);
            utils.page.changePage(driver);
            utils.page.changePageSingle(driver, "A", "B");

            eyes.check("Home Page", Target.window().fully()); // .ignoreDisplacements());

            //driver.findElementByCssSelector("li.apply > a").click();
            driver.get("https://www.bbvausa.com/digital-banking-services/apply.html");
            eyes.check("Apply Now", Target.window().fully().ignoreDisplacements());

            driver.findElementByCssSelector("#checking > div:nth-child(4) > a.btn.btn-aqua.btn-sm").click();
            ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(newTab.get(1));
            eyes.check("Apply Free Checking", Target.window().fully().ignoreDisplacements());

            closeEyes(isVG);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void openEyes(boolean isVG, String appName, String testName, int rx, int ry){
        System.out.println("Opening Eyes..." + testName);
        if(isVG) {
            eyes.open(driver, appName, testName);
        } else {
            eyes.open(driver, appName, testName, new RectangleSize(rx, ry));
        }
    }

    private void closeEyes(boolean isVG) {
        long before;
        System.out.println("Closing Eyes... ");
        if(isVG) {
            eyes.closeAsync();
        } else {
            TestResults testResult = eyes.close(false);
            System.out.println("Applitools Test Results");
            System.out.println(testResult.toString());
        }
    }

    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @BeforeClass(alwaysRun = true)
    public void baseBeforeClass(String platformName ,String platformVersion,
                                String browserName, String browserVersion) {

        String threadId = Long.toString(Thread.currentThread().getId());
        long before = System.currentTimeMillis();

        BatchInfo batchInfo = new BatchInfo(BATCH_NAME);
        if (BATCH_ID != null) batchInfo.setId(BATCH_ID);

        if(!isVG) {
            eyes = myeyes.getEyes(threadId);
            eyes.setLogHandler(new FileLogger("log/file.log", true, true));  //eyes.setLogHandler(new StdoutLogHandler(false));
            eyes.setBatch(batchInfo);
            eyes.setStitchMode(StitchMode.CSS);
            eyes.setForceFullPageScreenshot(false);
            if (params.FULL_SCREEN) eyes.setForceFullPageScreenshot(true);
            eyes.setSendDom(true);
            //Optional, used to compare browsers against each other
            //eyes.setBaselineEnvName(<tag string here>);
            //eyes.setMatchLevel(params.MATCH_MODE);
        }

        if(isVG) {

            renderConfig
                .setServerUrl(params.EYES_URL)
                .setBatch(batchInfo)
                .setSendDom(true)
                .setApiKey(params.EYES_KEY)
                .setViewportSize(new RectangleSize(1600, 900));

            
            renderConfig
                .addBrowser(1100, 600, BrowserType.CHROME)
                .addDeviceEmulation(DeviceName.iPad_Pro, ScreenOrientation.PORTRAIT)
                .addDeviceEmulation(DeviceName.Nexus_10, ScreenOrientation.PORTRAIT)

                .addBrowser(1400, 600, BrowserType.IE_10)
                .addBrowser(1400, 600, BrowserType.IE_11)
                .addBrowser(1400, 600, BrowserType.EDGE)
                .addBrowser(1400, 600, BrowserType.FIREFOX)
                .addBrowser(1400, 600, BrowserType.CHROME)
                .addBrowser(1000, 600, BrowserType.CHROME)
                .addBrowser(1100, 600, BrowserType.CHROME)
                .addBrowser(1200, 600, BrowserType.CHROME)

                .addBrowser(400, 600, BrowserType.CHROME)
                .addBrowser(500, 600, BrowserType.CHROME)
                .addBrowser(600, 600, BrowserType.CHROME)
                .addBrowser(700, 600, BrowserType.CHROME)
                .addBrowser(800, 600, BrowserType.CHROME)
                .addBrowser(900, 600, BrowserType.CHROME)
                .addBrowser(1000, 600, BrowserType.CHROME)
                .addBrowser(1100, 600, BrowserType.CHROME)
                .addBrowser(1200, 600, BrowserType.CHROME)
                .addBrowser(1300, 600, BrowserType.CHROME)
                .addBrowser(1400, 600, BrowserType.CHROME)
                .addBrowser(1500, 600, BrowserType.CHROME)
                .addBrowser(1600, 600, BrowserType.CHROME)

                .addBrowser(600, 500, BrowserType.FIREFOX)
                .addBrowser(700, 600, BrowserType.FIREFOX)
                .addBrowser(800, 600, BrowserType.FIREFOX)
                .addBrowser(900, 600, BrowserType.FIREFOX)
                .addBrowser(1000, 600, BrowserType.FIREFOX)
                .addBrowser(1200, 600, BrowserType.FIREFOX)
                .addBrowser(1600, 600, BrowserType.FIREFOX)

                .addBrowser(800, 600, BrowserType.EDGE)
                .addBrowser(1200, 600, BrowserType.EDGE)
                .addBrowser(1600, 500, BrowserType.EDGE)

                .addBrowser(800, 600, BrowserType.IE_11)
                .addBrowser(1200, 600, BrowserType.IE_11)
                .addBrowser(1600, 500, BrowserType.IE_11);


            visualGridRunner = new VisualGridRunner(100);
            visualGridRunner.setLogHandler(new FileLogger("log/file.log", true, true));
            visualGridRunner.getLogger().log("Starting Test");

            eyes = new Eyes(visualGridRunner);

            eyes.setConfiguration(renderConfig);
        }

        //Allows for filtering dashboard view
        //eyes.addProperty("Sample Group", "YES");

        driver = drivers.getLocalChrome(threadId);
        driver.manage().timeouts().setScriptTimeout(90, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);

        System.out.println("START THREAD ID - " + Thread.currentThread().getId() + " " + browserName + " " + browserVersion);
        System.out.println("baseBeforeClass took " + (System.currentTimeMillis() - before) + "ms");
    }

    @AfterClass(alwaysRun = true)
    public void baseAfterClass() {

        if(isVG) {
            driver.quit();
            System.out.println("Go to the dashboard to see VG tests finishing up...");
            TestResultsSummary allTestResults = visualGridRunner.getAllTestResults(false);
            System.out.println(allTestResults.toString());
        }

        if (driver != null) {
            long before = System.currentTimeMillis();
            //eyes.abortIfNotClosed(); // deprecated
            //Wait until the test results are available and retrieve them
            if(!eyes.getIsOpen()) eyes.close(false);
            driver.quit();
            System.out.println("Driver quit took " + (System.currentTimeMillis() - before) + "ms");
        }

    }
}
