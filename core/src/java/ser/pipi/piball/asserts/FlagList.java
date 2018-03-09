package ser.pipi.piball.asserts;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by ser on 09.03.18.
 */

public class FlagList {

    static final int NUM_COUNTRY = 32;
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
        return number > 0 && number < NUM_COUNTRY;
    }
}
