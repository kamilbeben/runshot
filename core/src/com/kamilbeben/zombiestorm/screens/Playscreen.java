package com.kamilbeben.zombiestorm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.characters.Enemy;
import com.kamilbeben.zombiestorm.characters.Player;
import com.kamilbeben.zombiestorm.gamelogic.GameState;
import com.kamilbeben.zombiestorm.gamelogic.ObjectSpawner;
import com.kamilbeben.zombiestorm.gamelogic.Physics;
import com.kamilbeben.zombiestorm.gamelogic.Shotgun;
import com.kamilbeben.zombiestorm.graphicalfireworks.GraphicsOverlay;
import com.kamilbeben.zombiestorm.hud.HudPlayscreen;
import com.kamilbeben.zombiestorm.objects.SingleShell;
import com.kamilbeben.zombiestorm.obstacles.Hole;
import com.kamilbeben.zombiestorm.obstacles.Obstacle;
import com.kamilbeben.zombiestorm.tools.InputHandler;
import com.kamilbeben.zombiestorm.tools.Timer;
import com.kamilbeben.zombiestorm.graphicalfireworks.WorldRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bezik on 17.09.16.
 */
public class Playscreen implements Screen {

    private Physics physics;
    private Zombie game;
    private GameState state;
    private Timer timer = new Timer();

    private List<Enemy> enemies;
    private List<Hole> holes;
    private List<Obstacle> obstacles;
    private List<SingleShell> singleShells;
    private ObjectSpawner objectSpawner;
    private Player player;
    private Shotgun shotgun;

    private OrthographicCamera camera;
    private Viewport viewport;
    private HudPlayscreen hud;
    private WorldRenderer worldRenderer;
    private GraphicsOverlay graphicsOverlay;

    private int speedlLevel = 1;
    private boolean gameOverNotCalledYet = true;

    public Playscreen(Zombie game, boolean clearManager) {
        state = new GameState();
        this.game = game;
        game.assets.loadPlayscreenAssets(clearManager);
        worldRenderer = new WorldRenderer(game.assets.textureHolder);
        hud = new HudPlayscreen(game);
        setupCamera();
        physics = new Physics();
        setupLists();
        objectSpawner = new ObjectSpawner(enemies, holes, obstacles, singleShells, physics.world, game.assets.textureHolder);
        graphicsOverlay = new GraphicsOverlay(physics.world, game.assets.textureHolder);
        shotgun = new Shotgun(game.assets.textureHolder.GAME_EXTRAS_FIRE_EFFECT);
        Zombie.enableAndroidBackKey();
        Zombie.enableAndroidMenuKey();
    }

    private void setupLists() {
        player = new Player(physics.world, game.assets.textureHolder);
        enemies = new ArrayList<Enemy>();
        holes = new ArrayList<Hole>();
        obstacles = new ArrayList<Obstacle>();
        singleShells = new ArrayList<SingleShell>();
    }

    private void setupCamera() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(Zombie.WIDTH / Zombie.PPM, Zombie.HEIGHT / Zombie.PPM, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        worldRenderer.draw(game.batch);
        for (Enemy tmp : enemies) {
            tmp.render(game.batch);
        }
        for (Obstacle tmp : obstacles) {
            tmp.render(game.batch);
        }
        for (Hole tmp : holes) {
            tmp.render(game.batch);
        }
        for (SingleShell tmp : singleShells) {
            tmp.render(game.batch);
        }
        shotgun.render(game.batch);
        player.render(game.batch);
        graphicsOverlay.render(game.batch);
        game.batch.end();
        physics.renderDebug(camera);
        hud.render(game.batch);
    }

    private void update(float delta) {
        handleInput();
        if (!state.isPause()) {
            physics.update(delta);
            player.update(delta);
            if (!state.isNotReady()) {
                timer.updateTimer(delta);
                updateEnemies(delta);
            }
            if (state.isGoing()) {
                updateHolesAndIslands(delta);
                shotgun.update(delta);
                worldRenderer.updateGroundAndBackgroundAnimation(timer.getTime(), delta);
                objectSpawner.update(timer);
            }
            for (SingleShell tmp : singleShells) {
                tmp.update(delta);
            }
            checkForGameOver();
            updateSpeedLevel();
        }
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
        if (gameOverNotCalledYet && !player.isAlive()) {
            gameOverNotCalledYet = false;
            gameOver();
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

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            if (state.isOver() && timer.canIReadInputAfterGameOver()) {
                game.setScreen(new Playscreen(game, false));
            } else if (state.isNotReady()) {
                startGame();
            } else if (state.isPause()) {
                pauseGame();
            } else if (state.isGoing()) {
                if (InputHandler.shot()) {
                    shotgunShot();
                } else if (InputHandler.pause()) {
                    pauseGame();
                } else if (InputHandler.jump()) {
                    jump();
                }
            }
        } else {
            if (InputHandler.menuKey()) {
                pauseGame();
            } else if (state.isPause() && InputHandler.backKey()) {
                game.setScreen(new Menuscreen(game));
            }
        }
    }

    private void startGame() {
        state.setGoing();
        player.startMoving();
    }

    private void jump() {
        if (physics.canPlayerJump()) {
            player.jumpFirst();
        } else if (player.isInTheAir()) {
            player.reverseJump();
        }
    }

    private void pauseGame() {
        if (state.isPause()) {
            hud.unpause();
            state.unpause();
        } else {
            hud.pause();
            state.setPause();
        }
    }

    @Override
    public void show() {

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
        objectSpawner.dispose();
        physics.dispose();
    }
}
