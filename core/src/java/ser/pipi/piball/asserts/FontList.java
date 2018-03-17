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

    static final String BEANTOWN = "beantown";
    static final String DIR = "fonts/";
    static final String SUFFIX = ".fnt";


    public static BitmapFont welcomeFont(){
        BitmapFont font = new BitmapFont(Gdx.files.internal(DIR + BEANTOWN + SUFFIX));
        font.setColor(Color.WHITE);
        font.getData().setScale(0.9f);
        return font;
    }


    public static BitmapFont arenaFont(){
        final BitmapFont bf = new BitmapFont(Gdx.files.internal(DIR + BEANTOWN + SUFFIX));
        bf.setColor(Color.BLACK);
        bf.getData().setScale(0.6f);
        return bf;
    }

    public static BitmapFont resultFont(){
        BitmapFont font = new BitmapFont(Gdx.files.internal(DIR + BEANTOWN + SUFFIX));
        //font.setColor();
        font.getData().setScale(0.9f);
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
