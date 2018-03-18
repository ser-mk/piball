package ser.pipi.piball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import ser.pipi.piball.engine.*;

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
		//setScreen(new Welcome(this));
		//startArena(0);
		showResult(( new ResultScreen.ResultGame()));
	}

	public void startWelcome(){
		setScreen(new Welcome(this));
	}

	public void startArena(int flag){
		setScreen(new Arena(this, flag));
	}

	public void exit(){
		Gdx.app.exit();
	}

	public void showResult(ResultScreen.ResultGame rg){
		setScreen(new ResultScreen(this, rg));
	}
}
