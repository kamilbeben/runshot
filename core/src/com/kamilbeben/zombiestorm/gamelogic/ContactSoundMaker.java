package com.kamilbeben.zombiestorm.gamelogic;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by bezik on 03.11.16.
 */
public class ContactSoundMaker {

    private Sound honk;
    private Sound hit;
    private Sound reload;
    private float volume;

    public ContactSoundMaker(AssetManager sounds, float volume) {
        honk = sounds.get("audio/sfx/honk.ogg", Sound.class);
        hit = sounds.get("audio/sfx/hit.ogg", Sound.class);
        reload = sounds.get("audio/sfx/reload.ogg", Sound.class);
        this.volume = volume;
    }

    public void honk() {
        honk.play(volume);
    }

    public void hit() {
        hit.play(volume);
    }

    public void reload() {
        reload.play(volume);
    }
}
