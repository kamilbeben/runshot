package com.kamilbeben.zombiestorm.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by bezik on 22.10.16.
 */
public class Data {

    private Preferences preferences;
    private static final String DATA_SFX = "sfx";
    private static final String DATA_MUSIC = "music";
    private static final String DATA_HIGHSCORE = "score";

    private int highScore = 0;
    public float musicVolume = 0;
    public float sfxVolume = 0;

    public Data() {
        loadData();
    }

    public int getHighScore() {
        return highScore;
    }

    private void loadData() {
        preferences = Gdx.app.getPreferences("config");
        highScore = preferences.getInteger(DATA_HIGHSCORE, 0);
        musicVolume = preferences.getFloat(DATA_MUSIC, 0.5f);
        sfxVolume = preferences.getFloat(DATA_SFX, 0.5f);
    }

    public void saveData() {
        preferences.putInteger(DATA_HIGHSCORE, highScore);
        preferences.putFloat(DATA_MUSIC, musicVolume);
        preferences.putFloat(DATA_SFX, sfxVolume);
        preferences.flush();
    }

}
