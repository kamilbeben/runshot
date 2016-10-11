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
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x * 1/ Zombie.PPM, y * 1/ Zombie.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        EdgeShape line = new EdgeShape();
        line.set(new Vector2(-40 / Zombie.PPM, 0 / Zombie.PPM), new Vector2(40 / Zombie.PPM, 0 / Zombie.PPM));
        fixtureDef.shape = line;

        fixtureDef.filter.categoryBits = Zombie.HOLE_BIT;
        fixtureDef.filter.maskBits = Zombie.STATIC_BIT;

        body.createFixture(fixtureDef);
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Zombie.HOLE_BIT;
        fixtureDef.filter.maskBits = Zombie.PLAYER_BIT | Zombie.ENEMY_BIT;

        body.createFixture(fixtureDef);

        line.set(new Vector2(100 / Zombie.PPM, 10 / Zombie.PPM), new Vector2(120 / Zombie.PPM, 10 / Zombie.PPM));
        fixtureDef.filter.categoryBits = Zombie.PREHOLE_BIT;
        fixtureDef.filter.maskBits = Zombie.ENEMY_BIT;
        fixtureDef.shape = line;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);
        body.setUserData(this);
    }


    @Override
    public HolePosition getStartTileAndNumberOfTiles() {
        float start = ((body.getPosition().x - getWidth() / 2 / Zombie.PPM - 134 / Zombie.PPM) * 100 / 32);
        return new HolePosition(Tools.roundTilePosition(start), 8);

    }
}
