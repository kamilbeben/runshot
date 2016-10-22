package com.kamilbeben.zombiestorm.graphicalfireworks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.TextureHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bezik on 13.10.16.
 */
public class GraphicsOverlay {

    private float lastRotationTime = 0f;
    private float lastDirectionChange = 0f;
    private float timer = 0f;


    List<Fireflies> fireflies;

    private Sprite palette;


    public GraphicsOverlay(World world, TextureHolder textureHolder) {
        setupFireflies(world, textureHolder);
        setupColorPalette(textureHolder);
    }

    private void setupFireflies(World world, TextureHolder textureHolder) {
        fireflies = new ArrayList<Fireflies>();
        fireflies.add(new Fireflies(world, textureHolder.GAME_EXTRAS_FIREFLY_GREEN));
        fireflies.add(new Fireflies(world, textureHolder.GAME_EXTRAS_FIREFLY_GREEN));
        fireflies.add(new Fireflies(world, textureHolder.GAME_EXTRAS_FIREFLY_TRANSPARENT));
        fireflies.add(new Fireflies(world, textureHolder.GAME_EXTRAS_FIREFLY_BLUE));
        fireflies.add(new Fireflies(world, textureHolder.GAME_EXTRAS_FIREFLY_BLUE));

    }

    private void setupColorPalette(TextureHolder textureHolder) {
        palette = new Sprite(textureHolder.GAME_EXTRAS_PALETTE);
        palette.setSize(Zombie.WIDTH / Zombie.PPM, Zombie.HEIGHT / Zombie.PPM);
    }

    public void update(float timer) {
        if (timer > lastRotationTime + 0.1f) {
            lastRotationTime = timer;
            rotateFireflies();
        }

        if (timer > lastDirectionChange + 1f) {
            lastDirectionChange = timer;
            changeFirefliesDirection();
        }

        for (Fireflies tmp : fireflies) {
            tmp.update();
        }
    }


    private void changeFirefliesDirection() {
        for (Fireflies tmp : fireflies) {
            tmp.changeDirection();
        }
    }

    private void rotateFireflies() {
        for (Fireflies tmp : fireflies) {
            tmp.rotate();
        }
    }

    public void render(SpriteBatch batch) {
        palette.draw(batch);
        for (Fireflies tmp : fireflies) {
            tmp.render(batch);
        }
    }
}
