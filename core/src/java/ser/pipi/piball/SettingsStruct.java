package ser.pipi.piball;

/**
 * Created by ser on 20.02.18.
 */

public class SettingsStruct {
    public float wait_end_game = 2;

    public float yPaddleSelf = 0;
    public float widthPaddle = 155;
    public float heigthPaddle = 22;
    public float radiusBall = 15;
    public float focus = 220;
    public boolean server = true;
    public int bankPort = 0;
    public int timeoutDiscoverHost = 2000;
    public int timeoutServerConnect = 2000;
    public float sendPeriod = 0;
    public boolean sendTCP = false;

    public int deadDiff = 11;
    public int maxVelocityPaddle = 5555;
    public int minVelocityPaddle = 55;
    public int minAccPaddle = 555;
    public int maxAccPaddle = 2000;

    public int startBallVelocity = 155;
    public float ballAcc = 0.02f;
    public int maxBallVelocity = 555;
    public int goalStepVelocity = 33;

    public float TIMEOUT_TOUCH_FLAG = 0.5f;
}
