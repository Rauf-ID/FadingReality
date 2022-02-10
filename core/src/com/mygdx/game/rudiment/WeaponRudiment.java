package com.mygdx.game.rudiment;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.component.Player;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.item.Item;
import com.mygdx.game.weapon.Weapon;

import java.util.ArrayList;

public class WeaponRudiment extends Weapon {

    private Item.ItemID weaponID, rudimentID;
    private String name;
    private ArrayList<Integer> rudimentStatsProperties;

    public WeaponRudiment(){
        super();
    }

    public WeaponRudiment(WeaponRudiment weaponRudiment){
        super(weaponRudiment);
        this.setRudimentID(weaponRudiment.getRudimentID());
        this.setName(weaponRudiment.getName());
        this.setWeaponID(weaponRudiment.getWeaponID());
    }

    public void activateRudiment(Player player){
        switch (this.getName()){
            case "Superior":
                Array<Entity> nearbyEnemies = player.checkForNearbyEnemies();
                for( Entity mapEntity : nearbyEnemies) {
                    mapEntity.setHealth(0);
                }
        }
    }

    public Item.ItemID getRudimentID() {
        return rudimentID;
    }

    public void setRudimentID(Item.ItemID rudimentID) {
        this.rudimentID = rudimentID;
    }

    public Item.ItemID getWeaponID() {
        return weaponID;
    }

    public void setWeaponID(Item.ItemID weaponID) {
        this.weaponID = weaponID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getRudimentStatsProperties() {
        return rudimentStatsProperties;
    }

    public void setRudimentStatsProperties(ArrayList<Integer> rudimentStatsProperties) {
        this.rudimentStatsProperties = rudimentStatsProperties;
    }

}
