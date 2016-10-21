package com.kamilbeben.zombiestorm.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 19.10.16.
 */
public class Parachute {

    private Body body;
    private Sprite sprite;

    public Parachute(World world, Texture texture) {
        setupBody(World world);
        setupLooks(texture);
    }

    private void setupBody(World world) {
        defineBody(world, x, y);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        setupStaticBody(fixtureDef);
        setupSensorBody(fixtureDef);

        body.setUserData(this);
    }

    private void defineBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((x) / Zombie.PPM, (y + 32) / Zombie.PPM);
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
}
