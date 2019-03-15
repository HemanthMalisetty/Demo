package utils;

import com.applitools.eyes.MatchLevel;

public class params {

    public static String EYES_KEY = System.getProperty("eyesAPIKey", "DEFAULT_TOKEN");
    public static String GRID_URL = "http://localhost:4444/wd/hub";


    public static String BATCH_NAME = "Magic Leap 02 VG S";
    public static String APP_NAME = "Magic Leap  02";
    public static String TEST_NAME = "Magic Leap  02";
    public static String URL_FILE = "MagicLeap.csv";

    public static MatchLevel MATCH_MODE = MatchLevel.STRICT;
    public static String BATCH_ID = null;

    public static Boolean DISABLE_EYES = false;
}

