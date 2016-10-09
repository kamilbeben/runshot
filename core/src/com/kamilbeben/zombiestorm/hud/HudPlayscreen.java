package com.kamilbeben.zombiestorm.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 08.10.16.
 */
public class HudPlayscreen {

    private Stage stage;
    private Viewport viewport;

    private TextPlayscreen text;

    public HudPlayscreen(SpriteBatch batch) {
        viewport = new FitViewport(Zombie.WIDTH, Zombie.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        text = new TextPlayscreen(stage);
    }

    public void update(float timer, float score) {
        text.update(timer, score);
    }

    public void gameOver() {
        text.gameOver();
    }

    public void render() {
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}
