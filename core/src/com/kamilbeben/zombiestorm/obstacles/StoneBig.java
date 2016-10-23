package com.kamilbeben.zombiestorm.obstacles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.TextureHolder;

/**
 * Created by bezik on 23.10.16.
 */
public class StoneBig extends Stone{

    public StoneBig(World world, float x, float y, int speedLevel, TextureHolder textureHolder) {
        super(textureHolder.GAME_OBSTACLE_STONE_BIG, speedLevel);
        setSize(getWidth() / Zombie.PPM, getHeight() / Zombie.PPM);
        setupBody(x, y, world);
        updateSpritePosition();
    }

    @Override
    protected void setupBody(float x, float y, World world) {

        islandShape.setAsBox(15 / Zombie.PPM, 100 / Zombie.PPM);
        jumpLine.set(new Vector2(-15 / Zombie.PPM, 108 / Zombie.PPM), new Vector2(15 / Zombie.PPM, 108 / Zombie.PPM));

        createBody(x, y, world);
    }
}
