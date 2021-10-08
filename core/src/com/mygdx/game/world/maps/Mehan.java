package com.mygdx.game.world.maps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.component.Message;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.world.Map;
import com.mygdx.game.world.MapFactory;

public class Mehan extends Map {

    private static String mapPath = "maps/mehan/Mehan.tmx";
    private Json json;

    public Mehan() {
        super(MapFactory.MapType.MEHAN, mapPath);

        json = new Json();

//        Entity townfolk1 = EntityFactory.getInstance().getEnemyByName(EntityFactory.EntityName.TOWN_FOL);
//        initSpecialEntityPosition(townfolk1, new Vector2(641,530), Entity.Direction.DOWN);
//        mapEntities.add(townfolk1);

    }

    private void initSpecialEntityPosition(Entity entity, Vector2 position, Entity.Direction direction){

        entity.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(position));
        entity.sendMessage(Message.MESSAGE.CURRENT_DIRECTION, json.toJson(direction));
        entity.sendMessage(Message.MESSAGE.INIT_CONFIG, json.toJson(entity.getEntityConfig()));

    }

}
