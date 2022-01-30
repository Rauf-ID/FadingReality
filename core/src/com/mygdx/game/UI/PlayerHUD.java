package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.FadingReality;
import com.mygdx.game.UI.pda.BrowserUI;
import com.mygdx.game.UI.pda.SkillUI;
import com.mygdx.game.component.Message;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.ItemFactory;
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
import com.mygdx.game.skills.Skill;
import com.mygdx.game.skills.SkillFactory;
import com.mygdx.game.tools.ProgressBarNew;
import com.mygdx.game.tools.Toast;
import com.mygdx.game.weapon.Ammo.AmmoID;
import com.mygdx.game.weapon.WeaponSystem;
import com.mygdx.game.world.MapItem;
import com.mygdx.game.world.MapManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.mygdx.game.component.Message.MESSAGE_TOKEN_2;

public class PlayerHUD extends Stage implements ProfileObserver, ComponentObserver, ConversationGraphObserver, InventoryObserver {

    // PlayerHUD это челл который подписан на каналы, обсерверы

    private Entity player;

    private ProgressBarNew healthBar;
    private ProgressBarNew progressBar;

    private int iis;

    private SkillUI skillUI;
    private QuestUI questUI;
    public static PDAUI pdaUI;
    private StatusUI statusUI;
    private TooltipUI tooltipUI;
    private InventoryUI inventoryUI;
    public ConversationUI conversationUI;
    public static BrowserUI browserUI;

    private Json json;
    private MapManager mapMgr;

    private Label tooltip1, tooltip2;
    private int hour, min;
    private String currentState;
    private float ammoCount;
    private Vector3 mouseCoordinates;
    private float zoom;
    private Vector2 playerPosition;
    private String mapName;

    public static List<Toast> toasts = new LinkedList<Toast>();
    private static Toast.ToastFactory toastFactory;

    public PlayerHUD(Entity _player, MapManager _mapMgr) {
        player=_player;
        mapMgr=_mapMgr;

        toastFactory = new Toast.ToastFactory.Builder().font(FadingReality.font).build();

        json = new Json();

        healthBar = new ProgressBarNew(180, 20, 0f,1f, 0.01f, false);
        healthBar.setPosition(Gdx.graphics.getWidth()- 200, Gdx.graphics.getHeight() - 50);

        progressBar = new ProgressBarNew(292, 10, 0f,1f, 0.01f, false);
        progressBar.setPosition(16+113, 55);

        statusUI = new StatusUI();
        statusUI.setVisible(true);
        statusUI.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        statusUI.setPosition(0, 0);

        tooltipUI = new TooltipUI();
        tooltipUI.setSize(300, Gdx.graphics.getHeight());
        tooltipUI.setPosition(Gdx.graphics.getWidth(), 0);
        tooltipUI.setMovable(true);
        tooltipUI.setVisible(true);

        tooltipUI.right().bottom();

        inventoryUI = new InventoryUI();
        inventoryUI.setKeepWithinStage(false);
        inventoryUI.setMovable(true);
        inventoryUI.setVisible(false);
        inventoryUI.setSize(1100,600);
        inventoryUI.setPosition(0, 100);

        conversationUI = new ConversationUI(FadingReality.getUiSkin());
        conversationUI.setSize(500,300);
        conversationUI.setPosition(FadingReality.HEIGHT / 1.5f,50);
        conversationUI.setMovable(true);
        conversationUI.setVisible(false);

        questUI = new QuestUI();
        questUI.setSize(500,300);
        questUI.setPosition(FadingReality.WIDTH / 4, 50);
        questUI.setMovable(true);
        questUI.setVisible(false);
        questUI.setKeepWithinStage(false);

        skillUI = new SkillUI();
        skillUI.setSize(1756,946);
        skillUI.setPosition(50, 60);
        skillUI.setMovable(true);
        skillUI.setVisible(false);

        browserUI = new BrowserUI(inventoryUI);
        browserUI.setSize(1756,946);
        browserUI.setPosition(50, 60);
        browserUI.setVisible(false);

        pdaUI = new PDAUI(this,"", FadingReality.getUiSkin());
        pdaUI.setMovable(true);
        pdaUI.setVisible(false);
        pdaUI.setResizable(true);
        pdaUI.setSize(468,790);
        pdaUI.setPosition(1250, 200);

        //Delta, FPS and other stats
        tooltip1 = new Label("", FadingReality.getUiSkin());
        tooltip1.setPosition(0, Gdx.graphics.getHeight() - 100);

        tooltip2 = new Label("Tab - PDA \n" + "Z - Inventory \n" + "X - QuestList \n" + "NUMPAD_1 - Activate Enemy \n " + "NUMPAD_2 - Shader1 & Speed \n " + "NUMPAD_3 - Shader2 \n " + "NUMPAD_4 - Activate Debug", FadingReality.getUiSkin());
        tooltip2.setPosition(0, 0);

//        this.addActor(progressBar);
//        this.addActor(healthBar);
//        this.addActor(statusUI);
        this.addActor(inventoryUI);
        this.addActor(conversationUI);
        this.addActor(questUI);
        this.addActor(skillUI);
        this.addActor(pdaUI);
        this.addActor(browserUI);
        this.addActor(tooltip1);
//        this.addActor(tooltip2);
        this.addActor(tooltipUI);

        //add tooltips to the stage
        this.addActor(inventoryUI.getInventorySlotTooltip());

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
                        allAmmoCount.put(nameAmmo, 100); // should be 0 Ammo
                    }
                    WeaponSystem.setBagAmmunition(allAmmoCount);

