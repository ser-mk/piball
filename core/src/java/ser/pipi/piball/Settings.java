package ser.pipi.piball;

/**
 * Created by ser on 20.02.18.
 */

public class Settings {
    public float WAIT_SEC_END_WELCOME = 2;

    public float Y_PADDLES_HIFT = 0;
    public float WIDTH_PADDLE = 155;
    public float HEIGHT_PADDLE = 22;
    public float RADIUS_BALL = 22;
    public float FOCUS_PADDLE = 220;
    public boolean IS_SERVER = true;
    public int PORT_GROUP = 0;
    public int TIMEOUT_MS_DISCOVERING_HOST = 2000;
    public int TIMEOUT_MS_SERVER_CONNECTING = 2000;
    public float SEND_PERIOD_SEC = 0;
    public boolean SEND_TCP = true;

    public int DEAD_DIFF_POSITION_CHANGE_PADDLE = 11;
    public int MAX_VELOCITY_PADDLE = 5555;
    public int TRESHOLD_VELOCITY_FOR_MAX_ACC_PADDLE = 55;
    public int MIN_ACC_PADDLE = 555;
    public int MAX_ACC_PADDLE = 2000;

    public float RATE_ANGLE_ROTATE_RELATE_LINIAR_VELOCITY = 0.2f;
    public int START_BALL_Y_VELOCITY = 200;
    public int START_BALL_X_VELOCITY = 0;

    public boolean IS_FORCED_LITTLE_ANGLE_REFLECT = true;
    public float LITTLE_ANGLE_HALF_INTERVAL_DEG = 10;
    public int ADD_DEG_FOR_FORCED_ANGLE = 30;

    public float BALL_ACC = 0.02f;
    public int MAX_BALL_VELOCITY = 555;
    public int ADD_VELOCITY_AFTER_GOAL = 33;

    public float TIMEOUT_SECOND_TOUCH_FLAG = 0.5f;

    public float TIMEOUT_SEC_DEADTIME_PADDLE_UNDEF = 1;

    public boolean SHOW_DEBUG = false;

    public boolean MANUAL_TOUCH_CONTROL = true;
    public boolean INVERT_POSITION = false;
}
