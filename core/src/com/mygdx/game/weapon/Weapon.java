package com.mygdx.game.weapon;

import com.mygdx.game.inventory.InventoryItem.ItemTypeID;

public class Weapon {

    public enum AmmoTypeID {
        NINE_MM,
    }

    private ItemTypeID itemTypeID;
    private AmmoTypeID ammoTypeID;
    private int knockback;
    private int hitRange;
    private int minDamage;
    private int maxDamage;
    private int critChance;
    private int ammoSpeed;

    public Weapon(){}

    public Weapon(Weapon weapon){
        super();
        this.itemTypeID = weapon.getItemTypeID();
        this.ammoTypeID = weapon.getAmmoTypeID();
        this.knockback = weapon.getKnockback();
        this.hitRange = weapon.getHitRange();
        this.minDamage = weapon.getMinDamage();
        this.maxDamage = weapon.getMaxDamage();
        this.critChance = weapon.getCritChance();
        this.ammoSpeed = weapon.getAmmoSpeed();
    }

    public ItemTypeID getItemTypeID() {
        return itemTypeID;
    }

    public void setItemTypeID(ItemTypeID itemTypeID) {
        this.itemTypeID = itemTypeID;
    }

    public AmmoTypeID getAmmoTypeID() {
        return ammoTypeID;
    }

    public void setAmmoTypeID(AmmoTypeID ammoTypeID) {
        this.ammoTypeID = ammoTypeID;
    }

    public int getKnockback() {
        return knockback;
    }

    public void setKnockback(int knockback) {
        this.knockback = knockback;
    }

    public int getHitRange() {
        return hitRange;
    }

    public void setHitRange(int hitRange) {
        this.hitRange = hitRange;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public int getCritChance() {
        return critChance;
    }

    public void setCritChance(int critChance) {
        this.critChance = critChance;
    }

    public int getAmmoSpeed() {
        return ammoSpeed;
    }

    public void setAmmoSpeed(int ammoSpeed) {
        this.ammoSpeed = ammoSpeed;
    }
}
