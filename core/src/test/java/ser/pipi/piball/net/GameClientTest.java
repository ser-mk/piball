package ser.pipi.piball.net;

import org.junit.Before;
import org.junit.Test;

import ser.pipi.piball.GameTestHeadlessApplication;
import ser.pipi.piball.SettingsStruct;
import ser.pipi.piball.engine.AllObjectsState;
import ser.pipi.piball.engine.LocalState;
import ser.pipi.piball.engine.SyncSystem;

//import com.badlogic.gdx.backends.;


/**
 * Created by ser on 04.03.18.
 */
public class GameClientTest extends GameTestHeadlessApplication {
    final String TAG = this.getClass().getName();

    SyncSystem syncSystemServer;
    LocalState localStateServer;
    AllObjectsState allObjectsStateServer;

    SyncSystem syncSystemClient;
    LocalState localStateClient;
    AllObjectsState allObjectsStateClient;

    @Before
    public void prepare(){

        SettingsStruct ss = new SettingsStruct();
        localStateServer = new LocalState(ss);
        allObjectsStateServer = new AllObjectsState(ss);

        syncSystemServer = new SyncSystem(ss, allObjectsStateServer, localStateServer);

        ss.server = false;
        localStateClient = new LocalState(ss);
        allObjectsStateClient = new AllObjectsState(ss);
        syncSystemClient = new SyncSystem(ss, allObjectsStateClient,localStateClient);

        syncSystemClient.update(0);
        syncSystemClient.update(0);
    }


    @Test
    public void localHostCut() throws Exception {
        allObjectsStateServer.selfGoal = 1;
        while (true){
            syncSystemServer.update(0);
            syncSystemClient.update(0);
            Thread.sleep(1000);
            if(allObjectsStateClient.selfGoal == allObjectsStateServer.selfGoal){
                break;
            }
        }
        while (true){
            syncSystemServer.update(0);
            syncSystemClient.update(0);
        }
    }

}