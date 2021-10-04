package com.mygdx.game.entity;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.inventory.InventoryItem.ItemTypeID;

import java.awt.Rectangle;

public class EntityConfig {

    public static enum EntityProperties{
        ENTITY_HEALTH_POINTS,
        ENTITY_ATTACK_POINTS,
        ENTITY_DEFENSE_POINTS,
        ENTITY_HIT_DAMAGE_TOTAL,
        ENTITY_XP_REWARD,
        ENTITY_GP_REWARD,
        NONE
    }

    private Array<ItemTypeID> inventory;
    private Entity.State state;
    private Entity.Direction direction;
    private String entityID;
    private String conversationConfigPath;
    private String questConfigPath;
    private String currentQuestID;
    private String itemTypeID;
    private int attackRangeBoxWidth, attackRangeBoxHeight;
    private ObjectMap<String, String> entityProperties;

    EntityConfig() {
        inventory = new Array<ItemTypeID>();
        entityProperties = new ObjectMap<String, String>();
    }

    EntityConfig(EntityConfig config) {
        state = config.getState();
        direction = config.getDirection();
        entityID = config.getEntityID();
        conversationConfigPath = config.getConversationConfigPath();
        questConfigPath = config.getQuestConfigPath();
        currentQuestID = config.getCurrentQuestID();
        itemTypeID = config.getItemTypeID();

        attackRangeBoxWidth = config.getAttackRadiusBoxWidth();
        attackRangeBoxHeight = config.getAttackRadiusBoxHeight();

        inventory = new Array<ItemTypeID>();
        inventory.addAll(config.getInventory());

        entityProperties = new ObjectMap<String, String>();
        entityProperties.putAll(config.entityProperties);
    }

    public void setAttackRadiusBoxWidth(int attackRadiusBoxWidth) {
        this.attackRangeBoxWidth = attackRadiusBoxWidth;
    }

    public int getAttackRadiusBoxWidth() {
        return attackRangeBoxWidth;
    }

    public void setAttackRadiusBoxHeight(int attackRadiusBoxHeight) {
        this.attackRangeBoxHeight = attackRadiusBoxHeight;
    }

    public int getAttackRadiusBoxHeight() {
        return attackRangeBoxHeight;
    }

    public void setInventory(Array<ItemTypeID> inventory) {
        this.inventory = inventory;
    }

    public Array<ItemTypeID> getInventory() {
        return inventory;
    }

    public void setState(Entity.State state) {
        this.state = state;
    }

    public Entity.State getState() {
        return state;
    }

    public void setDirection(Entity.Direction direction) {
        this.direction = direction;
    }

    public Entity.Direction getDirection() {
        return direction;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setConversationConfigPath(String conversationConfigPath) {
        this.conversationConfigPath = conversationConfigPath;
    }

    public String getConversationConfigPath() {
        return conversationConfigPath;
    }

    public void setQuestConfigPath(String questConfigPath) {
        this.questConfigPath = questConfigPath;
    }

    public String getQuestConfigPath() {
        return questConfigPath;
    }

    public void setCurrentQuestID(String currentQuestID) {
        this.currentQuestID = currentQuestID;
    }

    public String getCurrentQuestID() {
        return currentQuestID;
    }

    public void setItemTypeID(String itemTypeID) {
        this.itemTypeID = itemTypeID;
    }

    public String getItemTypeID() {
        return itemTypeID;
    }

}
