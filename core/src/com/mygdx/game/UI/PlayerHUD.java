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
import com.mygdx.game.FadingReality;
import com.mygdx.game.UI.pda.BrowserUI;
import com.mygdx.game.UI.pda.SkillUI;
import com.mygdx.game.component.Message;
import com.mygdx.game.entity.EntityFactory;
import com.mygdx.game.item.Item;
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
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.skills.Skill;
import com.mygdx.game.skills.SkillFactory;
import com.mygdx.game.tools.ProgressBarNew;
import com.mygdx.game.tools.Toast;
import com.mygdx.game.weapon.Ammo.AmmoID;
import com.mygdx.game.weapon.WeaponSystem;
import com.mygdx.game.world.MapManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.mygdx.game.component.Message.MESSAGE_TOKEN_2;

public class PlayerHUD extends Stage implements ProfileObserver, ComponentObserver, ConversationGraphObserver, InventoryObserver {

    private Json json;
    private Entity player;
    private MapManager mapMgr;
    private GameScreen gameScreen;

    private SkillUI skillUI;
    private QuestUI questUI;
    public static PDAUI pdaUI;
    private StatusUI statusUI;
    private TooltipUI tooltipUI;
    private InventoryUI inventoryUI;
    private ConversationUI conversationUI;
    public static BrowserUI browserUI;

    private ProgressBarNew healthBar;
    private ProgressBarNew progressBar;

    private float zoom;
    private String mapName;
    private String currentState;
    private Vector2 playerPosition;
    private Vector3 mouseCoordinates;
    private Label tooltip1, tooltip2;

    private static List<Toast> toasts = new LinkedList<Toast>();
    private static Toast.ToastFactory toastFactory;

    public PlayerHUD(GameScreen gameScreen, Entity player, MapManager _mapMgr) {
        json = new Json();
        mapMgr = _mapMgr;
        this.player = player;
        this.gameScreen = gameScreen;
        toastFactory = new Toast.ToastFactory.Builder().font(FadingReality.font).build();

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
        inventoryUI.setPosition(25, 110);

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
                mapMgr.clearCurrentMapEntity();
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
                    InventoryUI.clearInventoryItems(inventoryUI.getInventorySlotTable());
                    InventoryUI.clearInventoryItems(inventoryUI.getEquipSlotTable());

                    Map<String, Integer> allAmmoCount = new HashMap<>();
                    for (AmmoID ammo: AmmoID.values()) {
                        String nameAmmo = ammo.getValue();
                        allAmmoCount.put(nameAmmo, 100); // should be 0 Ammo
                    }
                    WeaponSystem.setBagAmmunition(allAmmoCount);

                    Array<Item.ItemID> inventoryItems = player.getEntityConfig().getInventory(); // дефолтные предметы из EntityConfig
                    Array<InventoryItemLocation> itemLocations = new Array<>();
                    for( int i = 0; i < inventoryItems.size; i++){
                        itemLocations.add(new InventoryItemLocation(i, inventoryItems.get(i).toString(), 1, 0, InventoryUI.PLAYER_INVENTORY)); // расставляем предметы
                    }
                    InventoryUI.populateInventory(inventoryUI.getInventorySlotTable(), itemLocations, inventoryUI.getDragAndDrop(), InventoryUI.PLAYER_INVENTORY, false);

                    player.setCurrentPosition(new Vector2(1188,281));
                    player.setCurrentDirection(Entity.Direction.LEFT);
                    player.setHealth(100);
                    player.setMaxHealth(100);
                    player.setExperience(5);
                    player.setDamageResist(0);
                    player.setDashCharge(4);
                    player.setMaxDashCharges(4);
                    player.setDashSpeed(0);
                    player.setDashDist(0);
                    player.setExoskeletonName(EntityFactory.EntityName.NONE);
                    player.setPlayerSkills(new Array<Integer>());
                    player.setAvailableSkills(new Array<Integer>());
                    player.getAvailableSkills().addAll(0,1,2);

                    progressBar.setValue(player.getDashCharge());
                    profileManager.getPlayerConfig().setCoins(10);
                    browserUI.setShopItems(player.getEntityConfig().getShopItems());
                    questUI.loadQuest("main/plot/start.json");

                    Skill firstSkill = SkillFactory.getInstance().getSkill(0);
                    Skill secondSkill = SkillFactory.getInstance().getSkill(1);
                    Skill thirdSkill = SkillFactory.getInstance().getSkill(2);
                    firstSkill.unlockSkill(player);
                    secondSkill.unlockSkill(player);
                    thirdSkill.unlockSkill(player);


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

                    player.setCurrentPosition(profileManager.getPlayerConfig().getPosition());
                    player.setCurrentDirection(profileManager.getPlayerConfig().getDirection());
                    player.setDashCharge(profileManager.getPlayerConfig().getDashCharges());
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
                    questUI.setQuests(profileManager.getPlayerConfig().getQuests());
                    progressBar.setValue(profileManager.getPlayerConfig().getDashCharges());
                    browserUI.setShopItems(profileManager.getPlayerConfig().getShopItems());

                    if (profileManager.getPlayerConfig().getExoskeletonName() != EntityFactory.EntityName.NONE) {
                        EntityFactory.EntityName exoskeletonName = profileManager.getPlayerConfig().getExoskeletonName();
                        player.sendMessage(Message.MESSAGE.SET_EXOSKELETON, json.toJson(exoskeletonName));
                    }
                }

