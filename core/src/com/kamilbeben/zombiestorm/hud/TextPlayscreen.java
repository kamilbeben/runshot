package com.kamilbeben.zombiestorm.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 08.10.16.
 */
public class TextPlayscreen {

    private BitmapFont font;
    private Stage stage;

    private final float padding = 20f;
    private Label timer;
    private Label score;
    private Label gameOver;
    private Label fps;


    public TextPlayscreen(Stage stage) {
        font = new BitmapFont();
        this.stage = stage;
        initializeLabels();
    }

    private void initializeLabels() {

        timer = new Label("0", new Label.LabelStyle(font, Color.WHITE));
        timer.setPosition(padding, Zombie.HEIGHT - padding - textHeight(timer.getText().toString()));

        fps = new Label("0", new Label.LabelStyle(font, Color.WHITE));
        fps.setPosition(padding, padding + 32f);

        score = new Label("0", new Label.LabelStyle(font, Color.WHITE));
        score.setPosition(Zombie.WIDTH - padding - textWidth(score.getText().toString()),
                Zombie.HEIGHT - padding - textHeight(score.getText().toString()));

        gameOver = new Label("Game Over", new Label.LabelStyle(font, Color.WHITE));
        gameOver.setPosition((Zombie.WIDTH - textWidth(gameOver.getText().toString()))/2,
                (Zombie.HEIGHT - textHeight(gameOver.getText().toString()))/2);

        stage.addActor(timer);
        stage.addActor(fps);
        stage.addActor(score);
    }

    public void update(float timerFloat, float scoreFloat) {
        timer.setText(String.format("%1.1f", timerFloat));
        timer.setPosition(padding, Zombie.HEIGHT - padding - textHeight(timer.getText().toString()));

        fps.setText(Integer.toString(Gdx.graphics.getFramesPerSecond()));

        score.setText(String.format("%1.0f", scoreFloat));
        score.setPosition(Zombie.WIDTH - padding - textWidth(score.getText().toString()),
                Zombie.HEIGHT - padding - textHeight(score.getText().toString()));
    }

    public void gameOver() {
        stage.addActor(this.gameOver);
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
