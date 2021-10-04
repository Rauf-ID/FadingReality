package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

public class InventorySlotSource extends DragAndDrop.Source {

    private DragAndDrop dragAndDrop;
    private InventorySlot sourceSlot;

    public InventorySlotSource(InventorySlot sourceSlot, DragAndDrop dragAndDrop) {
        super(sourceSlot.getTopInventoryItem());
        this.sourceSlot = sourceSlot;
        this.dragAndDrop = dragAndDrop;
    }

    @Override
    public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
        DragAndDrop.Payload payload = new DragAndDrop.Payload();

        Actor actor = getActor();
        if( actor == null ){
            return null;
        }

        InventorySlot source = (InventorySlot)actor.getParent();
        if( source == null ){
            return null;
        }else{
            sourceSlot = source;
        }

        sourceSlot.decrementItemCount(true);

        payload.setDragActor(getActor());
        dragAndDrop.setDragActorPosition(30, -30); // смещение актера во время перестакивания

        return payload;
    }

    @Override
    public void dragStop (InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
        if( target == null ){
            sourceSlot.add(payload.getDragActor());
        }
    }

    public InventorySlot getSourceSlot() {
        return sourceSlot;
    }
}
