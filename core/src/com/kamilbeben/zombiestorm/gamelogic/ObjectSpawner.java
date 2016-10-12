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

    public ObjectSpawner(List<Enemy> enemies, List <Hole> holes, List <Island> islands, World world) {
        this.enemies = enemies;
        this.holes = holes;
        this.islands = islands;
        this.world = world;
    }

    public void update(Tools timer) {
        if (timer.isItTimeToSpawnNewObstacleOrEnemy()) {
            chooseBetweenObstacleAndEnemy(timer);
        }
    }

    private void chooseBetweenObstacleAndEnemy(Tools timer) {
        int random = Tools.randomFrom1To10();
        if (random < 7 || lastRandomWasAnObstacle) {
            enemies.add(randomizeEnemy(timer));
            lastRandomWasAnObstacle = false;
        } else {
            chooseBetweenHoleAndIsland(timer);
        }
    }

    private void chooseBetweenHoleAndIsland(Tools timer) {
        int random = Tools.randomFrom1To10();
        if (random > 5) {
            addHole(timer);
        } else {
            addIsland(timer);
        }
    }

    private void addHole(Tools timer) {
        holes.add(randomizeHole(timer));
        lastRandomWasAnObstacle = true;
    }

    private void addIsland(Tools timer) {
        islands.add(randomizeIsland(timer));
        lastRandomWasAnObstacle = true;
    }

    private Island randomizeIsland(Tools timer) {
        int random = Tools.randomFrom1To10();
        if (random < 5) {
            return new IslandShort(world, 1200, 260, timer.getTime());
        } else {
            return new IslandLong(world, 1200, 260, timer.getTime());
        }
    }

    private Hole randomizeHole(Tools timer) {
        int random = Tools.randomFrom1To10();
        if (random < 5) {
            return new HoleShort(world, 1200, 200, timer.getTime());
        } else {
            return new HoleLong(world, 1200, 200, timer.getTime()) {
            };
        }
    }

    private Enemy randomizeEnemy(Tools timer) {
        int random = Tools.randomFrom1To10();
        if (random < 5) {
            return new Walker(world, 1200, 200, timer.getTime());
        } else if (random < 8) {
            return new Monkey(world, 1200, 200, timer.getTime());
        } else if (!lastRandomWasAnObstacle && !lastRandomWasACar){
            lastRandomWasACar = true;
            return new Car(world, 1200, 200, timer.getTime());
        } else {
            return new Monkey(world, 1200, 200, timer.getTime());
        }
    }
}
