package com.kamilbeben.zombiestorm.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 08.10.16.
 */
public abstract class Island extends Sprite {

    private float speed = 7f;
    private boolean islandIsOnScreen = false;

    protected World world;
    public Body body;

    public Island(Texture texture, int speedLevel) {
        super(texture);
        setSpeedLevel(speedLevel);
    }

    protected abstract void setupBody(float x, float y);

    public void update(float delta) {
        isIslandOnScreen();
        updateSpritePosition();
        move(delta);
    }


    public void move(float delta) {
        float calculatedSpeed = speed * delta;
        body.setLinearVelocity(new Vector2(-calculatedSpeed, 0f));
    }

    protected void updateSpritePosition() {
        setPosition(body.getPosition().x - getWidth() / 2,
                (body.getPosition().y) - getHeight() / 2);
    }

    public void render(SpriteBatch batch) {
        if (islandIsOnScreen) {
            draw(batch);
        }
    }



    private void isIslandOnScreen() {
        if (body.getPosition().x > -200 / Zombie.PPM && body.getPosition().x < 900 / Zombie.PPM) {
            islandIsOnScreen = true;
        } else {
            islandIsOnScreen = false;
        }
    }


    public void setSpeedLevel(int speedLevel) {
        speed = Tools.getStaticObjectsSpeedLevel(speedLevel);
    }

}
