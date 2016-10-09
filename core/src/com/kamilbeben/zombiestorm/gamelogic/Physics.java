package com.kamilbeben.zombiestorm.gamelogic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bezik on 17.09.16.
 */
public class Physics {

    private Vector2 gravity = new Vector2(0, -10);
    public World world = new World(gravity, true);
    public WorldContactListener contactListener;
    public Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    private List<ShotgunShell> shotgunShells;

    public Physics() {
        initializeCollisionDetection();
        shotgunShells = new ArrayList<ShotgunShell>();
    }

    public void update(float delta) {
        world.step(delta, 6, 2);
        for (int i = 0; i< shotgunShells.size(); i++) {
            if (shotgunShells.get(i).disposeIfOutOfMap(world)) {
                shotgunShells.remove(i);
            }
        }
    }

    public boolean playerCollidesWithLeftWall() {
        return contactListener.playerCollidesWithLeftWall;
    }

    public void renderDebug(OrthographicCamera camera) {
        debugRenderer.render(world, camera.combined);
    }


    public void setupPhysisWorld() {
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        shape.setAsBox(32*50f / Zombie.PPM, 96f / Zombie.PPM);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Zombie.STATIC_BIT;
        fixtureDef.friction = 1.6f;
        body.createFixture(fixtureDef);

        shape.setAsBox(32*50f / Zombie.PPM, 100f / Zombie.PPM);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Zombie.GROUND_BIT;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);
        shape.dispose();

        EdgeShape line = new EdgeShape();
        line.set(new Vector2(5 / Zombie.PPM, 0 / Zombie.PPM), new Vector2(5 / Zombie.PPM, 600 / Zombie.PPM));
        fixtureDef.filter.categoryBits = Zombie.LEFT_CORNER;
        fixtureDef.shape = line;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
    }

    private void initializeCollisionDetection() {
        contactListener = new WorldContactListener(world);
        world.setContactListener(contactListener);
    }

    public void shotgunShot(float force, int yAxis, float playerYPosition) {
        shotgunShells.add(new ShotgunShell(world, force, yAxis, playerYPosition));
    }

    public boolean canPlayerJump() {
        if (contactListener.playerFootContacts > 0) {
            return true;
        } else return false;
    }

    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }

}



