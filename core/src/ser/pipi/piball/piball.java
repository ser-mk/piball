package ser.pipi.piball;

import com.badlogic.gdx.Game;

public class piball extends Game {

	final GameInterface pii;

	public piball(GameInterface pii) {
		this.pii = pii;
	}

	@Override
	public void create () {
		setScreen(new Welcome(this.pii));
	}
}
