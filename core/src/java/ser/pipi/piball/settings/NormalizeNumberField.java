package ser.pipi.piball.settings;

import java.util.HashMap;

import ser.pipi.piball.Settings;

/**
 * Created by ser on 20.04.18.
 */

public class NormalizeNumberField {

    public static class Interval {
        public final float min;
        public final float max;

        public Interval(float min, float max) {
            this.min = min;
            this.max = max;
            if (max < min) {
                throw new AssertionError("if(max < min)");
            }
        }
    }

    public static String getNote(String name){
        Interval interval = mapInterval.get(name);

        return String.valueOf(interval.min)
                + " ... " + String.valueOf(interval.max);
    }

    public static Interval getInterval(String name){
        return mapInterval.get(name);
    }

    public static int checkIntInterval(String name, int value){
        Interval interval = getInterval(name);
        if(value < interval.min){
            value = Math.round(interval.min);
        }

        if(value > interval.max){
            value = Math.round(interval.max);
        }

        return value;
    }

    public static float checkIntInterval(String name, float value){
        Interval interval = getInterval(name);
        if(value < interval.min){
            value = interval.min;
        }

        if(value > interval.max){
            value = interval.max;
        }

        return value;
    }

    public static final  HashMap<String,Interval> mapInterval;// = new HashMap<String, Interval>();

    static {
        mapInterval = new HashMap<String, Interval>();

        Settings settings = new Settings();

        mapInterval.put("WAIT_SEC_END_WELCOME", new Interval(0,5));
        mapInterval.put("Y_PADDLES_HIFT", new Interval(-20,300));
        mapInterval.put("WIDTH_PADDLE", new Interval(50,500));
        mapInterval.put("HEIGHT_PADDLE", new Interval(2,200));
        mapInterval.put("RADIUS_BALL", new Interval(5,150));
        mapInterval.put("FOCUS_PADDLE", new Interval(2,100000));
        mapInterval.put("PORT_GROUP", new Interval(-10,100));
        mapInterval.put("TIMEOUT_MS_DISCOVERING_HOST", new Interval(0,5*1000));
        mapInterval.put("TIMEOUT_MS_SERVER_CONNECTING", new Interval(0,5*1000));
        mapInterval.put("SEND_PERIOD_SEC", new Interval(0,1));
        mapInterval.put("DEAD_DIFF_POSITION_CHANGE_PADDLE", new Interval(1,500));
        mapInterval.put("MAX_VELOCITY_PADDLE", new Interval(10,10*1000));
        mapInterval.put("TRESHOLD_VELOCITY_FOR_MAX_ACC_PADDLE", new Interval(1,1000));
        mapInterval.put("MIN_ACC_PADDLE", new Interval(1,2000));
        mapInterval.put("MAX_ACC_PADDLE", new Interval(1,10000));
        mapInterval.put("RATE_ANGLE_ROTATE_RELATE_LINIAR_VELOCITY", new Interval(0,2));
        mapInterval.put("START_BALL_VELOCITY", new Interval(-500,500));
        mapInterval.put("BALL_ACC", new Interval(0,10));
        mapInterval.put("MAX_BALL_VELOCITY", new Interval(10,10*1000));
        mapInterval.put("ADD_VELOCITY_AFTER_GOAL", new Interval(0,1000));
        mapInterval.put("TIMEOUT_SECOND_TOUCH_FLAG", new Interval(0,2));


        //mapInterval.put("", new Interval(,));
    }
}
