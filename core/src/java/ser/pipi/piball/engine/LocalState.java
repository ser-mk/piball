package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import ser.pipi.piball.SettingsStruct;

/**
 * Created by ser on 25.02.18.
 */

public class LocalState {
    Rectangle paddleSelf;
    String statusPI;
    public String statusNET;

    public LocalState(){}

    public LocalState(SettingsStruct ss) {
        final float X_MID = Gdx.graphics.getWidth()/2;
        final float Y_PADDLE_SELF = ss.yPaddleSelf;
        final float WIDTH_PADDLE = ss.widthPaddle;
        final float HEIGHT_PADDLE = ss.heigthPaddle;
        this.paddleSelf = new Rectangle(X_MID, Y_PADDLE_SELF, WIDTH_PADDLE, HEIGHT_PADDLE);
        this.statusPI = new String();
        this.statusNET = new String();
    }
}
