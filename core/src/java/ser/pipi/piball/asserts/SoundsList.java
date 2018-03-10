package pipi.piball.Asserts;

/**
 * Created by ser on 27.02.18.
 */

public class SoundsList {

    static final String SUFFIX = ".wav";

    public static class effects {
        static final String SOUND_DIR = "sounds/";
        public static final String kick = SOUND_DIR + "kick" + SUFFIX;
        public static final String referee = SOUND_DIR + "referee" + SUFFIX;
    }

    public static class musics {
        static final String MUSIC_DIR = "musics/";
        public static final String stadium = MUSIC_DIR + "stadium" + SUFFIX;
        public static final String common_fon = MUSIC_DIR + "common_fon" + SUFFIX;
    }
}
