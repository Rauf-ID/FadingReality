package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.inventory.InventoryItem.ItemID;
import com.mygdx.game.tools.managers.ResourceManager;

public class EntityConfig {

    private String entityID;
    private String entityName;
    private Entity.State state;
    private Entity.Direction direction;
    private String conversationConfigPath;
    private String questConfigPath;
    private String currentQuestID;
    private String itemTypeID;
    private Array<ItemID> inventory;
    private Array<AnimationConfig> animationConfig;

    private Vector2 hitBox;
    private Vector2 imageBox;
    private Vector2 boundingBox;
    private Vector2 activeZoneBox;
    private Vector2 attackZoneBox;

    private Vector2 walkVelocity;
    private Vector2 walkVelocityD;
    private Vector2 runVelocity;
    private Vector2 runVelocityD;

    private int health;
    private int damageResist;

    EntityConfig() {
        inventory = new Array<ItemID>();
        animationConfig = new Array<AnimationConfig>();

        hitBox = new Vector2();
        imageBox = new Vector2();
        boundingBox = new Vector2();
        activeZoneBox = new Vector2();
        attackZoneBox = new Vector2();

        walkVelocity  = new Vector2();
        walkVelocityD = new Vector2();
        runVelocity = new Vector2();
        runVelocityD = new Vector2();
    }

    EntityConfig(EntityConfig config) {
        state = config.getState();
        direction = config.getDirection();
        entityID = config.getEntityID();
        entityName = config.getEntityName();
        conversationConfigPath = config.getConversationConfigPath();
        questConfigPath = config.getQuestConfigPath();
        currentQuestID = config.getCurrentQuestID();
        itemTypeID = config.getItemTypeID();

        inventory = new Array<ItemID>();
        inventory.addAll(config.getInventory());

        animationConfig = new Array<AnimationConfig>();
        animationConfig.addAll(config.getAnimationConfig());

        hitBox = config.getHitBox();
        imageBox = config.getImageBox();
        boundingBox = config.getBoundingBox();
        activeZoneBox = config.getActiveZoneBox();
        attackZoneBox = config.getAttackZoneBox();

        walkVelocity = config.getWalkVelocity();
        walkVelocityD = config.getWalkVelocityD();
        runVelocity=config.getRunVelocity();
        runVelocityD=config.getRunVelocityD();

        health = config.getHealth();
        damageResist = config.getDamageResist();
    }



    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
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

    public String getConversationConfigPath() {
        return conversationConfigPath;
    }

    public void setConversationConfigPath(String conversationConfigPath) {
        this.conversationConfigPath = conversationConfigPath;
    }

    public String getQuestConfigPath() {
        return questConfigPath;
    }

    public void setQuestConfigPath(String questConfigPath) {
        this.questConfigPath = questConfigPath;
    }

    public String getCurrentQuestID() {
        return currentQuestID;
    }

    public void setCurrentQuestID(String currentQuestID) {
        this.currentQuestID = currentQuestID;
    }

    public String getItemTypeID() {
        return itemTypeID;
    }

    public void setItemTypeID(String itemTypeID) {
        this.itemTypeID = itemTypeID;
    }

    public Array<ItemID> getInventory() {
        return inventory;
    }

    public void setInventory(Array<ItemID> inventory) {
        this.inventory = inventory;
    }

    public Array<AnimationConfig> getAnimationConfig() {
        return animationConfig;
    }

    public void setAnimationConfig(Array<AnimationConfig> animationConfig) {
        this.animationConfig = animationConfig;
    }

    public Vector2 getImageBox() {
        return imageBox;
    }

    public void setImageBox(Vector2 imageBox) {
        this.imageBox = imageBox;
    }

    public Vector2 getHitBox() {
        return hitBox;
    }

    public void setHitBox(Vector2 hitBox) {
        this.hitBox = hitBox;
    }

    public Vector2 getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Vector2 boundingBox) {
        this.boundingBox = boundingBox;
    }

    public Vector2 getActiveZoneBox() {
        return activeZoneBox;
    }

    public void setActiveZoneBox(Vector2 activeZoneBox) {
        this.activeZoneBox = activeZoneBox;
    }

    public Vector2 getAttackZoneBox() {
        return attackZoneBox;
    }

    public void setAttackZoneBox(Vector2 attackZoneBox) {
        this.attackZoneBox = attackZoneBox;
    }

    public Vector2 getWalkVelocity(){return walkVelocity;}

    public void setWalkVelocity(Vector2 walkVelocity){this.walkVelocity = walkVelocity;}

    public Vector2 getWalkVelocityD(){return walkVelocityD;}

    public void setWalkVelocityD(Vector2 runVelocity){this.walkVelocity = walkVelocityD;}

    public Vector2 getRunVelocity() {return runVelocity;}

    public Vector2 getRunVelocityD() {return runVelocityD;}

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamageResist(){
        return damageResist;
    }

    public void setDamageResist(int damageResist){
        this.damageResist = damageResist;
    }

    static public class AnimationConfig {

        private float frameDuration = 1.0f;
        private Entity.AnimationType animationType;
        private ResourceManager.AtlasType atlasType;
        private Animation.PlayMode playMode;

        public AnimationConfig(){
            animationType = Entity.AnimationType.IDLE_RIGHT;
            atlasType = ResourceManager.AtlasType.NONE;
            playMode = Animation.PlayMode.LOOP;
        }

        public void setFrameDuration(float frameDuration) {
            this.frameDuration = frameDuration;
        }

        public float getFrameDuration() {
            return frameDuration;
        }

        public Entity.AnimationType getAnimationType() {
            return animationType;
        }

        public void setAnimationType(Entity.AnimationType animationType) {
            this.animationType = animationType;
        }

        public ResourceManager.AtlasType getAtlasType() {
            return atlasType;
        }

        public void setAtlasType(ResourceManager.AtlasType atlasType) {
            this.atlasType = atlasType;
        }

        public void setPlayMode(Animation.PlayMode playMode) {
            this.playMode = playMode;
        }

        public Animation.PlayMode getPlayMode() {
            return playMode;
        }

    }

}
