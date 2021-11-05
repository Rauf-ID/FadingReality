package com.mygdx.game.weapon;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.component.Component;
import com.mygdx.game.weapon.Ammo.AmmoID;

public class WeaponSystem {

    private Weapon meleeWeapon = null;
    private Weapon rangedWeapon = null;
    private HashMap<AmmoID, Integer> bagAmmunition;

    private float angle;
    private float shootTimer = 0f;

    public WeaponSystem() {
       bagAmmunition = new HashMap<AmmoID, Integer>();
    }

    public void update(float delta, Component player) {

        if (meleeWeapon != null) {
            meleeWeapon.update(delta);
        }

        if (rangedWeapon != null) {
            updateAngleCenterToMouse();
            rangedWeapon.update(delta, player.currentEntityPosition.x, player.currentEntityPosition.y, angle);

            shootTimer += delta;
            if (player.isGunActive2 && shootTimer >= rangedWeapon.getAttackTime()){ //&& shootTimer >= SHOOT_WAIT_TIMER
                player.isGunActive2 = false;
                Ammo bullet = new Ammo(rangedWeapon);
                rangedWeapon.addActiveAmmo(bullet, getBagAmmunition());
                shootTimer = 0;
            }
        }

    }

    public void addAmmoCountInMagazine(int ammoCount) {
        if (rangedWeapon != null && !bagAmmunition.isEmpty()) {
            int ammoCountFromBag = getAmmoCountFromBag(rangedWeapon.getAmmoID());
            rangedWeapon.addAmmoInMagazine(ammoCount);
        }
    }


    public void reloadWeapon() {
//        if ()
    }

    public int getAmmoCountFromBag() {
        return bagAmmunition.get(rangedWeapon.getAmmoID().toString());
    }

    public int getAmmoCountFromBag(AmmoID ammoID) {
        return bagAmmunition.get(ammoID.toString());
    }

    public void setStartAmmoCountInMagazine(int ammoCount) {
        if (rangedWeapon != null && ammoCount != 0) {
            rangedWeapon.addAmmoInMagazine(ammoCount);
        }
    }

    public void updateAngleCenterToMouse() {
        float screenX = Gdx.input.getX();
        float screenY = Gdx.input.getY();
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        angle = (float) Math.toDegrees(Math.atan2(screenX - (screenWidth/2), screenY - (screenHeight/2)));
        angle = angle < 0 ? angle += 360: angle;
        angle -= 90;
    }





    public boolean meleeIsActive() {
        return meleeWeapon != null;
    }

    public boolean rangedIsActive() {
        return rangedWeapon != null;
    }

    public Weapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public void setMeleeWeapon(Weapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public Weapon getRangedWeapon() {
        return rangedWeapon;
    }

    public void setRangedWeapon(Weapon rangedWeapon) {
        this.rangedWeapon = rangedWeapon;
    }

    public HashMap<AmmoID, Integer> getBagAmmunition() {
        return bagAmmunition;
    }

    public void setBagAmmunition(HashMap<AmmoID, Integer> bagAmmunition) {
        this.bagAmmunition = bagAmmunition;
    }

}
