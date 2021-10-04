package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.entity.Entity;

public class Map {
    private static final String TAG = Map.class.getSimpleName();

    public final static float UNIT_SCALE  = 1f;

    public final static String MAP_OBJECTS_LAYER = "MAP_OBJECTS_LAYER";
    protected final static String COLLISION_LAYER = "COLLISION_LAYER";
    protected final static String PORTAL_LAYER = "PORTAL_LAYER";


    public final static String PARALLAX_LAYER = "PARALLAX_LAYER";
    public final static String BACKGROUND_LAYER = "BACKGROUND_LAYER";
    public final static String FRONT_LAYER = "FRONT_LAYER";
    public final static String LIGHT_LAYER = "LIGHT_LAYER";

    protected Json json;

    protected TiledMap currentMap = null;
    protected MapFactory.MapType currentMapType;
    protected Vector2 playerStart;

    protected MapLayer collisionLayer = null;
    protected MapLayer mapObjectsLayer = null;
    protected MapLayer portalLayer = null;

    protected Array<Entity> mapEntities;
    protected Array<Entity> mapQuestEntities;




    public Map(MapFactory.MapType mapType, String fullMapPath) {
        json = new Json();
        currentMapType = mapType;
        playerStart = new Vector2(0,0);
        mapEntities = new Array<Entity>(10);
        mapQuestEntities = new Array<Entity>();

//        collisionLayer = currentMap.getLayers().get(COLLISION_LAYER);
//        if( collisionLayer == null ){
//            Gdx.app.debug(TAG, "No collision layer!");
//        }

        if( fullMapPath == null || fullMapPath.isEmpty() ) {
            Gdx.app.debug(TAG, "Map is invalid");
            return;
        } else {
            currentMap = new TmxMapLoader().load(fullMapPath);
        }

        mapObjectsLayer = currentMap.getLayers().get(MAP_OBJECTS_LAYER);
        if( mapObjectsLayer == null ){
            Gdx.app.debug(TAG, "No Map Objects layer!");
        }

        collisionLayer = currentMap.getLayers().get(COLLISION_LAYER);
        if( collisionLayer == null ){
            Gdx.app.debug(TAG, "No collision layer!");
        }

        portalLayer = currentMap.getLayers().get(PORTAL_LAYER);
        if( portalLayer == null ){
            Gdx.app.debug(TAG, "No portal layer!");
        }



    }

    public TiledMap getCurrentTiledMap() {
        return currentMap;
    }

    public String getNameMap() {
        return currentMapType.toString();
    }

    public MapFactory.MapType getCurrentMapType(){
        return currentMapType;
    }

    public MapLayer getMapObjectsLayer(){
        return mapObjectsLayer;
    }

    public MapLayer getCollisionLayer(){
        return collisionLayer;
    }

    public MapLayer getPortalLayer(){
        return portalLayer;
    }

    public Array<Entity> getMapEntities(){
        return mapEntities;
    }

    public Array<Entity> getMapQuestEntities(){
        return mapQuestEntities;
    }

    public void setPlayerStart(Vector2 playerStart) {
        this.playerStart = playerStart;
    }

    public Vector2 getPlayerStart() {
        return playerStart;
    }

    protected void updateMapEntities(MapManager mapMgr, Batch batch, float delta){
        for( int i=0; i < mapEntities.size; i++){
            mapEntities.get(i).update(mapMgr, batch, delta);
        }
        for( int i=0; i < mapQuestEntities.size; i++){
            mapQuestEntities.get(i).update(mapMgr, batch, delta);
        }
    }

    protected void dispose(){
        for( int i=0; i < mapEntities.size; i++){
            mapEntities.get(i).dispose();
        }
        for( int i=0; i < mapQuestEntities.size; i++){
            mapQuestEntities.get(i).dispose();
        }
    }

}
