package com.mygdx.game.world.maps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.component.Message;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.world.Map;
import com.mygdx.game.world.MapFactory;


public class Leprozia extends Map {

    private static String mapPath = "textures/maps/leprozia/Leprozia.tmx";
    private Json json;

    public Leprozia() {
        super(MapFactory.MapType.LEPROZIA, mapPath);

        json = new Json();

    }

    private void initSpecialEntityPosition(Entity entity, Vector2 position){

        entity.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(position));
        entity.sendMessage(Message.MESSAGE.INIT_CONFIG, json.toJson(entity.getEntityConfig()));

    }

}