package com.mygdx.game.UI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FadingReality;

public class TooltipUI extends Window {

    private Array<Label> labels = new Array<>();
    private Array<Tooltip> tooltips = new Array<>();

    private float timer1 = 0, timer2 = 0, timer3 = 0;

    public TooltipUI() {
        super("", FadingReality.getUiSkin());
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.textureNone));
        background(drawable);
    }


    public void addTooltip(String text) {
        Label label = new Label(text, FadingReality.getUiSkin());
        tooltips.add(new Tooltip(label, tooltips.size + 1, 3));
//        labels.add(label);
        if (tooltips.size < 3) {
            this.add(label);
            this.row();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

//        int q = 0;
//        if (labels.size > 0) {
//            if (timer1 == 0) {
//                timer1 += delta;
//            }
//        }


        if (labels.size == 1) {
            timer1 += delta;
            if (timer1 > 3) {
                System.out.println(getChild(0).getZIndex());
                getChild(1).remove();
                labels.removeIndex(0);
                timer1 = 0;
            }
        }
        if (labels.size == 2) {
            timer2 += delta;
            if (timer2 > 3) {
                getChild(2).remove();
                labels.removeIndex(1);
                timer2 = 0;
            }
        }
        if (labels.size == 3) {
            timer3 += delta;
            if (timer3 > 3) {
                getChild(3).remove();
                labels.removeIndex(2);
                timer3 = 0;
            }
        }

    }




}
