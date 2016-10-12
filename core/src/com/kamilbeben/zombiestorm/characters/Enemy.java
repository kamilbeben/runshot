package com.kamilbeben.zombiestorm.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

/**
 * Created by bezik on 18.09.16.
 */
public abstract class Enemy extends Sprite {


    protected enum State {WALKING, JUMPING, DEAD }

    protected State currentState;
    protected State previousState;

    protected float jumpTimer = 0f;
    protected float stateTimer = 0f;
    protected boolean alive = true;
    protected boolean justGotShot = false;



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


    public abstract void dead();

    public void killEnemy() {
        alive = false;
        justGotShot = true;
        disableBodyCollisions();
        dead();
    }

    public void disableBodyCollisions() {
        Array<Fixture> fixtures = body.getFixtureList();
        Filter filter;
        for (Fixture tmp : fixtures) {
            filter = tmp.getFilterData();
            filter.categoryBits = Zombie.DEAD_BIT; //Disable collisions
            filter.maskBits = Zombie.ENEMY_BIT;
            tmp.setFilterData(filter);
            tmp.setFriction(0.5f); //Prevent from sliding
        }
        body.setUserData(null);
    }


    public boolean gotShot() {
        if (justGotShot) {
            justGotShot = false;
            return true;
        } else return false;
    }

    public void dispose(World world) {
        world.destroyBody(body);
        getTexture().dispose();
    }


    public boolean isEnemyOnScreen() {
        if (body.getPosition().x > -100 / Zombie.PPM && body.getPosition().x < 1300 / Zombie.PPM) {
            return true;
        } else {
            return false;
        }
    }
    public abstract void setSpeedLevel(int level);

    public Vector2 getPosition() {
        return body.getPosition();
    }

}
