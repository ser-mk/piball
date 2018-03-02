package ser.pipi.piball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import ser.pipi.piball.engine.LocalController;
import ser.pipi.piball.engine.LocalState;
import ser.pipi.piball.engine.RenderSystem;
import ser.pipi.piball.engine.SoundSystem;
import ser.pipi.piball.engine.StateController;
import ser.pipi.piball.engine.StateStore;

/**
 * Created by ser on 19.02.18.
 */

public class Arena implements Screen {

    final RenderSystem render;
    final SoundSystem soundSystem;
    final StateController stateController;
    final LocalController localController;

    public Arena(GameInterface gameInterface) {

        SettingsStruct ss = new SettingsStruct();
        StateStore stateStore = new StateStore(ss);
        LocalState localState = new LocalState(ss);
        localController = new LocalController(localState, gameInterface);
        stateController = new StateController(ss, stateStore, gameInterface);
        render = new RenderSystem(stateStore, localState);
        soundSystem = new SoundSystem(stateStore, localState);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        localController.update(delta);
        stateController.update(delta);
        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        render.update();
        soundSystem.update();
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

    @Override
    public void dispose() {

    }
}
