package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;

public class InventorySlotTarget extends Target {

    InventorySlot targetSlot;

    public InventorySlotTarget(InventorySlot actor){
        super(actor);
        targetSlot = actor;
    }

    @Override
    public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
        return true;
    }

    @Override
    public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
    }

    @Override
    public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
        InventoryItem sourceActor = (InventoryItem) payload.getDragActor();
        InventoryItem targetActor = targetSlot.getTopInventoryItem();
        InventorySlot sourceSlot = ((InventorySlotSource)source).getSourceSlot();

        if( sourceActor == null ) {
            return;
        }

        //First, does the slot accept the source item type?
        if( !targetSlot.doesAcceptItemUseType(sourceActor.getItemUseType()))  {
            //Put item back where it came from, slot doesn't accept item
            sourceSlot.add(sourceActor);
            return;
        }

        if( !targetSlot.hasItem() ){
            targetSlot.add(sourceActor);
        }else{
            //If the same item and stackable, add
            if( sourceActor.isSameItemType(targetActor) && sourceActor.isStackable()){
                targetSlot.add(sourceActor);
            }else{
                //If they aren't the same items or the items aren't stackable, then swap
                InventorySlot.swapSlots(sourceSlot, targetSlot, sourceActor);
            }
        }

    }
}
