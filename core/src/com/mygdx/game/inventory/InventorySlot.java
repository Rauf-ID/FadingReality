package com.mygdx.game.inventory;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.game.observer.InventorySlotObserver;
import com.mygdx.game.observer.InventorySlotSubject;
import com.mygdx.game.tools.Utility;
import com.mygdx.game.inventory.InventoryItem.ItemUseType;

public class InventorySlot extends Stack implements InventorySlotSubject {

    //All slots have this default image
    private Stack defaultBackground;
    private Image customBackgroundDecal;
    private ItemUseType filterItemType;

    private Label numItemsLabel;
    private int numItems = 0; // кол. едениц предмета

    private Array<InventorySlotObserver> observers;


    public InventorySlot(){
        defaultBackground = new Stack();
        customBackgroundDecal = new Image();
        filterItemType = ItemUseType.NONE; // filter nothing

        observers = new Array<InventorySlotObserver>();

        Image image = new Image(new NinePatch(Utility.STATUSUI.createPatch("dialog")));

        numItemsLabel = new Label(String.valueOf(numItems), Utility.STATUSUI_SKIN);
        numItemsLabel.setAlignment(Align.bottomRight);
        numItemsLabel.setVisible(false);

        defaultBackground.add(image);

        defaultBackground.setName("background");
        numItemsLabel.setName("numitems");

        this.add(defaultBackground);
        this.add(numItemsLabel);
    }

    public InventorySlot(ItemUseType filterItemType, Image customBackgroundDecal){
        this();
        this.filterItemType = filterItemType;
        this.customBackgroundDecal = customBackgroundDecal;
        defaultBackground.add(this.customBackgroundDecal);
    }

    public void incrementItemCount(boolean sendAddNotification) {
        numItems++;
        numItemsLabel.setText(String.valueOf(numItems));
        if( defaultBackground.getChildren().size > 1 ){ // проверка на два слота(дефолт и кастомный), непонятно нахрена
            defaultBackground.getChildren().pop();
        }
        checkVisibilityOfItemCount(); // надо ли отобразить колл. ед.
        if(sendAddNotification) {
            notify(this, InventorySlotObserver.SlotEvent.ADDED_ITEM);
        }
    }

    public void decrementItemCount(boolean sendRemoveNotification) {
        numItems--;
        numItemsLabel.setText(String.valueOf(numItems));
        if( defaultBackground.getChildren().size == 1 ){ // проверка на дефолтный слот, непонятно нахрена
            defaultBackground.add(customBackgroundDecal);
        }
        checkVisibilityOfItemCount();
        if( sendRemoveNotification ){
            notify(this, InventorySlotObserver.SlotEvent.REMOVED_ITEM);
        }
    }


    @Override
    public void add(Actor actor) {
        super.add(actor);

        if( numItemsLabel == null ){
            return;
        }

        if(!actor.equals(defaultBackground) && !actor.equals(numItemsLabel)) {
            incrementItemCount(true);
        }
    }

    public void add(Array<Actor> array) {
        for( Actor actor : array){
            super.add(actor);

            if( numItemsLabel == null ){
                return;
            }

            if( !actor.equals(defaultBackground) && !actor.equals(numItemsLabel) ) {
                incrementItemCount(true);
            }
        }
    }

    public void remove(Actor actor) {
        super.removeActor(actor);

        if( numItemsLabel == null ){
            return;
        }

        if( !actor.equals(defaultBackground) && !actor.equals(numItemsLabel) ) { // если предмет не является фоном и меткой колличества, то уменьшить колл. предмета
            decrementItemCount(true);
        }
    }


    public Array<Actor> getAllInventoryItems() {
        Array<Actor> items = new Array<Actor>();
        if( hasItem() ){
            SnapshotArray<Actor> arrayChildren = this.getChildren();
            int numInventoryItems =  arrayChildren.size - 2;
            for(int i = 0; i < numInventoryItems; i++) {
                decrementItemCount(true);
                items.add(arrayChildren.pop());
            }
        }
        return items;
    }

