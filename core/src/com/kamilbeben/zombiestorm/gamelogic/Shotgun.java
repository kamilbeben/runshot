package com.kamilbeben.zombiestorm.gamelogic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.characters.Enemy;
import com.kamilbeben.zombiestorm.characters.Player;

import java.util.List;

/**
 * Created by bezik on 18.10.16.
 */
public class Shotgun {

    private int zombiesKilled = 0;
    private int closestIndex;
    private float closestPosition;
    private boolean isSomeoneOnScreen = false;

    public void Shotgun() {

    }

    public void shot(List <Enemy> enemies, Player player) {
        isSomeoneOnScreen = false;

        if (isEnemyOnScreen(enemies, 0)) {
            closestIndex = 0;
            closestPosition = enemies.get(0).getX();
            isSomeoneOnScreen = true;
        }
        for (int i=0; i<enemies.size(); i++) {
            if (isEnemyOnScreen(enemies, i)) {
                if ((enemies.get(i).getX() < closestPosition)) {
                    closestIndex = i;
                    closestPosition = enemies.get(i).getX();
                    isSomeoneOnScreen = true;
                }
            }
        }
        if (isPlayerLowEnough(player) && isSomeoneOnScreen) {
            enemies.get(closestIndex).shotgunShot();
            zombiesKilled++;
        }
    }

    private boolean isEnemyOnScreen(List <Enemy> enemies, int i) {
        if ((enemies.get(i).getX() > 0) && enemies.get(i).getX() < Zombie.WIDTH) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isPlayerLowEnough(Player player) {
        if (player.getPosition().y < 2.5f) {
            return true;
        } else {
            return false;
        }
    }

    public int howManyKilled() {
        return zombiesKilled;
    }

    public void render(SpriteBatch batch) {

    }
}
