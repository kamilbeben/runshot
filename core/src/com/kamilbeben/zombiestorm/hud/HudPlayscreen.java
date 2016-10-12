package com.kamilbeben.zombiestorm.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    public HudPlayscreen(Zombie game) {
        viewport = new FitViewport(Zombie.WIDTH, Zombie.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        text = new TextPlayscreen(stage);
        ammoRenderer = new AmmoRenderer(game.assets.textureHolder);
    }

    public void update(float timer, float score, int bulletAmount) {
        text.update(timer, score);
        ammoRenderer.checkHowManyBullets(bulletAmount);
    }

    public void gameOver() {
        text.gameOver();
    }

    public void render(SpriteBatch batch) {
        stage.draw();
        batch.begin();
        ammoRenderer.render(batch);
        batch.end();
    }

    public void dispose() {
        stage.dispose();
    }
}
