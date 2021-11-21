package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.FadingReality;
import com.mygdx.game.UI.pda.BrowserUI;
import com.mygdx.game.component.Message;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.inventory.InventoryItem;
import com.mygdx.game.quest.QuestGraph;
import com.mygdx.game.UI.pda.PDAUI;
import com.mygdx.game.observer.InventoryObserver;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.EntityConfig;
import com.mygdx.game.inventory.InventoryItemLocation;
import com.mygdx.game.observer.ComponentObserver;
import com.mygdx.game.dialogs.ConversationGraph;
import com.mygdx.game.observer.ConversationGraphObserver;
import com.mygdx.game.observer.ProfileObserver;
import com.mygdx.game.profile.ProfileManager;
import com.mygdx.game.tools.HealthBar;
import com.mygdx.game.tools.Toast;
import com.mygdx.game.weapon.Ammo.AmmoID;
import com.mygdx.game.weapon.WeaponSystem;
import com.mygdx.game.world.MapManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PlayerHUD extends Stage implements ProfileObserver, ComponentObserver, ConversationGraphObserver, InventoryObserver {

    // PlayerHUD это челл который подписан на каналы, обсерверы

    private Entity player;

    private HealthBar healthBar;

    private InventoryUI inventoryUI;
    public ConversationUI conversationUI;
    private QuestUI questUI;
    public static PDAUI pdaui;
    public static BrowserUI browserUI;
    private Image image;

    private Json json;
    private MapManager mapMgr;

    private Label tooltip1, tooltip2;
    private int hour, min;
    private String currentState;
    private float ammoCount;
    private Vector3 mouseCoordinates;
    private float zoom;
    private Vector2 playerPosition;

    private ImageButton button, button2, button3;
    private Label labelTextTest;

    private Label labelMapName;
    private String mapName;

    public static List<Toast> toasts = new LinkedList<Toast>();
    private static Toast.ToastFactory toastFactory;

    public PlayerHUD(Entity _player, MapManager _mapMgr) {
        player=_player;
        mapMgr=_mapMgr;

        toastFactory = new Toast.ToastFactory.Builder().font(FadingReality.font).build();

        json = new Json();

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.texture));
        Drawable drawable2 = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.texture2));
        Drawable drawable3 = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.texture3));


        button = new ImageButton(drawable3);
        button.setPosition(50,50);

        button2 = new ImageButton(drawable);
        button2.setPosition(25,25);

        button3 = new ImageButton(drawable2);
        button3.setPosition(1550,25);

//        this.addActor(button);
//        this.addActor(button2);
//        this.addActor(button3);

        healthBar = new HealthBar(180, 20);
        healthBar.setPosition(Gdx.graphics.getWidth()- 200, Gdx.graphics.getHeight() - 50);

        inventoryUI = new InventoryUI();
        inventoryUI.setKeepWithinStage(false);
        inventoryUI.setMovable(true);
        inventoryUI.setVisible(false);
        inventoryUI.setSize(1100,600);
        inventoryUI.setPosition(0, 100);

        conversationUI = new ConversationUI(FadingReality.getUiSkin());
        conversationUI.setPosition(FadingReality.HEIGHT / 1.5f,50);
        conversationUI.setSize(500,300);
        conversationUI.setMovable(true);
        conversationUI.setVisible(false);

        questUI = new QuestUI();
        questUI.setPosition(FadingReality.WIDTH / 4, 50);
        questUI.setSize(500,300);
        questUI.setMovable(true);
        questUI.setVisible(false);
        questUI.setKeepWithinStage(false);

        browserUI = new BrowserUI("BB", FadingReality.getUiSkin());
        browserUI.setSize(1800,950);
        browserUI.setPosition(50, 60);
        browserUI.setVisible(false);
        browserUI.setResizable(true);

        pdaui = new PDAUI("", FadingReality.getUiSkin());
        pdaui.setMovable(true);
        pdaui.setVisible(false);
        pdaui.setResizable(true);
        pdaui.setSize(468,790);
        pdaui.setPosition(1250, 200);
