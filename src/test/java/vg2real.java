import com.applitools.eyes.*;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
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

    private Random rand = new Random();

    private static final String BATCH_NAME = params.BATCH_NAME;
    private static final String BATCH_ID = params.BATCH_ID;
    private static final String APP_NAME = params.APP_NAME;

    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @Test(priority = 1, alwaysRun = true, enabled = true)
    public void CheckURL(String platformName ,String platformVersion,
                         String browserName, String browserVersion) {

        Integer i=0;
        String testName = params.TEST_NAME;
        String eyesBLName;
        long before;

        eyes.setMatchLevel(params.MATCH_MODE);
        renderConfig.setSendDom(true);
        renderConfig.setTestName(testName);

        eyes.setConfiguration(renderConfig);


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
        eyes.setSaveNewTests(true);
        eyes.setEnvName(testName);
        eyes.setBaselineBranchName("VGComparisons");
        //System.out.println("BL Name: " + eyesBLName);

        //eyesReal.setEnvName(testName);
        eyesReal.setBaselineEnvName(testName);
        eyesReal.setBaselineBranchName("VGComparisons");
        //eyesReal.setBranchName("vg " + testName);

        for(i=0;i<arr.length;i++){
            System.out.println("Checking URL " + i + ": " + arr[i]);
            try {
                eyes.open(driver,APP_NAME, "VG " + testName, new RectangleSize(1200, 600));

                driver.get(arr[i]);
                //utils.page.arrowDown(driver);
                //utils.page.home(driver);
                //utils.page.suspend(2000);
                //utils.page.changePage(driver);
                //utils.page.changePageSingle(driver);
                eyes.check(arr[i],
                        Target.window().fully());   // Check the entire page

                System.out.println("Completed URL Check in " + ((System.currentTimeMillis() - before)) / 1000 + " seconds");
                System.out.println("Waiting for Visual Grid Rendering ...");
                before = System.currentTimeMillis();
                TestResultSummary allTestResults = visualGridRunner.getAllTestResults();
                System.out.println(allTestResults.toString());
                System.out.println("Completed Rendering in " + ((System.currentTimeMillis() - before)) / 1000 + " seconds");

                eyesReal.open(driver,"Real Browser", "Real Browser", new RectangleSize(1200, 600));
                eyesReal.check(arr[i],
                        Target.window().fully());   // Check the entire page
            } catch (Exception e) {
                System.out.println("FAILED URL " + i + " in " + (System.currentTimeMillis() - before) + "ms");
                e.printStackTrace();
            }

            TestResults testResult = eyesReal.close(false);
            System.out.println("=================================");
            System.out.println(testResult);
        }

    }

    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @BeforeClass(alwaysRun = true)
    public void baseBeforeClass(String platformName ,String platformVersion,
                                String browserName, String browserVersion) throws MalformedURLException {

        String threadId = Long.toString(Thread.currentThread().getId());

        ImageMatchSettings ims = new ImageMatchSettings();
        ims.setMatchLevel(params.MATCH_MODE);
        //ims.setEnablePatterns(true);
        //ims.setUseDom(true);

        driver = utils.drivers.getLocalChrome(threadId);
        driver.manage().timeouts().setScriptTimeout(90, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);

        FileLogger logHandler = new FileLogger("log/eyes_vg2real.log", false, true);

        BatchInfo batchInfo = new BatchInfo(BATCH_NAME);
        int batchId = rand.nextInt(99999);
        batchInfo.setId(Integer.toString(batchId));

        visualGridRunner = new VisualGridRunner(100);
        visualGridRunner.setLogHandler(logHandler);
        visualGridRunner.getLogger().log("enter");
        visualGridRunner.setServerUrl(params.EYES_URL);
        renderConfig.setAppName(APP_NAME);
        renderConfig.setBatch(batchInfo);
        renderConfig.setDefaultMatchSettings(ims);

        renderConfig.addBrowser(1200, 600, BrowserType.CHROME);

        eyes = new Eyes(visualGridRunner);

        eyes.setApiKey(params.EYES_KEY);
        eyes.setIsDisabled(params.DISABLE_EYES);
        eyes.setLogHandler(new FileLogger("log/vg2realeyes.log",true,true));

        eyesReal = utils.myeyes.getEyes(threadId);
        eyesReal.setLogHandler(new FileLogger("log/file.log",true,true));
        eyesReal.setServerUrl(params.EYES_URL);
        eyesReal.setBatch(batchInfo);
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

