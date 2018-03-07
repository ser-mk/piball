package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;

import ser.pipi.piball.GameInspector;
import ser.pipi.piball.GameInterface;

/**
 * Created by ser on 20.02.18.
 */

public class LocalController {

    final GameInterface gameInterface;
    final GameInspector gameInspector;
    final LocalState localState;


    public LocalController(LocalState localState, GameInterface gameInterface) {
        this.gameInterface = gameInterface;
        this.localState = localState;
        this.gameInspector = new GameInspector(gameInterface);
    }

    public void update(float delta){
        gameInspector.checkPiPos(delta);
        localState.statusPI = gameInspector.getStatus();

        final int pos = gameInterface.getPosition();
        if (pos < GameInterface.POSITION_MIN){
            return;
        }
        final float XP = (Gdx.graphics.getWidth()*pos)/gameInterface.POSITION_MAX;
        localState.paddleSelf.setX(XP);

    }

}
