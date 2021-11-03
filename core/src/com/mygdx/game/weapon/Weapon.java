package com.mygdx.game.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.inventory.InventoryItem.ItemID;
import com.mygdx.game.weapon.Ammo.AmmoID;

import java.util.ArrayList;

public class Weapon {

    private ItemID weaponID; // ID оружия
    private boolean isRangedWeapon;
    private int knockback; // отталкивание
    private int minDamage; // минимальный дамаг
    private int maxDamage; // максимальный дамаг
    private int critChance; // шанс критического удара
    private int attackTime; // время между аттакой/выстрелом
//    private Rectangle hitbox; // хитбокс лезвия/боеприпаса

    private String pathTexture; // Текстура оружия
    private AmmoID ammoID; // ID патрона
    private int ammoSpeed; // скорость патрона
    private int hitRange; // дальность поражения

    private ArrayList<Ammo> activeAmmo;



    public Weapon(){}

    public Weapon(Weapon weapon){
        this.weaponID = weapon.getWeaponID();
        this.isRangedWeapon = weapon.isRangedWeapon();
        this.knockback = weapon.getKnockback();
        this.minDamage = weapon.getMinDamage();
        this.maxDamage = weapon.getMaxDamage();
        this.critChance = weapon.getCritChance();
        this.attackTime = weapon.getAttackTime();

        if(isRangedWeapon) {
            this.pathTexture = weapon.getPathTexture();
            this.ammoID = weapon.getAmmoID();
            this.ammoSpeed = weapon.getAmmoSpeed();
            this.hitRange = weapon.getHitRange();

            Texture texture = new Texture(pathTexture);


            activeAmmo = new ArrayList<>();
        }
    }






    public ItemID getWeaponID() {
        return weaponID;
    }

    public void setWeaponID(ItemID weaponID) {
        this.weaponID = weaponID;
    }

    public boolean isRangedWeapon() {
        return isRangedWeapon;
    }

    public void setRangedWeapon(boolean rangedWeapon) {
        isRangedWeapon = rangedWeapon;
    }

    public int getKnockback() {
        return knockback;
    }

    public void setKnockback(int knockback) {
        this.knockback = knockback;
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

    public int getAttackTime() {
        return attackTime;
    }

    public void setAttackTime(int attackTime) {
        this.attackTime = attackTime;
    }





    public String getPathTexture() {
        return pathTexture;
    }

    public void setPathTexture(String pathTexture) {
        this.pathTexture = pathTexture;
    }

    public AmmoID getAmmoID() {
        return ammoID;
    }

    public void setAmmoID(AmmoID ammoID) {
        this.ammoID = ammoID;
    }

    public int getAmmoSpeed() {
        return ammoSpeed;
    }

    public void setAmmoSpeed(int ammoSpeed) {
        this.ammoSpeed = ammoSpeed;
    }

    public int getHitRange() {
        return hitRange;
    }

    public void setHitRange(int hitRange) {
        this.hitRange = hitRange;
    }
}
