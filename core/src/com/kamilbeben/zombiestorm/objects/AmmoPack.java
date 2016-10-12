package com.kamilbeben.zombiestorm.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 12.10.16.
 */
public class AmmoPack {

    private static PolygonShape shape = new PolygonShape();
    private Sprite sprite;
    private Body body;
    private boolean render = true;

    public AmmoPack(World world, float x, float y,Texture texture) {
        sprite = new Sprite(texture);
        sprite.setSize(32 / Zombie.PPM, 32 / Zombie.PPM);
        shape.setAsBox(16f / Zombie.PPM, 16 / Zombie.PPM);
        setupBody(world, x, y);
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
        bodyDef.position.set((x) / Zombie.PPM, (y + 32) / Zombie.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
    }

    private void setupStaticBody(FixtureDef fixtureDef) {
        fixtureDef.filter.categoryBits = Zombie.AMMO_PACK;
        fixtureDef.filter.maskBits =  Zombie.STATIC_BIT | Zombie.PLAYER_BIT;
        body.createFixture(fixtureDef);

    }

    private void setupSensorBody(FixtureDef fixtureDef) {
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Zombie.AMMO_PACK;
        fixtureDef.filter.maskBits =  Zombie.PLAYER_BIT;
        body.createFixture(fixtureDef);
    }

    public void render(SpriteBatch batch) {
        if (render) {
            sprite.draw(batch);
        }
    }

    public void updateSpritePosition() {
        sprite.setPosition(body.getPosition().x - 16 / Zombie.PPM,
                body.getPosition().y - 16 / Zombie.PPM);
    }

    public void move(float speed) {
        body.setLinearVelocity(new Vector2(-speed, body.getLinearVelocity().y));
    }

    public void stopRendering() {
        render = false;
    }
}
