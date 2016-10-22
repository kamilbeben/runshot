package com.kamilbeben.zombiestorm.tools;

import com.kamilbeben.zombiestorm.Zombie;

import java.util.Random;

/**
 * Created by bezik on 25.09.16.
 */
public class Timer {

    private float timerGeneral = 0f;
    private float timerLastEnemySpawn = 0f;
    private float timerBetweenEnemies = 0f;

    private float time_long = 2.5f;
    private float time_middle = 1.75f;
    private float time_short = 1.0f;

    private static final float time_long_base = 2.5f;
    private static final float time_middle_base = 1.75f;
    private static final float time_short_base = 1.0f;

    private boolean gameOver = false;
    private float gameOverTimer = 0f;




    public void updateTimer(float delta) {
        if (gameOver) {
            gameOverTimer += delta;
        } else {
            timerGeneral += delta;
        }
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
        updateSpawnTimer();
        int random = Tools.randomFrom1To10();
        if (random > 7) {
            timerBetweenEnemies = time_long;
        } else if (random > 2) {
            timerBetweenEnemies = time_middle;
        } else if (random > 0) {
            timerBetweenEnemies = time_short;
        }
    }

    private void updateSpawnTimer() {
        switch (getSpeedLevel()) {
            default:
            case 1:
                setTimeMultiplier(Tools.speedMultiplier_1);
                break;
            case 2:
                setTimeMultiplier(Tools.speedMultiplier_2);
                break;
            case 3:
                setTimeMultiplier(Tools.speedMultiplier_3);
                break;
            case 4:
                setTimeMultiplier(Tools.speedMultiplier_4);
                break;
            case 5:
                setTimeMultiplier(Tools.speedMultiplier_5);
                break;
            case 6:
                setTimeMultiplier(Tools.speedMultiplier_6);
                break;
        }
    }

    private void setTimeMultiplier(float speedMultiplier) {
        time_long = time_long_base / speedMultiplier;
        time_middle = time_middle_base / speedMultiplier;
        time_short = time_short_base / speedMultiplier;
    }

    public static final float randomizeMonkeyJumpTime() {
        return 1f + (Tools.randomFrom1To10() / 4f);
    }

    public void setGameOver() {
        gameOver = true;
    }

    public boolean canIReadInputAfterGameOver() {
        if (gameOverTimer > 0.5f) {
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
