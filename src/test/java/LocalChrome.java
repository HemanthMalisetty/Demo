import com.applitools.eyes.*;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.params;
import utils.ResultsHandler;
import utils.ResultStatus;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class LocalChrome {

    protected RemoteWebDriver driver;
    protected Eyes eyes;

    private static final String BATCH_NAME = params.BATCH_NAME;
    private static final String BATCH_ID = params.BATCH_ID;
    private static final String APP_NAME = params.APP_NAME;

    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @Test(priority = 1, alwaysRun = true, enabled = true)
    public void CheckURL(String platformName ,String platformVersion,
                         String browserName, String browserVersion) throws Exception {

        Integer i=0;
        String testName = params.TEST_NAME;
        long before;

        //Force to check against specific baseline branch
        //eyes.setBaselineBranchName("CIBC");
        //Force; to check with the forced baselines corresponding environment
        //eyes.setBaselineEnvName("FF1200x900");
        //eyes.setAppName();

        //Set the environment name in the test batch results
        //eyes.setEnvName(driver.getCapabilities().getBrowserName() + " " + driver.getCapabilities().getVersion());
        //eyes.setEnvName("CIBC");

        eyes.setMatchLevel(params.MATCH_MODE);
        eyes.setStitchMode(StitchMode.CSS);
        eyes.setForceFullPageScreenshot(false);
        if(params.FULL_SCREEN) eyes.setForceFullPageScreenshot(true);
        eyes.setSendDom(true);
        eyes.open(driver,APP_NAME, testName, new RectangleSize(1200, 1040));

        tests.urlscan.scanlist(driver, eyes, params.URL_FILE);

        TestResults testResult = eyes.close(false);
        exportImages(testResult);

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
        eyes.setLogHandler(new FileLogger("log/Eyes_LC.log",true,true));
        eyes.setServerUrl(params.EYES_URL);

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
            //eyes.abortIfNotClosed(); // deprecated
            if(!eyes.getIsOpen()) eyes.abort();
            driver.quit();
            System.out.println("Driver quit took " + (System.currentTimeMillis() - before) + "ms");
        }


    }

    public void exportImages(TestResults testResult){

        System.out.println("This is the link for the Batch Result: "+testResult.getUrl());

        try {
            ResultsHandler testResultHandler = new ResultsHandler(testResult, params.EYES_READ_KEY);
    //            ApplitoolsTestResultsHandler testResultHandler = new ApplitoolsTestResultsHandler(testResult, System.getenv("Applitools_ViewKey"),"ProxyServerURL","ProxyServerPort");
    //            ApplitoolsTestResultsHandler testResultHandler = new ApplitoolsTestResultsHandler(testResult, System.getenv("Applitools_ViewKey"),"ProxyServerURL","ProxyServerPort","ProxyServerUserName","ProxyServerPassword");

            List<BufferedImage>  base = testResultHandler.getBaselineBufferedImages();  // get Baseline Images as BufferedImage
            List<BufferedImage>  curr = testResultHandler.getCurrentBufferedImages();   // get Current Images as BufferedImage
            List<BufferedImage> diff = testResultHandler.getDiffsBufferedImages();      // get Diff Images as BufferedImage
            String[] names = testResultHandler.getStepsNames();

            String PDFName = "./log/" + testResultHandler.getTestName() + ".pdf";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
            Calendar timeStamp = testResult.getStartedAt();
            String ts = sdf.format(timeStamp.getTime());

            utils.PDF.makePDF(base,diff,names, testResult.getBatchName(), testResultHandler.getTestName(), PDFName, ts);

            System.out.println("PDF created: " + PDFName);
/*
            // Optional Setting this prefix will determine the structure of the repository for the downloaded images.
            testResultHandler.SetPathPrefixStructure("TestName/AppName/viewport/hostingOS/hostingApp");
            //Link to test/step result
//            System.out.println("This is the url to the first step " +testResultHandler.getLinkToStep(1));
            testResultHandler.downloadImages("./log/images");                // Download both the Baseline and the Current images to the folder specified in Path.
            testResultHandler.downloadDiffs("./log/diffs");                 // Download Diffs to the folder specified in Path.
            testResultHandler.downloadAnimateGiff("./log/gif");           // Download Animated GIf to the folder specified in Path.
*/
    //            Get the status of each step (Pass / Unresolved / New / Missing).
            ResultStatus[] results = testResultHandler.calculateStepResults();
            for (int i=0 ; i< results.length; i++){
                System.out.println("The result of step "+(i+1)+" is "+ results[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //===============
    }

}
