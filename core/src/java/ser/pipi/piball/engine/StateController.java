package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ser.pipi.piball.SettingsStruct;
import ser.pipi.piball.asserts.SoundsList;

/**
 * Created by ser on 20.02.18.
 */

public class StateController {

    final String TAG = this.getClass().getName();

    final AllObjectsState allObjectsState;
    final ReflactionSystem reflactionSystem;
    final LocalState localStore;
    final SettingsStruct ss;


    public StateController(SettingsStruct ss, AllObjectsState allObjectsState, LocalState localStore) {
        this.allObjectsState = allObjectsState;
        this.localStore = localStore;
        this.ss = ss;
        this.reflactionSystem = new ReflactionSystem(ss,
                new Rectangle(
                        0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
    }

    public void update(float delta){
        paddleSelf(delta);
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

    private void paddleSelf(float delta){
        final float XP = localStore.paddleSelf.getX();
        allObjectsState.paddleSelf.setX(XP);
        allObjectsState.statusPISelf = localStore.statusPI;
        allObjectsState.flagSelf = localStore.flag;
        allObjectsState.inputStatusSelf = localStore.inputStatus;
    }

    private void ball(float delta){
        Vector2 delta_pos = allObjectsState.ballVelocity.cpy().scl(delta);
        float new_x_pos = allObjectsState.ball.x + delta_pos.x;
        float new_y_pos = allObjectsState.ball.y + delta_pos.y;
        allObjectsState.ball.setPosition(new_x_pos, new_y_pos);
        if (allObjectsState.ballVelocity.len() < ss.maxBallVelocity) {
            allObjectsState.ballVelocity.scl(1 + ss.ballAcc * delta);
        }
    }



    private void reflectionEffect(boolean wasReflection){

        if (wasReflection){
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
        final int all_goals = allObjectsState.enemyGoal + allObjectsState.selfGoal;
        final float velocity = ss.startBallVelocity + all_goals * ss.goalStepVelocity;
        allObjectsState.ballVelocity.set(0,goal*velocity);
        allObjectsState.ballVelocity.limit(ss.maxBallVelocity);
    }
}
