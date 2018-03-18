package ser.pipi.piball.desktop;

import ser.pipi.piball.PositionInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by ser on 17.02.18.
 */

class PII_Stub extends PositionInterface implements InputProcessor {

    final String TAG = this.getClass().getName();
    int position = 500;

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void update() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.C){
            Gdx.app.log(TAG, "CLOSE_GAME");
            status = InputStatus.CLOSE_GAME;
        } else if (keycode == Input.Keys.X){
            Gdx.app.log(TAG, "CONNECTED_PROBLEM");
            status = InputStatus.CONNECTED_PROBLEM;
        } else if (keycode == Input.Keys.SPACE){
            Gdx.app.log(TAG, "POSITION_UNDEFINED");
            status = InputStatus.POSITION_UNDEFINED;
        }
        else if (keycode == Input.Keys.BACKSPACE){
            Gdx.app.log(TAG, "BACKSPACE");
            status = InputStatus.BACKSPACE;
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
        status = InputStatus.NORMAL_WORK;
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
