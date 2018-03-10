package ser.pipi.piball.desktop;

import ser.pipi.piball.PositionInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by ser on 17.02.18.
 */

class PII_Stub implements PositionInterface, InputProcessor {

    final String TAG = this.getClass().getName();
    int state = PositionInterface.NORMAL_WORK;
    int position = 500;

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void update() {
        /*
        Gdx.app.log(TAG, "exit");
        Gdx.app.exit();
        return state;
        */
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.C){
            Gdx.app.log(TAG, "CLOSE_GAME");
            state = PositionInterface.CLOSE_GAME;
        } else if (keycode == Input.Keys.X){
            Gdx.app.log(TAG, "CONNECTED_PROBLEM");
            state = PositionInterface.CONNECTED_PROBLEM;
        } else if (keycode == Input.Keys.SPACE){
            Gdx.app.log(TAG, "POSITION_UNDEFINED");
            state = PositionInterface.POSITION_UNDEFINED;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Gdx.app.log(TAG, "touchDragged screenX " + screenX + " " + pointer);
        position = (screenX* PositionInterface.POSITION_MAX) / Gdx.graphics.getWidth();
        state = PositionInterface.NORMAL_WORK;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
