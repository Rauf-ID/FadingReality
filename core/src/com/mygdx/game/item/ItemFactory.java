package com.mygdx.game.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.item.Item.ItemID;
import com.mygdx.game.tools.Utility;
import com.mygdx.game.managers.ResourceManager;

import java.util.ArrayList;
import java.util.Hashtable;


public class ItemFactory {

    private static ItemFactory instance = null;

    private Json json = new Json();
    private Hashtable<ItemID, Item> inventoryItemList;

    private ItemFactory(){
        ArrayList<JsonValue> list = json.fromJson(ArrayList.class, Gdx.files.internal(ResourceManager.INVENTORY_ITEM));
        inventoryItemList = new Hashtable<ItemID, Item>();

        for (JsonValue jsonVal : list) {
            Item item = json.readValue(Item.class, jsonVal);
            inventoryItemList.put(item.getItemID(), item);
        }
    }

    public static ItemFactory getInstance() {
        if (instance == null) {
            instance = new ItemFactory();
        }
        return instance;
    }

    public Item getInventoryItem(ItemID inventoryItemType){
        Item item = new Item(inventoryItemList.get(inventoryItemType));
        item.setDrawable(new TextureRegionDrawable(Utility.ITEMS_TEXTUREATLAS.findRegion(item.getItemID().toString())));
        item.setScaling(Scaling.none);
        return item;
    }

}