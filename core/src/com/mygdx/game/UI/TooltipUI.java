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
    private Array<Tooltip> currentTooltips = new Array<>();



    public TooltipUI() {
        super("", FadingReality.getUiSkin());
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.textureNone));
        background(drawable);
    }


    public void addTooltip(String text) {
        Label label = new Label(text, FadingReality.getUiSkin());
        Tooltip tooltip = new Tooltip(label,tooltips.size%3,0,true);
        tooltips.add(tooltip);
//        labels.add(label);
        if (currentTooltips.size < 3) {
            makeTooltipCurrent(tooltip);
        }
    }

    public void makeTooltipCurrent(Tooltip tooltip){
        currentTooltips.add(tooltip);
        System.out.println(currentTooltips.size + "sixze");
        this.add(tooltip.label);
        this.row();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (Tooltip tooltip:currentTooltips){
            tooltip.timer+=delta;
            if(tooltip.timer>3 && tooltip.active){
                tooltip.active=false;
                if(tooltips.size>0){
                    tooltips.removeIndex(0);
                }
                currentTooltips.removeIndex(0);
                try {
                    currentTooltips.add(tooltips.get(0));
                }catch (IndexOutOfBoundsException e){}

                System.out.println("removed");
                getChild(1).remove();





            }
        }






    }




}
