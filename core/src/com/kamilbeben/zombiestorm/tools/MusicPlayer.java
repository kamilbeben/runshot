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
    }

    public void startMenuTheme() {
        game.assets.loadMenuSounds();
        setupMusic(game.assets.sounds.get("audio/music/menu.ogg", Music.class));
    }

    public void startGameTheme() {
        setupMusic(game.assets.sounds.get("audio/music/game.ogg", Music.class));
    }

    private void setupMusic(Music source) {
        stopCurrentSong();
        music = source;
        music.setLooping(true);
        music.setVolume(game.options.musicVolume);
        music.play();
    }

    private void stopCurrentSong() {
        if (music != null) {
            music.stop();
        }
    }


    public void updateMusicVolume() {
        music.setVolume(game.options.musicVolume);
    }
}
