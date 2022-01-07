package com.mygdx.game.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.inventory.Item;
import com.mygdx.game.inventory.Item.ItemID;
import com.mygdx.game.tools.managers.ResourceManager;

import java.util.ArrayList;
import java.util.Hashtable;

public class WeaponFactory {

    private static WeaponFactory instance = null;

    private Json json = new Json();
    private Hashtable<ItemID, Weapon> weaponList;

    private WeaponFactory(){
        ArrayList<JsonValue> list = json.fromJson(ArrayList.class, Gdx.files.internal(ResourceManager.PATH_TO_JSON_WEAPONS));
        weaponList = new Hashtable<ItemID, Weapon>();

        for (JsonValue jsonVal : list) {
            Weapon weapon = json.readValue(Weapon.class, jsonVal);
            weaponList.put(weapon.getWeaponID(), weapon);
        }
    }

    public static WeaponFactory getInstance() {
        if (instance == null) {
            instance = new WeaponFactory();
        }
        return instance;
    }

    public Weapon getWeapon(Item.ItemID inventoryItemType){
        return new Weapon(weaponList.get(inventoryItemType));
    }


}
