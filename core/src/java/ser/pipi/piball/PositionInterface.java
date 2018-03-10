package ser.pipi.piball;

/**
 * Created by ser on 17.02.18.
 */

public interface PositionInterface {

    public static final int POSITION_MIN = 0;
    public static final int POSITION_MAX = 1000;

    public enum InputStatus{NORMAL_WORK,POSITION_UNDEFINED,
        CONNECTED_PROBLEM,CLOSE_GAME, BACKSPACE}

    public void update();
    public int getPosition();
    public InputStatus getState();

}
