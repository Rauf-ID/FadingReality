package com.mygdx.game.rudiment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.item.Item;
import com.mygdx.game.managers.ResourceManager;

import java.util.ArrayList;
import java.util.Hashtable;

public class RudimentFactory {
    private static RudimentFactory instance = null;

    private Json json = new Json();
    private Hashtable<Item.ItemID, Rudiment> rudimentTable;
    private Hashtable<Item.ItemID, ActiveRudiment> activeRudimentTable;

    private RudimentFactory(){
        ArrayList<JsonValue> rudimentsList = json.fromJson(ArrayList.class, Gdx.files.internal(ResourceManager.PATH_TO_JSON_RUDIMENTS));
        ArrayList<JsonValue> activeRudimentsList = json.fromJson(ArrayList.class, Gdx.files.internal("items/activeRudiments.json"));
        rudimentTable = new Hashtable<Item.ItemID, Rudiment>();
        activeRudimentTable = new Hashtable<Item.ItemID, ActiveRudiment>();

        for (JsonValue jsonVal : rudimentsList) {
            Rudiment rudiment = json.readValue(Rudiment.class, jsonVal);
            rudimentTable.put(rudiment.getRudimentID(), rudiment);
        }
        for (JsonValue jsonVal : activeRudimentsList){
            ActiveRudiment activeRudiment = json.readValue(ActiveRudiment.class, jsonVal);
            activeRudimentTable.put(activeRudiment.getRudimentID(), activeRudiment);
        }
    }

    public static RudimentFactory getInstance() {
        if (instance == null) {
            instance = new RudimentFactory();
        }
        return instance;
    }

    public Rudiment getRudiment(Item.ItemID inventoryItemType){
        return new Rudiment(rudimentTable.get(inventoryItemType));
    }

    public ActiveRudiment getActiveRudiment(Item.ItemID inventoryItemType){
        return new ActiveRudiment(activeRudimentTable.get(inventoryItemType));
    }
}
