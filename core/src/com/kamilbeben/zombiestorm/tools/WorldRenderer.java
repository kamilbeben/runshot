package com.kamilbeben.zombiestorm.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 01.10.16.
 */
public class WorldRenderer {

    private boolean renderGround[];
    private boolean gameOver = false;

    private Sprite spriteAnimation;
    private Animation animation;

    public WorldRenderer () {
        renderGround = new boolean[25];
        resetGround();
        setupAnimations();
    }

    private void setupAnimations() {
        spriteAnimation = new Sprite(new Texture("grass_animation.png"));
        spriteAnimation.setBounds(0, 0, 32 / Zombie.PPM, 96 / Zombie.PPM);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0; i<4; i++) {
            frames.add(new TextureRegion(spriteAnimation.getTexture(), i * 32, 0, 32, 96));
        }
        animation = new Animation(0.05f, frames);
        frames.clear();
    }

    public void draw(SpriteBatch batch) {
        for (int i=0; i<renderGround.length; i++) {
            if (renderGround[i]) {
                spriteAnimation.setPosition(i * (32 / Zombie.PPM), 0);
                spriteAnimation.draw(batch);
            }
        }
    }


    public void updateGround(HolePosition position) {
        resetGround();
        int end = (position.end >= 25) ? 25 : position.end;
        try {
            for (int i = position.start; i < end; i++) {
               if (i >= 0) {
                   renderGround[i] = false;
                   renderGround[i+1] = false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    public void updateAnimation(float timer) {
        if (!gameOver) {
            TextureRegion region;
            region = animation.getKeyFrame(timer, true);
            spriteAnimation.setRegion(region);
        }
    }

    private void resetGround() {
        for (int i = 0; i < renderGround.length; i++) {
            renderGround[i] = true;
        }

    }

    public void nextSpeedLevel(float timer) {
        float speed = 0.05f; //TODO
        animation.setFrameDuration(speed);
    }

    public void stopAnimating() {
        gameOver = true;
    }
}
