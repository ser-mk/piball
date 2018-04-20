package ser.pipi.piball.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ser.pipi.piball.Piball;
import ser.pipi.piball.Settings;

public class DesktopLauncher {
	public static void main (String[] arg) {

		System.out.println("arg : " + arg[0] + " " + arg[1]);

		final Settings ss = new Settings();
		final boolean server = arg[0].equals("s");
		final int bankport = Integer.parseInt(arg[1]);
		ss.server = server;
		ss.bankPort = bankport;

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = server ? "server" : "client";
		config.width = 800;
		config.height = 1230;
		config.resizable = true;
		final PII_Stub pii = new PII_Stub();
		//new LwjglApplication(new Piball(pii), config);

		new LwjglApplication(new Piball(pii,ss), config);

		Gdx.input.setInputProcessor(pii);

	}
}
