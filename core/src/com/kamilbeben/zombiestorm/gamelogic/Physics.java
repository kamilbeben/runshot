package com.kamilbeben.zombiestorm.gamelogic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

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

    private List<ShotgunShell> shotgunShells;
    private Texture textureShell;

    public Physics(Texture texture) {
        textureShell = texture;
        initializeCollisionDetection();
        shotgunShells = new ArrayList<ShotgunShell>();
    }

    public void update(float delta) {
        world.step(delta, 6, 2);
        for (int i = 0; i< shotgunShells.size(); i++) {
            if (shotgunShells.get(i).disposeIfOutOfMap(world)) {
                shotgunShells.remove(i);
            }
        }
    }

    public boolean playerCollidesWithLeftWall() {
        return contactListener.playerCollidesWithLeftWall;
    }

    public void renderShells(SpriteBatch batch) {
        for (ShotgunShell tmp : shotgunShells) {
            tmp.render(batch);
        }
    }

    public void renderDebug(OrthographicCamera camera) {
        debugRenderer.render(world, camera.combined);
    }


    private void initializeCollisionDetection() {
        contactListener = new WorldContactListener(world);
        world.setContactListener(contactListener);
    }

    public void shotgunShot(int yAxis, float playerYPosition) {
        shotgunShells.add(new ShotgunShell(world, textureShell, yAxis, playerYPosition));
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



