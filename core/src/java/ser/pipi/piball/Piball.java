package ser.pipi.piball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import ser.pipi.piball.settings.UI.SettingsScreen;
import ser.pipi.piball.engine.*;
import ser.pipi.piball.settings.SettingsManagerImp;

public class Piball extends Game {

	private final String TAG = this.getClass().getName();
	final PositionInterface positionInterface;

	private final SettingsManagerImp settingsManager;

	private Settings settings;

	public Piball(PositionInterface positionInterface, SettingsManagerImp settingsManager) {
		this.positionInterface = positionInterface;
		this.settingsManager = settingsManager;
		this.settings = settingsManager.getSettings4Game();
	}

	public Settings getSettings(){
		return settings;
	}

	public void setSettings(Settings settings){
		this.settings = settings;
	}

	public PositionInterface getPositionInterface(){
		return positionInterface;
	}

	@Override
	public void create () {
		//setSettingsScreen();
		startWelcome();
		//startArena(0);
		//showResult(( new ResultScreen.ResultGame()));
	}

	public void startWelcome(){
		setScreen(new Welcome(this));
	}

	public void startArena(int flag){
		setScreen(new Arena(this, flag));
	}

	public void exit(){
		Gdx.app.log(TAG,"exit!");
		Gdx.app.exit();
	}

	public void showResult(ResultScreen.ResultGame rg){
		setScreen(new ResultScreen(this, rg));
	}

	public void setSettingsScreen(){
		setScreen(new SettingsScreen(this));
	}

	public void saveSettings(){
		this.settingsManager.saveSettingsFromGame(this.settings);
	}
}
