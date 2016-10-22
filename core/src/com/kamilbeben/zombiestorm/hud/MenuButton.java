package com.kamilbeben.zombiestorm.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 21.10.16.
 */
public class MenuButton {
    public static final int buttonHeight = 32;
    private Button button;
    private Texture texture;
    public boolean clicked = false;

    public MenuButton(Stage stage, Vector2 position, Texture texture) {
        this.texture = texture;
        setMenuButtonStyleAndPosition( generateButtonSkinFromFile(), position);
        setButtonListener();
        stage.addActor(button);
    }

    private void setButtonListener() {
        button.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                clicked = true;
                return true;
            }
        });
    }

    private void setMenuButtonStyleAndPosition(Skin skin, float positionY) {
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = skin.getDrawable("texture");

        button = new Button(style);

        button.setPosition(centerHorizontal(), positionY);
        button.setWidth(texture.getWidth());
        button.setHeight(texture.getHeight());
    }

    private float centerHorizontal() {
        return (Zombie.WIDTH/2) - (texture.getWidth()/2);
    }

    private void setMenuButtonStyleAndPosition(Skin skin, Vector2 position) {
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = skin.getDrawable("texture");

        button = new Button(style);

        button.setPosition(position.x, position.y);
        button.setWidth(texture.getWidth());
        button.setHeight(texture.getHeight());
    }

    private Skin generateButtonSkinFromFile() {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Skin skin = new Skin();
        skin.add("texture", texture);
        return skin;
    }

    public boolean isClicked() {
        if (clicked) {
            clicked = false;
            return true;
        } else return false;
    }

    public void setTexture(Stage stage, Texture texture) {
        button.remove();
        this.texture = texture;
        setMenuButtonStyleAndPosition( generateButtonSkinFromFile(), button.getY());
        setButtonListener();
        stage.addActor(button);
    }

    public void remove() {
        button.remove();
    }

}
