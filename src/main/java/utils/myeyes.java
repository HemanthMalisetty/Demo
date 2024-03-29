package utils;

import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;

import java.util.Hashtable;
import java.util.Map;

public class myeyes {

    static Map<String,Eyes> eyes = new Hashtable<String,Eyes>();


    public static Eyes getEyes(String threadId){

       // EyesRunner runner = new ClassicRunner();

        if (eyes == null || !eyes.containsKey(threadId)) {
            Eyes eye = new Eyes();
            eye.setApiKey(params.EYES_KEY);
            eyes.put(threadId, eye);
        }
        return eyes.get(threadId);
    }

}
