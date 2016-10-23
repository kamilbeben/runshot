package com.kamilbeben.zombiestorm.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 23.10.16.
 */
public abstract class Obstacle extends Sprite {

    protected PolygonShape islandShape = new PolygonShape();
    protected EdgeShape jumpLine = new EdgeShape();

    protected float speed = 7f;

    public Body body;

    public Obstacle(Texture texture, int speedLevel) {
        super(texture);
        setSpeedLevel(speedLevel);
    }

    protected abstract void setupBody(float x, float y, World world);
    protected abstract void setupSpecificTypeBody(FixtureDef fixtureDef);
    protected abstract void updateSpritePosition();
    public abstract void update(float delta);
    public abstract void stopMoving();
    public abstract void move(float delta);
    public abstract void render(SpriteBatch batch);

    protected void createBody(float x, float y, World world) {
        defineBody(x, y, world);
        FixtureDef fixtureDef = new FixtureDef();
        setupSpecificTypeBody(fixtureDef);
        createJumpSensor(fixtureDef);
    }

    private void defineBody(float x, float y, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x * 1/ Zombie.PPM, y * 1/ Zombie.PPM);
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bodyDef);
    }

    private void createJumpSensor(FixtureDef fixtureDef) {
        fixtureDef.filter.categoryBits = Zombie.GROUND_BIT;
        fixtureDef.shape = jumpLine;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
    }


    public boolean isObstacleOnScreen() {
        if (body.getPosition().x > -300 / Zombie.PPM && body.getPosition().x < 1300 / Zombie.PPM) {
            return true;
        } else {
            return false;
        }
    }

    public void setSpeedLevel(int speedLevel) {
        speed = Tools.getStaticObjectsSpeedLevel(speedLevel);
    }

}
