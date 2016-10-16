package com.kamilbeben.zombiestorm.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 07.10.16.
 */
public class PlayerRenderer {

    public enum State {RUNNING, SHOOTING, JUMPING, STANDING}

    public boolean gameStarted = true;

    public State currentState;
    public State previousState;

    private float stateTimer_UPPERBODY = 0f;
    private float stateTimer_LOWERBODY = 0f;

    private Sprite upperBody;
    private Sprite lowerBody;

    public Animation animationRunningUpperBody;
    public Animation animationRunningLowerBody;
    public Animation animationShootingUppderBody;
    public Animation animationJumpingUpperBody;
    public Animation animationJumpingLowerBody;
    public Animation animationSteadyUpperBody;
    public Animation animationSteadyLowerBody;


    public PlayerRenderer(Texture playerTextureMap) {
        upperBody = new Sprite(playerTextureMap);
        lowerBody = new Sprite(playerTextureMap);
    }

    public void setupLooks() {
        int yPosition = 0*113;
        upperBody.setBounds(32 / Zombie.PPM, 80 / Zombie.PPM, 101 / Zombie.PPM, 113 / Zombie.PPM);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(upperBody.getTexture(), i * 101, yPosition, 101, 113));
        }
        animationRunningUpperBody = new Animation(0.1f, frames);
        frames.clear();

        yPosition = 1*113;
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(upperBody.getTexture(), i * 101, yPosition, 101, 113));
        }
        animationShootingUppderBody = new Animation(0.1f, frames);
        frames.clear();

        yPosition = 2*113;
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(upperBody.getTexture(), i * 101, yPosition, 101, 113));
        }
        animationJumpingUpperBody = new Animation(0.1f, frames);
        frames.clear();

        lowerBody.setBounds(32 / Zombie.PPM, 30 / Zombie.PPM, 101 / Zombie.PPM, 67 / Zombie.PPM);
        yPosition = 339;
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(lowerBody.getTexture(), i * 101, yPosition, 101, 67));
        }
        animationRunningLowerBody = new Animation(0.1f, frames);
        frames.clear();

        yPosition = 406;
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(lowerBody.getTexture(), i * 101, yPosition, 101, 67));
        };

        animationJumpingLowerBody = new Animation(0.1f, frames);
        frames.clear();

        yPosition = 471;
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(lowerBody.getTexture(), i * 101, yPosition, 101, 113));
        };

        animationSteadyUpperBody = new Animation(0.1f, frames);
        frames.clear();

        yPosition = 584;
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(lowerBody.getTexture(), i * 101, yPosition, 101, 67));
        };

        animationSteadyLowerBody = new Animation(0.1f, frames);
        frames.clear();
    }

    public void setSpeedLevel(int speedLevel) {
        float levelOne = 0.1f;
        switch (speedLevel) {
            default:
            case 1:
                animationRunningLowerBody.setFrameDuration(levelOne / Tools.speedMultiplier_1);
                animationRunningUpperBody.setFrameDuration(levelOne / Tools.speedMultiplier_1);
                animationShootingUppderBody.setFrameDuration(levelOne / Tools.speedMultiplier_1);
                break;
            case 2:
                animationRunningLowerBody.setFrameDuration(levelOne / Tools.speedMultiplier_2);
                animationRunningUpperBody.setFrameDuration(levelOne / Tools.speedMultiplier_2);
                animationShootingUppderBody.setFrameDuration(levelOne / Tools.speedMultiplier_2);
                break;
            case 3:
                animationRunningLowerBody.setFrameDuration(levelOne / Tools.speedMultiplier_3);
                animationRunningUpperBody.setFrameDuration(levelOne / Tools.speedMultiplier_3);
                animationShootingUppderBody.setFrameDuration(levelOne / Tools.speedMultiplier_3);
                break;
            case 4:
                animationRunningLowerBody.setFrameDuration(levelOne / Tools.speedMultiplier_4);
                animationRunningUpperBody.setFrameDuration(levelOne / Tools.speedMultiplier_4);
                animationShootingUppderBody.setFrameDuration(levelOne / Tools.speedMultiplier_4);
                break;
            case 5:
                animationRunningLowerBody.setFrameDuration(levelOne / Tools.speedMultiplier_5);
                animationRunningUpperBody.setFrameDuration(levelOne / Tools.speedMultiplier_5);
                animationShootingUppderBody.setFrameDuration(levelOne / Tools.speedMultiplier_5);
                break;
            case 6:
                animationRunningLowerBody.setFrameDuration(levelOne / Tools.speedMultiplier_6);
                animationRunningUpperBody.setFrameDuration(levelOne / Tools.speedMultiplier_6);
                animationShootingUppderBody.setFrameDuration(levelOne / Tools.speedMultiplier_6);
                break;
        }
    }

    public void update(float delta, Boolean shooting, Boolean jumping, Body body) {
        updatePosition(body);
        upperBody.setRegion(getUpperBodyFrame(delta, shooting, jumping));
        lowerBody.setRegion(getLowerBodyFrame(delta, shooting, jumping));
    }


    public TextureRegion getUpperBodyFrame(float delta, Boolean shooting, Boolean jumping) {
        currentState = getState(shooting, jumping);
        TextureRegion region;

        stateTimer_UPPERBODY = currentState == previousState ? stateTimer_UPPERBODY + delta : 0;

        previousState = currentState;

        switch(currentState) {
            case STANDING:
                region = animationSteadyUpperBody.getKeyFrame(stateTimer_UPPERBODY, true);
                break;
            case JUMPING:
                region = animationJumpingUpperBody.getKeyFrame(stateTimer_UPPERBODY, true);
                break;
            case SHOOTING:
                region = animationShootingUppderBody.getKeyFrame(stateTimer_UPPERBODY, false);
                break;
            case RUNNING:
            default:
                region = animationRunningUpperBody.getKeyFrame(stateTimer_UPPERBODY, true);
                break;
        }

        return region;
    }

    public TextureRegion getLowerBodyFrame(float delta, Boolean shooting, Boolean jumping) {
        currentState = getState(shooting, jumping);
        TextureRegion region;

        stateTimer_LOWERBODY = currentState == previousState ? stateTimer_LOWERBODY + delta : 0;
        previousState = currentState;

        switch(currentState) {
            case STANDING:
                region = animationSteadyLowerBody.getKeyFrame(stateTimer_LOWERBODY, true);
                break;
            case JUMPING:
                region = animationJumpingLowerBody.getKeyFrame(stateTimer_LOWERBODY, true);
                break;
            case SHOOTING:
            case RUNNING:
            default:
                region = animationRunningLowerBody.getKeyFrame(stateTimer_LOWERBODY, true);
                break;
        }

        return region;
    }


    public State getState(boolean shooting, boolean jumping) {
        State state = State.RUNNING;
        if (gameStarted) {
            if (shooting) {
                state = State.SHOOTING;
            } else if (jumping) {
                state = State.JUMPING;
            }
            if (!shooting && !jumping) {
                state = State.RUNNING;
            }
        } else {
            state = State.STANDING;
        }
        return state;
    }


    public void updatePosition(Body body) {
        upperBody.setPosition(body.getPosition().x - upperBody.getWidth() / 3, body.getPosition().y - 8 / Zombie.PPM);
        lowerBody.setPosition(body.getPosition().x - lowerBody.getWidth() / 3 - 19 / Zombie.PPM,
                body.getPosition().y - 58 / Zombie.PPM);
    }

    public void render(SpriteBatch batch) {
        lowerBody.draw(batch);
        upperBody.draw(batch);
    }

    public boolean isShootingOver() {
        if (stateTimer_UPPERBODY > animationShootingUppderBody.getAnimationDuration()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isJumpingOver() {
        if (stateTimer_UPPERBODY > animationJumpingUpperBody.getAnimationDuration()) {
            return true;
        } else {
            return false;
        }
    }


}
