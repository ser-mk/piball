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
import ser.pipi.piball.net.Network;

/**
 * Created by ser on 19.02.18.
 */

public class Arena implements Screen {

    final RenderSystem render;
    final SoundSystem soundSystem;
    final LocalController localController;
    final SyncSystem syncSystem;
    final PositionInspector positionInspector;
    final LocalState localState;
    final AllObjectsState allObjectsState;

    public Arena(Piball piball, int flag) {

        SettingsStruct ss = piball.getSettingsStruct();
        this.allObjectsState = new AllObjectsState(ss);
        this.localState = new LocalState(ss, flag);

        localController = new LocalController(ss, localState, piball.getPositionInterface());
        render = new RenderSystem(allObjectsState, localState);
        soundSystem = new SoundSystem(allObjectsState, localState);
        syncSystem = new SyncSystem(ss, allObjectsState, localState);
        this.positionInspector = new PositionInspector(piball.getPositionInterface());
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

    private void checkEventSystem(float delta) {
        final Network.ConnectionState netState = syncSystem.getNetState();
        if(netState == Network.ConnectionState.CONNECTED_PLAYER){
            printNetStatus(delta, getStringNetStatus(netState));
        }

        final PositionInspector.PiStatus piStatus = positionInspector.piStatus();
        if(piStatus == PositionInspector.PiStatus.NORMAL_WORK || piStatus == PositionInspector.PiStatus.POSITION_UNDEFINED)
            return;


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

    static private String getStringPiStatus(PositionInspector.PiStatus piState){
        switch (piState){
            case CLOSE_GAME: return "GAME END";
            case CONNECTED_PROBLEM: return "CAN'T FIND YOUR FLOW";
            case KEY_BACK: return "RETURN TO BACK";
            default: return "UNDEFINED STATUS";
        }
    }

    private void printPiStatus(float delta, String status) {
    }

    private void printNetStatus(float delta, String status){

    }

    @Override
    public void show() {
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
