package com.kamilbeben.zombiestorm.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.screens.Playscreen;

/**
 * Created by bezik on 18.09.16.
 */
public abstract class Enemy extends Sprite {


    protected enum State {WALKING, JUMPING, DEAD, SHOT, HIT_BY_CAR }

    protected State currentState;
    protected State previousState;

    protected float jumpTimer = 0f;
    protected float stateTimer = 0f;
    protected boolean alive = true;
    protected boolean justGotShot = false;
    protected boolean justGotHitByCar = false;



    protected World world;
    public Body body;

    public Enemy(World world, float x, float y, Texture texture) {
        super(texture);
        this.world = world;
        setPosition(x, y);
        currentState = State.WALKING;
        previousState = State.WALKING;
    }

    protected  abstract void setupBody(float x, float y);

    public void move(float speed, float delta) {
        float calculatedSpeed = speed * delta;
        body.setLinearVelocity(new Vector2(-calculatedSpeed, body.getLinearVelocity().y));
    }

    public float getX() {
        return body.getPosition().x;
    }

    public void render(SpriteBatch batch) {
        if (isEnemyOnScreen()) {
            draw(batch);
        }
    }

    public abstract void update(float delta);



    public void killEnemy() {
        alive = false;
        disableBodyCollisions();
    }

    public void shotgunShot() {
        applyShotgunForceToBody();
        killEnemy();
        justGotShot = true;
    }

    private void applyShotgunForceToBody() {
        body.applyLinearImpulse(new Vector2(500f / Zombie.PPM, 20f / Zombie.PPM), body.getWorldCenter(), true);
    }

    public void carAccident() {
        applyCarForceToBody();
        justGotHitByCar = true;
        killEnemy();
    }

    private void applyCarForceToBody() {
        body.applyLinearImpulse(new Vector2(300f / Zombie.PPM, 300f / Zombie.PPM), body.getWorldCenter(), true);
    }

    public void disableBodyCollisions() {
        Array<Fixture> fixtures = body.getFixtureList();
        Filter filter;
        for (Fixture tmp : fixtures) {
            filter = tmp.getFilterData();
            filter.categoryBits = Zombie.DEAD_BIT;
            filter.maskBits = -1;
            tmp.setFilterData(filter);
        }
    }

    public void headHit() {
        killEnemy();
    }

    public void dispose() {
        body.getWorld().destroyBody(body);
        getTexture().dispose();
    }


    public boolean isEnemyOnScreen() {
        if (getX() > -200 / Zombie.PPM && getX() < 1300 / Zombie.PPM) {
            return true;
        } else {
            return true;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public abstract void setSpeedLevel(int level);

    public Vector2 getPosition() {
        return body.getPosition();
    }

}
