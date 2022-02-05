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
    private Rudiment rudimentOne = null, rudimentTwo = null, uniqueRudiment = null;


    public RudimentSystem() {

    }

    public Rudiment getRudimentOne() {
        return rudimentOne;
    }

    public void setRudimentOne(Rudiment rudimentOne) {
        this.rudimentOne = rudimentOne;
    }

    public Rudiment getRudimentTwo() {
        return rudimentTwo;
    }

    public void setRudimentTwo(Rudiment rudimentTwo) {
        this.rudimentTwo = rudimentTwo;
    }

    public Rudiment getUniqueRudiment() {
        return uniqueRudiment;
    }

    public void setUniqueRudiment(Rudiment uniqueRudiment) {
        this.uniqueRudiment = uniqueRudiment;
    }
}

