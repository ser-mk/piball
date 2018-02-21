package ser.pipi.piball.desktop;

import ser.pipi.piball.GameInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by ser on 17.02.18.
 */

class PII_Stub implements GameInterface, InputProcessor {

    final String TAG = this.getClass().getName();
    int position = 0;

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void release() {
        Gdx.app.log(TAG, "exit");
        Gdx.app.exit();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.C){
            Gdx.app.log(TAG, "CLOSE_GAME");
            position = GameInterface.CLOSE_GAME;
        } else if (keycode == Input.Keys.X){
            Gdx.app.log(TAG, "CONNECTED_PROBLEM");
            position = GameInterface.CONNECTED_PROBLEM;
        } else if (keycode == Input.Keys.SPACE){
            Gdx.app.log(TAG, "POSITION_UNDEFINED");
            position = GameInterface.POSITION_UNDEFINED;
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
        position = (screenX*GameInterface.POSITION_MAX) / Gdx.graphics.getWidth();
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
