package ser.pipi.piball.net;

import com.badlogic.gdx.Gdx;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

import ser.pipi.piball.GameTestHeadlessApplication;
import ser.pipi.piball.SettingsStruct;
import ser.pipi.piball.engine.LocalState;

import static org.junit.Assert.*;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.backends.;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by ser on 04.03.18.
 */
public class GameClientTest extends GameTestHeadlessApplication {
    final String TAG = this.getClass().getName();

    GameClient client;
    GameServer server;

    @Before
    public void prepare(){

        SettingsStruct ss = new SettingsStruct();
        LocalState localState = new LocalState(ss);
        server = new GameServer(ss, localState);
        client = new GameClient(ss, localState);

        Assert.assertFalse(server.noWaitPlayer(0));
    }


    @Test
    public void localHostCut() throws Exception {
        Assert.assertTrue(client.noWaitPlayer(0));
    }

}