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
import com.kamilbeben.zombiestorm.graphicalfireworks.GDXRenderer;
import com.kamilbeben.zombiestorm.hud.HudPlayscreen;
import com.kamilbeben.zombiestorm.obstacles.Hole;
import com.kamilbeben.zombiestorm.obstacles.Island;
import com.kamilbeben.zombiestorm.obstacles.HoleLong;
import com.kamilbeben.zombiestorm.obstacles.HoleShort;
import com.kamilbeben.zombiestorm.obstacles.IslandLong;
import com.kamilbeben.zombiestorm.obstacles.IslandShort;
import com.kamilbeben.zombiestorm.tools.Timer;
import com.kamilbeben.zombiestorm.graphicalfireworks.WorldRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bezik on 17.09.16.
 */
public class Playscreen implements Screen {

    private Zombie game;

    private OrthographicCamera camera = new OrthographicCamera();
    private Viewport viewport;
    private WorldRenderer worldRenderer;
    public HudPlayscreen hud;
    private GDXRenderer gdxRenderer;

    private Physics physics;
    private Player player;
    private List<Enemy> enemies;
    private List<Hole> holes;
    private List<Island> islands;
    private ObjectSpawner objectSpawner;
    private Timer timer = new Timer();

    private int speedlLevel = 1;
    private boolean playerIsFallingThroughHole = false;
    private boolean gameOver = false;

    private boolean pause = false;
    private boolean debugMode = false;
    private boolean GFXFireworks = true;




    public Playscreen(Zombie game, boolean clearManager) {
        this.game = game;
        game.assets.loadPlayscreenAssets(clearManager);
        worldRenderer = new WorldRenderer(game.assets.textureHolder);
        hud = new HudPlayscreen(game);
        setupCamera();
        physics = new Physics();
        player = new Player(physics.world, game.assets.textureHolder);
        enemies = new ArrayList<Enemy>();
        holes = new ArrayList<Hole>();
        islands = new ArrayList<Island>();
        objectSpawner = new ObjectSpawner(enemies, holes, islands, physics.world, game.assets.textureHolder);
        gdxRenderer = new GDXRenderer(physics.world, game.assets.textureHolder);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
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
        handleInput();
        updateSpeedLevel();
        physics.update(delta);
        if (physics.playerCollidesWithLeftWall()) {
            player.dead();
        }
        if (playerIsFallingThroughHole) {
            player.playerFallingDown();
        }

        player.update(delta);
        updateEnemies(delta);

        if (!gameOver) {
            timer.updateTimer(delta);
            updateHolesAndIslands(delta);
        }
        hud.update(timer.getTime(), player.getBulletsAmount());
        worldRenderer.updateGroundAndBackgroundAnimation(timer.getTime(), delta);
        camera.update();
        checkForGameOver();
//        objectSpawner.update(timer);
        gdxRenderer.update(delta);
    }

    private void updateSpeedLevel() {
        if (speedlLevel != timer.getSpeedLevel()) {
            speedlLevel = timer.getSpeedLevel();
            updateObjectsSpeed();
        }
    }

    private void updateObjectsSpeed() {
        for (Enemy tmp : enemies) {
            tmp.setSpeedLevel(speedlLevel);
        }
        for (Hole tmp : holes) {
            tmp.setSpeedLevel(speedlLevel);
        }
        for (Island tmp : islands) {
            tmp.setSpeedLevel(speedlLevel);
        }
        player.setSpeedLevel(speedlLevel);
        worldRenderer.setSpeedLevel(speedlLevel);
    }

    private void updateEnemies(float delta) {
        for (Enemy tmp : enemies) {
            tmp.update(delta, this);
        }
    }

    private void updateHolesAndIslands(float delta) {
        for (Hole tmp : holes) {
            tmp.update(delta);
            if (tmp.isPlayerAboveHole()) {
                playerIsFallingThroughHole = true;
            }
            if (tmp.isHoleOnScreen()) {
                worldRenderer.updateGround(tmp.getStartTileAndNumberOfTiles());
            }
        }
        for (Island tmp : islands) {
            tmp.update(delta);
        }
    }


    private void checkForGameOver() {
        if (!player.isAlive()) {
            gameOver();
        }
    }

    private void gameOver() {
        for (Island tmp : islands) {
            tmp.stopMoving();
        }
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
                Gdx.input.getX() < Gdx.graphics.getWidth()/2) ) && physics.canPlayerJump() && !playerIsFallingThroughHole) {
            player.jump();
        }
        if ((Gdx.input.isKeyJustPressed(Input.Keys.R) || Gdx.input.justTouched()) && gameOver) {
            game.setScreen(new Playscreen(game, false));
        }
    }

    private void testKeyboard() {
        float enemiesPosition = 900;
        float holesPosition = 1200;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            enemies.add(new Walker(physics.world, enemiesPosition, 200, timer.getSpeedLevel(), game.assets.textureHolder.GAME_ENEMY_WALKER));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            enemies.add(new Monkey(physics.world, enemiesPosition, 200, timer.getSpeedLevel(), game.assets.textureHolder.GAME_ENEMY_MONKEY));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            enemies.add(new Car(physics.world, enemiesPosition + 100, 200, timer.getSpeedLevel(), game.assets.textureHolder.GAME_ENEMY_CAR));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            holes.add(new HoleShort(physics.world, holesPosition, 100, timer.getSpeedLevel(), game.assets.textureHolder.GAME_OBSTACLE_HOLE_SHORT));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            holes.add(new HoleLong(physics.world, holesPosition, 100, timer.getSpeedLevel(), game.assets.textureHolder.GAME_OBSTACLE_HOLE_LONG));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
            islands.add(new IslandLong(physics.world, holesPosition, 260, timer.getSpeedLevel(), game.assets.textureHolder));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
            islands.add(new IslandShort(physics.world, holesPosition, 260, timer.getSpeedLevel(), game.assets.textureHolder));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
            player.pickAmmo();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.PLUS)) {
            timer.addTenSeconds();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P) || Gdx.input.isKeyJustPressed(Input.Keys.MENU)) {
            if (pause) {
                pause = false;
            } else {
                pause = true;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            if (debugMode) {
                debugMode = false;
            } else {
                debugMode = true;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            if (GFXFireworks) {
                GFXFireworks = false;
            } else {
                GFXFireworks = true;
            }
        }
    }

    private void shotgunShot( int yAxis) {
        if (player.shotgunShot()) {
            physics.shotgunShot(yAxis, player.getPosition().y);
        }
    }


    @Override
    public void render(float delta) {
        testKeyboard();
        if (!pause) {
            update(delta);
            Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            game.batch.setProjectionMatrix(camera.combined);

            if (!debugMode) {
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

                if (GFXFireworks) {
                    gdxRenderer.render(game.batch);
                }
                game.batch.end();
                hud.render(game.batch);

                physics.renderDebug(camera);

            }
            else {
                physics.renderDebug(camera);
                hud.render(game.batch);
            }
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
        hud.dispose();
        player.dispose();
        for (Enemy tmp : enemies) {
            tmp.dispose(physics.world);
        }
        physics.dispose();
//        shadowRenderer.dispose();
    }
}
