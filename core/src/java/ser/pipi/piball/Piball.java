package ser.pipi.piball;

import com.badlogic.gdx.Game;

public class Piball extends Game {

	final GameInterface gameInterface;

	final SettingsStruct settingsStruct;

	public Piball(GameInterface gameInterface) {
		this.gameInterface = gameInterface;

		settingsStruct = new SettingsStruct();
	}

	public Piball(GameInterface gameInterface, SettingsStruct settingsStruct) {
		this.gameInterface = gameInterface;
		this.settingsStruct = settingsStruct;
	}

	public SettingsStruct getSettingsStruct(){
		return settingsStruct;
	}

	public GameInterface getGameInterface(){
		return gameInterface;
	}

	@Override
	public void create () {
		setScreen(new Welcome(this));
		//setScreen(new Arena(this));
	}

	public void startArena(int flag){
		setScreen(new Arena(this, flag));
	}
}
