package com.kamilbeben.zombiestorm.tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by bezik on 12.10.16.
 */
public class Assets implements Disposable {

    public AssetManager manager = new AssetManager();
    public TextureHolder textureHolder;

    public Assets() {

    }

    public void loadPlayscreenAssets(boolean clearManager) {
        if (clearManager) {
            manager.clear();
        }
        manager.load("Playscreen/Hud/ammoBG.png", Texture.class);
        manager.load("Playscreen/Hud/pause.png", Texture.class);
        manager.load("Playscreen/Hud/ammoOFF.png", Texture.class);
        manager.load("Playscreen/Hud/ammoON.png", Texture.class);
        manager.load("Playscreen/Extras/shell.png", Texture.class);
        manager.load("Playscreen/Extras/ammopack.png", Texture.class);
        manager.load("Playscreen/Extras/grass_animation.png", Texture.class);
        manager.load("Playscreen/Extras/firefly.png", Texture.class);
        manager.load("Playscreen/Extras/firefly_blue.png", Texture.class);
        manager.load("Playscreen/Extras/firefly_transparent.png", Texture.class);
        manager.load("Playscreen/Extras/palette.png", Texture.class);
        manager.load("Playscreen/Extras/background.jpg", Texture.class);
        manager.load("Playscreen/Extras/parallax_far.png", Texture.class);
        manager.load("Playscreen/Extras/parallax_middle.png", Texture.class);
        manager.load("Playscreen/Extras/parallax_fog.png", Texture.class);
        manager.load("Playscreen/Extras/fire_effect.png", Texture.class);
        manager.load("Playscreen/Characters/car.png", Texture.class);
        manager.load("Playscreen/Characters/car_lights.png", Texture.class);
        manager.load("Playscreen/Characters/monkey.png", Texture.class);
        manager.load("Playscreen/Characters/walker.png", Texture.class);
        manager.load("Playscreen/Characters/player.png", Texture.class);
        manager.load("Playscreen/Characters/player_ext.png", Texture.class);
        manager.load("Playscreen/Obstacles/hole_long.png", Texture.class);
        manager.load("Playscreen/Obstacles/hole_short.png", Texture.class);
        manager.load("Playscreen/Obstacles/island_long.png", Texture.class);
        manager.load("Playscreen/Obstacles/island_short.png", Texture.class);
        manager.load("Playscreen/Obstacles/stone_big.png", Texture.class);
        manager.load("Playscreen/Obstacles/stone_small.png", Texture.class);
        manager.load("Fonts/font.fnt", BitmapFont.class);
        waitForLoadingToComplete();
        textureHolder = new TextureHolder(
                manager.get("Playscreen/Hud/ammoBG.png", Texture.class),
                manager.get("Playscreen/Hud/pause.png", Texture.class),
                manager.get("Playscreen/Hud/ammoON.png", Texture.class),
                manager.get("Playscreen/Hud/ammoOFF.png", Texture.class),
                manager.get("Playscreen/Extras/shell.png", Texture.class),
                manager.get("Playscreen/Extras/ammopack.png", Texture.class),
                manager.get("Playscreen/Extras/grass_animation.png", Texture.class),
                manager.get("Playscreen/Extras/firefly.png", Texture.class),
                manager.get("Playscreen/Extras/firefly_blue.png", Texture.class),
                manager.get("Playscreen/Extras/firefly_transparent.png", Texture.class),
                manager.get("Playscreen/Extras/palette.png", Texture.class),
                manager.get("Playscreen/Extras/background.jpg", Texture.class),
                manager.get("Playscreen/Extras/parallax_far.png", Texture.class),
                manager.get("Playscreen/Extras/parallax_middle.png", Texture.class),
                manager.get("Playscreen/Extras/parallax_fog.png", Texture.class),
                manager.get("Playscreen/Extras/fire_effect.png", Texture.class),
                manager.get("Playscreen/Characters/car.png", Texture.class),
                manager.get("Playscreen/Characters/car_lights.png", Texture.class),
                manager.get("Playscreen/Characters/monkey.png", Texture.class),
                manager.get("Playscreen/Characters/walker.png", Texture.class),
                manager.get("Playscreen/Characters/player.png", Texture.class),
                manager.get("Playscreen/Characters/player_ext.png", Texture.class),
                manager.get("Playscreen/Obstacles/hole_long.png", Texture.class),
                manager.get("Playscreen/Obstacles/hole_short.png", Texture.class),
                manager.get("Playscreen/Obstacles/island_long.png", Texture.class),
                manager.get("Playscreen/Obstacles/island_short.png", Texture.class),
                manager.get("Playscreen/Obstacles/stone_big.png", Texture.class),
                manager.get("Playscreen/Obstacles/stone_small.png", Texture.class)
        );
    }

    public void loadMenuAssets() {
        manager.clear();

        manager.load("Menuscreen/background.jpg", Texture.class);
        manager.load("Menuscreen/background_top.png", Texture.class);
        manager.load("Playscreen/Extras/parallax_fog.png", Texture.class);
        manager.load("Menuscreen/play.png", Texture.class);
        manager.load("Menuscreen/options.png", Texture.class);
        manager.load("Menuscreen/about.png", Texture.class);
        manager.load("Menuscreen/tutorial.png", Texture.class);
        manager.load("Menuscreen/run_frames.png", Texture.class);
        waitForLoadingToComplete();
        textureHolder = new TextureHolder(
                manager.get("Menuscreen/background.jpg", Texture.class),
                manager.get("Menuscreen/background_top.png", Texture.class),
                manager.get("Playscreen/Extras/parallax_fog.png", Texture.class),
                manager.get("Menuscreen/play.png", Texture.class),
                manager.get("Menuscreen/options.png", Texture.class),
                manager.get("Menuscreen/about.png", Texture.class),
                manager.get("Menuscreen/tutorial.png", Texture.class),
                manager.get("Menuscreen/run_frames.png", Texture.class)
        );
    }


    public void loadAboutAssets() {
        manager.clear();

        manager.load("Fonts/font_about.fnt", BitmapFont.class);
        manager.load("Aboutscreen/background.jpg", Texture.class);
        manager.load("Aboutscreen/return.png", Texture.class);
        waitForLoadingToComplete();
        textureHolder = new TextureHolder(
                manager.get("Aboutscreen/background.jpg", Texture.class),
                manager.get("Aboutscreen/return.png", Texture.class)
        );
    }

    public void loadOptionsAssets() {
        manager.clear();

        manager.load("Optionscreen/background.jpg", Texture.class);
        manager.load("Optionscreen/return.png", Texture.class);
        manager.load("Optionscreen/background.jpg", Texture.class);
        manager.load("Optionscreen/background.jpg", Texture.class);
        manager.load("Optionscreen/slider_background.png", Texture.class);
        manager.load("Optionscreen/knob.png", Texture.class);
        waitForLoadingToComplete();
        textureHolder = new TextureHolder(
                manager.get("Optionscreen/background.jpg", Texture.class),
                manager.get("Optionscreen/return.png", Texture.class),
                manager.get("Optionscreen/background.jpg", Texture.class),
                manager.get("Optionscreen/background.jpg", Texture.class),
                manager.get("Optionscreen/slider_background.png", Texture.class),
                manager.get("Optionscreen/knob.png", Texture.class)
        );
    }

    private void waitForLoadingToComplete() {
        while (!manager.update()) {
        }
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