    public void updateAllInventoryItemNames(String name){
        if( hasItem() ){
            SnapshotArray<Actor> arrayChildren = this.getChildren();
            //skip first two elements
            for(int i = arrayChildren.size - 1; i > 1 ; i--) {
                arrayChildren.get(i).setName(name);
            }
        }
    }

    public void removeAllInventoryItemsWithName(String name){
        if( hasItem() ){
            SnapshotArray<Actor> arrayChildren = this.getChildren();
            //skip first two elements
            for(int i = arrayChildren.size - 1; i > 1 ; i--) {
                String itemName = arrayChildren.get(i).getName();
                if( itemName.equalsIgnoreCase(name)){
                    decrementItemCount(true);
                    arrayChildren.removeIndex(i);
                }
            }
        }
    }


    public boolean hasItem(){ // есть ли предмет в слоте
        if( hasChildren() ){
            SnapshotArray<Actor> items = this.getChildren();
            if( items.size > 2 ){
                return true;
            }
        }
        return false;
    }

    public int getNumItems(){ // возвращает колличество ед. предмета
        if( hasChildren() ){
            SnapshotArray<Actor> items = this.getChildren();
            return items.size - 2;
        }
        return 0;
    }

    public int getNumItems(String name){ // возвращает колличество ед. предмета по имени
        if( hasChildren() ){
            SnapshotArray<Actor> items = this.getChildren();
            int totalFilteredSize = 0;
            for( Actor actor: items ){
                if( actor.getName().equalsIgnoreCase(name)){
                    totalFilteredSize++;
                }
            }
            return totalFilteredSize;
        }
        return 0;
    }

    private void checkVisibilityOfItemCount(){ // если ед. предмета больше 1, то отобразить колличество
        if( numItems < 2){
            numItemsLabel.setVisible(false);
        }else{
            numItemsLabel.setVisible(true);
        }
    }

    public void clearAllInventoryItems(boolean sendRemoveNotifications) {
        if( hasItem() ){
            SnapshotArray<Actor> arrayChildren = this.getChildren();
            int numInventoryItems = getNumItems();
            for(int i = 0; i < numInventoryItems; i++) {
                decrementItemCount(sendRemoveNotifications);
                arrayChildren.pop();
            }
        }
    }

    public InventoryItem getTopInventoryItem(){ // берем последний элемент в стеке предмета
        InventoryItem actor = null;
        if( hasChildren() ){
            SnapshotArray<Actor> items = this.getChildren();
            if( items.size > 2 ){
                actor = (InventoryItem) items.peek();
            }
        }
        return actor;
    }

    public boolean doesAcceptItemUseType(ItemUseType itemUseType) {
        if(filterItemType == ItemUseType.NONE){
            return true;
        } else {
            return filterItemType == itemUseType;
        }
    }

    static public void swapSlots(InventorySlot inventorySlotSource, InventorySlot inventorySlotTarget, InventoryItem dragActor){
        //check if items can accept each other, otherwise, no swap
        if( !inventorySlotTarget.doesAcceptItemUseType(dragActor.getItemUseType()) || !inventorySlotSource.doesAcceptItemUseType(inventorySlotTarget.getTopInventoryItem().getItemUseType())) {
            inventorySlotSource.add(dragActor);
            return;
        }

        //swap
        Array<Actor> tempArray = inventorySlotSource.getAllInventoryItems();
        tempArray.add(dragActor);
        inventorySlotSource.add(inventorySlotTarget.getAllInventoryItems());
        inventorySlotTarget.add(tempArray);
    }



    @Override
    public void addObserver(InventorySlotObserver slotObserver) {
        observers.add(slotObserver);
    }

    @Override
    public void removeObserver(InventorySlotObserver slotObserver) {
        observers.removeValue(slotObserver, true);
    }

    @Override
    public void removeAllObservers() {
        for(InventorySlotObserver observer: observers){
            observers.removeValue(observer, true);
        }
    }

    @Override
    public void notify(final InventorySlot slot, InventorySlotObserver.SlotEvent event) {
        for(InventorySlotObserver observer: observers){
            observer.onNotify(slot, event);
        }
    }
}