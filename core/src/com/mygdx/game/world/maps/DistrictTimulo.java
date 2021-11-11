package com.mygdx.game.world.maps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.component.Message;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.world.Map;
import com.mygdx.game.world.MapFactory;

public class DistrictTimulo extends Map {

    private static String mapPath = "maps/earth/districtTimulo/TimuloTest.tmx";
    private Json json;

    public DistrictTimulo() {
        super(MapFactory.MapType.TIMULO, mapPath);

        json = new Json();

        Entity policeB1 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.POLICE_B1);
        initSpecialEntityPosition(policeB1, new Vector2(925,920), Entity.Direction.RIGHT);
        mapEntities.add(policeB1);

//        Entity policeB1_2 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.POLICE_B1);
//        initSpecialEntityPosition(policeB1_2, new Vector2(725,950), Entity.Direction.RIGHT);
//        mapEntities.add(policeB1_2);


//        Entity townfolk2 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.TOWN_FOLK2);
//        initSpecialEntityPosition(townfolk2, new Vector2(940,1100), Entity.Direction.LEFT);
//        mapEntities.add(townfolk2);
//
//        Entity townfolk3 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.TOWN_FOLK2);
//        initSpecialEntityPosition(townfolk3, new Vector2(720,1070), Entity.Direction.RIGHT);
//        mapEntities.add(townfolk3);

    }

    private void initSpecialEntityPosition(Entity entity, Vector2 position, Entity.Direction direction){

        entity.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(position));
        entity.sendMessage(Message.MESSAGE.CURRENT_DIRECTION, json.toJson(direction));
        entity.sendMessage(Message.MESSAGE.INIT_CONFIG, json.toJson(entity.getEntityConfig()));

    }

}