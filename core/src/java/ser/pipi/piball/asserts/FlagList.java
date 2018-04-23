package ser.pipi.piball.asserts;

import com.badlogic.gdx.graphics.Texture;

import ser.pipi.piball.Welcome;

/**
 * Created by ser on 09.03.18.
 */

public class FlagList {

    public static final int NUM_COUNTRY = 32;
    static final Texture[] country = new Texture[NUM_COUNTRY];

    public FlagList() {
        init();
    }

    static private void init(){
        for (int i = 0; i < NUM_COUNTRY; i++){
            country[i] = new Texture("country/" + i + ".png");
        }
    }

    public Texture getFlag(int number){
        return country[number];
    }

    public boolean consistNumberFlag(int number){
        return number != Welcome.UNDEFINED_FLAG && number < NUM_COUNTRY;
    }
}
