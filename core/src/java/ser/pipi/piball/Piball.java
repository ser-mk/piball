package ser.pipi.piball;

import com.badlogic.gdx.Game;

import ser.pipi.piball.engine.AllObjectsState;
import ser.pipi.piball.engine.LocalState;

public class Piball extends Game {

	final PositionInterface positionInterface;

	final SettingsStruct settingsStruct;

	public Piball(PositionInterface positionInterface) {
		this.positionInterface = positionInterface;

		settingsStruct = new SettingsStruct();
	}

	public Piball(PositionInterface positionInterface, SettingsStruct settingsStruct) {
		this.positionInterface = positionInterface;
		this.settingsStruct = settingsStruct;
	}

	public SettingsStruct getSettingsStruct(){
		return settingsStruct;
	}

	public PositionInterface getPositionInterface(){
		return positionInterface;
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
