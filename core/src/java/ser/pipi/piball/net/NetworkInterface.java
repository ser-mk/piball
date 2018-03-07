package ser.pipi.piball.net;

/**
 * Created by ser on 03.03.18.
 */

public interface NetworkInterface {

    void prepare(float delta);
    boolean waitPlayer(float delta);
    void updateStatus(float delta);
    void sendState(Object object);
    void release();

}
