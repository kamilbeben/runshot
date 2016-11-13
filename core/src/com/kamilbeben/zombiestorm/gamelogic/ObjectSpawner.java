package com.kamilbeben.zombiestorm.gamelogic;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.characters.Car;
import com.kamilbeben.zombiestorm.characters.Enemy;
import com.kamilbeben.zombiestorm.characters.Monkey;
import com.kamilbeben.zombiestorm.characters.Walker;
import com.kamilbeben.zombiestorm.objects.SingleShell;
import com.kamilbeben.zombiestorm.obstacles.Hole;
import com.kamilbeben.zombiestorm.obstacles.HoleLong;
import com.kamilbeben.zombiestorm.obstacles.HoleShort;
import com.kamilbeben.zombiestorm.obstacles.Island;
import com.kamilbeben.zombiestorm.obstacles.IslandLong;
import com.kamilbeben.zombiestorm.obstacles.IslandShort;
import com.kamilbeben.zombiestorm.obstacles.Obstacle;
import com.kamilbeben.zombiestorm.obstacles.Stone;
import com.kamilbeben.zombiestorm.obstacles.StoneBig;
import com.kamilbeben.zombiestorm.obstacles.StoneSmall;
import com.kamilbeben.zombiestorm.tools.Assets;
import com.kamilbeben.zombiestorm.tools.TextureHolder;
import com.kamilbeben.zombiestorm.tools.Timer;
import com.kamilbeben.zombiestorm.tools.Tools;

import java.util.List;

import jdk.nashorn.tools.ShellFunctions;

/**
 * Created by bezik on 12.10.16.
 */
public class ObjectSpawner {

    private List<Enemy> enemies;
    private List<Hole> holes;
    private List<Obstacle> obstacles;
    private List<SingleShell> singleShells;
    private World world;
    private boolean lastRandomWasAnObstacleOrHole = false;
    private boolean[] lastRandomWasAMonkey;
    private boolean[] lastRandomWasAWalker;

    private Zombie game;

    float lastHole = -3f;
    float lastObstacle = -3f;


    public ObjectSpawner(List<Enemy> enemies, List <Hole> holes, List <Obstacle> obstacles, List<SingleShell> singleShells, World world, Zombie game) {
        this.enemies = enemies;
        this.holes = holes;
        this.obstacles = obstacles;
        this.singleShells = singleShells;
        this.world = world;
        this.game = game;

        lastRandomWasAMonkey = new boolean[2];
        lastRandomWasAWalker = new boolean[2];
        for (int i=0; i<2; i++) {
            lastRandomWasAWalker[i] = false;
            lastRandomWasAMonkey[i] = false;
        }
    }

    public void update(Timer timer) {
        clearArrays();
        if (timer.isItTimeToSpawnNewObstacleOrEnemy() && ((lastHole + 3f < timer.getTime()))  && ((lastObstacle + 2f < timer.getTime()))) {
            if (lastObstacle + 10f < timer.getTime()) {
                addObstacle(timer);
            } else  {
                chooseBetweenObstacleAndEnemy(timer);
            }
            if (timer.isItTimeToSpawnSingleShell()) {
                singleShells.add(new SingleShell(world, 1280, 200, game.assets.textureHolder.GAME_EXTRAS_SHOTGUN_SHELL, timer.getSpeedLevel()));
            }
        }
    }

    private void clearArrays() {
        clearEnemies();
        clearHoles();
        clearIslands();
        clearShells();
    }

    private void clearShells() {
        boolean shellOnScreen = false;
        for (SingleShell tmp : singleShells) {
            if (tmp.isShellOnScreen()) {
                shellOnScreen = true;
            }
        }
        if (!shellOnScreen && !singleShells.isEmpty()) {
            singleShells.clear();
        }
    }

    private void clearEnemies() {
        boolean enemyOnScreen = false;
        for (Enemy tmp : enemies) {
            if (tmp.isEnemyOnScreen()) {
                enemyOnScreen = true;
            }
        }
        if (!enemyOnScreen && !enemies.isEmpty()) {
            enemies.clear();
        }
    }

    private void clearHoles() {
        boolean holeOnScreen = false;
        for (Hole tmp : holes) {
            if (tmp.isHoleOnScreen()) {
                holeOnScreen = true;
            }
        }
        if (!holeOnScreen && !holes.isEmpty()) {
            holes.clear();
        }
    }

    private void clearIslands() {
        boolean islandOnScreen = false;
        for (Obstacle tmp : obstacles) {
            if (tmp.isObstacleOnScreen()) {
                islandOnScreen = true;
            }
        }
        if (!islandOnScreen && !obstacles.isEmpty()) {
            obstacles.clear();
        }
    }

