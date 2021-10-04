package com.mygdx.game.profile;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.quest.QuestGraph;
import com.mygdx.game.inventory.InventoryItemLocation;
import com.mygdx.game.world.MapFactory;

public class PlayerConfig {

    private Entity.State state;
    private Entity.Direction direction;
    private Vector2 position;
    private MapFactory.MapType currentMap;
    private Array<QuestGraph> playerQuests;
    private Array<InventoryItemLocation> inventory;
    private int hp;
    private int money;

    private int level;
    private int exp;
    private int maxHp;
    private int maxExp;
    private int minDamage;
    private int maxDamage;

    PlayerConfig() {
    }

//    PlayerConfig(PlayerConfig config) {
//        positionX = config.getPositionX();
//        positionY = config.getPцositionY();
//        mainInventory = config.getMainInventory();
//        equipInventory = config.getEquipInventory();
//
//        hp = config.getHp();
//        money = config.getMoney();
//
//        level = config.getLevel();
//        exp = config.getExp();
//        maxHp = config.getMaxHp();
//        maxExp = config.getMaxExp();
//        minDamage = config.getMinDamage();
//        maxDamage = config.getMaxDamage();
//    }


    public void setCurrentMap(MapFactory.MapType currentMap) {
        this.currentMap = currentMap;
    }
    public MapFactory.MapType getCurrentMap() {
        return currentMap;
    }

    public void setInventory(Array<InventoryItemLocation> inventory) {
        this.inventory = inventory;
    }
    public Array<InventoryItemLocation> getInventory() {
        return inventory;
    }

    public void setPlayerQuests(Array<QuestGraph> playerQuests) {
        this.playerQuests = playerQuests;
    }
    public Array<QuestGraph> getPlayerQuests() {
        return playerQuests;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    public int getMoney() { return money; }

    public void setExp(int exp) {
        this.exp = exp;
    }
    public int getExp() {
        return exp;
    }

    public void setMaxExp(int maxExp) {
        this.maxExp = maxExp;
    }
    public int getMaxExp() {
        return maxExp;
    }

    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
    public int getMaxHp() {
        return maxHp;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }
    public int getMaxDamage() {
        return maxDamage;
    }

    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }
    public int getMinDamage() {
        return minDamage;
    }

    public Entity.Direction getDirection() {
        return direction;
    }

    public void setDirection(Entity.Direction direction) {
        this.direction = direction;
    }

    public Entity.State getState() {
        return state;
    }

    public void setState(Entity.State state) {
        this.state = state;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
