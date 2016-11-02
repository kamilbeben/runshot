package com.kamilbeben.zombiestorm.tools;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by bezik on 12.10.16.
 */
public class TextureHolder {

    public Texture HUD_AMMO_BACKGROUND;
    public Texture HUD_PAUSE;
    public Texture HUD_AMMO_ON;
    public Texture HUD_AMMO_OFF;

    public Texture GAME_EXTRAS_AMMOPACK;
    public Texture GAME_EXTRAS_GRASS_ANIMATION;
    public Texture GAME_EXTRAS_FIREFLY_GREEN;
    public Texture GAME_EXTRAS_FIREFLY_BLUE;;
    public Texture GAME_EXTRAS_FIREFLY_TRANSPARENT;
    public Texture GAME_EXTRAS_PALETTE;
    public Texture GAME_EXTRAS_BACKGROUND;
    public Texture GAME_EXTRAS_PARALLAX_MOUNTAINS_FAR;
    public Texture GAME_EXTRAS_PARALLAX_MOUNTAINS_CLOSE;
    public Texture GAME_EXTRAS_PARALLAX_FOG;
    public Texture GAME_EXTRAS_FIRE_EFFECT;
    public Texture GAME_EXTRAS_POINTS_BREAK;
    public Texture GAME_EXTRAS_SHOTGUN_SHELL;

    public Texture GAME_ENEMY_CAR;
    public Texture GAME_ENEMY_CAR_LIGHTS;
    public Texture GAME_ENEMY_MONKEY;
    public Texture GAME_ENEMY_WALKER;
    public Texture GAME_PLAYER;
    public Texture GAME_PLAYER_EXTENDED;

    public Texture GAME_OBSTACLE_HOLE_LONG;
    public Texture GAME_OBSTACLE_HOLE_SHORT;
    public Texture GAME_OBSTACLE_ISLAND_LONG;
    public Texture GAME_OBSTACLE_ISLAND_SHORT;
    public Texture GAME_OBSTACLE_STONE_BIG;
    public Texture GAME_OBSTACLE_STONE_SMALL;

    public Texture MENU_BACKGROUND_BOT;
    public Texture MENU_BACKGROUND_TOP;
    public Texture MENU_PARALLAX_FOG;
    public Texture MENU_PLAY;
    public Texture MENU_OPTIONS;
    public Texture MENU_ABOUT;
    public Texture MENU_TUTORIAL;
    public Texture MENU_PLAYER_ANIMATION;

    public Texture ABOUT_BACKGROUND;
    public Texture ABOUT_RETURN;

    public Texture OPTIONS_BACKGROUND;
    public Texture OPTIONS_RETURN;
    public Texture OPTIONS_SFX_BACKGROUND;
    public Texture OPTIONS_SFX_SLIDER;
    public Texture OPTIONS_SLIDER_BACKGROUND;
    public Texture OPTIONS_SLIDER_KNOB;


    public TextureHolder() {

    }

