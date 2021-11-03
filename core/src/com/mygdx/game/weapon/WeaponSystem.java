package com.mygdx.game.weapon;

import java.util.HashMap;
import com.mygdx.game.weapon.Ammo.AmmoID;

public class WeaponSystem {

    private Weapon meleeWeapon;
    private Weapon rangedWeapon;

    private HashMap<AmmoID, Integer> allAmmoCount;

    public WeaponSystem() {
        meleeWeapon = new Weapon();
        rangedWeapon = new Weapon();

        allAmmoCount = new HashMap<AmmoID, Integer>();
    }

    public void update() {

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

    public HashMap<AmmoID, Integer> getAllAmmoCount() {
        return allAmmoCount;
    }

    public void setAllAmmoCount(HashMap<AmmoID, Integer> allAmmoCount) {
        this.allAmmoCount = allAmmoCount;
    }
}
