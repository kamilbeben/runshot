package com.kamilbeben.zombiestorm.obstacles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.TextureHolder;

/**
 * Created by bezik on 23.10.16.
 */
public class StoneSmall extends Stone {


    public StoneSmall(World world, float x, float y, int speedLevel, TextureHolder textureHolder) {
        super(textureHolder.GAME_OBSTACLE_STONE_SMALL, speedLevel);
        setSize(getWidth() / Zombie.PPM, getHeight() / Zombie.PPM);
        setupBody(x, y, world);
        updateSpritePosition();
    }

    @Override
    protected void setupBody(float x, float y, World world) {

        islandShape.setAsBox(10 / Zombie.PPM, 56f / Zombie.PPM);
        jumpLine.set(new Vector2(-10 / Zombie.PPM, 64 / Zombie.PPM), new Vector2(10 / Zombie.PPM, 64 / Zombie.PPM));

        createBody(x, y, world);
    }

}
