package ser.pipi.piball.settings.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import ser.pipi.piball.Piball;
import ser.pipi.piball.Settings;
import ser.pipi.piball.asserts.TextureList;

/**
 * Created by ser on 19.04.18.
 */

public class SettingsScreen implements Screen {

    final String TAG = this.getClass().getName();

    final Piball piball;

    final Texture welcomeTexture;

    final Skin skin;
    final Stage stage;
    final SpriteBatch spriteBatch;
    final ScrollPane scrollPane;
    final PrefTable prefTable;

    public SettingsScreen(Piball piball) {
        this.piball = piball;
        spriteBatch = new SpriteBatch();
        welcomeTexture = TextureList.loadTexture(TextureList.WELCOME);

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("data/glassy-ui" +
                ".json"));

        prefTable = new PrefTable(skin);
        scrollPane = new ScrollPane(prefTable);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        scrollPane.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        stage.addActor(scrollPane);
        prefTable.setPosition(0, 0);

        //prefTable.debug();
        prefTable.convertSettingsStruct2Table(piball.getSettings());
        TextButton saveButton = new TextButton("Save", skin);
        saveButton.addListener(saveClick);
        saveButton.align(Align.center);
        prefTable.add(saveButton);
        prefTable.pack();

        stage.addListener(backListener);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(welcomeTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if(isBack){
            piball.startWelcome();
        }
    }

    final InputListener saveClick = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            setPiballSettings();
            piball.saveSettings();
            Gdx.app.log(TAG,"saveClick");
            return true;
        }
    };

    private void setPiballSettings(){
        prefTable.checkAndCorrectValueTable();
        final Settings settings = prefTable.getSettingsFromTable();
        piball.setSettings(settings);
        Gdx.app.log(TAG,"setPiballSettings");
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        setPiballSettings();
    }

    @Override
    public void dispose() {
        stage.dispose();
        spriteBatch.dispose();
    }

    boolean isBack = false;

    final InputListener backListener =  new InputListener() {
        @Override
        public boolean keyUp(InputEvent event, int keycode) {
            if (keycode == Input.Keys.B
                    || keycode == Input.Keys.BACK){
                Gdx.app.log(TAG, "BACKSPACE");
                isBack = true;
            }
            return super.keyUp(event, keycode);
        }
    };
}
