package ser.pipi.piball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
    final Texture confirmFlag;
    final Texture approveFlag;

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

    enum State {CHOICE, CONFIRM_WAIT, APPROVE}

    float wait_timeout = 0;
    int selectFlag;

    State state = State.CHOICE;

    final PositionInterface positionInterface;
    final PositionInspector positionInspector;
    final BitmapFont fontGameInspector;

    public Welcome(Piball piball) {
        this.positionInterface = piball.getPositionInterface();
        this.piball = piball;
        this.ss = piball.getSettingsStruct();
        positionInspector = new PositionInspector(positionInterface);
        spriteBatch = new SpriteBatch();
        welcomeTexture = TextureList.loadTexture(TextureList.WELCOME);
        confirmFlag = new Texture("confirm.png");
        approveFlag = new Texture("approve.png");
        this.flagList = new FlagList();
        this.fon = SoundsList.musics.getMusic(SoundsList.musics.common_fon);
        fontGameInspector = FontList.welcomeFont();
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
        render();
    }

    private void update(float delta){
        positionInspector.checkPiPos(delta);
        switch (state) {
            case CHOICE: choice(delta); break;
            case CONFIRM_WAIT: confirm(delta); break;
            case APPROVE: approve(delta); break;
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
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(welcomeTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderFlags();
        print_status();
        spriteBatch.end();
    }

    private void print_status(){
        final String status = positionInspector.getStatus();
        fontGameInspector.draw(spriteBatch,status,
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

        Gdx.app.log(TAG, "row " + row + "col " + col);

        int number = col + row * MAX_COLS;
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
        Texture border;
        if(state == State.CONFIRM_WAIT){
            border = confirmFlag;
        } else if(state == State.APPROVE){
            border = approveFlag;
        } else {
            return;
        }

        if(indexFlag < 0 || indexFlag > FlagList.NUM_COUNTRY){
            return;
        }

        final float[] xy = calcXYFlag(indexFlag);
        final float borderWidth = 22;
        spriteBatch.draw(border,xy[0] - borderWidth,xy[1] - borderWidth,
                WIDTH_FLAG + borderWidth*2,HEIGHT_FLAG + 2*borderWidth);
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

    @Override
    public void dispose () {
        spriteBatch.dispose();
        welcomeTexture.dispose();
        fon.stop();
        fon.dispose();
        confirmFlag.dispose();
        approveFlag.dispose();
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

    }

}
