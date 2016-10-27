package com.kamilbeben.zombiestorm.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.Assets;

/**
 * Created by bezik on 08.10.16.
 */
public class HudPlayscreen {

    private Stage stage;
    private Viewport viewport;
    private AmmoRenderer ammoRenderer;

    private TextPlayscreen text;

    private int zombieKilled = 0;

    private Sprite pause;


    public HudPlayscreen(Zombie game) {
        viewport = new FitViewport(Zombie.WIDTH, Zombie.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        text = new TextPlayscreen(stage, game.assets.manager.get("Fonts/font.fnt", BitmapFont.class));
        Gdx.input.setInputProcessor(stage);
        ammoRenderer = new AmmoRenderer(game.assets.textureHolder);
        setupPauseSprite(game);
    }

    private void setupPauseSprite(Zombie game) {
        pause = new Sprite(game.assets.textureHolder.HUD_PAUSE);
        pause.setPosition(16, Zombie.HEIGHT - pause.getHeight() - 16);
    }

    public void update(float timer, int bulletAmount) {
        text.update(calculateDistance(timer));
        ammoRenderer.checkHowManyBullets(bulletAmount);

    }

    private float calculateDistance(float time) {
        return (3 * time) + ((time * time) / 200);
    }

    public void gameOver(float time) {
        text.gameOver(calculateDistance(time), zombieKilled);
    }

    public void render(SpriteBatch batch) {
        stage.draw();
        batch.begin();
        pause.draw(batch);
        ammoRenderer.render(batch);
        batch.end();
    }

    public void unpause() {
        text.unpause();
    }

    public void pause() {
        text.pause();
    }

    public void zombieGotShot() {
        zombieKilled++;
    }

    public void dispose() {
        stage.dispose();
    }
}
