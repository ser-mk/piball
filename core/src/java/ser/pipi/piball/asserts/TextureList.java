package ser.pipi.piball.asserts;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by ser on 10.03.18.
 */

public class TextureList {
    public static final String FINAL_LOGO = "final_logo.png";
    public static final String WELCOME = "field.png";

    public static Texture loadTexture(String name){
        return new Texture(name);
    }
}