                pdaUI.setCoins(profileManager.getPlayerConfig().getCoins());
                skillUI.createSkillTree(player);
                this.addActor(skillUI.getSkillTooltip());

                break;
            case SAVING_PROFILE:
                System.out.println("PROFILE CONFIG SAVING");
                profileManager.getSettingsConfig().setLastActiveAccount(profileManager.getProfileName());
                profileManager.getPlayerConfig().setQuests(questUI.getQuests()); // Quests
                profileManager.getPlayerConfig().setBagAmmunition(player.getBagAmmunition()); // Bag Ammunition
                profileManager.getPlayerConfig().setInventory(InventoryUI.getInventory(inventoryUI.getInventorySlotTable())); // Inventory
                profileManager.getPlayerConfig().setEquipment(InventoryUI.getInventory(inventoryUI.getEquipSlotTable())); // Equipment
                profileManager.getPlayerConfig().setHealth(player.getHealth());
                profileManager.getPlayerConfig().setMaxHp(player.getMaxHealth());
                profileManager.getPlayerConfig().setDashDist(player.getDashDist());
                profileManager.getPlayerConfig().setState(player.getCurrentState());
                profileManager.getPlayerConfig().setDashSpeed(player.getDashSpeed());
                profileManager.getPlayerConfig().setHealAmount(player.getHealAmount());
                profileManager.getPlayerConfig().setExperience(player.getExperience());
                profileManager.getPlayerConfig().setCritChance(player.getCritChanсe());
                profileManager.getPlayerConfig().setDashCharges(player.getDashCharge());
                profileManager.getPlayerConfig().setDamageBoost(player.getDamageBoost());
                profileManager.getPlayerConfig().setWeaponSpeed(player.getWeaponSpeed());
                profileManager.getPlayerConfig().setPosition(player.getCurrentPosition());
                profileManager.getPlayerConfig().setPlayerSkills(player.getPlayerSkills());
                profileManager.getPlayerConfig().setDamageResist(player.getDamageResist());
                profileManager.getPlayerConfig().setDirection(player.getCurrentDirection());
                profileManager.getPlayerConfig().setMaxDashCharges(player.getMaxDashCharges());
                profileManager.getPlayerConfig().setExoskeletonName(player.getExoskeletonName());
                profileManager.getPlayerConfig().setAvailableSkills(player.getAvailableSkills());
                profileManager.getPlayerConfig().setRudimentCooldown(player.getRudimentCooldown());
                profileManager.getPlayerConfig().setMeleeDamageBoost(player.getMeleeDamageBoost());
                profileManager.getPlayerConfig().setRangedDamageBoost(player.getRangedDamageBoost());
                profileManager.getPlayerConfig().setExecutionThreshold(player.getExecutionThreshold());
                profileManager.getPlayerConfig().setExoskeletonName(player.getExoskeletonName());
                profileManager.getPlayerConfig().setCoins(pdaUI.getCoins());
                profileManager.getPlayerConfig().setShopItems(browserUI.getShopItems());
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
            case PLAYER_SHOT:
                String string = json.fromJson(String.class, value);
                String[] splitStr = string.split(MESSAGE_TOKEN_2);
                statusUI.setLabelAmmoCountText(splitStr[0] + "/" + splitStr[1]);
                if(inventoryUI.getItemFromUniqueRudimentSlot().isRudimentWeapon()){
                    inventoryUI.getItemFromUniqueRudimentSlot().setNumberItemsInside(Integer.parseInt(splitStr[0]));
                }else {
                    inventoryUI.getItemFromWeaponRangedWeaponSlot().setNumberItemsInside(Integer.parseInt(splitStr[0]));
                }
                break;
            case ENEMY_DEAD:
                questUI.updateQuests(mapMgr);
//                mapMgr.setMapChanged(true);
                mapMgr.clearCurrentMapEntity();
                break;
            case ITEM_PICK_UP:
                String string2 = json.fromJson(String.class, value);
                String[] splitStr2 = string2.split(MESSAGE_TOKEN_2);

