package com.mygdx.game.world.maps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.component.Message;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.world.Map;
import com.mygdx.game.world.MapFactory;

public class SpaceStation extends Map {

    private static String mapPath = "maps/spaceStation/SpaceStation.tmx";
    private Json json;

    public SpaceStation() {
        super(MapFactory.MapType.SPACE_STATION, mapPath);

        json = new Json();

        Entity townfolk1 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.TOWN_FOLK3);
        initSpecialEntityPosition(townfolk1, new Vector2(890,280), Entity.Direction.RIGHT);
        mapEntities.add(townfolk1);

        Entity townfolk2 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.TOWN_FOLK4);
        initSpecialEntityPosition(townfolk2, new Vector2(790,320), Entity.Direction.RIGHT);
        mapEntities.add(townfolk2);

        Entity townfolk3 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.TOWN_FOLK7);
        initSpecialEntityPosition(townfolk3, new Vector2(820,335), Entity.Direction.RIGHT);
        mapEntities.add(townfolk3);


    }

    private void initSpecialEntityPosition(Entity entity, Vector2 position, Entity.Direction direction){

        entity.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(position));
        entity.sendMessage(Message.MESSAGE.CURRENT_DIRECTION, json.toJson(direction));
        entity.sendMessage(Message.MESSAGE.INIT_CONFIG, json.toJson(entity.getEntityConfig()));

    }

}