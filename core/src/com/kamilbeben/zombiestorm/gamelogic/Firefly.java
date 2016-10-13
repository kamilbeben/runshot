package com.kamilbeben.zombiestorm.gamelogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.kamilbeben.zombiestorm.Zombie;

import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * Created by bezik on 13.10.16.
 */
public class Firefly {

    private PointLight outerGlow;
    private PointLight insideGlow;

    public Firefly(RayHandler rayHandler) {
        outerGlow = new PointLight(rayHandler, 6, Color.GREEN, 30f / Zombie.PPM, 150 / Zombie.PPM, 200 / Zombie.PPM);
        insideGlow = new PointLight(rayHandler, 4, new Color(1, 1, 1, 1), 10f / Zombie.PPM, 150 / Zombie.PPM, 200 / Zombie.PPM);
    }

    public void update() {
        insideGlow.setPosition(outerGlow.getX() + (1 * Gdx.graphics.getDeltaTime()), outerGlow.getY());
        outerGlow.setPosition(outerGlow.getX() + (1 * Gdx.graphics.getDeltaTime()), outerGlow.getY());
    }
}
