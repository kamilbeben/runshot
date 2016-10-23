package com.kamilbeben.zombiestorm.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.objects.AmmoPack;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 08.10.16.
 */
public abstract class Island extends Obstacle {

    protected AmmoPack ammoPack;
    protected EdgeShape accidentLine = new EdgeShape();

    public Island(Texture texture, int speedLevel) {
        super(texture, speedLevel);
    }

    @Override
    protected void setupSpecificTypeBody(FixtureDef fixtureDef) {
        createIslandKinematicBody(fixtureDef);
        createAccidentSensor(fixtureDef);
    }

    private void createIslandKinematicBody(FixtureDef fixtureDef) {
        fixtureDef.shape = islandShape;
        fixtureDef.friction = 0f;
        fixtureDef.filter.categoryBits = Zombie.STATIC_BIT;
        fixtureDef.filter.maskBits = Zombie.PLAYER_BIT | Zombie.AMMO_PACK_BIT;
        body.createFixture(fixtureDef);
    }

    private void createAccidentSensor(FixtureDef fixtureDef) {
        fixtureDef.filter.categoryBits = Zombie.CAR_BIT;
        fixtureDef.filter.maskBits = Zombie.PLAYER_BIT;
        fixtureDef.shape = accidentLine;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
    }

    protected void addAmmoPack(World world, float x, float y, Texture texture) {
        ammoPack = new AmmoPack(world, x, y, texture);
    }

    @Override
    public void update(float delta) {
        move(delta);
        updateSpritePosition();
        ammoPack.updateSpritePosition();
    }

    @Override
    public void stopMoving() {
        body.setAwake(false);
        ammoPack.body.setAwake(false);
    }

    @Override
    public void move(float delta) {
        float calculatedSpeed = speed * delta;
        body.setLinearVelocity(new Vector2(-calculatedSpeed, 0f));
        ammoPack.move(calculatedSpeed);
    }

    @Override
    protected void updateSpritePosition() {
        setPosition(body.getPosition().x - getWidth() / 2,
                (body.getPosition().y) - getHeight() / 2);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (isObstacleOnScreen()) {
            draw(batch);
            ammoPack.render(batch);
        }
    }
}
