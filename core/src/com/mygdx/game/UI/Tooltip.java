package com.mygdx.game.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Tooltip {

    Label label;
    int index;
    float timer;

    public Tooltip(Label label, int index, float timer) {
        this.label = label;
        this.index = index;
        this.timer = timer;
    }
}
