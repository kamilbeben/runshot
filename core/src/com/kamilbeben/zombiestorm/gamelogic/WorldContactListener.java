package com.kamilbeben.zombiestorm.gamelogic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.characters.Enemy;
import com.kamilbeben.zombiestorm.characters.Player;
import com.kamilbeben.zombiestorm.objects.AmmoPack;
import com.kamilbeben.zombiestorm.obstacles.Hole;

/**
 * Created by bezik on 18.09.16.
 */
public class WorldContactListener implements ContactListener {

    public World world;
    public int playerFootContacts = 0;
    public boolean playerCollidesWithLeftWall = false;

    public WorldContactListener(World world) {
        this.world = world;
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        checkForCollisionBetweenZombieAndShotgunShell(a, b);
        checkForCollisionsBetweeenPlayerAndHoles(a, b);
        checkForCollisionsBetweeenPlayerAndAmmoPacks(a, b);
        checkForCollisionsBetweeenZombiesAndHoles(a, b);
        checkForCollisionBetweenPlayerAndGround(contact.getFixtureA(), contact.getFixtureB());
        checkIfPlayerCollidesWithLeftWall(contact.getFixtureA(), contact.getFixtureB());
    }

    private void checkForCollisionsBetweeenPlayerAndHoles(Body a, Body b) {
        Body player = (a.getUserData() instanceof Player) ? a : b;
        Body hole = (a.getUserData() instanceof Hole) ? a : b;

        if (hole.getUserData() instanceof Hole && player.getUserData() instanceof Player) {
            ((Hole) hole.getUserData()).collisionOff();
        }
    }


    private void checkForCollisionsBetweeenZombiesAndHoles(Body a, Body b) {
        Body enemy = (a.getUserData() instanceof Enemy) ? a : b;
        Body hole = (a.getUserData() instanceof Hole) ? a : b;

        if (hole.getUserData() instanceof Hole && enemy.getUserData() instanceof Enemy) {
            ((Enemy) enemy.getUserData()).killEnemy();
        }
    }

    private void checkForCollisionBetweenPlayerAndGround(Fixture a, Fixture b) {
        if ((a.getFilterData().categoryBits == Zombie.PLAYER_BIT && b.getFilterData().categoryBits == Zombie.GROUND_BIT) ||
                (b.getFilterData().categoryBits == Zombie.PLAYER_BIT && a.getFilterData().categoryBits == Zombie.GROUND_BIT)) {
            playerFootContacts++;
        }
    }

    private void checkIfPlayerCollidesWithLeftWall(Fixture a, Fixture b) {
        if ((a.getFilterData().categoryBits == Zombie.PLAYER_BIT && b.getFilterData().categoryBits == Zombie.LEFT_CORNER) ||
                (b.getFilterData().categoryBits == Zombie.PLAYER_BIT && a.getFilterData().categoryBits == Zombie.LEFT_CORNER)) {
            playerCollidesWithLeftWall = true;
        }
    }

    private void checkForCollisionBetweenZombieAndShotgunShell(Body a, Body b) {
        Body shotgunShell = (a.getUserData() instanceof ShotgunShell) ? a : b;
        Body enemy = (a.getUserData() instanceof Enemy) ? a : b;

        if (enemy.getUserData() instanceof Enemy && shotgunShell.getUserData() instanceof ShotgunShell) {
            ((Enemy) enemy.getUserData()).killEnemy();
            ((ShotgunShell) shotgunShell.getUserData()).setToHarmless();
        }
    }

    private void checkForCollisionsBetweeenPlayerAndAmmoPacks(Body a, Body b) {
        Body player = (a.getUserData() instanceof Player) ? a : b;
        Body ammo = (a.getUserData() instanceof AmmoPack) ? a : b;

        if (ammo.getUserData() instanceof AmmoPack && player.getUserData() instanceof Player) {
            ((AmmoPack) ammo.getUserData()).stopRendering();
            ((Player) player.getUserData()).pickAmmo(); //TODO sound here
        }
    }

    @Override
    public void endContact(Contact contact) {
        checkForCollisionEndBetweenPlayerAndGround(contact.getFixtureA(), contact.getFixtureB());
    }

    private void checkForCollisionEndBetweenPlayerAndGround(Fixture a, Fixture b) {
        if ((a.getFilterData().categoryBits == Zombie.PLAYER_BIT && b.getFilterData().categoryBits == Zombie.GROUND_BIT) ||
                (b.getFilterData().categoryBits == Zombie.PLAYER_BIT && a.getFilterData().categoryBits == Zombie.GROUND_BIT)) {
            playerFootContacts--;
        }
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
