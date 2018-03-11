package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;

import ser.pipi.piball.PositionInspector;
import ser.pipi.piball.PositionInterface;
import ser.pipi.piball.SettingsStruct;

/**
 * Created by ser on 20.02.18.
 */

public class LocalController {

    final String TAG = this.getClass().getName();

    final PositionInterface positionInterface;
    final LocalState localState;
    final SettingsStruct ss;
    int current_position = 0;
    float current_velocity = 0;


    public LocalController(SettingsStruct ss, LocalState localState, PositionInterface positionInterface) {
        this.positionInterface = positionInterface;
        this.localState = localState;
        this.ss = ss;
        current_position = positionInterface.POSITION_MAX / 2;
    }

    public void update(float delta){

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

        if(Math.abs(pos - current_position) < ss.deadDiff)
            return current_position;

        float calcVelocity = (pos - current_position)/delta;

        if(!collinearVelocity(calcVelocity, current_velocity)){
            calcVelocity = ss.minAccPaddle * signV(calcVelocity);
        } else { // same diriction
            if (Math.abs(current_velocity) < ss.minVelocityPaddle){
                calcVelocity = current_velocity + signV(current_velocity)*ss.minAccPaddle*delta;
            } else {
                calcVelocity = current_velocity + signV(current_velocity)*ss.maxAccPaddle*delta;
            }
        }

        if(Math.abs(calcVelocity) > ss.maxVelocityPaddle){
            calcVelocity = ss.maxVelocityPaddle * signV(calcVelocity);
        }
        
        current_velocity = calcVelocity;

        return current_position + (int)(calcVelocity*delta);
    }

    private void setXCenterPaddle(float xc){
        localState.paddleSelf.x = xc - localState.paddleSelf.getWidth()/2;
    }

}
