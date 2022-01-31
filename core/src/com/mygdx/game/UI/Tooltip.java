package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.FadingReality;

public class Tooltip {

    private Window tooltipBox;

    private float timer;
    private boolean active;

    public Tooltip(String text,  float timer, boolean active) {
        this.timer = timer;
        this.active = active;

        Label label = new Label(text, FadingReality.getUiSkin(), "default");
        Image image = new Image(new Texture(Gdx.files.internal("textures/UI/itemTest.png")));

        tooltipBox = new Window("", FadingReality.getUiSkin());
        tooltipBox.add(image);
        tooltipBox.add(label);
        tooltipBox.background(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/UI/tooltipBack.png")))));
    }

    public Table getTooltipBox() {
        return tooltipBox;
    }

    public float getTimer() {
        return timer;
    }

    public void setTimer(float delta) {
        this.timer += delta;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
