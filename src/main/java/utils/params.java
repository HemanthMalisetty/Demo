package utils;

import com.applitools.eyes.MatchLevel;

public class params {

     public static String EYES_KEY = System.getProperty("eyesAPIKey", "DEFAULT_TOKEN");
    // public static String EYES_KEY = "TCX1FEgWioHJpBSVryz2EeI8yU7J5WijAJNNuIEbSKU110";
    // public static String EYES_KEY = "sK3O8LZgrenpvvNKHMrZx8104HzYvsTDRNB6xfrsPP0fk110"; // FedEx Europe Key
    // public static String EYES_KEY = "sjmNt4T85wb9hRjwDAhCk4lz61nJEg6dpLsEyRPoVPY110";   // FedEx Tennessee Key
    // public static String EYES_KEY = "bq91GrRWoSL26p7qINN2QdgGdWI9evF1abIREFBaSEo110";   // CME Demo

     // public static String EYES_KEY = "fYnaQh4V2Ma5X6b2SXZstwO3OB108109FstnIhPNFTNcJ3k110";   // First Data


    public static String EYES_URL = "https://eyes.applitools.com";

    public static String GRID_URL = "http://localhost:4444/wd/hub";

    private static String name = "url";   //name of the url file
    private static String suffix = " FD_1 Demo";

    public static Boolean changePage = false;  // to change the content for demo purposes
    public static Boolean changePageSingle = false;

    public static String APP_NAME = name + suffix;
    public static String TEST_NAME = name + suffix;
    public static String URL_FILE = name + ".csv";
    public static MatchLevel MATCH_MODE = MatchLevel.STRICT;
    public static Boolean FULL_SCREEN = true;
    public static Boolean DISABLE_EYES = false;
    public static int MATCH_TIMEOUT = 2000;

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
        if(System.getProperty("APPLITOOLS_BATCH_ID") != null)
            batchId = System.getProperty("APPLITOOLS_BATCH_ID");
        return batchId;
    }
}

