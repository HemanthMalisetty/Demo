package tests;

import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.selenium.Eyes;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class urlscan {

    public static void scanlist(RemoteWebDriver driver, Eyes eyes, String urlList){

        int i;
        long before;

        before = System.currentTimeMillis();
        urlscan wantFile = new urlscan();

        System.out.println("Scanning URL's in " + urlList);
        String[] arr = new String[0];
        try {
            Scanner sc = new Scanner(wantFile.getFile(urlList));
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

            System.out.println("Checking URL " + +i + ": " + arr[i]);
            try {
                driver.get(arr[i]);


                //clear cookie warning
                try{
                    //System.out.println("Clearing floater");
                    //String jscript = "var x = document.getElementsByClassName('container container--trending'); " +
                    //        "x[0].style.display = 'none';";
                    //driver.executeScript(jscript);

                  //  utils.page.clickLinkText(driver, "Accept Cookies");
                  //  System.out.println("Clear succeeded");
                } catch (Exception e){
                    System.out.println("Clear skipped");
                };


                utils.page.suspend(1000);
                utils.page.arrowDown(driver);
                utils.page.arrowUp(driver);
                utils.page.suspend(1000);
                utils.page.changePage(driver);
                utils.page.changePageSingle(driver, "Choose", "Select");
                utils.page.skewPage(driver);
                utils.page.alignTextCenter(driver);
                utils.page.removeNodes(driver);
                eyes.check(arr[i], Target.window().fully());
            } catch (Exception e) {
                System.out.println("FAILED URL " + +i + " in " + (System.currentTimeMillis() - before) + "ms");
                e.printStackTrace();
            }
        }
        System.out.println("Completed URL scan in " + ((System.currentTimeMillis() - before))/1000 + " seconds");
    }

    public static void scanlist(RemoteWebDriver driver, Eyes eyes, String urlList, int urlPos){

        int i = urlPos;
        long before;

        before = System.currentTimeMillis();
        urlscan wantFile = new urlscan();

        System.out.println("Scanning URL's in " + urlList);
        String[] arr = new String[0];
        try {
            Scanner sc = new Scanner(wantFile.getFile(urlList));
            List<String> lines = new ArrayList<String>();
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
            arr = lines.toArray(new String[0]);
            System.out.println("URL's to check: " + arr.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Checking URL " + +i + ": " + arr[i]);
        try {
            driver.get(arr[i]);
            utils.page.suspend(2000);
            utils.page.arrowDown(driver);
            utils.page.arrowUp(driver);
           // utils.page.home(driver);
           // utils.page.suspend(2000);
            utils.page.changePage(driver);
            utils.page.changePageSingle(driver,"1", "2");
            eyes.check(arr[i], Target.window());
        } catch (Exception e) {
            System.out.println("FAILED URL " + +i + " in " + (System.currentTimeMillis() - before) + "ms");
            e.printStackTrace();
        }

        System.out.println("Completed URL scan in " + ((System.currentTimeMillis() - before))/1000 + " seconds");
    }

    public static void scanListMobile(RemoteWebDriver driver, Eyes eyes, String urlList){

        int i;
        long before;
        long beforeStep;

        before = System.currentTimeMillis();
        urlscan wantFile = new urlscan();

        System.out.println("Scanning URL's in " + urlList);
        String[] arr = new String[0];
        try {
            Scanner sc = new Scanner(wantFile.getFile(urlList));
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

            System.out.println("Checking URL " + +i + ": " + arr[i]);
            try {
                driver.get(arr[i]);
                utils.page.suspend(5000);
                beforeStep = System.currentTimeMillis();
                eyes.check(arr[i], Target.window());
                System.out.println("eyes url took " + (System.currentTimeMillis() - beforeStep) + "ms");
            } catch (Exception e) {
                System.out.println("FAILED URL " + +i + " in " + (System.currentTimeMillis() - before) + "ms");
                e.printStackTrace();
            }
        }
        System.out.println("Completed URL scan in " + ((System.currentTimeMillis() - before))/1000 + " seconds");
    }


    public File getFile(String fileName){

        ClassLoader classLoader = getClass().getClassLoader();
        File file = null;
        try {
            file = new File(classLoader.getResource(fileName).getFile());
        } catch (Exception e) {
            System.out.println("File Not Found: " + fileName);
            e.printStackTrace();
        }
        return file;
    }
}
