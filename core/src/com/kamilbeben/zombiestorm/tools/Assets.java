package com.kamilbeben.zombiestorm.tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by bezik on 12.10.16.
 */
public class Assets implements Disposable {

    public AssetManager textures;
    public AssetManager sounds;
    public TextureHolder textureHolder;

    public Assets() {
        textures = new AssetManager();
        sounds = new AssetManager();
        textureHolder = new TextureHolder();
    }

    public float getProgress() {
        return (sounds.getProgress() + textures.getProgress()) /2 ;
    }

    public void loadLoadingscreenAssets() {
        textures.clear();
        textures.load("texture/loadscreen/background.jpg", Texture.class);
        textures.load("texture/loadscreen/progressBG.jpg", Texture.class);
        textures.load("texture/loadscreen/progressKNOB.jpg", Texture.class);
        waitForLoadingToComplete(textures);
        textureHolder.loadLoadingScreen(
                textures.get("texture/loadscreen/background.jpg", Texture.class),
                textures.get("texture/loadscreen/progressBG.jpg", Texture.class),
                textures.get("texture/loadscreen/progressKNOB.jpg", Texture.class)
        );
    }

    public void loadMenuSounds() {
        sounds.load("audio/music/menu.ogg", Music.class);
        waitForLoadingToComplete(sounds);
    }

    public boolean loadPlayscreenComplete() {
        return (textures.update() && sounds.update());
    }

    public void loadPlaySounds() {
        sounds.load("audio/sfx/shot.ogg", Sound.class);
        sounds.load("audio/sfx/reload.ogg", Sound.class);
        sounds.load("audio/sfx/jump.ogg", Sound.class);
        sounds.load("audio/sfx/honk.ogg", Sound.class);
        sounds.load("audio/sfx/hit.ogg", Sound.class);
    }


    public void assignPlayscreenTextures() {
        textureHolder.loadPlayscreen(
                textures.get("texture/playscreen/hud/ammoBG.png", Texture.class),
                textures.get("texture/playscreen/hud/pause.png", Texture.class),
                textures.get("texture/playscreen/hud/ammoON.png", Texture.class),
                textures.get("texture/playscreen/hud/ammoOFF.png", Texture.class),
                textures.get("texture/playscreen/extras/shell.png", Texture.class),
                textures.get("texture/playscreen/extras/ammopack.png", Texture.class),
                textures.get("texture/playscreen/extras/grass_animation.png", Texture.class),
                textures.get("texture/playscreen/extras/firefly.png", Texture.class),
                textures.get("texture/playscreen/extras/firefly_blue.png", Texture.class),
                textures.get("texture/playscreen/extras/firefly_transparent.png", Texture.class),
                textures.get("texture/playscreen/extras/palette.png", Texture.class),
                textures.get("texture/playscreen/extras/background.jpg", Texture.class),
                textures.get("texture/playscreen/extras/parallax_far.png", Texture.class),
                textures.get("texture/playscreen/extras/parallax_middle.png", Texture.class),
                textures.get("texture/playscreen/extras/parallax_fog.png", Texture.class),
                textures.get("texture/playscreen/extras/fire_effect.png", Texture.class),
                textures.get("texture/playscreen/extras/points_break.png", Texture.class),
                textures.get("texture/playscreen/characters/car.png", Texture.class),
                textures.get("texture/playscreen/characters/car_lights.png", Texture.class),
                textures.get("texture/playscreen/characters/monkey.png", Texture.class),
                textures.get("texture/playscreen/characters/walker.png", Texture.class),
                textures.get("texture/playscreen/characters/player.png", Texture.class),
                textures.get("texture/playscreen/characters/player_ext.png", Texture.class),
                textures.get("texture/playscreen/obstacles/hole_long.png", Texture.class),
                textures.get("texture/playscreen/obstacles/hole_short.png", Texture.class),
                textures.get("texture/playscreen/obstacles/island_long.png", Texture.class),
                textures.get("texture/playscreen/obstacles/island_short.png", Texture.class),
                textures.get("texture/playscreen/obstacles/stone_big.png", Texture.class),
                textures.get("texture/playscreen/obstacles/stone_small.png", Texture.class)
        );

        textures.unload("texture/loadscreen/background.jpg");
        textures.unload("texture/loadscreen/progressBG.jpg");
        textures.unload("texture/loadscreen/progressKNOB.jpg");
    }

