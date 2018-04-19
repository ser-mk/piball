package ser.pipi.piball.net;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import ser.pipi.piball.Settings;

import ser.pipi.piball.net.Network.ConnectionState;

/**
 * Created by ser on 02.03.18.
 */

public class GameClient extends  NetworkBaseClass implements Runnable {

    final String TAG = this.getClass().getName();

    final private Client client;
    final Settings ss;
    Thread discoverThread;

    InetAddress server;

    public GameClient(Settings ss, GameNetImpl gameNet) {
        super(gameNet);
        this.ss = ss;
        client = new Client();
        discoverThread = new Thread(this);
        super.init(client);
    }

    private List<InetAddress> discoverServer(){
        List<InetAddress> hosts = client.discoverHosts(
                Network.portBrodcast(ss.bankPort), ss.timeoutDiscoverHost);
        Gdx.app.log(TAG,"InetAddress : " + hosts);

        return  hosts;
    }

    private boolean tryConnect(InetAddress inetAddress){
        try {
            client.start();
            client.connect(ss.timeoutServerConnect,
                    inetAddress,
                    Network.portTCP(ss.bankPort),
                    Network.portUDP(ss.bankPort));
        } catch (IOException e) {
            e.printStackTrace();
            Gdx.app.log(TAG, " @@@@@@@@@" +  e.toString());
            state = ConnectionState.NETWORK_EXCEPTION;
            return false;
        }

        return true;
    }

    static public void localHostCut(List<InetAddress> addresses){
        try {
            final InetAddress localHost = InetAddress.getByName("localhost");
            //Gdx.app.log("localHostCut", "getLocalHost : " + localHost);
            while (addresses.contains(localHost)) {
                addresses.remove(localHost);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
        Gdx.app.log(TAG,"disconnected : " + connection);
        server = null;
        connection.close();
    }

    private boolean isFinishState(){
        if (state == ConnectionState.DISCONNECTED_PLAYER
                ||
                state == ConnectionState.NETWORK_EXCEPTION) {
            return true;
        }
        return false;
    }

    @Override
    public boolean waitPlayer(float delta) {
        if (server != null) {
            return false;
        }

        if (state == ConnectionState.CONNECTED_PLAYER) {
            return false;
        }

        if (isFinishState()) {
            return true;
        }

        if (!discoverThread.isAlive()) {
            discoverThread = new Thread(this);
            discoverThread.start();
        }

        return true;
    }

    @Override
    public void sendState(Object object) {
        if(ss.sendTCP) {
            client.sendTCP(object);
        } else {
            client.sendUDP(object);
        }
    }

    @Override
    public void release() {
        if (discoverThread.isAlive()) {
            discoverThread.interrupt();
            while(discoverThread.isAlive()){
                try {
                    Thread.sleep(300);
                    System.out.println("wait client thread\r\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        client.stop();
        while(client.isConnected()){
            try {
                System.out.println("client.isConnected");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            client.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("release client\r\n");
    }


    @Override
    public void run() {
        List<InetAddress> servers = discoverServer();
        while(servers.size() != 1 && !Thread.currentThread().isInterrupted()) {
            servers = discoverServer();
            localHostCut(servers);

            if (servers.size() == 0) {
                //state = ConnectionState.WAIT_PLAYER;
                continue;
            }

            if (servers.size() > 1) {
                state = ConnectionState.COLLISION_PLAYER;
                continue;
            }
        }

        while(!Thread.currentThread().isInterrupted()
                && !tryConnect(servers.get(0))
                && !isFinishState()){
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
