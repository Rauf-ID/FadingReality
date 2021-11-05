package com.mygdx.game.weapon;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.component.Component;
import com.mygdx.game.weapon.Ammo.AmmoID;

public class WeaponSystem {

    private Weapon meleeWeapon = null;
    private Weapon rangedWeapon = null;
    private HashMap<AmmoID, Integer> allAmmoCount;

    private float angle;
    private float shootTimer = 0f;

    public WeaponSystem() {
       allAmmoCount = new HashMap<AmmoID, Integer>();
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
                shootTimer = 0;
                player.isGunActive2 = false;
                Ammo bullet = new Ammo(rangedWeapon);
                rangedWeapon.addActiveAmmo(bullet);
            }
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
        if (rangedWeapon != null && !allAmmoCount.isEmpty()) {
            int b = allAmmoCount.get(rangedWeapon.getAmmoID().toString());
            rangedWeapon.addAmmo(b);
        }
    }

    public HashMap<AmmoID, Integer> getAllAmmoCount() {
        return allAmmoCount;
    }

    public void setAllAmmoCount(HashMap<AmmoID, Integer> allAmmoCount) {
        this.allAmmoCount = allAmmoCount;
    }
}
