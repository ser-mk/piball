package ser.pipi.piball.net;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import ser.pipi.piball.engine.AllObjectsState;
import ser.pipi.piball.engine.LocalState;

/**
 * Created by ser on 02.03.18.
 */

public class Network {

    public enum ConnectionState {NETWORK_EXCEPTION,
        WAIT_PLAYER,
        CONNECTED_PLAYER,
        DISCONNECTED_PLAYER,
        COLLISION_PLAYER}

    static public void register (EndPoint endPoint) {
        //Log.set(Log.LEVEL_DEBUG);
        Kryo kryo = endPoint.getKryo();
        kryo.register(AllObjectsState.class, new CustJsonSerializer(AllObjectsState.class));
        kryo.register(LocalState.class, new CustJsonSerializer(LocalState.class));
    }

    static final int basePort = 5000;
    static final int offsetUDP = 1;
    static final int offsetTCP = 2;
    static final int bankStep = 10;

    static public int portBrodcast(int bankPort){
        return basePort + bankPort*bankStep;
    }

    static public int portUDP(int bankPort){
        return basePort + bankPort*bankStep + offsetUDP;
    }

    static public int portTCP(int bankPort){
        return basePort + bankPort*bankStep + offsetTCP;
    }
}
