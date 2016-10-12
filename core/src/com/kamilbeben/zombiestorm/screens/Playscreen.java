package com.kamilbeben.zombiestorm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.characters.Car;
import com.kamilbeben.zombiestorm.characters.Enemy;
import com.kamilbeben.zombiestorm.characters.Monkey;
import com.kamilbeben.zombiestorm.characters.Player;
import com.kamilbeben.zombiestorm.characters.Walker;
import com.kamilbeben.zombiestorm.gamelogic.ObjectSpawner;
import com.kamilbeben.zombiestorm.gamelogic.Physics;
import com.kamilbeben.zombiestorm.hud.HudPlayscreen;
import com.kamilbeben.zombiestorm.obstacles.Hole;
import com.kamilbeben.zombiestorm.obstacles.Island;
import com.kamilbeben.zombiestorm.obstacles.HoleLong;
import com.kamilbeben.zombiestorm.obstacles.HoleShort;
import com.kamilbeben.zombiestorm.tools.Tools;
import com.kamilbeben.zombiestorm.tools.WorldRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bezik on 17.09.16.
 */
public class Playscreen implements Screen {

    private Zombie game;

    private OrthographicCamera camera = new OrthographicCamera();
    private Viewport viewport;
    private WorldRenderer worldRenderer = new WorldRenderer();
    private HudPlayscreen hud;

    private Physics physics;
    private Player player;
    private List<Enemy> enemies;
    private List<Hole> holes;
    private List<Island> islands;
    private ObjectSpawner objectSpawner;

    private float score = 0f;
    private float distance = 0f;
    private Tools timer = new Tools();
    private boolean playerIsFalling = false;
    private boolean gameOver = false;

    public Playscreen(Zombie game) {
        this.game = game;
        hud = new HudPlayscreen(game.batch);
        setupCamera();
        physics = new Physics();
        player = new Player(physics.world);
        enemies = new ArrayList<Enemy>();
        holes = new ArrayList<Hole>();
        islands = new ArrayList<Island>();
        objectSpawner = new ObjectSpawner(enemies, holes, islands, physics.world);
    }

    private void setupCamera() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(Zombie.WIDTH / Zombie.PPM, Zombie.HEIGHT / Zombie.PPM, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }

    @Override
    public void show() {

    }

    private void update(float delta) {
        physics.update(delta);
        handleInput();
        if (physics.playerCollidesWithLeftWall()) {
            player.dead();
        }
        if (playerIsFalling) {
            player.collisionsOff();
        }

        player.update(delta);
        updateEnemies(delta);

        if (!gameOver) {
            timer.updateTimer(delta);
            setScore(timer.getTime());
            updateHolesAndIslands(delta);
        }
        hud.update(timer.getTime(), score, player.getBulletsAmount());
        worldRenderer.updateAnimation(timer.getTime());
        camera.update();
        checkForGameOver();
        objectSpawner.update(timer);
    }

    private void updateEnemies(float delta) {
        for (Enemy tmp : enemies) {
            tmp.update(delta);
            tmp.disposeIfOutOfMap(physics.world);
            if (tmp.gotShot()) {
                player.zombieGotShot();
            }
        }
    }

    private void updateHolesAndIslands(float delta) {
        for (Hole tmp : holes) {
            tmp.update(delta);
            if (tmp.isPlayerAboveHole()) {
                playerIsFalling = true;
            }
            if (tmp.isHoleOnScreen()) {
                worldRenderer.updateGround(tmp.getStartTileAndNumberOfTiles());
            }
        }
        for (Island tmp : islands) {
            tmp.update(delta);
        }
    }

    private void setScore(float time) {
        distance = (3 * time) + ((time * time) / 200);
        score = distance + (player.howManyZombiesDidIKilled() * 10);
    }


    private void checkForGameOver() {
        if (!player.isAlive()) {
            gameOver();
        }
    }

    private void gameOver() {
        gameOver = true;
        worldRenderer.stopAnimating();
        hud.gameOver();
    }

    private void handleInput() {
        if (Gdx.input.justTouched() && timer.isItTimeToShootSomething() &&
                Gdx.input.getX() > Gdx.graphics.getWidth()/2) {
            shotgunShot((int) (Gdx.input.getY() * (Zombie.HEIGHT)) / viewport.getScreenHeight());
        }
        if (((Gdx.input.isKeyJustPressed(Input.Keys.UP)) || (Gdx.input.justTouched() &&
                Gdx.input.getX() < Gdx.graphics.getWidth()/2) ) && physics.canPlayerJump() && !playerIsFalling) {
            player.jump();
        }
        if ((Gdx.input.isKeyJustPressed(Input.Keys.R) || Gdx.input.justTouched()) && gameOver) {
            game.setScreen(new Playscreen(game));
        }
        testEnemiesSpawnWithKeyboard();
    }

    private void testEnemiesSpawnWithKeyboard() {
        float enemiesPosition = 700;
        float holesPosition = 500;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            enemies.add(new Walker(physics.world, enemiesPosition, 200, timer.getTime()));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            enemies.add(new Monkey(physics.world, enemiesPosition, 200, timer.getTime()));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            enemies.add(new Car(physics.world, enemiesPosition, 200, timer.getTime()));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            holes.add(new HoleShort(physics.world, holesPosition, 200, timer.getTime()));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            holes.add(new HoleLong(physics.world, holesPosition, 200, timer.getTime()));
        }
    }

    private void shotgunShot( int yAxis) {
        if (player.shotgunShot()) {
            physics.shotgunShot(yAxis, player.getPosition().y);
        }
    }


    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        worldRenderer.draw(game.batch);

        player.render(game.batch);
        for (Enemy tmp : enemies) {
            tmp.render(game.batch);
        }
        for (Island tmp : islands) {
            tmp.render(game.batch);
        }

        for (Hole tmp : holes)
            tmp.render(game.batch);

        game.batch.end();

        hud.render();
        physics.renderDebug(camera);

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
        hud.dispose();
        player.dispose();
        for (Enemy tmp : enemies) {
            tmp.dispose(physics.world);
        }
        physics.dispose();
    }
}
