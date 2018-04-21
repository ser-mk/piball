package ser.pipi.piball;

import com.badlogic.gdx.InputProcessor;

/**
 * Created by ser on 17.02.18.
 */

public abstract class PositionInterface implements InputProcessor {

    public static final int POSITION_MIN = 0;
    public static final int POSITION_MAX = 1000;

    public enum InputStatus{NORMAL_WORK,POSITION_UNDEFINED,
        CONNECTED_PROBLEM,CLOSE_GAME, BACKSPACE}

    protected InputStatus status = InputStatus.NORMAL_WORK;

    public abstract void update();
    public abstract int getPosition();

    public InputStatus getState() {
        return status;
    }

    public void clearState(){
        status = InputStatus.POSITION_UNDEFINED;
    }

    public String getStringErrorPiStatus(){
        return getStringErrorPiStatus(status);
    }

    static public String getStringErrorPiStatus(PositionInterface.InputStatus piState){
        switch (piState){

            case CLOSE_GAME: return "GAME END";
            case CONNECTED_PROBLEM: return "CAN'T FIND FLOW";
            case BACKSPACE: return "RETURN TO BACK";
            default: return "";
        }
    }

}
