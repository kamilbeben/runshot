package com.kamilbeben.zombiestorm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 05.11.16.
 */
public class Loadscreen implements Screen{

    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;

    private Sprite background;
    private Zombie game;

    private ProgressBar progressBar;

    public Loadscreen(Zombie game) {
        this.game = game;
        game.assets.loadLoadingscreenAssets();
        background = new Sprite(game.assets.textureHolder.LOADING_BACKGROUND);
        background.setSize(background.getTexture().getWidth(), background.getTexture().getHeight());
        background.setPosition(0, 0);

        setupStage();
        setupProgressBar();

        game.assets.loadPlayscreenTextures();
        game.assets.loadPlaySounds();
    }

    private void setupStage() {
        camera = new OrthographicCamera(game.WIDTH, game.HEIGHT);
        camera.translate(camera.viewportWidth/2, camera.viewportHeight/2);
        viewport = new FitViewport(game.WIDTH, game.HEIGHT, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    }

    private void setupProgressBar() {
        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
        style.background = new Image(game.assets.textureHolder.LOADING_PROGRESS_BG).getDrawable();
        style.knob = new Image(game.assets.textureHolder.LOADING_PROGRESS_KNOB).getDrawable();
        style.knobAfter = new Image(game.assets.textureHolder.LOADING_PROGRESS_KNOB).getDrawable();
        progressBar = new ProgressBar(0f, 1f, 0.1f, false, style);
        progressBar.setSize(300f, 10f);
        progressBar.setPosition((Zombie.WIDTH - progressBar.getWidth()) /2, (Zombie.HEIGHT - progressBar.getHeight()) /2 - 30f);
        stage.addActor(progressBar);
    }

    @Override
    public void show() {

    }

    private void update() {
        progressBar.setValue(game.assets.getProgress());
        if (game.assets.loadPlayscreenComplete()) {
            game.assets.assignPlayscreenTextures();
            game.setScreen(new Playscreen(game));
        }
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        background.draw(game.batch);
        progressBar.draw(game.batch, 1f);
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
