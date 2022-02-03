package com.mygdx.game.rudiment;

import com.mygdx.game.entity.Entity;
import com.mygdx.game.item.Item;

import java.util.ArrayList;

public class Rudiment {

    private Item.ItemID rudimentID;
    private String name;
    private ArrayList<Integer> rudimentStatsProperties;

    public Rudiment(){

    }

    public Rudiment(Rudiment rudiment){
        this.rudimentID = rudiment.getRudimentID();
        this.name = rudiment.getName();
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
}
