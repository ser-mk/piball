package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import ser.pipi.piball.PositionInterface;
import ser.pipi.piball.Settings;

/**
 * Created by ser on 25.02.18.
 */

public class LocalState {
    Rectangle paddleSelf;
    String statusPI;
    String statusNET;
    PositionInterface.InputStatus inputStatus;

    int flag;

    public LocalState(){}

    public LocalState(Settings ss, int flag) {
        final float X_MID = Gdx.graphics.getWidth()/2;
        final float Y_PADDLE_SELF = ss.Y_PADDLES_HIFT;
        final float WIDTH_PADDLE = ss.WIDTH_PADDLE;
        final float HEIGHT_PADDLE = ss.HEIGHT_PADDLE;
        this.paddleSelf = new Rectangle(X_MID, Y_PADDLE_SELF, WIDTH_PADDLE, HEIGHT_PADDLE);
        this.statusPI = new String();
        this.statusNET = new String();
        this.flag = flag;
        inputStatus = PositionInterface.InputStatus.POSITION_UNDEFINED;
    }
}
