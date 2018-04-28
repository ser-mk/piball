package ser.pipi.piball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import sermk.pipi.pilib.PiBind;

/**
 * Created by ser on 17.02.18.
 */

class PII_Stub extends PositionInterface {

    final String TAG = this.getClass().getName();
    //InputStatus status = InputStatus.NORMAL_WORK;
    int position = 500;
    PiBind piBind;

    public PII_Stub(PiBind piBind) {
        this.piBind = piBind;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void update() {
        final int pos = piBind.getPosition();
        //Gdx.app.log(TAG, "position " + position);

        if(status == InputStatus.BACKSPACE)
            return;

        if(pos > PiBind.POSITION_UNDEFINED) {
            position = pos;
            status = InputStatus.NORMAL_WORK;
            return;
        }

        switch (pos){
            case PiBind.POSITION_UNDEFINED:
                status = InputStatus.POSITION_UNDEFINED;
                break;
            case PiBind.CLOSE_GAME:
                status = InputStatus.CLOSE_GAME;
                break;
            case PiBind.CONNECTED_PROBLEM:
                status = InputStatus.CONNECTED_PROBLEM;
                break;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Keys.BACK){
            // Respond to the back button click here
            Gdx.app.log(TAG, "back press keycode " + keycode);
            status = InputStatus.BACKSPACE;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        Gdx.app.log(TAG, "keycode " + keycode);
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
        position = (screenX* PositionInterface.POSITION_MAX) / Gdx.graphics.getWidth();
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
