package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.FadingReality;
import com.mygdx.game.component.Message;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.profile.ProfileManager;
import com.mygdx.game.tools.MapObjectsManager;
import com.mygdx.game.UI.PlayerHUD;
import com.mygdx.game.tools.OrthogonalTiledMapRendererWithSprites;
import com.mygdx.game.tools.managers.ShaderVFXManager;
import com.mygdx.game.world.Map;
import com.mygdx.game.world.MapFactory;
import com.mygdx.game.world.MapManager;

import java.util.ArrayList;
import java.util.Collections;

public class GameScreen implements Screen {

    public enum GameState {
        SAVING,
        LOADING,
        RUNNING,
        PAUSED,
        GAME_OVER
    }

    private static GameState gameState;

    private FadingReality game;

    private Json json;
    private Entity player;
    private MapManager mapMgr;
    private PlayerHUD playerHUD;
    private OrthographicCamera camera;

    private OrthogonalTiledMapRendererWithSprites mapRenderer = null;
    private InputMultiplexer inputMultiplexer;
//        private QuestManager questManager;

    //MANAGERS ShaderAndVFX / MapObjects
    private ShaderVFXManager shaderVFXManager;
    private MapObjectsManager mapObjectsManager;

    //LIST FOR SORT ENTITIES
    private ArrayList<Entity> entities = new ArrayList<Entity>();

    public GameScreen(FadingReality game) {
        this.game=game;

        json = new Json();

        mapMgr = new MapManager();
        player = EntityFactory.getInstance().getEntity(EntityFactory.EntityType.PLAYER);
        player.sendMessage(Message.MESSAGE.INIT_CONFIG, json.toJson(player.getEntityConfig()));
        mapMgr.setPlayer(player);

        playerHUD = new PlayerHUD(player, mapMgr);
//        playerHUD.updateEntityObservers();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        camera.zoom = 0.5f; //45 //1.80
        mapMgr.setCamera(camera);
        if( mapRenderer == null ){
            mapRenderer = new OrthogonalTiledMapRendererWithSprites(mapMgr.getCurrentTiledMap(), Map.UNIT_SCALE);
        }

        //ADD OBSERVER FOR PROFILE
        ProfileManager.getInstance().addObserver(mapMgr);
        ProfileManager.getInstance().addObserver(playerHUD);
        setGameState(GameState.LOADING);

        //CREATE QuestMANAGER
//            questManager = new QuestManager();
//            questManager.loadQuest("quest1");

        //CREATE ShaderVFXMANAGER
        shaderVFXManager = new ShaderVFXManager();

        //CREATE MAP OBJECTS
        mapObjectsManager = new MapObjectsManager();

        //INPUT
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(playerHUD);
        inputMultiplexer.addProcessor(player.getInputProcessor());
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().setProjectionMatrix(camera.combined);

        //MAPS CHANGER
        mapMgr.quickChangeMap();

        //SOME UI UPDATES
        playerHUD.update();
        playerHUD.setCurrentState(player.getCurrentState().toString());
        playerHUD.setCountAmmo(0);
        playerHUD.setMouseCoordinates(player.getMouseCoordinates());
        playerHUD.setCameraZoom(camera.zoom);
        playerHUD.setPlayerPosition(player.getCurrentPosition());

        //MapRENDER
        mapRenderer.setView(camera);
        mapRenderer.getBatch().enableBlending();
        mapRenderer.getBatch().setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        //MAP HAS CHANGED
        if(mapMgr.hasMapChanged()){
            mapRenderer.setMap(mapMgr.getCurrentTiledMap());
            playerHUD.updateEntityObservers();
            playerHUD.setLabelMapName(mapMgr.getCurrentMap().getNameMap());
            entities.clear();
            MapLayer mapCollisionLayer = mapMgr.getMapObjectsLayer();
            if(mapCollisionLayer != null){
                MapObjects objects = mapCollisionLayer.getObjects();
                for(MapObject object: objects) {
                    if (object.getProperties().get("objectType").equals("item") && player.getMapItems().contains((int) object.getProperties().get("objectID"), true)) {
                        continue;
                    } else {
                        TextureMapObject textureMapObject = (TextureMapObject) object;
                        mapMgr.addMapEntities(EntityFactory.getInstance().getEntity(EntityFactory.EntityType.MAPOBJECT, textureMapObject));
                    }
                }
            }

            entities.add(player);
            for (Entity entity: mapMgr.getCurrentMapEntities()){
                entities.add(entity);
            }
            for (Entity entity: mapMgr.getCurrentMapQuestEntities()){
                entities.add(entity);
            }
            mapMgr.setMapChanged(false);
        }

        //SORT ENTITY AND MAP OBJECTS
        Collections.sort(entities);

        //UPDATE ENTITY
        player.update(mapMgr, game.getBatch(), delta);
        mapMgr.updateCurrentMapEntities(mapMgr, game.getBatch(), delta);

        //VFX - EFFECTS
        shaderVFXManager.updateVFX();

        //RENDER BACKGROUND LAYERS MAP
        mapRenderer.getBatch().begin();
        TiledMapTileLayer backgroundMapLayer = (TiledMapTileLayer)mapMgr.getCurrentTiledMap().getLayers().get(Map.BACKGROUND_LAYER);
        if( backgroundMapLayer != null ){
            mapRenderer.renderTileLayer(backgroundMapLayer);
        }
        mapRenderer.getBatch().end();

        //RENDER SORTED ENTITY, GUN AND MAP OBJECTS
        for(Entity e: entities){
            e.draw(game.getBatch(), delta);
        }

        //UPDATE MAP OBJECTS
        mapObjectsManager.update(game, mapMgr, delta);

        //RENDER FRONT LAYERS MAP
        mapRenderer.getBatch().begin();
        TiledMapTileLayer groundMapLayer = (TiledMapTileLayer)mapMgr.getCurrentTiledMap().getLayers().get(Map.FRONT_LAYER);
        if( groundMapLayer != null ){
            mapRenderer.renderTileLayer(groundMapLayer);
        }
        TiledMapTileLayer lightMapLayer = (TiledMapTileLayer)mapMgr.getCurrentTiledMap().getLayers().get(Map.LIGHT_LAYER);
        if( groundMapLayer != null ){
            mapRenderer.renderTileLayer(lightMapLayer);
        }
        mapRenderer.getBatch().end();

        //UPDATE SHADER
        shaderVFXManager.updateShader(game, player, mapMgr, delta);

        //VFX - EFFECTS
        shaderVFXManager.renderVFX();

        //DRAW RENDERING
        playerHUD.render(delta);

        game.getBatch().begin();
        FadingReality.font.setColor(Color.GOLD);
        FadingReality.font.draw(game.getBatch(), "Hello", 250,340);
        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        shaderVFXManager.resize(width, height);
    }

