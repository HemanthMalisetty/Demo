package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.rmi.Remote;


public class page {

    public static void pageDown(RemoteWebDriver driver){

        try {
            Long height = (Long) driver.executeScript("return document.body.scrollHeight;");

            Actions builder = new Actions(driver);
            Action seriesOfActions = builder
                    .sendKeys(Keys.PAGE_DOWN)
                    .build();

            Integer i = 0;
            for (i = 0; i < height / 800; i++) {
                seriesOfActions.perform();
            }
            seriesOfActions = builder
                    .sendKeys(Keys.HOME)
                    .build();
            seriesOfActions.perform();
        } catch (Exception e) {}
    }

    public static void arrowDown(RemoteWebDriver driver){

        try {
            Long height = (Long) driver.executeScript("return document.body.scrollHeight;");

            Actions builder = new Actions(driver);
            Action seriesOfActions = builder
                    .sendKeys(Keys.ARROW_DOWN)
                    .build();

            Integer i = 0;
            for (i = 0; i < height / 4; i++) {
                seriesOfActions.perform();
            }
        } catch (Exception e) {}
    }

    public static void arrowUp(RemoteWebDriver driver){

        try {
            Long height = (Long) driver.executeScript("return document.body.scrollHeight;");

            Actions builder = new Actions(driver);
            Action seriesOfActions = builder
                    .sendKeys(Keys.ARROW_UP)
                    .build();

            Integer i = 0;
            for (i = 0; i < height / 4; i++) {
                seriesOfActions.perform();
            }
        } catch (Exception e) {}
    }


    public static void home(RemoteWebDriver driver){

        Actions builder = new Actions(driver);
        Action seriesOfActions = builder
            .sendKeys(Keys.HOME)
            .build();
        seriesOfActions.perform();
    }

    public static void suspend(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {

        }
    }

    public static void changePage(RemoteWebDriver driver){

        String script = "" +
                "                                    var elements = window.document.querySelectorAll(\"body, body *\");\n" +
                "                                    var child;\n" +
                "                                    for(var i = 0; i < elements.length; i++) {\n" +
                "                                        child = elements[i].childNodes[0];\n" +
                "                                        if(elements[i].hasChildNodes() && child.nodeType == 3) {\n" +
                "                                           child.nodeValue = child.nodeValue.replace('o','0');" +
                "                                           child.nodeValue = child.nodeValue.replace('?','!                       ');" +
                "                                           child.nodeValue = child.nodeValue.replace(',','.');" +
                "                                           child.nodeValue = child.nodeValue.replace('.','.</><br>');" +
                "                                        }\n" +
                "                                    }\n";
        if(params.changePage) {
            driver.executeScript(script);
        }
    }

    public static void changePageSingle(RemoteWebDriver driver, String from, String to){

        String script = "" +
                "                                    var elements = window.document.querySelectorAll(\"body, body *\");\n" +
                "                                    var child;\n" +
                "                                    for(var i = 0; i < elements.length; i++) {\n" +
                "                                        child = elements[i].childNodes[0];\n" +
                "                                        if(elements[i].hasChildNodes() && child.nodeType == 3) {\n" +
                "                                           child.nodeValue = child.nodeValue.replace('" + from +"','" + to + "');" +
                "                                        }\n" +
                "                                    }\n";
        if(params.changePageSingle) {
            driver.executeScript(script);
        }
    }

    public static void removeNodes(RemoteWebDriver driver){

        String script = "" +
                "                                    var elements = window.document.querySelectorAll(\"body, body *\");\n" +
                "                                    var child;\n" +
                "                                    for(var i = 0; i < elements.length; i++) {\n" +
                "                                       child = elements[i].childNodes[0];\n" +
                "                                        if(elements[i].hasChildNodes() && child.nodeType == 3) {\n" +
                "                                           elements[i].removeChild(child);" +
                "                                        }\n" +
                "                                    }\n";
        if(params.removeChildNode) {
            driver.executeScript(script);
        }
    }

    public static void alignTextCenter(RemoteWebDriver driver){

        String script = "" +
                "                                    var elements = window.document.querySelectorAll(\"body, body *\");\n" +
                "                                    var child;\n" +
                "                                    for(var i = 0; i < elements.length; i++) {\n" +
                "                                       child = elements[i].childNodes[0];\n" +
                "                                        if(elements[i].hasChildNodes() && child.nodeType == 3) {\n" +
                "                                           elements[i].style.textAlign = \"center\";;" +
                "                                        }\n" +
                "                                    }\n";
        if(params.alignTextCenter) {
            driver.executeScript(script);
        }
    }


    public static void skewPage(RemoteWebDriver driver){

        String script = "" +
                "var cssText = 'div { transform: skewY(.009deg);}'; \n" +
                "var css = document.createElement('style'); \n" +
                "css.type = 'text/css'; \n" +
                "if('textContent' in css) \n" +
                "css.textContent = cssText; \n" +
                "else \n" +
                "css.innerText = cssText; \n" +
                "document.body.appendChild(css); \n";

        if(params.skewPage) {
            driver.executeScript(script);
        }
    }



    public static void clickLinkText(RemoteWebDriver driver, String identifier){
        Integer i;
        for(i=0;i<=5;i++){
            try {
                driver.findElementByLinkText(identifier).click();
                i=999;
            } catch (Exception e) {}
        }
    }

    public static void clickName(RemoteWebDriver driver, String identifier){
        Integer i;
        for(i=0;i<=5;i++){
            try {
                driver.findElementByName(identifier).click();
                i=999;
            } catch (Exception e) {}
        }
    }

}
