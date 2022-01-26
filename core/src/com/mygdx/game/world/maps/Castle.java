package com.mygdx.game.world.maps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.component.Message;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.world.Map;
import com.mygdx.game.world.MapFactory;

public class Castle extends Map {

    private static String mapPath = "textures/maps/antikva/castle/Castle.tmx";
    private Json json;

    public Castle() {
        super(MapFactory.MapType.CASTLE, mapPath);

        json = new Json();

//        Entity townfolk1 = EntityFactory.getInstance().getNPCByName(EntityFactory.EntityName.TOWN_FOLK5);
//        initSpecialEntityPosition(townfolk1, new Vector2(606,876), Entity.Direction.RIGHT);
//        mapEntities.add(townfolk1);
//
//        Entity eliteKnight1 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.ELITE_KNIGHT);
//        initSpecialEntityPosition(eliteKnight1, new Vector2(500,835), Entity.Direction.RIGHT);
//        mapEntities.add(eliteKnight1);
//
//        Entity eliteKnight2 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.ELITE_KNIGHT);
//        initSpecialEntityPosition(eliteKnight2, new Vector2(718,835), Entity.Direction.LEFT);
//        mapEntities.add(eliteKnight2);


    }

    private void initSpecialEntityPosition(Entity entity, Vector2 position, Entity.Direction direction){

        entity.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(position));
        entity.sendMessage(Message.MESSAGE.CURRENT_DIRECTION, json.toJson(direction));
        entity.sendMessage(Message.MESSAGE.INIT_CONFIG, json.toJson(entity.getEntityConfig()));

    }


}