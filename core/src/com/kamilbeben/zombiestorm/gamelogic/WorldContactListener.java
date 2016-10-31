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
import com.kamilbeben.zombiestorm.objects.SingleShell;
import com.kamilbeben.zombiestorm.obstacles.Hole;

/**
 * Created by bezik on 18.09.16.
 */
public class WorldContactListener implements ContactListener {

    public int playerFootContacts = 0;

    public WorldContactListener() {
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        checkForCollisionsBetweeenPlayerAndHoles(a, b);
        checkForCollisionsBetweeenPlayerAndAmmoPacks(a, b);
        checkForCollisionsBetweeenPlayerAndSingleShell(a, b);
        checkForCollisionsBetweenPlayerAndStumbleLines(contact.getFixtureA(), contact.getFixtureB());
        checkForCollisionsBetweeenPlayerAndCar(a, b);
        checkForCollisionBetweenPlayerAndGround(contact.getFixtureA(), contact.getFixtureB());
        checkIfPlayerCollidesWithLeftWall(contact.getFixtureA(), contact.getFixtureB());
        checkForCollisionBetweenPlayerAndIslands(contact.getFixtureA(), contact.getFixtureB());

        checkForCollisionsBetweeenZombiesAndHoles(a, b);
        checkForCollisionBetweenZombieAndCar(contact.getFixtureA(), contact.getFixtureB());
    }

    private void checkForCollisionsBetweenPlayerAndStumbleLines(Fixture a, Fixture b) {
        Body player = (a.getFilterData().categoryBits == Zombie.PLAYER_BIT) ? a.getBody() : b.getBody();
        Body enemy = (a.getFilterData().categoryBits == Zombie.STUMBLE_BIT) ? a.getBody() : b.getBody();
        if ((a.getFilterData().categoryBits == Zombie.PLAYER_BIT && b.getFilterData().categoryBits == Zombie.STUMBLE_BIT) ||
                (b.getFilterData().categoryBits == Zombie.PLAYER_BIT && a.getFilterData().categoryBits == Zombie.STUMBLE_BIT)) {

            if (player.getUserData() instanceof Player) {
                if (enemy.getUserData() instanceof Car) {
                    ((Player) player.getUserData()).stumble();
                } else if (enemy.getUserData() instanceof Enemy) {
                    ((Player) player.getUserData()).onHitEnemyHead();
                    ((Enemy) enemy.getUserData()).headHit();
                }
            }
        }
    }

    private void checkForCollisionsBetweeenPlayerAndHoles(Body a, Body b) {
        Body player = (a.getUserData() instanceof Player) ? a : b;
        Body hole = (a.getUserData() instanceof Hole) ? a : b;

        if (hole.getUserData() instanceof Hole && player.getUserData() instanceof Player) {
            ((Player) player.getUserData()).playerFallingDown();
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

    private void checkForCollisionBetweenPlayerAndIslands(Fixture a, Fixture b) {
        if ((a.getFilterData().categoryBits == Zombie.PLAYER_BIT && b.getFilterData().categoryBits == Zombie.CAR_BIT) ||
                (b.getFilterData().categoryBits == Zombie.PLAYER_BIT && a.getFilterData().categoryBits == Zombie.CAR_BIT)) {
            Body player = (a.getBody().getUserData() instanceof Player) ? a.getBody() : b.getBody();
            ((Player) player.getUserData()).hitByCar();
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
            Body player = (a.getBody().getUserData() instanceof Player) ? a.getBody() : b.getBody();
            ((Player) player.getUserData()).touchLeftWall();
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
            ((Player) player.getUserData()).pickAmmo();
        }
    }

    private void checkForCollisionsBetweeenPlayerAndSingleShell(Body a, Body b) {
        Body player = (a.getUserData() instanceof Player) ? a : b;
        Body shell = (a.getUserData() instanceof SingleShell) ? a : b;

        if (shell.getUserData() instanceof SingleShell && player.getUserData() instanceof Player) {
            if (!((SingleShell) shell.getUserData()).alreadyUsed()) {
                ((Player) player.getUserData()).pickSingleShell();
            }
            ((SingleShell) shell.getUserData()).stopRendering();
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