                    Array<Item.ItemID> inventoryItems = player.getEntityConfig().getInventory(); // дефолтные предметы из EntityConfig
                    Array<InventoryItemLocation> itemLocations = new Array<InventoryItemLocation>();
                    for( int i = 0; i < inventoryItems.size; i++){
                        itemLocations.add(new InventoryItemLocation(i, inventoryItems.get(i).toString(), 1, 0, InventoryUI.PLAYER_INVENTORY)); // расставляем предметы
                    }
                    InventoryUI.populateInventory(inventoryUI.getInventorySlotTable(), itemLocations, inventoryUI.getDragAndDrop(), InventoryUI.PLAYER_INVENTORY, false);
                    profileManager.getPlayerConfig().setInventory(InventoryUI.getInventory(inventoryUI.getInventorySlotTable()));

                    Array<Item.ItemID> shopItems = player.getEntityConfig().getShopItems(); // дефолтные предметы из EntityConfig
                    profileManager.getPlayerConfig().setShopItems(shopItems);
                    browserUI.setShopItems(shopItems);

                    Array<Integer> mapItems = new Array<>(10);
                    mapItems.addAll(0,1,2,3,4,5);
                    player.setMapItems(mapItems);

//                    questUI.setQuests(new Array<QuestGraph>());
                    questUI.loadQuest("main/plot/start.json");

                    profileManager.getPlayerConfig().setPosition(new Vector2(1188,281));
                    profileManager.getPlayerConfig().setDirection(Entity.Direction.LEFT);
                    profileManager.getPlayerConfig().setExoskeletonName(EntityFactory.EntityName.NONE);
                    profileManager.getPlayerConfig().setDashCharges(4);
                    profileManager.getPlayerConfig().setMaxDashCharges(4);
                    profileManager.getPlayerConfig().setMaxHp(100);
                    profileManager.getPlayerConfig().setDamageResist(0);
                    profileManager.getPlayerConfig().setExperience(5);
                    profileManager.getPlayerConfig().setCoins(10);

