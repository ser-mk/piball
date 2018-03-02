package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;

import pipi.piball.Asserts.SoundsList;
import ser.pipi.piball.GameInterface;

/**
 * Created by ser on 20.02.18.
 */

public class StateController {

    final String TAG = this.getClass().getName();

    final StateStore stateStore;
    final GameInterface gameInterface;
    final ReflactionSystem reflactionSystem;


    public StateController(StateStore stateStore, GameInterface gameInterface) {
        this.stateStore = stateStore;
        this.gameInterface = gameInterface;
        this.reflactionSystem = new ReflactionSystem(
                new Rectangle(
                        0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
    }

    public void update(float delta){
        gameInterface.update();
        paddleSelf(delta);
        ball(delta);
        final boolean wasReflection = reflection();
        reflectionEffect(wasReflection);

        int goal = reflactionSystem.goal(stateStore);
        if(goal != 0){
            resetMatch(goal);
        }
    }

    private void paddleSelf(float delta){
        final int pos = gameInterface.getPosition();
        if (pos < GameInterface.POSITION_MIN){
            return;
        }
        final float XP = (Gdx.graphics.getWidth()*pos)/gameInterface.POSITION_MAX;
        stateStore.paddleSelf.setX(XP);
        stateStore.paddleEnemy.setX(XP);
    }

    private float valueBallVelocity(){
        return 222;
    }

    private void ball(float delta){
        Vector2 delta_pos = stateStore.ballVelocity.cpy().scl(delta);
        float new_x_pos = stateStore.ball.x + delta_pos.x;
        float new_y_pos = stateStore.ball.y + delta_pos.y;
        stateStore.ball.setPosition(new_x_pos, new_y_pos);
    }



    private void reflectionEffect(boolean wasReflection){
        ArrayList<String> list = new ArrayList( Arrays.asList(stateStore.soundEffect));

        if (wasReflection){
            Gdx.app.log(TAG, "appendSound " + SoundsList.effects.kick);
            SoundSystem.appendSound(list, SoundsList.effects.kick);
        } else {
            SoundSystem.removeSound(list, SoundsList.effects.kick);
        }
        stateStore.soundEffect = list.toArray(new String[0]);
    }

    private boolean reflection() {
        return reflactionSystem.check(stateStore);
    }

    private void resetMatch(int goal){
        stateStore.ball.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        stateStore.ballVelocity.set(0,goal*valueBallVelocity());
    }
}
