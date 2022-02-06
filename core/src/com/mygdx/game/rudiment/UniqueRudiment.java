package com.mygdx.game.rudiment;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.component.Message;
import com.mygdx.game.component.Player;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.item.Item;

import java.util.ArrayList;

public class UniqueRudiment extends PassiveRudiment{

    private String rudimentType;
    private Item.ItemID weaponID, rudimentID;
    private String name;
    private ArrayList<Integer> rudimentStatsProperties;

    public UniqueRudiment(){}

    public UniqueRudiment(UniqueRudiment rudimentWeapon){
        this.setRudimentID(rudimentWeapon.getRudimentID());
        this.setName(rudimentWeapon.getName());
        this.setWeaponID(rudimentWeapon.getWeaponID());
        this.setRudimentType(rudimentWeapon.getRudimentType());
    }

    @Override
    public void activateRudiment(Player player){
        switch (this.getName()){
            case "Superior":
                Array<Entity> nearbyEnemies = player.checkForNearbyEnemies();
                for( Entity mapEntity : nearbyEnemies) {
                    mapEntity.setHealth(0);
                }
            case "Vortex":
                nearbyEnemies = player.checkForNearbyEnemies();
                for( Entity mapEntity : nearbyEnemies) {
                    mapEntity.sendMessage(Message.MESSAGE.STUN);
                }
                break;
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

    public String getRudimentType() {
        return rudimentType;
    }

    public void setRudimentType(String rudimentType) {
        this.rudimentType = rudimentType;
    }
}
