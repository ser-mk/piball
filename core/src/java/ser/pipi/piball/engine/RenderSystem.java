package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by ser on 19.02.18.
 */

public class RenderSystem {

    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;

    Texture field;
    BitmapFont fontGoal;

    final AllObjectsState allObjectsState;
    final LocalState localState;

    public RenderSystem(AllObjectsState allObjectsState, LocalState localState) {

        this.allObjectsState = allObjectsState;
        this.localState = localState;
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        field = new Texture("field.jpg");

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
        spriteBatch.end();
    }

    private void print_status(){
        final String statusPI = localState.statusPI;
        fontGoal.draw(spriteBatch,statusPI,
                Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/2);

        final String statusNET = localState.statusNET;
        fontGoal.draw(spriteBatch,statusNET,
                Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/2 - fontGoal.getLineHeight()*2);
        final String statusPIEnemy = allObjectsState.statusPIEnemy;
        fontGoal.draw(spriteBatch,statusPIEnemy,
                Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/2 + fontGoal.getLineHeight()*2);
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
}
