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
 * Created by bezik on 08.10.16.
 */
public class IslandShort extends Island {

    public IslandShort(World world, float x, float y, int speedLevel) {
        super(new Texture("island_short.png"), speedLevel);
        setSize(getWidth() / Zombie.PPM, 16f / Zombie.PPM);
        setupBody(x, y, world);
        updateSpritePosition();
    }

    @Override
    protected void setupBody(float x, float y, World world) {

        PolygonShape islandShape = new PolygonShape();
        islandShape.setAsBox(100f / Zombie.PPM, 12f / Zombie.PPM);

        EdgeShape jumpLine = new EdgeShape();
        jumpLine.set(new Vector2(-105 / Zombie.PPM, 28 / Zombie.PPM), new Vector2(110 / Zombie.PPM, 28 / Zombie.PPM));

        createBody(x, y, islandShape, jumpLine, world);
    }
}
