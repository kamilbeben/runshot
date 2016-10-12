package com.kamilbeben.zombiestorm.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 10.10.16.
 */
public class IslandLong extends Island {

    public IslandLong(World world, float x, float y, int speedLevel) {
        super(new Texture("island_long.png"), speedLevel);
        setSize(getWidth() * 2 / Zombie.PPM, 16f / Zombie.PPM);
        setupBody(x, y, world);
        updateSpritePosition();
    }

    @Override
    protected void setupBody(float x, float y, World world) {

        PolygonShape islandShape = new PolygonShape();
        islandShape.setAsBox(200f / Zombie.PPM, 12f / Zombie.PPM);

        EdgeShape jumpLine = new EdgeShape();
        jumpLine.set(new Vector2(-205 / Zombie.PPM, 28 / Zombie.PPM), new Vector2(210 / Zombie.PPM, 28 / Zombie.PPM));

        createBody(x, y, islandShape, jumpLine, world);


    }
}
