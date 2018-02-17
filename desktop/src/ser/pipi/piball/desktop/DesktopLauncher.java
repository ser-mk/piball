package ser.pipi.piball.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ser.pipi.piball.Settings;
import ser.pipi.piball.piball;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "ball";
		config.width = Settings.screen_width;
		config.height = Settings.screen_heigth;
		config.resizable = false;
		new LwjglApplication(new piball(), config);
	}
}
