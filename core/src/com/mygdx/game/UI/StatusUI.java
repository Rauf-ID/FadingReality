package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.FadingReality;
import com.mygdx.game.item.Item;
import com.mygdx.game.tools.Utility;
import com.mygdx.game.weapon.Weapon;
import com.mygdx.game.weapon.WeaponFactory;
import com.mygdx.game.weapon.WeaponSystem;

public class StatusUI extends Group {

    private ImageButton button2;

    private Stack stackIchor;
    private Stack stackMedicKit;
    private Stack stackRangeWeapon;
    private Stack stackMeleeWeapon;

    private Label numItemsIchor;
    private Label numItemsMedicKit;
    private Label ammoCount = new Label("", FadingReality.getUiSkin());

    public StatusUI() {
//        super("", FadingReality.getUiSkin());
//        Drawable drawable = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.textureNone));
//        background(drawable);
//        Image image1 = new Image(new NinePatch(Utility.STATUSUI.createPatch("testBox1")));

        Drawable drawableUIForStats = new TextureRegionDrawable(new TextureRegion(new Texture("textures/UI/stats.png")));
        Drawable drawableUIForConsumables = new TextureRegionDrawable(new TextureRegion(new Texture("textures/UI/UIForConsumables.png")));
        Drawable drawableUIForWeapons = new TextureRegionDrawable(new TextureRegion(new Texture("textures/UI/UIForWeapons.png")));

        Image imageStats = new Image(drawableUIForStats);
        imageStats.setPosition(16,16);

        Image imageIchor = new Image(drawableUIForConsumables);
        imageIchor.setPosition(Gdx.graphics.getWidth()-400,16);
        imageIchor.setSize(64, 64);

        stackIchor = new Stack();
        stackIchor.setPosition(Gdx.graphics.getWidth()-400,16);
        stackIchor.setSize(64, 64);

        Image imageMedicKit = new Image(drawableUIForConsumables);
        imageMedicKit.setPosition(Gdx.graphics.getWidth()-500,16);
        imageMedicKit.setSize(64, 64);

        stackMedicKit = new Stack();
        stackMedicKit.setPosition(Gdx.graphics.getWidth()-500,16);
        stackMedicKit.setSize(64, 64);

        Image imageMeleeWeapon = new Image(drawableUIForWeapons);
        imageMeleeWeapon.setPosition(Gdx.graphics.getWidth()-208+56,16+56);
        imageMeleeWeapon.setSize(128, 128);

        stackMeleeWeapon = new Stack();
        stackMeleeWeapon.setPosition(imageMeleeWeapon.getX() + 32,imageMeleeWeapon.getY() + 32);
        stackMeleeWeapon.setSize(64, 64);

        Image imageRangeWeapon = new Image(drawableUIForWeapons);
        imageRangeWeapon.setPosition(Gdx.graphics.getWidth()-208,16);
        imageRangeWeapon.setSize(128, 128);

        stackRangeWeapon = new Stack();
        stackRangeWeapon.setPosition(imageRangeWeapon.getX() + 32, imageRangeWeapon.getY()+ 32);
        stackRangeWeapon.setSize(64, 64);

        this.addActor(imageStats);
        this.addActor(imageIchor);
        this.addActor(stackIchor);
        this.addActor(imageMedicKit);
        this.addActor(stackMedicKit);
        this.addActor(imageMeleeWeapon);
        this.addActor(stackMeleeWeapon);
        this.addActor(imageRangeWeapon);
        this.addActor(stackRangeWeapon);

//        Drawable drawable2 = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.texture2));
//        button2 = new ImageButton(drawable2);
//        button2.setPosition(600,16);
//        this.addActor(button2);

    }

    public void setIchor(Item.ItemID ichor) {
        stackIchor.clear();
        Image imageIchor = new Image(new TextureRegionDrawable(Utility.ITEMS_TEXTUREATLAS.findRegion(ichor.toString())));
        stackIchor.add(imageIchor);
    }

    public void setDrawableImageMedicKit(Item.ItemID medicKit) {
    }

    public void setMeleeWeapon(Item.ItemID meleeWeapon) {
        stackMeleeWeapon.clear();
        Image imageMeleeWeapon = new Image(new TextureRegionDrawable(Utility.ITEMS_TEXTUREATLAS.findRegion(meleeWeapon.toString())));
        stackMeleeWeapon.add(imageMeleeWeapon);
    }

    public void setRangeWeapon(Item rangeWeapon) {
        stackRangeWeapon.clear();

        Weapon weapon = WeaponFactory.getInstance().getWeapon(rangeWeapon.getItemID());
        setLabelAmmoCountText(rangeWeapon.getNumberItemsInside() + "/" + WeaponSystem.getBagAmmunition().get(weapon.getAmmoID().getValue()));
        ammoCount.setAlignment(Align.bottom);

        Image imageRangeWeapon = new Image(new TextureRegionDrawable(Utility.ITEMS_TEXTUREATLAS.findRegion(rangeWeapon.getItemID().toString())));
        stackRangeWeapon.add(imageRangeWeapon);
        stackRangeWeapon.add(ammoCount);
    }


    public void clearMeleeWeapon() {
        stackMeleeWeapon.clear();
    }

    public void clearRangeWeapon() {
        stackRangeWeapon.clear();
    }


    public void setLabelAmmoCountText(String text) {
        ammoCount.setText(text);
    }

}