    public void loadPlayscreenTextures() {
        textures.load("texture/playscreen/hud/ammoBG.png", Texture.class);
        textures.load("texture/playscreen/hud/pause.png", Texture.class);
        textures.load("texture/playscreen/hud/ammoOFF.png", Texture.class);
        textures.load("texture/playscreen/hud/ammoON.png", Texture.class);
        textures.load("texture/playscreen/extras/shell.png", Texture.class);
        textures.load("texture/playscreen/extras/ammopack.png", Texture.class);
        textures.load("texture/playscreen/extras/grass_animation.png", Texture.class);
        textures.load("texture/playscreen/extras/firefly.png", Texture.class);
        textures.load("texture/playscreen/extras/firefly_blue.png", Texture.class);
        textures.load("texture/playscreen/extras/firefly_transparent.png", Texture.class);
        textures.load("texture/playscreen/extras/palette.png", Texture.class);
        textures.load("texture/playscreen/extras/background.jpg", Texture.class);
        textures.load("texture/playscreen/extras/parallax_far.png", Texture.class);
        textures.load("texture/playscreen/extras/parallax_middle.png", Texture.class);
        textures.load("texture/playscreen/extras/parallax_fog.png", Texture.class);
        textures.load("texture/playscreen/extras/fire_effect.png", Texture.class);
        textures.load("texture/playscreen/extras/points_break.png", Texture.class);
        textures.load("texture/playscreen/characters/car.png", Texture.class);
        textures.load("texture/playscreen/characters/car_lights.png", Texture.class);
        textures.load("texture/playscreen/characters/monkey.png", Texture.class);
        textures.load("texture/playscreen/characters/walker.png", Texture.class);
        textures.load("texture/playscreen/characters/player.png", Texture.class);
        textures.load("texture/playscreen/characters/player_ext.png", Texture.class);
        textures.load("texture/playscreen/obstacles/hole_long.png", Texture.class);
        textures.load("texture/playscreen/obstacles/hole_short.png", Texture.class);
        textures.load("texture/playscreen/obstacles/island_long.png", Texture.class);
        textures.load("texture/playscreen/obstacles/island_short.png", Texture.class);
        textures.load("texture/playscreen/obstacles/stone_big.png", Texture.class);
        textures.load("texture/playscreen/obstacles/stone_small.png", Texture.class);
        textures.load("fonts/font.fnt", BitmapFont.class);
    }

    public void loadMenuAssets() {
        textures.clear();
        textures.load("texture/menuscreen/background.jpg", Texture.class);
        textures.load("texture/menuscreen/background_top.png", Texture.class);
        textures.load("texture/playscreen/extras/parallax_fog.png", Texture.class);
        textures.load("texture/menuscreen/play.png", Texture.class);
        textures.load("texture/menuscreen/options.png", Texture.class);
        textures.load("texture/menuscreen/about.png", Texture.class);
        textures.load("texture/menuscreen/tutorial.png", Texture.class);
        textures.load("texture/menuscreen/run_frames.png", Texture.class);
        waitForLoadingToComplete(textures);
        textureHolder.loadMenu(
                textures.get("texture/menuscreen/background.jpg", Texture.class),
                textures.get("texture/menuscreen/background_top.png", Texture.class),
                textures.get("texture/playscreen/extras/parallax_fog.png", Texture.class),
                textures.get("texture/menuscreen/play.png", Texture.class),
                textures.get("texture/menuscreen/options.png", Texture.class),
                textures.get("texture/menuscreen/about.png", Texture.class),
                textures.get("texture/menuscreen/tutorial.png", Texture.class),
                textures.get("texture/menuscreen/run_frames.png", Texture.class)
        );
    }


    public void loadAboutAssets() {
        textures.clear();

        textures.load("fonts/font_about.fnt", BitmapFont.class);
        textures.load("texture/aboutscreen/background.jpg", Texture.class);
        textures.load("texture/aboutscreen/return.png", Texture.class);
        waitForLoadingToComplete(textures);
        textureHolder.loadAbout(
                textures.get("texture/aboutscreen/background.jpg", Texture.class),
                textures.get("texture/aboutscreen/return.png", Texture.class)
        );
    }


    public void loadTutorialAssets() {
        textures.clear();
        textures.load("texture/tutorial/1.jpg", Texture.class);
        textures.load("texture/tutorial/2.jpg", Texture.class);
        textures.load("texture/tutorial/3.jpg", Texture.class);
        textures.load("texture/tutorial/4.jpg", Texture.class);
        textures.load("texture/tutorial/5.jpg", Texture.class);
        waitForLoadingToComplete(textures);
        textureHolder.loadTutorialscreen(
                textures.get("texture/tutorial/1.jpg", Texture.class),
                textures.get("texture/tutorial/2.jpg", Texture.class),
                textures.get("texture/tutorial/3.jpg", Texture.class),
                textures.get("texture/tutorial/4.jpg", Texture.class),
                textures.get("texture/tutorial/5.jpg", Texture.class)
        );
    }


    public void loadOptionsAssets() {
        textures.clear();

        textures.load("texture/optionscreen/background.jpg", Texture.class);
        textures.load("texture/optionscreen/return.png", Texture.class);
        textures.load("texture/optionscreen/background.jpg", Texture.class);
        textures.load("texture/optionscreen/background.jpg", Texture.class);
        textures.load("texture/optionscreen/slider_background.png", Texture.class);
        textures.load("texture/optionscreen/knob.png", Texture.class);
        waitForLoadingToComplete(textures);
        textureHolder.loadOptions(
                textures.get("texture/optionscreen/background.jpg", Texture.class),
                textures.get("texture/optionscreen/return.png", Texture.class),
                textures.get("texture/optionscreen/background.jpg", Texture.class),
                textures.get("texture/optionscreen/background.jpg", Texture.class),
                textures.get("texture/optionscreen/slider_background.png", Texture.class),
                textures.get("texture/optionscreen/knob.png", Texture.class)
        );
    }

    private void waitForLoadingToComplete(AssetManager manager) {
        while (!manager.update()) {
        }
    }

    @Override
    public void dispose() {
        textures.dispose();
    }
}
