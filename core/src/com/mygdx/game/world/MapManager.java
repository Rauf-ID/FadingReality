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

public class MapManager implements ProfileObserver {

    private static final String TAG = MapManager.class.getSimpleName();

    private OrthographicCamera camera;
    private boolean mapChanged = false;
    private Map currentMap;
    private Entity player;
    private Entity currentSelectedEntity = null;

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
                loadMap(mapType);

                break;
            case SAVING_PROFILE:
                if( currentMap != null ){
                    profileManager.getPlayerConfig().setCurrentMap(currentMap.getCurrentMapType());
                }
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
        Map map = MapFactory.getMap(mapType);

        if( map == null ){
            Gdx.app.debug(TAG, "Map does not exist!  ");
            return;
        }

        currentMap = map;
        mapChanged = true;
        clearCurrentSelectedMapEntity();
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

    public void clearCurrentSelectedMapEntity(){
        if( currentSelectedEntity == null ) return;
        currentSelectedEntity.sendMessage(Message.MESSAGE.ENTITY_DESELECTED);
        currentSelectedEntity = null;
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

    public Entity getCurrentSelectedMapEntity(){
        return currentSelectedEntity;
    }

    public void setCurrentSelectedMapEntity(Entity currentSelectedEntity) {
        this.currentSelectedEntity = currentSelectedEntity;
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

}
