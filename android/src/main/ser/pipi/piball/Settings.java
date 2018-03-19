package ser.pipi.piball;

import android.content.Context;
import android.content.SharedPreferences;

import com.esotericsoftware.jsonbeans.Json;

/**
 * Created by ser on 18.03.18.
 */

public class Settings {

    private final static  String TAG = "Game Settings:";

    private final static  String SETTINGS_NAME_FIELD = TAG;
    private final static  String SETTINGS_FILE_NAME = "settings";

    private final static  String SUCCES_SET_SETTINGS = "SUCCES_SET_SETTINGS";

    static public String getSettingsJson(Context context){
        final SharedPreferences sharedPreferences =
                context.getSharedPreferences(SETTINGS_FILE_NAME,Context.MODE_PRIVATE);
        final String json = sharedPreferences.getString(SETTINGS_NAME_FIELD, "");
        return json;
    }

    static public String setSettings(Context context, String json){
        final SharedPreferences sharedPreferences =
                context.getSharedPreferences(SETTINGS_FILE_NAME,Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(SETTINGS_NAME_FIELD, json).apply();
        return SUCCES_SET_SETTINGS;
    }

    static public SettingsStruct getSettingsStruct(Context context){
        final String json = getSettingsJson(context);
        return new Json().fromJson(SettingsStruct.class, json);
    }
}
