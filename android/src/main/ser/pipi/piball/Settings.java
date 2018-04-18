package ser.pipi.piball;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.esotericsoftware.jsonbeans.Json;

import sermk.pipi.pilib.PiUtils;

/**
 * Created by ser on 18.03.18.
 */

public class Settings {

    private final static  String TAG = "Game Settings:";

    private final static  String SETTINGS_NAME_FIELD = TAG;
    private final static  String SETTINGS_FILE_NAME = "settings";

    private final static  String SUCCES_SET_SETTINGS = "SUCCES_SET_SETTINGS";

    static public boolean saveSettings(Context context, String json){
        final SettingsStruct temp = new Json().
                fromJson(SettingsStruct.class,json);
        if(PiUtils.checkHasNullPublicField(temp, SettingsStruct.class)){
            Log.v(TAG,"object settings has null object!");
            return false;
        }

        PiUtils.saveJson(context, json);

        return true;
    }

    //todo: check null and null field and new settings
    static public SettingsStruct getSettingsStruct(Context context){
        final String json = PiUtils.getJsonFromShared(context);
        final SettingsStruct temp =  new Json().fromJson(SettingsStruct.class, json);

        if(PiUtils.checkHasNullPublicField(temp, SettingsStruct.class)){
            Log.v(TAG,"saved settings broken!");
            return new SettingsStruct();
        }

        return temp;
    }
}
