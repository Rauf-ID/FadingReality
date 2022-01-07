package com.mygdx.game.observer;


import com.mygdx.game.inventory.Item;

public interface InventorySubject {
    void addObserver(InventoryObserver inventoryObserver);
    void removeObserver(InventoryObserver inventoryObserver);
    void removeAllObservers();
    void notify(final Item item, InventoryObserver.InventoryEvent event);
}
