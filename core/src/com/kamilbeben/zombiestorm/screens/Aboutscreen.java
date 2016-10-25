package com.kamilbeben.zombiestorm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.hud.MenuButton;
import com.kamilbeben.zombiestorm.hud.AboutText;

/**
 * Created by bezik on 25.10.16.
 */
public class Aboutscreen implements Screen {

    private Zombie game;
    private OrthographicCamera camera;
    private Viewport viewport;
    public Stage stage;

    private Sprite background;
    private MenuButton buttonReturn;

    private AboutText text;

    public Aboutscreen(Zombie game) {
        this.game = game;
        game.assets.loadAboutAssets();
        initializeBackground();
        initializeStage();
        initializeText();
        game.enableAndroidBackKey();
    }

    private void initializeBackground() {
        background = new Sprite(game.assets.textureHolder.ABOUT_BACKGROUND);
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
                game.assets.textureHolder.ABOUT_RETURN);
    }



    public void handleUserInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || buttonReturn.isClicked()) {
            game.setScreen(new Menuscreen(game));
            dispose();
        }

    }

    @Override
    public void show() {

    }

    private void initializeText() {
        text = new AboutText(game.assets.manager.get("Fonts/font_about.fnt", BitmapFont.class));
    }

    @Override
    public void render(float delta) {
        handleUserInput();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        background.draw(game.batch);
        text.render(game.batch);
        game.batch.end();

        stage.draw();
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
        text.dispose();
    }

}