                    Vector2 initPlayerPosition = profileManager.getPlayerConfig().getPosition();
                    Entity.Direction direction = profileManager.getPlayerConfig().getDirection();
                    player.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(initPlayerPosition));
                    player.sendMessage(Message.MESSAGE.CURRENT_DIRECTION, json.toJson(direction));
                    player.setDashCharge(profileManager.getPlayerConfig().getDashCharges());
                    progressBar.setValue(profileManager.getPlayerConfig().getDashCharges());
                    player.setMaxDashCharges(profileManager.getPlayerConfig().getMaxDashCharges());
                    player.setMaxHealth(profileManager.getPlayerConfig().getMaxHp());
                    player.setHealth(profileManager.getPlayerConfig().getMaxHp());
                    player.setExperience(profileManager.getPlayerConfig().getExperience());
                    player.setDashSpeed(0);
                    player.setDashDist(0);
                    player.setPlayerSkills(new Array<Integer>());
                    player.setAvailableSkills(new Array<Integer>());
                    player.getAvailableSkills().addAll(0,1,2,27);

                    Skill firstSkill = SkillFactory.getInstance().getSkill(0);
                    Skill secondSkill = SkillFactory.getInstance().getSkill(1);
                    Skill thirdSkill = SkillFactory.getInstance().getSkill(27);
                    firstSkill.unlockSkill(player);
                    secondSkill.unlockSkill(player);
                    thirdSkill.unlockSkill(player);
                    skillUI.createSkillTree(player);
                    this.addActor(skillUI.getSkillTooltip());

                    pdaUI.setCoins(profileManager.getPlayerConfig().getCoins());
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

                    Array<Item.ItemID> shopItems = profileManager.getPlayerConfig().getShopItems();
                    browserUI.setShopItems(shopItems);

                    Array<Integer> mapItems = profileManager.getPlayerConfig().getMapItems();
                    player.setMapItems(mapItems);

                    Array<QuestGraph> quests = profileManager.getPlayerConfig().getQuests();
                    questUI.setQuests(quests);

                    Vector2 initPlayerPosition = profileManager.getPlayerConfig().getPosition();
                    Entity.Direction direction = profileManager.getPlayerConfig().getDirection();
                    player.sendMessage(Message.MESSAGE.INIT_START_POSITION, json.toJson(initPlayerPosition));
                    player.sendMessage(Message.MESSAGE.CURRENT_DIRECTION, json.toJson(direction));
                    player.setDashCharge(profileManager.getPlayerConfig().getDashCharges());
                    progressBar.setValue(profileManager.getPlayerConfig().getDashCharges());
                    player.setMaxDashCharges(profileManager.getPlayerConfig().getMaxDashCharges());
                    player.setCritChanсe(profileManager.getPlayerConfig().getCritChance());
                    player.setExecutionThreshold(profileManager.getPlayerConfig().getExecutionThreshold());
                    player.setHealAmount(profileManager.getPlayerConfig().getHealAmount());
                    player.setMeleeDamageBoost(profileManager.getPlayerConfig().getMeleeDamageBoost());
                    player.setRangedDamageBoost(profileManager.getPlayerConfig().getRangedDamageBoost());
                    player.setWeaponSpeed(profileManager.getPlayerConfig().getWeaponSpeed());
                    player.setRudimentCooldown(profileManager.getPlayerConfig().getRudimentCooldown());
                    player.setDamageBoost(profileManager.getPlayerConfig().getDamageBoost());
                    player.setDamageResist(profileManager.getPlayerConfig().getDamageResist());
                    player.setMaxHealth(profileManager.getPlayerConfig().getMaxHp());
                    player.setHealth(profileManager.getPlayerConfig().getHealth());
                    player.setPlayerSkills(profileManager.getPlayerConfig().getPlayerSkills());
                    player.setAvailableSkills(profileManager.getPlayerConfig().getAvailableSkills());
                    player.setExperience(profileManager.getPlayerConfig().getExperience());
                    player.setDashDist(profileManager.getPlayerConfig().getDashDist());
                    player.setDashSpeed(profileManager.getPlayerConfig().getDashSpeed());

                    skillUI.createSkillTree(player);
                    this.addActor(skillUI.getSkillTooltip());

                    pdaUI.setCoins(profileManager.getPlayerConfig().getCoins());

                    if (profileManager.getPlayerConfig().getExoskeletonName() != EntityFactory.EntityName.NONE) {
                        EntityFactory.EntityName exoskeletonName = profileManager.getPlayerConfig().getExoskeletonName();
                        player.sendMessage(Message.MESSAGE.EQUIP_EXOSKELETON, json.toJson(exoskeletonName));
                    }
                }
                break;
            case SAVING_PROFILE:
                System.out.println("PROFILE CONFIG SAVING");
                profileManager.getSettingsConfig().setLastActiveAccount(profileManager.getProfileName());
                profileManager.getPlayerConfig().setQuests(questUI.getQuests()); // Quests
                profileManager.getPlayerConfig().setBagAmmunition(player.getBagAmmunition()); // Bag Ammunition
                profileManager.getPlayerConfig().setInventory(InventoryUI.getInventory(inventoryUI.getInventorySlotTable())); // Inventory
                profileManager.getPlayerConfig().setEquipment(InventoryUI.getInventory(inventoryUI.getEquipSlotTable())); // Equipment
                profileManager.getPlayerConfig().setPosition(player.getCurrentPosition());  // XY position
                profileManager.getPlayerConfig().setDirection(player.getCurrentDirection());  // Direction
                profileManager.getPlayerConfig().setState(player.getCurrentState());  // State
                profileManager.getPlayerConfig().setExoskeletonName(player.getExoskeletonName()); // Exoskeleton name
                profileManager.getPlayerConfig().setDashCharges(player.getDashCharge()); // Dash charges
                profileManager.getPlayerConfig().setMaxDashCharges(player.getMaxDashCharges()); // Max dash charges
                profileManager.getPlayerConfig().setCritChance(player.getCritChanсe());
                profileManager.getPlayerConfig().setDamageBoost(player.getDamageBoost());
                profileManager.getPlayerConfig().setMeleeDamageBoost(player.getMeleeDamageBoost());
                profileManager.getPlayerConfig().setRangedDamageBoost(player.getRangedDamageBoost());
                profileManager.getPlayerConfig().setExecutionThreshold(player.getExecutionThreshold());
                profileManager.getPlayerConfig().setHealAmount(player.getHealAmount());
                profileManager.getPlayerConfig().setDamageResist(player.getDamageResist());
                profileManager.getPlayerConfig().setWeaponSpeed(player.getWeaponSpeed());
                profileManager.getPlayerConfig().setRudimentCooldown(player.getRudimentCooldown());
                profileManager.getPlayerConfig().setHealth(player.getHealth());
                profileManager.getPlayerConfig().setMaxHp(player.getMaxHealth());
                profileManager.getPlayerConfig().setPlayerSkills(player.getPlayerSkills());
                profileManager.getPlayerConfig().setAvailableSkills(player.getAvailableSkills());
                profileManager.getPlayerConfig().setExperience(player.getExperience());
                profileManager.getPlayerConfig().setCoins(pdaUI.getCoins());
                profileManager.getPlayerConfig().setDashDist(player.getDashDist());
                profileManager.getPlayerConfig().setDashSpeed(player.getDashSpeed());
                profileManager.getPlayerConfig().setMapItems(player.getMapItems());
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
                String string = json.fromJson(String.class, value);
                String[] splitStr = string.split(MESSAGE_TOKEN_2);
                statusUI.setLabelAmmoCountText(splitStr[0] + "/" + splitStr[1]);
                inventoryUI.getItemFromWeaponRangedWeaponSlot().setNumberItemsInside(Integer.parseInt(splitStr[0]));
                break;
            case ENEMY_DEAD:
                questUI.updateQuests(mapMgr);
