package com.mygdx.game.inventory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class InventoryItem extends Image {

    public enum ItemAttribute{
        CONSUMABLE,
        EQUIPPABLE,
        STACKABLE,
        ITEM_INSIDE
    }

    public enum ItemUseType{
        ITEM_RESTORE_HEALTH,
        ITEM_RESTORE_MP,
        ITEM_DAMAGE,
        WEAPON_ONEHAND,
        WEAPON_TWOHAND,
        WAND_ONEHAND,
        WAND_TWOHAND,
        ARMOR_SHIELD,
        ARMOR_HELMET,
        ARMOR_CHEST,
        ARMOR_FEET,
        QUEST_ITEM,
        NONE
    }

    public enum ItemTypeID {
        ARMOR01,ARMOR02,ARMOR03,ARMOR04,ARMOR05,
        BOOTS01,BOOTS02,BOOTS03,BOOTS04,BOOTS05,
        HELMET01,HELMET02,HELMET03,HELMET04,HELMET05,
        SHIELD01,SHIELD02,SHIELD03,SHIELD04,SHIELD05,
        WANDS01,WANDS02,WANDS03,WANDS04,WANDS05,
        WEAPON01,WEAPON02,WEAPON03,WEAPON04,WEAPON05,
        POTIONS01,POTIONS02,POTIONS03,
        SCROLL01,SCROLL02,SCROLL03,
        HERB001,BABY001,HORNS001,FUR001,
        PISTOL_ALG,
        NONE
    }

    // начальные настройки предмета
    private ItemTypeID itemTypeID;
    private String itemShortDescription;
    private Array<ItemAttribute> itemAttributes;
    private ItemUseType itemUseType;
    private int itemUseTypeValue; // amount of something
    private int itemValue; // for Trade Value

    // изменяемые настройки предмета
    private int numberItemsInside;

    public InventoryItem(){
        super();
    }

    public InventoryItem(TextureRegion textureRegion, ItemTypeID itemTypeID, ItemUseType itemUseType, Array<ItemAttribute> itemAttribute, int itemUseTypeValue, int itemValue){
        super(textureRegion);

        this.itemTypeID = itemTypeID;
        this.itemUseType = itemUseType;
        this.itemAttributes = itemAttribute;
        this.itemUseTypeValue = itemUseTypeValue;
        this.itemValue = itemValue;
    }

    public InventoryItem(InventoryItem inventoryItem){
        super();
        this.itemTypeID = inventoryItem.getItemTypeID();
        this.itemShortDescription = inventoryItem.getItemShortDescription();
        this.itemUseType = inventoryItem.getItemUseType();
        this.itemAttributes = inventoryItem.getItemAttributes();
        this.itemUseTypeValue = inventoryItem.getItemUseTypeValue();
        this.itemValue = inventoryItem.getItemValue();
    }

    public int getItemUseTypeValue() {
        return itemUseTypeValue;
    }

    public void setItemUseTypeValue(int itemUseTypeValue) {
        this.itemUseTypeValue = itemUseTypeValue;
    }

    public int getItemValue() {
        return itemValue;
    }

    public void setItemValue(int itemValue) {
        this.itemValue = itemValue;
    }

    public ItemTypeID getItemTypeID() {
        return itemTypeID;
    }

    public void setItemTypeID(ItemTypeID itemTypeID) {
        this.itemTypeID = itemTypeID;
    }

    public Array<ItemAttribute> getItemAttributes() {
        return itemAttributes;
    }

    public void setItemAttributes(Array<ItemAttribute> itemAttributes) {
        this.itemAttributes = itemAttributes;
    }

    public ItemUseType getItemUseType() {
        return itemUseType;
    }

    public void setItemUseType(ItemUseType itemUseType) {
        this.itemUseType = itemUseType;
    }

    public String getItemShortDescription() {
        return itemShortDescription;
    }

    public void setItemShortDescription(String itemShortDescription) {
        this.itemShortDescription = itemShortDescription;
    }

    public int getNumberItemsInside() {
        return numberItemsInside;
    }

    public void setNumberItemsInside(int numberItemsInside) {
        this.numberItemsInside = numberItemsInside;
    }


    public static boolean doesRestoreHP(ItemUseType itemUseType){
        return itemUseType == ItemUseType.ITEM_RESTORE_HEALTH;
    }

    public static boolean doesRestoreMP(ItemUseType itemUseType){
        return itemUseType == ItemUseType.ITEM_RESTORE_MP;
    }

    public boolean isStackable(){
        return itemAttributes.contains(ItemAttribute.STACKABLE, true);
    }

    public boolean isConsumable(){
        return itemAttributes.contains(ItemAttribute.CONSUMABLE, true);
    }

    public boolean hasItemInside() {
        return itemAttributes.contains(ItemAttribute.ITEM_INSIDE, true);
    }

    public boolean isSameItemType(InventoryItem candidateInventoryItem){ //Если два предмета одного типа
        return itemTypeID == candidateInventoryItem.getItemTypeID();
    }

    public int getTradeValue(){
        //For now, we will set the trade in value of items at about one third their original value
        if( itemValue >= 0 ) {
            return MathUtils.floor(itemValue * .33f) + 2;
        }else{
            return 0;
        }
    }

    public boolean isInventoryItemOffensiveWand(){
        return itemUseType == ItemUseType.WAND_ONEHAND|| itemUseType == ItemUseType.WAND_TWOHAND;
    }

    public boolean isInventoryItemOffensiveWeapon(){
        return itemUseType == ItemUseType.WEAPON_ONEHAND || itemUseType == ItemUseType.WEAPON_TWOHAND;
    }

    public boolean isInventoryItemOffensive(){
        return itemUseType == ItemUseType.WEAPON_ONEHAND || itemUseType == ItemUseType.WEAPON_TWOHAND || itemUseType == ItemUseType.WAND_ONEHAND || itemUseType == ItemUseType.WAND_TWOHAND;
    }

    public boolean isInventoryItemDefensive(){
        return itemUseType == ItemUseType.ARMOR_CHEST || itemUseType == ItemUseType.ARMOR_HELMET || itemUseType == ItemUseType.ARMOR_FEET || itemUseType == ItemUseType.ARMOR_SHIELD;
    }

}
