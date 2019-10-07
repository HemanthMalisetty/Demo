package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

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
           // cOptions.addArguments("--headless");   // toggling this between tests can cause some good examples of font issues
            cOptions.addArguments("--disable-infobars");
            cOptions.addArguments("--disable-gpu");
            cOptions.addArguments("--disable-popup-blocking");
            cOptions.addArguments("--disable-default-apps");
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
                //options.addArguments("--disable-popup-blocking");
                //options.addArguments("--disable-default-apps");
                //options.addArguments("--disable-infobars");
                //options.addArguments("–-disable-notifications");
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

    public static RemoteWebDriver getPerfecto(String threadId, String deviceName, String platformName, String browserName){

        RemoteWebDriver driver = null;

        String PERFECTO_HOST = "partners.perfectomobile.com";
        String PERFECTO_USER = "";
        String PERFECTO_PASSWORD = "";
        String CLOUD_URL = "https://" + PERFECTO_HOST + "/nexperience/perfectomobile/wd/hub";
        //String FAST_WEB = System.getProperty("fastweb", "true");
        String SECURITY_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLc1BUWU1zZHBSZ21LXzk4OGZwaUx6cWsxdWpZMnduZG5SQU50ejgyVHBNIn0.eyJqdGkiOiIyMTA2ODZlNS1iN2RkLTQ5YmEtYWI5Mi03OGQ5ZTAzOWI2NjUiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNTY3ODcxODY2LCJpc3MiOiJodHRwczovL2F1dGgucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL3BhcnRuZXJzLXBlcmZlY3RvbW9iaWxlLWNvbSIsImF1ZCI6Im9mZmxpbmUtdG9rZW4tZ2VuZXJhdG9yIiwic3ViIjoiNjczOTkwMzYtZjA3ZC00OTZhLWI3NmItZDg1ODBhY2YzMGY3IiwidHlwIjoiT2ZmbGluZSIsImF6cCI6Im9mZmxpbmUtdG9rZW4tZ2VuZXJhdG9yIiwibm9uY2UiOiIwYTMzOWZmNC1iZTdlLTRjODMtODQ1Ni03NTEyNzAyM2I2NDIiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiJiY2I1NjI4OC0yNTUyLTQwZjMtOTM3Yi1kMTRlM2ViMmQzNzIiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19fQ.M_Yk0ny9tb_peDJ9OQXflENBvsuR1EaR5Xp-aSMySG2U2cUmT10vBEINlcvr8c5hnUXZzXAXwxUmXRsombQdqKkRWCfNZbhPJjgCT6SdH-XzyaU-K7qEADEw6V7USS9A7cXt4wPS7UyFmFeCtkp0pFZxQuyj8hXjwiHj-jOCdE40rwlztpQjo8rytzuLstUQwdzRFbNBFhCidd3PwOha4gO4fySRTvgGrkSMpzR9oAQ7az6jl1Xp201P-8Zss_9W8uVNAYXffWdR9-ickZIFKSzWDQn75SVQGUOr3WT0HXoQ6VEL8oUJWjt7u3G0i7b4zPi1J5_k65HXz1nyFXTVZA";

        if (remoteWebDrivers == null || !remoteWebDrivers.containsKey(threadId)) {

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("securityToken", SECURITY_TOKEN);
            capabilities.setCapability("platformName", platformName);
            //capabilities.setCapability("platformVersion", "999");
            //capabilities.setCapability("browserName", browserName);
            //capabilities.setCapability("browserVersion", 1);
            capabilities.setCapability("deviceName", deviceName);
            //capabilities.setCapability("model", "MODEL");
            capabilities.setCapability("windTunnelPersona", "Empty");

           // if(automation.matches("XCUITest")){
           //     capabilities.setCapability("automationName", automation);
           // }

            try {
                //driver = new AppiumDriver(new URL(CLOUD_URL), capabilities);
                //driver = new RemoteWebDriver(new URL(CLOUD_URL), capabilities);

                if(capabilities.getCapability("platformName").toString().equals("iOS")) {
                   driver = new IOSDriver(new URL(CLOUD_URL), capabilities);
                } else if (capabilities.getCapability("platformName").toString().equals("Android")){
                   driver = new AndroidDriver(new URL(CLOUD_URL), capabilities);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            remoteWebDrivers.put(threadId, driver);
        }
        return remoteWebDrivers.get(threadId);

    }
}
