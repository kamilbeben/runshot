package com.kamilbeben.zombiestorm.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.objects.AmmoPack;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 08.10.16.
 */
public abstract class Island extends Sprite {

    protected AmmoPack ammoPack;
    protected PolygonShape islandShape = new PolygonShape();
    protected EdgeShape jumpLine = new EdgeShape();

    private float speed = 7f;

    public Body body;

    public Island(Texture texture, int speedLevel) {
        super(texture);
        setSpeedLevel(speedLevel);
    }

    protected abstract void setupBody(float x, float y, World world);

    protected void createBody(float x, float y, PolygonShape islandShape, EdgeShape jumpLine, World world) {
        defineBody(x, y, world);
        FixtureDef fixtureDef = new FixtureDef();

        createIslandKinematicBody(fixtureDef, islandShape);
        createJumpSensor(fixtureDef, jumpLine);
    }

    private void defineBody(float x, float y, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x * 1/ Zombie.PPM, y * 1/ Zombie.PPM);
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bodyDef);
    }

    private void createIslandKinematicBody(FixtureDef fixtureDef, PolygonShape islandShape) {
        fixtureDef.shape = islandShape;
        fixtureDef.friction = 0f;
        fixtureDef.filter.categoryBits = Zombie.STATIC_BIT;
        fixtureDef.filter.maskBits = Zombie.PLAYER_BIT | Zombie.AMMO_PACK_BIT;
        body.createFixture(fixtureDef);
    }

    private void createJumpSensor(FixtureDef fixtureDef, EdgeShape jumpLine) {
        fixtureDef.filter.categoryBits = Zombie.GROUND_BIT;
        fixtureDef.shape = jumpLine;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
    }


    public void update(float delta) {
        move(delta);
        updateSpritePosition();
        ammoPack.updateSpritePosition();
    }


    public void move(float delta) {
        float calculatedSpeed = speed * delta;
        body.setLinearVelocity(new Vector2(-calculatedSpeed, 0f));
        ammoPack.move(calculatedSpeed);
    }

    protected void updateSpritePosition() {
        setPosition(body.getPosition().x - getWidth() / 2,
                (body.getPosition().y) - getHeight() / 2);
    }

    public void render(SpriteBatch batch) {
        if (isIslandOnScreen()) {
            draw(batch);
            ammoPack.render(batch);
        }
    }

    public boolean isIslandOnScreen() {
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
