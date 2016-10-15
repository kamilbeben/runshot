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

    private float score = 0f;
    private float distance = 0f;
    private int zombieKilled = 0;
    private int headshots = 0;

    public HudPlayscreen(Zombie game) {
        viewport = new FitViewport(Zombie.WIDTH, Zombie.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        text = new TextPlayscreen(stage);
        ammoRenderer = new AmmoRenderer(game.assets.textureHolder);
    }

    public void update(float timer, int bulletAmount) {
        text.update(timer, score);
        ammoRenderer.checkHowManyBullets(bulletAmount);
        setScore(timer);
    }

    private void setScore(float time) {
        distance = (3 * time) + ((time * time) / 200);
        score = distance + (zombieKilled * 10) + (headshots * 50);
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

    public void zombieGotShot() {

    }

    public void zombieGotHeadShot() {

    }

    public void dispose() {
        stage.dispose();
    }
}
