package ser.pipi.piball.engine;

import com.badlogic.gdx.math.Vector2;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ser on 01.03.18.
 */
public class ReflactionSystemTest {

    static void assertDegree(float degreeRef, float degree, float epsilon) throws Exception{

    }

    @Test
    public void focusReflectBall() throws Exception {

        Vector2 foc = new Vector2(1,0);
        Vector2 vel = foc.cpy().rotate(30);

        Vector2 ret = ReflactionSystem.focusReflectBall(vel,foc);
        System.out.println("ret " + ret + " "  + ret.angle());
        Assert.assertEquals(ret.angle(),150,2);

        vel = foc.cpy().rotate(180);
        ret = ReflactionSystem.focusReflectBall(vel,foc);
        System.out.println("ret " + ret + " "  + ret.angle());
        //Assert.assertEquals(ret.angle(),0,2);
        Assert.assertTrue(ReflactionSystem.equalsDergee(0,ret.angle(),2));

        vel = foc.cpy();
        ret = ReflactionSystem.focusReflectBall(vel,foc);
        System.out.println("ret " + ret + " " +ret.angle() + " " + foc.angle());
        Assert.assertEquals(ret.angle(),foc.angle(),2);

        vel = foc.cpy().rotate(-60);
        ret = ReflactionSystem.focusReflectBall(vel,foc);
        System.out.println("ret " + ret + " "  + ret.angle());
        Assert.assertEquals(ret.angle(),240,2);

    }

}