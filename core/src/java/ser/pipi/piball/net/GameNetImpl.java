package ser.pipi.piball.net;

import com.esotericsoftware.kryonet.Connection;

/**
 * Created by ser on 06.03.18.
 */

public interface GameNetImpl {
    public void recieve(Connection connection, Object object);
    public void setState(Network.ConnectionState state);
}
