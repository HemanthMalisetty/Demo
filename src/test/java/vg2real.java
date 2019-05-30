import com.applitools.eyes.*;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.TestResultSummary;
import com.applitools.eyes.visualgrid.services.EyesRunner;
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
import java.util.Random;

public class vg2real {


    protected RemoteWebDriver driver;

    protected Target target;

    private EyesRunner visualGridRunner;
    private Configuration renderConfig = new Configuration();
    private Eyes eyes;
    private Eyes eyesReal;
    private BatchInfo batchInfo;
    private String[] arr = new String[0];

    private Random rand = new Random();

    private static final String BATCH_NAME = params.BATCH_NAME;
    private static final String BATCH_ID = params.BATCH_ID;
    private static final String APP_NAME = params.APP_NAME;
    private static final String TEST_NAME = params.TEST_NAME;

    /* How to Use
        Must have a new test name to gather fresh VG baseline
        Comparison will have Checkpoint environment name "Real"
        It will be the second test in the results
        list urls  */

    public void eyesVG(){

        Integer i=0;
        long before;

        ImageMatchSettings ims = new ImageMatchSettings();
        ims.setMatchLevel(params.MATCH_MODE);
        //ims.setEnablePatterns(true);
        //ims.setUseDom(true);

        FileLogger logHandler = new FileLogger("log/eyes_vg2real.log", false, true);

        visualGridRunner = new VisualGridRunner(10);
        visualGridRunner.setLogHandler(logHandler);
        visualGridRunner.getLogger().log("enter");
        visualGridRunner.setServerUrl(params.EYES_URL);

        renderConfig.setDefaultMatchSettings(ims);
        renderConfig.setAppName(APP_NAME);
        renderConfig.setBatch(batchInfo);
        renderConfig.setSendDom(true);
        renderConfig.setTestName(TEST_NAME);
        renderConfig.addBrowser(1200, 600, BrowserType.CHROME);

        eyes = new Eyes(visualGridRunner);
        eyes.setConfiguration(renderConfig);
        eyes.setApiKey(params.EYES_KEY);
        eyes.setLogHandler(new FileLogger("log/vg2realeyes.log",true,true));
        eyes.setMatchLevel(params.MATCH_MODE);
        eyes.setSaveNewTests(true);
        eyes.setBaselineEnvName("VG1");

        System.out.println("===============================================");

        before = System.currentTimeMillis();

        for(i=0;i<arr.length;i++){
            System.out.println("Checking URL " + i + ": " + arr[i]);
            try {
                eyes.open(driver, APP_NAME, TEST_NAME, new RectangleSize(1200, 600));
                driver.get(arr[i]);
                //utils.page.changePageSingle(driver, "HTML", "VG");
                eyes.check(arr[i],
                        Target.window().fully());   // Check the entire page
            } catch (Exception e) {
                System.out.println("FAILED URL " + i + " in " + (System.currentTimeMillis() - before) + "ms");
                e.printStackTrace();
            }
        }
        System.out.println("Completed URL Check in " + ((System.currentTimeMillis() - before)) / 1000 + " seconds");
        System.out.println("Waiting for Visual Grid Rendering ...");
        before = System.currentTimeMillis();
        TestResultSummary allTestResults = visualGridRunner.getAllTestResults(false);
        System.out.println(allTestResults.toString());
        System.out.println("Completed Rendering in " + ((System.currentTimeMillis() - before)) / 1000 + " seconds");
    }

    public void eyesReal(){

        Integer i=0;
        long before;
        String threadId = Long.toString(Thread.currentThread().getId());

        eyesReal = utils.myeyes.getEyes(threadId);
        eyesReal.setLogHandler(new FileLogger("log/file.log",true,true));
        eyesReal.setServerUrl(params.EYES_URL);
        eyesReal.setBatch(batchInfo);
        eyesReal.setMatchLevel(params.MATCH_MODE);
        eyesReal.setStitchMode(StitchMode.CSS);
        eyesReal.setBaselineEnvName("VG1");

        System.out.println("===============================================");

        for(i=0;i<arr.length;i++){
            System.out.println("Checking URL " + i + ": " + arr[i] + 1);

            try {
                eyesReal.open(driver,APP_NAME, TEST_NAME, new RectangleSize(1200, 600));
                //utils.page.changePageSingle(driver, "VG", "HTML");
                eyesReal.check(arr[i],
                        Target.window().fully());   // Check the entire page


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        TestResults testResult = eyesReal.close(false);
        System.out.println(testResult);
    }

    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @Test(priority = 1, alwaysRun = true, enabled = true)
    public void CheckURL(String platformName ,String platformVersion,
                         String browserName, String browserVersion) {
        eyesVG();
        eyesReal();
    }

    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @BeforeClass(alwaysRun = true)
    public void baseBeforeClass(String platformName ,String platformVersion,
                                String browserName, String browserVersion) throws MalformedURLException {

        String threadId = Long.toString(Thread.currentThread().getId());

        batchInfo = new BatchInfo(BATCH_NAME);
        int batchId = rand.nextInt(99999);
        batchInfo.setId(Integer.toString(batchId));

        //driver = utils.drivers.getGrid(threadId, "chrome");
        driver = utils.drivers.getLocalChrome(threadId);

        driver.manage().timeouts().setScriptTimeout(90, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);

        buildUrlList();
        System.out.println("Begin batch name: " + BATCH_NAME + " (" + batchId + ")");
    }

    @AfterClass(alwaysRun = true)
    public void baseAfterClass() {

        if (driver != null) {
            long before = System.currentTimeMillis();
            eyes.abortIfNotClosed();
            eyesReal.abortIfNotClosed();
            driver.quit();
            System.out.println("Driver quit took " + (System.currentTimeMillis() - before) + "ms");
        }
    }

    private void buildUrlList(){
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
    }

}

