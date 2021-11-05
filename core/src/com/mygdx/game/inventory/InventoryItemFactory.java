package com.mygdx.game.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.inventory.InventoryItem.ItemID;
import com.mygdx.game.tools.Utility;

import java.util.ArrayList;
import java.util.Hashtable;


public class InventoryItemFactory {

    private static final String INVENTORY_ITEM = "items/json/items.json";
    private static InventoryItemFactory instance = null;

    private Json json = new Json();
    private Hashtable<ItemID, InventoryItem> inventoryItemList;

    private InventoryItemFactory(){
        ArrayList<JsonValue> list = json.fromJson(ArrayList.class, Gdx.files.internal(INVENTORY_ITEM));
        inventoryItemList = new Hashtable<ItemID, InventoryItem>();

        for (JsonValue jsonVal : list) {
            InventoryItem inventoryItem = json.readValue(InventoryItem.class, jsonVal);
            inventoryItemList.put(inventoryItem.getItemID(), inventoryItem);
        }
    }

    public static InventoryItemFactory getInstance() {
        if (instance == null) {
            instance = new InventoryItemFactory();
        }
        return instance;
    }

    public InventoryItem getInventoryItem(ItemID inventoryItemType){
        InventoryItem item = new InventoryItem(inventoryItemList.get(inventoryItemType));
        item.setDrawable(new TextureRegionDrawable(Utility.ITEMS_TEXTUREATLAS.findRegion(item.getItemID().toString())));
        item.setScaling(Scaling.none);
        return item;
    }

}