package com.mygdx.game.entity;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.component.Component;
import com.mygdx.game.component.Enemy;
import com.mygdx.game.component.Exoskeleton;
import com.mygdx.game.component.MapObject;
import com.mygdx.game.component.NPC;
import com.mygdx.game.component.Player;
import com.mygdx.game.tools.managers.ResourceManager;

import java.util.Hashtable;

public class EntityFactory {

    public static enum EntityType{
        PLAYER,
        ENEMY,
        NPC,
        MAPOBJECT,
        TESTPLAYER,
        EXOSKELETON
    }

    public static enum EntityName{
        TOWN_FOL, TOWN_FOLK1, TOWN_FOLK2, TOWN_FOLK3, TOWN_FOLK4, TOWN_FOLK5, TOWN_FOLK7, TOWN_FOLK8, TOWN_FOLK9, TOWN_FOLK10, TOWN_FOLK11, TOWN_FOLK12, TOWN_FOLK13, TOWN_FOLK14, TOWN_FOLK15,
        POLICE_B1, POLICE_R1,
        MERCENARIES_M1,
        EARTHLINGS_D1, EARTHLINGS_D2, EARTHLINGS_G1, EARTHLINGS_E1, BARTENDER, HOLOGRAM_G2,
        ELITE_KNIGHT,
        MERCHANT,
        EXOSKELETON_M1
    }

    private static Json json = new Json();
    private static EntityFactory instance = null;
    private Hashtable<String, EntityConfig> entities;

    private EntityFactory() {
        entities = new Hashtable<String, EntityConfig>();

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

        Array<EntityConfig> exoskeletonConfig = Entity.getEntityConfigs(ResourceManager.EXOSKELETON_CONFIGS);
        for(EntityConfig config: exoskeletonConfig){
            entities.put(config.getEntityID(), config);
        }
    }

    public static EntityFactory getInstance() {
        if (instance == null) {
            instance = new EntityFactory();
        }

        return instance;
    }

    public static Entity getEntity(EntityType entityType){
        Entity entity = null;
        switch(entityType){
            case PLAYER:
                entity = new Entity(new Player());
                entity.setEntityConfig(Entity.getEntityConfig(ResourceManager.PLAYER_CONFIG));
                entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, json.toJson(entity.getEntityConfig()));
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
            default:
                return null;
        }
    }

    public static Entity getEntity(EntityType entityType, TextureMapObject textureMapObject){
        Entity entity = null;
        switch(entityType){
            case MAPOBJECT:
                entity = new Entity(new MapObject(textureMapObject.getTextureRegion(),
                        textureMapObject.getX(),
                        textureMapObject.getY(),
                        textureMapObject.getTextureRegion().getRegionWidth(),
                        textureMapObject.getTextureRegion().getRegionHeight()));
                return entity;
            default:
                return null;
        }
    }

    public Entity getNPCByName(EntityName entityName){
        EntityConfig config = new EntityConfig(entities.get(entityName.toString()));
        Entity entity = Entity.initNPC(config);
        return entity;
    }

    public Entity getEnemyByName(EntityName entityName){
        EntityConfig config = new EntityConfig(entities.get(entityName.toString()));
        Entity entity = Entity.initEnemy(config);
        return entity;
    }

    public Entity getNPCByNameForQuest(EntityName entityName, Vector2 position, Entity.Direction direction, String conversationConfigPath){
        EntityConfig config = new EntityConfig(entities.get(entityName.toString()));
        config.setConversationConfigPath(conversationConfigPath);
        Entity entity = Entity.initNPCForQuest(config, position, direction);
        return entity;
    }

    public Entity getEnemyByNameForQuest(EntityName entityName){
        EntityConfig config = new EntityConfig(entities.get(entityName.toString()));
        Entity entity = Entity.initEnemyForQuest(config);
        return entity;
    }

    public Entity getExoskeletonByName(EntityName entityName){
        EntityConfig config = new EntityConfig(entities.get(entityName.toString()));
        return Entity.initExoskeleton(config);
    }

}
