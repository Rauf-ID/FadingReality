package com.mygdx.game.UI;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FadingReality;
import com.mygdx.game.observer.InventoryObserver;
import com.mygdx.game.observer.InventorySlotObserver;
import com.mygdx.game.observer.InventorySubject;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.ItemFactory;
import com.mygdx.game.inventory.InventoryItemLocation;
import com.mygdx.game.inventory.InventorySlot;
import com.mygdx.game.inventory.InventorySlotSource;
import com.mygdx.game.inventory.InventorySlotTarget;
import com.mygdx.game.inventory.InventorySlotTooltip;
import com.mygdx.game.item.Item.ItemID;
import com.mygdx.game.item.Item.ItemUseType;
import com.mygdx.game.inventory.InventorySlotTooltipListener;
import com.mygdx.game.tools.Utility;


public class InventoryUI extends Window implements InventorySubject, InventorySlotObserver{

    public static final String PLAYER_INVENTORY = "Player_Inventory";
    public static final String STORE_INVENTORY = "Store_Inventory";
    public final static int numSlots = 50;
    private final static int slotWidth = 64; // should be 100
    private final static int slotHeight = 64;
    private final static int lengthSlotRow = 10;

    private Array<InventoryObserver> observers;

    private Table inventorySlotTable;
    private Table playerSlotsTable;
    private Table equipSlots;
    private DragAndDrop dragAndDrop;
    private Array<Actor> inventoryActors;
    private InventorySlotTooltip inventorySlotTooltip;

    private InventorySlot meleeWeaponSlot, armorSlot, rangedWeaponSlot, medicKitSlot, ichorSlot, rudimentOneSlot, rudimentTwoSlot, uniqueRudimentSlot;

    public InventoryUI(){
        super("Inventory", FadingReality.getUiSkin());

        observers = new Array<InventoryObserver>();

        inventorySlotTable = new Table();
        inventorySlotTable.setName("Inventory_Slot_Table");

        playerSlotsTable = new Table();
        playerSlotsTable.setBackground(new Image(new NinePatch(Utility.STATUSUI.createPatch("dialog"))).getDrawable());

        equipSlots = new Table();
        equipSlots.setName("Equipment_Slot_Table");
        equipSlots.defaults().space(10);

        dragAndDrop = new DragAndDrop();
        inventoryActors = new Array<Actor>();
        inventorySlotTooltip = new InventorySlotTooltip(FadingReality.getUiSkin());

        meleeWeaponSlot = new InventorySlot(ItemUseType.MELEE_WEAPON, new Image(Utility.ITEMS_TEXTUREATLAS.findRegion("inv_melee")));
        armorSlot = new InventorySlot(ItemUseType.ARMOR, new Image(Utility.ITEMS_TEXTUREATLAS.findRegion("inv_chest")));
        rangedWeaponSlot = new InventorySlot(ItemUseType.RANGED_WEAPON, new Image(Utility.ITEMS_TEXTUREATLAS.findRegion("inv_ranged")));
        medicKitSlot = new InventorySlot(ItemUseType.MEDIC_KIT, new Image(Utility.ITEMS_TEXTUREATLAS.findRegion("none")));
        ichorSlot = new InventorySlot(ItemUseType.MEDIC_KIT, new Image(Utility.ITEMS_TEXTUREATLAS.findRegion("none")));
        rudimentOneSlot = new InventorySlot(ItemUseType.RUDIMENT, new Image(Utility.ITEMS_TEXTUREATLAS.findRegion("inv_rudiment_one")));
        uniqueRudimentSlot = new InventorySlot(ItemUseType.UNIQUE_RUDIMENT, new Image(Utility.ITEMS_TEXTUREATLAS.findRegion("inv_unique_rudiment")));
        rudimentTwoSlot = new InventorySlot(ItemUseType.RUDIMENT, new Image(Utility.ITEMS_TEXTUREATLAS.findRegion("inv_rudiment_two")));

        meleeWeaponSlot.addListener(new InventorySlotTooltipListener(inventorySlotTooltip));
        armorSlot.addListener(new InventorySlotTooltipListener(inventorySlotTooltip));
        rangedWeaponSlot.addListener(new InventorySlotTooltipListener(inventorySlotTooltip));
        medicKitSlot.addListener(new InventorySlotTooltipListener(inventorySlotTooltip));
        ichorSlot.addListener(new InventorySlotTooltipListener(inventorySlotTooltip));
        rudimentOneSlot.addListener(new InventorySlotTooltipListener(inventorySlotTooltip));
        uniqueRudimentSlot.addListener(new InventorySlotTooltipListener(inventorySlotTooltip));
        rudimentTwoSlot.addListener(new InventorySlotTooltipListener(inventorySlotTooltip));

        meleeWeaponSlot.addObserver(this);
        armorSlot.addObserver(this);
        rangedWeaponSlot.addObserver(this);
        medicKitSlot.addObserver(this);
        ichorSlot.addObserver(this);
        rudimentOneSlot.addObserver(this);
        uniqueRudimentSlot.addObserver(this);
        rudimentTwoSlot.addObserver(this);

        dragAndDrop.addTarget(new InventorySlotTarget(meleeWeaponSlot));
        dragAndDrop.addTarget(new InventorySlotTarget(armorSlot));
        dragAndDrop.addTarget(new InventorySlotTarget(rangedWeaponSlot));
        dragAndDrop.addTarget(new InventorySlotTarget(medicKitSlot));
        dragAndDrop.addTarget(new InventorySlotTarget(ichorSlot));
        dragAndDrop.addTarget(new InventorySlotTarget(rudimentOneSlot));
        dragAndDrop.addTarget(new InventorySlotTarget(uniqueRudimentSlot));
        dragAndDrop.addTarget(new InventorySlotTarget(rudimentTwoSlot));


        //layout
        for(int i = 1; i <= numSlots; i++){
            InventorySlot inventorySlot = new InventorySlot();
            inventorySlot.addListener(new InventorySlotTooltipListener(inventorySlotTooltip));
//            inventorySlot.addObserver(this); // без этого нет связи Инвента со Слотом
            dragAndDrop.addTarget(new InventorySlotTarget(inventorySlot));
            inventorySlotTable.add(inventorySlot).size(slotWidth, slotHeight).pad(2);

            inventorySlot.addListener(new ClickListener() {
                  @Override
                  public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                      super.touchUp(event, x, y, pointer, button);
                      if(getTapCount() == 2){ // если нажал дважды на предмет
                          InventorySlot slot = (InventorySlot)event.getListenerActor();
                          if(slot.hasItem()){
                              Item item = slot.getTopInventoryItem();
                              if(item.isConsumable()){
                                  System.out.println("Used");
                                  InventoryUI.this.notify(item, InventoryObserver.InventoryEvent.ITEM_CONSUMED);
                                  slot.remove(item);
                              }
                          }
                      }
                  }
            });

            if(i % lengthSlotRow == 0) inventorySlotTable.row();

        }