                Item.ItemID itemID = Item.ItemID.valueOf(splitStr2[0]);
                inventoryUI.addItemToInventory(itemID);
                tooltipUI.addTooltip(itemID.toString() + " added to inventory");
                mapMgr.deleteCurrentMapEntity();
                gameScreen.getEntities().remove(mapMgr.getCurrentMapEntity());
                mapMgr.clearCurrentMapEntity();

                if (!Boolean.parseBoolean(splitStr2[1])) {
                    mapMgr.addIdEntityForDelete(Integer.parseInt(splitStr2[2]));
                }
                break;
            case PLAYER_DASH:
                progressBar.minusValue(0.25f);
                break;
            case PLAYER_DASH_UPDATE:
                progressBar.plusValue(0.25f);
                break;
            case OPEN_PDA:
                pdaUI.setVisible(true);
                break;
            case CLOSE_PDA:
                pdaUI.setVisible(false);
                break;
            case OPEN_INVENTORY:
                inventoryUI.setVisible(true);
                break;
            case CLOSE_INVENTORY:
                inventoryUI.setVisible(false);
                break;
            case TEST_EVENT:
//                System.out.println(mapMgr.getCurrentMapEntity().getEntityConfig().getEntityID());
//                mapMgr.clearCurrentMapEntity();
//                mapMgr.deleteCurrentMapEntity();
//                gameScreen.getEntities().remove(mapMgr.getCurrentMapEntity());
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
                mapMgr.clearCurrentMapEntity();
                break;
            case EXIT_CONVERSATION:
                System.out.println("Exit conversation!");

                conversationUI.setVisible(false);
                mapMgr.clearCurrentMapEntity();
                break;
            case TASK_COMPLETE:
                questUI.updateQuests(mapMgr);
//                mapMgr.setMapChanged(true);
//                mapMgr.clearCurrentMapEntity();

//                Entity currentlyEntity2 = mapMgr.getCurrentMapEntity();
//                QuestGraph graph2 = json.fromJson(QuestGraph.class, Gdx.files.internal(currentlyEntity2.getEntityConfig().getQuestConfigPath()));
//                questUI.questTaskComplete(graph2.getQuestID(), questTaskID);

