package com.kamilbeben.zombiestorm.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.HolePosition;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 01.10.16.
 */
public class HoleLong extends Hole {


    public HoleLong(World world, float x, float y, int speedLevel, Texture texture) {
        super(texture, speedLevel);
        setSize(getWidth() / Zombie.PPM, getHeight() / Zombie.PPM);
        setupBody(x, y, world);
        updateSpritePosition();
    }

    @Override
    protected void setupBody(float x, float y, World world) {

        shape.setAsBox(45 / Zombie.PPM, 1 / Zombie.PPM);

        createBody(x, y, shape, world);
    }

}
