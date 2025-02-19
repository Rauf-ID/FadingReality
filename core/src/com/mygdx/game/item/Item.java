package com.mygdx.game.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class Item extends Image {

    public enum ItemAttribute{
        CONSUMABLE,
        EQUIPPABLE,
        STACKABLE,
        ITEM_INSIDE,
        LIGHT_WEAPON,
        MEDIUM_WEAPON,
        HEAVY_WEAPON,
        RUDIMENT_WEAPON
    }

    public enum ItemUseType{
        ITEM_RESTORE_HEALTH,
        ITEM_RESTORE_MP,
        ITEM_DAMAGE,
        QUEST_ITEM,
        ARMOR,
        MELEE_WEAPON,
        RANGED_WEAPON,
        MEDIC_KIT,
        RUDIMENT,
        UNIQUE_RUDIMENT,
        WEAPON_RUDIMENT,
        NONE
    }

    public enum ItemID {
        ARMOR01,ARMOR02,ARMOR03,ARMOR04,ARMOR05,
        BOOTS01,BOOTS02,BOOTS03,BOOTS04,BOOTS05,
        HELMET01,HELMET02,HELMET03,HELMET04,HELMET05,
        SHIELD01,SHIELD02,SHIELD03,SHIELD04,SHIELD05,
        WANDS01,WANDS02,WANDS03,WANDS04,WANDS05,
        WEAPON01,WEAPON02,WEAPON03,WEAPON04,WEAPON05,
        POTIONS01,POTIONS02,POTIONS03,
        SCROLL01,SCROLL02,SCROLL03,
        HERB001,BABY001,HORNS001,FUR001,
        ALG, PREDATOR,
        AHR,
        ANGEL_BLADE,
        ICHOR,
        ATOMIC_HEART, RUBEDO,
        VORTEX, SUPERIOR,
        NONE
    }

    // начальные настройки предмета
    private ItemID itemID;
    private String itemShortDescription;
    private Array<ItemAttribute> itemAttributes;
    private ItemUseType itemUseType;
    private int itemUseTypeValue; // amount of something
    private int itemValue; // for Trade Value

    // изменяемые настройки предмета
    private int numberItemsInside;

    public Item(){
        super();
    }

    public Item(TextureRegion textureRegion, ItemID itemID, ItemUseType itemUseType, Array<ItemAttribute> itemAttribute, int itemUseTypeValue, int itemValue){
        super(textureRegion);

        this.itemID = itemID;
        this.itemUseType = itemUseType;
        this.itemAttributes = itemAttribute;
        this.itemUseTypeValue = itemUseTypeValue;
        this.itemValue = itemValue;
    }

    public Item(Item item){
        super();
        this.itemID = item.getItemID();
        this.itemShortDescription = item.getItemShortDescription();
        this.itemUseType = item.getItemUseType();
        this.itemAttributes = item.getItemAttributes();
        this.itemUseTypeValue = item.getItemUseTypeValue();
        this.itemValue = item.getItemValue();
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

    public ItemID getItemID() {
        return itemID;
    }

    public void setItemID(ItemID itemID) {
        this.itemID = itemID;
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


    public boolean isSameItemType(Item candidateItem){ //Если два предмета одного типа
        return itemID == candidateItem.getItemID();
    }

    public int getTradeValue(){
//        For now, we will set the trade in value of items at about one third their original value
        if( itemValue >= 0 ) {
            return MathUtils.floor(itemValue * .33f) + 2;
        }else{
            return 0;
        }
    }

    public boolean isConsumable(){
        return itemAttributes.contains(ItemAttribute.CONSUMABLE, true);
    }

    public boolean isEquippable(){
        return itemAttributes.contains(ItemAttribute.EQUIPPABLE, true);
    }

    public boolean isStackable(){
        return itemAttributes.contains(ItemAttribute.STACKABLE, true);
    }

    public boolean hasItemInside() {
        return itemAttributes.contains(ItemAttribute.ITEM_INSIDE, true);
    }

    public  boolean isLightWeapon() {
        return  itemAttributes.contains(ItemAttribute.LIGHT_WEAPON, true);
    }

    public  boolean isMediumWeapon() {
        return  itemAttributes.contains(ItemAttribute.MEDIUM_WEAPON, true);
    }

    public  boolean isHeavyWeapon() {
        return  itemAttributes.contains(ItemAttribute.HEAVY_WEAPON, true);
    }

    public  boolean isRudimentWeapon() {
        if(itemAttributes==null){
            return false;
        }else {
            return itemAttributes.contains(ItemAttribute.RUDIMENT_WEAPON, true);
        }
    }

    public boolean isInventoryItemRanged() {
        return itemUseType == ItemUseType.RANGED_WEAPON;
    }

    public boolean isInventoryItemMelee() {
        return itemUseType == ItemUseType.MELEE_WEAPON;
    }

    public boolean isInventoryItemRudimentOne() {
        return itemUseType == ItemUseType.RUDIMENT;
    }

    public boolean isInventoryItemRudimentTwo() {
        return itemUseType == ItemUseType.RUDIMENT;
    }

    public boolean isInventoryItemUniqueRudiment() {
        return itemUseType == ItemUseType.UNIQUE_RUDIMENT;
    }

    public boolean isInventoryItemOffensiveWeapon(){
        return itemUseType == ItemUseType.MELEE_WEAPON || itemUseType == ItemUseType.RANGED_WEAPON;
    }

    public boolean isInventoryItemDefensive(){
        return itemUseType == ItemUseType.ARMOR;
    }

}
