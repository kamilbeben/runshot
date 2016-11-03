package com.kamilbeben.zombiestorm.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private Sprite carLights;

    public Car(World world, float x, float y, int speedLevel, Zombie game) {
        super(world, x, y, game.assets.textureHolder.GAME_ENEMY_CAR);
        setupBody(x, y);
        setupLooks(game.assets.textureHolder.GAME_ENEMY_CAR_LIGHTS);
        setSpeedLevel(speedLevel);
    }

    @Override
    protected void setupBody(float x, float y) {
        defineBody(x, y);
        FixtureDef fixtureDef = new FixtureDef();
        setupStaticBody(fixtureDef);
        setupZombieDestroyer(fixtureDef);
        setupStumbleLine(fixtureDef);
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
        shape.setAsBox(100 / Zombie.PPM, 35 / Zombie.PPM);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Zombie.ENEMY_BIT;
        fixtureDef.filter.maskBits = Zombie.ENEMY_BIT | Zombie.STATIC_BIT | Zombie.PLAYER_BIT;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private void setupZombieDestroyer(FixtureDef fixtureDef) {
        EdgeShape line = new EdgeShape();
        line.set(new Vector2(-104 / Zombie.PPM, -10 / Zombie.PPM), new Vector2(-104 / Zombie.PPM, 10 / Zombie.PPM));
        fixtureDef.shape = line;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Zombie.CAR_BIT;
        fixtureDef.filter.maskBits = Zombie.ENEMY_BIT;
        body.createFixture(fixtureDef);
        line.dispose();
    }

    private void setupStumbleLine(FixtureDef fixtureDef) {
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(new Vector2(-95 / Zombie.PPM, 39 / Zombie.PPM), new Vector2(99 / Zombie.PPM, 39 / Zombie.PPM));
        fixtureDef.shape = edgeShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Zombie.STUMBLE_BIT;
        fixtureDef.filter.maskBits = Zombie.PLAYER_BIT;
        body.createFixture(fixtureDef);
        edgeShape.dispose();
    }

    private void setupLooks(Texture carLightsTexture) {

        setBounds(0, 98, 202 / Zombie.PPM, 70 / Zombie.PPM);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(getTexture(), i * 202, 0, 202, 70));
        }
        carRiding = new Animation(0.1f, frames);
        frames.clear();

        setSize(getWidth() * 1.5f, getHeight() * 1.5f);

        carLights = new Sprite(carLightsTexture);
        carLights.setSize(carLights.getTexture().getWidth() / Zombie.PPM, carLights.getTexture().getHeight() / Zombie.PPM);
    }

    @Override
    public void setSpeedLevel(int speedLevel) {
        speed = Tools.getStaticObjectsSpeedLevel(speedLevel) * 1.6f;
    }


    public void update(float delta) {
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
        setPosition(body.getPosition().x - getWidth() / 2 + 20f / Zombie.PPM, body.getPosition().y - 44 / Zombie.PPM);
        carLights.setPosition(body.getPosition().x - 405f / Zombie.PPM, body.getPosition().y - 16 / Zombie.PPM);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (isEnemyOnScreen()) {
            carLights.draw(batch);
            draw(batch);
        }
    }

    @Override
    public void killEnemy() {
        laughAtPlayer();
    }

    private void laughAtPlayer() {

    }
}
