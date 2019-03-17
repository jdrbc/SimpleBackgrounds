package ca.jdr23bc.backgrounds.backgrounds;

import android.content.SharedPreferences;

public class BackgroundPreferences {

    private SharedPreferences preferences;

    public BackgroundPreferences(SharedPreferences prefs) {
        this.preferences = prefs;
    }

    public Boolean treesEnabled() {
        return preferences.getBoolean("treesEnabled", true);
    }

    public Boolean circlesEnabled() {
        return preferences.getBoolean("circlesEnabled", true);
    }

    public Boolean rectanglesEnabled() {
        return preferences.getBoolean("rectanglesEnabled", true);
    }

    public Boolean targetsEnabled() {
        return preferences.getBoolean("targetsEnabled", true);
    }

    public Boolean linesEnabled() {
        return preferences.getBoolean("linesEnabled", true);
    }

    public Boolean starsEnabled() {
        return preferences.getBoolean("starsEnabled", true);
    }
}
