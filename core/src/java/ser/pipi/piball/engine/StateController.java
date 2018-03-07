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

    final AllObjectsState allObjectsState;
    final ReflactionSystem reflactionSystem;
    final LocalState localStore;


    public StateController(SettingsStruct ss, AllObjectsState allObjectsState, LocalState localStore) {
        this.allObjectsState = allObjectsState;
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

        int goal = reflactionSystem.goal(allObjectsState);
        if(goal != 0){
            resetMatch(goal);
            allObjectsState.soundEffect = SoundSystem.appendSound(allObjectsState.soundEffect, SoundsList.effects.referee);
        } else {
            allObjectsState.soundEffect = SoundSystem.removeSound(allObjectsState.soundEffect, SoundsList.effects.referee);
        }
    }

    private void paddleEnemy(float delta){
        final float XP = localStore.paddleSelf.getX();
        allObjectsState.paddleEnemy.setX(XP);
    }

    private float valueBallVelocity(){
        return 222;
    }

    private void ball(float delta){
        Vector2 delta_pos = allObjectsState.ballVelocity.cpy().scl(delta);
        float new_x_pos = allObjectsState.ball.x + delta_pos.x;
        float new_y_pos = allObjectsState.ball.y + delta_pos.y;
        allObjectsState.ball.setPosition(new_x_pos, new_y_pos);
    }



    private void reflectionEffect(boolean wasReflection){

        if (wasReflection){
            Gdx.app.log(TAG, "appendSound " + SoundsList.effects.kick);
            allObjectsState.soundEffect = SoundSystem.appendSound(allObjectsState.soundEffect, SoundsList.effects.kick);
        } else {
            allObjectsState.soundEffect = SoundSystem.removeSound(allObjectsState.soundEffect, SoundsList.effects.kick);
        }
    }

    private boolean reflection() {
        return reflactionSystem.check(allObjectsState);
    }

    private void resetMatch(int goal){
        allObjectsState.ball.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        allObjectsState.ballVelocity.set(0,goal*valueBallVelocity());
    }
}
