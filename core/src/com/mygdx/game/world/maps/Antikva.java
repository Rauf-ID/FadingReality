package com.mygdx.game.world.maps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.component.Message;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.world.Map;
import com.mygdx.game.world.MapFactory;

public class Antikva extends Map {

    private static String mapPath = "maps/antikva/Antikva.tmx";
    private Json json;

    public Antikva() {
        super(MapFactory.MapType.ANTIKVA, mapPath);

        json = new Json();


//        Entity townfolk1 = EntityFactory.getInstance().getNPCByName(EntityFactory.EntityName.TOWN_FOLK1);
//        initSpecialEntityPosition(townfolk1, new Vector2(-65,150));
//        mapEntities.add(townfolk1);
//
//        Entity townfolk2 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.TOWN_FOLK1);
//        initSpecialEntityPosition(townfolk2, new Vector2(50,150));
//        mapEntities.add(townfolk2);


        Entity eliteKnight1 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.ELITE_KNIGHT);
        initSpecialEntityPosition(eliteKnight1, new Vector2(1530,400), Entity.Direction.RIGHT);
        mapEntities.add(eliteKnight1);

        Entity eliteKnight2 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.ELITE_KNIGHT);
        initSpecialEntityPosition(eliteKnight2, new Vector2(1480,300), Entity.Direction.RIGHT);
        mapEntities.add(eliteKnight2);

        Entity eliteKnight3 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.ELITE_KNIGHT);
        initSpecialEntityPosition(eliteKnight3, new Vector2(1430,200), Entity.Direction.RIGHT);
        mapEntities.add(eliteKnight3);

    }

    private void initSpecialEntityPosition(Entity entity, Vector2 position, Entity.Direction direction){

        entity.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(position));
        entity.sendMessage(Message.MESSAGE.CURRENT_DIRECTION, json.toJson(direction));
        entity.sendMessage(Message.MESSAGE.INIT_CONFIG, json.toJson(entity.getEntityConfig()));

    }




}
