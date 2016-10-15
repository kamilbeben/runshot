package com.kamilbeben.zombiestorm.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.screens.Playscreen;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 27.09.16.
 */
public class Car extends Enemy {
    private float speed = 12f;

    private Animation carRiding;

    public Car(World world, float x, float y, int speedLevel, Texture texture) {
        super(world, x, y, texture);
        setupBody(x, y);
        setupLooks();
        setSpeedLevel(speedLevel);
    }

    @Override
    protected void setupBody(float x, float y) {
        defineBody(x, y);

        FixtureDef fixtureDef = new FixtureDef();

        setupStaticBody(fixtureDef);
        setupZombieDestroyer(fixtureDef);

        body.setUserData(this);
    }

    private void defineBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x * 1/ Zombie.PPM, y * 1/ Zombie.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
    }

    private void setupStaticBody(FixtureDef fixtureDef) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(60f / Zombie.PPM, 35 / Zombie.PPM);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Zombie.ENEMY_BIT;
        fixtureDef.filter.maskBits = Zombie.ENEMY_BIT | Zombie.STATIC_BIT | Zombie.PLAYER_BIT | Zombie.SHOTGUN_BIT;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private void setupZombieDestroyer(FixtureDef fixtureDef) {
        EdgeShape line = new EdgeShape();
        line.set(new Vector2(-84 / Zombie.PPM, -10 / Zombie.PPM), new Vector2(-84 / Zombie.PPM, 10 / Zombie.PPM));
        fixtureDef.shape = line;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Zombie.CAR_BIT;
        fixtureDef.filter.maskBits = Zombie.ENEMY_BIT;
        body.createFixture(fixtureDef);
        line.dispose();
    }

    private void setupLooks() {

        setBounds(0, 98, 161 / Zombie.PPM, 64 / Zombie.PPM);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(getTexture(), i * 161, 0, 161, 64));
        }
        carRiding = new Animation(0.1f, frames);
        frames.clear();

        setSize(getWidth() * 1.5f, getHeight() * 1.5f);
    }

    @Override
    public void setSpeedLevel(int speedLevel) {
        speed = Tools.getStaticObjectsSpeedLevel(speedLevel) * 1.6f;
    }


    @Override
    public void dead() {
        Gdx.app.log("Walker", "Im dead now");
    }


    public void update(float delta, Playscreen playscreen) {
        updatePosition();
        setRegion(getFrame(delta));
        move(speed, delta);
    }

    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        region = carRiding.getKeyFrame(stateTimer, true);
        stateTimer += delta;
        return region;
    }


    private void updatePosition() {
        setPosition(body.getPosition().x - getWidth() / 2 + 40f / Zombie.PPM, body.getPosition().y - 48 / Zombie.PPM);
    }


    @Override
    public void killEnemy() {
        laughAtPlayer();
    }

    private void laughAtPlayer() {

    }
}
