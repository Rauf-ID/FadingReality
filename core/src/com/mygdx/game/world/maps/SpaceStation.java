package com.mygdx.game.world.maps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.component.Message;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.item.Item;
import com.mygdx.game.world.Map;
import com.mygdx.game.world.MapFactory;

public class SpaceStation extends Map {

    private static String mapPath = "textures/maps/spaceStation/SpaceStation.tmx";
    private Json json;

    public SpaceStation() {
        super(MapFactory.MapType.SPACE_STATION, mapPath);

        json = new Json();

        Entity mercenariesM1 = EntityFactory.getInstance().getNPCByName(EntityFactory.EntityName.MERCENARIES_M1);
        initEntity(mercenariesM1, new Vector2(890,0), Entity.Direction.RIGHT);
        mapEntities.add(mercenariesM1);

        Entity mercenariesM1_2 = EntityFactory.getInstance().getNPCByName(EntityFactory.EntityName.MERCENARIES_M1);
        initEntity(mercenariesM1_2, new Vector2(890,-125), Entity.Direction.RIGHT);
        mapEntities.add(mercenariesM1_2);

        Entity potions01 = EntityFactory.getInstance().getItem(Item.ItemID.POTIONS01);
        initEntity(potions01, new Vector2(1100,60), Entity.Direction.RIGHT);
        mapEntities.add(potions01);

        Entity potions01_2 = EntityFactory.getInstance().getItem(Item.ItemID.POTIONS01);
        initEntity(potions01_2, new Vector2(1100,-60), Entity.Direction.RIGHT);
        mapEntities.add(potions01_2);

    }

    private void initEntity(Entity entity, Vector2 position, Entity.Direction direction){

        entity.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(position));
        entity.sendMessage(Message.MESSAGE.CURRENT_DIRECTION, json.toJson(direction));
        entity.sendMessage(Message.MESSAGE.INIT_CONFIG, json.toJson(entity.getEntityConfig()));

    }

}