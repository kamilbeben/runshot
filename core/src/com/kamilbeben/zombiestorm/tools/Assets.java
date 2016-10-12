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

    public void loadPlayscreenAssets() {
        manager.clear();
        manager.load("Playscreen/Hud/ammoBG.png", Texture.class);
        manager.load("Playscreen/Hud/ammoOFF.png", Texture.class);
        manager.load("Playscreen/Hud/ammoON.png", Texture.class);
        manager.load("Playscreen/Extras/ammopack.png", Texture.class);
        manager.load("Playscreen/Extras/grass_animation.png", Texture.class);
        manager.load("Playscreen/Characters/car.png", Texture.class);
        manager.load("Playscreen/Characters/monkey.png", Texture.class);
        manager.load("Playscreen/Characters/walker.png", Texture.class);
        manager.load("Playscreen/Characters/player.png", Texture.class);
        manager.load("Playscreen/Obstacles/hole_long.png", Texture.class);
        manager.load("Playscreen/Obstacles/hole_short.png", Texture.class);
        manager.load("Playscreen/Obstacles/island_long.png", Texture.class);
        manager.load("Playscreen/Obstacles/island_short.png", Texture.class);
        waitForLoadingToComplete();
        textureHolder = new TextureHolder(
                manager.get("Playscreen/Hud/ammoBG.png", Texture.class),
                manager.get("Playscreen/Hud/ammoON.png", Texture.class),
                manager.get("Playscreen/Hud/ammoOFF.png", Texture.class),
                manager.get("Playscreen/Extras/ammopack.png", Texture.class),
                manager.get("Playscreen/Extras/grass_animation.png", Texture.class),
                manager.get("Playscreen/Characters/car.png", Texture.class),
                manager.get("Playscreen/Characters/monkey.png", Texture.class),
                manager.get("Playscreen/Characters/walker.png", Texture.class),
                manager.get("Playscreen/Characters/player.png", Texture.class),
                manager.get("Playscreen/Obstacles/hole_long.png", Texture.class),
                manager.get("Playscreen/Obstacles/hole_short.png", Texture.class),
                manager.get("Playscreen/Obstacles/island_long.png", Texture.class),
                manager.get("Playscreen/Obstacles/island_short.png", Texture.class)
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
