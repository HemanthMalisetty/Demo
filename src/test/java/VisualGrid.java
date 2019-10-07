import com.applitools.eyes.*;


import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;

import com.applitools.eyes.TestResultsSummary;


import com.applitools.eyes.visualgrid.services.VisualGridRunner;


import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.params;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class VisualGrid {


    protected RemoteWebDriver driver;

    protected Target target;

    private EyesRunner visualGridRunner;
    private Configuration renderConfig = new Configuration();
    private Eyes eyes;

    private static final String BATCH_NAME = params.BATCH_NAME;
    private static final String BATCH_ID = params.BATCH_ID;
    private static final String APP_NAME = params.APP_NAME;

    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @Test(priority = 1, alwaysRun = true, enabled = true)
    public void CheckURL(String platformName ,String platformVersion,
                         String browserName, String browserVersion) {

        Integer i=0;
        String testName = params.TEST_NAME;
        long before;

        eyes.setMatchLevel(params.MATCH_MODE);
        renderConfig.setSendDom(true);
        renderConfig.setTestName(testName);

        //eyes.setBaselineEnvName("Chrome");   // change in the renderconfig

        eyes.setConfiguration(renderConfig);
        eyes.open(driver,APP_NAME, testName, new RectangleSize(1400, 900));

        String[] arr = new String[0];
        try {
            Scanner sc = new Scanner(new File("src/main/resources/" + params.URL_FILE));
            List<String> lines = new ArrayList<String>();
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
            arr = lines.toArray(new String[0]);
            System.out.println("URL's to check: " + arr.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        before = System.currentTimeMillis();
        for(i=0;i<arr.length;i++){
            System.out.println("Checking URL " + i + ": " + arr[i]);
            try {
                driver.get(arr[i]);


                //clear cookie warning or floating toolbar or whatever needs cleanup
                /*
                try{
                    System.out.println("Clearing floater");

                    String jscript = "var x = document.getElementsByClassName('container container--trending'); " +
                        "x[0].style.display = 'none';";
                    driver.executeScript(jscript);

                    driver.findElement(By.cssSelector("body > div.container.container--trending > label")).click();
                    System.out.println("Clear succeeded");
                    utils.page.suspend(2000);
                } catch (Exception e){
                    System.out.println("Clear failed");
                };
                   */

                utils.page.suspend(1000);
                utils.page.arrowDown(driver);
                utils.page.arrowUp(driver);
                //utils.page.home(driver);
                //utils.page.suspend(2000);
                utils.page.changePage(driver);
                utils.page.changePageSingle(driver, "q", "g");

                utils.page.suspend(2000);

                eyes.check(arr[i],
                        Target.window().fully());

                //.region(By.cssSelector("body > div.content-wrapper > div.main > div:nth-child(10) > div > div > div > div")));   // Check the entire page
                //eyes.check(arr[i],
                //        Target.region(By.cssSelector("#mw-content-text > div > table.infobox.hproduct > tbody > tr:nth-child(1) > td > a > img")));

            } catch (Exception e) {
                System.out.println("FAILED URL " + i + " in " + (System.currentTimeMillis() - before) + "ms");
                e.printStackTrace();
            }
        }
        System.out.println("Completed URL Check in " + ((System.currentTimeMillis() - before)) / 1000 + " seconds");
        System.out.println("Waiting for Visual Grid Rendering ...");

        before = System.currentTimeMillis();

        eyes.close(false);
        TestResultsSummary allTestResults = visualGridRunner.getAllTestResults(false);
        System.out.println(allTestResults.toString());
        System.out.println("Completed Rendering in " + ((System.currentTimeMillis() - before)) / 1000 + " seconds");

    }

    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @BeforeClass(alwaysRun = true)
    public void baseBeforeClass(String platformName ,String platformVersion,
                                String browserName, String browserVersion) throws MalformedURLException {

        String threadId = Long.toString(Thread.currentThread().getId());

        ImageMatchSettings ims = new ImageMatchSettings();
        ims.setMatchLevel(params.MATCH_MODE);
        ims.setEnablePatterns(true);
        ims.setUseDom(true);

        driver = utils.drivers.getLocalChrome(threadId);
        driver.manage().timeouts().setScriptTimeout(90, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);

        FileLogger logHandler = new FileLogger("log/eyes_vg.log", false, true);

        BatchInfo batchInfo = new BatchInfo(BATCH_NAME);
        if(BATCH_ID!=null) batchInfo.setId(BATCH_ID);

        String sequenceName = "Alpha";
        batchInfo.setSequenceName(sequenceName);

        visualGridRunner = new VisualGridRunner(100);
        visualGridRunner.setLogHandler(logHandler);
        visualGridRunner.getLogger().log("Starting Test");

        renderConfig.setServerUrl(params.EYES_URL);
        renderConfig.setAppName(APP_NAME);
        renderConfig.setBatch(batchInfo);
        renderConfig.setDefaultMatchSettings(ims);

        //renderConfig.setBaselineEnvName("Chrome");   // Change the baseline !!!!!

        renderConfig.addBrowser(1100, 600, BrowserType.CHROME);

        renderConfig.addBrowser(1400, 600, BrowserType.EDGE);
        renderConfig.addBrowser(1400, 600, BrowserType.FIREFOX);
        renderConfig.addBrowser(1400, 600, BrowserType.CHROME);
        renderConfig.addBrowser(1000, 600, BrowserType.CHROME);
        renderConfig.addBrowser(1100, 600, BrowserType.CHROME);
        renderConfig.addBrowser(1200, 600, BrowserType.CHROME);

        renderConfig.addBrowser(400, 600, BrowserType.CHROME);
        renderConfig.addBrowser(500, 600, BrowserType.CHROME);
        renderConfig.addBrowser(600, 600, BrowserType.CHROME);
        renderConfig.addBrowser(700, 600, BrowserType.CHROME);
        renderConfig.addBrowser(800, 600, BrowserType.CHROME);
        renderConfig.addBrowser(900, 600, BrowserType.CHROME);
        renderConfig.addBrowser(1000, 600, BrowserType.CHROME);
        renderConfig.addBrowser(1100, 600, BrowserType.CHROME);
        renderConfig.addBrowser(1200, 600, BrowserType.CHROME);
        renderConfig.addBrowser(1300, 600, BrowserType.CHROME);
        renderConfig.addBrowser(1400, 600, BrowserType.CHROME);
        renderConfig.addBrowser(1500, 600, BrowserType.CHROME);
        renderConfig.addBrowser(1600, 600, BrowserType.CHROME);

        renderConfig.addBrowser(1800, 600, BrowserType.CHROME);
        renderConfig.addBrowser(1900, 600, BrowserType.CHROME);
        renderConfig.addBrowser(2000, 600, BrowserType.CHROME);
        renderConfig.addBrowser(2500, 600, BrowserType.CHROME);
        renderConfig.addBrowser(3000, 600, BrowserType.CHROME);

        renderConfig.addBrowser(600,  500, BrowserType.FIREFOX);
        renderConfig.addBrowser(700, 600, BrowserType.FIREFOX);
        renderConfig.addBrowser(800, 600, BrowserType.FIREFOX);
        renderConfig.addBrowser(900,  600, BrowserType.FIREFOX);
        renderConfig.addBrowser(1000, 600, BrowserType.FIREFOX);
        renderConfig.addBrowser(1200, 600, BrowserType.FIREFOX);
        renderConfig.addBrowser(1600, 600, BrowserType.FIREFOX);

        renderConfig.addBrowser(800,  600, BrowserType.EDGE);
        renderConfig.addBrowser(1200, 600, BrowserType.EDGE);
        renderConfig.addBrowser(1600, 500, BrowserType.EDGE);

        /*
        renderConfig.addBrowser(1400, 600, BrowserType.IE_10);
        renderConfig.addBrowser(1400, 600, BrowserType.IE_11);
        renderConfig.addBrowser(800,  600, BrowserType.IE_11);
        renderConfig.addBrowser(1200,  600, BrowserType.IE_11);
        renderConfig.addBrowser(1600,  500, BrowserType.IE_11);
        */

        renderConfig.addDeviceEmulation(DeviceName.iPad_Pro, ScreenOrientation.PORTRAIT);
        renderConfig.addDeviceEmulation(DeviceName.Nexus_10, ScreenOrientation.PORTRAIT);
        renderConfig.addDeviceEmulation(DeviceName.iPhone_X, ScreenOrientation.PORTRAIT);
        renderConfig.addDeviceEmulation(DeviceName.iPad, ScreenOrientation.PORTRAIT);
        renderConfig.addDeviceEmulation(DeviceName.Pixel_2_XL, ScreenOrientation.PORTRAIT);


        eyes = new Eyes(visualGridRunner);

        eyes.setApiKey(params.EYES_KEY);
        eyes.setIsDisabled(params.DISABLE_EYES);
        eyes.setLogHandler(new FileLogger("log/eyes.log",true,true));
        //eyes.addProperty("SANDBOX", "YES");


    }

    @AfterClass(alwaysRun = true)
    public void baseAfterClass() {

        if (driver != null) {
            long before = System.currentTimeMillis();
            //eyes.abortIfNotClosed(); // deprecated
            if(!eyes.getIsOpen()) eyes.close();
            driver.quit();
            System.out.println("Driver quit took " + (System.currentTimeMillis() - before) + "ms");
        }


    }

}