        equipSlots.add(medicKitSlot).size(slotWidth, slotHeight);
        equipSlots.add();
        equipSlots.add(ichorSlot).size(slotWidth, slotHeight).row();
        equipSlots.add(meleeWeaponSlot).size(slotWidth, slotHeight);
        equipSlots.add(armorSlot).size(slotWidth, slotHeight);
        equipSlots.add(rangedWeaponSlot).size(slotWidth, slotHeight).row();
        equipSlots.add(rudimentOneSlot).size(slotWidth, slotHeight);
        equipSlots.add(uniqueRudimentSlot).size(slotWidth, slotHeight);
        equipSlots.add(rudimentTwoSlot).size(slotWidth, slotHeight);

        playerSlotsTable.add(equipSlots);
        inventoryActors.add(inventorySlotTooltip);

//        this.row();
        this.add(inventorySlotTable).colspan(2);
        this.add(playerSlotsTable).padBottom(20);
        this.row();
        this.pack();
    }

    public static void populateInventory(Table targetTable, Array<InventoryItemLocation> inventoryItemLocations, DragAndDrop draganddrop, String defaultName, boolean disableNonDefaultItems) {

        clearInventoryItems(targetTable); // очистить инвентарь от предметов

        Array<Cell> cells = targetTable.getCells(); // все ячейки иневнтаря
        for(InventoryItemLocation itemLocation: inventoryItemLocations){ // заполняем инвентарь текущими предметами
            ItemID itemID = ItemID.valueOf(itemLocation.getItemTypeAtLocation());
            InventorySlot inventorySlot = (InventorySlot) cells.get(itemLocation.getLocationIndex()).getActor();

            for(int index = 0; index < itemLocation.getNumberItemsAtLocation(); index++ ){ // каждую ед. предмета добавляем в слот
                Item item = ItemFactory.getInstance().getInventoryItem(itemID);
                String itemName =  itemLocation.getItemNameProperty();

                if( itemName == null || itemName.isEmpty() ){ // задаем имя предмету
                    item.setName(defaultName);
                } else{
                    item.setName(itemName);
                }

                if (item.hasItemInside()) {
                    int numberItemsInside = itemLocation.getNumberItemsInside();
                    item.setNumberItemsInside(numberItemsInside);
                }

                inventorySlot.add(item); // добавляем предмет в слот
                if(item.getName().equalsIgnoreCase(defaultName)){
                    draganddrop.addSource(new InventorySlotSource(inventorySlot, draganddrop));
                }else if( disableNonDefaultItems == false ){ // если не дефолтный предмет ???
                    draganddrop.addSource(new InventorySlotSource(inventorySlot, draganddrop));
                }
            }
        }
    }

    public static Array<InventoryItemLocation> getInventory(Table targetTable){ // получить все предметы инвентаря
        Array<Cell> cells = targetTable.getCells();
        Array<InventoryItemLocation> items = new Array<InventoryItemLocation>();
        for(int i = 0; i < cells.size; i++){
            InventorySlot inventorySlot = ((InventorySlot)cells.get(i).getActor());
            if( inventorySlot == null ) continue;

            int numItems = inventorySlot.getNumItems();

            Item item = inventorySlot.getTopInventoryItem();
            if( item == null ) continue;

            int numberItemsInside = 0;
            if (item.hasItemInside()) {
                numberItemsInside = inventorySlot.getTopInventoryItem().getNumberItemsInside();
            }

            if( numItems > 0 ){
                items.add(new InventoryItemLocation(i, item.getItemID().toString(), numItems, numberItemsInside, inventorySlot.getTopInventoryItem().getName()));
            }
        }
        return items;
    }

    public void addItemToInventory(ItemID itemID){
        Array<Cell> sourceCells = inventorySlotTable.getCells();
        int index = 0;

        for (; index < sourceCells.size; index++) {
            InventorySlot inventorySlot = ((InventorySlot) sourceCells.get(index).getActor());
            if (inventorySlot == null) continue;
            int numItems = inventorySlot.getNumItems();
            if (numItems == 0) {
                Item item = ItemFactory.getInstance().getInventoryItem(itemID);
                item.setName(itemID.toString());
                inventorySlot.add(item);
                dragAndDrop.addSource(new InventorySlotSource(inventorySlot, dragAndDrop));
                break;
            }
        }
    }

    public boolean doesInventoryHaveSpace(){ // есть ли в инвентаре место
        Array<Cell> sourceCells = inventorySlotTable.getCells();
        int index = 0;

        for (; index < sourceCells.size; index++) {
            InventorySlot inventorySlot = ((InventorySlot) sourceCells.get(index).getActor());
            if (inventorySlot == null) continue;
            int numItems = inventorySlot.getNumItems();
            if (numItems == 0) {
                return true;
            }else{
                index++;
            }
        }
        return false;
    }

    public static void clearInventoryItems(Table targetTable){ // очищает инвентарь от всех предметов
        Array<Cell> cells = targetTable.getCells();
        for( int i = 0; i < cells.size; i++){
            InventorySlot inventorySlot = (InventorySlot) cells.get(i).getActor();
            if( inventorySlot == null ) continue;
            inventorySlot.clearAllInventoryItems(false); // очищаем слот от предмета
        }
    }

    public DragAndDrop getDragAndDrop(){
        return dragAndDrop;
    }

    public Table getInventorySlotTable() {
        return inventorySlotTable;
    }

    public Array<Actor> getInventoryActors(){
        return inventoryActors;
    }

    public InventorySlotTooltip getInventorySlotTooltip() {
        return inventorySlotTooltip;
    }

    public Table getEquipSlotTable() {
        return equipSlots;
    }

    public Item getItemFromWeaponRangedWeaponSlot() {
        return rangedWeaponSlot.getTopInventoryItem();
    }

    public Item getItemFromUniqueRudimentSlot(){
        if(uniqueRudimentSlot.getTopInventoryItem() == null){
            return new Item();
        }
        return uniqueRudimentSlot.getTopInventoryItem();
    }

    public InventorySlot getRangedWeaponSlot() {
        return rangedWeaponSlot;
    }

    public void setRangedWeaponSlot(InventorySlot rangedWeaponSlot) {
        this.rangedWeaponSlot = rangedWeaponSlot;
    }

    @Override
    public void onNotify(InventorySlot slot, SlotEvent event) {
        switch(event) {
            case ADDED_ITEM:
                Item addItem = slot.getTopInventoryItem();
                if(addItem == null) return;
                notify(addItem, InventoryObserver.InventoryEvent.ADDED);
                break;
            case REMOVED_ITEM:
                Item removeItem = slot.getTopInventoryItem();
                if(removeItem == null) return;
                notify(removeItem, InventoryObserver.InventoryEvent.REMOVED);
                break;
            default:
                break;
        }
    }

    @Override
    public void addObserver(InventoryObserver inventoryObserver) {
        observers.add(inventoryObserver);
    }

    @Override
    public void removeObserver(InventoryObserver inventoryObserver) {
        observers.removeValue(inventoryObserver, true);
    }

    @Override
    public void removeAllObservers() {
        for(InventoryObserver observer: observers){
            observers.removeValue(observer, true);
        }
    }

    @Override
    public void notify(Item item, InventoryObserver.InventoryEvent event) {
        for(InventoryObserver observer: observers){
            observer.onNotify(item, event);
        }
    }

}
