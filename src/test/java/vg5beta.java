
/*
import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.FileLogger;
import com.applitools.eyes.rendering.Eyes;
import com.applitools.eyes.rendering.Target;
import com.applitools.eyes.visualGridClient.model.RenderingConfiguration;
import com.applitools.eyes.visualGridClient.model.TestResultSummary;
import com.applitools.eyes.visualGridClient.services.VisualGridManager;

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

*/


public class vg5beta {

    /*

    protected RemoteWebDriver driver;

    protected Target target;

    private VisualGridManager vg = new VisualGridManager(100);
    private RenderingConfiguration renderConfig = new RenderingConfiguration();
    private Eyes eyes = new Eyes(vg);

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
        renderConfig.setTestName(testName);

        eyes.open(driver, renderConfig);

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
                utils.page.arrowDown(driver);
                utils.page.home(driver);
                utils.page.suspend(5000);
                utils.page.changePage(driver);
                eyes.check(arr[i],
                        Target.window().fully());   // Check the entire page
            } catch (Exception e) {
                System.out.println("FAILED URL " + i + " in " + (System.currentTimeMillis() - before) + "ms");
                e.printStackTrace();
            }
        }
      //  eyes.close();
        System.out.println("Completed URL Check in " + ((System.currentTimeMillis() - before))/1000 + "s");
        System.out.println("Waiting for Visual Grid Rendering ...");
        before = System.currentTimeMillis();
        TestResultSummary allTestResults = vg31510.getAllTestResults();
        System.out.println("Completed Rendering in " + ((System.currentTimeMillis() - before))/1000 + "s");
        System.out.println("Results: " + allTestResults);

    }


    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @BeforeClass(alwaysRun = true)
    public void baseBeforeClass(String platformName ,String platformVersion,
                                String browserName, String browserVersion) throws MalformedURLException {

        String threadId = Long.toString(Thread.currentThread().getId());
        long before = System.currentTimeMillis();

        driver = utils.drivers.getLocalChrome(threadId);

        //driver = utils.drivers.getGrid(threadId, browserName);

        driver.manage().timeouts().setScriptTimeout(90, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);

        eyes.setApiKey(params.EYES_KEY);

        eyes.setIsDisabled(params.DISABLE_EYES);

        browserName = "Local Chrome";
        browserVersion = "Local Version";


        renderConfig.setAppName(APP_NAME);

        renderConfig.addBrowser(800,  600, RenderingConfiguration.BrowserType.CHROME);
        renderConfig.addBrowser(1024, 768, RenderingConfiguration.BrowserType.CHROME);
        renderConfig.addBrowser(1600, 500, RenderingConfiguration.BrowserType.CHROME);

        renderConfig.addBrowser(800,  600, RenderingConfiguration.BrowserType.FIREFOX);
        renderConfig.addBrowser(1024,  768, RenderingConfiguration.BrowserType.FIREFOX);
        renderConfig.addBrowser(1600,  500, RenderingConfiguration.BrowserType.FIREFOX);


        eyes.setLogHandler(new FileLogger("log/file.log",true,true));

        BatchInfo batchInfo = new BatchInfo(BATCH_NAME);
        if(BATCH_ID!=null) batchInfo.setId(BATCH_ID);
        eyes.setBatch(batchInfo);

        //Allows for filtering dashboard view
        //not yet implemented in VG SDK eyes.addProperty("SANDBOX", "YES");

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
*/
}

