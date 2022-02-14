package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.UI.PlayerHUD;
import com.mygdx.game.component.Message;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.observer.ComponentObserver;
import com.mygdx.game.observer.ProfileObserver;
import com.mygdx.game.profile.ProfileManager;
import com.mygdx.game.tools.Toast;

import java.util.HashMap;

public class MapManager implements ProfileObserver {

    private static final String TAG = MapManager.class.getSimpleName();

    private OrthographicCamera camera;
    private boolean mapChanged = false;
    private boolean deletedCurrentMapEntity = false;
    private Map currentMap;
    private Entity player;
    private Entity currentEntity = null;

    private java.util.Map<String, Array<Integer>> idEntityForDelete = new HashMap<>();

    public MapManager() {
    }

    @Override
    public void onNotify(ProfileManager profileManager, ProfileEvent event) {
        switch(event){
            case PROFILE_LOADED:
                MapFactory.MapType mapType = profileManager.getPlayerConfig().getCurrentMap();
                if(mapType==null) {
                    mapType = MapFactory.MapType.SPACE_STATION;
                }

                java.util.Map<String, Array<Integer>> idEntityForDelete = profileManager.getPlayerConfig().getIdEntityForDelete();
                setIdEntityForDelete(idEntityForDelete);

                loadMap(mapType);
                break;
            case SAVING_PROFILE:
                if( currentMap != null ){
                    profileManager.getPlayerConfig().setCurrentMap(currentMap.getCurrentMapType());
                }
                profileManager.getPlayerConfig().setIdEntityForDelete(getIdEntityForDelete());
                break;
            case CLEAR_CURRENT_PROFILE:
                currentMap = null;
                profileManager.getPlayerConfig().setCurrentMap(MapFactory.MapType.SPACE_STATION);

                MapFactory.clearCache();
                break;
            default:
                break;
        }
    }

    public void loadMap(MapFactory.MapType mapType){
        Map map = MapFactory.getMap(mapType,this);

        if( map == null ){
            Gdx.app.debug(TAG, "Map does not exist!  ");
            return;
        }

        currentMap = map;
        mapChanged = true;
        clearCurrentMapEntity();
    }

    public void registerCurrentMapEntityObservers(ComponentObserver observer){
        if( currentMap != null ){
            Array<Entity> entities = currentMap.getMapEntities();
            for(Entity entity: entities){
                entity.registerObserver(observer);
            }

            Array<Entity> questEntities = currentMap.getMapQuestEntities();
            for(Entity questEntity: questEntities){
                questEntity.registerObserver(observer);
            }
        }
    }

    public void unregisterCurrentMapEntityObservers(){
        if( currentMap != null ){
            Array<Entity> entities = currentMap.getMapEntities();
            for(Entity entity: entities){
                entity.unregisterObservers();
            }

            Array<Entity> questEntities = currentMap.getMapQuestEntities();
            for(Entity questEntity: questEntities){
                questEntity.unregisterObservers();
            }
        }
    }

    public MapLayer getCollisionLayer(){
        return currentMap.getCollisionLayer();
    }

    public MapLayer getMapObjectsLayer(){
        return currentMap.getMapObjectsLayer();
    }

    public TiledMap getCurrentTiledMap(){
        if( currentMap == null ) {
            loadMap(MapFactory.MapType.SPACE_STATION);
        }
        return currentMap.getCurrentTiledMap();
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public MapFactory.MapType getCurrentMapType(){
        return currentMap.getCurrentMapType();
    }

    public void addMapEntities(Entity entities){
        currentMap.getMapEntities().add(entities);
    }

    public void addMapQuestEntities(Array<Entity> entities){
        currentMap.getMapQuestEntities().addAll(entities);
    }

    public void clearAllMapQuestEntities(){
        currentMap.getMapQuestEntities().clear();
    }

    public void updateCurrentMapEntities(MapManager mapMgr, Batch batch, float delta){
        currentMap.updateMapEntities(mapMgr, batch, delta);
    }

    public final Array<Entity> getCurrentMapEntities(){
        return currentMap.getMapEntities();
    }

    public final Array<Entity> getCurrentMapQuestEntities(){
        return currentMap.getMapQuestEntities();
    }

    public void setCamera(OrthographicCamera camera){
        this.camera = camera;
    }

    public OrthographicCamera getCamera(){
        return camera;
    }

    public void setPlayer(Entity entity){
        this.player = entity;
    }

    public Entity getPlayer(){
        return player;
    }

    public Entity getCurrentMapEntity(){
        return currentEntity;
    }

    public void setCurrentMapEntity(Entity currentSelectedEntity) {
        this.currentEntity = currentSelectedEntity;
    }

    public void clearCurrentMapEntity(){
        if( currentEntity == null ) return;
        currentEntity.sendMessage(Message.MESSAGE.ENTITY_DESELECTED);
        currentEntity = null;
    }

    public void deleteCurrentMapEntity() {
        currentMap.getMapEntities().removeValue(currentEntity, true);
    }

    public java.util.Map<String, Array<Integer>> getIdEntityForDelete() {
        return idEntityForDelete;
    }

    public void setIdEntityForDelete(java.util.Map<String, Array<Integer>> idEntityForDelete) {
        this.idEntityForDelete = idEntityForDelete;
    }

    public void addIdEntityForDelete(int id) {
        if (idEntityForDelete.get(currentMap.getCurrentMapType().toString()) == null) {
            Array<Integer> IDs = new Array<>();
            IDs.add(id);
            idEntityForDelete.put(currentMap.getCurrentMapType().toString(), IDs);
        } else {
            Array<Integer> IDs = new Array<>(idEntityForDelete.get(currentMap.getCurrentMapType().toString()));
            IDs.add(id);
            idEntityForDelete.put(currentMap.getCurrentMapType().toString(), IDs);
        }
    }

    public void quickChangeMap() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            PlayerHUD.toastShort("SPACE_STATION", Toast.Length.SHORT);
            this.loadMap(MapFactory.MapType.SPACE_STATION);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            PlayerHUD.toastShort("CASTLE", Toast.Length.SHORT);
            this.loadMap(MapFactory.MapType.CASTLE);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            PlayerHUD.toastShort("MEHAN", Toast.Length.SHORT);
            this.loadMap(MapFactory.MapType.MEHAN);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            PlayerHUD.toastShort("ANTIKVA", Toast.Length.SHORT);
            this.loadMap(MapFactory.MapType.ANTIKVA);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            PlayerHUD.toastShort("LEPROZIA", Toast.Length.SHORT);
            this.loadMap(MapFactory.MapType.LEPROZIA);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
            PlayerHUD.toastShort("TIMULO", Toast.Length.SHORT);
            this.loadMap(MapFactory.MapType.TIMULO);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
            PlayerHUD.toastShort("VI_CLUB", Toast.Length.SHORT);
            this.loadMap(MapFactory.MapType.VI_CLUB);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
            PlayerHUD.toastShort("ROOF_KBM_OFFICE", Toast.Length.SHORT);
            this.loadMap(MapFactory.MapType.ROOF_KBM_OFFICE);
        }
    }

    public boolean hasMapChanged(){
        return mapChanged;
    }

    public void setMapChanged(boolean hasMapChanged){
        this.mapChanged = hasMapChanged;
    }

    public boolean hasDeletedCurrentMapEntity(){
        return deletedCurrentMapEntity;
    }

    public void setCurrentMapEntity(boolean hasDeletedCurrentMapEntity){
        this.deletedCurrentMapEntity = hasDeletedCurrentMapEntity;
    }

}
