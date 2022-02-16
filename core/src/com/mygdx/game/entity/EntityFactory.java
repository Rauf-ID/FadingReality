package com.mygdx.game.entity;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.component.Component;
import com.mygdx.game.component.Enemy;
import com.mygdx.game.component.Exoskeleton;
import com.mygdx.game.component.MapItem;
import com.mygdx.game.component.MapObject;
import com.mygdx.game.component.Message;
import com.mygdx.game.component.NPC;
import com.mygdx.game.component.Player;
import com.mygdx.game.item.Item;
import com.mygdx.game.managers.ResourceManager;

import java.util.Hashtable;

public class EntityFactory {

    public enum EntityType{
        NPC,
        ENEMY,
        PLAYER,
        MAP_ITEM,
        MAP_OBJECT,
        EXOSKELETON
    }

    public enum EntityName {
        TOWN_FOL, TOWN_FOLK1, TOWN_FOLK2, TOWN_FOLK3, TOWN_FOLK4, TOWN_FOLK5, TOWN_FOLK7, TOWN_FOLK8, TOWN_FOLK9, TOWN_FOLK10, TOWN_FOLK11, TOWN_FOLK12, TOWN_FOLK13, TOWN_FOLK14, TOWN_FOLK15,
        EARTHLINGS_D1, EARTHLINGS_D2, EARTHLINGS_G1, EARTHLINGS_E1, BARTENDER, HOLOGRAM_G2,
        POLICE_B1, POLICE_R1,
        MERCENARIES_M1,
        EXOSKELETON_M1,
        EXOSKELETON_M2,
        ELITE_KNIGHT,
        SCIENTIST,
        MERCHANT,
        OVERSEER,
        MECHANIC,
        ISB_S1,
        NONE
    }

    private static Json json = new Json();
    private static EntityFactory instance = null;
    private Hashtable<String, EntityConfig> entities;

    private EntityFactory() {
        entities = new Hashtable<>();

        Array<EntityConfig> townFolkConfigs = Entity.getEntityConfigs(ResourceManager.TOWN_FOLK_CONFIGS);
        for(EntityConfig config: townFolkConfigs){
            entities.put(config.getEntityID(), config);
        }

        Array<EntityConfig> earthlingsConfigs = Entity.getEntityConfigs(ResourceManager.EARTHLINGS_CONFIGS);
        for(EntityConfig config: earthlingsConfigs){
            entities.put(config.getEntityID(), config);
        }

        Array<EntityConfig> eliteKnightConfigs = Entity.getEntityConfigs(ResourceManager.ELITE_KNIGHT_CONFIGS);
        for(EntityConfig config: eliteKnightConfigs){
            entities.put(config.getEntityID(), config);
        }

        Array<EntityConfig> policeConfig = Entity.getEntityConfigs(ResourceManager.POLICE_CONFIGS);
        for(EntityConfig config: policeConfig){
            entities.put(config.getEntityID(), config);
        }

        Array<EntityConfig> mercenariesConfig = Entity.getEntityConfigs(ResourceManager.MERCENARIES_CONFIGS);
        for(EntityConfig config: mercenariesConfig){
            entities.put(config.getEntityID(), config);
        }

        Array<EntityConfig> merchants = Entity.getEntityConfigs(ResourceManager.MERCHANT_CONFIGS);
        for(EntityConfig config: merchants){
            entities.put(config.getEntityID(), config);
        }

        Array<EntityConfig> overseer = Entity.getEntityConfigs(ResourceManager.OVERSEER_CONFIGS);
        for(EntityConfig config: overseer){
            entities.put(config.getEntityID(), config);
        }

        Array<EntityConfig> mechanic = Entity.getEntityConfigs(ResourceManager.MECHANIC_CONFIGS);
        for(EntityConfig config: mechanic){
            entities.put(config.getEntityID(), config);
        }

        Array<EntityConfig> scientist = Entity.getEntityConfigs(ResourceManager.SCIENTIST_CONFIGS);
        for(EntityConfig config: scientist){
            entities.put(config.getEntityID(), config);
        }

        Array<EntityConfig> exoskeletonConfig = Entity.getEntityConfigs(ResourceManager.EXOSKELETON_CONFIGS);
        for(EntityConfig config: exoskeletonConfig){
            entities.put(config.getEntityID(), config);
        }

        Array<EntityConfig> mapItemsConfig = Entity.getEntityConfigs(ResourceManager.MAP_ITEMS_CONFIG);
        for(EntityConfig config: mapItemsConfig){
            entities.put(config.getEntityID(), config);
        }
    }

    public static EntityFactory getInstance() {
        if (instance == null) {
            instance = new EntityFactory();
        }

        return instance;
    }

    public Entity getEntity(EntityType entityType, TextureMapObject textureMapObject){
        Entity entity;
        switch(entityType){
            case PLAYER:
                entity = new Entity(new Player());
                entity.setEntityConfig(Entity.getEntityConfig(ResourceManager.PLAYER_CONFIG));
                entity.sendMessage(Component.MESSAGE.INIT_ANIMATIONS, json.toJson(entity.getEntityConfig()));
                return entity;
            case NPC:
                entity = new Entity(new NPC());
                return entity;
            case ENEMY:
                entity = new Entity(new Enemy());
                return entity;
            case EXOSKELETON:
                entity = new Entity(new Exoskeleton());
                return entity;
            case MAP_ITEM:
                entity = new Entity(new MapItem());
                return entity;
            case MAP_OBJECT:
                entity = new Entity(new MapObject(textureMapObject));
                return entity;
            default:
                return null;
        }
    }

    public Entity getEntityByName(String entityType, String entityName, Vector2 position, Entity.State state, Entity.Direction direction, int idSpawnMap, boolean isItem, boolean isQuestItem, String conversationConfigPath){
        Entity entity = EntityFactory.getInstance().getEntity(EntityType.valueOf(entityType), null);
        EntityConfig config = new EntityConfig(entities.get(entityName));
        if (entity != null) {
            entity.setEntityConfig(config);
            config.setConversationConfigPath(conversationConfigPath);
            entity.sendMessage(Message.MESSAGE.INIT_ID_MAP_SPAWN, json.toJson(idSpawnMap));
            entity.sendMessage(Message.MESSAGE.INIT_STATE, json.toJson(state));
            entity.sendMessage(Message.MESSAGE.INIT_DIRECTION, json.toJson(direction));
            entity.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(position));
            entity.sendMessage(Message.MESSAGE.INIT_CONFIG, json.toJson(entity.getEntityConfig()));
            entity.sendMessage(Component.MESSAGE.INIT_ANIMATIONS, json.toJson(entity.getEntityConfig()));
            if (isItem) {
                entity.sendMessage(Component.MESSAGE.INIT_ITEM, json.toJson(Item.ItemID.valueOf(entityName)));
                if (isQuestItem) {
                    entity.sendMessage(Component.MESSAGE.ITEM_IS_FOR_QUEST, json.toJson(true));
                }
            }
        }
        return entity;
    }

    public Entity getExoskeletonByName(EntityName entityName){
        Entity entity = EntityFactory.getInstance().getEntity(EntityFactory.EntityType.EXOSKELETON, null);
        EntityConfig config = new EntityConfig(entities.get(entityName.toString()));
        if (entity != null) {
            entity.setEntityConfig(config);
            entity.sendMessage(Component.MESSAGE.INIT_ANIMATIONS, json.toJson(entity.getEntityConfig()));
        }
        return entity;
    }

}
