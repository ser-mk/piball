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

    static public boolean saveStructSettings(Context context, Settings settings){

        if(PiUtils.checkHasNullPublicField(settings, Settings.class)){
            Log.v(TAG,"object settings has null object!");
            return false;
        }

        final String json = new Json().toJson(settings, Settings.class);

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
