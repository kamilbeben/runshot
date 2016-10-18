package com.kamilbeben.zombiestorm.gamelogic;

import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.characters.Car;
import com.kamilbeben.zombiestorm.characters.Enemy;
import com.kamilbeben.zombiestorm.characters.Monkey;
import com.kamilbeben.zombiestorm.characters.Walker;
import com.kamilbeben.zombiestorm.obstacles.Hole;
import com.kamilbeben.zombiestorm.obstacles.HoleLong;
import com.kamilbeben.zombiestorm.obstacles.HoleShort;
import com.kamilbeben.zombiestorm.obstacles.Island;
import com.kamilbeben.zombiestorm.obstacles.IslandLong;
import com.kamilbeben.zombiestorm.obstacles.IslandShort;
import com.kamilbeben.zombiestorm.tools.TextureHolder;
import com.kamilbeben.zombiestorm.tools.Timer;
import com.kamilbeben.zombiestorm.tools.Tools;

import java.util.List;

/**
 * Created by bezik on 12.10.16.
 */
public class ObjectSpawner {

    private List<Enemy> enemies;
    private List<Hole> holes;
    private List<Island> islands;
    private World world;
    private boolean lastRandomWasAnObstacle = false;
    private boolean lastRandomWasACar = false;
    private boolean[] lastRandomWasAMonkey;
    private boolean[] lastRandomWasAWalker;
    private TextureHolder textureHolder;

    float lastHole = -3f;

    public ObjectSpawner(List<Enemy> enemies, List <Hole> holes, List <Island> islands, World world, TextureHolder textureHolder) {
        this.enemies = enemies;
        this.holes = holes;
        this.islands = islands;
        this.world = world;
        this.textureHolder = textureHolder;

        lastRandomWasAMonkey = new boolean[2];
        lastRandomWasAWalker = new boolean[2];
        for (int i=0; i<2; i++) {
            lastRandomWasAWalker[i] = false;
            lastRandomWasAMonkey[i] = false;
        }
    }

    public void update(Timer timer) {
        if (timer.isItTimeToSpawnNewObstacleOrEnemy() && ((lastHole + 3f < timer.getTime()))) {
            chooseBetweenObstacleAndEnemy(timer);
        }
        clearArrays();
    }

    private void clearArrays() {
        clearEnemies();
        clearHoles();
        clearIslands();
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
        for (Island tmp : islands) {
            if (tmp.isIslandOnScreen()) {
                islandOnScreen = true;
            }
        }
        if (!islandOnScreen && !islands.isEmpty()) {
            islands.clear();
        }
    }

    private void chooseBetweenObstacleAndEnemy(Timer timer) {
        int random = Tools.randomFrom1To10();
        if (random < 5 || lastRandomWasAnObstacle) {
            enemies.add(randomizeEnemy(timer));
        } else {
            chooseBetweenHoleAndIsland(timer);
        }
    }

    private void chooseBetweenHoleAndIsland(Timer timer) {
        resetWalkerBoolean();
        resetMonkeyBoolean();
        resetCarBoolean();
        lastRandomWasAnObstacle = true;
        int random = Tools.randomFrom1To10();
        if (random > 5) {
            addHole(timer);
        } else {
            addIsland(timer);
        }
    }

    private void addHole(Timer timer) {
        if (lastHole + 3f < (timer.getTime())) {
            holes.add(randomizeHole(timer));
            lastHole = timer.getTime();
        } else {
            islands.add(randomizeIsland(timer));
        }
    }

    private void addIsland(Timer timer) {
        islands.add(randomizeIsland(timer));
    }

    private Island randomizeIsland(Timer timer) {
        int random = Tools.randomFrom1To10();
        if (random < 5) {
            return new IslandShort(world, 1200, 260, timer.getSpeedLevel(), textureHolder);
        } else {
            return new IslandLong(world, 1200, 260, timer.getSpeedLevel(), textureHolder);
        }
    }


    private Hole randomizeHole(Timer timer) {
        int random = Tools.randomFrom1To10();
        if (random < 5) {
            return new HoleShort(world, 1200, 100, timer.getSpeedLevel(), textureHolder.GAME_OBSTACLE_HOLE_SHORT);
        } else {
            return new HoleLong(world, 1200, 100, timer.getSpeedLevel(), textureHolder.GAME_OBSTACLE_HOLE_LONG) {
            };
        }
    }

    private Enemy randomizeEnemy(Timer timer) {
        int random = Tools.randomFrom1To10();
        lastRandomWasAnObstacle = false;
        if (random < 5) {
            if (!lastRandomWasAWalker[1]) {
                resetMonkeyBoolean();
                resetCarBoolean();
                return newWalker(timer);
            } else {
                return randomizeEnemy(timer);
            }
        } else if (random < 8) {
            if (!lastRandomWasAMonkey[1]) {
                resetWalkerBoolean();
                resetCarBoolean();
                return newMonkey(timer);
            } else {
                return randomizeEnemy(timer);
            }
        } else if (!lastRandomWasACar && lastHole + 2f < timer.getTime()){
            resetWalkerBoolean();
            resetMonkeyBoolean();
            lastRandomWasACar = true;
            return new Car(world, 1200, 100, timer.getSpeedLevel(), textureHolder.GAME_ENEMY_CAR);
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

    private void resetCarBoolean() {
        lastRandomWasACar = false;
    }

    private Enemy newWalker(Timer timer) {
        if (lastRandomWasAWalker[0]) {
            lastRandomWasAWalker[1] = true;
        } else {
            lastRandomWasAWalker[0] = true;
        }
        lastRandomWasAMonkey[0] = false;
        lastRandomWasAMonkey[1] = false;
        lastRandomWasACar = false;
        return new Walker(world, 1200, 100, timer.getSpeedLevel(), textureHolder.GAME_ENEMY_WALKER);
    }

    private Enemy newMonkey(Timer timer) {
        if (lastRandomWasAMonkey[0]) {
            lastRandomWasAMonkey[1] = true;
        } else {
            lastRandomWasAMonkey[0] = true;
        }
        lastRandomWasAWalker[0] = false;
        lastRandomWasAWalker[1] = false;
        lastRandomWasACar = false;
        return new Monkey(world, 1200, 100, timer.getSpeedLevel(), textureHolder.GAME_ENEMY_MONKEY);
    }
}
