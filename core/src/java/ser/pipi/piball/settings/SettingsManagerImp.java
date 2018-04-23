package ser.pipi.piball.settings;

import ser.pipi.piball.Settings;

/**
 * Created by ser on 20.04.18.
 */

public interface SettingsManagerImp {

    public Settings getSettings4Game();

    public void saveSettingsFromGame(Settings settings);
}
