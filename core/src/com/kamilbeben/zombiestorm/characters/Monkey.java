package com.kamilbeben.zombiestorm.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.screens.Playscreen;
import com.kamilbeben.zombiestorm.tools.Timer;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 24.09.16.
 */
public class Monkey extends Enemy {

    private float speed = 10f;
    private static final float jumpForce = 4f;

    private float randomJumptimer = Timer.randomizeMonkeyJumpTime();

    private Animation running;
    private Animation jumping;
    private Animation shot;


    public Monkey(World world, float x, float y, int speedLevel, Texture texture) {
        super(world, x, y, texture);
        setupBody(x, y);
        setupLooks();
        updateSpritePosition();
        setSpeedLevel(speedLevel);
    }

    @Override
    protected void setupBody(float x, float y) {
        defineBody(x, y);
        FixtureDef fixtureDef = new FixtureDef();
        setupMainBody(fixtureDef);
        setupAdditionalHitbox(fixtureDef);
        body.setUserData(this);
        setupStumbleLine(fixtureDef);
    }

    private void defineBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / Zombie.PPM, y / Zombie.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
    }

    private void setupMainBody(FixtureDef fixtureDef) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(20 / Zombie.PPM, 40 / Zombie.PPM);
        fixtureDef.shape = shape;

        fixtureDef.filter.categoryBits = Zombie.ENEMY_BIT;
        fixtureDef.filter.maskBits = Zombie.ENEMY_BIT | Zombie.STATIC_BIT | Zombie.PLAYER_BIT | Zombie.SHOTGUN_BIT | Zombie.HOLE_BIT | Zombie.CAR_BIT;
        body.createFixture(fixtureDef);
    }

    private void setupAdditionalHitbox(FixtureDef fixtureDef) {
        CircleShape shape = new CircleShape();
        shape.setRadius(15 / Zombie.PPM);
        shape.setPosition(new Vector2(0 / Zombie.PPM, 55 / Zombie.PPM));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Zombie.HEAD_BIT;
        fixtureDef.filter.maskBits = Zombie.SHOTGUN_BIT;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private void setupStumbleLine(FixtureDef fixtureDef) {
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(new Vector2(0 / Zombie.PPM, 50 / Zombie.PPM), new Vector2(10 / Zombie.PPM, 50 / Zombie.PPM));
        fixtureDef.shape = edgeShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Zombie.STUMBLE_BIT;
        fixtureDef.filter.maskBits = Zombie.PLAYER_BIT;
        body.createFixture(fixtureDef);
        edgeShape.dispose();
    }

    private void setupLooks() {

        setBounds(0, 0, 128 / Zombie.PPM, 148 / Zombie.PPM);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(getTexture(), i * 128, 0, 128, 148));
        }
        running = new Animation(0.075f, frames);
        frames.clear();

        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(getTexture(), i * 128, 148, 128, 148));
        }
        jumping = new Animation(0.075f, frames);
        frames.clear();

        for (int i=0; i<6; i++) {
            frames.add(new TextureRegion(getTexture(), i * 128, 148 * 2, 128, 148));
        }
        shot = new Animation(0.075f, frames);
        frames.clear();
    }

    @Override
    public void setSpeedLevel(int speedLevel) {
        speed = Tools.getStaticObjectsSpeedLevel(speedLevel) * 1.4f;
    }

    @Override
    public void dead() {
        Gdx.app.log("Monkey", "Im dead now");
    }

    public void update(float delta, Playscreen playscreen) {
        updateSpritePosition();
        setRegion(getFrame(delta));

        if (alive) {
            move(speed, delta);
            jumpTimer += delta;
            if (jumpTimer > randomJumptimer) {
                currentState = State.JUMPING;
                jumpOnce();
            }
        }

        if (justGotShot) {
            playscreen.hud.zombieGotShot();
            justGotShot = false;
        }
    }

    private void jumpOnce() {
            body.applyLinearImpulse(new Vector2(0, jumpForce), body.getWorldCenter(), true);
            jumpTimer = 0f;
    }


    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion region;

        region = getAnimation().getKeyFrame(stateTimer, true);

        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    private Animation getAnimation() {
        switch(currentState) {
            default:
            case WALKING:
                return running;
            case JUMPING:
                return jumping;
            case DEAD:
            case SHOT:
            case HIT_BY_CAR:
                return shot;
        }
    }

    public State getState() {
        if (currentState != State.HIT_BY_CAR && currentState != State.SHOT) {
            if (!alive) {
                if (justGotShot) {
                    return State.SHOT;
                } else if (justGotHitByCar) {
                    return State.HIT_BY_CAR;
                } else {
                    return State.DEAD;
                }
            } else if (body.getPosition().y > 2.325) {
                return State.JUMPING;
            } else {
                return State.WALKING;
            }
        } else {
            return currentState;
        }
    }


    private void updateSpritePosition() {
        setPosition(body.getPosition().x - getWidth() / 2 + 10 / Zombie.PPM, body.getPosition().y - 40 / Zombie.PPM);
    }
}
