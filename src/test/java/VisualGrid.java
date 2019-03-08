import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.FileLogger;
import com.applitools.eyes.MatchLevel;
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

public class VisualGrid {

    protected RemoteWebDriver driver;

    protected Target target;

    private VisualGridManager VisualGrid = new VisualGridManager(10);
    private RenderingConfiguration renderConfig = new RenderingConfiguration();
    private Eyes eyes = new Eyes(VisualGrid);

    private static final String BATCH_NAME = "Perdue Global VG 1";
    private static final String BATCH_ID = null;  //optional - setting will keep all tests in the same batch
    private static final String APP_NAME = "PerdueGlobal1DemoVG";


    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @Test(priority = 1, alwaysRun = true, enabled = true)
    public void CheckURL(String platformName ,String platformVersion,
                         String browserName, String browserVersion) {

        Integer i=0;
        String testName = "Perdue Global Demo VG 1";
        long before;

        eyes.setMatchLevel(MatchLevel.LAYOUT);
        renderConfig.setTestName(testName);

        eyes.open(driver, renderConfig);

        String[] arr = new String[0];
        try {
            Scanner sc = new Scanner(new File("resources/urls/PerdueGlobal.csv"));
            List<String> lines = new ArrayList<String>();
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
            arr = lines.toArray(new String[0]);
            System.out.println("URL's to check: " + arr.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(i=0;i<arr.length;i++){
            before = System.currentTimeMillis();
            System.out.println("Checking URL " + i + ": " + arr[i]);
            try {
                driver.get(arr[i]);
                eyes.check(arr[i], Target.window());
            } catch (Exception e) {
                System.out.println("FAILED URL " + i + " in " + (System.currentTimeMillis() - before) + "ms");
                e.printStackTrace();
            }
        }
        eyes.close();
        System.out.println("Waiting for Visual Grid Rendering ...");
        TestResultSummary allTestResults = VisualGrid.getAllTestResults();
        System.out.println("Results: " + allTestResults);
    }


    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @BeforeClass(alwaysRun = true)
    public void baseBeforeClass(String platformName ,String platformVersion,
                                String browserName, String browserVersion) throws MalformedURLException {

        String threadId = Long.toString(Thread.currentThread().getId());
        long before = System.currentTimeMillis();

        driver = utils.drivers.getLocalChrome(threadId);

        eyes.setApiKey(params.EYES_KEY);

        browserName = "Local Chrome";
        browserVersion = "Local Version";

        renderConfig.setAppName("APP_NAME");
        renderConfig.addBrowser(800,  600, RenderingConfiguration.BrowserType.CHROME);
        renderConfig.addBrowser(1200, 800, RenderingConfiguration.BrowserType.CHROME);
        renderConfig.addBrowser(1600, 800, RenderingConfiguration.BrowserType.CHROME);
        renderConfig.addBrowser(700,  500, RenderingConfiguration.BrowserType.FIREFOX);
        renderConfig.addBrowser(1200,  800, RenderingConfiguration.BrowserType.FIREFOX);
        renderConfig.addBrowser(1600,  800, RenderingConfiguration.BrowserType.FIREFOX);

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
}
