package ser.pipi.piball.net;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

import ser.pipi.piball.SettingsStruct;
import ser.pipi.piball.engine.LocalState;

import ser.pipi.piball.net.Network.ConnectionState;

/**
 * Created by ser on 02.03.18.
 */

public class GameServer extends NetworkBaseClass {

    final String TAG = this.getClass().getName();

    Server broadcastServer;
    Server gameServer;

    public GameServer(SettingsStruct ss, LocalState localState) {
        super(localState);
        final int bankPort = ss.bankPort;

        try {
            broadcastServer = new Server();
            broadcastServer.start();
            broadcastServer.bind(0, Network.portBrodcast(bankPort));

            gameServer = new Server();
            super.init(gameServer);
            gameServer.start();
            gameServer.bind(Network.portTCP(bankPort), Network.portUDP(bankPort));

            state = ConnectionState.WAIT_PLAYER;

        } catch (IOException e) {
            e.printStackTrace();
            state = ConnectionState.NETWORK_EXCEPTION;
        }

    }

    ConnectionState state = ConnectionState.WAIT_PLAYER;

    @Override
    public void received(Connection connection, Object object) {
        Gdx.app.log(TAG, "received: " + object.toString());
    }

    @Override
    public boolean noWaitPlayer(float delta) {
        final int qtyConnection = gameServer.getConnections().length;
        if(qtyConnection == 1){
            state = ConnectionState.CONNECTED_PLAYER;
            return true;
        }

        return false;
    }
}