//        pdaui.setDebug(true);

        //Delta, FPS and other stats
        tooltip1 = new Label("", FadingReality.getUiSkin());
        tooltip1.setPosition(0, Gdx.graphics.getHeight() - 100);

        tooltip2 = new Label("Tab - PDA \n" + "Z - Inventory \n" + "X - QuestList \n" + "NUMPAD_1 - Activate Enemy \n " + "NUMPAD_2 - Shader1 & Speed \n " + "NUMPAD_3 - Shader2 \n " + "NUMPAD_4 - Activate Debug", FadingReality.getUiSkin());
        tooltip2.setPosition(0, 0);

        labelMapName = new Label("", FadingReality.getUiSkin());
        labelMapName.setPosition(Gdx.graphics.getWidth() - 500, Gdx.graphics.getHeight() - 50);

//        image = new Image(new TextureRegion(NonameGame.resourceManager.texture2));
//        image.setPosition(500,500);
//        image.setSize(128,128);

        this.addActor(inventoryUI);
        this.addActor(conversationUI);
        this.addActor(questUI);
        this.addActor(pdaui);
        this.addActor(healthBar);
        this.addActor(browserUI);
        this.addActor(tooltip1);
        this.addActor(tooltip2);
        this.addActor(labelMapName);
//        this.addActor(image);

        //add tooltips to the stage
        Array<Actor> actors = inventoryUI.getInventoryActors();
        for(Actor actor : actors){
            this.addActor(actor);
        }

        //Observers
        player.registerObserver(this);
        inventoryUI.addObserver(this);

        //Listeners
        conversationUI.getCloseButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                conversationUI.setVisible(false);
                mapMgr.clearCurrentSelectedMapEntity();
            }
        });

    }

    @Override
    public void onNotify(ProfileManager profileManager, ProfileEvent event) {
        switch(event){
            case PROFILE_LOADED:
                System.out.println("PROFILE LOADING");
                boolean firstTime = profileManager.getIsNewProfile();
                if(firstTime){
                    System.out.println("CREATING NEW PROFILE");
                    InventoryUI.clearInventoryItems(inventoryUI.getInventorySlotTable());
                    InventoryUI.clearInventoryItems(inventoryUI.getEquipSlotTable());

                    Map<String, Integer> allAmmoCount = new HashMap<>();
                    for (AmmoID ammo: AmmoID.values()) {
                        String nameAmmo = ammo.getValue();
                        allAmmoCount.put(nameAmmo, 0);
                    }
                    WeaponSystem.setBagAmmunition(allAmmoCount);

                    Array<InventoryItem.ItemID> items = player.getEntityConfig().getInventory(); // дефолтные предметы из EntityConfig
                    Array<InventoryItemLocation> itemLocations = new Array<InventoryItemLocation>();
                    for( int i = 0; i < items.size; i++){
                        itemLocations.add(new InventoryItemLocation(i, items.get(i).toString(), 1, 0, InventoryUI.PLAYER_INVENTORY)); // расставляем предметы
                    }
                    InventoryUI.populateInventory(inventoryUI.getInventorySlotTable(), itemLocations, inventoryUI.getDragAndDrop(), InventoryUI.PLAYER_INVENTORY, false);
                    profileManager.getPlayerConfig().setInventory(InventoryUI.getInventory(inventoryUI.getInventorySlotTable()));

//                    questUI.setQuests(new Array<QuestGraph>());
                    questUI.loadQuest("main/plot/start.json");

                    profileManager.getPlayerConfig().setPosition(new Vector2(1188,281));
                    profileManager.getPlayerConfig().setDirection(Entity.Direction.LEFT);

                    Vector2 initPlayerPosition = profileManager.getPlayerConfig().getPosition();
                    Entity.Direction direction = profileManager.getPlayerConfig().getDirection();
                    player.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(initPlayerPosition));
                    player.sendMessage(Message.MESSAGE.CURRENT_DIRECTION, json.toJson(direction));
                } else {
                    Map<String, Integer> allAmmoCount = profileManager.getPlayerConfig().getBagAmmunition();
                    WeaponSystem.setBagAmmunition(allAmmoCount);
//                    player.sendMessage(Message.MESSAGE.INIT_ALL_AMMO_COUNT, json.toJson(allAmmoCount));

                    Array<InventoryItemLocation> inventory = profileManager.getPlayerConfig().getInventory();
                    InventoryUI.populateInventory(inventoryUI.getInventorySlotTable(), inventory, inventoryUI.getDragAndDrop(), InventoryUI.PLAYER_INVENTORY, false);

                    Array<InventoryItemLocation> equipment = profileManager.getPlayerConfig().getEquipment();
                    if( equipment != null && equipment.size > 0 ){
                        InventoryUI.populateInventory(inventoryUI.getEquipSlotTable(), equipment, inventoryUI.getDragAndDrop(), InventoryUI.PLAYER_INVENTORY, false);
                    }

                    Array<QuestGraph> quests = profileManager.getPlayerConfig().getQuests();
                    questUI.setQuests(quests);

                    Vector2 initPlayerPosition = profileManager.getPlayerConfig().getPosition();
                    Entity.Direction direction = profileManager.getPlayerConfig().getDirection();
                    player.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(initPlayerPosition));
                    player.sendMessage(Message.MESSAGE.CURRENT_DIRECTION, json.toJson(direction));
                }
                break;
            case SAVING_PROFILE:
                System.out.println("PROFILE CONFIG SAVING");
                profileManager.getSettingsConfig().setLastActiveAccount(profileManager.getProfileName());
                profileManager.getPlayerConfig().setQuests(questUI.getQuests());  // Quests
                profileManager.getPlayerConfig().setBagAmmunition(player.getBagAmmunition()); // Bag Ammunition
                profileManager.getPlayerConfig().setInventory(InventoryUI.getInventory(inventoryUI.getInventorySlotTable())); // Inventory
                profileManager.getPlayerConfig().setEquipment(InventoryUI.getInventory(inventoryUI.getEquipSlotTable())); // Equipment
                profileManager.getPlayerConfig().setPosition(player.getCurrentPosition());  // XY position
                profileManager.getPlayerConfig().setDirection(player.getCurrentDirection());  // Direction
                profileManager.getPlayerConfig().setState(player.getCurrentState());  // State
                break;
            case CLEAR_CURRENT_PROFILE:
                System.out.println("PROFILE CONFIG CLEARING");
