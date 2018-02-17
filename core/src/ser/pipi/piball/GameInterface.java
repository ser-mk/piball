package ser.pipi.piball;

/**
 * Created by ser on 17.02.18.
 */

public interface GameInterface {

    public static final int POSITION_UNDEFINED = -1;
    public static final int CONNECTED_PROBLEM = -2;
    public static final int CLOSE_GAME = -3;

    public static final int POSITION_MIN = 0;
    public static final int POSITION_MAX = 1000;

    public int getPosition();

    public void release();
}
