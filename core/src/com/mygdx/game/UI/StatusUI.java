package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.FadingReality;
import com.mygdx.game.inventory.InventoryItem;
import com.mygdx.game.tools.Utility;

public class StatusUI extends Group {

    private ImageButton button, button2, button3;

    private Stack stackIchor;
    private Stack stackMedicKit;
    private Stack stackRangeWeapon;
    private Stack stackMeleeWeapon;

    public StatusUI() {
//        super("", FadingReality.getUiSkin());
//        Drawable drawable = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.textureNone));
//        background(drawable);

        Image image1 = new Image(new NinePatch(Utility.STATUSUI.createPatch("testBox1")));
        Image image2 = new Image(new NinePatch(Utility.STATUSUI.createPatch("testBox1")));
        Image image3 = new Image(new NinePatch(Utility.STATUSUI.createPatch("testBox3")));
        Image image4 = new Image(new NinePatch(Utility.STATUSUI.createPatch("testBox3")));

        stackIchor = new Stack();
        stackIchor.setPosition(Gdx.graphics.getWidth()-500,32);
        stackIchor.setSize(64, 64);

        stackMedicKit = new Stack();
        stackMedicKit.setPosition(Gdx.graphics.getWidth()-600,32);
        stackMedicKit.setSize(64, 64);

        stackMeleeWeapon = new Stack();
        stackMeleeWeapon.setPosition(Gdx.graphics.getWidth()-208+56,16+56);
        stackMeleeWeapon.setSize(128, 128);

        stackRangeWeapon = new Stack();
        stackRangeWeapon.setPosition(Gdx.graphics.getWidth()-208,16);
        stackRangeWeapon.setSize(128, 128);

        stackIchor.add(image1);
        stackMedicKit.add(image2);
        stackRangeWeapon.add(image4);
        stackMeleeWeapon.add(image3);

        this.addActor(stackIchor);
        this.addActor(stackMedicKit);
        this.addActor(stackMeleeWeapon);
        this.addActor(stackRangeWeapon);




        Drawable drawable1 = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.texture));
        Drawable drawable2 = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.texture2));
        Drawable drawable3 = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.texture3));

        button = new ImageButton(drawable3);
        button.setPosition(50,50);

        button2 = new ImageButton(drawable1);
        button2.setPosition(300,25);

        button3 = new ImageButton(drawable2);
        button3.setPosition(600,25);

//        this.addActor(button);
//        this.addActor(button2);
//        this.addActor(button3);

    }

    public void setDrawableImageIchor(InventoryItem.ItemID ichor) {
    }

    public void setDrawableImageMedicKit(InventoryItem.ItemID medicKit) {
    }

    public void setMeleeWeapon(InventoryItem.ItemID meleeWeapon) {
        Image imageMeleeWeapon = new Image(new TextureRegionDrawable(Utility.ITEMS_TEXTUREATLAS.findRegion(meleeWeapon.toString())));
        stackMeleeWeapon.add(imageMeleeWeapon);
    }

    public void setRangeWeapon(InventoryItem.ItemID rangeWeapon) {
        Image imageRangeWeapon = new Image(new TextureRegionDrawable(Utility.ITEMS_TEXTUREATLAS.findRegion(rangeWeapon.toString())));
        stackRangeWeapon.add(imageRangeWeapon);
    }


    public void clearMeleeWeapon() {
        stackMeleeWeapon.removeActorAt(1,true);
    }

    public void clearRangeWeapon() {
        stackRangeWeapon.removeActorAt(1,true);
    }


}
