package com.mygdx.game.rudiment;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.component.Message;
import com.mygdx.game.component.Player;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.item.Item;
import com.mygdx.game.weapon.Weapon;

import java.util.ArrayList;

public class RudimentWeapon extends Weapon implements Rudiment{

    private String rudimentType = "Weapon";
    private Item.ItemID rudimentID;
    private String name;
    private ArrayList<Integer> rudimentStatsProperties;

    public RudimentWeapon(){}

    public RudimentWeapon(RudimentWeapon rudimentWeapon){
        this.setRudimentID(rudimentWeapon.getRudimentID());
        this.setName(rudimentWeapon.getName());
        this.setRudimentType(rudimentWeapon.getRudimentType());
    }

    public void equipRudiment(Entity player){
        player.setMaxHealth(player.getMaxHealth()+rudimentStatsProperties.get(0));
        player.setDashCharge(player.getMaxDashCharges()+rudimentStatsProperties.get(1));
        player.setMeleeDamageBoost(player.getMeleeDamageBoost()+rudimentStatsProperties.get(2));
        player.setDamageResist(player.getDamageResist()+rudimentStatsProperties.get(3));
        player.setWeaponSpeed(player.getWeaponSpeed()+rudimentStatsProperties.get(4));
    }

    public void unequipRudiment(Entity player){
        player.setMaxHealth(player.getMaxHealth()-rudimentStatsProperties.get(0));
        player.setDashCharge(player.getMaxDashCharges()-rudimentStatsProperties.get(1));
        player.setMeleeDamageBoost(player.getMeleeDamageBoost()-rudimentStatsProperties.get(2));
        player.setDamageResist(player.getDamageResist()-rudimentStatsProperties.get(3));
        player.setWeaponSpeed(player.getWeaponSpeed()-rudimentStatsProperties.get(4));
    }

    public void activateRudiment(Player player){
        switch (this.getName()){
            case "Superior":
                Array<Entity> nearbyEnemies = player.checkForNearbyEnemies();
                for( Entity mapEntity : nearbyEnemies) {
                    mapEntity.setHealth(0);
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
