package com.kamilbeben.zombiestorm.graphicalfireworks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.tools.TextureHolder;

import java.util.List;

/**
 * Created by bezik on 13.10.16.
 */
public class GDXRenderer {

    private float lastRotationTime = 0f;
    private float lastDirectionChange = 0f;
    private float timer = 0f;

    List<Fireflies> fireflies;


    public GDXRenderer(List<Fireflies> fireflies, World world, TextureHolder textureHolder) {
        this.fireflies = fireflies;
        fireflies.add(new Fireflies(world, textureHolder.GAME_EXTRAS_FIREFLY));
        fireflies.add(new Fireflies(world, textureHolder.GAME_EXTRAS_FIREFLY));
        fireflies.add(new Fireflies(world, textureHolder.GAME_EXTRAS_FIREFLY));
        fireflies.add(new Fireflies(world, textureHolder.GAME_EXTRAS_FIREFLY));
    }

    public void update(float delta) {
        timer += delta;
        if (timer > lastRotationTime + 0.1f) {
            rotateFireflies();
        }

        if (timer > lastDirectionChange + 1f) {
            changeFirefliesDirection();
        }

        for (Fireflies tmp : fireflies) {
            tmp.update();
        }
    }

    private void changeFirefliesDirection() {
        lastDirectionChange = timer;
        for (Fireflies tmp : fireflies) {
            tmp.changeDirection();
        }
    }

    private void rotateFireflies() {
        lastRotationTime = timer;
        for (Fireflies tmp : fireflies) {
            tmp.rotate();
        }
    }

    public void render(SpriteBatch batch) {
        for (Fireflies tmp : fireflies) {
            tmp.render(batch);
        }
    }
}
