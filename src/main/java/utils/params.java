package utils;

import com.applitools.eyes.MatchLevel;

public class params {

    public static String EYES_KEY = System.getProperty("eyesAPIKey", "DEFAULT_TOKEN");
    public static String GRID_URL = "http://localhost:4444/wd/hub";

    private static String name = "url";
    private static String suffix = " Float DG02";
    public static Boolean changePage = false;  // to change the content for demo purposes
    public static String APP_NAME = name + suffix;
    public static String TEST_NAME = name + suffix;
    public static String URL_FILE = name + ".csv";
    public static MatchLevel MATCH_MODE = MatchLevel.LAYOUT;
    public static Boolean FULL_SCREEN = true;
    public static Boolean DISABLE_EYES = false;

    public static String BATCH_NAME = getBatchName();
    public static String BATCH_ID = getBatchId();

    private static String getBatchName(){
        String batchName = name + suffix;
        String envName = System.getenv("APPLITOOLS_BATCH_NAME");

        if(envName != null) batchName = envName;

        return batchName;
    }

    private static String getBatchId(){
        String batchId = null;

        if(System.getenv("APPLITOOLS_BATCH_ID") != null)
            batchId = System.getenv("APPLITOOLS_BATCH_ID");

        return batchId;
    }
}

