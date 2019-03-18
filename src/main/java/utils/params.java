package utils;

import com.applitools.eyes.MatchLevel;

public class params {

    public static String EYES_KEY = System.getProperty("eyesAPIKey", "DEFAULT_TOKEN");
    public static String GRID_URL = "http://localhost:4444/wd/hub";


    public static String BATCH_NAME = "Aetna 01 VG";
    public static String APP_NAME = "Aetna";
    public static String TEST_NAME = "Aetna";
    public static String URL_FILE = "Aetna.csv";

    public static MatchLevel MATCH_MODE = MatchLevel.STRICT;
    public static String BATCH_ID = null;

    public static Boolean DISABLE_EYES = false;
}

