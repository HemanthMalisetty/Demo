package utils;

import com.applitools.eyes.MatchLevel;

public class params {

     public static String EYES_KEY = System.getProperty("eyesAPIKey", "DEFAULT_TOKEN");

    // public static String EYES_KEY = "TCX1FEgWioHJpBSVryz2EeI8yU7J5WijAJNNuIEbSKU110";
    // public static String EYES_KEY = "sK3O8LZgrenpvvNKHMrZx8104HzYvsTDRNB6xfrsPP0fk110"; // FedEx POC Europe Key
    // public static String EYES_KEY = "sjmNt4T85wb9hRjwDAhCk4lz61nJEg6dpLsEyRPoVPY110";   // FedEx POC Separate Tennessee Key

    // public static String EYES_KEY = "sK3O8LZgrenpvvNKHMrZx8104HzYvsTDRNB6xfrsPP0fk110";   // FedEx Tennessee Key
    // public static String EYES_KEY = "bq91GrRWoSL26p7qINN2QdgGdWI9evF1abIREFBaSEo110";   // CME Demo

    // public static String EYES_KEY = "SfOlH07cYI0R69heErJe102gvu1Z7b110NZnd8qNk14Llng110";   // CME Feature Demo

    // public static String EYES_KEY = "fYnaQh4V2Ma5X6b2SXZstwO3OB108109FstnIhPNFTNcJ3k110";   // First Data
    // public static String EYES_KEY = "81XHcNk8YT107Yyee5PHWV7CfEA8EapSPkXtZIIl9rzv0110";   // BBVA
    // public static String EYES_KEY = "cympyEnNOa107108iLfAQXOuaejtjVcBnlK1cx101QgrrUmAY110";  //EBSCO

    public static String EYES_READ_KEY = "m44cI102QR8rpsg110aS5whMp0qAMO32a7LjSPB1Geh9cdE110";  // tied to Applitools team

    public static String EYES_URL = "https://eyes.applitools.com";

    public static String GRID_URL = "http://localhost:4444/wd/hub";

    private static String name = "url";   //name of the url file
    private static String suffix = " VG Demo";

    public static Boolean changePage = false;  // to change the content for demo purposes
    public static Boolean changePageSingle = false;
    public static Boolean removeChildNode = false;
    public static Boolean skewPage = false;
    public static Boolean alignTextCenter = false;

    public static String APP_NAME = name + suffix;
    public static String TEST_NAME = name + suffix;
    public static String URL_FILE = name + ".csv";
    public static MatchLevel MATCH_MODE = MatchLevel.STRICT;
    public static Boolean FULL_SCREEN = true;
    public static Boolean DISABLE_EYES = false;
    public static int MATCH_TIMEOUT = 2000;

    public static String BATCH_NAME = getBatchName();
    public static String BATCH_ID = getEnvBatchId();

    public static String IMAGE_DOWNLOAD_PATH = "log";

    private static String getBatchName(){
        String batchName = name + suffix;
        String envName = System.getenv("APPLITOOLS_BATCH_NAME");

        if(envName != null) batchName = envName;

        return batchName;
    }

    private static String getEnvBatchId(){
        String batchId = null;
        if(System.getenv("APPLITOOLS_BATCH_ID") != null)
            batchId = System.getenv("APPLITOOLS_BATCH_ID");
        if(System.getProperty("APPLITOOLS_BATCH_ID") != null)
            batchId = System.getProperty("APPLITOOLS_BATCH_ID");
        return batchId;
    }
}

