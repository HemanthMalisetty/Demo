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
    private static final String APP_NAME = "Fidelity Header";

    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @Test(priority = 1, alwaysRun = true, enabled = true)
    public void CheckURL(String platformName ,String platformVersion,
                         String browserName, String browserVersion) {

        Integer i=0;
        String testName = params.TEST_NAME;
        long before;

        eyes.setMatchLevel(params.MATCH_MODE);
       // eyes.setEnvName("Fidelity Header");
        eyes.setBranchName("Fidelity Header");
        renderConfig.setSendDom(true);
        renderConfig.setTestName(testName);


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
                    utils.page.clickLinkText(driver, "Accept Cookies");
                    utils.page.suspend(2000);
                } catch (Exception e){
                    System.out.println("Clear failed");
                };
                */

                utils.page.arrowDown(driver);
                utils.page.arrowUp(driver);

                /*
                eyes.check(arr[i],
                        Target.window());

                int upwards = 1000, downwards = 1000, toTheLeft = 1000, toTheRight = 1000;
                eyes.check(arr[i],
                            Target.window()
                            .floating(By.cssSelector("#pgnb > div.pbn > div.pnld > a"), upwards, downwards, toTheLeft, toTheRight)
                );
                */

                eyes.check(arr[1],
                        Target.region(By.cssSelector("#pgnb > div.pbn > div.pnld > a")));

            } catch (Exception e) {
                System.out.println("FAILED URL " + i + " in " + (System.currentTimeMillis() - before) + "ms");
                e.printStackTrace();
            }
        }
        System.out.println("Completed URL Check in " + ((System.currentTimeMillis() - before)) / 1000 + " seconds");
        System.out.println("Waiting for Visual Grid Rendering ...");

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

        visualGridRunner = new VisualGridRunner(500);
        visualGridRunner.setLogHandler(logHandler);
        visualGridRunner.getLogger().log("Starting Test");

        eyes = new Eyes(visualGridRunner);

        renderConfig.setServerUrl(params.EYES_URL);
        renderConfig.setAppName(APP_NAME);
        renderConfig.setBatch(batchInfo);
        renderConfig.setDefaultMatchSettings(ims);

        //renderConfig.setBaselineEnvName("Firefox");   // Change the baseline !!!!!

        renderConfig.addBrowser(600, 600, BrowserType.CHROME);
        renderConfig.addBrowser(1400, 600, BrowserType.CHROME);
        renderConfig.addBrowser(1600, 600, BrowserType.CHROME);

        renderConfig.addBrowser(600, 600, BrowserType.FIREFOX);
        renderConfig.addBrowser(1400, 600, BrowserType.FIREFOX);
        renderConfig.addBrowser(1600, 600, BrowserType.FIREFOX);

        renderConfig.addBrowser(600, 600, BrowserType.EDGE);
        renderConfig.addBrowser(1400, 600, BrowserType.EDGE);
        renderConfig.addBrowser(1600, 600, BrowserType.EDGE);

        renderConfig.addBrowser(600, 600, BrowserType.IE_10);
        renderConfig.addBrowser(1400, 600, BrowserType.IE_10);
        renderConfig.addBrowser(1600, 600, BrowserType.IE_10);

        renderConfig.addBrowser(600, 600, BrowserType.IE_11);
        renderConfig.addBrowser(1400, 600, BrowserType.IE_11);
        renderConfig.addBrowser(1600, 600, BrowserType.IE_11);

        /*
        renderConfig.addDeviceEmulation(DeviceName.iPad_Pro, ScreenOrientation.PORTRAIT);
        renderConfig.addDeviceEmulation(DeviceName.Nexus_10, ScreenOrientation.PORTRAIT);
        renderConfig.addDeviceEmulation(DeviceName.iPhone_X, ScreenOrientation.PORTRAIT);
        renderConfig.addDeviceEmulation(DeviceName.iPad, ScreenOrientation.PORTRAIT);
        renderConfig.addDeviceEmulation(DeviceName.Pixel_2_XL, ScreenOrientation.PORTRAIT);
*/

        eyes.setApiKey(params.EYES_KEY);
        eyes.setIsDisabled(params.DISABLE_EYES);
        eyes.setLogHandler(new FileLogger("log/eyes_only.log",true,true));
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

