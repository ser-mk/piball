package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ser.pipi.piball.asserts.FlagList;
import ser.pipi.piball.asserts.FontList;

/**
 * Created by ser on 19.02.18.
 */

public class RenderSystem {

    final String TAG = this.getClass().getName();

    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;

    Texture field;
    BitmapFont fontGoal;

    final AllObjectsState allObjectsState;
    final LocalState localState;
    final FlagList flagList;

    public RenderSystem(AllObjectsState allObjectsState, LocalState localState) {

        this.allObjectsState = allObjectsState;
        this.localState = localState;
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        field = new Texture("field.jpg");

        this.flagList = new FlagList();

        fontGoal = new BitmapFont();
        fontGoal.setColor(Color.RED);
        fontGoal.getData().setScale(2);
    }

    public void update(){
        // Tells shapeRenderer to begin drawing filled shapes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        paddleSelf();
        paddleEnemy();
        ball();
        // Tells the shapeRenderer to finish rendering
        // We MUST do this every time.
        shapeRenderer.end();

        spriteBatch.begin();
        goalStatistic();
        print_status();
        showFlag();
        spriteBatch.end();
    }

    private void print_status(){
        final String statusPI = localState.statusPI;
        final int X_CENTER = Gdx.graphics.getWidth()/2;

        FontList.printTextCenter(spriteBatch,fontGoal,statusPI,
                X_CENTER,Gdx.graphics.getHeight()/2 + 100);

        final String statusNET = localState.statusNET;
        FontList.printTextCenter(spriteBatch,fontGoal,statusNET,
                X_CENTER,Gdx.graphics.getHeight()/2 - 100 - - fontGoal.getLineHeight()*2);

        final String statusPIEnemy = allObjectsState.statusPIEnemy;
        FontList.printTextCenter(spriteBatch,fontGoal,statusPIEnemy,
                X_CENTER,Gdx.graphics.getHeight()*2/3);

        final float frameRate = Gdx.graphics.getFramesPerSecond();
        //Gdx.app.log(TAG, frameRate + " fps");
        fontGoal.draw(spriteBatch,frameRate + " fps", 0,222);
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

    private void goalStatistic(){

        fontGoal.draw(spriteBatch,String.valueOf(allObjectsState.selfGoal),
                Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/3);
        fontGoal.draw(spriteBatch,String.valueOf(allObjectsState.enemyGoal),
                Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()*2/3);
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
