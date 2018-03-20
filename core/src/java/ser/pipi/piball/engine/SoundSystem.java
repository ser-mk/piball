package ser.pipi.piball.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ser.pipi.piball.asserts.SoundsList;

/**
 * Created by ser on 26.02.18.
 */

public class SoundSystem {
    final AllObjectsState allObjectsState;
    final LocalState localState;
    
    final private Map<String, Sound> sounds = new HashMap<String, Sound>();
    final private Map<String, Music> musics = new HashMap<String, Music>();

    public SoundSystem(AllObjectsState allObjectsState, LocalState localState) {
        this.allObjectsState = allObjectsState;
        this.localState = localState;
        initSounds();
        initMusics();
    }

    private void initSounds(){

        sounds.put(SoundsList.effects.kick,
                Gdx.audio.newSound(Gdx.files.internal(SoundsList.effects.kick)));
        sounds.put(SoundsList.effects.referee,
                Gdx.audio.newSound(Gdx.files.internal(SoundsList.effects.referee)));
    }

    private void initMusics(){

        musics.put(SoundsList.musics.stadium,
                Gdx.audio.newMusic(Gdx.files.internal(SoundsList.musics.stadium)));
        musics.put(SoundsList.musics.common_fon,
                Gdx.audio.newMusic(Gdx.files.internal(SoundsList.musics.common_fon)));
        musics.put(SoundsList.musics.atmosphere,
                Gdx.audio.newMusic(Gdx.files.internal(SoundsList.musics.atmosphere)));
        musics.put(SoundsList.musics.ole,
                Gdx.audio.newMusic(Gdx.files.internal(SoundsList.musics.ole)));
    }
    
    static public String getDefaultMusic(){
        return SoundsList.musics.atmosphere;
    }

    public void update(){
        effects();
        musicFon();
    }
    
    
    
    private void effects(){
        final String[] soundEffect = allObjectsState.soundEffect;
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
        final String musicState = allObjectsState.musicFon;
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

    public void release(){
        if(currentMusic == null)
            return;

        if(musics.get(currentMusic) == null)
            return;

        musics.get(currentMusic).stop();
        musics.get(currentMusic).dispose();
    }

    private static boolean appendSound(ArrayList<String> soundList, String effect){
        if(soundList.contains(effect)){
            return false;
        }
        soundList.add(effect);
        return true;
    }

    private static boolean removeSound(ArrayList<String> soundList, String effect){
        if(!soundList.contains(effect)){
            return false;
        }
        soundList.remove(effect);
        return true;
    }

    public static String[] appendSound(String[] soundEffect, String effect){
        ArrayList<String> list = new ArrayList( Arrays.asList(soundEffect));
        appendSound(list, effect);
        return list.toArray(new String[0]);
    }

    public static String[] removeSound(String[] soundEffect, String effect){
        ArrayList<String> list = new ArrayList( Arrays.asList(soundEffect));
        removeSound(list, effect);
        return list.toArray(new String[0]);
    }
}
