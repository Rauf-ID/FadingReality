package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.pathfinder.Node;

public class Map {

    private static final String TAG = Map.class.getSimpleName();

    public final static float UNIT_SCALE  = 1f;
    public final static int CELL_SIZE  = 16;

    public final static String LIGHT_LAYER = "LIGHT_LAYER";
    public final static String FRONT_LAYER = "FRONT_LAYER";
    public final static String BACKGROUND_LAYER = "BACKGROUND_LAYER";
    public final static String PARALLAX_LAYER = "PARALLAX_LAYER";

    private final static String PORTAL_LAYER = "PORTAL_LAYER";
    private final static String ENTITY_SPAWNS_LAYER = "ENTITY_SPAWNS_LAYER";
    private final static String MAP_OBJECTS_LAYER = "MAP_OBJECTS_LAYER";
    private final static String COLLISION_LAYER = "COLLISION_LAYER";
    private final static String CLOSED_NODES_LAYER = "CLOSED_NODES_LAYER";

    protected Json json;

    private int mapWidth;
    private int mapHeight;
    private Array<Array<Node>> grid;

    private TiledMap currentMap = null;
    private MapFactory.MapType currentMapType;
    private Vector2 playerStart;

    private MapLayer portalLayer = null;
    private MapLayer entitySpawnsLayer = null;
    private MapLayer mapObjectsLayer = null;
    private MapLayer collisionLayer = null;
    private MapLayer closedNodesLayer = null;

    private MapProperties mapProperties = null;

    private Array<Entity> mapEntities;
    private Array<Entity> mapQuestEntities;

    public Map(MapManager mapManager, MapFactory.MapType mapType) {
        json = new Json();
        currentMapType = mapType;
        playerStart = new Vector2(0,0);
        mapEntities = new Array<>(10);
        mapQuestEntities = new Array<>();
        grid = new Array<>();

        if( mapType.getPath() == null || mapType.getPath().isEmpty() ) {
            Gdx.app.debug(TAG, "Map is invalid");
            return;
        } else {
            currentMap = new TmxMapLoader().load(mapType.getPath());
        }

        portalLayer = currentMap.getLayers().get(PORTAL_LAYER);
        if( portalLayer == null ){
            Gdx.app.debug(TAG, "No portal layer!");
        }

        entitySpawnsLayer = currentMap.getLayers().get(ENTITY_SPAWNS_LAYER);
        if( entitySpawnsLayer == null ){
            Gdx.app.debug(TAG, "No entity spawn layer!");
        }

        mapObjectsLayer = currentMap.getLayers().get(MAP_OBJECTS_LAYER);
        if( mapObjectsLayer == null ){
            Gdx.app.debug(TAG, "No Map Objects layer!");
        }

        collisionLayer = currentMap.getLayers().get(COLLISION_LAYER);
        if(collisionLayer == null){
            Gdx.app.debug(TAG, "No collision layer!");
        }

        closedNodesLayer = currentMap.getLayers().get(CLOSED_NODES_LAYER);
        if(closedNodesLayer == null){
            Gdx.app.debug(TAG, "No collision layer!");
        }

        mapProperties = currentMap.getProperties();
        mapWidth = mapProperties.get("tilewidth", Integer.class);
        mapHeight = mapProperties.get("tileheight", Integer.class);

        addEntitiesToMap(mapManager.getIdEntityForDelete().get(mapType.toString()));

        createGrid();
    }

    private void createGrid() {
        for (int y = 0; y < (mapWidth / CELL_SIZE); y++) {
            grid.add(new Array<Node>());
            for (int x = 0; x < (mapHeight / CELL_SIZE); x++) {
                Rectangle rectangle = new Rectangle(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                grid.get(y).add(new Node(rectangle));
            }
        }

        updateGridOjc();
    }

    public void updateGridOjc() {
        if( closedNodesLayer == null ){
            return;
        }

        Rectangle rectangle = null;
        for (int y = 0; y < grid.size; y++) {
            for (int x = 0; x < grid.get(y).size; x++) {
                for(MapObject object: closedNodesLayer.getObjects()){
                    if(object instanceof RectangleMapObject) {
                        rectangle = ((RectangleMapObject)object).getRectangle();
                        if(grid.get(y).get(x).rectangle.overlaps(rectangle)){
                            grid.get(y).get(x).setType(Node.GridType.CLOSE);
                        }
                    }
                }
            }
        }
    }

    public void updateMapEntities(MapManager mapMgr, Batch batch, float delta){
        for( int i=0; i < mapEntities.size; i++){
            mapEntities.get(i).update(mapMgr, batch, delta);
        }
        for( int i=0; i < mapQuestEntities.size; i++){
            mapQuestEntities.get(i).update(mapMgr, batch, delta);
        }
    }

    private void addEntitiesToMap(Array<Integer> ids) {
        if(entitySpawnsLayer == null){
            return;
        }

        for(MapObject object: entitySpawnsLayer.getObjects()){
            if (object == null) {
                return;
            }

            int id = (int) object.getProperties().get("id");
            if (ids != null && ids.contains(id, true)) {
                return;
            }

            String entityType = (String) object.getProperties().get("entityType");
            String entityName = object.getName();
            Vector2 position = new Vector2(((RectangleMapObject)object).getRectangle().getX(), ((RectangleMapObject)object).getRectangle().getY());
            boolean isItem = (Boolean) object.getProperties().get("isItem");

            Entity entity = EntityFactory.getInstance().getEntityByName(entityType, entityName, position, Entity.State.IDLE, Entity.Direction.RIGHT, id, isItem,false, "");
            mapEntities.add(entity);
        }
    }



    public Array<Array<Node>> getGrid() {
        return grid;
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

    public MapLayer getClosedNodesLayer(){
        return closedNodesLayer;
    }

    public MapLayer getCollisionLayer(){
        return collisionLayer;
    }

    public MapLayer getPortalLayer(){
        return portalLayer;
    }

    public MapLayer getEntitySpawnsLayer() {
        return entitySpawnsLayer;
    }

    public Array<Entity> getMapEntities(){
        return mapEntities;
    }

    public Array<Entity> getMapQuestEntities(){
        return mapQuestEntities;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public void setPlayerStart(Vector2 playerStart) {
        this.playerStart = playerStart;
    }

    public Vector2 getPlayerStart() {
        return playerStart;
    }

    public MapProperties getMapProperties() {
        return mapProperties;
    }

    public void setMapProperties(MapProperties mapProperties) {
        this.mapProperties = mapProperties;
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
