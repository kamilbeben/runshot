package com.kamilbeben.zombiestorm.obstacles;

import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.TextureHolder;

/**
 * Created by bezik on 30.09.16.
 */
public class HoleShort extends Hole {


    public HoleShort(World world, float x, float y, int speedLevel, TextureHolder textureHolder) {
        super(textureHolder.GAME_OBSTACLE_HOLE_SHORT, speedLevel);
        setSize(getWidth() / Zombie.PPM, getHeight() / Zombie.PPM);
        setupBody(x, y, world);
        updateSpritePosition();
    }

    @Override
    protected void setupBody(float x, float y, World world) {

        shape.setAsBox(25 / Zombie.PPM, 1 / Zombie.PPM);

        createBody(x, y, shape, world);
    }
}
