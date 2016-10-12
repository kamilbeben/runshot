package com.kamilbeben.zombiestorm.gamelogic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 12.10.16.
 */
public class PhysicsBasicWorld {

    public PhysicsBasicWorld(World world) {
        setupBasicWorld(world);
    }

    private void setupBasicWorld(World world) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        EdgeShape line = new EdgeShape();

        createFloor(body, fixtureDef, line);
        createJumpSensor(body, fixtureDef, line);
        createLeftWallSensor(body, fixtureDef, line);

        line.dispose();
    }

    private void createFloor(Body body, FixtureDef fixtureDef, EdgeShape line) {
        line.set(new Vector2(-5*32 / Zombie.PPM, 96 / Zombie.PPM), new Vector2(40*32 / Zombie.PPM, 96 / Zombie.PPM));
        fixtureDef.shape = line;
        fixtureDef.filter.categoryBits = Zombie.STATIC_BIT;
        fixtureDef.friction = 1.6f;
        body.createFixture(fixtureDef);
    }


    private void createJumpSensor(Body body, FixtureDef fixtureDef, EdgeShape line) {
        line.set(new Vector2(32 / Zombie.PPM, 100 / Zombie.PPM), new Vector2(96 / Zombie.PPM, 100 / Zombie.PPM));
        fixtureDef.shape = line;
        fixtureDef.filter.categoryBits = Zombie.GROUND_BIT;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
    }

    private void createLeftWallSensor(Body body, FixtureDef fixtureDef, EdgeShape line) {
        line.set(new Vector2(25 / Zombie.PPM, 0 / Zombie.PPM), new Vector2(25 / Zombie.PPM, 600 / Zombie.PPM));
        fixtureDef.filter.categoryBits = Zombie.LEFT_CORNER;
        fixtureDef.shape = line;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
    }
}
