package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.awt.Rectangle;
import java.math.MathContext;

import ser.pipi.piball.GameInspector;
import ser.pipi.piball.GameInterface;
import ser.pipi.piball.SettingsStruct;

/**
 * Created by ser on 20.02.18.
 */

public class LocalController {

    final String TAG = this.getClass().getName();

    final GameInterface gameInterface;
    final GameInspector gameInspector;
    final LocalState localState;
    final SettingsStruct ss;
    int current_position = 0;
    

    public LocalController(SettingsStruct ss, LocalState localState, GameInterface gameInterface) {
        this.gameInterface = gameInterface;
        this.localState = localState;
        this.gameInspector = new GameInspector(gameInterface);
        this.ss = ss;
        current_position = gameInterface.POSITION_MAX / 2;
    }

    public void update(float delta){
        gameInspector.checkPiPos(delta);
        localState.statusPI = gameInspector.getStatus();

        int pos = gameInterface.getPosition();
        if (pos < GameInterface.POSITION_MIN){
            return;
        }
        pos = filterLFPosition(delta, pos);
        current_position = pos;
        final float XP = (Gdx.graphics.getWidth()*pos)/gameInterface.POSITION_MAX;
        //localState.paddleSelf.setX(XP);
        setXCenterPaddle(XP);
    }

    private int filterLFPosition(float delta, int pos){
        float diff = (pos - current_position)/delta;
        if (Math.abs(diff) > ss.maxVelocityPaddle){
            Gdx.app.log(TAG, "max diff " + diff);
            diff = diff > 0 ? ss.maxVelocityPaddle : -ss.maxVelocityPaddle;
        }
        return current_position + (int)(diff*delta);
    }

    private void setXCenterPaddle(float xc){
        localState.paddleSelf.x = xc - localState.paddleSelf.getWidth()/2;
    }

}
