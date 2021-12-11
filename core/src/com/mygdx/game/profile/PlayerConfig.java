package com.mygdx.game.profile;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.quest.QuestGraph;
import com.mygdx.game.inventory.InventoryItemLocation;
import com.mygdx.game.skills.Skill;
import com.mygdx.game.world.MapFactory;

import java.util.HashMap;
import java.util.Map;

public class PlayerConfig {

    private Entity.State state;
    private Entity.Direction direction;
    private Vector2 position;
    private MapFactory.MapType currentMap;
    private Array<QuestGraph> quests;
    private Array<InventoryItemLocation> inventory;
    private Array<InventoryItemLocation> equipment;
    private Map<String, Integer> bagAmmunition;
    private EntityFactory.EntityName exoskeletonName;
    private int money;
    private int hp;

    private int level;
    private int exp;
    private int maxHp;
    private int maxExp;
    private int minDamage;
    private int maxDamage;
    private int dashCharges;
    private int maxDashCharges;
    private int damageResist;
    private int meleeDamageBoost;
    private int rangedDamageBoost;
    private int rudimentCooldown;
    private int weaponSpeed;
    private int critChanсe;
    private int healAmount;
    private int executionThreshold;
    private int damageBoost;
    private float rudimentCharge;
    private Array<Integer> playerSkills, availableSkills;


    private int health;

    PlayerConfig() {
        inventory = new Array<>();
        equipment = new Array<>();
        bagAmmunition = new HashMap<String, Integer>();
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

    public Array<QuestGraph> getQuests() {
        return quests;
    }

    public void setQuests(Array<QuestGraph> quests) {
        this.quests = quests;
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

    public Map<String, Integer> getBagAmmunition() {
        return bagAmmunition;
    }

    public void setBagAmmunition(Map<String, Integer> bagAmmunition) {
        this.bagAmmunition = bagAmmunition;
    }

    public EntityFactory.EntityName getExoskeletonName() {
        return exoskeletonName;
    }

    public void setExoskeletonName(EntityFactory.EntityName exoskeletonName) {
        this.exoskeletonName = exoskeletonName;
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

    public int getDashCharges(){return dashCharges;}

    public void setDashCharges(int dashCharges){this.dashCharges=dashCharges;}

    public int getMaxDashCharges(){return maxDashCharges;}

    public void setMaxDashCharges(int maxDashCharges){this.maxDashCharges=maxDashCharges;}

    public float getRudimentCharge(){return rudimentCharge;}

    public void setRudimentCharge(float rudimentCharge){this.rudimentCharge=rudimentCharge;}

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamageResist() {
        return damageResist;
    }

    public void setDamageResist(int damageResist) {
        this.damageResist = damageResist;
    }

    public int getMeleeDamageBoost() {
        return meleeDamageBoost;
    }

    public void setMeleeDamageBoost(int meleeDamageBoost) {
        this.meleeDamageBoost = meleeDamageBoost;
    }

    public int getRangedDamageBoost() {
        return rangedDamageBoost;
    }

    public void setRangedDamageBoost(int rangedDamageBoost) {
        this.rangedDamageBoost = rangedDamageBoost;
    }

    public int getRudimentCooldown() {
        return rudimentCooldown;
    }

    public void setRudimentCooldown(int rudimentCooldown) {
        this.rudimentCooldown = rudimentCooldown;
    }

    public int getWeaponSpeed() {
        return weaponSpeed;
    }

    public void setWeaponSpeed(int weaponSpeed) {
        this.weaponSpeed = weaponSpeed;
    }

    public int getCritChanсe() {
        return critChanсe;
    }

    public void setCritChanсe(int critChanсe) {
        this.critChanсe = critChanсe;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }

    public int getExecutionThreshold() {
        return executionThreshold;
    }

    public void setExecutionThreshold(int executionThreshold) {
        this.executionThreshold = executionThreshold;
    }

    public int getDamageBoost() {
        return damageBoost;
    }

    public void setDamageBoost(int damageBoost) {
        this.damageBoost = damageBoost;
    }

    public Array<Integer> getPlayerSkills(){return this.playerSkills;}

    public void setPlayerSkills(Array<Integer> playerSkills){this.playerSkills=playerSkills;}

    public Array<Integer> getAvailableSkills(){return this.availableSkills;}

    public void setAvailableSkills(Array<Integer> availableSkills){this.availableSkills=availableSkills;}

}
