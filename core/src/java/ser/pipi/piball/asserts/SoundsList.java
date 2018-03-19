package ser.pipi.piball.asserts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by ser on 27.02.18.
 */

public class SoundsList {

    //static final String SUFFIX = ".wav";
    static final String SUFFIX = ".ogg";

    public static class effects {
        static final String SOUND_DIR = "sounds/";
        public static final String kick = SOUND_DIR + "kick" + SUFFIX;
        public static final String referee = SOUND_DIR + "referee" + SUFFIX;
    }

    public static class musics {
        static final String MUSIC_DIR = "musics/";
        public static final String stadium = MUSIC_DIR + "stadium" + SUFFIX;
        public static final String common_fon = MUSIC_DIR + "common_fon" + SUFFIX;
        public static final String atmosphere = MUSIC_DIR + "atm" + SUFFIX;
        public static final String ole = MUSIC_DIR + "ole" + SUFFIX;

        public static Music getMusic(String name){
            final Music music = Gdx.audio.newMusic(Gdx.files.internal(name));
            music.setLooping(true);
            return music;
        }
    }
}
