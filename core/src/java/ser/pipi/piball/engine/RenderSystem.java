package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ser.pipi.piball.PositionInterface;
import ser.pipi.piball.Settings;
import ser.pipi.piball.asserts.FlagList;
import ser.pipi.piball.asserts.FontList;
import ser.pipi.piball.asserts.NamesCountry;
import ser.pipi.piball.asserts.TextureList;

/**
 * Created by ser on 19.02.18.
 */

public class RenderSystem {

    final String TAG = this.getClass().getName();

    final ShapeRenderer shapeRenderer;
    final SpriteBatch spriteBatch;

    final Texture arena;
    final Texture score;
    final Texture ball;
    final Texture paddle_self;
    final Texture paddle_self_undefined;
    final Texture paddle_enemy;
    final Texture paddle_enemy_undefined;
    final BitmapFont font;

    final AllObjectsState allObjectsState;
    final LocalState localState;
    final FlagList flagList;

    final Sprite spriteBall;
    final Settings ss;

    public RenderSystem(Settings ss, AllObjectsState allObjectsState, LocalState localState) {

        this.allObjectsState = allObjectsState;
        this.localState = localState;
        this.ss = ss;
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        arena = TextureList.loadTexture(TextureList.ARENA);
        score = TextureList.loadTexture(TextureList.SCORE);
        ball = TextureList.loadTexture(TextureList.BALL);
        paddle_self = TextureList.loadTexture(TextureList.PADDLE_SELF);
        paddle_enemy = TextureList.loadTexture(TextureList.PADDLE_ENEMY);
        paddle_self_undefined = TextureList.loadTexture(TextureList.PADDLE_SELF_UNDEFINED);
        paddle_enemy_undefined = TextureList.loadTexture(TextureList.PADDLE_ENEMY_UNDEFINED);

        this.flagList = new FlagList();

        font = FontList.arenaFont();

        spriteBall = new Sprite(ball);
        final float scale = 2*ss.RADIUS_BALL /spriteBall.getHeight();
        spriteBall.setScale(scale);
    }

    public void update(float delta){
        // Tells shapeRenderer to begin drawing filled shapes
        /*
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        paddleSelf();
        paddleEnemy();
        ball();

        // Tells the shapeRenderer to finish rendering
        // We MUST do this every time.
        shapeRenderer.end();
*/
        spriteBatch.begin();
        spriteBatch.draw(arena, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite_ball(delta);
        spritePaddle(delta);
        goalStatistic();
        print_status();
        showFlag();
        spriteBatch.end();
    }

    private void print_status(){
        final String statusPI = localState.statusPI;
        final int X_CENTER = Gdx.graphics.getWidth()/2;

        final float preScale = font.getData().scaleX;

        font.getData().setScale(0.3f*3);

        FontList.printTextCenter(spriteBatch, font,statusPI,
                X_CENTER,Gdx.graphics.getHeight()/2 - 100 - font.getLineHeight()*2);

        final String statusNET = localState.statusNET;
        FontList.printTextCenter(spriteBatch, font,statusNET,
                X_CENTER,Gdx.graphics.getHeight()*3/4 );

        final String statusPIEnemy = allObjectsState.statusPIEnemy.isEmpty()
                ? "" : "player enemy: " + allObjectsState.statusPIEnemy;
        FontList.printTextCenter(spriteBatch, font, statusPIEnemy,
                X_CENTER,Gdx.graphics.getHeight()/2 + 100);

        font.getData().setScale(preScale);

        if(ss.SHOW_DEBUG) {
            final float frameRate = Gdx.graphics.getFramesPerSecond();
            font.draw(spriteBatch, frameRate + " fps", 0, 222);

            font.draw(spriteBatch,
                    SyncSystem.getPassMessage() + " pm", 0, 255);
            font.draw(spriteBatch,
                    SyncSystem.getTimePass() + " ms", 0, 288);
        }
    }

    private void paddleSelf(){
        shapeRenderer.setColor(255 / 255.0f, 109 / 255.0f, 120 / 255.0f, 1);

        // Draws the rectangle from myWorld (Using ShapeType.Filled)

        shapeRenderer.rect(this.localState.paddleSelf.getX(), this.localState.paddleSelf.getY(),
                this.localState.paddleSelf.getWidth(), this.localState.paddleSelf.getHeight());

    }

    private void paddleEnemy(){
        shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1);

        // Draws the rectangle from myWorld (Using ShapeType.Filled)
        shapeRenderer.rect(this.allObjectsState.paddleEnemy.getX(), this.allObjectsState.paddleEnemy.getY(),
                this.allObjectsState.paddleEnemy.getWidth(), this.allObjectsState.paddleEnemy.getHeight());

    }

    private void ball(){
        shapeRenderer.setColor(87 / 255.0f, 200 / 255.0f, 255 / 255.0f, 1);

        // Draws the rectangle from myWorld (Using ShapeType.Filled)
        shapeRenderer.circle(this.allObjectsState.ball.x, this.allObjectsState.ball.y,
                this.allObjectsState.ball.radius);
    }

