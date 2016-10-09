package com.kamilbeben.zombiestorm.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 01.10.16.
 */
public class WorldRenderer {

    private Sprite ground[];
    private boolean renderGround[];

    public WorldRenderer () {
        ground = new Sprite[25];
        renderGround = new boolean[ground.length];
        for (int i=0; i<ground.length; i++) {
            ground[i] = new Sprite(new Texture("grass_bg.png"));
            ground[i].setSize(ground[i].getWidth() / Zombie.PPM, ground[i].getHeight() / Zombie.PPM);
            ground[i].setPosition(i* (32 / Zombie.PPM), 0);
            renderGround[i] = true;
        }
        resetGround();
    }

    public void draw(SpriteBatch batch) {
        for (int i=0; i<ground.length; i++) {
            if (renderGround[i]) {
                ground[i].draw(batch);
            }
        }
    }

    public void updateGround(HolePosition position) {
        resetGround();
        int end = (position.end >= 25) ? 25 : position.end;
        try {
            for (int i = position.start; i < end; i++) {
               if (i >= 0) {
                   renderGround[i] = false;
                   renderGround[i+1] = false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    private void resetGround() {
        for (int i=0; i<renderGround.length; i++) {
            renderGround[i] = true;
        }
    }
}
