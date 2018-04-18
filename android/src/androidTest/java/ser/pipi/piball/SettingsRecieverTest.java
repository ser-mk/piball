package ser.pipi.piball;

import android.content.Context;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.esotericsoftware.jsonbeans.Json;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import sermk.pipi.pilib.CommandCollection;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by ser on 18.03.18.
 */
@RunWith(AndroidJUnit4.class)
public class SettingsRecieverTest {
/**/
    @Rule
    public ActivityTestRule<AndroidLauncher> al = new ActivityTestRule<>(AndroidLauncher.class);

    final int bankPort_test = -1;


    @Before
    public void before(){

    }

    @Test
    public void onReceive() throws Exception {

        SettingsStruct settingsStruct = new SettingsStruct();
        settingsStruct.bankPort = bankPort_test;
        String json = new Json().toJson(settingsStruct, SettingsStruct.class);

        sendBroadCastStringContent(al.getActivity(),
                CommandCollection.ACTION_RECIVER_PIBALL_SET_AND_SAVE_SETTINGS,json);

        Thread.sleep(14111);


        settingsStruct = AndroidSettings.getSettingsStruct(al.getActivity());

        Assert.assertEquals(bankPort_test, settingsStruct.bankPort);

        settingsStruct.bankPort = -bankPort_test;
        json = new Json().toJson(settingsStruct);

        sendBroadCastStringContent(al.getActivity(),
                CommandCollection.ACTION_RECIVER_PIBALL_SET_AND_SAVE_SETTINGS,json);

        Thread.sleep(14111);
        settingsStruct = AndroidSettings.getSettingsStruct(al.getActivity());
        Assert.assertNotEquals(bankPort_test, settingsStruct.bankPort);
    }

    private void sendBroadCastStringContent(Context context,String action, String content){
        Intent intent = new Intent();
        intent.setAction(action);

        intent.putExtra(Intent.EXTRA_TEXT, content);

        context.sendBroadcast(intent);
    }

}