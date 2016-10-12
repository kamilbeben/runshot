package com.kamilbeben.zombiestorm.hud;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamilbeben.zombiestorm.tools.TextureHolder;

/**
 * Created by bezik on 12.10.16.
 */
public class AmmoRenderer {

    private Sprite background;
    private Sprite ammoON;
    private Sprite ammoOFF;

    private boolean[] renderBullet;

    public AmmoRenderer(TextureHolder textureHolder) {
        background = new Sprite(textureHolder.HUD_AMMO_BACKGROUND);
        ammoON = new Sprite(textureHolder.HUD_AMMO_ON);
        ammoOFF = new Sprite(textureHolder.HUD_AMMO_OFF);
        background.setPosition(16, 16);
        renderBullet = new boolean[6];

    }

    private void resetBooleans() {
        for (int i=0; i<6; i++) {
            renderBullet[i] = true;
        }
    }

    public void checkHowManyBullets(int bullets) {
         resetBooleans();
        if (bullets < 6) {
            for (int i = bullets; i<6; i++) {
                renderBullet[i] = false;
            }
        }
    }

    public void render(SpriteBatch batch) {
        background.draw(batch);
        for (int i=0; i<6; i++) {
            if (renderBullet[i]) {
                ammoON.setPosition(background.getX() + 5 + i*34, background.getY() + 4);
                ammoON.draw(batch);
            } else  {
                ammoOFF.setPosition(background.getX() + 5 + i*34, background.getY() + 4);
                ammoOFF.draw(batch);
            }
        }
    }


}
