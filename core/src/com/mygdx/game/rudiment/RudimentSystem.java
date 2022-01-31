package com.mygdx.game.rudiment;

import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.UI.PlayerHUD;
import com.mygdx.game.component.Component;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.tools.Rumble;
import com.mygdx.game.tools.Toast;
import com.mygdx.game.weapon.Ammo;
import com.mygdx.game.weapon.Ammo.AmmoID;
import com.mygdx.game.weapon.Weapon;

public class RudimentSystem {
    private Weapon meleeWeapon = null;
    private Weapon rangedWeapon = null;
    private static Map<String, Integer> bagAmmunition;

    private float angle;
    private float shootTimer = 0f;

    public RudimentSystem() {

    }
}

