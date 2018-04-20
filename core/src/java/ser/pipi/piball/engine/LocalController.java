package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;

import ser.pipi.piball.PositionInterface;
import ser.pipi.piball.Settings;

/**
 * Created by ser on 20.02.18.
 */

public class LocalController {

    final String TAG = this.getClass().getName();

    final PositionInterface positionInterface;
    final LocalState localState;
    final Settings ss;
    int current_position = 0;
    float current_velocity = 0;


    public LocalController(Settings ss, LocalState localState, PositionInterface positionInterface) {
        this.positionInterface = positionInterface;
        this.localState = localState;
        this.ss = ss;
        current_position = positionInterface.POSITION_MAX / 2;
    }

    public void update(float delta){

        localState.inputStatus = positionInterface.getState();

        int pos = positionInterface.getPosition();
        if (pos < PositionInterface.POSITION_MIN){
            return;
        }
        pos = filterLFPosition(delta, pos);
        current_position = pos;
        final float XP = (Gdx.graphics.getWidth()*pos)/ positionInterface.POSITION_MAX;
        setXCenterPaddle(XP);
    }

    private static boolean collinearVelocity(float v1, float v2){
        if(v1 > 0 && v2 > 0){
            return true;
        }
        if (v1 < 0 && v2 < 0){
            return true;
        }
        return false;
    }

    private static float signV(float v){ return v > 0 ? 1 : -1; }

    private int filterLFPosition(float delta, int pos){

        if(Math.abs(pos - current_position) < ss.DEAD_DIFF_POSITION_CHANGE_PADDLE)
            return current_position;

        float calcVelocity = (pos - current_position)/delta;

        if(!collinearVelocity(calcVelocity, current_velocity)){
            calcVelocity = ss.MIN_ACC_PADDLE * signV(calcVelocity);
        } else { // same diriction
            if (Math.abs(current_velocity) < ss.TRESHOLD_VELOCITY_FOR_MAX_ACC_PADDLE){
                calcVelocity = current_velocity + signV(current_velocity)*ss.MIN_ACC_PADDLE *delta;
            } else {
                calcVelocity = current_velocity + signV(current_velocity)*ss.MAX_ACC_PADDLE *delta;
            }
        }

        if(Math.abs(calcVelocity) > ss.MAX_VELOCITY_PADDLE){
            calcVelocity = ss.MAX_VELOCITY_PADDLE * signV(calcVelocity);
        }
        
        current_velocity = calcVelocity;

        return current_position + (int)(calcVelocity*delta);
    }

    private void setXCenterPaddle(float xc){
        localState.paddleSelf.x = xc - localState.paddleSelf.getWidth()/2;
    }

}
