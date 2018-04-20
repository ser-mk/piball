package ser.pipi.piball.engine;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ser.pipi.piball.Settings;

/**
 * Created by ser on 01.03.18.
 */

class ReflactionSystem {

    private final String TAG = this.getClass().getName();

    public enum ReflectObject {NONE, LEFT_BORDER, RIGHT_BORDER, SELF_PADDLE, ENEMY_PADDLE}

    static ReflectObject lastReflect = ReflectObject.NONE;
    final Rectangle borderLine;
    final Settings ss;

    public ReflactionSystem(Settings ss, Rectangle borderLine) {
        this.borderLine = borderLine;
        this.ss = ss;
    }

    public boolean check(AllObjectsState allObjectsState){
        final ReflectObject reflect = checkBall(allObjectsState);
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
                allObjectsState.ballVelocity = forwardXReflectBall(allObjectsState.ballVelocity);
                break;
            case SELF_PADDLE:
            case ENEMY_PADDLE:
                allObjectsState.ballVelocity = paddleReflectBall(
                        allObjectsState, reflect);
                break;
        }

        lastReflect = reflect;

        return true;
    }

    private ReflectObject checkBall(AllObjectsState allObjectsState){
        if(Intersector.overlaps(allObjectsState.ball, allObjectsState.paddleSelf)){
            return ReflectObject.SELF_PADDLE;
        }

        if(Intersector.overlaps(allObjectsState.ball, allObjectsState.paddleEnemy)){
            return ReflectObject.ENEMY_PADDLE;
        }

        //left border
        if(allObjectsState.ball.x - allObjectsState.ball.radius <= borderLine.getX()){
            return ReflectObject.LEFT_BORDER;
        }
        if(allObjectsState.ball.x + allObjectsState.ball.radius >= borderLine.getX() + borderLine.getWidth()){
            return ReflectObject.RIGHT_BORDER;
        }

        return ReflectObject.NONE;
    }

    static private Vector2 forwardXReflectBall(Vector2 velocityBall){
        Vector2 newVelocity = velocityBall.cpy();
        newVelocity.set(-newVelocity.x, newVelocity.y);
        return  newVelocity;
    }

    //final static float deltaGap = 2;


    private Vector2 paddleReflectBall(AllObjectsState allObjectsState, ReflectObject type){
        Vector2 focusVector = new Vector2();
        if (type == ReflectObject.ENEMY_PADDLE){
            focusVector = allObjectsState.paddleEnemy.getCenter(focusVector);
            focusVector.y = ss.FOCUS_PADDLE;
        } else {
            focusVector = allObjectsState.paddleSelf.getCenter(focusVector);
            focusVector.y = -ss.FOCUS_PADDLE;
        }
        focusVector.x = focusVector.x - allObjectsState.ball.x;

        return focusReflectBall(allObjectsState.ballVelocity, focusVector);
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
        if (Math.abs(degreeRef - degree) < epsilon){
            return true;
        }

        if (Math.abs(360 + degreeRef - degree) < epsilon){
            return true;
        }

        if (Math.abs(degreeRef - degree - 360) < epsilon){
            return true;
        }

        return false;
    }

    public int goal(AllObjectsState allObjectsState){
        int goal = checkGoal(allObjectsState.ball, borderLine);
        if(goal == 0)
            return 0;
        if(goal > 0){
            allObjectsState.selfGoal +=1;
        } else {
            allObjectsState.enemyGoal += 1;
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
