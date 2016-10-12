package com.kamilbeben.zombiestorm.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 27.09.16.
 */
public class Car extends Enemy {
    private float speed = 12f;

    private Animation carRiding;

    public Car(World world, float x, float y, float timer) {
        super(world, x, y, new Texture("car.png"));
        setupBody(x, y);
        setupLooks();
        calculateSpeed(timer);
    }

    @Override
    protected void setupBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x * 1/ Zombie.PPM, y * 1/ Zombie.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(80f / Zombie.PPM, 35 / Zombie.PPM);
        fixtureDef.shape = shape;

        fixtureDef.filter.categoryBits = Zombie.ENEMY_BIT;
        fixtureDef.filter.maskBits = Zombie.ENEMY_BIT | Zombie.STATIC_BIT | Zombie.PLAYER_BIT | Zombie.SHOTGUN_BIT;

        body.createFixture(fixtureDef);

        body.setUserData(this);
    }

    private void setupLooks() {

        setBounds(0, 98, 161 / Zombie.PPM, 64 / Zombie.PPM);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(getTexture(), i * 161, 0, 161, 64));
        }
        carRiding = new Animation(0.1f, frames); //TODO adjust
        frames.clear();

        setSize(getWidth() * 1.5f, getHeight() * 1.5f);
    }


    @Override
    public void dead() {
        Gdx.app.log("Walker", "Im dead now");
    }


    public void update(float delta) {
        updatePosition();
        setRegion(getFrame(delta));
        walk(speed, delta);
    }

    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        region = carRiding.getKeyFrame(stateTimer, true);
        stateTimer += delta;
        return region;
    }


    public void render(SpriteBatch batch) {
        draw(batch);
    }

    private void updatePosition() {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - 48 / Zombie.PPM);
    }

    private void calculateSpeed(float timer) {
        speed = 12f + 1 * timer / 25f;
    }

    @Override
    public void killEnemy() {
        laughAtPlayer();
    }

    private void laughAtPlayer() {

    }
}
