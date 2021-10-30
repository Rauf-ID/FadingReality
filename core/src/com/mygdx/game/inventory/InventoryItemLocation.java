package com.mygdx.game.inventory;


public class InventoryItemLocation {

    private int locationIndex; // расположение в иневентаре
    private String itemTypeAtLocation; // тип предмета
    private int numberItemsAtLocation; // кол. едениц предмета
    private int numberItemsInside; // кол. элементов внутри предмета
    private String itemNameProperty; // ничего особенного

    public InventoryItemLocation(){
    }

    public InventoryItemLocation(int locationIndex, String itemTypeAtLocation, int numberItemsAtLocation, int numberItemsInside, String itemNameProperty){
        this.locationIndex = locationIndex;
        this.itemTypeAtLocation = itemTypeAtLocation;
        this.numberItemsAtLocation = numberItemsAtLocation;
        this.numberItemsInside = numberItemsInside;
        this.itemNameProperty = itemNameProperty;
    }

    public String getItemNameProperty() {
        return itemNameProperty;
    }

    public void setItemNameProperty(String itemNameProperty) {
        this.itemNameProperty = itemNameProperty;
    }

    public String getItemTypeAtLocation() {
        return itemTypeAtLocation;
    }

    public void setItemTypeAtLocation(String itemTypeAtLocation) {
        this.itemTypeAtLocation = itemTypeAtLocation;
    }

    public int getLocationIndex() {
        return locationIndex;
    }

    public void setLocationIndex(int locationIndex) {
        this.locationIndex = locationIndex;
    }

    public int getNumberItemsAtLocation() {
        return numberItemsAtLocation;
    }

    public void setNumberItemsAtLocation(int numberItemsAtLocation) {
        this.numberItemsAtLocation = numberItemsAtLocation;
    }

    public int getNumberItemsInside() {
        return numberItemsInside;
    }

    public void setNumberItemsInside(int numberItemsInside) {
        this.numberItemsInside = numberItemsInside;
    }
}