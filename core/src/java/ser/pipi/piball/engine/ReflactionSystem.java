package ser.pipi.piball.engine;

import com.badlogic.gdx.math.Intersector;

/**
 * Created by ser on 01.03.18.
 */

class ReflactionSystem {

    public enum ReflectObject {NONE, LEFT_BORDER, RIGHT_BORDER, SELF_PADDLE, ENEMY_PADDLE}

    static ReflectObject lastReflect = ReflectObject.NONE;
    StateStore stateStore;

    public ReflectObject check(){
        if(Intersector.overlaps(stateStore.ball,stateStore.paddleSelf)){
            return ReflectObject.SELF_PADDLE;
        }

        return ReflectObject.NONE;

    }
}
