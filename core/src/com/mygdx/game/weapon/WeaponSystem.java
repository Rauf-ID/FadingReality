package com.mygdx.game.weapon;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.UI.PlayerHUD;
import com.mygdx.game.component.Component;
import com.mygdx.game.tools.Toast;
import com.mygdx.game.weapon.Ammo.AmmoID;

public class WeaponSystem {

    private Weapon meleeWeapon = null;
    private Weapon rangedWeapon = null;
    private static Map<String, Integer> bagAmmunition;

    private float angle;
    private float shootTimer = 0f;

    public WeaponSystem() {
       bagAmmunition = new HashMap<String, Integer>();
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
                rangedWeapon.addActiveAmmo(bullet);
                shootTimer = 0;
            }
        }

    }

    public void reloadWeapon() {
        int currentCount = rangedWeapon.getAmmoCountInMagazine();
        int magazineSize = rangedWeapon.getMagazineSize();

        int ammoCountFromBag = getAmmoCountFromBag();
        int difference = magazineSize - currentCount;

        if (currentCount < magazineSize &&  ammoCountFromBag != 0) {
            for (int i = 0; ammoCountFromBag != 0 &&  i < difference; i++) {
                ammoCountFromBag -= 1;
                rangedWeapon.addAmmoInMagazine(1);
            }
            PlayerHUD.toastShort("Weapon reloaded", Toast.Length.SHORT);
            setAmmoCountForBag(ammoCountFromBag);
        }
    }

    public void setStartAmmoCountInMagazine(int ammoCount) {
        if (rangedWeapon != null && ammoCount > 0) {
            rangedWeapon.addAmmoInMagazine(ammoCount);
        }
    }

    public int getAmmoCountFromBag() {
        if (rangedWeapon != null && !bagAmmunition.isEmpty()) {
            return bagAmmunition.get(rangedWeapon.getAmmoID().getValue());
        } else {
            return 0;
        }
    }

    public int getAmmoCountFromBag(AmmoID ammoID) {
        if (rangedWeapon != null && !bagAmmunition.isEmpty()) {
            return bagAmmunition.get(ammoID.getValue());
        } else {
            return 0;
        }
    }

    public void setAmmoCountForBag(int ammoCount) {
        if (ammoCount >= 0) {
            bagAmmunition.remove(rangedWeapon.getAmmoID().toString());
            bagAmmunition.put(rangedWeapon.getAmmoID().getValue(), ammoCount);
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

    public static Map<String, Integer> getBagAmmunition() {
        return bagAmmunition;
    }

    public void setBagAmmunition(Map<String, Integer> bagAmmunition) {
        this.bagAmmunition = bagAmmunition;
    }

}
