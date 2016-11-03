package com.kamilbeben.zombiestorm.gamelogic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.characters.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bezik on 17.09.16.
 */
public class Physics {

    private Vector2 gravity = new Vector2(0, -10);
    public World world = new World(gravity, true);
    private PhysicsBasicWorld basicWorld = new PhysicsBasicWorld(world);
    public WorldContactListener contactListener;
    public Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();


    public Physics(Zombie game) {
        initializeCollisionDetection(game);
    }

    public void update(float delta) {
        world.step(delta, 8, 3);
    }

    public void renderDebug(OrthographicCamera camera) {
        debugRenderer.render(world, camera.combined);
    }


    private void initializeCollisionDetection(Zombie game) {
        contactListener = new WorldContactListener(game);
        world.setContactListener(contactListener);
    }

    public boolean canPlayerJump() {
        if (contactListener.playerFootContacts > 0) {
            return true;
        } else return false;
    }

    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }

}