                conversationUI.setVisible(false);

//                float sec = 1;
//                Timer.schedule(new Timer.Task(){
//                    @Override
//                    public void run() {
//                        conversationUI.setVisible(false);
//                        mapMgr.clearCurrentMapEntity();
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
                if(item.isInventoryItemMelee()) {
                    Item.ItemID itemID = item.getItemID();
                    statusUI.setMeleeWeapon(itemID);
                    player.sendMessage(Message.MESSAGE.SET_MELEE_WEAPON, json.toJson(itemID.toString()));
                } else if (item.isInventoryItemRanged()) {
                    String itemID = item.getItemID().toString();
                    int ammoCountInMagazine = item.getNumberItemsInside();
                    statusUI.setRangeWeapon(item);
                    player.sendMessage(Message.MESSAGE.SET_RANGED_WEAPON, json.toJson(itemID + MESSAGE_TOKEN_2 + ammoCountInMagazine));
                } else if (item.isInventoryItemRudimentOne()) {
                    Item.ItemID itemID = item.getItemID();
                    player.sendMessage(Message.MESSAGE.SET_RUDIMENT_ONE, json.toJson(itemID.toString()));
                } else if (item.isInventoryItemRudimentTwo()) {
                    Item.ItemID itemID = item.getItemID();
                    player.sendMessage(Message.MESSAGE.SET_RUDIMENT_TWO, json.toJson(itemID.toString()));
                } else if (item.isInventoryItemUniqueRudiment() && item.isRudimentWeapon()) {
                    System.out.println("dadda");
                } else if (item.isInventoryItemUniqueRudiment()) {
                    Item.ItemID itemID = item.getItemID();
                    player.sendMessage(Message.MESSAGE.SET_UNIQUE_RUDIMENT, json.toJson(itemID.toString()));
                }
                break;
            case REMOVED:
                if(item.isInventoryItemMelee()) {
                    statusUI.clearMeleeWeapon();
                    player.sendMessage(Message.MESSAGE.REMOVE_MELEE_WEAPON, json.toJson("null"));
                } else if(item.isInventoryItemRanged()) {
                    statusUI.clearRangeWeapon();
                    player.sendMessage(Message.MESSAGE.REMOVE_RANGED_WEAPON, json.toJson("null"));
                } else if (item.isInventoryItemRudimentOne()) {
                    player.sendMessage(Message.MESSAGE.REMOVE_RUDIMENT_ONE, json.toJson("null"));
                } else if (item.isInventoryItemRudimentTwo()) {
                    player.sendMessage(Message.MESSAGE.REMOVE_RUDIMENT_TWO, json.toJson("null"));
                } else if (item.isInventoryItemUniqueRudiment()) {
                    player.sendMessage(Message.MESSAGE.REMOVE_UNIQUE_RUDIMENT, json.toJson("null"));
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

    public void setV(float zoom) {
        this.zoom = zoom;
        this.playerPosition = player.getCurrentPosition();
        this.mouseCoordinates = player.getMouseCoordinates();
        this.currentState = player.getCurrentState().toString();
        this.mapName = mapMgr.getCurrentMap().getCurrentMapType().toString();
    }

    public void update() {
        if (!browserUI.isVisible()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                questUI.setVisible(!questUI.isVisible());
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
                skillUI.setVisible(!skillUI.isVisible());
            }
        }
    }

    public void render(float delta) {
        tooltip1.setText("Delta = " + delta + "\n" +
                "FPS = " + Gdx.graphics.getFramesPerSecond() + "\n" +
                "State = " + currentState + "\n" +
                "Ammo = " + player.getAmmoCountInMagazine() + "\n" +
                "Mouse Cord = X: " + Math.round(mouseCoordinates.x) + " Y: " + Math.round(mouseCoordinates.y) + "\n" +
                "Camera Zoom = " + zoom + "\n" +
                "Player Position = X: " + playerPosition.x + " Y: " + playerPosition.y + "\n" +
                "Map = " + mapName);

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
