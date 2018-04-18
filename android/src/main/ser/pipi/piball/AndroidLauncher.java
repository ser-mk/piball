package ser.pipi.piball;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.WindowManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import sermk.pipi.pilib.PiBind;

public class AndroidLauncher extends AndroidApplication {
	final String TAG = this.getClass().getName();

	PiBind piBind;

	private void standTo(){
		PowerManager powerManager = ((PowerManager) getSystemService(Context.POWER_SERVICE));
		PowerManager.WakeLock wake = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, TAG);

		wake.acquire();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
				WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
				WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
	}

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		standTo();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useWakelock = true;
		config.hideStatusBar = true;
		piBind = new PiBind(this);
		PII_Stub pii = new PII_Stub(piBind);
		initialize(new Piball(pii, getSettingsStruct()), config);
		Gdx.input.setInputProcessor(pii);
		Gdx.input.setCatchBackKey(true);
	}

	private SettingsStruct getSettingsStruct(){

		final SettingsStruct ss = AndroidSettings.getSettingsStruct(this);
		if (this.getIntent().hasExtra("Server")) {
			ss.server = this.getIntent().getBooleanExtra("Server", false);
		}
		Log.d ( "Server:", "" + ss.server);
		return ss;
	}

	@Override
	protected void onDestroy() {
		Gdx.app.log(TAG, "onDestroy");
		piBind.release(this);
		super.onDestroy();
	}
}
