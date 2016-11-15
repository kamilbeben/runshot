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
        text =  "Created by Kamil Beben in 2016" +
                "\nusing libGDX library with box2d extension." +
                "\nContact: kamilbeben94@gmail.com" +
                "\nUsed programs: Gimp (graphics), DragonBones(animations), LMMS(music), Audacity(sounds)" +
                "\nUsed sounds created by: Mike Koenig (bite, honk, bounce), RA The Sun God (shot, reload) " +
                "\nUsed font \"" + fontName + "\"" + " created by " + fontCreator + ".";
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
