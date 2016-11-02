package com.kamilbeben.zombiestorm.graphicalfireworks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.kamilbeben.zombiestorm.Zombie;
import com.kamilbeben.zombiestorm.tools.TextureHolder;
import com.kamilbeben.zombiestorm.tools.Tools;

/**
 * Created by bezik on 01.10.16.
 */
public class WorldRenderer {

    private boolean gameOver = false;

    private Sprite staticBackground;
    private ParallaxBackground parallaxFar;
    private ParallaxBackground parallaxMiddle;
    private ParallaxBackground parallaxClose;
    private Sprite spriteAnimation;
    private Animation animation;

    public WorldRenderer(TextureHolder textureHolder) {
        setupAnimations(textureHolder.GAME_EXTRAS_GRASS_ANIMATION);
        setupStaticBackground(textureHolder.GAME_EXTRAS_BACKGROUND);
        parallaxFar = new ParallaxBackground(textureHolder.GAME_EXTRAS_PARALLAX_MOUNTAINS_FAR, 0.5f, true);
        parallaxMiddle = new ParallaxBackground(textureHolder.GAME_EXTRAS_PARALLAX_FOG, 1.2f, true);
        parallaxClose = new ParallaxBackground(textureHolder.GAME_EXTRAS_PARALLAX_MOUNTAINS_CLOSE, 1f, true);
        setSpeedLevel(1);
        updateGroundAndBackgroundAnimation(0f, 0f);
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
        parallaxFar.render(batch);
        parallaxMiddle.render(batch);
        parallaxClose.render(batch);
        for (int i=0; i<27; i++) {
            spriteAnimation.setPosition(i * (32 / Zombie.PPM), 0);
            spriteAnimation.draw(batch);
        }
    }


    public void updateGroundAndBackgroundAnimation(float timer, float delta) {
        if (!gameOver) {
            TextureRegion region;
            region = animation.getKeyFrame(timer, true);
            spriteAnimation.setRegion(region);
            parallaxFar.update(delta);
            parallaxMiddle.update(delta);
            parallaxClose.update(delta);
        }
    }

    public void setSpeedLevel(int speedLevel) {
        parallaxFar.setSpeedLevel(speedLevel);
        parallaxMiddle.setSpeedLevel(speedLevel);
        parallaxClose.setSpeedLevel(speedLevel);
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
