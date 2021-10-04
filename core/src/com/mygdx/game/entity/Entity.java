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
import com.mygdx.game.world.MapManager;

import java.util.ArrayList;

public class Entity implements Comparable<Entity> {

    @Override
    public int compareTo(Entity entity) {
        float temp_y =  entity.getCurrentEntityRangeBox().y;
        float compare_y = getCurrentEntityRangeBox().y;

        return (temp_y < compare_y ) ? -1: (temp_y > compare_y) ? 1:0;
    }

    public static enum MouseDirection {
        UP,
        RIGHT,
        DOWN,
        LEFT,
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT,
    }

    public static enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT,
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT,
    }

    public static enum State {
        NONE,
        IDLE,
        RUN,
        WALK,
        WALK_SHADOW,
        RUNING,
        MELEE_ATTACK,
        WEAPON_ATTACK,
        DASH,
        USE_RUDIMENT,
        SWORD__ATTACK,
        SHOOT_ATTACK,
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

    public Rectangle getCurrentBoundingBox(){
        return component.boundingBox;
    }

    public Rectangle getCurrentEntityRangeBox() {
        return component.entityRangeBox;
    }

    public Rectangle getCurrentSwordRangeBox() {
        return component.swordRangeBox;
    }

    public Vector2 getCurrentPosition() {
        return component.currentEntityPosition;
    }

    public Entity.State getCurrentState(){
        return component.currentState;
    }

    public Vector3 getMouseCoordinates(){
        return component.mouseCoordinates;
    }

//    public Rectangle getMeleeAttackBox() {
//        return physicsComponent.meleeAttackBox;
//    }

    public boolean getBoolGunActive() {
        return component.boolGunActive;
    }

    public boolean getBoolPissPiss() {
        return component.boolPissPiss;
    }

    public boolean getActivateAnimMechan(){
        return component.activateAnimMechan;
    }

    public void setBoolPissPiss(boolean boolPissPiss){
        component.boolPissPiss=boolPissPiss;
    }

    public Entity.Direction getCurrentDirection() {
        return component.currentDirection;
    }

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

    public static Entity initNPC(EntityConfig entityConfig){
        Json json = new Json();
        Entity entity = EntityFactory.getEntity(EntityFactory.EntityType.NPC);
        entity.setEntityConfig(entityConfig);

        entity.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(new Vector2(0,0)));
        entity.sendMessage(Message.MESSAGE.INIT_STATE, json.toJson(entity.getEntityConfig().getState()));
        entity.sendMessage(Message.MESSAGE.INIT_DIRECTION, json.toJson(entity.getEntityConfig().getDirection()));

        return entity;
    }

    public static Entity initEnemy(EntityConfig entityConfig){
        Json json = new Json();
        Entity entity = EntityFactory.getEntity(EntityFactory.EntityType.ENEMY);
        entity.setEntityConfig(entityConfig);

//        entity.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(new Vector2(0,0)));
        entity.sendMessage(Message.MESSAGE.INIT_STATE, json.toJson(entity.getEntityConfig().getState()));
        entity.sendMessage(Message.MESSAGE.INIT_DIRECTION, json.toJson(entity.getEntityConfig().getDirection()));

        return entity;
    }



    public void dispose(){
        for(Message message : messages){
            message.dispose();
        }
    }

}
