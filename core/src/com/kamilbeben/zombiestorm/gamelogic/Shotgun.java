package com.kamilbeben.zombiestorm.gamelogic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.characters.Car;
import com.kamilbeben.zombiestorm.characters.Enemy;
import com.kamilbeben.zombiestorm.characters.Player;
import com.kamilbeben.zombiestorm.hud.HudPlayscreen;

import java.util.List;

/**
 * Created by bezik on 18.10.16.
 */
public class Shotgun {

    private int closestIndex;
    private float closestPosition;
    private boolean isSomeoneOnScreen = false;

    private Sprite fireEffect;
    private boolean drawEffect = false;
    private float drawTimer = 0f;

    public Shotgun(Texture texture) {
        fireEffect = new Sprite(texture);
        fireEffect.setSize(fireEffect.getTexture().getWidth() / Zombie.PPM, fireEffect.getTexture().getHeight() / Zombie.PPM);
    }

    public void shot(List <Enemy> enemies, Player player, HudPlayscreen hud) {
        isSomeoneOnScreen = false;

        closestPosition = Zombie.WIDTH + 1;

        for (int i=0; i<enemies.size(); i++) {
            if (isEnemyOnScreen(enemies, i) && (enemies.get(i).isAlive()) && enemyIsNotACar(enemies.get(i))) {
                if ((enemies.get(i).getX() < closestPosition)) {
                    closestIndex = i;
                    closestPosition = enemies.get(i).getX();
                    isSomeoneOnScreen = true;
                }
            }
        }
        if (isPlayerLowEnough(player) && isSomeoneOnScreen) {
            enemies.get(closestIndex).shotgunShot();
            hud.zombieGotShot();
        }

        updateFireEffect(player);
    }

    private boolean enemyIsNotACar(Enemy enemy) {
        if (enemy instanceof Car) {
            return false;
        } else {
            return true;
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

    public void update(float delta) {
        if (drawEffect) {
            drawTimer += delta;
            if (drawTimer > 0.1f) {
                drawEffect = false;
            }
        }
    }

    private void updateFireEffect(Player player) {
        drawTimer = 0f;
        drawEffect = true;
        fireEffect.setPosition(player.getPosition().x + 64 / Zombie.PPM, player.getPosition().y + 4 / Zombie.PPM);
    }

    public void render(SpriteBatch batch) {
        if (drawEffect) {
            fireEffect.draw(batch);
            fireEffect.setAlpha(1 - 10*drawTimer);
        }
    }
}
