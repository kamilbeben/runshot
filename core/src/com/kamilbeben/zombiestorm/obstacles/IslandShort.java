package com.kamilbeben.zombiestorm.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 08.10.16.
 */
public class IslandShort extends Island {

    public IslandShort(World world, float x, float y, int speedLevel) {
        super(new Texture("island_short.png"), speedLevel);
        setSize(getWidth() / Zombie.PPM, 16f / Zombie.PPM);
        this.world = world;
        setupBody(x, y);
        updateSpritePosition();
    }

    @Override
    protected void setupBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x * 1/ Zombie.PPM, y * 1/ Zombie.PPM);
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(100f / Zombie.PPM, 12f / Zombie.PPM);
        fixtureDef.shape = shape;
        fixtureDef.friction = 0f;
        fixtureDef.filter.categoryBits = Zombie.STATIC_BIT;
        fixtureDef.filter.maskBits = Zombie.PLAYER_BIT;
        body.createFixture(fixtureDef);

        EdgeShape line = new EdgeShape();
        line.set(new Vector2(-105 / Zombie.PPM, 28 / Zombie.PPM), new Vector2(110 / Zombie.PPM, 28 / Zombie.PPM));
        fixtureDef.filter.categoryBits = Zombie.GROUND_BIT;
        fixtureDef.shape = line;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);

    }
}
