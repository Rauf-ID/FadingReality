package com.mygdx.game.UI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FadingReality;
//import jdk.javadoc.internal.doclets.toolkit.util.InternalException;

public class TooltipUI extends Window {

    private Array<Tooltip> tooltips = new Array<>();
    private Array<Tooltip> currentTooltips = new Array<>();



    public TooltipUI() {
        super("", FadingReality.getUiSkin());
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.textureNone));
        background(drawable);
    }


    public void addTooltip(String text) {
        Label label = new Label(text, FadingReality.getUiSkin());
        Tooltip tooltip = new Tooltip(label,currentTooltips.size%3,0,true);
        tooltips.add(tooltip);
        if (currentTooltips.size < 3) {
            makeTooltipCurrent(tooltip);
        }
    }

    public void makeTooltipCurrent(Tooltip tooltip){
        currentTooltips.add(tooltip);
        tooltips.removeIndex(0);
        System.out.println(currentTooltips.size + " - size");
        this.add(tooltip.label);
        this.row();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        for (Tooltip tooltip : currentTooltips) {
                tooltip.timer += delta;
                System.out.println("time: " + tooltip.timer + " index:" + tooltip.index);
                if (tooltip.timer > 3 && tooltip.active) {
                    tooltip.active = false;
                    getChild(1).remove();

                    if (tooltips.size > 0) {
                        makeTooltipCurrent(tooltips.get(0));

                        System.out.println("added new");
                    }
                    System.out.println("removed" + currentTooltips.size);
                    currentTooltips.removeIndex(0);
                    System.out.println("removed" + currentTooltips.size);


                }
        }
    }
}
