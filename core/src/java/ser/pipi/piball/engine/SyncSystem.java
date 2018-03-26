package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryonet.Connection;

import java.lang.reflect.Field;

import ser.pipi.piball.SettingsStruct;
import ser.pipi.piball.net.GameClient;
import ser.pipi.piball.net.GameNetImpl;
import ser.pipi.piball.net.GameServer;
import ser.pipi.piball.net.Network;
import ser.pipi.piball.net.NetworkInterface;

/**
 * Created by ser on 06.03.18.
 */

public class SyncSystem implements GameNetImpl, Disposable {

    final String TAG = this.getClass().getName();

    final SettingsStruct ss;
    final StateController stateController;
    final NetworkInterface networkInterface;
    final LocalState localState;
    final AllObjectsState allObjectsState;
    Network.ConnectionState netState;
    private float sendTimeout = 0;

    public SyncSystem(SettingsStruct ss, AllObjectsState allObjectsState, LocalState localState) {
        this.ss = ss;
        this.localState = localState;
        this.allObjectsState = allObjectsState;
        netState = Network.ConnectionState.WAIT_PLAYER;
        if (this.ss.server){
            networkInterface = new GameServer(ss, this);
        } else {
            networkInterface = new GameClient(ss, this);
        }

        stateController = new StateController(ss, allObjectsState, localState);
    }

    public void update(float delta){

        networkInterface.updateStatus(delta);

        if (networkInterface.waitPlayer(delta))
            return;

        if (this.ss.server) {
            stateController.update(delta);
        }

        final Object state = this.ss.server ? allObjectsState : localState;
        if (sendTimeout >= this.ss.sendPeriod){
            networkInterface.sendState(state);
            sendTimeout = 0;
        } else {
            sendTimeout += delta;
        }

    }

    @Override
    public void recieve(Connection connection, Object object) {
        if (object instanceof AllObjectsState) {
            inversServerState((AllObjectsState)object);
            cloneStateStore((AllObjectsState)object);
        }

        if (object instanceof LocalState) {
            final LocalState ls =  (LocalState) object;
            //Gdx.app.log(TAG, "enemy : " + ls.paddleSelf.getX());
            inverseLocalPaddle(ls);
            allObjectsState.paddleEnemy.setX(ls.paddleSelf.getX());
            allObjectsState.statusPIEnemy = ls.statusPI;
            allObjectsState.flagEnemy = ls.flag;
            allObjectsState.inputStatusEnemy = ls.inputStatus;
        }
    }

    private void inversServerState(AllObjectsState server){
        final float width =  Gdx.graphics.getWidth();
        final float heigth = Gdx.graphics.getHeight();
        server.paddleEnemy.x = width - server.paddleSelf.x - server.paddleSelf.width;
        server.ball.x = width -  server.ball.x;
        server.ball.y = heigth -  server.ball.y;
        server.statusPIEnemy = server.statusPISelf;
        server.flagEnemy = server.flagSelf;
        final int goal = server.enemyGoal;
        server.enemyGoal = server.selfGoal;
        server.selfGoal = goal;
        server.inputStatusEnemy = server.inputStatusSelf;
    }

    private void inverseLocalPaddle(LocalState ls){
        final float width =  Gdx.graphics.getWidth();
        ls.paddleSelf.x = width - ls.paddleSelf.x - ls.paddleSelf.width;
    }

    private boolean cloneStateStore(AllObjectsState obj){
        try{
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                field.set(allObjectsState, field.get(obj));
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void setState(Network.ConnectionState state) {
        netState = state;
    }

    public Network.ConnectionState getNetState(){ return netState; }

    @Override
    public void dispose() {
        networkInterface.release();
    }
}
