package com.kamilbeben.zombiestorm.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.HolePosition;

/**
 * Created by bezik on 30.09.16.
 */
public abstract class Hole extends Sprite {

    private float speed = 7f;

    private boolean isPlayerAboveHole = false;

    protected World world;
    public Body body;

    public Hole(Texture texture, float timer) {
        super(texture);
        calculateSpeed(timer);
    }

    protected abstract void setupBody(float x, float y);
    public abstract HolePosition getStartTileAndNumberOfTiles();


    public void update(float delta) {
        updateSpritePosition();
        move(delta);
    }


    public void move(float delta) {
        float calculatedSpeed = speed * delta;
        if (body.getLinearVelocity().x >= -(speed / 3f)) {
            body.applyLinearImpulse(new Vector2(-calculatedSpeed*2, 0f), body.getWorldCenter(), true);
        }
    }

    protected void updateSpritePosition() {
        setPosition(body.getPosition().x - getWidth() / 2 - 8f / Zombie.PPM,
                body.getPosition().y - getHeight() - (4f / Zombie.PPM));
    }

    public void render(SpriteBatch batch) {
        draw(batch);
    }



    public void collisionOff() {
        isPlayerAboveHole = true;
    }


    public boolean isPlayerAboveHole() {
        return isPlayerAboveHole;
    }

    public boolean isHoleOnScreen() {
        if (body.getPosition().x > -100 / Zombie.PPM && body.getPosition().x < 900 / Zombie.PPM) {
            return true;
        } else {
            return false;
        }
    }


    private void calculateSpeed(float timer) {
        speed = 7f + 1 * timer / 15f;
    }



}
