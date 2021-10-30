package com.mygdx.game.observer;

import com.mygdx.game.inventory.InventoryItem;

public interface InventoryObserver {
    public static enum InventoryEvent {
        UPDATED_AP,
        UPDATED_DP,
        ITEM_CONSUMED,
        ADD_WAND_AP,
        ADDED,
        REMOVED,
        REMOVE_WAND_AP,
        NONE
    }

    void onNotify(final InventoryItem item, InventoryEvent event);
}