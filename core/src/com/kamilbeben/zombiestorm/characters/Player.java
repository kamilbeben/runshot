package com.kamilbeben.zombiestorm.characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.TextureHolder;

/**
 * Created by bezik on 17.09.16.
 */
public class Player {


    private Boolean shooting = false;
    private Boolean jumping = false;
    private Boolean stumble = false;

    private Body body;

    private boolean alive = true;

    private static final float firstJumpForce = 5f;
    private static final float secondJumpForce = 3f;
    private int jumpCounter = 0;

    private PlayerRenderer playerRenderer;

    private int bullets = 6;


    public Player(World world, TextureHolder textureHolder) {
        setupBody(world);
        playerRenderer = new PlayerRenderer(textureHolder);
    }

    private void setupBody(World world) {
        defineBody(world);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = setupShape();
        createMainBody(fixtureDef);
        createSensor(fixtureDef);
        body.setUserData(this);
        fixtureDef.shape.dispose();
    }

    private void defineBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(64 / Zombie.PPM, 97 / Zombie.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
    }

    private PolygonShape setupShape() {
        PolygonShape polygon = new PolygonShape();
        polygon.setAsBox(15 / Zombie.PPM, 55 / Zombie.PPM);
        return polygon;
    }

    private void createMainBody(FixtureDef fixtureDef) {
        fixtureDef.filter.categoryBits = Zombie.PLAYER_BIT;
        fixtureDef.filter.maskBits = Zombie.ENEMY_BIT | Zombie.STATIC_BIT | Zombie.HOLE_BIT | Zombie.WALLS_BIT | Zombie.STUMBLE_BIT | Zombie.CAR_BIT;
        fixtureDef.friction = 0.2f;
        body.createFixture(fixtureDef);
    }

    private void createSensor(FixtureDef fixtureDef) {
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Zombie.PLAYER_BIT;
        fixtureDef.filter.maskBits = Zombie.GROUND_BIT | Zombie.HOLE_BIT | Zombie.AMMO_PACK_BIT | Zombie.CAR_BIT;
        body.createFixture(fixtureDef);
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

    public void pickAmmo() {
        bullets = 6;
    }

    public void update(float delta) {
        playerRenderer.update(delta, shooting, jumping, body);
        if (shooting) {
            shooting = !playerRenderer.isShootingOver();
        }
        if (jumping) {
            jumping = !playerRenderer.isJumpingOver();
        } else {
            jumpCounter = 0;
        }
    }

    public void startMoving() {
        playerRenderer.startMoving();
    }

    public void hitByCar() {
        if (!stumble) {
            playerRenderer.setCarAccident();
            body.applyLinearImpulse(new Vector2(-0.5f, 0), new Vector2(body.getWorldCenter().x + 20f / Zombie.PPM, body.getWorldCenter().y), true);
            collisionsOff();
        }
    }


    public void jumpFirst() {
        if (alive) {
            body.applyLinearImpulse(new Vector2(0, firstJumpForce), body.getWorldCenter(), true);
            jumping = true;
            jumpCounter = 1;
        }
    }

    public void jumpSecond() {
        if (jumpCounter == 1) {
            body.applyLinearImpulse(new Vector2(0, secondJumpForce), body.getWorldCenter(), true);
            jumpCounter = 2;
        }
    }

    public boolean isJumping() {
        return jumping;
    }


    public void stumble() {
        dead();
        playerRenderer.setStumble();
        stumble = true;
        body.applyLinearImpulse(new Vector2(3.5f, 2f), new Vector2(body.getWorldCenter().x + 20f / Zombie.PPM, body.getWorldCenter().y), true);
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

    public void playerFallingDown() {
        dead();
        body.applyLinearImpulse(new Vector2(25f / Zombie.PPM, 0f / Zombie.PPM), body.getWorldCenter(), true);
        collisionsOff();
    }

    private void collisionsOff() {
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

    public void dispose() {
    }
}
