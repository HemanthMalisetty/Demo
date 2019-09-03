package utils;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

public class drivers {

    static Map<String,ChromeDriver> chromeDrivers = new Hashtable<String,ChromeDriver>();
    static Map<String,RemoteWebDriver> remoteWebDrivers = new Hashtable<String,RemoteWebDriver>();


    public static ChromeDriver getLocalChrome(String threadId){

        if (chromeDrivers == null || !chromeDrivers.containsKey(threadId)) {

            ChromeOptions cOptions = new ChromeOptions();
           // cOptions.addArguments("--headless");
            cOptions.addArguments("--disable-popup-blocking");
            cOptions.addArguments("--disable-default-apps");
            cOptions.addArguments("--start-maximized");
            cOptions.addArguments("--disable-infobars");
            cOptions.addArguments("–-disable-notifications");
            cOptions.addArguments("--dom-automation");

            ChromeDriver driver = new ChromeDriver(cOptions);
            chromeDrivers.put(threadId, driver);
        }
        return chromeDrivers.get(threadId);
    }

    public static RemoteWebDriver getGrid(String threadId, String browserName){

        RemoteWebDriver driver = null;

        if (remoteWebDrivers == null || !remoteWebDrivers.containsKey(threadId)) {

            if(browserName.equalsIgnoreCase("CHROME")) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-popup-blocking");
                options.addArguments("--disable-default-apps");
                options.addArguments("--disable-infobars");
                options.addArguments("–-disable-notifications");
                //options.addArguments("--dom-automation");
                options.setCapability(CapabilityType.BROWSER_NAME, "chrome");
                options.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
                try {
                    driver = new RemoteWebDriver(new URL(params.GRID_URL), options);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            if(browserName.equalsIgnoreCase("FIREFOX")) {
                FirefoxOptions options = new FirefoxOptions();
                options.setCapability(CapabilityType.BROWSER_NAME, "firefox");
                options.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
                try {
                    driver = new RemoteWebDriver(new URL(params.GRID_URL), options);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            remoteWebDrivers.put(threadId, driver);
        }
        return remoteWebDrivers.get(threadId);
    }

    public static RemoteWebDriver PerfectoMobile_iOS(String threadId){

        RemoteWebDriver driver = null;

        public static String PERFECTO_HOST = System.getProperty("testHost", "DEFAULT_HOST_NAME");
        public static String PERFECTO_USER = System.getProperty("testUsername", "DEFAULT_USERNAME");
        public static String PERFECTO_PASSWORD = System.getProperty("testPassword", "DEFAULT_PASSWORD");
        public static String CLOUD_URL = "https://" + PERFECTO_HOST + "/nexperience/perfectomobile/wd/hub";
        public static String FAST_WEB = System.getProperty("fastweb", "true");
        public static String SECURITY_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJzbFV4OFFBdjdVellIajd4YWstR0tTbE43UjFNSllDbC1TRVJiTlU1RFlFIn0.eyJqdGkiOiJjY2U4YjQ4My03ZGRlLTQzZTEtOTQ4MC01ZmIyZTJjOWViZDYiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNTM4MzQyNDU0LCJpc3MiOiJodHRwczovL2F1dGgucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL2RlbW8tcGVyZmVjdG9tb2JpbGUtY29tIiwiYXVkIjoib2ZmbGluZS10b2tlbi1nZW5lcmF0b3IiLCJzdWIiOiI5YjQ4Nzk4MS02MTMyLTQzOGEtOWJmMi00YjY2M2YxYTMyNTciLCJ0eXAiOiJPZmZsaW5lIiwiYXpwIjoib2ZmbGluZS10b2tlbi1nZW5lcmF0b3IiLCJub25jZSI6IjAxZmYzNDdhLTUyMDYtNGM5My04YmFhLTU0NDc4ZmMxNmI5YyIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6IjhkNDQ1YmFjLTJkODUtNDQ4My05ZGI5LTdlNzIzMjc4NDY1YyIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fX0.RoDcVfkMhIvEuaqPOWA10Ln31yA0OI30vKgD2MOCLIHgsCwJ_cNIh-ob4bqCVFIDZZfzKidb_CvIp_QS_4pWWgg7t7EY4ij-T1pVg5wrAdp-gmBYw2NDREphJJpSqiadsQzxlTowCYQ-o7fTAmOSoNh_zXmNxs-Rr0hcFc_eEgCDmiAbs4hmWeSDcpritmGFrDMwEgOKGF3_MR2k2K8SCX2IghzFjOBb10lCGSBBeEwj6Xo9lD5P9_7p41wFCTbkmZAx1RN17FtgSYvFKqspWpTP4B4ogxJb95q2hjqvo6p6Yxixh5R8YTIx-gdCG83cP67eSFlFGxBkNDJBkED8Hg";

        if (remoteWebDrivers == null || !remoteWebDrivers.containsKey(threadId)) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            //capabilities.setCapability("user", PERFECTO_USER);
            //capabilities.setCapability("password", PERFECTO_PASSWORD);
            capabilities.setCapability("securityToken", SECURITY_TOKEN);
            capabilities.setCapability("platformName", platformName);
            capabilities.setCapability("platformVersion", platformVersion);
            capabilities.setCapability("browserName", browserName);
            capabilities.setCapability("browserVersion", browserVersion);

            capabilities.setCapability("deviceName", deviceName);
            capabilities.setCapability("model", "");
            capabilities.setCapability("windTunnelPersona", persona);
            if(automation.matches("XCUITest")){
                capabilities.setCapability("automationName", automation);
            }




            remoteWebDrivers.put(threadId, driver);
        }
        return remoteWebDrivers.get(threadId);


    }
}
