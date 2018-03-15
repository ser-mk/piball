package ser.pipi.piball.asserts;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by ser on 10.03.18.
 */

public class TextureList {
    //public static final String RESULT_FON = "final_logo.png";
    public static final String RESULT_FON = "result_w_s.png";
    public static final String WELCOME = "green_welcome_wr3.png";
    //public static final String ARENA = "field.png";
    public static final String ARENA = "field_simple_score.png";
    public static final String BALL = "big.png";
    public static final String PADDLE_ENEMY = "paddle_enemy.png";
    public static final String PADDLE_SELF = "paddle_self.png";

    public static Texture loadTexture(String name){
        return new Texture(name);
    }
}