    private void sprite_ball(float delta){
        spriteBall.setCenter(allObjectsState.ball.x, allObjectsState.ball.y);

        spriteBall.rotate(delta*allObjectsState.ballVelocity.len()*ss.RATE_ANGLE_ROTATE_RELATE_LINIAR_VELOCITY);

        spriteBall.draw(spriteBatch);
    }

    final int GOALS_TITLE_OFFSET_Y = 24;
    final int GOALS_TITLE_GAP_Y = 13;
    final int GOALS_TITLE_X = 50;
    final int EDGING_X_SCORE = 10;
    final int EDGING_Y_SCORE = 5;
    final float ALPHA_SCORE = 0.65f;

    private void goalStatistic(){

        final String nameSelf = NamesCountry.getNameShortCountry(localState.flag);
        final String nameEnemy = NamesCountry.getNameShortCountry(allObjectsState.flagEnemy);

        String title = nameSelf + " " + String.valueOf(allObjectsState.selfGoal)
                + "-" +
                 String.valueOf(allObjectsState.enemyGoal) + " " + nameEnemy;
        if(!ss.IS_SERVER){
            title = String.valueOf(allObjectsState.enemyGoal) + " " + nameEnemy
                    + "-" +
                    nameSelf + " " + String.valueOf(allObjectsState.selfGoal)
                    ;
        }

        final float Y = Gdx.graphics.getHeight() - GOALS_TITLE_GAP_Y;

        final GlyphLayout layout = new GlyphLayout(font, title);

        final Color prevColorScore = spriteBatch.getColor();
        final Color scoreColor = prevColorScore.cpy();
        scoreColor.a = ALPHA_SCORE;
        spriteBatch.setColor(scoreColor);
        spriteBatch.draw(score,GOALS_TITLE_X - EDGING_X_SCORE,
                Y - layout.height - EDGING_Y_SCORE,
                layout.width + 2*EDGING_X_SCORE, layout.height + 2*EDGING_Y_SCORE);
        spriteBatch.setColor(prevColorScore);

        final Color prevColorScoreFont = font.getColor();
        final Color scoreColorFont = prevColorScoreFont.cpy();
        scoreColorFont.a = ALPHA_SCORE;
        font.setColor(scoreColorFont);
        font.draw(spriteBatch, layout, GOALS_TITLE_X, Y);
        font.setColor(prevColorScoreFont);
    }

    private Float wait_undef_self = 0f;
    private float wait_undef_enemy= 0;

    private void spritePaddle(float delta){

        Texture self = paddle_self;

        if(PositionInterface.InputStatus.NORMAL_WORK.
                equals(localState.inputStatus)) {
            wait_undef_self = 0f;
        } else {
            wait_undef_self += delta;
        }

        if(wait_undef_self > ss.TIMEOUT_SEC_DEADTIME_PADDLE_UNDEF){
            self = paddle_self_undefined;
        }

        spriteBatch.draw(self,this.localState.paddleSelf.getX(), this.localState.paddleSelf.getY(),
                this.localState.paddleSelf.getWidth(), this.localState.paddleSelf.getHeight());

        Texture enemy = paddle_enemy;

        if(PositionInterface.InputStatus.NORMAL_WORK.
                equals(allObjectsState.inputStatusEnemy)) {
            wait_undef_enemy = 0f;
        } else {
            wait_undef_enemy += delta;
        }

        if(wait_undef_enemy > ss.TIMEOUT_SEC_DEADTIME_PADDLE_UNDEF){
            enemy = paddle_enemy_undefined;
        }

        spriteBatch.draw(enemy,this.allObjectsState.paddleEnemy.getX(), this.allObjectsState.paddleEnemy.getY(),
                this.allObjectsState.paddleEnemy.getWidth(), this.allObjectsState.paddleEnemy.getHeight());
    }

    final float X_FLAG = 100;
    final float Y_FLAG = 50;
    final float FLAG_WIDTH = 600;
    final float FLAG_HEIGHT = 350;

    private void showFlag(){

        final Color prevColor = spriteBatch.getColor();

        final Color flagColor = prevColor.cpy();
        flagColor.a = 0.3f;

        spriteBatch.setColor(flagColor);

        int numberFlag = allObjectsState.flagEnemy;
        if(flagList.consistNumberFlag(numberFlag)){
            final float Y_FLAG_ENEMY = Gdx.graphics.getHeight() - Y_FLAG - FLAG_HEIGHT;
            spriteBatch.draw(flagList.getFlag(numberFlag),
                    X_FLAG, Y_FLAG_ENEMY, FLAG_WIDTH, FLAG_HEIGHT);
        }

        numberFlag = localState.flag;
        if(flagList.consistNumberFlag(numberFlag)){
            spriteBatch.draw(flagList.getFlag(numberFlag),
                    X_FLAG, Y_FLAG, FLAG_WIDTH, FLAG_HEIGHT);
        }

        spriteBatch.setColor(prevColor);
    }
}