    @Override
    public void pause() {
        setGameState(GameState.SAVING);
    }

    @Override
    public void resume() {
        setGameState(GameState.LOADING);
    }

    @Override
    public void hide() {
        if( gameState != GameState.GAME_OVER ){
            setGameState(GameState.SAVING);
        }

        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("DISPOSE");
        if( player != null ){
            player.unregisterObservers();
            player.dispose();
        }

        if(mapRenderer != null ){
            mapRenderer.dispose();
        }

        playerHUD.dispose();
        MapFactory.clearCache();

        shaderVFXManager.dispose();
    }

    public static void setGameState(GameState _gameState){
        switch(_gameState){
            case RUNNING:
                gameState = GameState.RUNNING;
                break;
            case LOADING:
                ProfileManager.getInstance().loadProfile();
                gameState = GameState.RUNNING;
                break;
            case SAVING:
                ProfileManager.getInstance().saveProfile();
                ProfileManager.getInstance().saveSetting();
                gameState = GameState.PAUSED;
                break;
            case PAUSED:
                if( gameState == GameState.PAUSED ){
                    gameState = GameState.RUNNING;
                }else if( gameState == GameState.RUNNING ){
                    gameState = GameState.PAUSED;
                }
                break;
            case GAME_OVER:
                gameState = GameState.GAME_OVER;
                break;
            default:
                gameState = GameState.RUNNING;
                break;
        }

    }

}