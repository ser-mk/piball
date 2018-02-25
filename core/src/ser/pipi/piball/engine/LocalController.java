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
    LocalState localStore;


    public LocalController(LocalState localStore, GameInterface gameInterface) {
        this.gameInterface = gameInterface;
        this.localStore = localStore;
        this.gameInspector = new GameInspector(gameInterface);
    }

    public void update(float delta){
        gameInspector.checkPiPos(delta);
        localStore.status = gameInspector.getStatus();

        final int pos = gameInterface.getPosition();
        if (pos < GameInterface.POSITION_MIN){
            return;
        }
        final float XP = (Gdx.graphics.getWidth()*pos)/gameInterface.POSITION_MAX;
        localStore.paddleSelf.setX(XP);

    }

}
