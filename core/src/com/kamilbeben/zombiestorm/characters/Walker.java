package com.kamilbeben.zombiestorm.characters;

import com.badlogic.gdx.audio.Sound;
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
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 18.09.16.
 */
public class Walker extends Enemy {

    private float speed = 8f;

    private Animation walking;
    private Animation gotShot;
    private Animation gotHitByCar;

    public Walker(World world, float x, float y, int speedLevel, Zombie game) {
        super(world, x, y, game.assets.textureHolder.GAME_ENEMY_WALKER, game.assets.sounds.get("audio/sfx/onheadjump.ogg", Sound.class), game.options.sfxVolume);
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
        setupHeadLine(fixtureDef);
        body.setUserData(this);
    }

    private void defineBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / Zombie.PPM, y / Zombie.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
    }

    private void setupMainBody(FixtureDef fixtureDef) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(20 / Zombie.PPM, 50 / Zombie.PPM);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Zombie.ENEMY_BIT;
        fixtureDef.filter.maskBits = Zombie.ENEMY_BIT | Zombie.STATIC_BIT |
                Zombie.PLAYER_BIT | Zombie.HOLE_BIT | Zombie.CAR_BIT;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private void setupHeadLine(FixtureDef fixtureDef) {
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(new Vector2(-19 / Zombie.PPM, 60 / Zombie.PPM), new Vector2(19 / Zombie.PPM, 60 / Zombie.PPM));
        fixtureDef.shape = edgeShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Zombie.STUMBLE_BIT;
        fixtureDef.filter.maskBits = Zombie.PLAYER_BIT;
        body.createFixture(fixtureDef);
        edgeShape.dispose();
    }

    private void setupLooks() {
        setBounds(0, 0, 110 / Zombie.PPM, 156 / Zombie.PPM);
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i=0; i<11; i++) {
            frames.add(new TextureRegion(getTexture(), i * 110, 0, 110, 156));
        }
        walking = new Animation(0.12f, frames);
        frames.clear();

        for (int i=0; i<6; i++) {
            frames.add(new TextureRegion(getTexture(), i * 110, 158, 110, 156));
        }
        gotHitByCar = new Animation(0.12f, frames);
        frames.clear();

        for (int i=0; i<6; i++) {
            frames.add(new TextureRegion(getTexture(), i * 110, 158 * 2, 110, 156));
        }
        gotShot = new Animation(0.12f, frames);
        frames.clear();
    }

    @Override
    public void setSpeedLevel(int speedLevel) {
        speed = Tools.getStaticObjectsSpeedLevel(speedLevel) * 1.2f;
    }



    public void update(float delta) {
        updateSpritePosition();
        setRegion(getFrame(delta));
        if (alive) {
            move(speed, delta);
        }
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion region;
        region = getAnimation().getKeyFrame(stateTimer, true);
        stateTimer = (currentState == previousState) ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (currentState == State.WALKING) {
            if (alive) {
                return State.WALKING;
            } else if (justGotHitByCar) {
                return State.HIT_BY_CAR;
            } else if (justGotShot) {
                return State.SHOT;
            } else {
                return State.DEAD;
            }
        } else {
            return currentState;
        }
    }

    private Animation getAnimation() {
        switch (currentState) {
            case WALKING:
            default:
                return walking;
            case SHOT:
                return gotShot;
            case HIT_BY_CAR:
                return gotHitByCar;
        }
    }


    private void updateSpritePosition() {
        setPosition(body.getPosition().x - getHeight() / 2 + 10 / Zombie.PPM, body.getPosition().y - 50 / Zombie.PPM);
    }
}
