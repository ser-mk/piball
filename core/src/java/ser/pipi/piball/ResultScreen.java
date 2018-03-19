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
import ser.pipi.piball.asserts.NamesCountry;
import ser.pipi.piball.asserts.SoundsList;
import ser.pipi.piball.asserts.TextureList;

/**
 * Created by ser on 10.03.18.
 */

public class ResultScreen implements Screen {

    final String TAG = this.getClass().getName();

    private final PositionInterface positionInterface;
    private final Piball piball;

    public static class ResultGame{
        public int flagSelf = 3;
        public int flagEnemy = 4;
        public int goalsSelf = 0;
        public int goalsEnemy = 0;
    }

    final FlagList flagList;
    final SpriteBatch spriteBatch;
    final Texture resultFon;
    final ResultGame resultGame;
    final BitmapFont font;
    final Music fon;
    //final Label skin;


    public ResultScreen(Piball piball, ResultGame resultGame) {
        this.piball = piball;
        this.positionInterface = piball.getPositionInterface();
        this.spriteBatch = new SpriteBatch();
        this.flagList = new FlagList();
        resultFon =  new Texture(TextureList.RESULT_FON);
        this.resultGame = resultGame;
        font = FontList.resultFont();
        fon = SoundsList.musics.getMusic(SoundsList.musics.ole);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(resultFon, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        flag();
        showGoals();
        showTitle();
        showNamesShort();
        spriteBatch.end();
        checkEnd();
    }

    final int X_STEP_FLAG = 19;
    final int Y_FLAG = 100;
    final int FLAG_WIDTH = 250;
    final int FLAG_HEIGHT = 160;
    final int X_FLAG_CENTERED = X_STEP_FLAG + FLAG_WIDTH/2;

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

    final int Y_CENTERED_OFFSET_GOALS = 200;

    private void showGoals(){

        final int Y = Y_FLAG + FLAG_HEIGHT + Y_CENTERED_OFFSET_GOALS;

        final float prevScaleX = font.getScaleX();
        final float prevScaleY = font.getScaleY();
        font.getData().setScale(2.5f);

        FontList.printTextCenterFitWidth(spriteBatch, font, "" + resultGame.goalsSelf,
                X_FLAG_CENTERED , Y,FLAG_WIDTH);

        FontList.printTextCenterFitWidth(spriteBatch, font, "" + resultGame.goalsEnemy,
                Gdx.graphics.getWidth() - X_FLAG_CENTERED , Y,FLAG_WIDTH);

        font.getData().setScale(prevScaleX,prevScaleY);
    }

    final float Y_CENTERED_NAME = 650;

    private void showNamesShort(){
        final String nameSelf = NamesCountry.getNameCountry(resultGame.flagSelf);
        final String nameEnemy = NamesCountry.getNameCountry(resultGame.flagEnemy);

        FontList.printTextCenterFitWidth(spriteBatch, font, nameSelf,
                X_FLAG_CENTERED, Y_CENTERED_NAME, FLAG_WIDTH);

        FontList.printTextCenterFitWidth(spriteBatch, font, nameEnemy,
                Gdx.graphics.getWidth() - X_FLAG_CENTERED,
                Y_CENTERED_NAME, FLAG_WIDTH);
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

    public void checkEnd(){
        final PositionInterface.InputStatus piStatus = positionInterface.getState();
        if(piStatus == PositionInterface.InputStatus.BACKSPACE) {
            piball.startWelcome();
            return;
        }

        if(piStatus == PositionInterface.InputStatus.CLOSE_GAME ||
                piStatus == PositionInterface.InputStatus.CONNECTED_PROBLEM) {
            piball.exit();
            return;
        }
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
