package com.kamilbeben.zombiestorm.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 17.09.16.
 */
public class Player {


    private Boolean shooting = false;
    private Boolean jumping = false;

    private Body body;

    private boolean alive = true;

    private static final float jumpForce = 6.5f;
    private int zombieKilled = 0;

    private PlayerRenderer playerRenderer;

    private int bullets = 6;


    public Player(World world) {
        setupBody(world);
        playerRenderer = new PlayerRenderer(new Texture("player.png"));
        playerRenderer.setupLooks();
    }

    private void setupBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(64 / Zombie.PPM, 97 / Zombie.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();

        PolygonShape polygon = new PolygonShape();
        polygon.setAsBox(25 / Zombie.PPM, 55 / Zombie.PPM);
        fixtureDef.shape = polygon;

        fixtureDef.filter.categoryBits = Zombie.PLAYER_BIT;
        fixtureDef.filter.maskBits = Zombie.ENEMY_BIT | Zombie.STATIC_BIT | Zombie.HOLE_BIT | Zombie.LEFT_CORNER;

        fixtureDef.friction = 0.2f;
        body.createFixture(fixtureDef);


        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Zombie.PLAYER_BIT;
        fixtureDef.filter.maskBits = Zombie.GROUND_BIT | Zombie.HOLE_BIT;
        body.createFixture(fixtureDef);

        body.setUserData(this);
    }

    public boolean shotgunShot() {
        if (bullets > 0 ){
            shooting = true;
            bullets--;
            return true;
        } else {
            return false;
        }
    }

    public void update(float delta) {
        playerRenderer.update(delta, shooting, jumping, body);
        if (shooting) {
            shooting = !playerRenderer.isShootingOver();
        }
        if (jumping) {
            jumping = !playerRenderer.isJumpingOver();
        }
    }


    public void jump() {
        body.applyLinearImpulse(new Vector2(0, jumpForce), body.getWorldCenter(), true);
        jumping = true;
    }


    public void render(SpriteBatch batch) {
        playerRenderer.render(batch);
    }

    public boolean isAlive() {
        return alive;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void collisionsOff() {
        dead();
        Array<Fixture> fixtures = body.getFixtureList();
        for (Fixture tmp : fixtures) {
            tmp.setSensor(true);
        }
    }

    public int getBulletsAmount() {
        return bullets;
    }

    public void setSpeedLevel(int speedLevel) {
        playerRenderer.setSpeedLevel(speedLevel);
    }

    public void dead() {
        alive = false;
    }

    public void zombieGotShot() {
        zombieKilled++;
    }

    public int howManyZombiesDidIKilled() {
        return zombieKilled;
    }
    public void dispose() {
    }
}
