package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ser.pipi.piball.asserts.FlagList;
import ser.pipi.piball.asserts.FontList;
import ser.pipi.piball.asserts.TextureList;

/**
 * Created by ser on 19.02.18.
 */

public class RenderSystem {

    final String TAG = this.getClass().getName();

    final ShapeRenderer shapeRenderer;
    final SpriteBatch spriteBatch;

    final Texture arena;
    final Texture ball;
    final Texture paddle_self;
    final Texture paddle_enemy;
    final BitmapFont fontGoal;

    final AllObjectsState allObjectsState;
    final LocalState localState;
    final FlagList flagList;

    public RenderSystem(AllObjectsState allObjectsState, LocalState localState) {

        this.allObjectsState = allObjectsState;
        this.localState = localState;
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        arena = TextureList.loadTexture(TextureList.ARENA);
        ball = TextureList.loadTexture(TextureList.BALL);
        paddle_self = TextureList.loadTexture(TextureList.PADDLE_SELF);
        paddle_enemy = TextureList.loadTexture(TextureList.PADDLE_ENEMY);

        this.flagList = new FlagList();

        fontGoal = new BitmapFont();
        fontGoal.setColor(Color.RED);
        fontGoal.getData().setScale(2);
    }

    public void update(){
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
        sprite_ball();
        spritePaddle();
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

    private void sprite_ball(){
        final float X1 = allObjectsState.ball.x - allObjectsState.ball.radius;
        final float Y1 = allObjectsState.ball.y - allObjectsState.ball.radius;
        final float D = 2*allObjectsState.ball.radius;
        spriteBatch.draw(ball, X1, Y1, D, D);
    }

    private void goalStatistic(){

        fontGoal.draw(spriteBatch,String.valueOf(allObjectsState.selfGoal),
                Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/3);
        fontGoal.draw(spriteBatch,String.valueOf(allObjectsState.enemyGoal),
                Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()*2/3);
    }

    private void spritePaddle(){

        spriteBatch.draw(paddle_self,this.localState.paddleSelf.getX(), this.localState.paddleSelf.getY(),
                this.localState.paddleSelf.getWidth(), this.localState.paddleSelf.getHeight());

        spriteBatch.draw(paddle_enemy,this.allObjectsState.paddleEnemy.getX(), this.allObjectsState.paddleEnemy.getY(),
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
