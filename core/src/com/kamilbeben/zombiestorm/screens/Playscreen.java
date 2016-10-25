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
import com.kamilbeben.zombiestorm.gamelogic.GameState;
import com.kamilbeben.zombiestorm.gamelogic.ObjectSpawner;
import com.kamilbeben.zombiestorm.gamelogic.Physics;
import com.kamilbeben.zombiestorm.gamelogic.Shotgun;
import com.kamilbeben.zombiestorm.graphicalfireworks.GraphicsOverlay;
import com.kamilbeben.zombiestorm.hud.HudPlayscreen;
import com.kamilbeben.zombiestorm.obstacles.Hole;
import com.kamilbeben.zombiestorm.obstacles.Island;
import com.kamilbeben.zombiestorm.obstacles.HoleLong;
import com.kamilbeben.zombiestorm.obstacles.HoleShort;
import com.kamilbeben.zombiestorm.obstacles.IslandLong;
import com.kamilbeben.zombiestorm.obstacles.IslandShort;
import com.kamilbeben.zombiestorm.obstacles.Obstacle;
import com.kamilbeben.zombiestorm.obstacles.StoneBig;
import com.kamilbeben.zombiestorm.obstacles.StoneSmall;
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
    private GraphicsOverlay graphicsOverlay;

    public GameState state = new GameState();

    private Physics physics;
    private Player player;
    private List<Enemy> enemies;
    private List<Hole> holes;
    private List<Obstacle> obstacles;
    private ObjectSpawner objectSpawner;
    private Timer timer = new Timer();

    private Shotgun shotgun;

    private int speedlLevel = 1;

    private boolean gameOverNotCalledYet = true;

    //TEST ONLY
    private boolean spawnEnemies = true;
    private boolean debugMode = false;
    private boolean debugAndNormal = false;




    public Playscreen(Zombie game, boolean clearManager) {
        this.game = game;
        game.assets.loadPlayscreenAssets(clearManager);
        worldRenderer = new WorldRenderer(game.assets.textureHolder);
        hud = new HudPlayscreen(game);
        setupCamera();
        physics = new Physics();
        setupLists();
        objectSpawner = new ObjectSpawner(enemies, holes, obstacles, physics.world, game.assets.textureHolder);
        graphicsOverlay = new GraphicsOverlay(physics.world, game.assets.textureHolder);
        shotgun = new Shotgun(game.assets.textureHolder.GAME_EXTRAS_FIRE_EFFECT);
        game.enableAndroidBackKey();
        game.enableAndroidMenuKey();
    }

    private void setupLists() {
        player = new Player(physics.world, game.assets.textureHolder);
        enemies = new ArrayList<Enemy>();
        holes = new ArrayList<Hole>();
        obstacles = new ArrayList<Obstacle>();
    }

    private void setupCamera() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(Zombie.WIDTH / Zombie.PPM, Zombie.HEIGHT / Zombie.PPM, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }

    private void update(float delta) {
        handleInput();
        physics.update(delta, player);
        player.update(delta);
        updateEnemies(delta);

        if (state.isGoing()) {
            timer.updateTimer(delta);
            updateHolesAndIslands(delta);
            shotgun.update(delta);
            worldRenderer.updateGroundAndBackgroundAnimation(timer.getTime(), delta);
            if (spawnEnemies) {
                objectSpawner.update(timer);
            }
        }
        checkForGameOver();
        updateSpeedLevel();
        hud.update(timer.getTime(), player.getBulletsAmount());
        graphicsOverlay.update(timer.getTime());
        camera.update();
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
        for (Obstacle tmp : obstacles) {
            tmp.setSpeedLevel(speedlLevel);
        }
        player.setSpeedLevel(speedlLevel);
        worldRenderer.setSpeedLevel(speedlLevel);
    }

    private void updateEnemies(float delta) {
        for (Enemy tmp : enemies) {
            tmp.update(delta);
        }
    }

    private void updateHolesAndIslands(float delta) {
        for (Hole tmp : holes) {
            tmp.update(delta);
        }
        for (Obstacle tmp : obstacles) {
            tmp.update(delta);
        }
    }


    private void checkForGameOver() {
        if (!player.isAlive() && gameOverNotCalledYet) {
            gameOver();
            gameOverNotCalledYet = false;
        }
    }

    private void gameOver() {
        state.setOver();
        for (Obstacle tmp : obstacles) {
            tmp.stopMoving();
        }
        worldRenderer.stopAnimating();
        hud.gameOver(timer.getTime());
        timer.setGameOver();
    }

    private void shotgunShot() {
        if (player.shotgunShot()) {
            shotgun.shot(enemies, player, hud);
        }
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        testKeyboard();
        if (!state.isPause()) {
            update(delta);
            Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            game.batch.setProjectionMatrix(camera.combined);

            if (!debugMode) {
                game.batch.begin();
                worldRenderer.draw(game.batch);

                for (Enemy tmp : enemies) {
                    tmp.render(game.batch);
                }
                for (Obstacle tmp : obstacles) {
                    tmp.render(game.batch);
                }

                for (Hole tmp : holes)
                    tmp.render(game.batch);

                shotgun.render(game.batch);

                player.render(game.batch);

                graphicsOverlay.render(game.batch);
                game.batch.end();
                hud.render(game.batch);

                if (debugAndNormal) {
                    physics.renderDebug(camera);
                }

            }
            else {
                physics.renderDebug(camera);
                hud.render(game.batch);
            }
        }
    }

    private void handleInput() {

        if (state.isOver() && timer.canIReadInputAfterGameOver() && (Gdx.input.justTouched())) {
            game.setScreen(new Playscreen(game, false));
        } else if (state.isNotReady() && Gdx.input.justTouched()) {
            state.setGoing();
            player.startMoving();
        } else if (state.isGoing()) {
            if ((Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))&&
                    Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
                shotgunShot();
            }

            if (((Gdx.input.isKeyJustPressed(Input.Keys.UP)) || (Gdx.input.justTouched() &&
                    Gdx.input.getX() < Gdx.graphics.getWidth() / 2)) && physics.canPlayerJump()) {
                player.jumpFirst();
            } else if (((Gdx.input.isKeyJustPressed(Input.Keys.UP)) || (Gdx.input.justTouched() &&
                    Gdx.input.getX() < Gdx.graphics.getWidth() / 2)) && player.isJumping()) {
                player.jumpSecond();
            }
        }
    }

    private void testKeyboard() {
        float enemiesPosition = 1200;
        float holesPosition = 1200;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            enemies.add(new Walker(physics.world, enemiesPosition, 200, timer.getSpeedLevel(), game.assets.textureHolder.GAME_ENEMY_WALKER));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            enemies.add(new Monkey(physics.world, enemiesPosition, 200, timer.getSpeedLevel(), game.assets.textureHolder.GAME_ENEMY_MONKEY));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            enemies.add(new Car(physics.world, enemiesPosition + 100, 200, timer.getSpeedLevel(), game.assets.textureHolder.GAME_ENEMY_CAR, game.assets.textureHolder.GAME_ENEMY_CAR_LIGHTS));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            holes.add(new HoleShort(physics.world, holesPosition, 100, timer.getSpeedLevel(), game.assets.textureHolder.GAME_OBSTACLE_HOLE_SHORT));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            holes.add(new HoleLong(physics.world, holesPosition, 100, timer.getSpeedLevel(), game.assets.textureHolder.GAME_OBSTACLE_HOLE_LONG));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
            obstacles.add(new IslandLong(physics.world, holesPosition, 260, timer.getSpeedLevel(), game.assets.textureHolder));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
            obstacles.add(new StoneSmall(physics.world, 1200, 128, timer.getSpeedLevel(), game.assets.textureHolder));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
            obstacles.add(new StoneBig(physics.world, 1200, 128, timer.getSpeedLevel(), game.assets.textureHolder));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.PLUS)) {
            timer.addTenSeconds();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            player.pickAmmo();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P) || Gdx.input.isKeyJustPressed(Input.Keys.MENU)) {
            if (state.isPause()) {
                state.unpause();
            } else {
                state.setPause();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            if (spawnEnemies) {
                spawnEnemies = false;
            } else {
                spawnEnemies = true;
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
            if (debugAndNormal) {
                debugAndNormal = false;
            } else {
                debugAndNormal = true;
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
