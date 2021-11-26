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

        Entity exoskeletonM1 = EntityFactory.getInstance().getExoskeletonByName(EntityFactory.EntityName.EXOSKELETON_M1);
        initEntity(exoskeletonM1, new Vector2(850,950), Entity.Direction.RIGHT);
        mapEntities.add(exoskeletonM1);

        Entity exoskeletonM2 = EntityFactory.getInstance().getExoskeletonByName(EntityFactory.EntityName.EXOSKELETON_M2);
        initEntity(exoskeletonM2, new Vector2(850,850), Entity.Direction.RIGHT);
        mapEntities.add(exoskeletonM2);

//        Entity policeB1 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.POLICE_B1);
//        initEntity(policeB1, new Vector2(850,800), Entity.Direction.RIGHT);
//        mapEntities.add(policeB1);

//        Entity policeB1_2 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.POLICE_B1);
//        initEntity(policeB1_2, new Vector2(725,950), Entity.Direction.RIGHT);
//        mapEntities.add(policeB1_2);


//        Entity townfolk2 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.TOWN_FOLK2);
//        initEntity(townfolk2, new Vector2(940,1100), Entity.Direction.LEFT);
//        mapEntities.add(townfolk2);
//
//        Entity townfolk3 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.TOWN_FOLK2);
//        initEntity(townfolk3, new Vector2(720,1070), Entity.Direction.RIGHT);
//        mapEntities.add(townfolk3);

    }

    private void initEntity(Entity entity, Vector2 position, Entity.Direction direction){

        entity.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(position));
        entity.sendMessage(Message.MESSAGE.CURRENT_DIRECTION, json.toJson(direction));
        entity.sendMessage(Message.MESSAGE.INIT_CONFIG, json.toJson(entity.getEntityConfig()));

    }

}