package com.mygdx.game.rudiment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.item.Item;
import com.mygdx.game.tools.managers.ResourceManager;
import com.mygdx.game.weapon.Weapon;
import com.mygdx.game.weapon.WeaponFactory;

import java.util.ArrayList;
import java.util.Hashtable;

public class RudimentFactory {
    private static RudimentFactory instance = null;

    private Json json = new Json();
    private Hashtable<Item.ItemID, Rudiment> rudimentList;

    private RudimentFactory(){
        ArrayList<JsonValue> list = json.fromJson(ArrayList.class, Gdx.files.internal(ResourceManager.PATH_TO_JSON_RUDIMENTS));
        rudimentList = new Hashtable<Item.ItemID, Rudiment>();

        for (JsonValue jsonVal : list) {
            Rudiment rudiment = json.readValue(Rudiment.class, jsonVal);
            rudimentList.put(rudiment.getRudimentID(), rudiment);
        }
    }

    public static RudimentFactory getInstance() {
        if (instance == null) {
            instance = new RudimentFactory();
        }
        return instance;
    }

    public Rudiment getRudiment(Item.ItemID inventoryItemType){
        return new Rudiment(rudimentList.get(inventoryItemType));
    }
}
