package ser.pipi.piball.net;

/**
 * Created by ser on 03.03.18.
 */

public interface NetworkInterface {

    void prepare(float delta);
    boolean noWaitPlayer(float delta);
    void setStatus(float delta);

    void release();

}
