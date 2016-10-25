package com.kamilbeben.zombiestorm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.Vector2;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.hud.MenuButton;

/**
 * Created by bezik on 25.10.16.
 */
public class Optionscreen implements Screen {
    private Zombie game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;

    private Sprite background;
    private static final float itemSpacing = 28;
    private static final float adHeight = 50;

    private MenuButton buttonReturn;


    public Optionscreen(Zombie game) {
        this.game = game;
        game.assets.loadOptionsAssets();
        initializeSprites();
        initializeStage();
        game.enableAndroidBackKey();
    }

    private void initializeSprites() {
        background = new Sprite(game.assets.textureHolder.OPTIONS_BACKGROUND);
    }

    private void initializeStage() {
        camera = new OrthographicCamera(game.WIDTH, game.HEIGHT);
        camera.translate(camera.viewportWidth/2, camera.viewportHeight/2);
        viewport = new FitViewport(game.WIDTH, game.HEIGHT, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        addActors();
    }

    private void addActors() {
        initializeReturnButton();
    }

    private void initializeReturnButton() {
        buttonReturn = new MenuButton(stage, new Vector2(8, 8),
                game.assets.textureHolder.OPTIONS_RETURN);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        handleUserInput();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        drawSprites(game.batch);
        game.batch.end();
        stage.draw();
    }

    private void drawSprites(Batch batch) {
        background.draw(batch);
    }

    public void handleUserInput() {


        if (buttonReturn.clicked || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.setScreen(new Menuscreen(game));
            dispose();
        }

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
        stage.dispose();
    }
}
