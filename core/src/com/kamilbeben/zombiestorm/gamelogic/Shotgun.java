package com.kamilbeben.zombiestorm.gamelogic;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.characters.Car;
import com.kamilbeben.zombiestorm.characters.Enemy;
import com.kamilbeben.zombiestorm.characters.Player;

import java.util.List;

/**
 * Created by bezik on 18.10.16.
 */
public class Shotgun {

    private int enemiesKilled = 0;
    private int closestIndex;
    private float closestPosition;
    private boolean isSomeoneOnScreen = false;

    private Sprite fireEffect;
    private boolean drawEffect = false;
    private float drawTimer = 0f;

    private Sound shot;
    private float sfxVolume;

    public Shotgun(Zombie game) {
        fireEffect = new Sprite(game.assets.textureHolder.GAME_EXTRAS_FIRE_EFFECT);
        fireEffect.setSize(fireEffect.getTexture().getWidth() / Zombie.PPM, fireEffect.getTexture().getHeight() / Zombie.PPM);
        setupSound(game);
    }

    private void setupSound(Zombie game) {
        this.sfxVolume = game.options.sfxVolume;
        shot = game.assets.sounds.get("audio/sfx/shot.ogg", Sound.class);
    }

    public void shot(List <Enemy> enemies, Player player) {
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
            enemiesKilled++;
        }

        updateFireEffect(player);
        shot.play(sfxVolume);
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

    public void gameOver() {
        drawEffect = false;
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

    public int getEnemiesKilled() {
        return enemiesKilled;
    }
}
