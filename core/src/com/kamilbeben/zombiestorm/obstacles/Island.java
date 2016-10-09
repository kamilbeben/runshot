package com.kamilbeben.zombiestorm.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 08.10.16.
 */
public abstract class Island extends Sprite {

    private float speed = 7f;

    protected World world;
    public Body body;

    public Island(Texture texture, float timer) {
        super(texture);
        calculateSpeed(timer);
    }

    protected abstract void setupBody(float x, float y);

    public void update(float delta) {
        updateSpritePosition();
        move(delta);
    }


    public void move(float delta) {
        float calculatedSpeed = speed * delta;
        if (body.getLinearVelocity().x >= -(speed / 3f)) {
            body.setLinearVelocity(new Vector2(-calculatedSpeed*2, 0f));
        }
    }

    protected void updateSpritePosition() {
        setPosition(body.getPosition().x - getWidth() / 2,
                (body.getPosition().y) - getHeight() / 2);
    }

    public void render(SpriteBatch batch) {
        draw(batch);
    }



    public boolean isIslandOnScreen() {
        if (body.getPosition().x > -100 / Zombie.PPM && body.getPosition().x < 900 / Zombie.PPM) {
            return true;
        } else {
            return false;
        }
    }


    private void calculateSpeed(float timer) {
        speed = 70f + 1 * timer / 15f;
    }
}
