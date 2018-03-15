package ser.pipi.piball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ser.pipi.piball.asserts.FlagList;
import ser.pipi.piball.asserts.FontList;
import ser.pipi.piball.asserts.SoundsList;
import ser.pipi.piball.asserts.TextureList;

/**
 * Created by ser on 10.03.18.
 */

public class ResultScreen implements Screen {

    final String TAG = this.getClass().getName();

    public static class ResultGame{
        public int flagSelf = 0;
        public int flagEnemy = 0;
        public int goalsSelf = 0;
        public int goalsEnemy = 0;
    }

    final FlagList flagList;
    final SpriteBatch spriteBatch;
    final Texture logo;
    final ResultGame resultGame;
    final BitmapFont font;
    final Music fon;


    public ResultScreen(ResultGame resultGame) {
        this.spriteBatch = new SpriteBatch();
        this.flagList = new FlagList();
        logo =  new Texture(TextureList.FINAL_LOGO);
        this.resultGame = resultGame;
        font = FontList.resultFont();
        fon = SoundsList.musics.getMusic(SoundsList.musics.common_fon);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(logo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        flag();
        showGoals();
        showTitle();
        spriteBatch.end();
    }

    final int X_STEP_FLAG = 15;
    final int Y_FLAG = 90;
    final int FLAG_WIDTH = 250;
    final int FLAG_HEIGHT = 160;

    private void flag(){

        if(!flagList.consistNumberFlag(resultGame.flagSelf)
            || !flagList.consistNumberFlag(resultGame.flagEnemy))
            return;

        spriteBatch.draw(flagList.getFlag(resultGame.flagSelf),
                X_STEP_FLAG, Y_FLAG, FLAG_WIDTH, FLAG_HEIGHT);

        final float x_flag = Gdx.graphics.getWidth() - X_STEP_FLAG
                - flagList.getFlag(resultGame.flagEnemy).getWidth();

        spriteBatch.draw(flagList.getFlag(resultGame.flagEnemy),
                x_flag, Y_FLAG, FLAG_WIDTH, FLAG_HEIGHT);
    }

    final int X_STEP_GOALS = 50;
    final int Y_STEP_GOALS = 100;

    private void showGoals(){

        final int XSelf = X_STEP_FLAG + FLAG_WIDTH/2 - X_STEP_GOALS;
        final int Y = Y_FLAG + FLAG_HEIGHT + Y_STEP_GOALS;

        font.draw(spriteBatch,"" + resultGame.goalsSelf, XSelf, Y);

        final GlyphLayout layout = new GlyphLayout(font, "" + resultGame.goalsEnemy);

        final float XEnemy = Gdx.graphics.getWidth()
                - X_STEP_FLAG - FLAG_WIDTH/2 + X_STEP_GOALS - layout.width;

        font.draw(spriteBatch,"" + resultGame.goalsEnemy, XEnemy, Y);
    }

    final String DEAD_HEAT = "Dead heat!";
    final String MISSED = "Your Missed!";
    final String WIN = "Your Win!";
    final int Y_STEP_TITLE = 75;

    private void showTitle(){
        String text = "";

        if (resultGame.goalsEnemy == resultGame.goalsSelf){
            text = DEAD_HEAT;
        } else if ( resultGame.goalsEnemy > resultGame.goalsSelf){
            text = MISSED;
        } else if (resultGame.goalsEnemy < resultGame.goalsSelf){
            text = WIN;
        }

        final GlyphLayout layout = new GlyphLayout(font, text);


        final float fontX = (Gdx.graphics.getWidth() - layout.width)/2;
        final float fontY = Gdx.graphics.getHeight() - Y_STEP_TITLE;

        font.draw(spriteBatch, layout, fontX, fontY);
    }

    @Override
    public void show() {
        fon.play();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /*
     * todo release texture and music
     */
    @Override
    public void dispose() {

    }
}
