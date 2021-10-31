package com.mygdx.game.weapon;

import com.mygdx.game.inventory.InventoryItem.ItemTypeID;

public class Weapon {

    private ItemTypeID itemTypeID;
    private int knockback;
    private int hitRange;
    private int durability;
    private int minDamage;
    private int maxDamage;
    private int critChance;

    public Weapon(){}

    public Weapon(Weapon weapon){
        super();
        this.itemTypeID = weapon.getItemTypeID();
        this.knockback = weapon.getKnockback();
        this.hitRange = weapon.getHitRange();
        this.durability = weapon.getDurability();
        this.minDamage = weapon.getMinDamage();
        this.maxDamage = weapon.getMaxDamage();
        this.critChance = weapon.getCritChance();
    }

    public ItemTypeID getItemTypeID() {
        return itemTypeID;
    }

    public void setItemTypeID(ItemTypeID itemTypeID) {
        this.itemTypeID = itemTypeID;
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

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
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
}
