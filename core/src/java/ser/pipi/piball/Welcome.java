package ser.pipi.piball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Date;

import ser.pipi.piball.asserts.FlagList;
import ser.pipi.piball.asserts.FontList;
import ser.pipi.piball.asserts.SoundsList;
import ser.pipi.piball.asserts.TextureList;

/**
 * Created by ser on 15.02.18.
 */

class Welcome implements Screen {

    final String TAG = this.getClass().getName();

    final Piball piball;
    final SettingsStruct ss;

    final SpriteBatch spriteBatch;
    final Texture welcomeTexture;
    final float borderWidth = 15;
    final Texture edging;

    final FlagList flagList;
    final Music fon;

    final int WIDTH_FLAG = 140;
    final int HEIGHT_FLAG = 105;
    final int WIDTH_COL = 35 + WIDTH_FLAG;
    final int HEIGHT_ROW = 22 + HEIGHT_FLAG;
    final int START_X = 100;
    final int START_Y = 10;
    final int MAX_ROWS = 8;
    final int MAX_COLS = 4;
    final int START_GROUP_X = 25;

    enum State {CHOICE, CONFIRM_WAIT, APPROVE}

    float wait_timeout = 0;
    int selectFlag;

    State state = State.CHOICE;

    final PositionInterface positionInterface;
    final BitmapFont fontStatus;

    final Date dateCreating;

    public Welcome(Piball piball) {
        this.positionInterface = piball.getPositionInterface();
        this.piball = piball;
        this.ss = piball.getSettingsStruct();
        spriteBatch = new SpriteBatch();
        welcomeTexture = TextureList.loadTexture(TextureList.WELCOME);
        edging = TextureList.loadTexture(TextureList.EDGING);
        this.flagList = new FlagList();
        this.fon = SoundsList.musics.getMusic(SoundsList.musics.ole);
        fontStatus = FontList.welcomeFont();
        dateCreating = new Date();
    }

    @Override
    public void show() {
        fon.play();
        reset_variable();
    }

    private void reset_variable(){
        state = State.CHOICE;
        wait_timeout = 0;
        selectFlag = -1;
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkEnd(delta);
        render();
    }

    private void update(float delta){
        switch (state) {
            case CHOICE: choice(delta); break;
            case CONFIRM_WAIT: confirm(delta); break;
            case APPROVE: approve(delta); break;
        }
    }

    private float waitEnd = 0;

    private long DEAD_TIME_MS_EXIT =3*1000;

    private void checkEnd(float delta){
        boolean endForce = false;

        switch (positionInterface.getState()){
            case CLOSE_GAME: endForce = true; break;
            case CONNECTED_PROBLEM: endForce = true; break;
            case BACKSPACE:
                final long diff = new Date().getTime() - dateCreating.getTime();
                if(diff > DEAD_TIME_MS_EXIT) {
                    System.out.println("backspace@@@@@@@@");
                    piball.exit();
                } else {
                    positionInterface.clearState();
                }
                break;
        }

        if (endForce){
            waitEnd += delta;
            if (waitEnd > ss.wait_end_game){
                piball.exit();
            }
        } else {
            waitEnd = 0;
        }
    }

    private void choice(float delta){

        selectFlag = getNumberTouchedFlag();
        if(selectFlag < 0)
            return;
        state = State.CONFIRM_WAIT;
        wait_timeout = 0;
    }

    private void confirm(float delta){
        if(need_wait(delta)){
            return;
        }
        final int indexFlag = getNumberTouchedFlag();
        if(indexFlag < 0)
            return;
        if(indexFlag == selectFlag){
            state = State.APPROVE;
        }
        selectFlag = indexFlag;
        wait_timeout = 0;
    }

    private boolean need_wait(float delta){
        wait_timeout +=delta;
        if(wait_timeout > ss.TIMEOUT_TOUCH_FLAG)
            return false;
        return true;
    }

    private void approve(float delta){
        if(!need_wait(delta)){
            piball.startArena(selectFlag);
        }
    }

