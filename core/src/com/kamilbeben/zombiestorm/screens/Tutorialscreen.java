package com.kamilbeben.zombiestorm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 06.11.16.
 */
public class Tutorialscreen implements Screen {

    private Viewport viewport;

    private Sprite background;
    private Zombie game;

    private int clickCounter = 0;

    public Tutorialscreen(Zombie game) {
        this.game = game;
        game.assets.loadTutorialAssets();
        background = new Sprite(game.assets.textureHolder.TUTORIAL[0]);
        background.setSize(background.getTexture().getWidth(), background.getTexture().getHeight());
        background.setPosition(0, 0);
        setupViewport();
    }

    private void setupViewport() {
        viewport = new FitViewport(game.WIDTH, game.HEIGHT);
    }

    @Override
    public void show() {

    }

    private void update() {
        if (Gdx.input.justTouched()) {
            clickCounter++;
            if (clickCounter < game.assets.textureHolder.TUTORIAL.length) {
                background.setTexture(game.assets.textureHolder.TUTORIAL[clickCounter]);
            } else {
                game.setScreen(new Menuscreen(game));
            }
        }
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        background.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

}
