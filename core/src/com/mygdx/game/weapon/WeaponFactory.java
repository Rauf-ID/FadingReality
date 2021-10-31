package com.mygdx.game.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.inventory.InventoryItem.ItemTypeID;

import java.util.ArrayList;
import java.util.Hashtable;

public class WeaponFactory {

    private static final String WEAPONS = "items/json/weapons.json";
    private static WeaponFactory instance = null;

    private Json json = new Json();
    private Hashtable<ItemTypeID, Weapon> weaponList;

    private WeaponFactory(){
        ArrayList<JsonValue> list = json.fromJson(ArrayList.class, Gdx.files.internal(WEAPONS));
        weaponList = new Hashtable<ItemTypeID, Weapon>();

        for (JsonValue jsonVal : list) {
            Weapon weapon = json.readValue(Weapon.class, jsonVal);
            weaponList.put(weapon.getItemTypeID(), weapon);
        }
    }

    public static WeaponFactory getInstance() {
        if (instance == null) {
            instance = new WeaponFactory();
        }
        return instance;
    }

    public Weapon getInventoryItem(ItemTypeID inventoryItemType){
        Weapon weapon = new Weapon(weaponList.get(inventoryItemType));
        return weapon;
    }


}
