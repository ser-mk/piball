package ser.pipi.piball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by ser on 15.02.18.
 */

class Welcome implements Screen {

    final String TAG = this.getClass().getName();

    SpriteBatch spriteBatch;
    Texture field;
    Texture confirmFlag;
    Texture approveFlag;
    final int NUM_COUNTRY = 32;
    Texture[] country = new Texture[NUM_COUNTRY];

    final int WIDTH_FLAG = 111;
    final int HEIGHT_FLAG = 77;
    final int WIDTH_COL = 66 + WIDTH_FLAG;
    final int HEIGHT_ROW = 22 + HEIGHT_FLAG;
    final int START_X = 88;
    final int START_Y = 77;
    final int MAX_ROWS = 8;
    final int MAX_COLS = 4;

    enum State {CHOICE, CONFIRM_WAIT, APPROVE}

    final float MAX_WAIT_TIMEOUT = 2;
    float wait_timeout = 0;
    int selectFlag;

    State state = State.CHOICE;

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        field = new Texture("field.jpg");
        for (int i = 0; i < NUM_COUNTRY; i++){
            country[i] = new Texture("country/" + i + ".png");
        }
        confirmFlag = new Texture("confirm.png");
        approveFlag = new Texture("approve.png");
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
        if(wait_timeout > MAX_WAIT_TIMEOUT)
            return false;
        return true;
    }

    private void approve(float delta){
        if(!need_wait(delta)){
            Gdx.app.exit();
        }
    }

    private void render(){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(field, 0, 0,Platform.screen_width, Platform.screen_heigth);
        renderFlags();
        spriteBatch.end();
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
        for(int i = 0; i < NUM_COUNTRY; i++){
            final float xy[] = calcXYFlag(i);
            spriteBatch.draw(country[i],xy[0],xy[1], WIDTH_FLAG,HEIGHT_FLAG);
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

        if(indexFlag < 0 || indexFlag > NUM_COUNTRY){
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
        field.dispose();
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
