package com.kamilbeben.zombiestorm.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.HolePosition;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 30.09.16.
 */
public abstract class Hole extends Sprite {


    protected PolygonShape shape = new PolygonShape();

    private float speed = 7f;

    protected World world;
    public Body body;

    public Hole(Texture texture, int speedLevel) {
        super(texture);
        setSpeedLevel(speedLevel);
    }

    protected abstract void setupBody(float x, float y, World world);


    public void update(float delta) {
        updateSpritePosition();
        move(delta);
    }


    public void move(float delta) {
        float calculatedSpeed = speed * delta;
        body.setLinearVelocity(new Vector2(-calculatedSpeed, body.getLinearVelocity().y));
    }

    protected void updateSpritePosition() {
        setPosition(body.getPosition().x - getWidth() / 2 + 2 / Zombie.PPM,
                0f);
    }

    public void render(SpriteBatch batch) {
        if (isHoleOnScreen()) {
            draw(batch);
        }
    }


    public boolean isHoleOnScreen() {
        if (body.getPosition().x > -100 / Zombie.PPM && body.getPosition().x < 1300 / Zombie.PPM) {
            return true;
        } else {
            return false;
        }
    }

    protected void createBody(float x, float y, PolygonShape shape, World world) {
        defineBody(x, y, world);
        FixtureDef fixtureDef = new FixtureDef();

        createHoleDynamicBody(fixtureDef, shape);
        createHoleSensorForPlayerAndEnemies(fixtureDef, shape);

        shape.dispose();
        body.setUserData(this);
    }

    private void defineBody(float x, float y, World world) {BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / Zombie.PPM, y / Zombie.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
    }

    private void createHoleDynamicBody(FixtureDef fixtureDef, PolygonShape holeShape) {
        fixtureDef.shape = holeShape;
        fixtureDef.filter.categoryBits = Zombie.HOLE_BIT;
        fixtureDef.filter.maskBits = Zombie.STATIC_BIT;
        body.createFixture(fixtureDef);
    }

    private void createHoleSensorForPlayerAndEnemies(FixtureDef fixtureDef, PolygonShape holeShape) {
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Zombie.HOLE_BIT;
        fixtureDef.filter.maskBits = Zombie.PLAYER_BIT | Zombie.ENEMY_BIT;
        body.createFixture(fixtureDef);
    }

    public void setSpeedLevel(int speedLevel) {
        speed = Tools.getStaticObjectsSpeedLevel(speedLevel);
    }
}
