package com.kamilbeben.zombiestorm.tools;

import java.util.Random;

/**
 * Created by bezik on 25.09.16.
 */
public class Timer {

    private float timerGeneral = 0f;
    private float timerLastEnemySpawn = 0f;
    private float timerBetweenEnemies = 0f;

    private float timerLastShot = 0f;
    private float timerBetweenShots = 1.5f;


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
        int random = Tools.randomFrom1To10();
        if (random > 7) {
            timerBetweenEnemies = 2.5f;
        } else if (random > 2) {
            timerBetweenEnemies = 1.75f;
        } else if (random > 0) {
            timerBetweenEnemies = 1.0f;
        }
    }

    public static final float randomizeMonkeyJumpTime() {
        return 1f + (Tools.randomFrom1To10() / 4f);
    }


    public boolean isItTimeToShootSomething() {
        if (timerGeneral > timerLastShot + timerBetweenShots) {
            timerLastShot = timerGeneral;
            return true;
        } else {
            return false;
        }
    }

    public int getSpeedLevel() {
        if (timerGeneral < 15f) {
            return 1;
        } else if (timerGeneral < 30) {
            return 2;
        } else if (timerGeneral < 60) {
            return 3;
        } else if (timerGeneral < 120) {
            return 4;
        } else if (timerGeneral < 180) {
            return 5;
        } else {
            return 6;
        }
    }

    public void addTenSeconds() {
        timerGeneral += 10f;
    }

}
