package ser.pipi.piball.asserts;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by ser on 10.03.18.
 */

public class FontList {
    public static BitmapFont resultFont(){
        BitmapFont font = new BitmapFont();
        //font.setColor();
        font.getData().setScale(5f);
        return font;
    }
}
