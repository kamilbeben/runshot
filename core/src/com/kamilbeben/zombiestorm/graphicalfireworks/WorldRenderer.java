package com.kamilbeben.zombiestorm.graphicalfireworks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.HolePosition;
import com.kamilbeben.zombiestorm.tools.TextureHolder;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 01.10.16.
 */
public class WorldRenderer {

    private boolean renderGround[];
    private boolean gameOver = false;

    private Sprite staticBackground;
    private ParallaxBackground parallaxMountains;
    private ParallaxBackground parallaxTrees;
    private Sprite spriteAnimation;
    private Animation animation;

    public WorldRenderer(TextureHolder textureHolder) {
        renderGround = new boolean[27];
        resetGround();
        setupAnimations(textureHolder.GAME_EXTRAS_GRASS_ANIMATION);
        setSpeedLevel(1);
        setupStaticBackground(textureHolder.GAME_EXTRAS_BACKGROUND);
        parallaxMountains = new ParallaxBackground(textureHolder.GAME_EXTRAS_PARALLAX_MOUNTAINS, 0.5f);
        parallaxMountains = new ParallaxBackground(textureHolder.GAME_EXTRAS_PARALLAX_MOUNTAINS, 0.7f);
    }

    private void setupAnimations(Texture texture) {
        spriteAnimation = new Sprite(texture);
        spriteAnimation.setBounds(0, 0, 32 / Zombie.PPM, 96 / Zombie.PPM);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0; i<4; i++) {
            frames.add(new TextureRegion(spriteAnimation.getTexture(), i * 32, 0, 32, 96));
        }
        animation = new Animation(0.05f, frames);
        frames.clear();
    }

    private void setupStaticBackground(Texture texture) {
        staticBackground = new Sprite(texture);
        staticBackground.setSize(Zombie.WIDTH / Zombie.PPM, Zombie.HEIGHT / Zombie.PPM);
    }

    public void draw(SpriteBatch batch) {
        staticBackground.draw(batch);
        parallaxMountains.render(batch);
        for (int i=0; i<renderGround.length; i++) {
            if (renderGround[i]) {
                spriteAnimation.setPosition(i * (32 / Zombie.PPM), 0);
                spriteAnimation.draw(batch);
            }
        }
    }


    public void updateGround(HolePosition position) {
        resetGround();
        int end = (position.end >= renderGround.length) ? renderGround.length : position.end;
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

    public void updateGroundAndBackgroundAnimation(float timer, float delta) {
        if (!gameOver) {
            TextureRegion region;
            region = animation.getKeyFrame(timer, true);
            spriteAnimation.setRegion(region);
            parallaxMountains.update(delta);
        }
    }

    private void resetGround() {
        for (int i = 0; i < renderGround.length; i++) {
            renderGround[i] = true;
        }

    }

    public void setSpeedLevel(int speedLevel) {
        float levelOne = 0.033f;
        switch (speedLevel) {
            default:
            case 1:
                animation.setFrameDuration(levelOne / Tools.speedMultiplier_1);
                break;
            case 2:
                animation.setFrameDuration(levelOne / Tools.speedMultiplier_2);
                break;
            case 3:
                animation.setFrameDuration(levelOne / Tools.speedMultiplier_3);
                break;
            case 4:
                animation.setFrameDuration(levelOne / Tools.speedMultiplier_4);
                break;
            case 5:
                animation.setFrameDuration(levelOne / Tools.speedMultiplier_5);
                break;
            case 6:
                animation.setFrameDuration(levelOne / Tools.speedMultiplier_6);
                break;
        }
    }

    public void stopAnimating() {
        gameOver = true;
    }
}
