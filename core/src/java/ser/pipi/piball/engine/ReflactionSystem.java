package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ser on 01.03.18.
 */

class ReflactionSystem {

    private final String TAG = this.getClass().getName();

    public enum ReflectObject {NONE, LEFT_BORDER, RIGHT_BORDER, SELF_PADDLE, ENEMY_PADDLE}

    static ReflectObject lastReflect = ReflectObject.NONE;
    final Rectangle borderLine;

    public ReflactionSystem(Rectangle borderLine) {
        this.borderLine = borderLine;
    }

    public boolean check(StateStore stateStore){
        final ReflectObject reflect = checkBall(stateStore);
        if (reflect == ReflectObject.NONE){
            lastReflect = reflect;
            return false;
        }

        if (reflect == lastReflect){
            return false;
        }

        switch (reflect){
            case LEFT_BORDER:
            case RIGHT_BORDER:
                stateStore.ballVelocity = forwardXReflectBall(stateStore.ballVelocity);
                break;
            case SELF_PADDLE:
            case ENEMY_PADDLE:
                stateStore.ballVelocity = paddleReflectBall(
                        stateStore, reflect);
                break;
        }

        lastReflect = reflect;

        return true;
    }

    private ReflectObject checkBall(StateStore stateStore){
        if(Intersector.overlaps(stateStore.ball,stateStore.paddleSelf)){
            return ReflectObject.SELF_PADDLE;
        }

        if(Intersector.overlaps(stateStore.ball,stateStore.paddleEnemy)){
            return ReflectObject.ENEMY_PADDLE;
        }

        //left border
        if(stateStore.ball.x - stateStore.ball.radius <= borderLine.getX()){
            return ReflectObject.LEFT_BORDER;
        }
        if(stateStore.ball.x + stateStore.ball.radius >= borderLine.getX() + borderLine.getWidth()){
            return ReflectObject.RIGHT_BORDER;
        }

        return ReflectObject.NONE;
    }

    static private Vector2 forwardXReflectBall(Vector2 velocityBall){
        Vector2 newVelocity = velocityBall.cpy();
        newVelocity.set(-newVelocity.x, newVelocity.y);
        return  newVelocity;
    }

    final static int FOCUS = 220;
    final static float deltaGap = 2;


    static public Vector2 paddleReflectBall(StateStore stateStore, ReflectObject type){
        Vector2 focusVector = new Vector2();
        if (type == ReflectObject.ENEMY_PADDLE){
            focusVector = stateStore.paddleEnemy.getCenter(focusVector);
            focusVector.y = FOCUS;
        } else {
            focusVector = stateStore.paddleSelf.getCenter(focusVector);
            focusVector.y = -FOCUS;
        }
        focusVector.x = focusVector.x - stateStore.ball.x;

        return focusReflectBall(stateStore.ballVelocity, focusVector);
    }

    static public Vector2 focusReflectBall(Vector2 velocityBall, Vector2 focus){
        Vector2 newVelocity = velocityBall.cpy();
        final float angle = focus.angle(newVelocity);
        System.out.println("angle " + angle);

        float rotAngle = 180;
        if (angle > 0){
            rotAngle -= 2*angle;
        } else {
            rotAngle = 2*(-angle) - rotAngle;
        }

        return newVelocity.rotate(rotAngle);
    }

    static public boolean equalsDergee(float degreeRef, float degree, float epsilon){
        if (Math.abs(degreeRef - degree) < deltaGap){
            return true;
        }

        if (Math.abs(360 + degreeRef - degree) < deltaGap){
            return true;
        }

        if (Math.abs(degreeRef - degree - 360) < deltaGap){
            return true;
        }

        return false;
    }

    public int goal(StateStore stateStore){
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

}
