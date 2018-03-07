package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import pipi.piball.Asserts.SoundsList;
import ser.pipi.piball.SettingsStruct;

/**
 * Created by ser on 20.02.18.
 */

public class StateController {

    final String TAG = this.getClass().getName();

    final StateStore stateStore;
    final ReflactionSystem reflactionSystem;
    final LocalState localStore;


    public StateController(SettingsStruct ss, StateStore stateStore, LocalState localStore) {
        this.stateStore = stateStore;
        this.localStore = localStore;
        this.reflactionSystem = new ReflactionSystem(
                new Rectangle(
                        0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
    }

    public void update(float delta){
        //paddleEnemy(delta);
        ball(delta);
        final boolean wasReflection = reflection();
        reflectionEffect(wasReflection);

        int goal = reflactionSystem.goal(stateStore);
        if(goal != 0){
            resetMatch(goal);
            stateStore.soundEffect = SoundSystem.appendSound(stateStore.soundEffect, SoundsList.effects.referee);
        } else {
            stateStore.soundEffect = SoundSystem.removeSound(stateStore.soundEffect, SoundsList.effects.referee);
        }
    }

    private void paddleEnemy(float delta){
        final float XP = localStore.paddleSelf.getX();
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

        if (wasReflection){
            Gdx.app.log(TAG, "appendSound " + SoundsList.effects.kick);
            stateStore.soundEffect = SoundSystem.appendSound(stateStore.soundEffect, SoundsList.effects.kick);
        } else {
            stateStore.soundEffect = SoundSystem.removeSound(stateStore.soundEffect, SoundsList.effects.kick);
        }
    }

    private boolean reflection() {
        return reflactionSystem.check(stateStore);
    }

    private void resetMatch(int goal){
        stateStore.ball.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        stateStore.ballVelocity.set(0,goal*valueBallVelocity());
    }
}
