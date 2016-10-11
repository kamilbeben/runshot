package com.kamilbeben.zombiestorm.tools;

import java.util.Random;

/**
 * Created by bezik on 25.09.16.
 */
public class Tools {

    private float timerGeneral = 0f;
    private float timerLastEnemySpawn = 0f;
    private float timerBetweenEnemies = 0f;

    private float timerLastShot = 0f;
    private float timerBetweenShots = 1.5f;

    public static int randomFrom1To10() {
        return new Random().nextInt(9) + 1; //minimum + rn.nextInt(maxValue - minvalue + 1)
    }

    public static int roundTilePosition(float number) {
        if (number >= 0) {
            return (int) number;
        } else {
            return ((int) number) - 1;
        }
    }

    public void updateTimer(float delta) {
        timerGeneral += delta;
    }

    public float getTime() {
        return timerGeneral;
    }

    public boolean isItTimeToSpawnNewObstacleOrEnemy() {
        if (timerGeneral > timerLastEnemySpawn + timerBetweenEnemies) {
            randomizeTimerBetweenEnemies();
            timerLastEnemySpawn = timerGeneral;
            return true;
        } else {
            return false;
        }
    }

    private void randomizeTimerBetweenEnemies() {
        int random = randomFrom1To10();
        if (random > 8) {
            timerBetweenEnemies = 1.5f;
        } else if (random > 4) {
            timerBetweenEnemies = 3f;
        } else if (random > 0) {
            timerBetweenEnemies = 3.5f;
        }
    }

    public static final float randomizeMonkeyJumpTime() {
        int random = randomFrom1To10();
        if (random > 5) {
            return 1.5f;
        } else if (random > 8) {
            return 2f;
        } else {
            return 6f;
        }
    }


    public boolean isItTimeToShootSomething() {
        if (timerGeneral > timerLastShot + timerBetweenShots) {
            timerLastShot = timerGeneral;
            return true;
        } else {
            return false;
        }
    }




}
