package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ser.pipi.piball.SettingsStruct;

/**
 * Created by ser on 20.02.18.
 */

public class AllObjectsState {
    Rectangle paddleSelf;
    String statusPISelf;
    int flagSelf;
    Rectangle paddleEnemy;
    String statusPIEnemy;
    int flagEnemy;
    Circle ball;
    Vector2 ballVelocity;
    public int selfGoal;
    int enemyGoal;
    String[] soundEffect;
    String musicFon;

    public AllObjectsState() {
    }

    public AllObjectsState(SettingsStruct ss) {
        final float X_MID = Gdx.graphics.getWidth()/2;
        final float Y_PADDLE_SELF = ss.yPaddleSelf;
        final float WIDTH_PADDLE = ss.widthPaddle;
        final float HEIGHT_PADDLE = ss.heigthPaddle;
        paddleSelf = new Rectangle(X_MID, Y_PADDLE_SELF, WIDTH_PADDLE, HEIGHT_PADDLE);
        paddleEnemy = new Rectangle(paddleSelf);
        paddleEnemy.setY(Gdx.graphics.getHeight() - Y_PADDLE_SELF - HEIGHT_PADDLE);
        statusPIEnemy = new String();
        statusPISelf = statusPIEnemy;
        final float Y_START_BALL = Gdx.graphics.getHeight()/2;
        final float RADIUS_BALL = ss.radiusBall;
        ball = new Circle(X_MID, Y_START_BALL, RADIUS_BALL);
        final float X_VELOCITY = 0;
        final float Y_VELOCITY = ss.startBallVelocity;
        ballVelocity = new Vector2(X_VELOCITY, Y_VELOCITY);
        selfGoal = enemyGoal = 0;
        flagSelf = flagEnemy = -1;

        soundEffect = new String[0];
        musicFon = SoundSystem.getDefaultMusic();
    }
}