//                mapMgr.setMapChanged(true);
                mapMgr.clearCurrentSelectedMapEntity();
                break;
            case ITEM_PICK_UP:
                Item item = ItemFactory.getInstance().getInventoryItem(Item.ItemID.POTIONS01);
                iis++;
                tooltipUI.addTooltip(  iis + " ITEM added to inventory");
            case PLAYER_DASH:
                progressBar.minusValue(0.25f);
                break;
            case PLAYER_DASH_UPDATE:
                progressBar.plusValue(0.25f);
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

//                float sec = 1;
//                Timer.schedule(new Timer.Task(){
//                    @Override
//                    public void run() {
//                        conversationUI.setVisible(false);
//                        mapMgr.clearCurrentSelectedMapEntity();
//                    }
//                }, sec);
            case NONE:
                break;
        }

    }

    @Override
    public void onNotify(Item item, InventoryEvent event) {
        switch (event) {
            case ADDED:
                if(item.isInventoryItemOffensiveMelee()) {
                    Item.ItemID itemID = item.getItemID();
                    statusUI.setMeleeWeapon(itemID);
                    player.sendMessage(Message.MESSAGE.SET_MELEE_WEAPON, json.toJson(itemID.toString()));
                } else if(item.isInventoryItemOffensiveRanged()) {
                    String itemID = item.getItemID().toString();
                    int ammoCountInMagazine = item.getNumberItemsInside();
                    statusUI.setRangeWeapon(item);
                    player.sendMessage(Message.MESSAGE.SET_RANGED_WEAPON, json.toJson(itemID + MESSAGE_TOKEN_2 + ammoCountInMagazine));
                }
                break;
            case REMOVED:
                if(item.isInventoryItemOffensiveMelee()) {
                    statusUI.clearMeleeWeapon();
                    player.sendMessage(Message.MESSAGE.REMOVE_MELEE_WEAPON, json.toJson("null"));
                } else if(item.isInventoryItemOffensiveRanged()) {
                    statusUI.clearRangeWeapon();
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
        this.hour = hour;
        this.min = min;
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
                pdaUI.setVisible(pdaUI.isVisible() ? false : true);
            }

            if(Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                inventoryUI.setVisible(inventoryUI.isVisible() ? false : true);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                questUI.setVisible(questUI.isVisible() ? false : true);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
                skillUI.setVisible(skillUI.isVisible() ? false : true);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            browserUI.setVisible(false);
            pdaUI.setVisible(true);
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
                "Player Position = X: " + playerPosition.x + " Y: " + playerPosition.y + "\n" +
                "Map = " + mapName);

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
