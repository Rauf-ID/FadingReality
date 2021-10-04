package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public final class Utility {

    public static TextureAtlas ITEMS_TEXTUREATLAS = new TextureAtlas("items/icons/items.atlas");
    public static TextureAtlas STATUSUI = new TextureAtlas("skins/statusui.atlas");
    public static Skin STATUSUI_SKIN = new Skin(Gdx.files.internal("uiskin.json"));



}