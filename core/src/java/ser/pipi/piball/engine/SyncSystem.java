package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;

import java.lang.reflect.Field;

import ser.pipi.piball.SettingsStruct;
import ser.pipi.piball.net.GameClient;
import ser.pipi.piball.net.GameNetImpl;
import ser.pipi.piball.net.GameServer;
import ser.pipi.piball.net.NetworkInterface;

/**
 * Created by ser on 06.03.18.
 */

public class SyncSystem implements GameNetImpl {

    final String TAG = this.getClass().getName();

    final StateController stateController;
    final NetworkInterface networkInterface;
    final LocalState localState;
    final AllObjectsState allObjectsState;
    final boolean isServer;

    public SyncSystem(SettingsStruct ss, AllObjectsState allObjectsState, LocalState localState) {
        isServer = ss.server;
        this.localState = localState;
        this.allObjectsState = allObjectsState;
        if (isServer){
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

        if (isServer) {
            stateController.update(delta);
            networkInterface.sendState(allObjectsState);
        } else {
            networkInterface.sendState(localState);
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
    public void setStatus(String status) {
        localState.statusNET = status;
    }
}
