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

        setValue(0f);
        setAnimateDuration(0.5f);
    }

    public void minusValue(float minus) {
        float value = getValue();
        value -= minus;
        setValue(value);
        if (getValue() == 0.75f) {

        }
    }

    public void plusValue(float plus) {
        float value = getValue();
        value += plus;
        setValue(value);
    }

    public void setValue(int value) {
        for (int i = 0; i < value; i++) {
            plusValue(0.25f);
        }
    }

    public void setBackground(int width, int height, Color color) {
        getStyle().background = Utils.getColoredDrawable(width, height, color);
    }

    public void setKnob(int width, int height, Color color) {
        getStyle().knob = Utils.getColoredDrawable(width, height, color);
    }

    public void setKnobBefore(int width, int height, Color color) {
        getStyle().knobBefore = Utils.getColoredDrawable(width, height, color);
    }

}