package com.mygdx.game.entity;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.component.Enemy;
import com.mygdx.game.component.MapObject;
import com.mygdx.game.component.NPC;
import com.mygdx.game.component.Player;

import java.util.Hashtable;

public class EntityFactory {

    public static enum EntityType{
        PLAYER,
        ENEMY,
        NPC,
        MAPOBJECT,
        TESTPLAYER
    }

    public static enum EntityName{
        PLAYER_PUPPET,
        TOWN_FOL, TOWN_FOLK1, TOWN_FOLK2, TOWN_FOLK3, TOWN_FOLK4, TOWN_FOLK5, TOWN_FOLK7, TOWN_FOLK8, TOWN_FOLK9, TOWN_FOLK10,
        TOWN_FOLK11, TOWN_FOLK12, TOWN_FOLK13, TOWN_FOLK14, TOWN_FOLK15,
        EARTHLINGS_D1, EARTHLINGS_D2, EARTHLINGS_G1, EARTHLINGS_E1, BARTENDER, HOLOGRAM_G2,
        ELITE_KNIGHT,
    }

    private static Json json = new Json();
    private static EntityFactory instance = null;
    private Hashtable<String, EntityConfig> entities;

    public static String PLAYER_CONFIG = "scripts/player.json";
    public static String TOWN_FOLK_CONFIGS = "scripts/town_folk.json";
    public static String EARTHLINGS_CONFIGS = "scripts/earthlings.json";
    public static String ELITE_KNIGHT_CONFIGS = "scripts/eliteKnight.json";

    private EntityFactory() {
        entities = new Hashtable<String, EntityConfig>();

        Array<EntityConfig> townFolkConfigs = Entity.getEntityConfigs(TOWN_FOLK_CONFIGS);
        for( EntityConfig config: townFolkConfigs){
            entities.put(config.getEntityID(), config);
        }

        Array<EntityConfig> earthlingsConfigs = Entity.getEntityConfigs(EARTHLINGS_CONFIGS);
        for( EntityConfig config: earthlingsConfigs){
            entities.put(config.getEntityID(), config);
        }

        Array<EntityConfig> eliteKnightConfigs = Entity.getEntityConfigs(ELITE_KNIGHT_CONFIGS);
        for( EntityConfig config: eliteKnightConfigs){
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
                entity.setEntityConfig(Entity.getEntityConfig(EntityFactory.PLAYER_CONFIG));
                return entity;
            case NPC:
                entity = new Entity(new NPC());
                return entity;
            case ENEMY:
                entity = new Entity(new Enemy());
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

}
