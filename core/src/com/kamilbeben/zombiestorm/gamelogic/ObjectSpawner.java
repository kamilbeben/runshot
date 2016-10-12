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
    private TextureHolder textureHolder;

    public ObjectSpawner(List<Enemy> enemies, List <Hole> holes, List <Island> islands, World world, TextureHolder textureHolder) {
        this.enemies = enemies;
        this.holes = holes;
        this.islands = islands;
        this.world = world;
        this.textureHolder = textureHolder;
    }

    public void update(Timer timer) {
        if (timer.isItTimeToSpawnNewObstacleOrEnemy()) {
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
            System.out.println("Enemies cleared");
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
            System.out.println("Holes cleared");
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
            System.out.println("Islands cleared");
            islands.clear();
        }
    }

    private void chooseBetweenObstacleAndEnemy(Timer timer) {
        int random = Tools.randomFrom1To10();
        if (random < 5 || lastRandomWasAnObstacle) {
            enemies.add(randomizeEnemy(timer));
            lastRandomWasAnObstacle = false;
        } else {
            chooseBetweenHoleAndIsland(timer);
        }
    }

    private void chooseBetweenHoleAndIsland(Timer timer) {
        int random = Tools.randomFrom1To10();
        if (random > 5) {
            addHole(timer);
        } else {
            addIsland(timer);
        }
    }

    private void addHole(Timer timer) {
        holes.add(randomizeHole(timer));
        lastRandomWasAnObstacle = true;
    }

    private void addIsland(Timer timer) {
        islands.add(randomizeIsland(timer));
        lastRandomWasAnObstacle = true;
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
            return new HoleShort(world, 1200, 200, timer.getSpeedLevel(), textureHolder.GAME_OBSTACLE_HOLE_SHORT);
        } else {
            return new HoleLong(world, 1200, 200, timer.getSpeedLevel(), textureHolder.GAME_OBSTACLE_HOLE_LONG) {
            };
        }
    }

    private Enemy randomizeEnemy(Timer timer) {
        int random = Tools.randomFrom1To10();
        if (random < 5) {
            return new Walker(world, 1200, 200, timer.getSpeedLevel(), textureHolder.GAME_ENEMY_WALKER);
        } else if (random < 8) {
            return new Monkey(world, 1200, 200, timer.getSpeedLevel(), textureHolder.GAME_ENEMY_MONKEY);
        } else if (!lastRandomWasAnObstacle && !lastRandomWasACar){
            lastRandomWasACar = true;
            return new Car(world, 1200, 200, timer.getSpeedLevel(), textureHolder.GAME_ENEMY_CAR);
        } else {
            return new Monkey(world, 1200, 200, timer.getSpeedLevel(), textureHolder.GAME_ENEMY_MONKEY);
        }
    }
}
