package ser.pipi.piball;

import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import ser.pipi.piball.piball;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useWakelock = true;
		config.hideStatusBar = true;
		PII_Stub pii = new PII_Stub();
		initialize(new piball(pii), config);
		Gdx.input.setInputProcessor(pii);
		Gdx.input.setCatchBackKey(true);
	}
}
