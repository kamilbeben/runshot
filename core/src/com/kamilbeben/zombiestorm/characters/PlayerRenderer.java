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

    public enum State {RUNNING, SHOOTING, JUMPING}

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


    public PlayerRenderer(Texture playerTextureMap) {
        upperBody = new Sprite(playerTextureMap);
        lowerBody = new Sprite(playerTextureMap);
    }

    public void setupLooks() {
        int yPosition = 0*105;
        upperBody.setBounds(32 / Zombie.PPM, 80 / Zombie.PPM, 116 / Zombie.PPM, 105 / Zombie.PPM);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0; i<8; i++) {
            frames.add(new TextureRegion(upperBody.getTexture(), i * 116, yPosition, 116, 105));
        }
        animationRunningUpperBody = new Animation(0.1f, frames);
        frames.clear();

        yPosition = 1*105;
        for (int i=0; i<8; i++) {
            frames.add(new TextureRegion(upperBody.getTexture(), i * 116, yPosition, 116, 105));
        }
        animationShootingUppderBody = new Animation(0.1f, frames);
        frames.clear();

        yPosition = 3*105;
        for (int i=0; i<8; i++) {
            frames.add(new TextureRegion(upperBody.getTexture(), i * 116, yPosition, 116, 105));
        }
        animationJumpingUpperBody = new Animation(0.1f, frames);
        frames.clear();

        lowerBody.setBounds(32 / Zombie.PPM, 30 / Zombie.PPM, 116 / Zombie.PPM, 105 / Zombie.PPM);
        yPosition = 2*105;
        for (int i=0; i<8; i++) {
            frames.add(new TextureRegion(lowerBody.getTexture(), i * 116, yPosition, 116, 105));
        }
        animationRunningLowerBody = new Animation(0.1f, frames);
        frames.clear();

        yPosition = 4*105; //TODO ADJUST
        for (int i=0; i<6; i++) {
            frames.add(new TextureRegion(lowerBody.getTexture(), i * 116, yPosition, 116, 105));
        }
        for (int i=0; i<16; i++) {
            frames.add(new TextureRegion(lowerBody.getTexture(), 6 * 116, yPosition, 116, 105));
        }
        frames.add(new TextureRegion(lowerBody.getTexture(), 7 * 116, yPosition, 116, 105));

        animationJumpingLowerBody = new Animation(0.1f, frames);
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

        switch(currentState) {
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

        stateTimer_UPPERBODY = currentState == previousState ? stateTimer_UPPERBODY + delta : 0;

        previousState = currentState;
        return region;
    }

    public TextureRegion getLowerBodyFrame(float delta, Boolean shooting, Boolean jumping) {
        currentState = getState(shooting, jumping);
        TextureRegion region;

        switch(currentState) {
            case JUMPING:
                region = animationJumpingLowerBody.getKeyFrame(stateTimer_LOWERBODY, true);
                break;
            case SHOOTING:
            case RUNNING:
            default:
                region = animationRunningLowerBody.getKeyFrame(stateTimer_LOWERBODY, true);
                break;
        }

        stateTimer_LOWERBODY = currentState == previousState ? stateTimer_LOWERBODY + delta : 0;

        previousState = currentState;
        return region;
    }


    public State getState(boolean shooting, boolean jumping) {
        State state = State.RUNNING;
        if (shooting) {
            state = State.SHOOTING;
        } else if (jumping) {
            state = State.JUMPING;
        }
        if (!shooting && !jumping) {
            state = State.RUNNING;
        }
        return state;
    }


    public void updatePosition(Body body) {
        upperBody.setPosition(body.getPosition().x - upperBody.getWidth() / 3, body.getPosition().y - 12 / Zombie.PPM);
        lowerBody.setPosition(body.getPosition().x - lowerBody.getWidth() / 3 - 44 / Zombie.PPM,
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
