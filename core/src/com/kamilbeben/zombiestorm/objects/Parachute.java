package com.kamilbeben.zombiestorm.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 19.10.16.
 */
public class Parachute {

    private Body body;
    private Sprite sprite;
    private float timer = 0f;

    public Parachute(World world, Texture texture) {
        setupBody(world, 400, 600);
        setupLooks(texture);
    }

    private void setupBody(World world, float x, float y) {
        defineBody(world, x, y);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16f / Zombie.PPM, 16 / Zombie.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        setupStaticBody(fixtureDef);
        setupSensorBody(fixtureDef);

        body.setGravityScale(0.1f);
        body.setUserData(this);
        body.setLinearVelocity(new Vector2(-1.5f, 0));
    }

    private void defineBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / Zombie.PPM, y / Zombie.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
    }

    private void setupStaticBody(FixtureDef fixtureDef) {
        fixtureDef.filter.categoryBits = Zombie.AMMO_PACK_BIT;
        fixtureDef.filter.maskBits =  Zombie.STATIC_BIT | Zombie.PLAYER_BIT;
        body.createFixture(fixtureDef);

    }

    private void setupSensorBody(FixtureDef fixtureDef) {
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Zombie.AMMO_PACK_BIT;
        fixtureDef.filter.maskBits =  Zombie.PLAYER_BIT;
        body.createFixture(fixtureDef);
    }

    private void setupLooks(Texture texture) {
        sprite = new Sprite(texture);
        sprite.setSize(texture.getWidth() / Zombie.PPM, texture.getHeight() / Zombie.PPM);
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
    }

    private void update(float delta) {
    }
}
