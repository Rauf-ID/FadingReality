package com.mygdx.game.inventory;


public class InventoryItemLocation {

    private int locationIndex; // расположение в иневентаре
    private int numberItemsAtLocation; // колл. едениц предмета
    private String itemTypeAtLocation; // тип предмета
    private String itemNameProperty; // ничего особенного

    public InventoryItemLocation(){
    }

    public InventoryItemLocation(int locationIndex, String itemTypeAtLocation, int numberItemsAtLocation, String itemNameProperty){
        this.locationIndex = locationIndex;
        this.itemTypeAtLocation = itemTypeAtLocation;
        this.numberItemsAtLocation = numberItemsAtLocation;
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
}