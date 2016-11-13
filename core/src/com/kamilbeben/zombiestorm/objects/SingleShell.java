package com.kamilbeben.zombiestorm.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 29.10.16.
 */
public class SingleShell {

    private static PolygonShape shape = new PolygonShape();
    private Sprite sprite;
    private Animation animation;
    private float timer = 0f;
    public Body body;
    private boolean render = true;
    private float speed;


    public SingleShell(World world, float x, float y, Texture texture, int speedLevel) {
        shape.setAsBox(16f / Zombie.PPM, 16 / Zombie.PPM);
        setupBody(world, x, y);
        setupLooks(texture);
        speed = Tools.getStaticObjectsSpeedLevel(speedLevel);
    }

    private void setupBody(World world, float x, float y) {
        defineBody(world, x, y);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        setupStaticBody(fixtureDef);
        setupSensorBody(fixtureDef);

        body.setUserData(this);
    }

    private void defineBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / Zombie.PPM, y / Zombie.PPM);
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bodyDef);
    }

    private void setupStaticBody(FixtureDef fixtureDef) {
        fixtureDef.filter.categoryBits = Zombie.AMMO_PACK_BIT;
        fixtureDef.filter.maskBits =  Zombie.STATIC_BIT | Zombie.PLAYER_BIT;
        body.createFixture(fixtureDef);

    }

    private void setupSensorBody(FixtureDef fixtureDef) {
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Zombie.AMMO_PACK_BIT;
        fixtureDef.filter.maskBits =  Zombie.PLAYER_BIT;
        body.createFixture(fixtureDef);
    }

    private void setupLooks(Texture texture) {
        int width = 32;
        int height = 32;
        sprite = new Sprite(texture);
        sprite.setSize(width*0.5f / Zombie.PPM, height * 0.5f / Zombie.PPM);
        sprite.setBounds(0, 0, width / Zombie.PPM, height / Zombie.PPM);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0; i<8; i++) {
            frames.add(new TextureRegion(sprite.getTexture(), i * width, 0, width, height));
        }
        animation = new Animation(0.2f, frames);
        frames.clear();
    }

    public void render(SpriteBatch batch) {
        if (render) {
            sprite.draw(batch);
        }
    }

    private void updateSpritePosition() {
        sprite.setPosition(body.getPosition().x - 16 / Zombie.PPM,
                body.getPosition().y - 16 / Zombie.PPM);
    }

    public void update(float delta) {
        timer += delta;
        updateSpritePosition();
        sprite.setRegion(getFrame(delta));
        move(speed*delta);
    }

    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        region = animation.getKeyFrame(timer, true);
        timer += delta;
        return region;
    }

    public void move(float speed) {
        body.setLinearVelocity(new Vector2(-speed, body.getLinearVelocity().y));
    }

    public boolean alreadyUsed() {
        return !render;
    }


    public boolean isShellOnScreen() {
        if (sprite.getX() > -200 / Zombie.PPM && sprite.getX() < 1300 / Zombie.PPM) {
            return true;
        } else {
            return true;
        }
    }

    public void stopRendering() {
        render = false;
    }
}
