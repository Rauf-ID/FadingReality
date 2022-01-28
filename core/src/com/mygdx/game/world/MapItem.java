package com.mygdx.game.world;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.item.Item;

public class MapItem {

    private Item.ItemID itemID;
    private MapFactory.MapType mapType;
    private Vector2 position;

    private MapItem() {

    }

    public MapFactory.MapType getMapType() {
        return mapType;
    }

    public void setMapType(MapFactory.MapType mapType) {
        this.mapType = mapType;
    }

    public Item.ItemID getItemID() {
        return itemID;
    }

    public void setItemID(Item.ItemID itemID) {
        this.itemID = itemID;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "MapItem{" +
                "itemID=" + itemID +
                ", mapType=" + mapType +
                ", position=" + position +
                '}';
    }
}
