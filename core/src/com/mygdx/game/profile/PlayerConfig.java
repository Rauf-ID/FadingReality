package com.mygdx.game.profile;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.quest.QuestGraph;
import com.mygdx.game.inventory.InventoryItemLocation;
import com.mygdx.game.weapon.Ammo.AmmoID;
import com.mygdx.game.world.MapFactory;

import java.util.HashMap;

public class PlayerConfig {

    private Entity.State state;
    private Entity.Direction direction;
    private Vector2 position;
    private MapFactory.MapType currentMap;
    private Array<QuestGraph> playerQuests;
    private Array<InventoryItemLocation> inventory;
    private Array<InventoryItemLocation> equipment;
    private HashMap<AmmoID, Integer> allAmmoCount;
    private int money;
    private int hp;

    private int level;
    private int exp;
    private int maxHp;
    private int maxExp;
    private int minDamage;
    private int maxDamage;

    PlayerConfig() {
        inventory = new Array<>();
        equipment = new Array<>();
        allAmmoCount = new HashMap<AmmoID, Integer>();
    }


    public Entity.State getState() {
        return state;
    }

    public void setState(Entity.State state) {
        this.state = state;
    }

    public Entity.Direction getDirection() {
        return direction;
    }

    public void setDirection(Entity.Direction direction) {
        this.direction = direction;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public MapFactory.MapType getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(MapFactory.MapType currentMap) {
        this.currentMap = currentMap;
    }

    public Array<QuestGraph> getPlayerQuests() {
        return playerQuests;
    }

    public void setPlayerQuests(Array<QuestGraph> playerQuests) {
        this.playerQuests = playerQuests;
    }

    public Array<InventoryItemLocation> getInventory() {
        return inventory;
    }

    public void setInventory(Array<InventoryItemLocation> inventory) {
        this.inventory = inventory;
    }

    public Array<InventoryItemLocation> getEquipment() {
        return equipment;
    }

    public void setEquipment(Array<InventoryItemLocation> equipment) {
        this.equipment = equipment;
    }

    public HashMap<AmmoID, Integer> getAllAmmoCount() {
        return allAmmoCount;
    }

    public void setAllAmmoCount(HashMap<AmmoID, Integer> allAmmoCount) {
        this.allAmmoCount = allAmmoCount;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMaxExp() {
        return maxExp;
    }

    public void setMaxExp(int maxExp) {
        this.maxExp = maxExp;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }
}
