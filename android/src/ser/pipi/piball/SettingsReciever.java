package ser.pipi.piball;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import sermk.pipi.pilib.CommandCollection;
import sermk.pipi.pilib.ErrorCollector;
import sermk.pipi.pilib.MClient;

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
        Log.v(TAG, "inent: " + intent.toString());
        String action;
        try{
            action = intent.getAction().trim();
        } catch (Exception e){
            action = "wrong action!";
            EC.addError(action);
            Log.w(TAG, "action is not exist!");
        }
        Log.v(TAG, action);

        String content;
        try{
            content = intent.getStringExtra(Intent.EXTRA_TEXT).trim();
        } catch (Exception e){
            content = "wrong content!";
            EC.addError(content);
            Log.w(TAG, "content is not exist!");
        }
        Log.v(TAG, content);


        String success = ErrorCollector.NO_ERROR;

        try {
            success = doAction(context, content, action);
        } catch (Exception e){
            e.printStackTrace();
            EC.addError(EC.getStackTraceString(e));
            success = e.toString();
        }

        if(ErrorCollector.NO_ERROR.equals(success)) return;

        Log.v(TAG, EC.error);

        MClient.sendMessage(context, EC.subjError(TAG,action), EC.error);
    }

    private String doAction(Context context, String content, String action) {
        if(action.equals(CommandCollection.ACTION_RECIVER_PIBALL_GET_SETTINGS)){
            return getSettings(context, action);
        } else if (action.equals(CommandCollection.ACTION_RECIVER_PIBALL_SET_SETTINGS)){
            return setSettings(context, content, action);
        }

        final String err = "undefined action!";
        Log.w(TAG, err);
        EC.addError(err);

        return err;
    }

    private String setSettings(Context context, String content, String action) {
        final String res = Settings.setSettings(context, content);
        MClient.sendMessage(context, action, res);
        return ErrorCollector.NO_ERROR;
    }

    private String getSettings(Context context, String action) {
        final String settings = Settings.getSettingsJson(context);
        MClient.sendMessage(context, action, settings);
        return ErrorCollector.NO_ERROR;
    }
}
