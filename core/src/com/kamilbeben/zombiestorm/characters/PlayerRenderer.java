package com.kamilbeben.zombiestorm.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.TextureHolder;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 07.10.16.
 */
public class PlayerRenderer {

    private enum State {RUNNING, SHOOTING, JUMPING, STANDING, STUMBLE, HIT_BY_CAR}

    public boolean gameStarted = true;

    private State currentState;
    private State previousState;

    private float stateTimer_UPPERBODY = 0f;
    private float stateTimer_LOWERBODY = 0f;
    private float stateTimer_FULLBODY = 0f;

    private Sprite upperBody;
    private Sprite lowerBody;

    private Animation animationRunningUpperBody;
    private Animation animationRunningLowerBody;
    private Animation animationShootingUppderBody;
    private Animation animationJumpingUpperBody;
    private Animation animationJumpingLowerBody;
    private Animation animationSteadyUpperBody;
    private Animation animationSteadyLowerBody;


    private Sprite fullBodyStumble;
    private Animation animationStumble;

    private Sprite fullBodyCarAccident;
    private Animation animationCarAccident;

    private boolean stumbling = false;

    public PlayerRenderer(TextureHolder textureHolder) {
        upperBody = new Sprite(textureHolder.GAME_PLAYER);
        lowerBody = new Sprite(textureHolder.GAME_PLAYER);
        fullBodyStumble = new Sprite(textureHolder.GAME_PLAYER_EXTENDED);
        setupTwoPartedAnimations();
        setupOnePartedAnimations();
    }

    private void setupTwoPartedAnimations() {
        int yPosition = 0*113;
        upperBody.setBounds(32 / Zombie.PPM, 80 / Zombie.PPM, 101 / Zombie.PPM, 113 / Zombie.PPM);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(upperBody.getTexture(), i * 101, yPosition, 101, 113));
        }
        animationRunningUpperBody = new Animation(0.15f, frames);
        frames.clear();

        yPosition = 1*113;
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(upperBody.getTexture(), i * 101, yPosition, 101, 113));
        }
        animationShootingUppderBody = new Animation(0.066f, frames);
        frames.clear();

        yPosition = 2*113;
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(upperBody.getTexture(), i * 101, yPosition, 101, 113));
        }
        animationJumpingUpperBody = new Animation(0.066f, frames);
        frames.clear();

        lowerBody.setBounds(32 / Zombie.PPM, 30 / Zombie.PPM, 101 / Zombie.PPM, 67 / Zombie.PPM);
        yPosition = 339;
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(lowerBody.getTexture(), i * 101, yPosition, 101, 67));
        }
        animationRunningLowerBody = new Animation(0.066f, frames);
        frames.clear();

        yPosition = 406;
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(lowerBody.getTexture(), i * 101, yPosition, 101, 67));
        };

        animationJumpingLowerBody = new Animation(0.066f, frames);
        frames.clear();

        yPosition = 471;
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(lowerBody.getTexture(), i * 101, yPosition, 101, 113));
        };

        animationSteadyUpperBody = new Animation(0.066f, frames);
        frames.clear();

        yPosition = 584;
        for (int i=0; i<12; i++) {
            frames.add(new TextureRegion(lowerBody.getTexture(), i * 101, yPosition, 101, 67));
        };

        animationSteadyLowerBody = new Animation(0.066f, frames);
        frames.clear();
    }

    private void setupOnePartedAnimations() {
        fullBodyStumble.setBounds(0,0, 267 / Zombie.PPM, 158 / Zombie.PPM);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        int yPosition = 158;

        for (int k=0; k<3; k++) {
            for (int i=0; i<7; i++) {
                frames.add(new TextureRegion(fullBodyStumble.getTexture(), i * 267, k * yPosition, 267, 158));
            }
        }
        for (int i=0; i<3; i++) {
            frames.add(new TextureRegion(fullBodyStumble.getTexture(), i * 267, 3 * yPosition, 267, 158));
        }
        animationStumble = new Animation(0.05f, frames);
        frames.clear();
    }

    public void setSpeedLevel(int speedLevel) {
        float levelOne = 0.08f;
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
        if (stumbling) {
            fullBodyStumble.setRegion(animationStumble.getKeyFrame(stateTimer_FULLBODY, false));
            stateTimer_FULLBODY += delta;
        } else {
            upperBody.setRegion(getUpperBodyFrame(delta, shooting, jumping));
            lowerBody.setRegion(getLowerBodyFrame(delta, shooting, jumping));
        }
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
        if (stumbling) {
            fullBodyStumble.setPosition(body.getPosition().x, body.getPosition().y - 68f / Zombie.PPM);
        } else {
            upperBody.setPosition(body.getPosition().x - upperBody.getWidth() / 3, body.getPosition().y - 8 / Zombie.PPM);
            lowerBody.setPosition(body.getPosition().x - lowerBody.getWidth() / 3 - 19 / Zombie.PPM,
                    body.getPosition().y - 58 / Zombie.PPM);
        }
    }

    public void render(SpriteBatch batch) {
        if (stumbling) {
            fullBodyStumble.draw(batch);
        } else {
            lowerBody.draw(batch);
            upperBody.draw(batch);
        }
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

    public void setStumble() {
        stumbling = true;
    }


}
