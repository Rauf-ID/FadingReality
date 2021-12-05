package com.mygdx.game.UI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FadingReality;

public class TooltipUI extends Window {

    private Array<Tooltip> tooltips = new Array<>();
    private Array<Tooltip> currentTooltips = new Array<>();

    public TooltipUI() {
        super("", FadingReality.getUiSkin());
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.textureNone));
        background(drawable);
    }

    public void addTooltip(String text) {
        Tooltip tooltip = new Tooltip(text,0,true);
        tooltips.add(tooltip);
        if (currentTooltips.size < 3) {
            makeTooltipCurrent(tooltip);
        }
    }

    public void makeTooltipCurrent(Tooltip tooltip){
        currentTooltips.add(tooltip);
        tooltips.removeIndex(0);
        this.add(tooltip.getTooltipBox());
        this.row();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (Tooltip tooltip : currentTooltips) {
                tooltip.setTimer(delta);
                if (tooltip.getTimer() > 3 && tooltip.isActive()) {
                    tooltip.setActive(false);
                    getChild(1).remove();
                    if (tooltips.size > 0) {
                        makeTooltipCurrent(tooltips.get(0));
                    }
                    currentTooltips.removeIndex(0);
                }
        }
    }
}
