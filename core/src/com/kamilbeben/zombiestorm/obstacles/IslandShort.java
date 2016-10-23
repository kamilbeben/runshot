package com.kamilbeben.zombiestorm.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.objects.AmmoPack;
import com.kamilbeben.zombiestorm.tools.TextureHolder;

/**
 * Created by bezik on 08.10.16.
 */
public class IslandShort extends Island {


    public IslandShort(World world, float x, float y, int speedLevel, TextureHolder textureHolder) {
        super(textureHolder.GAME_OBSTACLE_ISLAND_SHORT, speedLevel);
        setSize(getWidth() / Zombie.PPM, getHeight() / Zombie.PPM);
        setupBody(x, y, world);
        addAmmoPack(world, x, y, textureHolder.GAME_EXTRAS_AMMOPACK);
        updateSpritePosition();
    }

    @Override
    protected void setupBody(float x, float y, World world) {

        islandShape.setAsBox(100f / Zombie.PPM, 12f / Zombie.PPM);
        jumpLine.set(new Vector2(-105 / Zombie.PPM, 28 / Zombie.PPM), new Vector2(110 / Zombie.PPM, 28 / Zombie.PPM));
        accidentLine.set(new Vector2(-102 / Zombie.PPM, 5 / Zombie.PPM), new Vector2(-102 / Zombie.PPM, -5 / Zombie.PPM));

        createBody(x, y, world);
    }
}
