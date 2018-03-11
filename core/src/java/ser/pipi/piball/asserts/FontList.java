package ser.pipi.piball.asserts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    public static BitmapFont welcomeFont(){
        BitmapFont font = new BitmapFont();
        font.setColor(Color.RED);
        font.getData().setScale(2);
        return font;
    }

    public static void printTextCenter(SpriteBatch spriteBatch, BitmapFont font,
                                       String text, float X, float Y){
        final GlyphLayout layout = new GlyphLayout(font, text);
        final float fontX = X - layout.width/2;
        final float fontY = Y - layout.height/2;
        font.draw(spriteBatch, layout, fontX, fontY);
    }
}
