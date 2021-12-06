package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ProgressBarNew extends ProgressBar {


    public ProgressBarNew(int width, int height, float min, float max, float stepSize, boolean vertical) {
        super(min, max, stepSize, vertical, new ProgressBarStyle());
//        getStyle().background = Utils.getColoredDrawable(width, height, Color.RED);
        getStyle().knob = Utils.getColoredDrawable(0, height, Color.GREEN);
        getStyle().knobBefore = Utils.getColoredDrawable(width, height, Color.GREEN);

        setWidth(width);
        setHeight(height);

        setValue(1f);
        setAnimateDuration(0.25f);
    }

    public void minusValue(float minus) {
        float value = getValue();
        value -= minus;
        setValue(value);
    }

}