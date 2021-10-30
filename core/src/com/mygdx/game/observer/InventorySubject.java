package com.mygdx.game.observer;


import com.mygdx.game.inventory.InventoryItem;

public interface InventorySubject {
    void addObserver(InventoryObserver inventoryObserver);
    void removeObserver(InventoryObserver inventoryObserver);
    void removeAllObservers();
    void notify(final InventoryItem item, InventoryObserver.InventoryEvent event);
}
