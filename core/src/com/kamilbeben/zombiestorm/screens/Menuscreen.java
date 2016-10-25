package com.kamilbeben.zombiestorm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.graphicalfireworks.ParallaxBackground;
import com.kamilbeben.zombiestorm.hud.MenuButton;

/**
 * Created by bezik on 21.10.16.
 */
public class Menuscreen implements Screen {


    private OrthographicCamera camera = new OrthographicCamera();
    private Viewport viewport;
    private Zombie game;

    public Stage stage;
    private static final int buttonSpacing = 48;
    private MenuButton buttonPlay;
    private MenuButton buttonOptions;
    private MenuButton buttonAbout;
    private MenuButton buttonTutorial;

    private Sprite background_bot;
    private ParallaxBackground fog;
    private Sprite background_top;

    private Sprite runningSprite;
    private Animation animation;
    private float animationTimer = 0f;

    public Menuscreen(Zombie game) {
        this.game = game;
        game.assets.loadMenuAssets();
        setupCamera();
        background_bot = new Sprite(game.assets.textureHolder.MENU_BACKGROUND_BOT);
        background_top = new Sprite(game.assets.textureHolder.MENU_BACKGROUND_TOP);
        fog = new ParallaxBackground(game.assets.textureHolder.MENU_PARALLAX_FOG, 25, false);
        setupStage();
        setupAnimation(game.assets.textureHolder.MENU_PLAYER_ANIMATION);
    }


    private void setupCamera() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(Zombie.WIDTH / Zombie.PPM, Zombie.HEIGHT / Zombie.PPM, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }

    private void setupStage() {
        camera = new OrthographicCamera(Zombie.WIDTH, Zombie.HEIGHT);
        camera.translate(camera.viewportWidth/2, camera.viewportHeight/2);
        viewport = new FitViewport(Zombie.WIDTH, Zombie.HEIGHT, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        addActors();
    }

    private void setupAnimation(Texture texture) {
        runningSprite = new Sprite(texture);
        runningSprite.setBounds(0, 0, 120, 146);
        runningSprite.setPosition((Zombie.WIDTH - 120)/2, 24);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(runningSprite.getTexture(), i * 120, 0, 120, 146));
        }
        animation = new Animation(0.1f, frames);
        frames.clear();
    }

    private void addActors() {
        buttonPlay = new MenuButton(stage, new Vector2(197, 204),
                game.assets.textureHolder.MENU_PLAY);

        buttonTutorial = new MenuButton(stage, new Vector2(124, 116),
                game.assets.textureHolder.MENU_TUTORIAL);

        buttonOptions = new MenuButton(stage, new Vector2(566, 204),
                game.assets.textureHolder.MENU_OPTIONS);

        buttonAbout = new MenuButton(stage, new Vector2(568, 116),
                game.assets.textureHolder.MENU_ABOUT);

    }

    public void update(float delta) {
        handleUserInput();
        fog.update(delta);
        camera.update();
        updateAnimation(delta);
    }

    public void handleUserInput() {
        if ( buttonPlay.clicked ) {
            game.setScreen(new Playscreen(game, true));
            dispose();
        }
        if ( buttonOptions.clicked ) {
            game.setScreen(new Optionscreen(game));
            dispose();
        }
        if ( buttonAbout.clicked ) {
            game.setScreen(new Aboutscreen(game));
            dispose();
        }
    }

    @Override
    public void show() {

    }

    private void updateAnimation(float delta) {
        runningSprite.setRegion(getFrame(delta));
    }


    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        region = animation.getKeyFrame(animationTimer, true);
        animationTimer += delta;
        return region;
    }


    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        background_bot.draw(game.batch);
        fog.render(game.batch);
        background_top.draw(game.batch);
        runningSprite.draw(game.batch);
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
    }
}
