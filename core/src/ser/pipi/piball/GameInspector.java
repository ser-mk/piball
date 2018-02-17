package ser.pipi.piball;

/**
 * Created by ser on 18.02.18.
 */

class GameInspector {

    GameInterface pii;
    public GameInspector(GameInterface pii) {
        this.pii = pii;
    }

    static public final String EMPTY = new String();
    private String status = EMPTY;
    private final String CLOSE_GAME = "CLOSE_GAME";
    private final String CONNECTED_PROBLEM = "CONNECTED_PROBLEM!";
    private final float TIMEOUT_EXIT = 10;
    private float wait_exit = 0;

    public void checkPiPos(float delta){
        checkPI(pii.getPosition(),delta);
    }

    public void checkPI(int pos, float delta){
        if(pos >= GameInterface.POSITION_UNDEFINED) {
            wait_exit = 0;
            status = EMPTY;
            return;
        }
        if(pos == GameInterface.CLOSE_GAME){
            status = CLOSE_GAME;
        } else {
            status = CONNECTED_PROBLEM;
        }

        wait_exit += delta;
        if(wait_exit > TIMEOUT_EXIT){
            pii.release();
        }
    }

    public String getStatus(){
        return status;
    }
}
