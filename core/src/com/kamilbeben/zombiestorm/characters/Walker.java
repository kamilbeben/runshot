package com.kamilbeben.zombiestorm.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 18.09.16.
 */
public class Walker extends Enemy {

    private static final float bodyRadius = 40 / Zombie.PPM;
    private float speed = 8f;

    private Animation walkerWalking;

    public Walker(World world, float x, float y, float timer) {
        super(world, x, y, new Texture("walker.png"));
        setupBody(x, y);
        setupLooks();
        updateSpritePosition();
        calculateSpeed(timer);
    }

    @Override
    protected void setupBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x * 1/ Zombie.PPM, y * 1/ Zombie.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(bodyRadius);
        fixtureDef.shape = shape;

        fixtureDef.filter.categoryBits = Zombie.ENEMY_BIT;
        fixtureDef.filter.maskBits = Zombie.ENEMY_BIT | Zombie.STATIC_BIT
                | Zombie.PLAYER_BIT | Zombie.SHOTGUN_BIT | Zombie.HOLE_BIT;

        body.createFixture(fixtureDef);

        body.setUserData(this);
    }

    private void setupLooks() {

        setBounds(0, 0, 110 / Zombie.PPM, 148 / Zombie.PPM);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0; i<11; i++) {
            frames.add(new TextureRegion(getTexture(), i * 110, 0, 110, 148));
        }
        walkerWalking = new Animation(0.15f, frames); //TODO adjust
        frames.clear();
    }

    @Override
    public void dead() {
        Gdx.app.log("Walker", "Im dead now");
    }


    public void update(float delta) {
        updateSpritePosition();
        setRegion(getFrame(delta));
        walk(speed, delta);
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion region;
        region = walkerWalking.getKeyFrame(stateTimer, true);
        stateTimer = (currentState == previousState) ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (alive) {
            return State.WALKING;
        } else {
            return State.DEAD;
        }
    }

    private void calculateSpeed(float timer) {
        speed = 8f + 1 * timer / 25f;
    }

    public void render(SpriteBatch batch) {
        draw(batch);
    }

    private void updateSpritePosition() {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - bodyRadius);
    }
}
