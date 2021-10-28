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
import com.mygdx.game.component.Message;
import com.mygdx.game.observer.InventoryObserver;
import com.mygdx.game.observer.InventorySlotObserver;
import com.mygdx.game.observer.InventorySubject;
import com.mygdx.game.inventory.InventoryItem;
import com.mygdx.game.inventory.InventoryItemFactory;
import com.mygdx.game.inventory.InventoryItemLocation;
import com.mygdx.game.inventory.InventorySlot;
import com.mygdx.game.inventory.InventorySlotSource;
import com.mygdx.game.inventory.InventorySlotTarget;
import com.mygdx.game.inventory.InventorySlotTooltip;
import com.mygdx.game.inventory.InventoryItem.ItemTypeID;
import com.mygdx.game.inventory.InventoryItem.ItemUseType;
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
        inventorySlotTooltip = new InventorySlotTooltip(Utility.STATUSUI_SKIN);

        InventorySlot leftArmSlot = new InventorySlot(
                ItemUseType.WEAPON_ONEHAND.getValue() |
                        ItemUseType.WEAPON_TWOHAND.getValue() |
                        ItemUseType.ARMOR_SHIELD.getValue() |
                        ItemUseType.WAND_ONEHAND.getValue() |
                        ItemUseType.WAND_TWOHAND.getValue(),
                new Image(Utility.ITEMS_TEXTUREATLAS.findRegion("inv_weapon"))
        );

        InventorySlot chestSlot = new InventorySlot(
                ItemUseType.ARMOR_CHEST.getValue(),
                new Image(Utility.ITEMS_TEXTUREATLAS.findRegion("inv_chest")));

        leftArmSlot.addListener(new InventorySlotTooltipListener(inventorySlotTooltip));
        chestSlot.addListener(new InventorySlotTooltipListener(inventorySlotTooltip));

        leftArmSlot.addObserver(this);
        chestSlot.addObserver(this);

        dragAndDrop.addTarget(new InventorySlotTarget(leftArmSlot));
        dragAndDrop.addTarget(new InventorySlotTarget(chestSlot));

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
                                              if( getTapCount() == 2 ){ // если нажал дважды на предмет
                                                  InventorySlot slot = (InventorySlot)event.getListenerActor();
                                                  if( slot.hasItem() ){
                                                      InventoryItem item = slot.getTopInventoryItem();
                                                      if( item.isConsumable() ){
                                                          String itemInfo = item.getItemUseType() + Message.MESSAGE_TOKEN + item.getItemUseTypeValue();
                                                          InventoryUI.this.notify(itemInfo, InventoryObserver.InventoryEvent.ITEM_CONSUMED);
                                                          slot.remove(item);
                                                      }
                                                  }
                                              }
                                          }
                                      }
            );

            if(i % lengthSlotRow == 0) inventorySlotTable.row();

        }

        equipSlots.add(leftArmSlot).size(slotWidth, slotHeight);
        equipSlots.add(chestSlot).size(slotWidth, slotHeight);

        playerSlotsTable.add(equipSlots);
        inventoryActors.add(inventorySlotTooltip);

        this.add(playerSlotsTable).padBottom(20);
        this.row();
        this.add(inventorySlotTable).colspan(2);
        this.row();
        this.pack();
    }

    public static void populateInventory(Table targetTable, Array<InventoryItemLocation> inventoryItemLocations, DragAndDrop draganddrop, String defaultName, boolean disableNonDefaultItems) {
        clearInventoryItems(targetTable); // очистить инвентарь от предметов

        Array<Cell> cells = targetTable.getCells(); // все ячейки иневнтаря
        for(InventoryItemLocation itemLocation: inventoryItemLocations){ // заполняем инвентарь текущими предметами
            ItemTypeID itemTypeID = ItemTypeID.valueOf(itemLocation.getItemTypeAtLocation());
            InventorySlot inventorySlot = (InventorySlot) cells.get(itemLocation.getLocationIndex()).getActor();

            for( int index = 0; index < itemLocation.getNumberItemsAtLocation(); index++ ){ // каждую ед. предмета добавляем в слот
                InventoryItem item = InventoryItemFactory.getInstance().getInventoryItem(itemTypeID);
                String itemName =  itemLocation.getItemNameProperty();

                if( itemName == null || itemName.isEmpty() ){ // задаем имя предмету
                    item.setName(defaultName);
                }else{
                    item.setName(itemName);
                }

                inventorySlot.add(item); // добавляем предмет в слот
                if( item.getName().equalsIgnoreCase(defaultName) ){
                    draganddrop.addSource(new InventorySlotSource(inventorySlot, draganddrop));
                }else if( disableNonDefaultItems == false ){ // если не дефолтный предмет ???
                    draganddrop.addSource(new InventorySlotSource(inventorySlot, draganddrop));
                }
            }
        }
    }

    public static Array<InventoryItemLocation> getInventory(Table targetTable){ // получить весь инвентарь
        Array<Cell> cells = targetTable.getCells();
        Array<InventoryItemLocation> items = new Array<InventoryItemLocation>();
        for(int i = 0; i < cells.size; i++){
            InventorySlot inventorySlot =  ((InventorySlot)cells.get(i).getActor());
            if( inventorySlot == null ) continue;
            int numItems = inventorySlot.getNumItems();
            if( numItems > 0 ){
                items.add(new InventoryItemLocation(
                        i,
                        inventorySlot.getTopInventoryItem().getItemTypeID().toString(),
                        numItems,
                        inventorySlot.getTopInventoryItem().getName()));
            }
        }
        return items;
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

    public Table getEquipSlotTable() {
        return equipSlots;
    }



    @Override
    public void onNotify(InventorySlot slot, SlotEvent event) {
        switch(event)
        {
            case ADDED_ITEM:
                InventoryItem addItem = slot.getTopInventoryItem();
                if( addItem == null ) return;
                if( addItem.isInventoryItemOffensive() ){
                    if( addItem.isInventoryItemOffensiveWand() ){
                        notify(String.valueOf(addItem.getItemUseTypeValue()), InventoryObserver.InventoryEvent.ADD_WAND_AP);
                    }

                }
                break;
            case REMOVED_ITEM:
                InventoryItem removeItem = slot.getTopInventoryItem();
                System.out.println("WTF");
                if( removeItem == null ) return;
                if( removeItem.isInventoryItemOffensive() ){
                    if( removeItem.isInventoryItemOffensiveWand() ){
                        notify(String.valueOf(removeItem.getItemUseTypeValue()), InventoryObserver.InventoryEvent.REMOVE_WAND_AP);
                    }

                }
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
    public void notify(String value, InventoryObserver.InventoryEvent event) {
        for(InventoryObserver observer: observers){
            observer.onNotify(value, event);
        }
    }

}
