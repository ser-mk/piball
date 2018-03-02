package ser.pipi.piball;

import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import sermk.pipi.pilib.PiBind;

public class AndroidLauncher extends AndroidApplication {
	final String TAG = this.getClass().getName();

	PiBind piBind;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useWakelock = true;
		config.hideStatusBar = true;
		piBind = new PiBind(this);
		PII_Stub pii = new PII_Stub(piBind);
		initialize(new Piball(pii), config);
		Gdx.input.setInputProcessor(pii);
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	protected void onDestroy() {
		Gdx.app.log(TAG, "onDestroy");
		piBind.release(this);
		super.onDestroy();
	}
}
