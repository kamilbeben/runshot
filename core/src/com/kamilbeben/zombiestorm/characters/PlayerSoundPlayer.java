package com.kamilbeben.zombiestorm.characters;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by bezik on 03.11.16.
 */
public class PlayerSoundPlayer {

    private Sound jump;
    private float volume;

    public PlayerSoundPlayer(AssetManager sounds, float volume) {
        jump = sounds.get("audio/sfx/jump.ogg", Sound.class);
        this.volume = volume;
    }

    public void jump() {
        jump.play(volume);
    }
}
