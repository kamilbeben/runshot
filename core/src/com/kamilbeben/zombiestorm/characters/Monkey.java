package com.kamilbeben.zombiestorm.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.Timer;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 24.09.16.
 */
public class Monkey extends Enemy {

    private float speed = 10f;
    private static final float jumpForce = 5f;

    private float randomJumptimer = Timer.randomizeMonkeyJumpTime();

    private Animation running;
    private Animation jumping;
    private Animation dying;


    public Monkey(World world, float x, float y, int speedLevel) {
        super(world, x, y, new Texture("monkey.png"));
        setupBody(x, y);
        setupLooks();
        updateSpritePosition();
        setSpeedLevel(speedLevel);
    }

    @Override
    protected void setupBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x * 1/ Zombie.PPM, y * 1/ Zombie.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(20 / Zombie.PPM, 40 / Zombie.PPM);
        fixtureDef.shape = shape;

        fixtureDef.filter.categoryBits = Zombie.ENEMY_BIT;
        fixtureDef.filter.maskBits = Zombie.ENEMY_BIT | Zombie.STATIC_BIT | Zombie.PLAYER_BIT | Zombie.SHOTGUN_BIT | Zombie.HOLE_BIT;
        body.createFixture(fixtureDef);

        body.setUserData(this);
    }

    private void setupLooks() {

        setBounds(0, 0, 119 / Zombie.PPM, 134 / Zombie.PPM);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0; i<14; i++) {
            frames.add(new TextureRegion(getTexture(), i * 134, 0, 134, 119));
        }
        running = new Animation(0.05f, frames); //TODO adjust
        frames.clear();

        for (int i=4; i<13; i++) {
            frames.add(new TextureRegion(getTexture(), i * 134, 0, 134, 119));
        }
        jumping = new Animation(0.3f, frames);
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

    public void update(float delta) {
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
    }

    private void jumpOnce() {
            body.applyLinearImpulse(new Vector2(0, jumpForce), body.getWorldCenter(), true);
            jumpTimer = 0f;
    }


    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion region;

        switch(currentState) {
            case WALKING:
                region = running.getKeyFrame(stateTimer, true);
                break;
            case JUMPING:
                region = jumping.getKeyFrame(stateTimer, true);
                break;
            default:
                region = running.getKeyFrame(stateTimer, true);
                break;
        }

        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (!alive) {
            return State.DEAD;
        } else if (body.getPosition().y > 2.325) {
            return State.JUMPING;
        } else {
            return State.WALKING;
        }
    }


    private void updateSpritePosition() {
        setPosition(body.getPosition().x - getWidth() / 2 + 10 / Zombie.PPM, body.getPosition().y - 40 / Zombie.PPM);
    }
}
