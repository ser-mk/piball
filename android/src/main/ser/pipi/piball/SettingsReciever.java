package ser.pipi.piball;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import sermk.pipi.pilib.CommandCollection;
import sermk.pipi.pilib.ErrorCollector;
import sermk.pipi.pilib.MClient;
import sermk.pipi.pilib.NameFieldCollection;
import sermk.pipi.pilib.PiUtils;
import sermk.pipi.pilib.UniversalReciver;

public class SettingsReciever extends BroadcastReceiver {

    private final String TAG = this.getClass().getName();

    private final ErrorCollector EC = new ErrorCollector();

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        EC.clear();

        final UniversalReciver.ReciverVarible rv
                = UniversalReciver.parseIntent(intent, TAG);

        String success = ErrorCollector.NO_ERROR;

        try {
            success = doAction(context, rv.content, rv.action);
        } catch (Exception e){
            e.printStackTrace();
            EC.addError(EC.getStackTraceString(e));
            success = e.toString();
        }

        if(ErrorCollector.NO_ERROR.equals(success)) return;

        Log.v(TAG, EC.error);

        MClient.sendMessage(context, EC.subjError(TAG,rv.action), EC.error);
    }

    private String doAction(Context context, String content, String action) {
        if(action.equals(CommandCollection.ACTION_RECIVER_FOR_ALL_QUERY_SETTINGS)){
            return getSettings(context, action);
        } else if (action.equals(CommandCollection.ACTION_RECIVER_PIBALL_SET_AND_SAVE_SETTINGS)){
            return setSettings(context, content, action);
        }

        final String err = "undefined action!";
        Log.w(TAG, err);
        EC.addError(err);

        return err;
    }

    private String setSettings(Context context, String content, String action) {
        final boolean success = Settings.saveSettings(context, content);
        MClient.sendMessage(context, action,
                NameFieldCollection.errSaveSettings(success));
        return ErrorCollector.NO_ERROR;
    }

    private String getSettings(Context context, String action) {
        final String settings = PiUtils.getJsonFromShared(context);
        MClient.sendMessage(context, action, settings);
        return ErrorCollector.NO_ERROR;
    }
}
