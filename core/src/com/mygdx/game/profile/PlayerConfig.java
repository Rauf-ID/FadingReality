package com.mygdx.game.profile;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityConfig;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.item.Item;
import com.mygdx.game.quest.QuestGraph;
import com.mygdx.game.inventory.InventoryItemLocation;
import com.mygdx.game.world.MapFactory;

import java.util.HashMap;
import java.util.Map;

public class PlayerConfig {

    private Vector2 position;
    private Entity.State state;
    private Array<Integer> mapItems;
    private Array<QuestGraph> quests;
    private Entity.Direction direction;
    private Array<Item.ItemID> shopItems;
    private MapFactory.MapType currentMap;
    private Map<String, Integer> bagAmmunition;
    private Array<InventoryItemLocation> inventory;
    private Array<InventoryItemLocation> equipment;
    private EntityFactory.EntityName exoskeletonName;
    private Array<Integer> playerSkills, availableSkills;
    private Map<String, Array<Integer>> idEntityForDelete;

    private int exp;
    private int maxHp;
    private int coins;
    private int level;
    private int health;
    private int maxExp;
    private int dashDist;
    private int dashSpeed;
    private int minDamage;
    private int maxDamage;
    private int healAmount;
    private int experience;
    private int critChance;
    private int dashCharges;
    private int damageBoost;
    private int weaponSpeed;
    private int damageResist;
    private int maxDashCharges;
    private float rudimentCharge;
    private int meleeDamageBoost;
    private int rudimentCooldown;
    private int rangedDamageBoost;
    private int executionThreshold;

    PlayerConfig() {
        mapItems = new Array<>();
        inventory = new Array<>();
        equipment = new Array<>();
        shopItems = new Array<>();
        bagAmmunition = new HashMap<>();
        idEntityForDelete = new HashMap<>();
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

    public Array<Item.ItemID> getShopItems() {
        return shopItems;
    }

    public void setShopItems(Array<Item.ItemID> shopItems) {
        this.shopItems = shopItems;
    }

    public Map<String, Array<Integer>> getIdEntityForDelete() {
        return idEntityForDelete;
    }

    public void setIdEntityForDelete(Map<String, Array<Integer>> idEntityForDelete) {
        this.idEntityForDelete = idEntityForDelete;
    }

    public Array<Integer> getMapItems() {
        return mapItems;
    }

    public void setMapItems(Array<Integer> mapItems) {
        this.mapItems = mapItems;
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

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
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

    public int getDashCharges() {
        return dashCharges;
    }

    public void setDashCharges(int dashCharges) {
        this.dashCharges = dashCharges;
    }

    public int getMaxDashCharges() {
        return maxDashCharges;
    }

    public void setMaxDashCharges(int maxDashCharges) {
        this.maxDashCharges = maxDashCharges;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
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

    public float getRudimentCharge() {
        return rudimentCharge;
    }

    public void setRudimentCharge(float rudimentCharge) {
        this.rudimentCharge = rudimentCharge;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public int getCritChance() {
        return critChance;
    }

    public void setCritChance(int critChance) {
        this.critChance = critChance;
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

    public int getDashSpeed(){return dashSpeed;}

    public void setDashSpeed(int dashSpeed){this.dashSpeed = dashSpeed;}

    public int getDashDist(){return dashDist;}

    public void setDashDist(int dashDist){this.dashDist = dashDist;}

    public Array<Integer> getPlayerSkills(){return this.playerSkills;}

    public void setPlayerSkills(Array<Integer> playerSkills){this.playerSkills=playerSkills;}

    public Array<Integer> getAvailableSkills(){return this.availableSkills;}

    public void setAvailableSkills(Array<Integer> availableSkills){this.availableSkills=availableSkills;}

}