//                profileManager.getPlayerConfig().setQuests(new Array<QuestGraph>());
//                profileManager.getPlayerConfig().setInventory(new Array<InventoryItemLocation>());
                break;
            default:
                break;
        }
    }

    @Override
    public void onNotify(String value, ComponentEvent event) {
        switch(event) {
            case LOAD_CONVERSATION:
                EntityConfig config = json.fromJson(EntityConfig.class, value);
                conversationUI.loadConversation(config);
                conversationUI.getConversationGraph().addObserver(this);
                if( config.getEntityID().equalsIgnoreCase(conversationUI.getCurrentEntityID())) {
                    conversationUI.setVisible(true);
                }
                break;
            case SHOW_CONVERSATION:
                EntityConfig configShow = json.fromJson(EntityConfig.class, value);
                if( configShow.getEntityID().equalsIgnoreCase(conversationUI.getCurrentEntityID())) {
                    conversationUI.setVisible(true);
                }
                break;
            case HIDE_CONVERSATION:
                EntityConfig configHide = json.fromJson(EntityConfig.class, value);
                if(configHide.getEntityID().equalsIgnoreCase(conversationUI.getCurrentEntityID())) {
                    conversationUI.setVisible(false);
                }
                break;
            case QUEST_LOCATION_DISCOVERED:
                break;
            case ENEMY_SPAWN_LOCATION_CHANGED:
                break;
            case PLAYER_HAS_MOVED:
                break;
            case PLAYER_SHOT:
                Integer ammoCountInMagazine = json.fromJson(Integer.class, value);
                inventoryUI.getItemFromWeaponRangedWeaponSlot().setNumberItemsInside(ammoCountInMagazine);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNotify(ConversationGraph graph, ConversationCommandEvent event) {
        switch(event) {
            case ACCEPT_QUEST:
                System.out.println("Accept quest!");

                Entity currentlyEntity = mapMgr.getCurrentMapEntity();
                if( currentlyEntity == null ){
                    break;
                }
                EntityConfig config = currentlyEntity.getEntityConfig();
                QuestGraph questGraph = questUI.loadQuest(config.getQuestConfigPath());

                if( questGraph != null ) {
                    updateEntityObservers();
                }

                conversationUI.setVisible(false);
                mapMgr.clearCurrentSelectedMapEntity();
                break;
            case EXIT_CONVERSATION:
                System.out.println("Exit conversation!");

                conversationUI.setVisible(false);
                mapMgr.clearCurrentSelectedMapEntity();
                break;
            case TASK_COMPLETE:
                questUI.updateQuests(mapMgr);
                mapMgr.setMapChanged(true);

//                Entity currentlyEntity2 = mapMgr.getCurrentMapEntity();
//                QuestGraph graph2 = json.fromJson(QuestGraph.class, Gdx.files.internal(currentlyEntity2.getEntityConfig().getQuestConfigPath()));
//                questUI.questTaskComplete(graph2.getQuestID(), questTaskID);

                conversationUI.setVisible(false);
                mapMgr.clearCurrentSelectedMapEntity();
            case NONE:
                break;
        }

    }

    @Override
    public void onNotify(InventoryItem item, InventoryEvent event) {
        switch (event) {
            case ADDED:
                if(item.isInventoryItemOffensiveMelee()) {
                    InventoryItem.ItemID itemID = item.getItemID();
                    player.sendMessage(Message.MESSAGE.SET_MELEE_WEAPON, json.toJson(itemID.toString()));
                } else if(item.isInventoryItemOffensiveRanged()) {
                    String itemID = item.getItemID().toString();
                    int ammoCountInMagazine = item.getNumberItemsInside();
                    player.sendMessage(Message.MESSAGE.SET_RANGED_WEAPON, json.toJson(itemID + Message.MESSAGE_TOKEN_2 + ammoCountInMagazine));
                }
                break;
            case REMOVED:
                if(item.isInventoryItemOffensiveMelee()) {
                    player.sendMessage(Message.MESSAGE.REMOVE_MELEE_WEAPON, json.toJson("null"));
                } else if(item.isInventoryItemOffensiveRanged()) {
                    player.sendMessage(Message.MESSAGE.REMOVE_RANGED_WEAPON, json.toJson("null"));
                }
                break;
            case ITEM_CONSUMED:
                break;
        }
    }


    public void updateEntityObservers(){
        mapMgr.unregisterCurrentMapEntityObservers();
        questUI.initQuests(mapMgr);
        mapMgr.registerCurrentMapEntityObservers(this);
    }

    public void setHourMin(int hour, int min) {
        this.hour=hour;
        this.min=min;
    }

    public void setCurrentState(String curentState){
        this.currentState=curentState;
    }

    public void setCountAmmo(float ammoCount){
        this.ammoCount=ammoCount;
    }

    public void setMouseCoordinates(Vector3 mouseCoordinates) {
        this.mouseCoordinates=mouseCoordinates;
    }

    public void setLabelMapName(String mapName) {
        this.mapName=mapName;
    }

    public void setCameraZoom(float zoom) {
        this.zoom=zoom;
    }

    public void setPlayerPosition(Vector2 playerPosition) {
        this.playerPosition=playerPosition;
    }



    public void update() {

        if (!browserUI.isVisible()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
                pdaui.setVisible(pdaui.isVisible() ? false : true);
            }

            if(Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                inventoryUI.setVisible(inventoryUI.isVisible() ? false : true);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                questUI.setVisible(questUI.isVisible() ? false : true);
            }
        }


        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            browserUI.setVisible(false);
            pdaui.setVisible(true);
        }


    }

    public void render(float delta) {
//        Delta and FPS
        tooltip1.setText("Delta = " + delta + "\n" +
                "FPS = " + Gdx.graphics.getFramesPerSecond() + "\n" +
                "Game Time = " + hour + ":" + min + "\n" +
                "State = " + currentState + "\n" +
                "Ammo = " + player.getAmmoCountInMagazine() + "\n" +
                "Mouse Cord = X: " + Math.round(mouseCoordinates.x) + " Y: " + Math.round(mouseCoordinates.y) + "\n" +
                "Camera Zoom = " + zoom + "\n" +
                "Player Position = X: " + playerPosition.x + " Y: " + playerPosition.y);
        labelMapName.setText(mapName);

        // handle toast queue and display
        Iterator<Toast> it = toasts.iterator();
        while(it.hasNext()) {
            Toast t = it.next();
            if (!t.render(Gdx.graphics.getDeltaTime())) {
                it.remove(); // toast finished -> remove
            } else {
                break; // first toast still active, break the loop
            }
        }

        this.act(delta);
        this.draw();
    }

    public static void toastShort(String text, Toast.Length length) {
        toasts.add(toastFactory.create(text, length));
    }


}
