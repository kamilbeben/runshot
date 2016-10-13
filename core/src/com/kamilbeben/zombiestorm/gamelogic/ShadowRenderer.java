package com.kamilbeben.zombiestorm.gamelogic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;

import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * Created by bezik on 13.10.16.
 */
public class ShadowRenderer {

    private RayHandler rayHandler;
    private Firefly firefly;


    public ShadowRenderer(World world) {
        rayHandler = new RayHandler(new World(new Vector2(0, 0), true), 800, 480);
        rayHandler.setShadows(true);
        rayHandler.setAmbientLight(new Color(0, 0.05f, 0.07f, 1));
        firefly = new Firefly(rayHandler);

    }

    public void render(OrthographicCamera camera) {
        updateFireflies();
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
    }

    private void updateFireflies() {
        firefly.update();
    }

    public void dispose() {
        rayHandler.dispose();
    }
}