    private void render(){
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(welcomeTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderFlags();
        print_status();
        printTitle(state);
        printColNameGroup();
        spriteBatch.end();
    }

    private void print_status(){
        final String status = positionInterface.getStringErrorPiStatus();
        fontStatus.draw(spriteBatch,status,
                Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/2);
    }

    private int getNumberTouchedFlag(){
        int[] xy = new int[2];
        if(!touchedPosition(xy)){
            return -1;
        }
        int row = (Gdx.graphics.getHeight() - xy[1] - START_Y) / HEIGHT_ROW;
        int col = (xy[0] - START_X) / WIDTH_COL;
        if(row < 0 || row >= MAX_ROWS)
            return -1;
        if(col < 0 || col >= MAX_COLS)
            return -1;

        int number = col + row * MAX_COLS;

        Gdx.app.log(TAG, "row " + row + "col " + col + " select " + number);

        return number;
    }

    private float[] calcXYFlag(int index){
        final float x = WIDTH_COL *(index%MAX_COLS) + START_X;
        final float y = (index/MAX_COLS)*HEIGHT_ROW + START_Y;
        return new float[]{x,y};
    }

    private void renderFlags(){

        if(selectFlag > 0 && state != State.CHOICE){
            drawBorder(selectFlag, state);
        }
        for(int i = 0; i < FlagList.NUM_COUNTRY; i++){
            final float xy[] = calcXYFlag(i);
            spriteBatch.draw(flagList.getFlag(i),xy[0],xy[1], WIDTH_FLAG,HEIGHT_FLAG);
        }

    }

    private void drawBorder(final int indexFlag, State state){
        if(indexFlag < 0 || indexFlag > FlagList.NUM_COUNTRY){
            return;
        }

        Texture border = edging;
        final Color prevColor = spriteBatch.getColor();
        final Color edgingColor = prevColor.cpy();

        if(state == State.CONFIRM_WAIT){
            edgingColor.a = 0.5f;
            spriteBatch.setColor(edgingColor);
        }

        final float[] xy = calcXYFlag(indexFlag);

        spriteBatch.draw(border,xy[0] - borderWidth,xy[1] - borderWidth,
                WIDTH_FLAG + borderWidth*2,HEIGHT_FLAG + 2*borderWidth);

        spriteBatch.setColor(prevColor);
    }

    private boolean touchedPosition(int[] xy){
        if(!Gdx.input.justTouched())
            return false;

        final int x = Gdx.input.getX();
        final int y = Gdx.input.getY();

        Gdx.app.log(TAG,"X "+x + " Y " + y);

        xy[0] = x; xy[1] = y;

        return true;
    }

    final String CHOICE_TITLE = "CHOICE YOUR COUNTRY:";
    final String APPROVE_TITLE = "LOAD GAME...";
    final String CONFIRM_WAIT_TITLE = "APPROVE YOUR CHOICE:";
    final int Y_TITLE = 1150;

    private void printTitle(State state){
        switch (state){
            case CHOICE:
                FontList.printTextCenter(spriteBatch, fontStatus, CHOICE_TITLE,
                    Gdx.graphics.getWidth() / 2, Y_TITLE);
                break;
            case CONFIRM_WAIT:
                FontList.printTextCenter(spriteBatch, fontStatus, CONFIRM_WAIT_TITLE,
                        Gdx.graphics.getWidth() / 2, Y_TITLE);
                break;
            case APPROVE:
                FontList.printTextCenter(spriteBatch, fontStatus, APPROVE_TITLE,
                        Gdx.graphics.getWidth() / 2, Y_TITLE);
                break;
        }
    }

    private void printColNameGroup(){
        final int A_pos = 65;

        for(int i = 0; i < MAX_ROWS; i++){
            final String group = Character.toString((char) (A_pos + MAX_ROWS - 1 - i)) + ":";
            final float Y = START_Y + i*HEIGHT_ROW + fontStatus.getLineHeight();
            fontStatus.draw(spriteBatch,group,START_GROUP_X,Y);
        }
    }

    @Override
    public void dispose () {
        spriteBatch.dispose();
        welcomeTexture.dispose();
        fon.stop();
        fon.dispose();
        edging.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, " - hide");
        fon.stop();
        fon.dispose();
    }

}
