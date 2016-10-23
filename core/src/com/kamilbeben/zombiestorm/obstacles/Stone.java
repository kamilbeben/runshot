package com.kamilbeben.zombiestorm.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 23.10.16.
 */
public abstract class Stone extends Obstacle {


    public Stone(Texture texture, int speedLevel) {
        super(texture, speedLevel);
    }

    @Override
    protected void setupSpecificTypeBody(FixtureDef fixtureDef) {
        createStoneSpecificBody(fixtureDef);
    }

    private void createStoneSpecificBody(FixtureDef fixtureDef) {
        fixtureDef.shape = islandShape;
        fixtureDef.friction = 0f;
        fixtureDef.filter.categoryBits = Zombie.STATIC_BIT;
        fixtureDef.filter.maskBits = Zombie.PLAYER_BIT;
        body.createFixture(fixtureDef);
    }

    @Override
    public void update(float delta) {
        move(delta);
        updateSpritePosition();
    }

    @Override
    public void stopMoving() {
        body.setAwake(false);
    }

    @Override
    public void move(float delta) {
        float calculatedSpeed = speed * delta;
        body.setLinearVelocity(new Vector2(-calculatedSpeed, 0f));
    }

    @Override
    protected void updateSpritePosition() {
        setPosition(body.getPosition().x - 30 / Zombie.PPM, 0);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (isObstacleOnScreen()) {
            draw(batch);
        }
    }
}
