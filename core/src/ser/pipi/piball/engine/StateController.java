package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ser.pipi.piball.GameInspector;
import ser.pipi.piball.GameInterface;

/**
 * Created by ser on 20.02.18.
 */

public class StateController {

    final String TAG = this.getClass().getName();

    final StateStore stateStore;
    final GameInterface gameInterface;

    Rectangle borderLine;

    public StateController(StateStore stateStore, GameInterface gameInterface) {
        this.stateStore = stateStore;
        this.gameInterface = gameInterface;
        this.borderLine = new Rectangle(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    public void update(float delta){
        gameInterface.update();
        paddleSelf(delta);
        ball(delta);
        reflection();
        int goal = goal();
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
        return 155;
    }

    private void ball(float delta){
        Vector2 delta_pos = stateStore.ballVelocity.cpy().scl(delta);
        float new_x_pos = stateStore.ball.x + delta_pos.x;
        float new_y_pos = stateStore.ball.y + delta_pos.y;
        stateStore.ball.setPosition(new_x_pos, new_y_pos);
    }

    private Vector2 reflectPaddleBall(Circle ball, Vector2 velocityBall,
                                   Rectangle paddle, float focus){
        final float x_mid_paddle = paddle.getX() + paddle.getWidth()/2;
        float y_focus_paddle;
        if(focus > 0) {
            y_focus_paddle = paddle.getY() + paddle.getHeight() + focus;
        } else {
            y_focus_paddle = paddle.getY() + focus;
        }
        float x_ball_velocity = ball.x - x_mid_paddle;
        float y_ball_velocity = ball.y - y_focus_paddle;
        Vector2 new_velocity = new Vector2(x_ball_velocity, y_ball_velocity);
        new_velocity.nor().scl(valueBallVelocity());
        return new_velocity;
    }

    private void reflection(){
        if(Intersector.overlaps(stateStore.ball,stateStore.paddleSelf)){
            stateStore.ballVelocity = reflectPaddleBall(stateStore.ball, stateStore.ballVelocity,
                    stateStore.paddleSelf, -22);
        }

        if(Intersector.overlaps(stateStore.ball,stateStore.paddleEnemy)){
            stateStore.ballVelocity = reflectPaddleBall(stateStore.ball, stateStore.ballVelocity,
                    stateStore.paddleEnemy, +22);
        }

        IntersectionBorderLine(stateStore.ball, stateStore.ballVelocity, borderLine);
    }

    private boolean noTouchBorderLine = true;
    private void IntersectionBorderLine(Circle ball, Vector2 ballVelocity, Rectangle borderLine){

        int rotateBall = checkIntersectionBorderLine(stateStore.ball, borderLine);

        if(rotateBall != 0){
            //rotateBall = ballVelocity.angle() > 180 ? -rotateBall : rotateBall;
            if(noTouchBorderLine) {
                //ballVelocity.rotate90(rotateBall);
                stateStore.ballVelocity = reflectVertcalVector(stateStore.ballVelocity);
                Gdx.app.log(TAG, "RotateBall " + rotateBall);
                noTouchBorderLine = false;
            }
        } else {
                noTouchBorderLine = true;
                //Gdx.app.log(TAG, "noTouchBorderLine " + noTouchBorderLine);
        }
    }

    static Vector2 reflectVertcalVector(Vector2 velocity){
        Vector2 rotateVelocity = velocity.cpy();
        final float angle = rotateVelocity.angle();
        float rotateAngle = angle < 180 ? 180 - angle : 540-angle;
        rotateVelocity.setAngle(rotateAngle);
        return rotateVelocity;
    }

    static private int checkIntersectionBorderLine(Circle ball, Rectangle borderLine){
        //left border
        if(ball.x - ball.radius <= borderLine.getX()){
            return -1;
        }
        if(ball.x + ball.radius >= borderLine.getX() + borderLine.getWidth()){
            return 1;
        }
        return 0;
    }

    private int goal(){
        int goal = checkGoal(stateStore.ball, borderLine);
        if(goal == 0)
            return 0;
        if(goal > 0){
            stateStore.selfGoal +=1;
        } else {
            stateStore.enemyGoal += 1;
        }
        return goal;
    }

    static private int  checkGoal(Circle ball, Rectangle goalLine){
        if(goalLine.getY() > ball.y)
            return -1;
        if(goalLine.getY() + goalLine.getHeight() < ball.y)
            return +1;

        return 0;
    }

    private void resetMatch(int goal){
        stateStore.ball.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        stateStore.ballVelocity.set(0,goal*valueBallVelocity());
    }
}
