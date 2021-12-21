package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.component.Message;
import com.mygdx.game.component.Component;
import com.mygdx.game.observer.ComponentObserver;
import com.mygdx.game.skills.Skill;
import com.mygdx.game.weapon.Ammo;
import com.mygdx.game.weapon.Weapon;
import com.mygdx.game.weapon.WeaponSystem;
import com.mygdx.game.world.MapManager;

import java.util.ArrayList;
import java.util.Map;

public class Entity implements Comparable<Entity> {

    public enum MouseDirection {
        UP,
        RIGHT,
        DOWN,
        LEFT,
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT,
    }

    public enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT,
    }

    public enum State {
        NONE,
        IDLE,
        RUN,
        WALK,
        WALK_SHADOW,
        MELEE_ATTACK,
        RANGED_ATTACK,
        DASH,
        USE_RUDIMENT,
        TAKING_DAMAGE,
        FALLING,
        LANDING,
        DEAD,
        POLICE_STOPS,
        POLICE_LOOKED_AROUND,
        DEATH,
        TALK,
        MECHANISM_OPEN_GATE,
        DANCE,
        WIPES_GLASS,
        BAR_DRINK,
        HURT_AMELIA,
        DETENTION,
        AMELIA_BAT_SIT
    }

    public enum AnimationType {
        IDLE_RIGHT, IDLE_LEFT, IDLE_UP, IDLE_DOWN,
        WALK_RIGHT, WALK_LEFT, WALK_UP, WALK_DOWN,
        RUN_RIGHT, RUN_LEFT, RUN_UP, RUN_DOWN,
        MELEE_ATTACK_RIGHT, MELEE_ATTACK_LEFT, MELEE_ATTACK_UP, MELEE_ATTACK_DOWN,
        MELEE_ATTACK_RIGHT_2, MELEE_ATTACK_LEFT_2, MELEE_ATTACK_UP_2, MELEE_ATTACK_DOWN_2,
        RANGED_ATTACK_RIGHT, RANGED_ATTACK_LEFT, RANGED_ATTACK_UP, RANGED_ATTACK_DOWN,
        RIFLE_RIGHT, RIFLE_LEFT, RIFLE_UP, RIFLE_DOWN,
        DASH_RIGHT, DASH_LEFT, DASH_UP, DASH_DOWN,
        IDLE2_RIGHT,
        IDLE2_LEFT,
        BAR_DRINK,
        HURT_AMELIA,
        USE_RUDIMENT_1,
        LOOKED_AROUND,
        DETENTION,
        STOPS,
        IDLE,
    }

    private static final int MAX_COMPONENTS = 5;

    private Json json;
    private EntityConfig entityConfig;
    private Array<Message> messages;

    private Component component;

    public Entity(Component component) {
        entityConfig = new EntityConfig();
        json = new Json();

        messages = new Array<Message>(MAX_COMPONENTS);

        this.component = component;

        messages.add(this.component);
    }

    public Entity(Entity entity) {
        set(entity);
    }

    private Entity set(Entity entity) {
        component = entity.component;

        if( messages == null ){
            messages = new Array<Message>(MAX_COMPONENTS);
        }
        messages.clear();
        messages.add(component);

        json = entity.json;

        entityConfig = new EntityConfig(entity.entityConfig);
        return this;
    }


    public void sendMessage(Message.MESSAGE messageType, String ... args){
        String fullMessage = messageType.toString();

        for (String string : args) {
            fullMessage += Message.MESSAGE_TOKEN + string;
        }

        for(Message message : messages){
            message.receiveMessage(fullMessage);
        }
    }

    public void registerObserver(ComponentObserver observer){
        component.addObserver(observer);
    }

    public void unregisterObservers(){
        component.removeAllObservers();
    }

    public void update(MapManager mapMgr, Batch batch, float delta){
        component.update(this, mapMgr, batch, delta);
    }

    public void draw(Batch batch, float delta) {
        component.draw(batch, delta);
    }

    public Rectangle getBoundingBox(){
        return component.boundingBox;
    }

    public  ArrayList<Ammo> getActiveAmmo(){
        return component.activeAmmo;
    }

    public Weapon getRangeWeapon() {
        return component.weaponSystem.getRangedWeapon();
    }

    public Rectangle getCurrentSwordRangeBox() {
        return component.swordRangeBox;
    }

    public Vector2 getCurrentPosition() {
        return component.currentEntityPosition;
    }

    public Map<String, Integer> getBagAmmunition() {
        return WeaponSystem.getBagAmmunition();
    }

    public int getAmmoCountInMagazine() {
        if (component.weaponSystem.rangedIsActive()) {
            return component.weaponSystem.getRangedWeapon().getAmmoCountInMagazine();
        } else {
            return 0;
        }
    }

    public Entity.State getCurrentState(){
        return component.currentState;
    }

    public String getCurrentCollision() {
        return component.currentCollision;
    }


    public EntityFactory.EntityName getExoskeletonName() {
        return component.getExoskeletonName();
    }

    public Vector3 getMouseCoordinates(){
        return component.mouseCoordinates;
    }

    public boolean getBoolPissPiss() {
        return component.isGunActive2;
    }

    public void setBoolPissPiss(boolean boolPissPiss){
        component.isGunActive2 =boolPissPiss;
    }

    public Entity.Direction getCurrentDirection() {
        return component.currentDirection;
    }

    public void setDashCharge(int dashCharge){component.setDashCharge(dashCharge);}

    public int getDashCharge(){return component.getDashCharge();}

    public void setMaxDashCharges(int maxDashCharges){component.setMaxDashCharges(maxDashCharges);}

    public int getMaxDashCharges(){return component.getMaxDashCharges();}

    public int getMeleeDamageBoost() {
        return component.getMeleeDamageBoost();
    }

    public void setMeleeDamageBoost(int meleeDamageBoost) {
        component.setMeleeDamageBoost(meleeDamageBoost);
    }

    public int getRangedDamageBoost() {
        return component.getRangedDamageBoost();
    }

    public void setRangedDamageBoost(int rangedDamageBoost) {
        component.setRangedDamageBoost(rangedDamageBoost);
    }

    public int getRudimentCooldown() {
        return component.getRudimentCooldown();
    }

    public void setRudimentCooldown(int rudimentCooldown) {
        component.setRudimentCooldown(rudimentCooldown);
    }

    public int getWeaponSpeed() {
        return component.getWeaponSpeed();
    }

    public void setWeaponSpeed(int weaponSpeed) {
        component.setWeaponSpeed(weaponSpeed);
    }

    public int getCritChanсe() {
        return component.getCritChanсe();
    }

    public void setCritChanсe(int critChanсe) {
        component.setCritChanсe(critChanсe);
    }

    public int getHealAmount() {return component.getHealAmount();}

    public void setHealAmount(int healAmount) {
        component.setHealAmount(healAmount);
    }

    public int getExecutionThreshold() {
        return component.getExecutionThreshold();
    }

    public void setExecutionThreshold(int executionThreshold) {
        component.setExecutionThreshold(executionThreshold);
    }

    public int getDamageBoost() {return component.getDamageBoost();}

    public void setDamageBoost(int damageBoost) {component.setDamageBoost(damageBoost);}

    public int getDamageResist(){return component.getDamageResist();}

    public void setDamageResist(int damageResist){component.setDamageResist(damageResist);}

    public int getMaxHealth(){return component.getMaxHealth();}

    public void setMaxHealth(int maxHealth){component.setMaxHealth(maxHealth);}

    public int getHealth(){return component.getHealth();}

    public void setHealth(int health){component.setHealth(health);}

    public Array<Integer> getPlayerSkills(){return component.getPlayerSkills();}

    public void setPlayerSkills(Array<Integer> playerSkills){ component.setPlayerSkills(playerSkills);}

    public Array<Integer> getAvailableSkills() {return component.getAvailableSkills();}

    public void setAvailableSkills(Array<Integer> availableSkills) {component.setAvailableSkills(availableSkills);}

    public InputProcessor getInputProcessor(){
        return component;
    }

    public void setEntityConfig(EntityConfig entityConfig){
        this.entityConfig = entityConfig;
    }

    public EntityConfig getEntityConfig() {
        return entityConfig;
    }

    static public EntityConfig getEntityConfig(String configFilePath){
        Json json = new Json();
        return json.fromJson(EntityConfig.class, Gdx.files.internal(configFilePath));
    }

    static public Array<EntityConfig> getEntityConfigs(String configFilePath){
        Json json = new Json();
        Array<EntityConfig> configs = new Array<EntityConfig>();

        ArrayList<JsonValue> list = json.fromJson(ArrayList.class, Gdx.files.internal(configFilePath));

        for (JsonValue jsonVal : list) {
            configs.add(json.readValue(EntityConfig.class, jsonVal));
        }

        return configs;
    }


    public static EntityConfig loadEntityConfigByPath(String entityConfigPath){
        return Entity.getEntityConfig(entityConfigPath);
    }

    public static Entity initNPC(EntityConfig entityConfig){
        Json json = new Json();
        Entity entity = EntityFactory.getEntity(EntityFactory.EntityType.NPC);
        entity.setEntityConfig(entityConfig);
        entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, json.toJson(entity.getEntityConfig()));
        return entity;
    }

    public static Entity initEnemy(EntityConfig entityConfig){
        Json json = new Json();
        Entity entity = EntityFactory.getEntity(EntityFactory.EntityType.ENEMY);
        entity.setEntityConfig(entityConfig);
        entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, json.toJson(entity.getEntityConfig()));
        return entity;
    }

    public static Entity initNPCForQuest(EntityConfig entityConfig, Vector2 position, Entity.Direction direction){
        Json json = new Json();
        Entity entity = EntityFactory.getEntity(EntityFactory.EntityType.NPC);
        entity.setEntityConfig(entityConfig);

        entity.sendMessage(Component.MESSAGE.INIT_START_POSITION, json.toJson(position));
        entity.sendMessage(Component.MESSAGE.CURRENT_DIRECTION, json.toJson(direction));
        entity.sendMessage(Message.MESSAGE.INIT_CONFIG, json.toJson(entity.getEntityConfig()));
        entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, json.toJson(entity.getEntityConfig()));

        return entity;
    }

    public static Entity initEnemyForQuest(EntityConfig entityConfig, Vector2 position, Entity.Direction direction){
        Json json = new Json();
        Entity entity = EntityFactory.getEntity(EntityFactory.EntityType.ENEMY);
        entity.setEntityConfig(entityConfig);

        entity.sendMessage(Component.MESSAGE.INIT_START_POSITION, json.toJson(position));
        entity.sendMessage(Component.MESSAGE.CURRENT_DIRECTION, json.toJson(direction));
        entity.sendMessage(Message.MESSAGE.INIT_CONFIG, json.toJson(entity.getEntityConfig()));
        entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, json.toJson(entity.getEntityConfig()));

        return entity;
    }

    public static Entity initExoskeleton(EntityConfig entityConfig){
        Json json = new Json();
        Entity entity = EntityFactory.getEntity(EntityFactory.EntityType.EXOSKELETON);
        entity.setEntityConfig(entityConfig);
        entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, json.toJson(entity.getEntityConfig()));
        return entity;
    }


    @Override
    public int compareTo(Entity entity) {
        float temp_y =  entity.getBoundingBox().y;
        float compare_y = getBoundingBox().y;

        return (temp_y < compare_y ) ? -1: (temp_y > compare_y) ? 1:0;
    }

    public void dispose(){
        for(Message message : messages){
            message.dispose();
        }
    }

}
