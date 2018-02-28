package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pipi.piball.Asserts.SoundsList;

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

        sounds.put(SoundsList.effects.kick,
                Gdx.audio.newSound(Gdx.files.internal(
                        "sounds/" + SoundsList.effects.kick)));
        sounds.put(SoundsList.effects.referee,
                Gdx.audio.newSound(Gdx.files.internal(
                        "sounds/" + SoundsList.effects.referee)));
    }

    private void initMusics(){

        musics.put(SoundsList.musics.stadium,
                Gdx.audio.newMusic(Gdx.files.internal(
                        "musics/" + SoundsList.musics.stadium)));
        musics.put(SoundsList.musics.goal,
                Gdx.audio.newMusic(Gdx.files.internal(
                        "musics/" + SoundsList.musics.goal)));
    }
    
    static public String getDefaultMusic(){
        return "stadium.wav";
    }

    public void update(){
        effects();
        musicFon();
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

    private void musicFon(){
        final String musicState = stateStore.musicFon;
        if(musicState == null)
            return;
        if (musicState.isEmpty())
            return;

        if(!musics.containsKey(musicState))
            return;

        if(musicState == currentMusic)
            return;

        if (currentMusic != EMPTY_MUSIC )
            musics.get(currentMusic).stop();

        currentMusic = musicState;
        musics.get(currentMusic).setLooping(true);
        musics.get(currentMusic).play();
    }

    public static boolean appendSound(ArrayList<String> soundList, String effect){
        if(soundList.contains(effect)){
            return false;
        }
        soundList.add(effect);
        return true;
    }

    public static boolean removeSound(ArrayList<String> soundList, String effect){
        if(!soundList.contains(effect)){
            return false;
        }
        soundList.remove(effect);
        return true;
    }
}
