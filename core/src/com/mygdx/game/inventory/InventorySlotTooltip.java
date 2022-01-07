package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.mygdx.game.weapon.Weapon;
import com.mygdx.game.weapon.WeaponFactory;
import com.mygdx.game.weapon.WeaponSystem;

public class InventorySlotTooltip extends Window {

    private Skin skin;
    private Label description;

    public InventorySlotTooltip(final Skin skin){
        super("", skin);
        this.skin = skin;

        description = new Label("", skin);

        this.add(description);
        this.padLeft(5).padRight(5);
        this.pack();
        this.setVisible(false);
    }

    public void setVisible(InventorySlot inventorySlot, boolean visible) {
        super.setVisible(visible);

        if( inventorySlot == null ){
            return;
        }

        if (!inventorySlot.hasItem()) {
            super.setVisible(false);
        }
    }

    public void updateDescription(InventorySlot inventorySlot){
        if( inventorySlot.hasItem() ){
            StringBuilder string = new StringBuilder();
            Item item = inventorySlot.getTopInventoryItem();
            string.append(item.getItemShortDescription());


            if(item.isInventoryItemOffensiveMelee()) {
                string.append(System.getProperty("line.separator"));
                string.append(String.format("Attack Points: %s", item.getItemUseTypeValue()));
            } else if(item.isInventoryItemOffensiveRanged()){
                Weapon weapon = WeaponFactory.getInstance().getWeapon(item.getItemID());

                string.append(System.getProperty("line.separator"));
                string.append(String.format("Attack Points: %s", item.getItemUseTypeValue()));
                string.append(System.getProperty("line.separator"));
                string.append(System.getProperty("line.separator"));
                string.append(String.format("Ammo: %s", item.getNumberItemsInside() + "/" + WeaponSystem.getBagAmmunition().get(weapon.getAmmoID().getValue())));
            } else if(item.isInventoryItemDefensive()){
                string.append(System.getProperty("line.separator"));
                string.append(String.format("Defense Points: %s", item.getItemUseTypeValue()));
            }
//            string.append(System.getProperty("line.separator"));
//            string.append(System.getProperty("line.separator"));
//            string.append(String.format("Original Value: %s GP", item.getItemValue()));
//            string.append(System.getProperty("line.separator"));
//            string.append(String.format("Trade Value: %s GP", item.getTradeValue()));

            description.setText(string);
            this.pack();
        }else{
            description.setText("");
            this.pack();
        }

    }
}
