package ser.pipi.piball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import ser.pipi.piball.engine.*;

public class Piball extends Game {

	final PositionInterface positionInterface;

	final Settings settings;

	public Piball(PositionInterface positionInterface) {
		this.positionInterface = positionInterface;

		settings = new Settings();
	}

	public Piball(PositionInterface positionInterface, Settings settings) {
		this.positionInterface = positionInterface;
		this.settings = settings;
	}

	public Settings getSettings(){
		return settings;
	}

	public PositionInterface getPositionInterface(){
		return positionInterface;
	}

	@Override
	public void create () {
		setScreen(new Welcome(this));
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
		Gdx.app.exit();
	}

	public void showResult(ResultScreen.ResultGame rg){
		setScreen(new ResultScreen(this, rg));
	}
}