    public void loadPlayscreen(Texture HUD_AMMO_BACKGROUND, Texture HUD_PAUSE, Texture HUD_AMMO_ON, Texture HUD_AMMO_OFF,Texture GAME_EXTRAS_SHOTGUN_SHELL, Texture GAME_EXTRAS_AMMOPACK, Texture GAME_EXTRAS_GRASS_ANIMATION, Texture GAME_EXTRAS_FIREFLY_GREEN, Texture GAME_EXTRAS_FIREFLY_BLUE, Texture GAME_EXTRAS_FIREFLY_TRANSPARENT, Texture GAME_EXTRAS_PALETTE, Texture GAME_EXTRAS_BACKGROUND, Texture GAME_EXTRAS_PARALLAX_MOUNTAINS_FAR, Texture GAME_EXTRAS_PARALLAX_MOUNTAINS_CLOSE, Texture GAME_EXTRAS_PARALLAX_FOG, Texture GAME_EXTRAS_FIRE_EFFECT, Texture GAME_EXTRAS_POINTS_BREAK, Texture GAME_ENEMY_CAR, Texture GAME_ENEMY_CAR_LIGHTS, Texture GAME_ENEMY_MONKEY, Texture GAME_ENEMY_WALKER, Texture GAME_PLAYER, Texture GAME_PLAYER_EXTENDED, Texture GAME_OBSTACLE_HOLE_LONG, Texture GAME_OBSTACLE_HOLE_SHORT, Texture GAME_OBSTACLE_ISLAND_LONG, Texture GAME_OBSTACLE_ISLAND_SHORT, Texture GAME_OBSTACLE_STONE_BIG, Texture GAME_OBSTACLE_STONE_SMALL) {
        this.HUD_AMMO_BACKGROUND = HUD_AMMO_BACKGROUND;
        this.HUD_PAUSE = HUD_PAUSE;
        this.HUD_AMMO_ON = HUD_AMMO_ON;
        this.HUD_AMMO_OFF = HUD_AMMO_OFF;
        this.GAME_EXTRAS_SHOTGUN_SHELL = GAME_EXTRAS_SHOTGUN_SHELL;
        this.GAME_EXTRAS_AMMOPACK = GAME_EXTRAS_AMMOPACK;
        this.GAME_EXTRAS_GRASS_ANIMATION = GAME_EXTRAS_GRASS_ANIMATION;
        this.GAME_EXTRAS_FIREFLY_GREEN = GAME_EXTRAS_FIREFLY_GREEN;
        this.GAME_EXTRAS_FIREFLY_BLUE = GAME_EXTRAS_FIREFLY_BLUE;
        this.GAME_EXTRAS_FIREFLY_TRANSPARENT = GAME_EXTRAS_FIREFLY_TRANSPARENT;
        this.GAME_EXTRAS_PALETTE = GAME_EXTRAS_PALETTE;
        this.GAME_EXTRAS_BACKGROUND = GAME_EXTRAS_BACKGROUND;
        this.GAME_EXTRAS_PARALLAX_MOUNTAINS_FAR = GAME_EXTRAS_PARALLAX_MOUNTAINS_FAR;
        this.GAME_EXTRAS_PARALLAX_MOUNTAINS_CLOSE = GAME_EXTRAS_PARALLAX_MOUNTAINS_CLOSE;
        this.GAME_EXTRAS_PARALLAX_FOG = GAME_EXTRAS_PARALLAX_FOG;
        this.GAME_EXTRAS_FIRE_EFFECT = GAME_EXTRAS_FIRE_EFFECT;
        this.GAME_EXTRAS_POINTS_BREAK = GAME_EXTRAS_POINTS_BREAK;
        this.GAME_ENEMY_CAR = GAME_ENEMY_CAR;
        this.GAME_ENEMY_CAR_LIGHTS = GAME_ENEMY_CAR_LIGHTS;
        this.GAME_ENEMY_MONKEY = GAME_ENEMY_MONKEY;
        this.GAME_ENEMY_WALKER = GAME_ENEMY_WALKER;
        this.GAME_PLAYER = GAME_PLAYER;
        this.GAME_PLAYER_EXTENDED = GAME_PLAYER_EXTENDED;
        this.GAME_OBSTACLE_HOLE_LONG = GAME_OBSTACLE_HOLE_LONG;
        this.GAME_OBSTACLE_HOLE_SHORT = GAME_OBSTACLE_HOLE_SHORT;
        this.GAME_OBSTACLE_ISLAND_LONG = GAME_OBSTACLE_ISLAND_LONG;
        this.GAME_OBSTACLE_ISLAND_SHORT = GAME_OBSTACLE_ISLAND_SHORT;
        this.GAME_OBSTACLE_STONE_BIG = GAME_OBSTACLE_STONE_BIG;
        this.GAME_OBSTACLE_STONE_SMALL = GAME_OBSTACLE_STONE_SMALL;
    }

    public void loadMenu(Texture MENU_BACKGROUND_BOT, Texture MENU_BACKGROUND_TOP, Texture MENU_PARALLAX_FOG, Texture MENU_PLAY, Texture MENU_OPTIONS, Texture MENU_ABOUT, Texture MENU_TUTORIAL, Texture MENU_PLAYER_ANIMATION) {
        this.MENU_BACKGROUND_BOT = MENU_BACKGROUND_BOT;
        this.MENU_BACKGROUND_TOP = MENU_BACKGROUND_TOP;
        this.MENU_PARALLAX_FOG = MENU_PARALLAX_FOG;
        this.MENU_PLAY = MENU_PLAY;
        this.MENU_OPTIONS = MENU_OPTIONS;
        this.MENU_ABOUT = MENU_ABOUT;
        this.MENU_TUTORIAL = MENU_TUTORIAL;
        this.MENU_PLAYER_ANIMATION = MENU_PLAYER_ANIMATION;
    }


    public void loadAbout(Texture ABOUT_BACKGROUND, Texture ABOUT_RETURN) {
        this.ABOUT_BACKGROUND = ABOUT_BACKGROUND;
        this.ABOUT_RETURN = ABOUT_RETURN;
    }

    public void loadOptions(Texture OPTIONS_BACKGROUND, Texture OPTIONS_RETURN, Texture OPTIONS_SFX_BACKGROUND, Texture OPTIONS_SFX_SLIDER, Texture OPTIONS_SLIDER_BACKGROUND, Texture OPTIONS_SLIDER_KNOB) {
        this.OPTIONS_BACKGROUND = OPTIONS_BACKGROUND;
        this.OPTIONS_RETURN = OPTIONS_RETURN;
        this.OPTIONS_SFX_BACKGROUND = OPTIONS_SFX_BACKGROUND;
        this.OPTIONS_SFX_SLIDER = OPTIONS_SFX_SLIDER;
        this.OPTIONS_SLIDER_BACKGROUND = OPTIONS_SLIDER_BACKGROUND;
        this.OPTIONS_SLIDER_KNOB = OPTIONS_SLIDER_KNOB;
    }
}
