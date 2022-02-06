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
    private Hashtable<Item.ItemID, PassiveRudiment> rudimentTable;
    private Hashtable<Item.ItemID, UniqueRudiment> uniqueRudimentTable;

    private RudimentFactory(){
        ArrayList<JsonValue> rudimentsList = json.fromJson(ArrayList.class, Gdx.files.internal(ResourceManager.PATH_TO_JSON_RUDIMENTS));
        ArrayList<JsonValue> uniqueRudimentsList = json.fromJson(ArrayList.class, Gdx.files.internal("main/items/uniqueRudiments.json"));
        rudimentTable = new Hashtable<>();
        uniqueRudimentTable = new Hashtable<>();

        for (JsonValue jsonVal : rudimentsList) {
            PassiveRudiment rudiment = json.readValue(PassiveRudiment.class, jsonVal);
            rudimentTable.put(rudiment.getRudimentID(), rudiment);
        }

        for (JsonValue jsonVal : uniqueRudimentsList ){
            UniqueRudiment uniqueRudiment = json.readValue(UniqueRudiment.class, jsonVal);
            uniqueRudimentTable.put(uniqueRudiment.getRudimentID(),uniqueRudiment );
        }
    }

    public static RudimentFactory getInstance() {
        if (instance == null) {
            instance = new RudimentFactory();
        }
        return instance;
    }

    public PassiveRudiment getRudiment(Item.ItemID inventoryItemType){
        return new PassiveRudiment(rudimentTable.get(inventoryItemType));
    }

    public UniqueRudiment getUniqueRudiment(Item.ItemID inventoryItemType){
        return new UniqueRudiment(uniqueRudimentTable.get(inventoryItemType));
    }


}
