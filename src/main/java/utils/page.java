package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class page {

    public static void click(WebDriver driver, String cssSelector) {
        try {
            driver.findElement(By.cssSelector(cssSelector)).click();
        } catch (Exception e) {

        }
    }
    public static void scrollPage(RemoteWebDriver driver){

        Long height = (Long) driver.executeScript("return document.body.scrollHeight;");

        Actions builder = new Actions(driver);
        Action seriesOfActions = builder
                .sendKeys(Keys.PAGE_DOWN)
                .build();

        Integer i=0;
        for(i=0;i<height/800;i++) {
            seriesOfActions.perform();
        }
        seriesOfActions = builder
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
}
