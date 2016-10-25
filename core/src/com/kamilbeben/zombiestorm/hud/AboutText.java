package com.kamilbeben.zombiestorm.hud;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.kamilbeben.zombiestorm.Zombie;

/**
 * Created by bezik on 25.10.16.
 */

public class AboutText {

    private static final String fontCreator = "P.D. Magnus";
    private static final String fontName = "Milk Run";

    private static final int padding = 32;
    private static final boolean textWrapping = true;

    private BitmapFont font;
    private String text;

    public AboutText(BitmapFont bitmapFont) {
        font = bitmapFont;
        addText();
    }

    private void addText() {
        text =  "Bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                "Bla bla bla bla bla bla bla bla bla bla. \n" +
                "Bla bala bla bla bla bal.\n\n" +
                "Created by Kamil Beben in 2016\n" +
                "using libGDX library with box2d extension.\n" +
                "Used font \"" + fontName + "\"" + " created by " + fontCreator + ".\n\n" +
                "Check out my website!\n" +
                "fuck you faggon, dont add website until its done.";
    }

    public void render(Batch batch) {
        font.draw(batch, text, padding, centerVertical(),
                Zombie.WIDTH - padding, Align.left, textWrapping);
    }

    private float centerVertical() {
        GlyphLayout glyphLayout = new GlyphLayout(font, text, Color.BLACK, Zombie.WIDTH - padding,
                Align.left, true);
        return (Zombie.HEIGHT/2) + (glyphLayout.height/2);
    }

    public void dispose() {
        font.dispose();
    }
}
