package com.kamilbeben.zombiestorm.gamelogic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.characters.Car;
import com.kamilbeben.zombiestorm.characters.Enemy;
import com.kamilbeben.zombiestorm.characters.Monkey;
import com.kamilbeben.zombiestorm.characters.Player;
import com.kamilbeben.zombiestorm.characters.Walker;
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

        checkForCollisionsBetweeenPlayerAndHoles(a, b);
        checkForCollisionsBetweeenPlayerAndAmmoPacks(a, b);
        checkForCollisionsBetweeenPlayerAndCar(a, b);
        checkForCollisionBetweenPlayerAndGround(contact.getFixtureA(), contact.getFixtureB());
        checkIfPlayerCollidesWithLeftWall(contact.getFixtureA(), contact.getFixtureB());
        checkForCollisionsBetweenPlayerAndStumbleLines(contact.getFixtureA(), contact.getFixtureB());

        checkForCollisionsBetweeenZombiesAndHoles(a, b);
        checkForCollisionBetweenZombieAndCar(contact.getFixtureA(), contact.getFixtureB());
    }

    private void checkForCollisionsBetweenPlayerAndStumbleLines(Fixture a, Fixture b) {
        Body player = (a.getFilterData().categoryBits == Zombie.PLAYER_BIT) ? a.getBody() : b.getBody();
        if ((a.getFilterData().categoryBits == Zombie.PLAYER_BIT && b.getFilterData().categoryBits == Zombie.STUMBLE_BIT) ||
                (b.getFilterData().categoryBits == Zombie.PLAYER_BIT && a.getFilterData().categoryBits == Zombie.STUMBLE_BIT)) {
            if (player.getUserData() instanceof Player) {
                ((Player) player.getUserData()).stumble();
            }
        }
    }

    private void checkForCollisionsBetweeenPlayerAndHoles(Body a, Body b) {
        Body player = (a.getUserData() instanceof Player) ? a : b;
        Body hole = (a.getUserData() instanceof Hole) ? a : b;

        if (hole.getUserData() instanceof Hole && player.getUserData() instanceof Player) {
            ((Hole) hole.getUserData()).collisionOff();
        }
    }

    private void checkForCollisionsBetweeenPlayerAndCar(Body a, Body b) {
        Body player = (a.getUserData() instanceof Player) ? a : b;
        Body car = (a.getUserData() instanceof Car) ? a : b;

        if (car.getUserData() instanceof Car && player.getUserData() instanceof Player) {
            ((Player) player.getUserData()).hitByCar();
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
        if ((a.getFilterData().categoryBits == Zombie.PLAYER_BIT && b.getFilterData().categoryBits == Zombie.WALLS_BIT) ||
                (b.getFilterData().categoryBits == Zombie.PLAYER_BIT && a.getFilterData().categoryBits == Zombie.WALLS_BIT)) {
            playerCollidesWithLeftWall = true;
        }
    }

    private void checkForCollisionBetweenZombieAndCar(Fixture a, Fixture b) {
        if ((a.getFilterData().categoryBits == Zombie.ENEMY_BIT && b.getFilterData().categoryBits == Zombie.CAR_BIT) ||
                (b.getFilterData().categoryBits == Zombie.ENEMY_BIT && a.getFilterData().categoryBits == Zombie.CAR_BIT)) {
            Fixture zombie = (a.getFilterData().categoryBits == Zombie.CAR_BIT) ? b : a;
            ((Enemy) zombie.getBody().getUserData()).carAccident();
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
