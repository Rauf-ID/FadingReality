package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ItemButton extends ImageButton {

    private Item item;

    public ItemButton(Drawable drawable) {
        super(drawable);
    }

    public ItemButton(Drawable drawable, Item item) {
        super(drawable);

        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
