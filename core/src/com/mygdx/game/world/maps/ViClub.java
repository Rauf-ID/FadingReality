package com.mygdx.game.world.maps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.component.Message;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.world.Map;
import com.mygdx.game.world.MapFactory;

public class ViClub extends Map {

    private static String mapPath = "maps/earth/viClub/ViClub.tmx";
    private Json json;

    public ViClub() {
        super(MapFactory.MapType.VI_CLUB, mapPath);

        json = new Json();

        Entity earthlingD1 = EntityFactory.getInstance().getNPCByName(EntityFactory.EntityName.EARTHLINGS_D1);
        initSpecialEntityPosition(earthlingD1, new Vector2(1030,360), Entity.Direction.RIGHT);
        mapEntities.add(earthlingD1);

        Entity earthlingD2 = EntityFactory.getInstance().getNPCByName(EntityFactory.EntityName.EARTHLINGS_D2);
        initSpecialEntityPosition(earthlingD2, new Vector2(900,360), Entity.Direction.RIGHT);
        mapEntities.add(earthlingD2);

        Entity earthlingE1 = EntityFactory.getInstance().getNPCByName(EntityFactory.EntityName.EARTHLINGS_E1);
        initSpecialEntityPosition(earthlingE1, new Vector2(860,230), Entity.Direction.RIGHT);
        mapEntities.add(earthlingE1);

        Entity earthlingG1 = EntityFactory.getInstance().getNPCByName(EntityFactory.EntityName.EARTHLINGS_G1);
        initSpecialEntityPosition(earthlingG1, new Vector2(1030,250), Entity.Direction.RIGHT);
        mapEntities.add(earthlingG1);

        Entity hologramG2 = EntityFactory.getInstance().getNPCByName(EntityFactory.EntityName.HOLOGRAM_G2);
        initSpecialEntityPosition(hologramG2, new Vector2(731.5f,410), Entity.Direction.RIGHT);
        mapEntities.add(hologramG2);

        Entity bartender = EntityFactory.getInstance().getNPCByName(EntityFactory.EntityName.BARTENDER);
        initSpecialEntityPosition(bartender, new Vector2(790,510), Entity.Direction.RIGHT);
        mapEntities.add(bartender);

        Entity amelia = EntityFactory.getInstance().getNPCByName(EntityFactory.EntityName.TOWN_FOLK15);
        initSpecialEntityPosition(amelia, new Vector2(876,429), Entity.Direction.RIGHT);
        mapEntities.add(amelia);

    }

    private void initSpecialEntityPosition(Entity entity, Vector2 position, Entity.Direction direction){

        entity.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(position));
        entity.sendMessage(Message.MESSAGE.CURRENT_DIRECTION, json.toJson(direction));
        entity.sendMessage(Message.MESSAGE.INIT_CONFIG, json.toJson(entity.getEntityConfig()));

    }

}