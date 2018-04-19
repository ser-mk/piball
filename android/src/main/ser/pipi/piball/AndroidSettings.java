package ser.pipi.piball;

import android.content.Context;
import android.util.Log;

import com.esotericsoftware.jsonbeans.Json;

import sermk.pipi.pilib.PiUtils;

/**
 * Created by ser on 18.03.18.
 */

public class AndroidSettings {

    private final static  String TAG = "Game AndroidSettings:";

    private final static  String SETTINGS_NAME_FIELD = TAG;
    private final static  String SETTINGS_FILE_NAME = "settings";

    private final static  String SUCCES_SET_SETTINGS = "SUCCES_SET_SETTINGS";

    static public boolean saveSettings(Context context, String json){
        final Settings temp = new Json().
                fromJson(Settings.class,json);
        if(PiUtils.checkHasNullPublicField(temp, Settings.class)){
            Log.v(TAG,"object settings has null object!");
            return false;
        }

        PiUtils.saveJson(context, json);

        return true;
    }

    //todo: check null and null field and new settings
    static public Settings getSettings(Context context){
        final String json = PiUtils.getJsonFromShared(context);
        final Settings temp =  new Json().fromJson(Settings.class, json);

        if(PiUtils.checkHasNullPublicField(temp, Settings.class)){
            Log.v(TAG,"saved settings broken!");
            return new Settings();
        }

        return temp;
    }
}
