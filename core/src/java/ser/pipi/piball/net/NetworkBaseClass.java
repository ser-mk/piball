package ser.pipi.piball.net;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;

import ser.pipi.piball.net.Network.ConnectionState;

/**
 * Created by ser on 04.03.18.
 */

public abstract class NetworkBaseClass extends Listener implements NetworkInterface {

    final String TAG = this.getClass().getName();
    final GameNetImpl gameNet;

    public NetworkBaseClass(GameNetImpl gameNet) {
        this.state = ConnectionState.WAIT_PLAYER;
        this.gameNet = gameNet;
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
    public void updateStatus(float delta) {
        gameNet.setState(state);
    }

    @Override
    public void received(Connection connection, Object object) {
        gameNet.recieve(connection,object);
    }

    @Override
    public void connected(Connection connection) {
        Gdx.app.log(TAG, "++connecting! " + connection.toString());
        if (state == ConnectionState.CONNECTED_PLAYER){
            state = ConnectionState.COLLISION_PLAYER;
        } else {
            state = ConnectionState.CONNECTED_PLAYER;
        }
    }

    @Override
    public void disconnected(Connection connection) {
        state = ConnectionState.DISCONNECTED_PLAYER;
        Gdx.app.log(TAG, "--disconnecting! " + connection.toString());
    }
}
