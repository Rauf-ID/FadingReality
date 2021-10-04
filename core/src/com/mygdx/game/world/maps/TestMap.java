package com.mygdx.game.world.maps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.component.Message;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.world.Map;
import com.mygdx.game.world.MapFactory;

public class TestMap extends Map {

    private static String mapPath = "maps/earth.tmx";
    private Json json;

    public TestMap() {
        super(MapFactory.MapType.TESP_MAP, mapPath);

        json = new Json();

//        Entity townfolk1 = EntityFactory.getInstance().getNPCByName(EntityFactory.EntityName.TOWN_FOLK1);
//        initSpecialEntityPosition(townfolk1, new Vector2(-65,150));
//        mapEntities.add(townfolk1);
//
//        Entity townfolk2 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.TOWN_FOLK1);
//        initSpecialEntityPosition(townfolk2, new Vector2(50,150));
//        mapEntities.add(townfolk2);
    }

    private void initSpecialEntityPosition(Entity entity, Vector2 position){

        entity.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(position));
        entity.sendMessage(Message.MESSAGE.INIT_CONFIG, json.toJson(entity.getEntityConfig()));

    }


}
