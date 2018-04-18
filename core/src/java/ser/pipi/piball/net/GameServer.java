package ser.pipi.piball.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

import ser.pipi.piball.SettingsStruct;

import ser.pipi.piball.net.Network.ConnectionState;

/**
 * Created by ser on 02.03.18.
 */

public class GameServer extends NetworkBaseClass {

    final String TAG = this.getClass().getName();

    final SettingsStruct ss;
    Server broadcastServer;
    Server gameServer;

    public GameServer(SettingsStruct ss, GameNetImpl gameNet) {
        super(gameNet);
        final int bankPort = ss.bankPort;
        this.ss = ss;

        try {
            broadcastServer = new Server();
            broadcastServer.start();
            broadcastServer.bind(0, Network.portBrodcast(bankPort));

            gameServer = new Server();
            super.init(gameServer);
            gameServer.start();
            gameServer.bind(Network.portTCP(bankPort), Network.portUDP(bankPort));

            state = ConnectionState.WAIT_PLAYER;
        } catch (Exception e) {
            e.printStackTrace();
            state = ConnectionState.NETWORK_EXCEPTION;
        }

    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
        //connection.close();
        gameServer.stop();
    }

    @Override
    public boolean waitPlayer(float delta) {

        if (state == ConnectionState.NETWORK_EXCEPTION){
            return true;
        }

        if (state == ConnectionState.DISCONNECTED_PLAYER){
            return true;
        }

        final int qtyConnection = gameServer.getConnections().length;
        if(qtyConnection == 1){
            state = ConnectionState.CONNECTED_PLAYER;
            return false;
        }

        return true;
    }

    @Override
    public void sendState(Object object) {
        if (ss.sendTCP) {
            gameServer.sendToAllTCP(object);
        } else {
            gameServer.sendToAllUDP(object);
        }
    }

    @Override
    public void release() {
        if(gameServer==null)
            return;
        gameServer.stop();
        broadcastServer.stop();
        try {
            broadcastServer.dispose();
            gameServer.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
