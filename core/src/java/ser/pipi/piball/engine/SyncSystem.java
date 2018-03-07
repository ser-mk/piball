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
    final StateStore stateStore;
    final boolean isServer;

    public SyncSystem(SettingsStruct ss, StateStore stateStore, LocalState localState) {
        isServer = ss.server;
        this.localState = localState;
        this.stateStore= stateStore;
        if (isServer){
            networkInterface = new GameServer(ss, this);
        } else {
            networkInterface = new GameClient(ss, this);
        }

        stateController = new StateController(ss, stateStore, localState);
    }

    public void update(float delta){

        networkInterface.updateStatus(delta);

        if (networkInterface.waitPlayer(delta))
            return;

        if (isServer) {
            stateController.update(delta);
            networkInterface.sendState(stateStore);
        } else {
            networkInterface.sendState(localState);
        }

    }

    @Override
    public void recieve(Connection connection, Object object) {
        if (object instanceof StateStore) {
            cloneStateStore((StateStore)object);
        }

        if (object instanceof LocalState) {
            //cloneStateStore((StateStore)object);
            Gdx.app.log(TAG, "recieve : " + ((LocalState) object).paddleSelf);
        }
    }

    private boolean cloneStateStore(StateStore obj){
        try{
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                field.set(stateStore, field.get(obj));
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