    private void chooseBetweenObstacleAndEnemy(Timer timer) {
        int random = Tools.randomFrom1To10();
        if (random < 5 || lastRandomWasAnObstacleOrHole) {
            enemies.add(randomizeEnemy(timer));
        } else {
            chooseBetweenHoleAndIsland(timer);
        }
    }

    private void chooseBetweenHoleAndIsland(Timer timer) {
        resetWalkerBoolean();
        resetMonkeyBoolean();
        lastRandomWasAnObstacleOrHole = true;
        int random = Tools.randomFrom1To10();
        if (random > 5) {
            addHole(timer);
        } else {
            addObstacle(timer);
        }
    }

    private void addHole(Timer timer) {
        if (lastHole + 3f < (timer.getTime())) {
            holes.add(randomizeHole(timer));
            lastHole = timer.getTime();
            lastRandomWasAnObstacleOrHole = true;
        } else {
            obstacles.add(randomizeIsland(timer));
        }
    }


    private void addObstacle(Timer timer) {
        lastObstacle = timer.getTime();
        obstacles.add(randomizeObstacle(timer));
    }

    private Obstacle randomizeObstacle(Timer timer) {
        int random = Tools.randomFrom1To10();
        if (random < 5) {
            return randomizeIsland(timer);
        } else {
            return randomizeStone(timer);
        }
    }

    private Island randomizeIsland(Timer timer) {
        int random = Tools.randomFrom1To10();
        if (random < 5) {
            return new IslandShort(world, 1200, 260, timer.getSpeedLevel(), game.assets.textureHolder);
        } else {
            return new IslandLong(world, 1200, 260, timer.getSpeedLevel(), game.assets.textureHolder);
        }
    }

    private Stone randomizeStone(Timer timer) {
        int random = Tools.randomFrom1To10();
        if (random < 5) {
            return new StoneSmall(world, 1200, 128, timer.getSpeedLevel(), game.assets.textureHolder);
        } else {
            return new StoneSmall(world, 1200, 128, timer.getSpeedLevel(), game.assets.textureHolder);
        }
    }


    private Hole randomizeHole(Timer timer) {
        int random = Tools.randomFrom1To10();
        if (random < 5) {
            return new HoleShort(world, 1200, 100, timer.getSpeedLevel(), game.assets.textureHolder);
        } else {
            return new HoleLong(world, 1200, 100, timer.getSpeedLevel(), game.assets.textureHolder) {
            };
        }
    }


    private Enemy randomizeEnemy(Timer timer) {
        int random = Tools.randomFrom1To10();
        if (!lastRandomWasAnObstacleOrHole && lastObstacle + 1f < timer.getTime()){
            resetWalkerBoolean();
            resetMonkeyBoolean();
            lastRandomWasAnObstacleOrHole = true;
            return new Car(world, 1400, 100, timer.getSpeedLevel(), game);
        } else if (random < 5) {
            if (!lastRandomWasAWalker[1]) {
                resetMonkeyBoolean();
                resetObstacleBoolean();
                return newWalker(timer);
            } else {
                return randomizeEnemy(timer);
            }
        } else if (random < 10) {
            if (!lastRandomWasAMonkey[1]) {
                resetWalkerBoolean();
                resetObstacleBoolean();
                return newMonkey(timer);
            } else {
                return randomizeEnemy(timer);
            }
        } else {
            return randomizeEnemy(timer);
        }
    }

    private void resetWalkerBoolean() {
        lastRandomWasAWalker[0] = false;
        lastRandomWasAWalker[1] = false;
    }

    private void resetMonkeyBoolean() {
        lastRandomWasAMonkey[0] = false;
        lastRandomWasAMonkey[1] = false;
    }

    private void resetObstacleBoolean() {
        lastRandomWasAnObstacleOrHole = false;
    }

    private Enemy newWalker(Timer timer) {
        if (lastRandomWasAWalker[0]) {
            lastRandomWasAWalker[1] = true;
        } else {
            lastRandomWasAWalker[0] = true;
        }
        lastRandomWasAMonkey[0] = false;
        lastRandomWasAMonkey[1] = false;
        lastRandomWasAnObstacleOrHole = false;
        return new Walker(world, 1200, 100, timer.getSpeedLevel(), game);
    }

    private Enemy newMonkey(Timer timer) {
        if (lastRandomWasAMonkey[0]) {
            lastRandomWasAMonkey[1] = true;
        } else {
            lastRandomWasAMonkey[0] = true;
        }
        lastRandomWasAWalker[0] = false;
        lastRandomWasAWalker[1] = false;
        lastRandomWasAnObstacleOrHole = false;
        return new Monkey(world, 1200, 100, timer.getSpeedLevel(), game);
    }

    public void dispose() {
        for (Enemy tmp : enemies) {
            tmp.dispose();
        }
        for (Obstacle tmp : obstacles) {
            tmp.dispose();
        }
    }
}
