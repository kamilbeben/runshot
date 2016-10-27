package com.kamilbeben.zombiestorm.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by bezik on 28.10.16.
 */
public class InputHandler {

    public static final boolean pause() {
        if (Gdx.input.getX() < Gdx.graphics.getWidth() * 0.1f && Gdx.input.getY() < Gdx.graphics.getHeight() * 0.15f) {
            return true;
        } else {
            return false;
        }
    }

    public static final boolean shot() {
        if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
            return true;
        } else {
            return false;
        }
    }

    public static final boolean jump() {
        if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2) {
            return true;
        } else {
            return false;
        }
    }

    public static final boolean menuKey() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.MENU)) {
            return true;
        } else {
            return false;
        }
    }

    public static final boolean backKey() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            return true;
        } else {
            return false;
        }
    }
}
