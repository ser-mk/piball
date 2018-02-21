package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ser.pipi.piball.Settings;

/**
 * Created by ser on 19.02.18.
 */

public class RenderSystem {

    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;

    Texture field;
    BitmapFont fontGoal;

    StateStore stateStore;

    public RenderSystem(StateStore stateStore) {

        this.stateStore = stateStore;
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        field = new Texture("field.jpg");

        fontGoal = new BitmapFont();
        fontGoal.setColor(Color.RED);
        fontGoal.getData().setScale(2);
    }

    public void update(){
/*
        spriteBatch.begin();
        spriteBatch.draw(field, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();
*/
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
        spriteBatch.end();
    }

    private void paddleSelf(){
        shapeRenderer.setColor(255 / 255.0f, 109 / 255.0f, 120 / 255.0f, 1);

        // Draws the rectangle from myWorld (Using ShapeType.Filled)
        shapeRenderer.rect(this.stateStore.paddleSelf.getX(), this.stateStore.paddleSelf.getY(),
                this.stateStore.paddleSelf.getWidth(), this.stateStore.paddleSelf.getHeight());
    }

    private void paddleEnemy(){
        shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1);

        // Draws the rectangle from myWorld (Using ShapeType.Filled)
        shapeRenderer.rect(this.stateStore.paddleEnemy.getX(), this.stateStore.paddleEnemy.getY(),
                this.stateStore.paddleEnemy.getWidth(), this.stateStore.paddleEnemy.getHeight());

    }

    private void ball(){
        shapeRenderer.setColor(87 / 255.0f, 200 / 255.0f, 255 / 255.0f, 1);

        // Draws the rectangle from myWorld (Using ShapeType.Filled)
        shapeRenderer.circle(this.stateStore.ball.x, this.stateStore.ball.y,
                this.stateStore.ball.radius);
    }

    private void goalStatistic(){

        fontGoal.draw(spriteBatch,String.valueOf(stateStore.selfGoal),
                Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/3);
        fontGoal.draw(spriteBatch,String.valueOf(stateStore.enemyGoal),
                Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()*2/3);
    }
}
