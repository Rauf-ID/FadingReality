package com.mygdx.game.weapon;

public class WeaponSystem {

    private Weapon meleeWeapon;
    private Weapon rangedWeapon;

    public WeaponSystem() {
        meleeWeapon = new Weapon();
        rangedWeapon = new Weapon();
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
}
