package com.kamilbeben.zombiestorm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamilbeben.zombiestorm.screens.Menuscreen;
import com.kamilbeben.zombiestorm.tools.Assets;
import com.kamilbeben.zombiestorm.tools.MusicPlayer;
import com.kamilbeben.zombiestorm.tools.Options;

public class Zombie extends Game {

	public static final float WIDTH = 854;
	public static final float HEIGHT = 480;
	public static final float PPM = 100;

	public static final short DEAD_BIT = 0;
	public static final short ENEMY_BIT = 2;
	public static final short PLAYER_BIT = 4;
	public static final short STATIC_BIT = 8;
	public static final short GROUND_BIT = 16;
	public static final short HOLE_BIT = 64;
	public static final short WALLS_BIT = 128;
	public static final short AMMO_PACK_BIT = 256;
	public static final short CAR_BIT = 512;
	public static final short STUMBLE_BIT = 1024;

	public static final void enableAndroidBackKey() {
		Gdx.input.setCatchBackKey(true);
	}

	public static final void enableAndroidMenuKey() {
		Gdx.input.setCatchMenuKey(true);
	}

	public Options options;
	public MusicPlayer music;

	public Assets assets;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		assets = new Assets();
		options = new Options();
		music = new MusicPlayer(this);
		music.startMenuTheme();
		setScreen(new Menuscreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
