package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ser on 26.02.18.
 */

public class SoundSystem {
    final StateStore stateStore;
    final LocalState localState;
    
    final private Map<String, Sound> sounds = new HashMap<String, Sound>();
    final private Map<String, Music> musics = new HashMap<String, Music>();

    public SoundSystem(StateStore stateStore, LocalState localState) {
        this.stateStore = stateStore;
        this.localState = localState;
        initSounds();
        initMusics();
    }

    private void initSounds(){
        final String kick = "kick.wav";
        sounds.put(kick,
                Gdx.audio.newSound(Gdx.files.internal(
                        "sounds/" + kick)));
        final String referee = "referee.wav";
        sounds.put(referee,
                Gdx.audio.newSound(Gdx.files.internal(
                        "sounds/" + referee)));
    }

    private void initMusics(){
        final String stadium = "stadium.wav";
        musics.put(stadium,
                Gdx.audio.newMusic(Gdx.files.internal(
                        "musics/" + stadium)));
        final String goal = "goal.wav";
        musics.put(goal,
                Gdx.audio.newMusic(Gdx.files.internal(
                        "musics/" + goal)));
    }
    
    static public String getDefaultMusic(){
        return "stadium.wav";
    }

    public void update(){

    }
    
    
    
    private void effects(){
        final String[] soundEffect = stateStore.soundEffect;
        if(soundEffect == null)
            return;
        if (soundEffect.length == 0)
            return;

        for (String effects : soundEffect) {
            if(sounds.containsKey(effects)){
                Sound sound = sounds.get(effects);
                sound.play();
            }
        }
    }

    final String EMPTY_MUSIC = new String();
    private String currentMusic = EMPTY_MUSIC;

    private void music_fon(){
        final String musicState = stateStore.musicFon;
        if(musicState == null)
            return;
        if (musicState.isEmpty())
            return;

        if(!musics.containsKey(musicState))
            return;

        if (currentMusic != EMPTY_MUSIC )
            musics.get(currentMusic).stop();

        currentMusic = musicState;
        musics.get(currentMusic).setLooping(true);
        musics.get(currentMusic).play();
    }
}
