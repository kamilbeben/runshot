package com.kamilbeben.zombiestorm.gamelogic;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by bezik on 02.11.16.
 */
public class PlayscreenSounds {

    private Sound shot;
    private float sfxVolume;

    public PlayscreenSounds(AssetManager manager, float sfxVolume, float musicVolume) { //TODO move to shotung class
        this.sfxVolume = sfxVolume;
        shot = manager.get("audio/sfx/shot.ogg", Sound.class);
    }

    public void shot() {
        shot.play(sfxVolume);
    }
}
