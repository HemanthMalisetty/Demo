package utils;

import com.applitools.eyes.MatchLevel;

public class params {

    public static String EYES_KEY = System.getProperty("eyesAPIKey", "DEFAULT_TOKEN");
    // "bq91GrRWoSL26p7qINN2QdgGdWI9evF1abIREFBaSEo110"; //System.getProperty("eyesAPIKey", "DEFAULT_TOKEN");
    //public static String EYES_KEY = "18IoQ102FEtR31VdC75SpLe1104gLDNp110QBdSSEDgQeoto8110";
    //public static String EYES_URL = "https://nytimeseyes.applitools.com";
    public static String EYES_URL = "https://eyes.applitools.com";

    public static String GRID_URL = "http://localhost:4444/wd/hub";

    private static String name = "url";
    private static String suffix = " CIBC";

    public static Boolean changePage = false;  // to change the content for demo purposes
    public static Boolean changePageSingle = false;

    //public static String APP_NAME = name + suffix;
    public static String APP_NAME = "ImageTester";
    //public static String TEST_NAME = name + suffix;
    public static String TEST_NAME = "ImageTester";
    public static String URL_FILE = name + ".csv";
    public static MatchLevel MATCH_MODE = MatchLevel.STRICT;
    public static Boolean FULL_SCREEN = false;
    public static Boolean DISABLE_EYES = false;
    public static int MATCH_TIMEOUT = 1000;


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

