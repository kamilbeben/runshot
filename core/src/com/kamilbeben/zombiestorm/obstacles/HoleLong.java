package com.kamilbeben.zombiestorm.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.HolePosition;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 01.10.16.
 */
public class HoleLong extends Hole {



    public HoleLong(World world, float x, float y, float timer) {
        super(new Texture("hole_long.png"), timer);
        setSize(getWidth() / Zombie.PPM, getHeight() / Zombie.PPM);
        this.world = world;
        setupBody(x, y);
        updateSpritePosition();
    }

    @Override
    protected void setupBody(float x, float y) {

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(40 / Zombie.PPM, 1 / Zombie.PPM);

        createBody(x, y, shape);
    }


    @Override
    public HolePosition getStartTileAndNumberOfTiles() {
        float start = ((body.getPosition().x - getWidth() / 2 / Zombie.PPM - 134 / Zombie.PPM) * 100 / 32);
        return new HolePosition(Tools.roundTilePosition(start), 8);

    }
}
