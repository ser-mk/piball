package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ser.pipi.piball.PositionInterface;
import ser.pipi.piball.Settings;

/**
 * Created by ser on 20.02.18.
 */

public class AllObjectsState {

    Rectangle paddleSelf;
    String statusPISelf;
    PositionInterface.InputStatus inputStatusSelf;
    int flagSelf;
    Rectangle paddleEnemy;
    String statusPIEnemy;
    PositionInterface.InputStatus inputStatusEnemy;
    int flagEnemy;
    Circle ball;
    Vector2 ballVelocity;
    public int selfGoal;
    int enemyGoal;
    String[] soundEffect;
    String musicFon;

    public AllObjectsState() { // for kryonet
    }

    public AllObjectsState(Settings ss) {

        final float WIDTH_PADDLE = ss.widthPaddle;
        final float HEIGHT_PADDLE = ss.heigthPaddle;
        final float X_MID = (Gdx.graphics.getWidth() - WIDTH_PADDLE)/2;
        final float Y_PADDLE_SELF = ss.yPaddleSelf;

        paddleSelf = new Rectangle(X_MID, Y_PADDLE_SELF, WIDTH_PADDLE, HEIGHT_PADDLE);
        paddleEnemy = new Rectangle(paddleSelf);
        paddleEnemy.setY(Gdx.graphics.getHeight() - Y_PADDLE_SELF - HEIGHT_PADDLE);
        statusPIEnemy = new String();
        statusPISelf = statusPIEnemy;
        inputStatusEnemy = PositionInterface.InputStatus.POSITION_UNDEFINED;
        inputStatusSelf = PositionInterface.InputStatus.POSITION_UNDEFINED;
        final float X_START_BALL = Gdx.graphics.getWidth()/2;
        final float Y_START_BALL = Gdx.graphics.getHeight()/2;
        final float RADIUS_BALL = ss.radiusBall;
        ball = new Circle(X_START_BALL, Y_START_BALL, RADIUS_BALL);
        final float X_VELOCITY = 0;
        final float Y_VELOCITY = ss.startBallVelocity;
        ballVelocity = new Vector2(X_VELOCITY, Y_VELOCITY);
        selfGoal = enemyGoal = 0;
        flagSelf = flagEnemy = -1;

        soundEffect = new String[0];
        musicFon = SoundSystem.getDefaultMusic();
    }
}
