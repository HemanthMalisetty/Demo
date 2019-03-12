package utils;

import com.applitools.eyes.MatchLevel;

public class params {

    public static String EYES_KEY = System.getProperty("eyesAPIKey", "DEFAULT_TOKEN");
    public static String GRID_URL = "http://localhost:4444/wd/hub";

    public static String BATCH_NAME = "FirstData 01 G";
    public static String APP_NAME = "FirstData 01";
    public static String TEST_NAME = "FirstData 01";
    public static String URL_FILE = "FirstData.csv";
    public static MatchLevel MATCH_MODE = MatchLevel.LAYOUT;
    public static String BATCH_ID = null;

    public static Boolean DISABLE_EYES = false;
}
