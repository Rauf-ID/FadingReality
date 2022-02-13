package com.mygdx.game.world;

import com.badlogic.gdx.utils.Array;

public class MapEntity {

    private MapFactory.MapType mapType;
    private Array<Integer> IDs;

    private MapEntity() {
        IDs = new Array<>();
    }

    public MapFactory.MapType getMapType() {
        return mapType;
    }

    public void setMapType(MapFactory.MapType mapType) {
        this.mapType = mapType;
    }
}
