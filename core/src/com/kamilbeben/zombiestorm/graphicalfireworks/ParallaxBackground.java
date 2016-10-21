package com.kamilbeben.zombiestorm.graphicalfireworks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 16.10.16.
 */
public class ParallaxBackground {

    private float world_scale;
    private float speed;
    private float basicSpeed;

    private Sprite left;
    private Sprite right;

    private boolean drawRight = false;

    public ParallaxBackground(Texture texture, float speed, boolean scaleWorld) {
        if (scaleWorld) {
            world_scale = Zombie.PPM;
        } else {
            world_scale = 1;
        }
        basicSpeed = speed;
        left = new Sprite(texture);
        right = new Sprite(texture);
        left.setSize(Zombie.WIDTH * 3 / world_scale, Zombie.HEIGHT / world_scale);
        right.setSize(Zombie.WIDTH * 3 / world_scale, Zombie.HEIGHT / world_scale);
        setSpeedLevel(1);
        left.setX(0);
    }

    public void update(float delta) {
        left.setX(left.getX() - speed * delta);
        if ((left.getX() + Zombie.WIDTH * 3 / world_scale) <= 0) {
           left.setX(0);
        }

        if (left.getX() + (Zombie.WIDTH * 2 / world_scale) <= 0) {
            drawRight = true;
        } else {
            drawRight = false;
        }
        right.setX(left.getX() + left.getWidth());
    }

    public void render(SpriteBatch batch) {
        left.draw(batch);
        if (drawRight) {
            right.draw(batch);
        }
    }

    public void setSpeedLevel(int speedLevel) {
        switch (speedLevel) {
            default:
            case 1:
                speed = basicSpeed * Tools.speedMultiplier_1;
                break;
            case 2:
                speed = basicSpeed * Tools.speedMultiplier_2;
                break;
            case 3:
                speed = basicSpeed * Tools.speedMultiplier_3;
                break;
            case 4:
                speed = basicSpeed * Tools.speedMultiplier_4;
                break;
            case 5:
                speed = basicSpeed * Tools.speedMultiplier_5;
                break;
            case 6:
                speed = basicSpeed * Tools.speedMultiplier_6;
                break;
        }
    }
}
