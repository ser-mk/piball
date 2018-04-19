package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import ser.pipi.piball.Piball;
import ser.pipi.piball.PositionInterface;
import ser.pipi.piball.ResultScreen;
import ser.pipi.piball.Settings;
import ser.pipi.piball.net.Network;

/**
 * Created by ser on 19.02.18.
 */

public class Arena implements Screen {

    private final String TAG = this.getClass().getName();
    final Piball piball;
    final Settings ss;
    final RenderSystem render;
    final SoundSystem soundSystem;
    final LocalController localController;
    final SyncSystem syncSystem;
    final PositionInterface positionInterface;
    final LocalState localState;
    final AllObjectsState allObjectsState;

    float wait = 0;

    public Arena(Piball piball, int flag) {
        final String TAG = this.getClass().getName();

        this.piball = piball;
        this.ss = piball.getSettings();
        this.allObjectsState = new AllObjectsState(ss);
        this.localState = new LocalState(ss, flag);

        localController = new LocalController(ss, localState, piball.getPositionInterface());
        render = new RenderSystem(ss, allObjectsState, localState);
        soundSystem = new SoundSystem(allObjectsState, localState);
        syncSystem = new SyncSystem(ss, allObjectsState, localState);
        this.positionInterface = piball.getPositionInterface();
    }

    @Override
    public void render(float delta) {
        localController.update(delta);
        syncSystem.update(delta);
        checkEventSystem(delta);
        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        render.update(delta);
        soundSystem.update();
    }

    final String CLEAR_STATUS = "";

    private void checkEventSystem(float delta) {

        final Network.ConnectionState netState = syncSystem.getNetState();
        if(netState != Network.ConnectionState.CONNECTED_PLAYER){
            markNetStatus(delta, getStringNetStatus(netState));
        } else {
            markNetStatus(delta, CLEAR_STATUS);
        }

        final PositionInterface.InputStatus piStatus = positionInterface.getState();
        if(piStatus == PositionInterface.InputStatus.NORMAL_WORK
                || piStatus == PositionInterface.InputStatus.POSITION_UNDEFINED) {
            markPiStatus(delta, CLEAR_STATUS);
        } else {
            markPiStatus(delta, PositionInterface.getStringErrorPiStatus(piStatus));
        }

        checkEnd(delta,netState,piStatus);
    }

    private void checkEnd(float delta,
                          Network.ConnectionState netState,
                          PositionInterface.InputStatus piStatus){
        boolean endForce = false;

        switch (netState){
            case DISCONNECTED_PLAYER: endForce = true; break;
        }

        switch (piStatus){
            case CLOSE_GAME: endForce = true; break;
            case CONNECTED_PROBLEM: endForce = true; break;
            case BACKSPACE:
                endForce = true;
                positionInterface.clearState();
                piball.startWelcome(); break;
        }

        if (endForce){
            wait += delta;
            if (wait > ss.wait_end_game){
                setResultGame();
            }
        } else {
            wait = 0;
        }
    }

    private void setResultGame(){
        ResultScreen.ResultGame rg = new ResultScreen.ResultGame();
        rg.goalsSelf = allObjectsState.selfGoal;
        rg.goalsEnemy = allObjectsState.enemyGoal;
        rg.flagEnemy = allObjectsState.flagEnemy;
        rg.flagSelf = localState.flag;
        piball.showResult(rg);
    }

    static private String getStringNetStatus(Network.ConnectionState netState){
        switch (netState){
            case WAIT_PLAYER: return "WAIT PLAYER";
            case DISCONNECTED_PLAYER: return "PLAYER DISCONNECTED";
            case NETWORK_EXCEPTION: return "NETWORK PROBLEMS";
            case COLLISION_PLAYER: return "MORE PLAYER FOR TOURNAMENT";
            default: return "UNDEFINED NETWORK ERROR";
        }
    }

    private void markPiStatus(float delta, String status) {
        localState.statusPI = status;
    }

    private void markNetStatus(float delta, String status){
        localState.statusNET = status;
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {    }

    @Override
    public void pause() {}

    @Override
    public void resume() {    }

    // todo: release
    @Override
    public void hide() {
        Gdx.app.log(TAG, " - hide");
        soundSystem.release();
        syncSystem.dispose();
    }

    @Override
    public void dispose() {}
}