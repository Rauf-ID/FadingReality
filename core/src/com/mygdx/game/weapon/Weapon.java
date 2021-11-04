package com.mygdx.game.weapon;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.FadingReality;
import com.mygdx.game.inventory.InventoryItem.ItemID;
import com.mygdx.game.weapon.Ammo.AmmoID;

import java.util.ArrayList;
import java.util.Iterator;

public class Weapon {

    private ItemID weaponID; // ID оружия
    private boolean isRangedWeapon; // оружие дальнего боя
    private int knockback; // отталкивание
    private int minDamage; // минимальный дамаг
    private int maxDamage; // максимальный дамаг
    private int critChance; // шанс критического удара
    private float attackTime; // время между аттакой/выстрелом
//    private Rectangle hitBox; // хитбокс лезвия/боеприпаса

    private AmmoID ammoID; // ID патрона
    private String pathTextureAmmo; // путь до текстуры боеприпаса
    private int ammoSpeed; // скорость патрона
    private int hitRange; // дальность поражения

    private Sprite weaponSprite = null;
    private ArrayList<Ammo> activeAmmo = null;
    private Vector3 pos;
    private float angle;
    private float ammoCount;
    private float stateTime;
    private float offsetX, offsetY;

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
            this.ammoID = weapon.getAmmoID();
            this.pathTextureAmmo = weapon.getPathTextureAmmo();
            this.ammoSpeed = weapon.getAmmoSpeed();
            this.hitRange = weapon.getHitRange();

            activeAmmo = new ArrayList<>();
            pos = new Vector3();
        }
    }

    public void update(float delta) {}

    public void update(float delta, float x, float y, float angle) {
        this.pos.x = x;
        this.pos.y = y;
        this.angle = angle;

        for(Ammo a : activeAmmo){
            a.tick(delta);
        }

        Iterator<Ammo> it = activeAmmo.iterator();
        while(it.hasNext()) {
            Ammo a = it.next();
            if(a.isRemove()){
                it.remove();
            }
        }
    }

    public void addActiveAmmo(Ammo a){
        if(ammoCount > 0){
            activeAmmo.add(a);
            ammoCount--;
        } else {
            System.out.println("Clink");
        }
    }

    public void addAmmo(int count) {
        ammoCount += count;
    }

    public void drawRotatedGun(Batch batch, float delta){
        stateTime += delta;
        weaponSprite = FadingReality.resourceManager.playerRifleAnimRight.getKeyFrame(stateTime, true);

        if(angle > 90 && angle < 270){ // LEFT
            weaponSprite.setFlip(false, true);
            offsetX = -8;
        } else { // RIGHT
            weaponSprite.setFlip(false, false);
            offsetX = -20;
        }

        batch.draw(weaponSprite, pos.x + offsetX +  77, pos.y + offsetY + 74, 1, weaponSprite.getHeight() / 2, weaponSprite.getWidth(), weaponSprite.getHeight(), 1, 1, angle);
    }

    public void drawAmmo(Batch batch) {
        for(Ammo a : activeAmmo){
            a.draw(batch);
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

    public float getAttackTime() {
        return attackTime;
    }

    public void setAttackTime(float attackTime) {
        this.attackTime = attackTime;
    }



    public AmmoID getAmmoID() {
        return ammoID;
    }

    public void setAmmoID(AmmoID ammoID) {
        this.ammoID = ammoID;
    }

    public String getPathTextureAmmo() {
        return pathTextureAmmo;
    }

    public void setPathTextureAmmo(String pathTextureAmmo) {
        this.pathTextureAmmo = pathTextureAmmo;
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



    public ArrayList<Ammo> getActiveAmmo() {
        return activeAmmo;
    }

    public void setActiveAmmo(ArrayList<Ammo> activeAmmo) {
        this.activeAmmo = activeAmmo;
    }

    public Sprite getWeaponSprite() {
        return weaponSprite;
    }

    public void setWeaponSprite(Sprite weaponSprite) {
        this.weaponSprite = weaponSprite;
    }

    public Vector3 getPos() {
        return pos;
    }

    public void setPos(Vector3 pos) {
        this.pos = pos;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getAmmoCount() {
        return ammoCount;
    }

    public void setAmmoCount(float ammoCount) {
        this.ammoCount = ammoCount;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
}