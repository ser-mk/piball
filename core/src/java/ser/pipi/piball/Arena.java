package ser.pipi.piball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import ser.pipi.piball.engine.AllObjectsState;
import ser.pipi.piball.engine.LocalController;
import ser.pipi.piball.engine.LocalState;
import ser.pipi.piball.engine.RenderSystem;
import ser.pipi.piball.engine.SoundSystem;
import ser.pipi.piball.engine.SyncSystem;

/**
 * Created by ser on 19.02.18.
 */

public class Arena implements Screen {

    final RenderSystem render;
    final SoundSystem soundSystem;
    final LocalController localController;
    final SyncSystem syncSystem;

    public Arena(Piball piball) {

        SettingsStruct ss = piball.getSettingsStruct();
        AllObjectsState allObjectsState = new AllObjectsState(ss);
        LocalState localState = new LocalState(ss);
        localController = new LocalController(localState, piball.getGameInterface());
        render = new RenderSystem(allObjectsState, localState);
        soundSystem = new SoundSystem(allObjectsState, localState);
        syncSystem = new SyncSystem(ss, allObjectsState, localState);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        localController.update(delta);
        syncSystem.update(delta);
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
