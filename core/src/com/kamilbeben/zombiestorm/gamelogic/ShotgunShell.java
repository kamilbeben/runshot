package com.kamilbeben.zombiestorm.gamelogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 25.09.16.
 */
public class ShotgunShell {

    private static final float force = 5f;

    public Body body;
    private FixtureDef fixtureDef;
    private BodyDef bodyDef;
    private CircleShape shape;


    public ShotgunShell(World world, int yAxis, float playerYPosition) {
        shape = new CircleShape();
        shape.setRadius(4 / Zombie.PPM);
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = Zombie.SHOTGUN_BIT;
        fixtureDef.filter.maskBits = Zombie.ENEMY_BIT;
        fixtureDef.density = 80f;
        bodyDef.position.set(64 / Zombie.PPM, playerYPosition);
        body = world.createBody(bodyDef);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        body.applyLinearImpulse(new Vector2(force, (480 - yAxis - 20) / Zombie.PPM), body.getWorldCenter(), true);
        body.setUserData(this);
        shape.dispose();
    }

    public boolean disposeIfOutOfMap(World world) {
        if (body.getPosition().y < 0) {
            dispose(world);
            return true;
        } else return false;
    }

    public void dispose(World world) {
        world.destroyBody(body);
    }

    public void render(SpriteBatch batch) {

    }

    public void setToHarmless() {
        Array<Fixture> fixtures = body.getFixtureList();
        Filter filter = new Filter();
        filter.categoryBits = Zombie.USED_BIT;
        filter.maskBits = 0;
        for (Fixture tmp : fixtures) {
            tmp.setFilterData(filter);
        }

    }
}
