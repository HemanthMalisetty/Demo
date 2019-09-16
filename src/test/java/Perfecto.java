import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.FileLogger;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;

import com.applitools.eyes.selenium.fluent.Target;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.params;

import java.util.concurrent.TimeUnit;

import java.util.*;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class Perfecto {

    protected RemoteWebDriver driver;

    protected Eyes eyes;

    private static final String BATCH_NAME = params.BATCH_NAME;
    private static final String BATCH_ID = params.BATCH_ID;
    private static final String APP_NAME = params.APP_NAME;


    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion", "deviceName"})
    @Test(priority = 1, alwaysRun = true, enabled = true)
    public void CheckURL(String platformName ,String platformVersion,
                         String browserName, String browserVersion, String deviceName) {

        Integer i=0;
        String testName = params.TEST_NAME;
        long before;

        //Force to check against specific baseline branch
        //eyes.setBaselineBranchName("Firefox");
        //Force; to check with the forced baselines corresponding environment
        //eyes.setBaselineEnvName("FF1200x900");
        //eyes.setAppName();

        //Set the environment name in the test batch results
        //eyes.setEnvName(driver.getCapabilities().getBrowserName() + " " + driver.getCapabilities().getVersion());

        eyes.setMatchLevel(params.MATCH_MODE);
        eyes.setStitchMode(StitchMode.SCROLL);
        eyes.setForceFullPageScreenshot(true);
        eyes.setSendDom(true);
        eyes.setIsDisabled(false);

        clickHomeButton(driver);
        if(platformName.matches("Android")) {
            //open Chrome
            openApp(driver, "Chrome");
        }

        eyes.open(driver,APP_NAME, testName);

        tests.urlscan.scanListMobile(driver, eyes, params.URL_FILE);

        //Now lets open some generic apps
        if(platformName.matches("Android")) {
            openApp(driver, "YouTube");
            suspend(8000);
            before = System.currentTimeMillis();
            eyes.check("YouTube", Target.window());
            System.out.println("eyes Android took " + (System.currentTimeMillis() - before) + "ms");
            openApp(driver, "Clock");
            suspend(8000);
            before = System.currentTimeMillis();
            eyes.check("Clock", Target.window());
            System.out.println("eyes Android took " + (System.currentTimeMillis() - before) + "ms");
            openApp(driver, "Calculator");
            suspend(8000);
            before = System.currentTimeMillis();
            eyes.check("Calculator", Target.window());
            System.out.println("eyes Android took " + (System.currentTimeMillis() - before) + "ms");
        } else {
            openApp(driver, "Maps");
            suspend(8000);
            before = System.currentTimeMillis();
            eyes.check("Maps", Target.window());
            System.out.println("eyes iOS took " + (System.currentTimeMillis() - before) + "ms");
            openApp(driver, "Weather");
            suspend(8000);
            before = System.currentTimeMillis();
            eyes.check("Weather", Target.window());
            System.out.println("eyes iOS took " + (System.currentTimeMillis() - before) + "ms");
            openApp(driver, "Stocks");
            suspend(8000);
            before = System.currentTimeMillis();
            eyes.check("Stocks", Target.window());
            System.out.println("eyes iOS took " + (System.currentTimeMillis() - before) + "ms");
        }

        TestResults testResult = eyes.close(false);
        System.out.println("Applitools Test Results");
        System.out.println(testResult.toString());
    }


    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion", "deviceName"})
    @BeforeClass(alwaysRun = true)
    public void baseBeforeClass(String platformName ,String platformVersion,
                                String browserName, String browserVersion, String deviceName) {

        String threadId = Long.toString(Thread.currentThread().getId());
        long before = System.currentTimeMillis();

        eyes = utils.myeyes.getEyes(threadId);
        eyes.setLogHandler(new FileLogger("log/file.log",true,true));
        eyes.setServerUrl(params.EYES_URL);

        eyes.setHostApp(browserName);

        BatchInfo batchInfo = new BatchInfo(BATCH_NAME);
        if(BATCH_ID!=null) batchInfo.setId(BATCH_ID);
        eyes.setBatch(batchInfo);

        driver = utils.drivers.getPerfecto(threadId, deviceName, platformName, browserName);
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

    //Below are a bunch of functions wrapping proprietary Perfecto functions
    //Below are a bunch of functions wrapping proprietary Perfecto functions

    public static void clickHomeButton(RemoteWebDriver webdriver) {
        String command = "mobile:presskey";
        Map<String, Object> params = new HashMap<>();
        params.put("keySequence", "HOME");
        try {
            webdriver.executeScript(command, params);
        } catch (WebDriverException e) { }
    }

    public static void clickButton(RemoteWebDriver webdriver, String button) {
        String command = "mobile:presskey";
        Map<String, Object> params = new HashMap<>();
        params.put("keySequence", button);
        try {
            webdriver.executeScript(command, params);
        } catch (WebDriverException e) { }
    }


    public static void openApp(RemoteWebDriver webdriver,String appName) {
        String command = "mobile:application:open";
        Map<String, Object> params = new HashMap<>();
        params.put("name", appName);
        params.put("timeout", "5");
        try{
            webdriver.executeScript(command, params);
            params.clear();
        } catch (WebDriverException e) { }
    }



    public static void closeApp(RemoteWebDriver webdriver,String appName) {
        String command = "mobile:application:close";
        Map<String, Object> params = new HashMap<>();
        params.put("name", appName);
        try{
            Object res = webdriver.executeScript(command, params);
            params.clear();
        } catch (WebDriverException e) {
            System.out.println("Warning: Cannot close app");
        }
    }



    public static void uninstallApp(RemoteWebDriver webdriver,String appIdentifier) {
        String command = "mobile:application:uninstall";
        Map<String, Object> params = new HashMap<>();
        params.put("identifier", appIdentifier);
        try{
            Object res = webdriver.executeScript(command, params);
            params.clear();
        } catch (WebDriverException e) {
            System.out.println("Warning: Cannot uninstall app");
        }
    }



    public static void clickButtonText(RemoteWebDriver webdriver,String buttonText) throws Exception{
        String command = "mobile:button-text:click";
        Map<String, Object> params = new HashMap<>();
        suspend(500);
        params.put("label", buttonText);
        params.put("timeout", "5");
        params.put("source", "camera");
        try{
            webdriver.executeScript(command, params);
        } catch (WebDriverException e) { }
    }


    public static Boolean TextCheckpoint(RemoteWebDriver webdriver,String checkpointText) {
        String command = "mobile:checkpoint:text";
        Map<String, Object> params = new HashMap<>();
        params.put("content", checkpointText);
        params.put("timeout", "5");
        params.put("source", "camera");
        params.put("ignorecase", "case");  //this means do not ignore case
        try{
            suspend(500);
            Object result = webdriver.executeScript(command, params);
            Boolean resultBool = Boolean.valueOf(result.toString());
            // try second time
            if(!resultBool){
                suspend(500);
                result = webdriver.executeScript(command, params);
                resultBool = Boolean.valueOf(result.toString());
            }
            return resultBool;
        } catch (WebDriverException e) { return false; }
    }


    public static void SetText(RemoteWebDriver webdriver,String objectLabel, String textToSet) {
        Map<String, Object> params = new HashMap<>();
        params.put("label", objectLabel);
        params.put("text", textToSet);
        params.put("timeout", "15");
        params.put("source", "camera");
        try {
            webdriver.executeScript("mobile:edit-text:set", params);
            params.clear();
        } catch (WebDriverException e) { }
    }

    public static void SetTextWithNarrowView(RemoteWebDriver webdriver,String objectLabel, String textToSet, String top, String height, String left, String width) {
        Map<String, Object> params = new HashMap<>();
        params.put("label", objectLabel);
        params.put("text", textToSet);
        params.put("screen.top", top + "%");
        params.put("screen.height", height + "%");
        params.put("screen.left", left + "%");
        params.put("screen.width", width + "%");
        params.put("timeout", "15");
        params.put("source", "camera");
        try {
            webdriver.executeScript("mobile:edit-text:set", params);
            params.clear();
        } catch (WebDriverException e) { }
    }

    public static void suspend(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {

        }
    }

    public static void HoverClick(RemoteWebDriver driver, String MainMenuItemText, String SubMenuItem){
        //Hover over MainMenuItem and click on SubMenuItem that is revealed

        try {
            WebElement MainMenuItemLink = driver.findElementByLinkText(MainMenuItemText);
            WebElement SubMenuItemLink ; //= driver.findElementByXPath(SubMenuItem);
            Actions builder = new Actions(driver);
            builder.moveToElement(MainMenuItemLink).build().perform();

            WebDriverWait wait = new WebDriverWait(driver, 15);
            wait.ignoring(NoSuchElementException.class)
                    .ignoring(NoSuchMethodError.class)
                    .ignoring(StaleElementReferenceException.class);

            SubMenuItemLink = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(SubMenuItem)));
            SubMenuItemLink = wait.until(ExpectedConditions.elementToBeClickable(By
                    .xpath(SubMenuItem)));
            SubMenuItemLink.click();


        } catch (WebDriverException e) {

        }

    }

    public static boolean swipeScreen(RemoteWebDriver driver, final String direction) {

        String start = "";
        String end = "";
        switch(direction){
            case "up":
                start = "50%,80%";
                end = "50%,20%";
                break;
            case "down" :
                start = "50%,20%";
                end = "50%,80%";
                break;
            case "left" :
                start = "80%,50%";
                end = "20%,50%";
                break;
            case "right" :
                start = "20%,50%";
                end = "80%,50%";
                break;
        }

        try{

            Map<String, Object> params = new HashMap<>();
            params.put("start", start);
            params.put("end", end);
            Object result = driver.executeScript("mobile:touch:swipe", params);
            suspend(1000);

            System.out.println("~~~~~~~~~~~~~~~~~~~~~~ swipeScreen " + direction);
            return true;
        } catch (WebDriverException e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~ ERROR swipeScreen " + direction);
            return false;
        }

    }

    public static boolean clickElement(RemoteWebDriver driver, final String xpath) {

        Integer t=0;
        Integer max=30;
        for (t=1;t<=max;t++) {
            try {
                driver.findElementByXPath(xpath).click();
                //System.out.println("~~~~~~~~~~~~~~~~~~~~~~ SUCCESS clickElement - # tries - " + t);
                return true;
            } catch (WebDriverException e) {
                //System.out.println("~~~~~~~~~~~~~~~~~~~~~~ ERROR clickElement - # tries - " + t);
                if(t>max){
                    return false; }
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~ ERROR clickElement FAILED");
        return false;
    }

    public static boolean elementExists(RemoteWebDriver driver, final String xpath) {

        Integer t=0;
        Integer max=15;
        for (t=1;t<=max;t++) {
            try {
                WebElement element;
                WebDriverWait wait = new WebDriverWait(driver, 1);
                wait.ignoring(NoSuchElementException.class)
                        .ignoring(NoSuchMethodError.class)
                        .ignoring(StaleElementReferenceException.class);
                element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                //element.click();
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~ SUCCESS elementExists - # tries - " + t);
                return true;
            } catch (WebDriverException e) {
                //System.out.println("**** ERROR clickElement try " + t + " " + e.getMessage());
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~ ERROR elementExists - # tries - " + t);
                if(t>max){
                    return false; }
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~ ERROR elementExists FAILED");
        return false;

    }

    public static boolean enterText(RemoteWebDriver driver, String text, final String xpath) {

        Integer t=0;
        Integer max=5;
        for (t=1;t<=max;t++) {
            try {
                WebElement element;
                WebDriverWait wait = new WebDriverWait(driver, 1);
                wait.ignoring(NoSuchElementException.class)
                        .ignoring(NoSuchMethodError.class)
                        .ignoring(StaleElementReferenceException.class);
                element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                element.sendKeys(text);
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~ SUCCESS enterText - # tries - " + t);
                return true;
            } catch (WebDriverException e) {
                //System.out.println("**** ERROR clickElement try " + t + " " + e.getMessage());
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~ ERROR enterText - # tries - " + t);
                if(t>max){
                    return false; }
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~ ERROR enterText FAILED");
        return false;
    }

    public static boolean elementIsVisible(RemoteWebDriver driver, final String xpath) {
        try{
            WebDriverWait wait = new WebDriverWait(driver, 1);
            wait.ignoring(NoSuchElementException.class)
                    .ignoring(NoSuchMethodError.class)
                    .ignoring(StaleElementReferenceException.class);
            //WebElement e = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

            WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            //element.click();
            return true;
        } catch (WebDriverException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public static void logs(RemoteWebDriver driver, String action){
        try {
            Map<String, Object> params1 = new HashMap<>();
            if(action.matches("start")){
                Object result1 = driver.executeScript("mobile:logs:start", params1);
            }
            if(action.matches("stop")){
                Object result2 = driver.executeScript("mobile:logs:stop", params1);
            }
        } catch (Exception e) {
            System.out.println("ERROR: Could not capture logs");
            e.printStackTrace();
        }
    }

    public static void startVNetwork(RemoteWebDriver driver){
        try {
            Map<String, Object> params10 = new HashMap<>();
            params10.put("generateHarFile", "true");
            Object result10 = driver.executeScript("mobile:vnetwork:start", params10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> getContextHandles(RemoteWebDriver driver) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        List<String> contexts =  (List<String>) executeMethod.execute(DriverCommand.GET_CONTEXT_HANDLES, null);
        return contexts;
    }

    public static boolean checkTableFormat(RemoteWebDriver driver){
        Map<String, Object> params = new HashMap<>();
        Object result;
        Boolean res = true;
        String regexAlpha, regexNumeric;

        //  regex to match -$999.99       \-?\$\d*.\d\d
        //  regex to match -999.99%       \-?\d*.\d\d%
        //  regex to match 99,999,999       \-?\d*.\d\d%
        //  regex to match a line of numbers \$\d{0,3},?\d{0,3},?\d{0,3}.\d\d \+?\-?\$\d{0,3}.\d\d \+?\-?\d{0,3}.\d\d% \d{0,3},?\d{0,3},?\d{0,3}
        //  regex to match a string  \D+
        regexAlpha = "\\D+";
        regexNumeric = "\\$\\d{0,3},?\\d{0,3},?\\d{0,3}.\\d\\d \\+?\\-?\\$\\d{0,3}.\\d\\d \\+?\\-?\\d{0,3}.\\d\\d% \\d{0,3},?\\d{0,3},?\\d{0,3}";

        //Function to check the Market Mover table on and iPad

        params.clear();
        params.put("context", "body");
        params.put("source", "native");
        params.put("screen.top", "21%");
        params.put("screen.height", "80%");
        params.put("screen.left", "20%");
        params.put("screen.width", "43%");
        result = driver.executeScript("mobile:screen:text", params);

        String[] tablecells = result.toString().split("\\r?\\n");  //split on new line chars

        Integer i = 0;
        for (String tc : tablecells){
            //match odd and even lines correctly
            if(i%2 == 0) {
                if (!tc.matches(regexAlpha)) {
                    res = false;
                }
            } else {
                if (!tc.matches(regexNumeric)) {
                    res = false;
                }
            }
            System.out.println(i + " " + res + ": " + tc);
            i++;
        }

        return res;
    }

    public static void setPickerWheel(RemoteWebDriver driver, String xpath, String setValue){
        String x, y, width, height, swipestart, swipeend, value;
        Integer i=0;
        Map<String, Object> params = new HashMap<>();
        Object result;

        x = driver.findElementByXPath(xpath).getAttribute("x");
        y = driver.findElementByXPath(xpath).getAttribute("y");
        width = driver.findElementByXPath(xpath).getAttribute("width");
        height = driver.findElementByXPath(xpath).getAttribute("height");

        //convert to the center of the box around the object
        x = Integer.toString((Integer.parseInt(width)/2) + Integer.parseInt(x));
        y = Integer.toString((Integer.parseInt(height)/2) + Integer.parseInt(y));
        swipestart = x + "," + y;
        swipeend = x + "," + (Integer.parseInt(y) + 50);
        value = driver.findElementByXPath(xpath).getAttribute("value");
        driver.findElementByXPath(xpath).click();

        while (!value.equals(setValue) && i<100) {
            params.put("location", swipeend);
            params.put("operation", "single");
            result = driver.executeScript("mobile:touch:tap", params);
            suspend(2000);
            value = driver.findElementByXPath(xpath).getAttribute("value");
            i++;
        }
    }

    public static void tapChart(RemoteWebDriver driver, String xpath){
        String x, y, centx, centy, width, height, taploc;
        Integer i=0;
        Map<String, Object> params = new HashMap<>();
        Object result;

        x = driver.findElementByXPath(xpath).getAttribute("x");
        y = driver.findElementByXPath(xpath).getAttribute("y");
        width = driver.findElementByXPath(xpath).getAttribute("width");
        height = driver.findElementByXPath(xpath).getAttribute("height");

        //convert to the center of the box around the object
        centx = Integer.toString((Integer.parseInt(width)/2) + Integer.parseInt(x));
        centy = Integer.toString((Integer.parseInt(height)/2) + Integer.parseInt(y));
        driver.findElementByXPath(xpath).click();

        //Cycle through tapping right, left, up, and down on chart
        taploc = (x + Integer.parseInt(width)/4) + "," + centy;

        params.put("location", taploc);
        params.put("operation", "single");
        result = driver.executeScript("mobile:touch:tap", params);
        suspend(2000);


    }

    public static void tapText(RemoteWebDriver driver, String findtext){
        Map<String, Object> params = new HashMap<>();
        Object result;
        Integer locx=0, locy=0;
        String currcontext;

        currcontext = getCurrentContextHandle(driver);
        switchToContext(driver, "VISUAL");
        locx = driver.findElementByLinkText(findtext).getLocation().getX();
        locy = driver.findElementByLinkText(findtext).getLocation().getY();
        if(locx!=0 && locy!=0) {
            params.put("location", locx + "," + locy);
            params.put("operation", "single");
            params.put("duration", "5");
            result = driver.executeScript("mobile:touch:tap", params);
        }
        switchToContext(driver, currcontext);
    }

    public static void tapObject(RemoteWebDriver driver, String xpath){
        Map<String, Object> params = new HashMap<>();
        Object result;
        String locx="", locy="";
        String currcontext;

        locx = driver.findElementByXPath(xpath).getAttribute("x");
        locy = driver.findElementByXPath(xpath).getAttribute("y");
        if(locx!="" && locy!="") {
            params.put("location", locx + "," + locy);
            params.put("operation", "single");
            params.put("duration", "5");
            result = driver.executeScript("mobile:touch:tap", params);
        }
    }

    public static void switchToContext(RemoteWebDriver driver, String context) {
        //Perfecto defaults to WEBVIEW
        //NATIVE_APP is obvious
        //Perfecto provides a 3rd, VISUAL, which uses OCR

        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", context);
        executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
    }

    public static String getCurrentContextHandle(RemoteWebDriver driver) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        String context =  (String) executeMethod.execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null);
        return context;
    }



}
