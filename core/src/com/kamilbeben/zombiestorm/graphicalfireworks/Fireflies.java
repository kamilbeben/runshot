package com.kamilbeben.zombiestorm.graphicalfireworks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 13.10.16.
 */
public class Fireflies {

    private Body body;
    private static FixtureDef fixtureDef;
    private static BodyDef bodyDef;
    private static CircleShape shape;

    private boolean isTurningRight = true;
    private float turningSpeed;


    private Sprite sprite;

    public Fireflies(World world, Texture texture) {
        setupBody(world);
        sprite = new Sprite(texture);
        sprite.setSize(32 / Zombie.PPM, 32 / Zombie.PPM);
        sprite.setOrigin(16f / Zombie.PPM, 16f / Zombie.PPM);
        updateSpritePosition();
        randomTurning();
    }

    private void setupBody(World world) {
        shape = new CircleShape();
        shape.setRadius(16 / Zombie.PPM);
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        fixtureDef = new FixtureDef();
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        bodyDef.position.set(randomFireflyPosition());
        body = world.createBody(bodyDef);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        body.setUserData(this);
        body.setGravityScale(0);
        shape.dispose();
    }

    private void updateSpritePosition() {
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
    }

    public void rotate(){
        if (isTurningRight) {
            sprite.rotate(turningSpeed);
        } else {
            sprite.rotate(-turningSpeed);
        }
    }

    public void update() {
        if (body.getPosition().y > 480 / Zombie.PPM) {
            body.applyLinearImpulse(new Vector2(0.5f / Zombie.PPM, -1f / Zombie.PPM), body.getWorldCenter(), true);
        } else if (body.getPosition().y < 0 / Zombie.PPM) {
            body.applyLinearImpulse(new Vector2(-0.5f / Zombie.PPM, 1f / Zombie.PPM), body.getWorldCenter(), true);
        } else if (body.getPosition().x < 0 / Zombie.PPM) {
            body.applyLinearImpulse(new Vector2(1f / Zombie.PPM, 0f / Zombie.PPM), body.getWorldCenter(), true);
        } else if (body.getPosition().x > 800 / Zombie.PPM) {
            body.applyLinearImpulse(new Vector2(-1f / Zombie.PPM, -0.5f / Zombie.PPM), body.getWorldCenter(), true);
        }
    }

    public void changeDirection() {
        body.applyLinearImpulse(randomDirectionImpulse(), body.getWorldCenter(), true);
    }

    public void render(SpriteBatch batch) {
        updateSpritePosition();
        sprite.draw(batch);
    }

    private Vector2 randomDirectionImpulse() {
        int random = Tools.randomFrom1To10();
        if (random < 3) {
            return new Vector2(1f / Zombie.PPM, 0.5f / Zombie.PPM);
        } else if (random < 5) {
            return new Vector2(-1f / Zombie.PPM, 0.5f / Zombie.PPM);
        } else if (random < 7) {
            return new Vector2(0f / Zombie.PPM, 1f / Zombie.PPM);
        } else {
            return new Vector2(0f / Zombie.PPM, -1f / Zombie.PPM);
        }
    }

    private Vector2 randomFireflyPosition() {
        float positionX = Tools.randomFrom1To10() * 70 / Zombie.PPM;
        float positionY = 100 / Zombie.PPM + Tools.randomFrom1To10() * 30 / Zombie.PPM;
        return new Vector2(positionX, positionY);
    }

    private void randomTurning() {
        int random = Tools.randomFrom1To10();
        if (random < 5) {
            isTurningRight = true;
        } else {
            isTurningRight = false;
        }

        random = Tools.randomFrom1To10();
        if (random < 4) {
            turningSpeed = 2f;
        } else if (random < 7) {
            turningSpeed = 4f;
        } else {
            turningSpeed = 7f;
        }
    }
}
