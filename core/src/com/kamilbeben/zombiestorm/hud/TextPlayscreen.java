package com.kamilbeben.zombiestorm.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 08.10.16.
 */
public class TextPlayscreen {


    private BitmapFont font;
    private Stage stage;

    private final float padding = 20f;
    private Label distance;
    private Label gameOver;
    private Label pause;


    public TextPlayscreen(Stage stage, BitmapFont bitmapFont) {
        font = bitmapFont;
        this.stage = stage;
        initializeLabels();
    }

    private void initializeLabels() {

        distance = new Label("0", new Label.LabelStyle(font, Color.WHITE));
        distance.setPosition(Zombie.WIDTH - padding - textWidth(distance.getText().toString()), Zombie.HEIGHT - padding - textHeight(distance.getText().toString()));


        stage.addActor(distance);
    }

    public void update(float distanceFloat) {
        distance.setText(String.format("%1.0f", distanceFloat));
        distance.setPosition(Zombie.WIDTH - padding - textWidth(distance.getText().toString()), Zombie.HEIGHT - padding - textHeight(distance.getText().toString()));
    }

    public void gameOver(float distance, int zombieKilled) {
        float score = distance + (zombieKilled * 10);

        gameOver = new Label("Game over! \nDistance: " + Integer.toString((int) distance) + "\nZombie's killed: " + Integer.toString(zombieKilled) + "\nScore: "  + Integer.toString((int) score),
                new Label.LabelStyle(font, Color.WHITE));
        gameOver.setPosition((Zombie.WIDTH - textWidth(gameOver.getText().toString()))/2,
                (Zombie.HEIGHT - textHeight(gameOver.getText().toString()))/2);

        stage.addActor(this.gameOver);
    }

    public void pause() {

        pause = new Label("         Pause \nClick on screen to unpause \nClick back button to leave", new Label.LabelStyle(font, Color.WHITE));
        pause.setPosition((Zombie.WIDTH - textWidth(pause.getText().toString()))/2,
                (Zombie.HEIGHT - textHeight(pause.getText().toString()))/2);

        stage.addActor(this.pause);
    }

    public void unpause() {
        pause.remove();
    }

    private float textHeight(String text) {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, text);
        return glyphLayout.height;
    }

    private float textWidth(String text) {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, text);
        return glyphLayout.width;
    }

    public void render() {
        stage.draw();
    }
}
