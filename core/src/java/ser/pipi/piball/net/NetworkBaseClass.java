package ser.pipi.piball.net;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;

import ser.pipi.piball.engine.LocalState;
import ser.pipi.piball.net.Network.ConnectionState;

/**
 * Created by ser on 04.03.18.
 */

public abstract class NetworkBaseClass extends Listener implements NetworkInterface {

    final String TAG = this.getClass().getName();
    final LocalState localState;

    public NetworkBaseClass(LocalState localState) {
        this.state = ConnectionState.WAIT_PLAYER;
        this.localState = localState;
    }

    protected void init(EndPoint endPoint){
        Network.register(endPoint);
        endPoint.addListener(this);
    }

    ConnectionState state = ConnectionState.WAIT_PLAYER;

    @Override
    public void prepare(float delta) {

    }

    @Override
    public void setStatus(float delta) {
        localState.statusNET = getStatus();
    }

    public String getStatus(){
        if(state == ConnectionState.CONNECTED_PLAYER)
            return "";

        return state.toString();
    }

    @Override
    public void connected(Connection connection) {
        state = ConnectionState.CONNECTED_PLAYER;
        Gdx.app.log(TAG, "++connecting! " + connection.toString());
    }

    @Override
    public void disconnected(Connection connection) {
        state = ConnectionState.DISCONECTED_PLAYER;
        Gdx.app.log(TAG, "--disconnecting! " + connection.toString());
    }
}