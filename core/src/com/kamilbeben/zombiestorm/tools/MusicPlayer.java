package com.kamilbeben.zombiestorm.tools;

import com.badlogic.gdx.audio.Music;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 02.11.16.
 */
public class MusicPlayer {

    private Zombie game;
    public Music music;

    public MusicPlayer(Zombie game) {
        this.game = game;
        startMenuTheme();
    }

    private void startMenuTheme() {
        game.assets.loadMenuSounds();
        music = game.assets.sounds.get("audio/music/menu.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(game.options.musicVolume);
        music.play();
    }

    public void updateMusicVolume() {
        music.setVolume(game.options.musicVolume);
    }

    public void setMenuTheme() {
        music = game.assets.sounds.get("audio/music/menu.ogg", Music.class);
    }

    public void setPlayTheme() {
        music = game.assets.sounds.get("audio/music/menu.ogg", Music.class);
    }
}
